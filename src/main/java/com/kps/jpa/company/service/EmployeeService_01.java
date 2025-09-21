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
import com.kps.jpa.company.dao.EmployeeRepository_01;
import com.kps.jpa.company.dto.AddressResponseDTO;
import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.dto.EmployeeResponseDTO;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.exception.CustomSQLException;

import jakarta.persistence.Cacheable;
import jakarta.persistence.LockModeType;

@Service
public class EmployeeService_01 {

	@Autowired
	private EmployeeRepository_01 employeeRepository_01;
	
	@Autowired
	private AddressRepository addressRepository;
	
	/**
	 * Search list employee with paging and sorting
	 * @param pageStartIndex
	 * @param pageSize
	 * @param sortByProps
	 * @param sortType
	 * @return
	 */
	public List<EmployeeResponseDTO> getListEmployeePagingAndSort(int pageStartIndex,int pageSize, String sortByProps, String sortType) {
//		PageRequest.of
		Pageable pageable = PageRequest.of(pageStartIndex, pageSize, Sort.by(sortType.equals("asc") ? Direction.ASC : Direction.DESC, sortByProps));
//		Pageable pageable = PageRequest.of(pageStartIndex, pageSize, Sort.by(sortBy).ascending());
		
		List<EmployeeResponseDTO> result =  employeeRepository_01.findAll(pageable).stream()
				.map(this::mapToEmployeeDto).toList();
		return result;
	}
	
	
	public Map<String, Object> getListEmployeePagingAndMultiColumnsSorting(int pageNo, int pageSize, String... sort){
		Map<String, Object> data = new HashMap<String, Object>();
		List<Sort.Order> sortings = new ArrayList<Sort.Order>();
		sortings = Stream.of(sort).map(s->{
			Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
			Matcher matcher = pattern.matcher(s);
			if(matcher.find()) {
				return matcher.group(3).equalsIgnoreCase("asc")
						? new Sort.Order(Direction.ASC, matcher.group(1))
						: new Sort.Order(Direction.DESC, matcher.group(1));
			}
			return new Sort.Order(Direction.ASC, matcher.group(1));
		}).toList();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Employee> page = employeeRepository_01.findAll(pageable);
		List<EmployeeResponseDTO> result = page.stream().map(emp->{
			return EmployeeResponseDTO.builder()
					.empName(emp.getEmpName())
					.phoneNo(emp.getPhoneNo())
					.email(emp.getEmail())
					.gender(emp.getGender())
					.dob(emp.getDob())
					.build();
		}).toList();
		data.put("listEmployess", result);
		data.put("totalPages", page.getTotalPages());
		data.put("totalElements", page.getTotalElements());
		
		return data;
	}
	
	@Transactional(rollbackFor = CustomSQLException.class)
	public void deleteEmployeeById(int id) {
		employeeRepository_01.deleteById(id);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = CustomSQLException.class)
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO dto) {
		Employee emp = mapToEmployee(dto);
		Set<Address> addresses = dto.getAddresses().stream()
				.map(address-> new Address(address.getAddress(),emp)).collect(Collectors.toSet());
		emp.setAddresses(addresses);
		return mapToEmployeeDto(employeeRepository_01.save(emp));
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Employee> getAllEmps() {
		return employeeRepository_01.findAll();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = CustomSQLException.class)
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void updateEmployee(EmployeeRequestDTO dto) {
		Employee _emp = getEmployeeById(dto.getId());
		_emp.setEmpName(dto.getEmpName());
		_emp.setDob(dto.getDobFrom());
		_emp.setPhoneNo(dto.getPhoneNo());
		_emp.setEmail(dto.getEmail());
		_emp.setGender(dto.getGender());
		_emp.setId(dto.getId());
		employeeRepository_01.save(_emp);
//		employeeRepository_01.updateEmployeeQuery(_emp);
//		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public EmployeeResponseDTO getEmployee(Integer id) {
		Employee _emp = getEmployeeById(id);
		return mapToEmployeeDto(_emp);
	}
	
	public List<AddressResponseDTO> getListAddressByEmpIds(List<Integer> ids){
		List<Address> addresses = addressRepository.findByEmployeeIdIn(ids);
//		List<Address> addresses = addressRepository.getListAddressByEmpIds(ids);
		List<AddressResponseDTO> result = addresses.stream().map(e-> {
			return new AddressResponseDTO(e.getId(),e.getAddress());
					
		}).toList();
		return result;
	}
	public Employee getEmployeeById(Integer id) {
		Employee _emp = employeeRepository_01.findById(id)
				.orElseThrow(()-> new CustomSQLException(String.format("Not found employee with id %d", id)));
		return _emp;
	}
	public Employee mapToEmployee(EmployeeRequestDTO dto) {
		Employee mappedEmp  = Employee.builder()
				.empName(dto.getEmpName())
				.phoneNo(dto.getPhoneNo())
				.email(dto.getEmail())
				.gender(dto.getGender())
				.dob(dto.getDobFrom())
				.build();
		return mappedEmp;
	}
	
	public EmployeeResponseDTO mapToEmployeeDto(Employee emp) {
		EmployeeResponseDTO mappedEmp  = EmployeeResponseDTO.builder()
				.empName(emp.getEmpName())
				.phoneNo(emp.getPhoneNo())
				.email(emp.getEmail())
				.gender(emp.getGender())
				.dob(emp.getDob())
				.build();
		if(emp.getAddresses() != null) {
			Set<AddressResponseDTO> listAddressDto =  emp.getAddresses().stream().map(address-> new AddressResponseDTO(address.getId(),address.getAddress())).collect(Collectors.toSet());
			mappedEmp.setAddresses(listAddressDto);
		}
			
		return mappedEmp;
	}
}
