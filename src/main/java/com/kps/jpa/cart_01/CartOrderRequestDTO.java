package com.kps.jpa.cart_01;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartOrderRequestDTO{
	private int id;
	private int totalAmount;
	private BigDecimal totalPayment;
	private Date purchargingDate;
	private Set<CartOrderItemRequestDTO> cartOrderItems;
}
