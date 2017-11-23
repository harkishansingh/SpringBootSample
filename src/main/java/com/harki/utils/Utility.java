package com.harki.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Utility {
	public static List<String> listValidationErrors(BindingResult bindingResult) {
		List<String> errorList = new ArrayList<String>();
		for (FieldError errors : bindingResult.getFieldErrors()) {
			errorList.add(errors.getField() + " " + errors.getDefaultMessage());
		}
		return errorList;
	}
}
