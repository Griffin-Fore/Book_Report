package com.griffinfore.LoginAndRegistration2.repositories;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.griffinfore.LoginAndRegistration2.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	public ArrayList<User> findAll();
	
	public User findByEmail(String email);
}
