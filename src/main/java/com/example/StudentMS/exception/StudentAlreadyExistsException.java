package com.example.StudentMS.exception;

public class StudentAlreadyExistsException extends RuntimeException{
	
	public StudentAlreadyExistsException(String message) {
		super(message);
	}

}
