package com.kps.jpa.company.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "comp01_address_01")
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String address;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id", name = "emp_id")
	private Employee employee;
	
	public Address(String address,Employee employee) {
		super();
		this.address = address;
		this.employee = employee;
	}

	public Address(Long id, String address) {
		super();
		this.id = id;
		this.address = address;
	}
	
}
