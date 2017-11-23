package com.harki.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.harki.exception.ToDoNotFoundException;
import com.harki.model.ToDo;
import com.harki.service.ToDoService;
import com.harki.utils.Utility;

@RestController
public class ToDoController {

	@Autowired
	private ToDoService toDoService;

	@GetMapping("/users/{name}/todos")
	public List<ToDo> retrieveToDos(@PathVariable String name) {
		return toDoService.retrieveTodos(name);
	}

	@GetMapping("/users/{name}/todos/{id}")
	public ToDo retrieveToDo(@PathVariable String name, @PathVariable int id) {
		ToDo todo = toDoService.retrieveToDo(id);
		if (todo == null)
			throw new ToDoNotFoundException(id + " Not Found!!!");
		return todo;
	}

	@PostMapping("/users/{name}/todos")
	ResponseEntity<?> add(@PathVariable String name, @Valid @RequestBody ToDo todo, BindingResult result) {
		if (result.hasErrors()) {
			throw new RuntimeException(Utility.listValidationErrors(result).toString());
		}
		ToDo createdTodo = toDoService.addTodo(name, todo.getDesc(), todo.getTargetDate(), todo.isDone());
		if (createdTodo == null) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTodo.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping(path = "/users/dummy-service")
	public ToDo errorService() {
		throw new RuntimeException("Some Exception occured");
	}
}
