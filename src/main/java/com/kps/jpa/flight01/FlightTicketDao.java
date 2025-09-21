package com.kps.jpa.flight01;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightTicketDao extends JpaRepository<FlightTicket, Long> {

}
