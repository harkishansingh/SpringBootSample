package com.harki.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringRunner.class) is shortcut for SpringJunit4ClassRunner annotation. It launched simple spring context for unit testing
@RunWith(SpringRunner.class)

// @WebMvcTest(BasicController.class) is used to test Spring MVC controllers.
// This will load beans associated with SpringMvc annotation.
@WebMvcTest(BasicController.class)
public class BasicControllerTest {
	/*
	 * With MockMvc, you're typically setting up a whole web application context
	 * and mocking the HTTP requests and responses. So, although a fake
	 * DispatcherServlet is up and running, simulating how your MVC stack will
	 * function, there are no real network connections made.
	 */

	@Autowired
	private MockMvc mvc; // used to make request

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void welcome() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/welcome").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("Hello World"));
	}

	@Test
	public void welcomeWithObject() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/welcome-with-object").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Hello World")));
	}

	@Test
	public void welcomeWithParameter() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/welcome-with-parameter/name/Buddy").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Hello World, Buddy")));
	}

}
