package com.kps.jpa.inventory;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inv01_inventory",
	indexes = @Index(name = "idx_inv_product_warehouse", columnList = "product_id,warehouse_code", unique = true)
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Product product;
	
	@Column(name = "warehouse_code", nullable = false, length = 32)
	private String warehouseCode;

	@Column(nullable = false)
	private Long totalQuantity;

	@Column(nullable = false)
	private Long reservedQuantity;
	
	@Column(nullable = false)
	private Long allocatedQuantity;
	
	@Version
	private Long version;

	public Inventory(Long id, Product product, String warehouseCode, Long totalQuantity, Long reservedQuantity,
			Long allocatedQuantity) {
		super();
		this.id = id;
		this.product = product;
		this.warehouseCode = warehouseCode;
		this.totalQuantity = totalQuantity;
		this.reservedQuantity = reservedQuantity;
		this.allocatedQuantity = allocatedQuantity;
	}
	
	@Transient
	public long available() {
		return totalQuantity - reservedQuantity - allocatedQuantity;
	}
	
	
}
