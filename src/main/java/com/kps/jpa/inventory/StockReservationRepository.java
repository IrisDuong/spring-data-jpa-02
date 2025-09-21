package com.kps.jpa.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {

	List<StockReservation> findByOrderIdAndStatus(String orderId, ReservationStatus status);
	
	@Query("SELECT sr FROM StockReservation sr WHERE sr.orderId = :orderId AND sr.status = :status")
	List<StockReservation> findByOrderIdAndStatus02(@Param("orderId") String orderId, @Param("") ReservationStatus status);
}
