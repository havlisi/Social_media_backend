package com.example.demo.exceptions;

public class UserWithEmailExistsException extends Exception {
	
	private String message;
	
	public UserWithEmailExistsException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
