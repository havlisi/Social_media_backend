package com.example.demo.exceptions;

public class CantFollowSelfException extends Exception {
	
	private String message;
	
	public CantFollowSelfException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
