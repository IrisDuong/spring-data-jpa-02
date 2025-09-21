package com.kps.jpa.company.service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kps.jpa.company.dao.MeetingRepository;
import com.kps.jpa.company.dto.MeetingRequestDTO;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Meeting;

@Service
public class MeetingService {
	@Autowired
	MeetingRepository meetingRepository;

	@Transactional
	public void bookMeeting(MeetingRequestDTO dto) {
		dto.setMeetingTime(new Date());
		Meeting meeting = Meeting.builder()
				.title(dto.getTitle())
				.meetingRoom(dto.getMeetingRoom())
				.meetingTime(dto.getMeetingTime())
				.build();
		
		Set<Employee> participants = dto.getParticipants().stream()
				.map(p->{
					Employee emp = new Employee();
					emp.setId(p.getId());
					return emp;
				}).collect(Collectors.toSet());
		meeting.setParticipants(participants);
		meetingRepository.save(meeting);
	}
}
