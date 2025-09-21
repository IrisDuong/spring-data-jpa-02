package com.kps.jpa.exception;

public class CustomSQLException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomSQLException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomSQLException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
