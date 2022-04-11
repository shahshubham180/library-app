package com.springboot.tutorials.libraryapp.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.tutorials.libraryapp.exception.DataNotFoundException;
import com.springboot.tutorials.libraryapp.book.entity.Book;
import com.springboot.tutorials.libraryapp.book.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Book Controller", description = "CRUD Operations For Type Books")
@RequestMapping("/books")
@SecurityRequirement(name = "swaggerSecurity")
public class BookController {
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService service;
	
	@Operation(summary = "Endpoint to save a Book in DB", tags = {"Book Controller"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Saved Book in DB", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
			@ApiResponse(responseCode = "500", description = "Error while Saving Book in DB", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)	
	})
	@PostMapping("/saveBook")
	public Book saveBook(@Parameter(description = "Book to be Added, cannot be null", required = true, 
		schema = @Schema(implementation = Book.class)) @RequestBody Book book) {
		log.info("Inside saveBook method of BookController");
		return service.saveBook(book);
	}
	
	@Operation(summary = "Endpoint to get a Book from DB", tags = {"Book Controller"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retrieved Book from DB", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
			@ApiResponse(responseCode = "500", description = "Error while retrieving Book from DB", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book Not Found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)	
	})
	@GetMapping("/getBook/{id}")
	public Book getBook(@Parameter(description = "Book Id", required = true)@PathVariable("id") Long id) throws DataNotFoundException {
		log.info("Inside getBook method of BookController");
		log.info("Book ID to be fetched",id);
		return service.getBook(id);
	}
	
	@Operation(summary = "Endpoint to delete a Book from DB", tags = {"Book Controller"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deleted Book from DB", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
			@ApiResponse(responseCode = "500", description = "Error while deleting Book from DB", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book Not Found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)	
	})
	@DeleteMapping("/delete")
	public Book deleteBook(@Parameter(description = "Book to be deleted, cannot be null", required = true)@RequestBody Book book) throws DataNotFoundException {
		log.info("Inside deleteBook method of BookController");
		log.info("Book ID to be deleted",book.getBookId());
		return service.deleteBook(book);	
	}
	
	@GetMapping("/ping")
	public String ping() {
		return "Service is Up";
	}
	
}
