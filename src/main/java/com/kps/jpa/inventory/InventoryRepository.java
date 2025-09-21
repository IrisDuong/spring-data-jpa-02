package com.kps.jpa.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT i FROM Inventory i WHERE i.product = :product order by i.warehouseCode asc")
	List<Inventory> lockAllByProductOrderByWarehouse(@Param("product") Product product);
}
