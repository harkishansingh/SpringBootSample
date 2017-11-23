package com.harki.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.harki.exception.RestResponseEntityExceptionHandler;
import com.harki.model.ToDo;
import com.harki.service.ToDoService;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

	private static final int CREATED_TODO_ID = 4;

	@Autowired
	private MockMvc mvc; // used to make request

	@MockBean
	private ToDoService toDoService;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void retrieveToDos() throws Exception {
		List<ToDo> mockList = Arrays.asList(new ToDo(1, "Jack", "Learn Spring MVC", new Date(), true),
				new ToDo(2, "Jack", "Learn Struts", new Date(), false));

		when(toDoService.retrieveTodos(anyString())).thenReturn(mockList);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/users/Jack/todos").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String expected = "["
				+ "{id:1,user:Jack,desc:\"Learn Spring MVC\",done:true}, {id:2,user:Jack,desc:\"Learn Struts\",done:false}"
				+ "]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveToDo() throws Exception {
		ToDo mockToDo = new ToDo(1, "Jack", "Learn Spring MVC", new Date(), true);

		when(toDoService.retrieveToDo(anyInt())).thenReturn(mockToDo);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/users/Jack/todos/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String expected = "{id:1,user:Jack,desc:\"Learn Spring MVC\",done:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createToDo() throws Exception {
		ToDo mockToDo = new ToDo(CREATED_TODO_ID, "Jack", "Learn Spring MVC", new Date(), true);
		String todo = "{\"user\":\"Jack\",\"desc\":\"Learn Spring MVC\",\"done\":\"true\"}";

		when(toDoService.addTodo(anyString(), anyString(), anyObject(), anyBoolean())).thenReturn(mockToDo);

		mvc.perform(MockMvcRequestBuilders.post("/users/Jack/todos").content(todo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("/users/Jack/todos/" + CREATED_TODO_ID)));
	}
	
	@Test(expected = NestedServletException.class)
	public void createToDo_WithValidationException() throws Exception {
		ToDo mockToDo = new ToDo(CREATED_TODO_ID, "Jack", "Learn Spring MVC", new Date(), true);
		String todo = "{\"user\":\"Jack\",\"desc\":\"Learn\",\"done\":\"true\"}";

		when(toDoService.addTodo(anyString(), anyString(), anyObject(), anyBoolean())).thenReturn(mockToDo);

		mvc.perform(MockMvcRequestBuilders.post("/users/Jack/todos").content(todo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}

}
