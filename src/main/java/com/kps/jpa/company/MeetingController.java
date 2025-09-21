package com.kps.jpa.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kps.jpa.company.dto.MeetingRequestDTO;
import com.kps.jpa.company.service.MeetingService;

@RestController
@RequestMapping("/meetingController")
public class MeetingController {

	@Autowired
	MeetingService meetingService;
	
	@PostMapping("/bookMeeting")
	public ResponseEntity<?> bookMeeting(@RequestBody MeetingRequestDTO request){
		try {
			meetingService.bookMeeting(request);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
