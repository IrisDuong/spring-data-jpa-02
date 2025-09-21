package com.kps.jpa.company.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MeetingRequestDTO {

	private int id;
	private String title;
	private String meetingRoom;
	private Date meetingTime;
	private Set<EmployeeRequestDTO> participants = new HashSet<EmployeeRequestDTO>();
}
