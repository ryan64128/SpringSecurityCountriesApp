package com.example.controller;


import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Country;
import com.example.model.User;
import com.example.service.CountryService;
import com.example.service.UserService;

@Controller
public class HomeController {
	@Autowired
	CountryService service;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String getCountryList(Model model) {
		List<Country> list = service.getListOfCountries();
		model.addAttribute("countries", list);
		return "home";
	}
	
	@RequestMapping("/country_info/{id}")
	public ModelAndView showCountryDetails(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView("country_info");
		Country country = service.getCountryById(id);
		mav.addObject("country", country);
		return mav;
	}
	
	@RequestMapping("/edit_population/{id}")
	public ModelAndView showEditPopulationPage(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView("edit_population");
		Country country = service.getCountryById(id);
		mav.addObject("country", country);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteCountry(@PathVariable(name="id") int id) {
		service.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/add_country")
	public String showAddCountryForm(Model model) {
		Country country = new Country();
		model.addAttribute("country", country);
		return "addCountryForm";
	}
	
	@PostMapping("/add_country")
	public String submitAddCountryForm(@Valid @ModelAttribute("country") Country country, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addCountryForm";
		}
		service.saveCountry(country);
		return "redirect:/";
	}
	
	@RequestMapping(value="/save_edit", method=RequestMethod.POST)
	public String saveEdit(@Valid @ModelAttribute("country") Country country, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("country", country);
			return "edit_population";
		}
		service.saveCountry(country);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
//	@GetMapping("/login")
//	public String showLoginPage(Model model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		System.out.println("Creating Login page");
//		return "login";
//	}
//	
//	@PostMapping("/login")
//	public String attemptLogin(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
//		User u = userService.getUserByEmail(user.getEmail());
//		System.out.println(" ************** Hello, " + u.getFirstName());
//		return "home";
//	}
}
