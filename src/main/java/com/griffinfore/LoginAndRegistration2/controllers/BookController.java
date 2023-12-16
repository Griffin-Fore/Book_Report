package com.griffinfore.LoginAndRegistration2.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.griffinfore.LoginAndRegistration2.models.Book;
import com.griffinfore.LoginAndRegistration2.models.User;
import com.griffinfore.LoginAndRegistration2.services.BookService;
import com.griffinfore.LoginAndRegistration2.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

// Needs:
//	Book Model connected to the User model one to many
//		one to many /
//		title validations notblank + message /
//		author name notblank + message /
//		my thoughts notblank + message /
//		user /
//		createdat /
//		updatedat /
//		constructor /
//		getters and setters /
//		createdAt Date /
//		updatedAt Date /

//	Book Repository for:
//		find all books /

//	Book Service
//		find all books /
//		find book by id /
//		create book /
//		update book /
//		delete book /
//		
//	Book Controller
//		view all books page /
//			protected by session /
//			name from session /
//			list all books table /
//				view one book link /
//			logout button /
//			create new book page link /

//		create book page /
//			back to all books button /
//			create book form /

//		create book route /
//			protected by session /
//			errors that load page with error messages /
//			create in database /
//			if successful: redirect to one_book /


//		view one book page /
//			redirect button to all books /

//		edit book page /
//			protected by session /
//			edit book form /

//		edit book route /
//			protected by session /
//			put request /

//		delete book route /
//			retrieve book id /
//			deleteById /

@Controller
public class BookController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private BookService bookService;
	
//	view all books page /
//	protected by session /
//	name from session /
//	list all books table /
//		view one book link as the book name /
//	logout button /
//	create new book page link /

//	View all books page
	@GetMapping("/books")
	public String welcomePage(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedIn", "You must be logged in to view this page.");
			return "redirect:/auth/loginandregpage";
		}
		Long userId = (Long) session.getAttribute("loggedInUser");
		User loggedInUser = userService.findOneUser(userId);
		model.addAttribute("user", loggedInUser);
		ArrayList<Book> allBooks = bookService.findAllBooks();
		model.addAttribute("all_books", allBooks);
		return "WelcomePage.jsp";
	}

//	Create new book page /
//	contains a @ModelAttribute /
//	protected by session /
//	returns a jsp file /
//	back to all books button /
//	contains the user from session /

	@GetMapping("/books/new")
	public String new_book_page(@Valid @ModelAttribute("NewBook") Book book, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedIn", "You must be logged in to view this page.");
			return "redirect:/auth/loginandregpage";
		}
		Long userId = (Long) session.getAttribute("loggedInUser");
		User loggedInUser = userService.findOneUser(userId);
		model.addAttribute("user", loggedInUser);
		return "CreateBookPage.jsp";
	}
	
//	Create new book route /
//	protected by session /
//	errors reload the same page /
//	create the book /
//	success redirects to the page of one book /
	@PostMapping("/create_book")
	public String create_book_route(@Valid @ModelAttribute("NewBook") Book book, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedInRoute", "You must be loggged in to view this route.");
			return "redirect:/auth/loginandregpage";
		}
		if(result.hasErrors()) {
			return "CreateBookPage.jsp";
		}
//		create the book /
//		Add the user to the book /
		Long userId = (Long) session.getAttribute("loggedInUser");
		User loggedInUser = userService.findOneUser(userId);
		book.setUser(loggedInUser);
		Book newBook = bookService.createBook(book);
		return "redirect:/books/" + newBook.getId();
	}
	
//	View one book page /
//	protected by session /
//	model.addattribute loads the book into the page /
//		if the book.user.id matches session id, edit and delete routes /
	@GetMapping("/books/{id}")
	public String view_one_book(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedIn", "You must be logged in to view this page.");
			return "redirect:/auth/loginandregpage";
		}
		Book oneBook = bookService.findBookById(id);
		if(oneBook.getId() == null) {
			return "redirect:/books";
		}
		Long userId = (Long) session.getAttribute("loggedInUser");
		model.addAttribute("selectedBook", oneBook);
		model.addAttribute("userId", userId);
		return "OneBook.jsp";
	}
	

//	Edit book page /
//	protected by session /
//	doesn't field the user /
//	if the book.user.id matches session, load page. If not, redirect to all books /
	@GetMapping("/books/{id}/edit")
	public String editBookPage(@ModelAttribute("EditBook") Book editedBook, @PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedIn", "You must be logged in to view this page.");
			return "redirect:/auth/loginandregpage";
		}
		Book bookToEdit = bookService.findBookById(id);
		if(bookToEdit.getId() == null) {
			redirectAttributes.addFlashAttribute("notBookReportCreator", "You must have created this report to edit it.");
			return "redirect:/books";
		}
		Long userId = (Long) session.getAttribute("loggedInUser");
		User loggedInUser = userService.findOneUser(userId);
		if(loggedInUser != bookToEdit.getUser()) {
			redirectAttributes.addFlashAttribute("notCreator", "You did not create this book report.");
			return "redirect:/books";
		}
		System.out.println("Reached the edit point");
		model.addAttribute("existingBook", bookToEdit);
		return "EditBook.jsp";		
	}
	
	
//	Edit book route /
//	PUT request /
//	protected by session /
//	errors load edit book page /
	@RequestMapping(value="/edit_book/{id}", method=RequestMethod.PUT)
	public String editBookProcess(@PathVariable("id") Long id, @Valid @ModelAttribute("EditBook") Book editedBook, BindingResult result, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
		System.out.println("Edit process works");
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedInRoute", "You must be logged in to view this route.");
			return "redirect:/auth/loginandregpage";
		}
		if(result.hasErrors()) {
			Book bookToEdit = bookService.findBookById(id);
			model.addAttribute("existingBook", bookToEdit);
			return "EditBook.jsp";
		}
		Book bookToEdit = bookService.findBookById(id);
		if(bookToEdit.getId() == null) {
			redirectAttributes.addFlashAttribute("bookNotExist", "The book you are trying to edit does not exist.");
			return "redirect:/books";
		}
//		Update the book fields that have information from the ModelAttribute
		if(editedBook.getTitle() != null) {
			bookToEdit.setTitle(editedBook.getTitle());
		}
		if(editedBook.getAuthor() != null) {
			bookToEdit.setAuthor(editedBook.getAuthor());
		}
		if(editedBook.getMyThoughts() != null) {
			bookToEdit.setMyThoughts(editedBook.getMyThoughts());
		}
		bookService.updateBook(bookToEdit);
		return "redirect:/books/" + bookToEdit.getId();
	}
	
//	Delete book route /
//	protected by session /
	@RequestMapping("/books/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpSession session) {
		if(session.getAttribute("loggedInUser") == null) {
			redirectAttributes.addFlashAttribute("notLoggedInRoute", "You must be logged in to view this route.");
			return "redirect:/auth/loginandregpage";
		}
		Book bookToDelete = bookService.findBookById(id);
		Long userId = (Long) session.getAttribute("loggedInUser");
		User loggedInUser = userService.findOneUser(userId);
		if(loggedInUser != bookToDelete.getUser()) {
			return "redirect:/books/" + id;
		}
		bookService.deleteBook(id);
		return "redirect:/books";
	}
}
