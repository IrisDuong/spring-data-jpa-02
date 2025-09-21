package com.kps.jpa.inventory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AllocationService {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	InventoryRepository inventoryRepo;

	@Autowired
	StockReservationRepository reservationRepo;


	@Transactional
	public void allocate(String orderId, List<OrderItem> items) {
		int maxRetries = 5;
		int attempt = 0;

		while (true) {
			try {
				doAllocateInTx(orderId, items);
				asyncAudit(orderId, "RESERVED_OK");
				return;
			} catch (OptimisticLockingFailureException | TransactionSystemException e) {
				// TODO: handle exception
				if(++attempt > maxRetries) {
					log.error("Allocate failed after retries. orderId={} err={}", orderId, e.getMessage());
					throw e;
				}
				try {
					Thread.sleep(50L * attempt);
				} catch (InterruptedException ignored) {
					// TODO: handle exception
					log.warn("Retry allocate attempt={} orderId={}", attempt, orderId);
				}
				e.printStackTrace();
			}
		}
	}

	protected void doAllocateInTx(String orderId, List<OrderItem> items) {
		List<StockReservation> created = new ArrayList<StockReservation>();

		for (OrderItem item : items) {

			Product product = productRepo.findBySku(item.sku())
					.orElseThrow(() -> new IllegalArgumentException("SKU not found: " + item.sku()));
			log.info("product name = "+product.getName());
			long remain = item.qty();
			List<Inventory> invs = inventoryRepo.lockAllByProductOrderByWarehouse(product);
			log.info("invs szie = "+invs.size());
			for (Inventory inv : invs) {
				log.info("invs warehouse codee = "+inv.getWarehouseCode());
				if (remain <= 0)
					break;
				long canTake = Math.min(remain, Math.max(0, inv.available()));
				if (canTake > 0) {
					inv.setReservedQuantity(inv.getReservedQuantity() + canTake);
					remain -= canTake;
				}
			}
			if (remain > 0)
				throw new IllegalStateException("Insufficient stock for SKU=" + item.sku());

			for (Inventory inv : invs) {
				StockReservation reservation = StockReservation.builder().orderId(orderId).product(product)
						.warehouseCode(inv.getWarehouseCode()).quantity(inv.getReservedQuantity())
						.status(ReservationStatus.RESERVED).createdAt(Instant.now())
						.build();
				reservationRepo.save(reservation);
			}
		}
	}

	@Transactional
	public void confirmAllocate(String orderId) {
		List<StockReservation> reservedStock = reservationRepo.findByOrderIdAndStatus(orderId,
				ReservationStatus.RESERVED);
		for (StockReservation r : reservedStock) {
			try {

				Product product = r.getProduct();
				var invs = inventoryRepo.lockAllByProductOrderByWarehouse(product);
				invs.stream().filter(inv-> inv.getWarehouseCode().equals(r.getWarehouseCode())).findFirst()
				.ifPresent(result->{
					result.setReservedQuantity(result.getReservedQuantity() - r.getQuantity());
					result.setAllocatedQuantity(result.getAllocatedQuantity() + r.getQuantity());
				});
				
				r.setStatus(ReservationStatus.ALLOCATED);
			} catch (NullPointerException e) {
				// TODO: handle exception
				log.error(e.getMessage());
			}
		}
		asyncAudit(orderId, "ALLOCATED_OK");
	}

	@Transactional
	public void cancelReservation(String orderId) {
//		List<StockReservation> reservedStock = reservationRepo.findByOrderIdAndStatus(orderId, ReservationStatus.RESERVED);
//		for(StockReservation sr : reservedStock) {
//			Product product = sr.getProduct();
//			var invs = inventoryRepo.lockAllByProductOrderByWarehouse(product);
//			var invOpt = invs.stream()
//					.filter(i-> i.getWarehouseCode().equals(sr.getWarehouseCode())).findFirst();
//			if(invOpt.isEmpty()) continue;
//			
//			Inventory inv = invOpt.get();
//			inv.setReservedQuantity(inv.getReservedQuantity()  + sr.getQuantity());
//			sr.setStatus(ReservationStatus.CANCELLED);
//			reservationRepo.save(sr);
//		}

		try {


			var reservedStock = reservationRepo.findByOrderIdAndStatus(orderId, ReservationStatus.RESERVED);
			for (StockReservation r : reservedStock) {
				Product product = r.getProduct();
				var invs = inventoryRepo.lockAllByProductOrderByWarehouse(product);
				invs.stream().filter(inv-> inv.getWarehouseCode().equals(r.getWarehouseCode())).findFirst()
				.ifPresent(result-> result.setReservedQuantity(result.getReservedQuantity() + r.getQuantity()));
				
				r.setStatus(ReservationStatus.CANCELLED);
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		asyncAudit(orderId, "CANCELLED_OK");
	}

	@Async("auditExecutor")
	public Future<Void> asyncAudit(String orderId, String action) {
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		log.info("[AUDIT] orderId = {}, action = {}", orderId, action);
		return new AsyncResult<>(null);
	}
}
