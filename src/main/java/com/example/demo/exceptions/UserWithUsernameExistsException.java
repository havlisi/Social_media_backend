package com.example.demo.exceptions;

public class UserWithUsernameExistsException extends Exception {
	
	private String message;
	
	public UserWithUsernameExistsException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
