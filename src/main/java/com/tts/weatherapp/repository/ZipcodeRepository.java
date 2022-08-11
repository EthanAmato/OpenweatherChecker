package com.tts.weatherapp.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.weatherapp.domain.Zipcode;

@Repository
public interface ZipcodeRepository extends CrudRepository<Zipcode,Long>{
	List<Zipcode> findAllByZipcode(String zipcode);
	
	
}
