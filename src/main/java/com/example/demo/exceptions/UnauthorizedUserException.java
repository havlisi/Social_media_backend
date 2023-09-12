package com.example.demo.exceptions;

public class UnauthorizedUserException extends Exception {
	
	private String message;
	
	public UnauthorizedUserException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
