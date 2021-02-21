package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Hello;
import com.example.demo.repository.HelloRepository;

@Service
public class HelloService {
	HelloRepository repository;
	@Autowired
	public HelloService(HelloRepository repository) {
		this.repository=repository;
		
	}
	
	public Hello save(Hello h) {
		return repository.save(h);
		
	}
	public List<Hello> findAll()
	{
	   return (List<Hello>)repository.findAll();
	}

}
