package com.afformed.http.calculator.controller;

import org.springframework.web.bind.annotation.RestController;

import com.afformed.http.calculator.dto.CalcResponse;
import com.afformed.http.calculator.service.CalcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HttpController {

	
	    @Autowired
	    private CalcService calcService;
	    
	    @GetMapping("/hi")
	    public String hi() {
	        return "hi";
	    }

	    @GetMapping("/numbers/{type}")
	    public CalcResponse getNumbers(@PathVariable("type") String type) {
	        return calcService.getNumbers(type);
	    }
	}

