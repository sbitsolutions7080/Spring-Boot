package com.week1.bookservice.service;

import java.util.List;
import java.util.Optional;

import com.week1.bookservice.model.Book;

public interface BookService {
	
	public List<Book> getAllBooks();
	
	public Optional<Book> getBook(long bookId);
	
	public Book saveBook(Book book);
	
	public void deleteBook(long bookId);
		
}
