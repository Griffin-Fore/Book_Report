package com.griffinfore.LoginAndRegistration2.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.griffinfore.LoginAndRegistration2.models.Book;

@Repository
public interface BookRepository extends CrudRepository <Book, Long> {
	public ArrayList<Book> findAll();
}
