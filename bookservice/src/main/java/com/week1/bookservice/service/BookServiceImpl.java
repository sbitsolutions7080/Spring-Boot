package com.week1.bookservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.week1.bookservice.dao.BookServiceRepository;
import com.week1.bookservice.model.Book;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookServiceRepository bookServiceRepository;

	@Override
	public List<Book> getAllBooks() {
		return (List<Book>) bookServiceRepository.findAll();
	}

	@Override
	public Optional<Book> getBook(long bookId) {
		return bookServiceRepository.findById(bookId);
	}

	@Override
	public Book saveBook(Book book) {
		return bookServiceRepository.save(book);
	}

	@Override
	public void deleteBook(long bookId) {
		bookServiceRepository.deleteById(bookId);
	}
	
}
