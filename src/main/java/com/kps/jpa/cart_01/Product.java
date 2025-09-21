package com.kps.jpa.cart_01;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Salary;
import com.kps.jpa.util.BaseAudit;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "cart01_product")
@EntityListeners(AuditingEntityListener.class)
public class Product  extends BaseAudit<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String productName;
	private int inStocks;
	private float price;
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	private String description;
}
