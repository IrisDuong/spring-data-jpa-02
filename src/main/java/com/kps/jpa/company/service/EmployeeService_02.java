package com.kps.jpa.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.kps.jpa.company.dao.EmployeeRepository_02;
import com.kps.jpa.company.dto.AddressResponseDTO;
import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.dto.EmployeeResponseDTO;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.exception.CustomSQLException;

import jakarta.persistence.Cacheable;
import jakarta.persistence.LockModeType;

@Service
public class EmployeeService_02 {

	@Autowired
	EmployeeRepository_02 employeeRepository_02;

	public Map<String, Object> search01(int pageNo, int pageSize, EmployeeRequestDTO search, String... sortBy) {

		Map<String, Object> data = employeeRepository_02.search01(pageNo, pageSize, search, sortBy);
		List<Employee> employees = (List<Employee>) data.get("result");
		List<EmployeeResponseDTO> result = employees.stream()
				.map(emp->{
					Set<AddressResponseDTO> addressDTOs = emp.getAddresses().stream().map(a-> new AddressResponseDTO(a.getId(), a.getAddress())).collect(Collectors.toSet());
				
					return EmployeeResponseDTO.builder()
							.id(emp.getId())
							.empName(emp.getEmpName())
							.phoneNo(emp.getPhoneNo())
							.email(emp.getEmail())
							.gender(emp.getGender())
							.dob(emp.getDob())
//							.addresses(addressDTOs)
							.build();
				}).toList();
		data.put("result", result);
		return data;
	}

	public Map<String, Object> search02(int pageNo, int pageSize, String[] search, String... sortBy) {

		Map<String, Object> data = employeeRepository_02.search02(pageNo, pageSize, search, sortBy);
		List<Employee> employees = (List<Employee>) data.get("result");
		List<EmployeeResponseDTO> result = employees.stream().map(emp -> {
			return EmployeeResponseDTO.builder()
					.id(emp.getId()).empName(emp.getEmpName()).phoneNo(emp.getPhoneNo())
					.email(emp.getEmail()).gender(emp.getGender()).dob(emp.getDob())
					.build();
		}).toList();
		data.put("result", result);
		return data;
	}
}
