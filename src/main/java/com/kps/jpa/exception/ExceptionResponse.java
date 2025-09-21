package com.kps.jpa.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

	private int statusCode;
	private String message;
	private Map<String, String> multiMessages;
	private LocalDateTime errorDate;
	
	
}
