package com.springboot.tutorials.libraryapp.book.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tutorials.libraryapp.book.entity.Book;
import com.springboot.tutorials.libraryapp.exception.DataNotFoundException;
import com.springboot.tutorials.libraryapp.book.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public Book getBook(Long id) throws DataNotFoundException {
		Book book = bookRepository.findByBookId(id);
		if(Objects.isNull(book))
			throw new DataNotFoundException("Book Does Not Exist To Delete");
		return book;
	}

	public Book deleteBook(Book book) throws DataNotFoundException {
			Book bookdb = bookRepository.findByBookId(book.getBookId());
			if(Objects.isNull(bookdb))
				throw new DataNotFoundException("Book Does Not Exist To Delete");
			bookRepository.delete(book);
			return book;
	}
	
	public Book getBookByName(String bookName) {
		return bookRepository.getBookByBookName(bookName);
	}
}
