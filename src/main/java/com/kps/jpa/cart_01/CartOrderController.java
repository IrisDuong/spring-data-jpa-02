package com.kps.jpa.cart_01;

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

@RestController
@RequestMapping("/api/cartOrder")
public class CartOrderController {

	@Autowired
	CartOrderService cartOrderService;
	
	@PostMapping("/doPayment")
	public ResponseEntity<?> createMultiProducts(@RequestBody CartOrderRequestDTO dto){
		try {
			cartOrderService.doPayment(dto);
			return new ResponseEntity<>("Create multi products successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/getCartOrderById/{cartId}")
	public ResponseEntity<?> getCartOrderById(@PathVariable("cartId") int id){
		try {
			CartOrderResponseDTO result = cartOrderService.getCartOrderById(id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/getAllCartOrders")
	public ResponseEntity<?> getAllCartOrders(){
		try {
			List<CartOrderResponseDTO> result = cartOrderService.getAllCartOrders();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
}
