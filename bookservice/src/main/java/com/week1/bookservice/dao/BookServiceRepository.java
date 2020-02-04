package com.week1.bookservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.week1.bookservice.model.Book;

@Repository
public interface BookServiceRepository extends CrudRepository<Book, Long>{
	
}
