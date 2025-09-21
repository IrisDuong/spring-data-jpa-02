package com.kps.jpa.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.kps.jpa.company.dao.AddressRepository;
import com.kps.jpa.company.dao.EmployeeRepositorySpec_01;
import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.dto.EmployeeResponseDTO;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.exception.CustomSQLException;

import jakarta.persistence.Cacheable;
import jakarta.persistence.LockModeType;

@Service
public class EmployeeServiceSpec_01 {

	@Autowired
	EmployeeRepositorySpec_01 employeeRepositorySpec_01;
	
	@Autowired
	private AddressRepository addressRepository;

	
	/**
	 * Search with specification
	 * @param pageNo
	 * @param pageSize
	 * @param empDto
	 * @param sortField
	 * @return
	 */
	public  Map<String, Object>  getListEmployeeBySpec01(int pageNo, int pageSize, EmployeeRequestDTO dto, String... sortField) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<Sort.Order> sortOrders = new ArrayList<Sort.Order>();
		sortOrders = Stream.of(sortField).map(field->{
			Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
			Matcher matcher = pattern.matcher(field);
			if(matcher.find()) {
				return matcher.group(3).equalsIgnoreCase("asc")
						? new Sort.Order(Direction.ASC, matcher.group(1))
						: new Sort.Order(Direction.DESC, matcher.group(1)); 
			}
			throw new CustomSQLException("Order by is invalid");
			
		}).toList();
		
		try {
			Specification<Employee> empSpec = EmployeeSpecification_01.builder()
					.salaryFrom(dto.getSalaryFrom())
					.salaryTo(dto.getSalaryTo())
					.dob(dto.getDobFrom())
					.searchTerm(dto.getKeySearchTerm())
					.address(dto.getAddressSearchTerm())
					.build();
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrders));
			Page<Employee> page = employeeRepositorySpec_01.findAll(empSpec, pageable);
			
			List<EmployeeResponseDTO> result = page.stream()
					.map(emp->{
//						Set<AddressDTO> listAddresses = emp.getAddresses()
//								.stream().map(ad->{
//									return AddressDTO.builder()
//											.id(ad.getId())
//											.address(ad.getAddress())
//											.build();
//								}).collect(Collectors.toSet());
						return EmployeeResponseDTO.builder()
								.empName(emp.getEmpName())
								.phoneNo(emp.getPhoneNo())
								.email(emp.getEmail())
								.gender(emp.getGender())
								.dob(emp.getDob())
//								.addresses(listAddresses)
								.build();
			}).toList();
			
			data.put("result",result);
			data.put("totalElements", page.getTotalElements());
			data.put("totalPages",page.getTotalPages() );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return data;
	}
	public  Map<String, Object>  getListEmployeeBySpec02(int pageNo, int pageSize, EmployeeRequestDTO dto, String... sortField) {
		Map<String, Object> data = new HashMap<String, Object>();
		Specification<Employee> finalSpec = (root,query,cb)->{
			return cb.and(
					cb.like(root.get("empName"), "%"+dto.getEmpName()+"%")
				   ,cb.like(root.get("phoneNo"), "%"+dto.getPhoneNo()+"%")
				   ,cb.like(root.get("email"), "%"+dto.getEmail()+"%")
				   ,cb.equal(root.get("gender"), dto.getGender())
				   ,cb.between(root.get("dob"), dto.getDobFrom(), dto.getDobTo())
			);
					
		};
		List<Employee> employees = employeeRepositorySpec_01.findAll(finalSpec);
		List<EmployeeResponseDTO> result = employees.stream()
				.map(emp->{
					return EmployeeResponseDTO.builder()
							.empName(emp.getEmpName())
							.phoneNo(emp.getPhoneNo())
							.email(emp.getEmail())
							.gender(emp.getGender())
							.dob(emp.getDob())
//							.addresses(listAddresses)
							.build();
				}).toList();
		data.put("result",result);
		return data;
	}
}
