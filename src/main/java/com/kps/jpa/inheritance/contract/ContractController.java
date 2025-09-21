package com.kps.jpa.inheritance.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/contractController")
@Tag(name = "Contract API", description = "These are contract related APIs")
public class ContractController {

	@Autowired
	ContractService contractService;
	
	@PostMapping("/new/sale")
	public ResponseEntity<?> saveContract(@RequestBody SaleContract contract){
		try {
			Contract newContract = contractService.saveContract(contract);
			return new ResponseEntity<>(newContract, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Create New Contract Failed !");
		}
	}

	@PostMapping("/new/rental")
	public ResponseEntity<?> saveContract(@RequestBody RentalContract contract){
		try {
			Contract newContract = contractService.saveContract(contract);
			return new ResponseEntity<>(newContract, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Create New Contract Failed !");
		}
	}
	@GetMapping("/detail/{contractId}")
	public ResponseEntity<?> getContractById(@PathVariable long contractId){

		try {
			Contract contract = contractService.getContractById(contractId)
					.orElseThrow(()-> new Exception("No Contract with ID "+contractId));
			return new ResponseEntity<>(contract, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllContracts(){

		try {
			List<Contract> allContracts = contractService.getAllContracts();
			if(allContracts != null && allContracts.size() > 0)
				return  ResponseEntity.ok(allContracts);
			throw new Exception("There are not any contracts") ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
