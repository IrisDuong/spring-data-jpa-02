package com.kps.jpa.flight01;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface FlightInfoDao extends JpaRepository<FlightInfo, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT fi FROM FlightInfo fi WHERE fi.id = :id")
	Optional<FlightInfo> findBydIdWithPessimisticLock(@Param("id") Long id);
}
