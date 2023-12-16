package com.griffinfore.LoginAndRegistration2.services;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.griffinfore.LoginAndRegistration2.repositories.UserRepository;
import com.griffinfore.LoginAndRegistration2.models.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	
	public ArrayList<User> allUsers(){
		return userRepo.findAll();
	}
	
	public User findOneUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	public User createUser(User user) {
		return userRepo.save(user);
	}
	
	public User getByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}
}
