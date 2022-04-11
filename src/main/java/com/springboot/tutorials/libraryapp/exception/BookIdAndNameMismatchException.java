package com.springboot.tutorials.libraryapp.exception;

public class BookIdAndNameMismatchException extends Exception {
	
	public BookIdAndNameMismatchException() {
		super();
	}
	
	public BookIdAndNameMismatchException(String message) {
		super(message);
	}

}
