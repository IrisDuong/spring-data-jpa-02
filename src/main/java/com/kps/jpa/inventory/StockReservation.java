package com.kps.jpa.inventory;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inv01_stock_reservation", indexes = @Index(name = "idx_resv_order", columnList = "orderId"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Product product;

	@Column(nullable = false)
	private String warehouseCode;

	@Column(nullable = false)
	private Long quantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private ReservationStatus status;

	@Version
	private Long version;

	@Column(nullable = false)
	private Instant createdAt = Instant.now();
}
