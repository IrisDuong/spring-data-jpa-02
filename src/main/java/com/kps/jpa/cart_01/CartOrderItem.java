package com.kps.jpa.cart_01;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Salary;
import com.kps.jpa.util.BaseAudit;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "cart01_cart_order_item")
@EntityListeners(AuditingEntityListener.class)
public class CartOrderItem  extends BaseAudit<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int totalAmount;
	private BigDecimal totalPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_order_id")
	private CartOrder cartOrder;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
