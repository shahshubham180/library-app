package com.springboot.tutorials.libraryapp.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.tutorials.libraryapp.book.entity.Book;
import com.springboot.tutorials.libraryapp.customer.entity.Customer;
import com.springboot.tutorials.libraryapp.customer.repository.CustomerRepository;
import com.springboot.tutorials.libraryapp.customer.utils.CustomerUtils;
import com.springboot.tutorials.libraryapp.exception.BookIdAndNameMismatchException;
import com.springboot.tutorials.libraryapp.exception.DataNotFoundException;
import com.springboot.tutorials.libraryapp.book.repository.BookRepository;

@Service
@RefreshScope
public class CustomerService {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private BookRepository bookRepository;
	
	@Autowired
	private CustomerUtils utils;
	
	@Autowired
	private RestTemplate template;
	
	private List<String> fullNames = new ArrayList<>();
	
	@Value("${senior.citizen}")
	private String seniorAgeGroup;
	
	@Value("${junior.citizen}")
	private String juniorAgeGroup;

	public Customer createCustomerAccount(Customer customer) throws BookIdAndNameMismatchException, DataNotFoundException {
		log.info("Inside createCustomerAccount method of CustomerService");
		if(StringUtils.isNotBlank(customer.getBookName())) {
			Book book = bookRepository.getBookByBookName(customer.getBookName());
			if(Objects.isNull(book)) {
				log.info("Book is not registered. Please register the Book");
				throw new DataNotFoundException("Book is not registered. Please register the Book");
			}
			if(!StringUtils.equals(String.valueOf(customer.getBookId()), String.valueOf(book.getBookId()))) {
				log.info("The Book Id and Book Name does not match in DB");
				throw new BookIdAndNameMismatchException("Book Id is invalid for the Book "+customer.getBookName());
			}
		}else
			throw new DataNotFoundException("The customer must register with a Book");
		return customerRepository.save(customer);
	}

	public Customer getCustomerInfo(Long custId) throws DataNotFoundException {
		log.info("Inside getCustomerInfo method of CustomerService");
		Customer customer = customerRepository.getCustomerByCustId(custId);
		if(Objects.isNull(customer)) {
			log.info("Customer does not Exist with Cust Id : {}",custId);
			throw new DataNotFoundException("Customer does not Exist with Cust Id :"+custId);
		}
		String fullName = utils.createFullName(customer.getFirstName(), customer.getLastName());
		fullNames.add(fullName);
		return customer;
	}
	
	public void deleteCustomer(Customer customer) throws DataNotFoundException {
		Customer customerdb = customerRepository.getCustomerByCustId(customer.getCustId());
		if(Objects.isNull(customerdb))
			throw new DataNotFoundException("Customer Does Not Exist To Delete");
		customerRepository.delete(customer);
	}

	public Customer bookSuggestion(Customer customer) throws JsonMappingException, JsonProcessingException {
		log.info("Inside bookSuggestion method of CustomerService");
		if(Objects.isNull(customer.getAge())) {
			log.info("Calling Public API");
			ResponseEntity<String> response = template.getForEntity("https://api.agify.io?name="+customer.getFirstName(), String.class);
			String jsonString = response.getBody();
			log.info(jsonString.toString());
			ObjectMapper mapper = new ObjectMapper();
				Map<String,Object> map = mapper.readValue(jsonString, Map.class);
				Integer age = (Integer) map.get("age");
				customer.setAge(age.longValue());
		}
		if(customer.getAge() < 60)
			customer.setBookName(juniorAgeGroup);
		else
			customer.setBookName(seniorAgeGroup);
		return customer;
	}
}
