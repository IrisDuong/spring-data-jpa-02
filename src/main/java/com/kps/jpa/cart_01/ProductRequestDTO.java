package com.kps.jpa.cart_01;

import java.util.Date;


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
public class ProductRequestDTO{
	private int id;
	private String productName;
	private int inStocks;
	private float price;
	private Date entryDate;
	private String description;
}
