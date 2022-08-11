package com.tts.weatherapp.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tts.weatherapp.domain.Zipcode;
import com.tts.weatherapp.models.Response;
import com.tts.weatherapp.repository.ZipcodeRepository;

//will have utility routines for accessing the api

@Service //tells Springboot that this is a service we want to participate in inversion of control (i.e. sb makes its own versions for us elsewhere)
public class WeatherService {
	@Value("${api_key}")
	private String apiKey;
	
	@Autowired
	private ZipcodeRepository repository;
	
	
	
	public Response getForecast(String zipCode) {
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")//ensures that only straight up strings are put in without special characters
				.host("api.openweathermap.org")
				.path("/data/2.5/weather")
				.queryParam("zip",zipCode+",us")
				.queryParam("units","imperial")
				.queryParam("appid",apiKey)
				.build(); 
		
		String url = uri.encode().toUriString();
		System.out.println(url);
		RestTemplate restTemplate = new RestTemplate(); //used for Rest calls
		
		Response response;
		try {
			response = restTemplate.getForObject(url, Response.class); //url, type we want to store the info into (Our response class)
		} catch(HttpClientErrorException e) {
			response = new Response();
			response.setName("Error");
		}
			
		return response;
	}
	
	
	public LinkedList<String> getRecentSearches(){
		int counter = 10;
		LinkedList<String> recents = new LinkedList<>(); 
		for (Zipcode zipcode: repository.findAll()) {
			if (counter == 0) {
				break;
			}
			recents.addFirst(zipcode.getZipcode());
			counter-=1;
			
		}
		return recents;
	}
	
}
