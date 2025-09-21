package com.kps.jpa.banking;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, PagingAndSortingRepository<BankAccount, Long>{

	@Modifying
	@Query(value = "insert into BankAccount(balance,address,name,phoneNo) values(:balance,:address,:name,:phoneNo)")
	int customSaveBanking(@Param("balance") BigDecimal balance,@Param("address") String address,@Param("name") String name,@Param("phoneNo") String phoneNo);
}
