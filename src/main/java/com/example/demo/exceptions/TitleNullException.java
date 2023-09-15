package com.example.demo.exceptions;

public class TitleNullException extends Exception {
	
	private String message;
	
	public TitleNullException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
