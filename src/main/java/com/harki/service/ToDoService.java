package com.harki.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.harki.model.ToDo;

@Service
public class ToDoService {

	private static List<ToDo> toDos = new ArrayList<>();
	private static int toDoCount = 3;

	static {
		toDos.add(new ToDo(1, "Jack", "Learn Spring MVC", new Date(), true));
		toDos.add(new ToDo(2, "Jack", "Learn Struts", new Date(), false));
		toDos.add(new ToDo(3, "Jill", "Learn Hibernate", new Date(), true));
	}

	public List<ToDo> retrieveTodos(String user) {
		return toDos.stream().filter(toDo -> toDo.getUser().equals(user)).collect(Collectors.toList());
	}

	public ToDo addTodo(String name, String desc, Date targetDate, boolean isDone) {
		ToDo toDo = new ToDo(++toDoCount, name, desc, targetDate, isDone);
		toDos.add(toDo);
		return toDo;
	}

	public ToDo retrieveToDo(int id) {
		for (ToDo todo : toDos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}
}
