package com.example.demo.exceptions;

public class PasswordConfirmationException extends Exception {
	
	private String message;
	
	public PasswordConfirmationException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
