package com.kps.jpa.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

	@Autowired
	AllocationService allocationService;
	
	@PostMapping("/reserve")
	public ResponseEntity<AllocateResponse> reserve(@RequestBody AllocateRequest req){
		allocationService.allocate(req.orderId(), req.items());
		return ResponseEntity.ok(new AllocateResponse(req.orderId(), "RESERVED"));
	}
	
	@PostMapping("/{orderId}/confirm")
	public ResponseEntity<AllocateResponse> confirm(@PathVariable String orderId) {
	allocationService.confirmAllocate(orderId);
	return ResponseEntity.ok(new AllocateResponse(orderId, "ALLOCATED"));
	}


	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<AllocateResponse> cancel(@PathVariable String orderId) {
	allocationService.cancelReservation(orderId);
	return ResponseEntity.ok(new AllocateResponse(orderId, "CANCELLED"));
	}
}
