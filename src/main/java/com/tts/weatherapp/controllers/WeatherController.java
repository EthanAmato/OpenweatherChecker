package com.tts.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tts.weatherapp.domain.Zipcode;
import com.tts.weatherapp.models.Request;
import com.tts.weatherapp.models.Response;
import com.tts.weatherapp.repository.ZipcodeRepository;
import com.tts.weatherapp.services.WeatherService;

//learned yesterday that restcontroller handles responses -> use it for handling api responses
//IF controller is returning web pages we want to use a diff annotation -> annotation
@Controller
public class WeatherController {
	//want a copy of Weather service
	@Autowired
	private WeatherService weatherService; //how we would typically do it BUT we are in SpringBoot
																  //SpringBoot has inversion of control and therefore we don't always make our own objects
																  //Instead we tell SB how 2 make object and later we can
																  //just ask 4 it. We don't need 2 instantiate ourselves
																  //use @Autowired annotation to ask for objects
	@Autowired
	private ZipcodeRepository repository;
	
	
	//	@GetMapping("/") //respond ONLY to get requests to "/" only
//	@PostMapping("/") //respond ONLY to post requests to "/" only
//	@PutMapping("/"),@DeleteMapping("/") etc.
	
//	@RequestMapping(path = "/", method = RequestMethod.GET) //responds to any request type usually, 
															 //but with method=RequestMethod.GET it only responds to GET requests
	
	@GetMapping(path = "/") //respond ONLY to get requests to "/" only
	public String getIndex(Model model) { //inject model variable that's almost like a hashmap that holds attributes
		///Response response = weatherService.getForecast("08540"); //need to get response to web page
		//to do that, we first need to store response somewhere the webpage can access
		
		Request request = new Request();
		//request.setZipCode("0999999-totallyinvalid");
		model.addAttribute("recent",weatherService.getRecentSearches());
		model.addAttribute("request",request);
		return "index"; //since this is a controller and not a RestController, it will not be displayed on web page but
						//instead the name of a template we want to load. Trying to look up template called 'index'
						//would load index.html
	}
	
	
	@PostMapping(path="/")
	public String postIndex(Model model, Request request) {  //we get our form variables from the request variables
		
		String zipcode = request.getZipCode();
		
		Response response = weatherService.getForecast(zipcode); //need to get response to web page
		model.addAttribute("data",response);
		
		model.addAttribute("recent",weatherService.getRecentSearches());
		repository.save(new Zipcode(zipcode));
		
		return "index";
		
	}
	
	
}																		
