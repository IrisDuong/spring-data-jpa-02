package com.kps.jpa.inheritance.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
//@Table(name = "comp01_contract_sale")
@DiscriminatorValue("sale")
@Data
public class SaleContract extends Contract {
	private String productName;
	private Integer quantity;
	private Double unitPrice;
}
