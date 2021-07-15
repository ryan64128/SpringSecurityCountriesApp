package com.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.model.User;
import com.example.web.UserRegistrationDto;

public interface UserServiceInterface extends UserDetailsService{
	public User getUserByEmail(String email);
	public User save(UserRegistrationDto registration);
}
