package com.example.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repo.UserRepo;
import com.example.web.UserRegistrationDto;

@Service
public class UserService implements UserServiceInterface{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	public User getUserByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	public User save(UserRegistrationDto registration) {
		User user = new User();
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setRoles(Arrays.asList(new Role("USER")));
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(" **** Called loadUserByUsername() ***** email was: " + email);
		User user = userRepo.getUserByEmail(email);
		if (user == null) {
			System.out.println(" ^^^^^^^^ throwing exception ^^^^^^^^^");
			throw new UsernameNotFoundException("invalid username or password");
		}
		System.out.println(" user is " + user.getFirstName() + ", " + user.getLastName());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), this.mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	

}
