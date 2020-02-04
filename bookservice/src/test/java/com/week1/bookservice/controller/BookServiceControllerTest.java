package com.week1.bookservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.week1.bookservice.model.Book;
import com.week1.bookservice.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookServiceController.class)
@ContextConfiguration(classes = { BookService.class })
public class BookServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	BookServiceController bookServiceController;

	@Mock
	BookService bookService;

	private static final String CONTEXT_PATH = "/api/v1/books";

	List<Book> books = null;

	@Before
	public void setup() {

		// this must be called for the @Mock annotations above to be processed
		// and for the mock service to be injected into the controller under
		// test.
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookServiceController).build();

	}

	@Test
	public void testGetAllBooks() throws Exception {

		List<Book> books = prepareBooks();
		when(bookService.getAllBooks()).thenReturn(books);
		mockMvc.perform(MockMvcRequestBuilders.get(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].book_id").isNotEmpty());

	}

	@Test
	public void testGetBook() throws Exception {

		Book book = prepareBooks().get(0);
		when(bookService.getBook((long) 1)).thenReturn(Optional.of(book));
		mockMvc.perform(MockMvcRequestBuilders.get(CONTEXT_PATH + "/{book_id}", 1).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.book_id").isNotEmpty());

	}

	@Test
	public void testSaveBook() throws Exception {
		Book book = prepareBooks().get(0);
		when(bookService.saveBook((Book) any(Object.class))).thenReturn(book);
		mockMvc.perform(MockMvcRequestBuilders.post(CONTEXT_PATH).content(asJsonString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.book_id").exists());

	}

	@Test
	public void testUpdateBook() throws Exception {
		Book book = prepareBooks().get(0);
		when(bookService.saveBook((Book) any(Object.class))).thenReturn(book);
		mockMvc.perform(MockMvcRequestBuilders.put(CONTEXT_PATH).content(asJsonString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testdeleteBook() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.delete(CONTEXT_PATH + "/{book_id}", 1).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isAccepted());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> prepareBooks() {

		Book book = new Book();
		book.setAuthor("sadiq");
		book.setBook_id((long) 1);
		book.setName("Complete Microservices");
		book.setCategory("IT");
		book.setDescription("Complete Microservices");

		Book book1 = new Book();
		book.setAuthor("Mannu");
		book.setBook_id((long) 2);
		book.setName("Microservices Design Patterns");
		book.setCategory("IT");
		book.setDescription("Microservices Design Patterns");

		List<Book> books = new ArrayList<Book>();

		books.add(book);
		books.add(book1);

		return books;
	}

}
