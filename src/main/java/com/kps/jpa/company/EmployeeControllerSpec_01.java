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
import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.service.EmployeeServiceSpec_01;
import com.kps.jpa.exception.CustomSQLException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/employeeControllerSpec")
@Validated // phai them cai nay thi validation cho path variable va request param hoat dong tren them so cua endpoint method
public class EmployeeControllerSpec_01 {


	@Autowired
	EmployeeServiceSpec_01 employeeServiceSpec_01;
	
	@GetMapping("/getListEmployeeBySpec01")
	public ResponseEntity<?> getListEmployeeBySpec01(
				@RequestParam(defaultValue  = "0", required = false) int pageNo,
				@RequestParam(defaultValue = "4", required = false) int pageSize,
				@RequestBody EmployeeRequestDTO request,
				@RequestParam String... sortings
			){
		try {
			Map<String, Object> result = employeeServiceSpec_01.getListEmployeeBySpec01(pageNo, pageSize, request, sortings);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("getListAddressByEmpIds failed and transaction will be rollbacked for");
		}
	}

	@GetMapping("/getListEmployeeBySpec02")
	public ResponseEntity<?> getListEmployeeBySpec02(
				@RequestParam(defaultValue  = "0", required = false) int pageNo,
				@RequestParam(defaultValue = "4", required = false) int pageSize,
				@RequestBody EmployeeRequestDTO request,
				@RequestParam String... sortings
			){
		try {
			Map<String, Object> result = employeeServiceSpec_01.getListEmployeeBySpec02(pageNo, pageSize, request, sortings);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomSQLException("getListAddressByEmpIds failed and transaction will be rollbacked for");
		}
	}
	

}
