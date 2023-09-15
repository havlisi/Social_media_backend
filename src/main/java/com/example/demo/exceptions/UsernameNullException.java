package com.example.demo.exceptions;

public class UsernameNullException extends Exception {
	
	private String message;
	
	public UsernameNullException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
