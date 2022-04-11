package com.springboot.tutorials.libraryapp.book.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Book Entity with fields")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "Auto Generated Book ID of the Book", example = "1", hidden = true)
	private Long bookId;
	@Schema(description = "Name of the Book", example = "Angels and Demons")
	private String bookName;
	@Schema(description = "Author of the Book", example = "Dan Brown")
	private String author;
	@Schema(description = "Publisher of the Book", example = "Pocket Books")
	private String publisher;
}
