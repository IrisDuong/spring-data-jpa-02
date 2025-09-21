package com.kps.jpa.flight01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightTicketServiceImpl {

	@Autowired
	FlightTicketDao flightTicketDao;

	@Autowired
	FlightInfoDao flightInfoDao;

	@Transactional
	public void bookFlight(String customer, Long flightId) {
//		FlightInfo flightInfo = flightInfoDao.findBydIdWithPessimisticLock(flightId)
//				.orElseThrow(()-> new RuntimeException("No Flight Info!"));
		

		FlightInfo flightInfo = flightInfoDao.findById(flightId)
				.orElseThrow(()-> new RuntimeException("No Flight Info!"));
		
		try {
			if(flightInfo.getAvailableSeats() > 0) {
				FlightTicket flightTicket = FlightTicket.builder()
						.customerName(customer)
						.flightNo(flightInfo.getFlightNo())
						.build();
				flightTicketDao.save(flightTicket);
				
				flightInfo.setAvailableSeats(flightInfo.getAvailableSeats() - 1);
				flightInfoDao.save(flightInfo);
				TimeUnit.SECONDS.sleep(3);
				
				log.info("Book Flight successfully !");
			}else {
				throw new RuntimeException("No available seat for this flight");
			}
//			log.error("........................No available seat for this flight");
//			return;
		} catch (OptimisticLockingFailureException e) {
			// TODO: handle exception
			log.error("........................[OptimisticLockingFailureException] = "+e.getMessage());
		} catch (InterruptedException e) {
			// TODO: handle exception
			Thread.interrupted();
			log.error("........................[InterruptedException] = "+e.getMessage());
		}
		
	}
}
