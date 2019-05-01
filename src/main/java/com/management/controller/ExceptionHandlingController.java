package com.management.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.modelmapper.spi.ErrorMessage;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlingController {

 	private static final Logger LOGGER = Logger.getLogger(ExceptionHandlingController.class.getName());

 	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(JsonMappingException e){
		ErrorMessage message = new ErrorMessage("Wrong LocalDate format.");
		LOGGER.warning("Wrong LocalDate format");
		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
	}
}
