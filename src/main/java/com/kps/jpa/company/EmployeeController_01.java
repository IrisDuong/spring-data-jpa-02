package com.kps.jpa.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kps.jpa.company.dto.AddressResponseDTO;
import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.dto.EmployeeResponseDTO;
import com.kps.jpa.company.service.EmployeeService_01;
import com.kps.jpa.exception.CustomSQLException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/employeeController_01")
@Validated // phai them cai nay thi validation cho path variable va request param hoat dong tren them so cua endpoint method
public class EmployeeController_01 {


	@Autowired
	EmployeeService_01 employeeService;
	
	@PostMapping("/saveMultiEmployees")
	public ResponseEntity<?> saveMultiEmployees(@RequestBody Map<String, Object> body) {
		try {
//			ObjectMapper mapper = new ObjectMapper();
//			EmployeeDTO emp = mapper.convertValue(body.get("emp"), EmployeeDTO.class);
//			Address address = mapper.convertValue(body.get("address"), Address.class);
//			employeeService.addEmployee(emp,address) ;
			return new ResponseEntity<>("save employee successfully", HttpStatus.CREATED);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomSQLException("addEmployee failed and transaction will be rollbacked for");
		}
	}

	@PostMapping("/saveEmployee")
	public ResponseEntity<?> saveEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
		try {

			EmployeeResponseDTO result = employeeService.addEmployee(dto);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("body", result);
			data.put("message", "save employee successfully");
			return new ResponseEntity<>(data, HttpStatus.CREATED);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("addEmployee failed and transaction will be rollbacked for");
		}
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequestDTO dto) {
		try {
			employeeService.updateEmployee(dto);
			return new ResponseEntity<>("update employee successfully", HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("update failed and transaction will be rollbacked for");
		}
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable @Min(1)  int id) {
		try {
			employeeService.deleteEmployeeById(id);
			return new ResponseEntity<>("deleteEmployee successfully", HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("deleteEmployee failed and transaction will be rollbacked for");
		}
	}

	@GetMapping("/getListAddressByEmpIds")
	public ResponseEntity<?> getListAddressByEmpIds(@RequestParam List<Integer> ids) {
		try {
			List<AddressResponseDTO> result = employeeService.getListAddressByEmpIds(ids);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("getListAddressByEmpIds failed and transaction will be rollbacked for");
		}
	}
	@GetMapping("/getListEmployeePagingAndSort")
	public ResponseEntity<?> getListEmployeePagingAndSort(@RequestParam int pageSize, @RequestParam int pageIndexStart,@RequestParam String sortByProp,@RequestParam  String sortType) {
		try {
			List<EmployeeResponseDTO> result = employeeService.getListEmployeePagingAndSort(pageIndexStart, pageSize, sortByProp, sortType);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("getListAddressByEmpIds failed and transaction will be rollbacked for");
		}
	}
	
	@GetMapping("/getListEmployeePagingAndMultiColumnsSorting")
	public ResponseEntity<?> getListEmployeePagingAndMultiColumnsSorting(
				@RequestParam(defaultValue  = "0", required = false) int pageNo,
				@RequestParam(defaultValue = "4", required = false) int pageSize,
				@RequestParam String... sortings
			){
		try {
			Map<String, Object> result = employeeService.getListEmployeePagingAndMultiColumnsSorting(pageNo, pageSize, sortings);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("getListAddressByEmpIds failed and transaction will be rollbacked for");
		}
	}
	
	@GetMapping("/testUpdateAndReadEmployee")
	public void testUpdateAndReadEmployee() throws InterruptedException {
		Integer id = 6;
//		Thread t1 = new Thread(()->{
////			employeeService.updateEmployee(id, new Employee("Nguyen Van A"));
//			employeeService.updateEmployee(id, new Employee("Nguyen Minh Khang"));
//			try {
//				TimeUnit.SECONDS.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		
//		Thread t2 = new Thread(()->{
//			Employee emp = employeeService.getEmployeeById(id);
//			System.out.println(".......... junit test ::  emp ...............");
//			System.out.println(emp.toString());	
//			try {
//				TimeUnit.SECONDS.sleep(15);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		
//		t1.start();
//		t2.start();
//		
//		t1.join();
//		t2.join();
		

//		employeeService.updateEmployee(id, new Employee("Nguyen Minh Khang"));
//		Employee emp = employeeService.getEmployeeById(id);
//		System.out.println(".......... junit test ::  emp ...............");
//		System.out.println(emp.toString());	
	}
}
