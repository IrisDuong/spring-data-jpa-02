package com.kps.jpa.cart_01;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Salary;
import com.kps.jpa.util.BaseAudit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"cartOrderItems"})
@Builder
@Entity
@Table(name = "cart01_cart_order")
@EntityListeners(AuditingEntityListener.class)
@BatchSize(size = 20)
public class CartOrder  extends BaseAudit<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int totalAmount;
	private BigDecimal totalPayment;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchargingDate;
	
	@OneToMany(mappedBy = "cartOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CartOrderItem> cartOrderItems;
}
