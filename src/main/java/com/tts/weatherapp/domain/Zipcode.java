package com.tts.weatherapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Zipcode {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String zipcode;
	
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Zipcode() {
		
	}
	
	public Zipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Override
	public String toString() {
		return "[id="+id+", zipcode="+zipcode+"]";
	}
}
