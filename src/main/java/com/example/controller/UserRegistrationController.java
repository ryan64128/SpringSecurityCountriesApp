package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.User;
import com.example.service.UserService;
import com.example.web.UserRegistrationDto;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	@Autowired
	private UserService service;
	
	@GetMapping
	public String showUserRegistrationForm(Model model) {
		model.addAttribute("user", new UserRegistrationDto());
		return "registration";
	}
	
	@PostMapping
	public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result, Model model) {
		User user = service.getUserByEmail(userDto.getEmail());
		if (user != null) {
			result.rejectValue("email", null, "A user account with that email already exists");
		}
		if (result.hasErrors()) {
			return "registration";
		}
		service.save(userDto);
		return "redirect:/";
	}
}
