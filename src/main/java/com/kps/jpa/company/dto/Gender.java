package com.kps.jpa.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {

	@JsonProperty("male")
	MALE,
	
	@JsonProperty("female")
	FEMALE
}
