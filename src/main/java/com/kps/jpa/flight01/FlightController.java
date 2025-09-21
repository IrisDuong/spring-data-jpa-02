package com.kps.jpa.flight01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/flight")
@Slf4j
public class FlightController {

	@Autowired
	FlightTicketServiceImpl flightTicketServiceImpl;
	
	@PostMapping("/bookFlight")
	public ResponseEntity<?> bookFlight(
			@RequestParam("customerName") String customerName,
			@RequestParam("flightId") Long flightId
	){
		try {
			log.info(".....................bookFlight with multi users");
			flightTicketServiceImpl.bookFlight(customerName, flightId);
			return ResponseEntity.ok("Book Flight successfully !");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(String.format(" %s booked flight failed !", customerName));
		}
	}
	
	@GetMapping("/bookFlight")
	public ResponseEntity<?> bookFlight(){
		try {
			log.info(".....................2 user Nguyen Van A va Le Thi C cung book 1 chuyen bay");
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			executorService.submit(()-> flightTicketServiceImpl.bookFlight("Nguyen Van A", 1l));
			executorService.submit(()-> flightTicketServiceImpl.bookFlight("Le Thi C", 1l));
			
			executorService.shutdown();
			return ResponseEntity.ok("Book Flight successfully !");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body("booked flight failed !");
		}
	}
}
