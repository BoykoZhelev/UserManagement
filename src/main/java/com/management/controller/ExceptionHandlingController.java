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
 	private static final String ERROR_MSG = "Wrong LocalDate format. Format 'dd/MM/yyyy' should be used";

 	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(JsonMappingException e){
		ErrorMessage message = new ErrorMessage(ERROR_MSG);
		LOGGER.warning(ERROR_MSG);
		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
	}
}
