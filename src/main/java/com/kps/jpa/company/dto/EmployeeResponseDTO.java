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
import jakarta.validation.constraints.Pattern;
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
public class EmployeeResponseDTO implements Serializable{

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
	private Date dob;
	
	@NotEmpty(message = "Roles is at least one role")
	List<String> roles;

	private Set<AddressResponseDTO> addresses = new HashSet<AddressResponseDTO>();
	private Set<SalaryResponseDTO> salaries = new HashSet<SalaryResponseDTO>();
	public EmployeeResponseDTO(int id,String empName,String phoneNo,String email,Gender gender, Date dob) {
		super();
		this.id = id;
		this.empName = empName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
	}
	
	
}
