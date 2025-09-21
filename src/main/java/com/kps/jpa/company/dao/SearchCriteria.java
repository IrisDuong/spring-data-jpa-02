package com.kps.jpa.company.dao;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCriteria {

	private String fieldName;
	private Object value;
	private String operation;
}
