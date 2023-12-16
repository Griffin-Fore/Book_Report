package com.griffinfore.LoginAndRegistration2.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.griffinfore.LoginAndRegistration2.models.Book;
import com.griffinfore.LoginAndRegistration2.repositories.BookRepository;

@Service
public class BookService {
	@Autowired BookRepository bookRepo;
	
	public ArrayList<Book> findAllBooks(){
		return bookRepo.findAll();
	}
	public Book findBookById(Long Id) {
		return bookRepo.findById(Id).orElse(null);
	}
	
	public Book createBook(Book book) {
		return bookRepo.save(book);
	}
	
	public Book updateBook(Book book) {
		return bookRepo.save(book);
	}
	
	public void deleteBook(Long Id) {
		bookRepo.deleteById(Id);
	}
}
