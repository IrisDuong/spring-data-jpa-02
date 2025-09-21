package com.kps.jpa.cart_01;

import java.math.BigDecimal;
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
public class CartOrderItemResponseDTO{
	private int id;
	private int totalAmount;
	private BigDecimal totalPrice;
	private int productId;
	private String productName;
}
