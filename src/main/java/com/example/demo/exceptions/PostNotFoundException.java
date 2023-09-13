package com.example.demo.exceptions;

public class PostNotFoundException extends Exception {
	
	private String message;
	
	public PostNotFoundException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
