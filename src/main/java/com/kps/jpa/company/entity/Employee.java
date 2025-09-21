package com.kps.jpa.company.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.util.BaseAudit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "comp01_employee_01")
@Table(name = "comp01_employee_01")
@EntityListeners(AuditingEntityListener.class)
public class Employee extends BaseAudit<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String empName;
	private String phoneNo;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Address> addresses = new HashSet<Address>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "employee")
	private Set<Salary> salaries  = new HashSet<Salary>();
	
	@ManyToMany(mappedBy = "participants")
	private Set<Meeting> workMeetings = new HashSet<Meeting>();
	
	public void setListAddress(Address address) {
		if(address != null)
			if(addresses == null) {
				addresses = new HashSet<Address>();
			}
			addresses.add(address);
			address.setEmployee(this);
	}
	
	public void setSalaries(Salary salary) {
		if(salary != null) {
			if(salaries == null) {
				salaries = new HashSet<Salary>();
			}
			salaries.add(salary);
			salary.setEmployee(this);
		}
	}
}
