package com.example.demo.exceptions;

public class FollowingExistsException extends Exception {
	
	private String message;
	
	public FollowingExistsException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
