package com.harki.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.net.URI;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.harki.Application;
import com.harki.model.ToDo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoControllerIT {

	private static final String LOCAL_HOST = "http://localhost:";
	@LocalServerPort
	private int port;
	private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void _1retrieveToDos() throws Exception {
		String expected = "["
				+ "{id:1,user:Jack,desc:\"Learn Spring MVC\",done:true}, {id:2,user:Jack,desc:\"Learn Struts\",done:false}"
				+ "]";

		String uri = "/users/Jack/todos";
		ResponseEntity<String> response = template.getForEntity(createURL(uri), String.class);
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	@Test
	public void _2retrieveToDo() throws Exception {
		String expected = "{id:1,user:Jack,desc:\"Learn Spring MVC\",done:true}";

		String uri = "/users/Jack/todos/1";
		ResponseEntity<String> response = template.getForEntity(createURL(uri), String.class);
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	@Test
	public void _3addToDo() throws Exception {
		ToDo todo = new ToDo(-1, "Jack", "Learn Spring MVC", new Date(), true);

		URI location = template.postForLocation(createURL("/users/Jack/todos"), todo);
		assertThat(location.getPath(),containsString("/users/Jack/todos/4"));

	}

	private String createURL(String uri) {
		return LOCAL_HOST + port + uri;

	}

}
