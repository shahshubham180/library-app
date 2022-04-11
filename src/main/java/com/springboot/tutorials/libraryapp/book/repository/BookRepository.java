package com.springboot.tutorials.libraryapp.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.tutorials.libraryapp.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findByBookId(Long id);

	public Book getBookByBookName(String bookName);

}
