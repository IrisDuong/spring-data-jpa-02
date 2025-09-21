package com.kps.jpa.cart_01;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.jpa.exception.CustomSQLException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartOrderService {
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	CartOrderDao cartOrderDao;
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void doPayment(CartOrderRequestDTO cartDto) {
		
//		long totalAmount  = cartDto.getCartOrderItems().stream()
//				.mapToInt(item-> item.getTotalAmount()).sum();
		
//		AtomicReference<Float> totalPayment = new AtomicReference<Float>(0.0f);
		
		AtomicInteger totalAmount = new AtomicInteger(0);
		
		CartOrder cartOrder = CartOrder.builder()
				.purchargingDate(new Date())
				.build();
		
		Set<CartOrderItem> cartOrderItems = new HashSet<CartOrderItem>();
		cartOrderItems = cartDto.getCartOrderItems().stream().map(item->{
			Product product = productDao.findById(item.getProductId()).get();
			product.setInStocks(product.getInStocks() - item.getTotalAmount());
			productDao.save(product);
			
			BigDecimal totalPrice = new BigDecimal(product.getPrice() * item.getTotalAmount());
			
			//totalAmount
			 totalAmount.getAndAdd(item.getTotalAmount());
			 
			return CartOrderItem.builder()
					.totalAmount(item.getTotalAmount())
					.totalPrice(totalPrice)
					.product(product)
					.cartOrder(cartOrder)
					.build();
		}).collect(Collectors.toSet());
		cartOrder.setTotalAmount(totalAmount.intValue());
		cartOrder.setTotalPayment(new BigDecimal(cartOrderItems.stream().mapToDouble(item -> item.getTotalPrice().doubleValue()).sum()));
		cartOrder.setCartOrderItems(cartOrderItems);
		
		cartOrderDao.save(cartOrder);
	}
	
	public CartOrderResponseDTO getCartOrderById(int id) {
		CartOrder result = cartOrderDao.findById(id)
				.orElseThrow(()-> new CustomSQLException("No Cart Order with ID = "+id));
		Set<CartOrderItemResponseDTO> resItems = result.getCartOrderItems().stream()
				.map(item->{
//					Product product = productDao.findById(item.ge)
					return CartOrderItemResponseDTO.builder()
							.id(item.getId())
							.totalAmount(item.getTotalAmount())
							.totalPrice(item.getTotalPrice())
							.productId(item.getProduct().getId())
							.productName(item.getProduct().getProductName())
							.build();
					}).collect(Collectors.toSet());
		CartOrderResponseDTO response = CartOrderResponseDTO.builder()
				.id(result.getId())
				.totalAmount(result.getTotalAmount())
				.totalPayment(result.getTotalPayment())
				.purchargingDate(result.getPurchargingDate())
				.cartOrderItems(resItems)
				.build();
		return response;
	}
	
	public List<CartOrderResponseDTO> getAllCartOrders(){
		Pageable pageable = PageRequest.of(0, 3);
		
//		List<CartOrder> result = cartOrderDao.findAllCartOrders01();
		List<CartOrder> result = cartOrderDao.findAllCartOrders02();
		List<CartOrderResponseDTO> cartResponses = result.stream()
				.map(cart->{
					return CartOrderResponseDTO.builder()
							.id(cart.getId())
							.totalAmount(cart.getTotalAmount())
							.totalPayment(cart.getTotalPayment())
							.purchargingDate(cart.getPurchargingDate())
							.build();
				}).collect(Collectors.toList());
		return cartResponses;
	}
}
