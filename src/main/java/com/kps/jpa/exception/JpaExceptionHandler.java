package com.kps.jpa.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class JpaExceptionHandler {

	@ExceptionHandler(value = CustomSQLException.class)
	public ResponseEntity<ExceptionResponse> customSqlException(Exception e){
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionResponse res = ExceptionResponse.builder()
				.statusCode(httpStatus.value())
				.message(e.getMessage())
				.errorDate(LocalDateTime.now())
				.build();
		return new ResponseEntity<ExceptionResponse>(res, httpStatus);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> validationMethodArgs(Exception ex){
		Map<String, String> errors = new HashMap<String, String>();
		
		if(ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
			e.getBindingResult().getAllErrors()
			.forEach(error-> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
		}else if( ex instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException e  = (HttpMessageNotReadableException) ex;
			if(e.getCause() instanceof InvalidFormatException) {
				InvalidFormatException formatException = (InvalidFormatException) e.getCause();
				if(formatException.getTargetType() == Date.class) {
					errors.put(formatException.getPath().get(0).getFieldName(), formatException.getMessage());
				}
			}
		}

		ExceptionResponse res = ExceptionResponse.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.multiMessages(errors)
				.errorDate(LocalDateTime.now())
				.build();
		return ResponseEntity.badRequest().body(res);
	}
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> validationException(ConstraintViolationException e){
		
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionResponse res = ExceptionResponse.builder()
				.statusCode(httpStatus.value())
				.message(e.getMessage())
				.errorDate(LocalDateTime.now())
				.build();
		return new ResponseEntity<ExceptionResponse>(res, httpStatus);
	}
}
