package com.kps.jpa.cart_01;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductDao extends JpaRepository<Product, Integer>{

}
