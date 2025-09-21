package com.kps.jpa.inheritance.contract;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
//@Table(name = "comp01_contract_rental")
@DiscriminatorValue("rental")
@Data
public class RentalContract extends Contract{

	private String assetName;
	private String rentalPrice;
}
