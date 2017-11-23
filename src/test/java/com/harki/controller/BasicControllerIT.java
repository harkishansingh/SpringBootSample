package com.harki.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.harki.Application;

@RunWith(SpringRunner.class)
//@SpringBootTest Provides extra functionality on top of SpringTest context
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BasicControllerIT {
	/*With RestTemplate, you have to deploy an actual server instance to listen for the HTTP requests you send.*/
	
	private static final String LOCAL_HOST = "http://localhost:";
	@LocalServerPort
	private int port;
	private TestRestTemplate template = new TestRestTemplate();
	
	@Test
	public void welcome() throws Exception{
		ResponseEntity<String> response = template.getForEntity(createURL("/welcome"),String.class);
		assertThat(response.getBody(),equalTo("Hello World"));
	}

	private String createURL(String uri){
		return LOCAL_HOST + port + uri;
		
	}
	
	@Test
	public void welcomeWithObject() throws Exception{
		ResponseEntity<String> response = template.getForEntity(createURL("/welcome-with-object"),String.class);
		assertThat(response.getBody(),containsString("Hello World"));
	}
	
	@Test
	public void welcomeWithParameter() throws Exception{
		ResponseEntity<String> response = template.getForEntity(createURL("/welcome-with-parameter/name/Buddy"),String.class);
		assertThat(response.getBody(),containsString("Hello World, Buddy"));
	}
}
