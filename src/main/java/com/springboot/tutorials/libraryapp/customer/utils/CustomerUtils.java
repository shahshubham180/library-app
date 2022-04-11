package com.springboot.tutorials.libraryapp.customer.utils;

public class CustomerUtils {

	public String createFullName(String firstName, String lastName) {
		return firstName.concat(" ").concat(lastName);
	}
}
