package com.harki.controller;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.harki.model.WelcomeBean;

// @RestController provided combination of ResponseBody and Controller annotation
@RestController
public class BasicController {
	@GetMapping("/welcome")
	public String welcome(){
		return "Hello World";
	}
	
	//Instance of default object to json will be converted by jasckson which is autoconfiguted by spring boot
	@GetMapping("/welcome-with-object")
	public WelcomeBean welcomeWithObject(){
		return new WelcomeBean("Hello World");
	}
	
	private static final String HELLO_WORLD_TEMPLATE = "Hello World, %s";
	
	@GetMapping("/welcome-with-parameter/name/{name}")
	public WelcomeBean welcomeWithParameter(@PathVariable String name){
		return new WelcomeBean(String.format(HELLO_WORLD_TEMPLATE, name));
	}
}
