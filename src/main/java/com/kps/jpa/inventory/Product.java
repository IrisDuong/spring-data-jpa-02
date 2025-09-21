package com.kps.jpa.inventory;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "inv01_product")
@Table(name = "inv01_product", indexes = @Index(name = "idx_products_sku", columnList = "sku", unique = true))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String sku;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal price;
}
