package com.example.demo.exceptions;

public class NonExistingEmailException extends Exception {
	
	private String message;
	
	public NonExistingEmailException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
