package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Hello;
import com.example.demo.model.GeneratePdfReport;
import com.example.demo.model.Reponse;
import com.example.demo.repository.ElasticSearchRepository;
import com.example.demo.repository.HelloRepository;
import com.example.demo.services.HelloService;


@RestController
@RequestMapping("/api")
public class HelloController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
	
	ElasticSearchRepository elasticSearchRepository;
    HelloRepository repository;
	HelloService helloService;
	@Autowired
	public HelloController(HelloRepository repository,HelloService helloService,ElasticSearchRepository elasticSearchRepository) {
		this.repository=repository;
		this.helloService=helloService;
		this.elasticSearchRepository=elasticSearchRepository;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Reponse> getValueById(@PathVariable("id") Long id) {
		//Date date = new Date();
    	Reponse resp = new Reponse();
    	resp.setNombre("Hello");
    	resp.setRegistros_status("SUCCESS");
    	LOGGER.info("Searching value with id: "+id);
        Optional<Hello> hell=repository.findById(id);
        if(hell.isEmpty())
        {
        	LOGGER.error("DATA NOT FOUND");
        	resp.setRegistros_status("FAILED");
        	resp.setRegistros_fallidos(resp.getRegistros_fallidos()+1);
        	return new ResponseEntity<Reponse>(resp, HttpStatus.NOT_FOUND);
        }
        	
				
       return new ResponseEntity<Reponse>(resp, HttpStatus.OK);
		
		
	}
	
	@GetMapping("/elasticsearch/{id}")
	public ResponseEntity<Reponse> findByElasticSearch(@PathVariable("id") Long id) {
		//Date date = new Date();
    	Reponse resp = new Reponse();
    	resp.setNombre("Hello");
    	resp.setRegistros_status("SUCCESS");
    	LOGGER.info("Searching value with id: "+id);
        Optional<Hello> hell=elasticSearchRepository.findById(id);
        if(hell.isEmpty())
        {
        	LOGGER.error("DATA NOT FOUND");
        	resp.setRegistros_status("FAILED");
        	resp.setRegistros_fallidos(resp.getRegistros_fallidos()+1);
        	return new ResponseEntity<Reponse>(resp, HttpStatus.NOT_FOUND);
        }
        	
				
       return new ResponseEntity<Reponse>(resp, HttpStatus.OK);
		
		
	}
	
	
	
	
	
	@PostMapping("/add")
	public ResponseEntity<Reponse> addValueById(@RequestBody Hello value) {
		//Date date = new Date();
    	Reponse resp = new Reponse();
    	resp.setNombre("Hello");
    	resp.setRegistros_status("SUCCESS");
    	LOGGER.info("Trying to add value: "+value);
        try {
        	Hello hello=helloService.save(value);
			
		} catch (Exception e) {
			LOGGER.error("DATA NOT FOUND");
        	resp.setRegistros_status("FAILED");
        	resp.setRegistros_fallidos(resp.getRegistros_fallidos()+1);
        	return new ResponseEntity<Reponse>(resp, HttpStatus.BAD_REQUEST);
		}
        
        return new ResponseEntity<Reponse>(resp, HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() {

        List<Hello> cities = (List<Hello>) helloService.findAll();

        ByteArrayInputStream bis = GeneratePdfReport.valuesReport(cities);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
	
		
		

}
