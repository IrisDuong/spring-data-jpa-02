package com.kps.jpa.company.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kps.jpa.util.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class EmployeeRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	
	@NotEmpty(message = "Employee's name is required")
	private String empName;
	
	@NotNull(message = "Phone number is required")
//	@Pattern(regexp = "^\\d{10}$",message = "Phone number is invalid format")
	@PhoneNumber
	private String phoneNo;

	@NotBlank(message = "Email is required")
	@Email(message = "Email is invalid format")
	private String email;
	
	private Gender gender;
	
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dobFrom;
	
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dobTo;
	
	
	
	@NotEmpty(message = "Roles is at least one role")
	List<String> roles;
	private Long salaryTo;
	private Long salaryFrom;
	private String addressSearchTerm;
	private String keySearchTerm;


	private Set<AddressRequestDTO> addresses = new HashSet<AddressRequestDTO>();
	
	public EmployeeRequestDTO(int id,String empName,String phoneNo,String email,Gender gender, Date dobFrom, Date dobTo) {
		super();
		this.id = id;
		this.empName = empName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.gender = gender;
		this.dobFrom = dobFrom;
		this.dobTo = dobTo;
	}

	public EmployeeRequestDTO(int id,String empName,String phoneNo,String email,Gender gender, Date dobFrom, Date dobTo, Long salaryTo, Long salaryFrom) {
		super();
		this.id = id;
		this.empName = empName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.gender = gender;
		this.dobFrom = dobFrom;
		this.dobTo = dobTo;
		this.salaryTo = salaryTo;
		this.salaryFrom = salaryFrom;
	}
}
