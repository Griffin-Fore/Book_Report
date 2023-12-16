package com.griffinfore.LoginAndRegistration2.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.griffinfore.LoginAndRegistration2.models.LoginUser;
import com.griffinfore.LoginAndRegistration2.models.User;
import com.griffinfore.LoginAndRegistration2.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	
//	@ModelAttribute, name of the jsp table takes data from the post, turns it into an object,
//		does not put it in the database
//	Model.addAttribute: object to be created before loading any jsp with a form,
//		load the jsp on the same to display errors. They cannot travel between redirects
//	Bindingresutlt must be right after the @ModelAttribute, the information to be validated
//	you must restart every time you make changes to files other than .jsp

//	A route that loads a page with both register and login
	@GetMapping("/loginandregpage")
	public String loginandregpage(@ModelAttribute("Register") User user, Model model) {
		
//		model.addAttribute must match the modelAttribute name in the form
//		You don't need to include the model.addAttribute if there is an @ModelAttribute
//		Because they both connect to the form, when you view a form, you add the modelAttribute
//		The name of both the @ModelAttribute and model.addAttribute needs to match the form name
//		Both add to the model object
//		When the @ModelAttribute is in the signature, it connects the sent model data to the form
//		model.addAttribute also pre-connects the form data to an empty object instance,
//		if there are form fields matching the object fields
//		You can send data using both methods
		model.addAttribute("LoginPath", new LoginUser());
		return "LoginAndRegPage.jsp";
	}
	
//	a POST route to create a user with errors that redirects back to the login
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("Register") User user, BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		model.addAttribute("LoginPath", new LoginUser());
		if(userService.getByEmail(user.getEmail()) != null) {
			result.rejectValue("email", "uniqueEmail", "Email is already in use!");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("password", "passwordMatch", "Password must match confirm password");
		}
		
		if(result.hasErrors()) {
			return "LoginAndRegPage.jsp";
		}
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		User newUser = userService.createUser(user);
		session.setAttribute("loggedInUser", newUser.getId());
		return "redirect:/books";
	}
	
//	A POST route to login with errors that redirects back to the login
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("LoginPath") LoginUser loginUser, BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		model.addAttribute("Register", new User());
		if(result.hasErrors()) {
			return "LoginAndRegPage.jsp";
		}
		User userFromDb = userService.getByEmail(loginUser.getEmail());
		
		if(userFromDb == null) {
			result.rejectValue("email", "invalidEmail", "Invalid Login");
			return "LoginAndRegPage.jsp";
		}
		
		if(!BCrypt.checkpw(loginUser.getPassword(), userFromDb.getPassword())) {
			result.rejectValue("password", "invalidPassword", "Invalid Login");
			return "LoginAndRegPage.jsp";
		}
		
		session.setAttribute("loggedInUser", userFromDb.getId());
		return "redirect:/books";
		
	}
//	a logged in page protected by session
	
//	Logout route that clears session
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/auth/loginandregpage";
	}
}
