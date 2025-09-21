package com.kps.jpa.company.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.util.BaseAudit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comp01_meeting_01")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Meeting extends BaseAudit<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String meetingRoom;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingTime;
	
	@ManyToMany
	@JoinTable(name = "comp01_emp_meeting_01", joinColumns = @JoinColumn(name ="meeting_id"), inverseJoinColumns = @JoinColumn(name ="emp_id"))
	private Set<Employee> participants = new HashSet<Employee>();

	public Meeting(String title, String meetingRoom, Date meetingTime, Set<Employee> participants) {
		super();
		this.title = title;
		this.meetingRoom = meetingRoom;
		this.meetingTime = meetingTime;
		this.participants = participants;
	}
	
	public Meeting(String title, String meetingRoom, Date meetingTime) {
		super();
		this.title = title;
		this.meetingRoom = meetingRoom;
		this.meetingTime = meetingTime;
	}
	
	
	
}
