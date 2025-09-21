package com.kps.jpa.cart_01;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@PostMapping("/createMultiProducts")
	public ResponseEntity<?> createMultiProducts(@RequestBody List<ProductRequestDTO> dtos){
		try {
			productService.createMultiProducts(dtos);
			return new ResponseEntity<>("Create multi products successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping("/createSingleProduct")
	public ResponseEntity<?> createSingleProduct(@RequestBody ProductRequestDTO dto){
		try {
			productService.createSingleProduct(dto);
			return new ResponseEntity<>("Create single product successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	


	@PostMapping("/getAllProducts")
	public ResponseEntity<?> getAllProducts(){
		try {
			List<ProductResponseDTO> result = productService.getAllProducts();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
