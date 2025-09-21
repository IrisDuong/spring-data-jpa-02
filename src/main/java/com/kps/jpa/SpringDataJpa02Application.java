package com.kps.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kps.jpa.inheritance.contract.ContractController;
import com.kps.jpa.inventory.AllocationService;
import com.kps.jpa.inventory.Inventory;
import com.kps.jpa.inventory.InventoryRepository;
import com.kps.jpa.inventory.OrderItem;
import com.kps.jpa.inventory.Product;
import com.kps.jpa.inventory.ProductRepository;
import com.kps.jpa.inventory.StockReservationRepository;
import com.kps.jpa.util.AuditorAwareImpl;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class SpringDataJpa02Application implements CommandLineRunner{
	@Autowired
	ProductRepository productRepo;

	@Autowired
	InventoryRepository inventoryRepo;

	@Autowired
	AllocationService allocationService;
	

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpa02Application.class, args);
	}

	@Bean
	AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}
	
	
	
//	public void testFlightBooking() {
//		FlightTicket ticket01 = FlightTicket.builder()
//				.flightNumber("VN300")
//				.availableSeats(1)
//				.build();
//		flightTicketRepository.save(ticket01);
//		
//		Passenger passengerManh = Passenger.builder()
//				.passengerName("Nguyen Khac Manh")
//				.build();
//		Passenger passengerBang = Passenger.builder()
//				.passengerName("Tran Ngoc Chau Bang")
//				.build();
//		passengerRepository.save(passengerBang);
//		passengerRepository.save(passengerManh);
//		
//		ExecutorService executorService = Executors.newFixedThreadPool(2);
//		executorService.submit(()->{
//			flightBookingService.bookFlight(ticket01.getId(), passengerBang.getId());
//		});
//		
//		executorService.submit(()-> flightBookingService.bookFlight(ticket01.getId(), passengerManh.getId()));
//		executorService.shutdown();
//	}
	public void demoAllocation() throws InterruptedException, ExecutionException {
		// innit dummy data
		if(productRepo.count() == 0) {
			Product product = Product.builder()
					.sku("SKU-IPHONE-15-PRO-128-GRAPHITE")
					.name("iPhone 15 Pro 128GB Graphite")
					.price(new BigDecimal("999.00"))
					.build();
			productRepo.save(product);
			
			inventoryRepo.save(
					Inventory.builder()
					.product(product)
					.warehouseCode("HCM-WH-01")
					.totalQuantity(10L)
					.reservedQuantity(0L)
					.allocatedQuantity(0L)
					.build()
			);

			inventoryRepo.save(
					Inventory.builder()
					.product(product)
					.warehouseCode("HN-WH-02")
					.totalQuantity(20l)
					.reservedQuantity(0L)
					.allocatedQuantity(0L)
					.build()
			);
		}
		
		// Mô phỏng 40 yêu cầu đặt hàng đồng thời, mỗi đơn muốn 5 cái
		
		ExecutorService pool = Executors.newFixedThreadPool(16);
		List<Callable<String>> tasks = IntStream.range(0, 2)
				.mapToObj(i->(Callable<String>) ()->{
						String orderId = UUID.randomUUID().toString();
						try {
							allocationService.allocate(orderId, List.of(
									new OrderItem("SKU-IPHONE-15-PRO-128-GRAPHITE", 25)
							));
							return "OK : "+orderId;
						} catch (Exception ex) {
							// TODO: handle exception
							return "FAIL:" + orderId + " => " + ex.getMessage();
						}
				}).toList();
		List<Future<String>> futures = pool.invokeAll(tasks);
		pool.shutdown();
		for(Future<String> f : futures) {
			System.out.println(f.get());
		}
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		testFlightBooking();
		demoAllocation();
	}
}
