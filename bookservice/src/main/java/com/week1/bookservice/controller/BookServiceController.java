package com.week1.bookservice.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.week1.bookservice.exception.BookNotFoundException;
import com.week1.bookservice.model.Book;
import com.week1.bookservice.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookServiceController {
	
	@Autowired
	private BookService bookService;
	
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
    
    @GetMapping(value = "/{book_id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getBook(@PathVariable @Min(1) long book_id){
		return bookService.getBook(book_id)
				.orElseThrow(() -> new BookNotFoundException(book_id));
	}
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Book> saveBooks( @Valid @RequestBody Book book){
		return  ResponseEntity.ok(bookService.saveBook(book));
	}
    
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> updateBook( @Valid @RequestBody Book book){
		return ResponseEntity.ok(bookService.saveBook(book));
	}
    
    @DeleteMapping(value = "/{book_id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable long book_id){
		 bookService.deleteBook(book_id);
		 return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

}
