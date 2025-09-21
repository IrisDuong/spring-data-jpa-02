package com.kps.jpa.company.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kps.jpa.company.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query("""
			SELECT NEW com.kps.jpa.company.dto.AddressResponseDTO(a.id, a.address) FROM Address a
			WHERE a.employee.id IN :ids
			""")
	List<Address> getListAddressByEmpIds(@Param("ids") Set<Integer> ids);
	List<Address> findByEmployeeIdIn(List<Integer> ids);
}
