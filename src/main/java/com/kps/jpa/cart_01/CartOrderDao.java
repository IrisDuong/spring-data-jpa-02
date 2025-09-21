package com.kps.jpa.cart_01;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartOrderDao extends JpaRepository<CartOrder, Integer>{
	
	@Query("SELECT c FROM CartOrder c JOIN FETCH c.cartOrderItems ci JOIN FETCH ci.product")
	public List<CartOrder> findAllCartOrders01();
	
	@Query("SELECT c FROM CartOrder c JOIN FETCH c.cartOrderItems ci JOIN FETCH ci.product")
	public List<CartOrder> findAllCartOrders01(Pageable pageable);
	

	@EntityGraph(attributePaths = {"cartOrderItems","cartOrderItems.product"})
	@Query("SELECT c FROM CartOrder c")
	public List<CartOrder> findAllCartOrders02();

	@EntityGraph(attributePaths = {"cartOrderItems","cartOrderItems.product"})
	@Query("SELECT c FROM CartOrder c")
	public List<CartOrder> findAllCartOrders02(Pageable pageable);
}
