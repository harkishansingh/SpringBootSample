package com.harki.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.harki.model.ExceptionResponse;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ToDoNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> todoNotFound(ToDoNotFoundException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), "Any desc you want to add");
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
