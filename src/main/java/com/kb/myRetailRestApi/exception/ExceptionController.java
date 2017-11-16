package com.kb.myRetailRestApi.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/*
 * Exception Handler to create user friendly messages when an exception occurs 
 */
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionEntity> handleException(NullPointerException ex){
		return new ResponseEntity<ExceptionEntity>(new ExceptionEntity(HttpStatus.NO_CONTENT,ex),HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionEntity> resourceNotFound(ResourceNotFoundException ex){
		
		String message = "Product Details not found";
		return new ResponseEntity<ExceptionEntity>(new ExceptionEntity(HttpStatus.NOT_FOUND,message,ex), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionEntity> resourceNotFound(MethodArgumentNotValidException ex){
		BindingResult errors = ex.getBindingResult();
		
		ExceptionEntity entity = new ExceptionEntity(HttpStatus.BAD_REQUEST);
		entity.setErrorMessage("Please crosscheck Method/Request Body Arguments");
		List<String> errorsList = new ArrayList<>();
		
		if(errors.hasFieldErrors()){
			errors.getFieldErrors().forEach(fe -> {
				String fieldError = fe.getDefaultMessage();
				errorsList.add(fieldError);
			});
			
		}
		entity.setValidationErrors(errorsList);
		//errors.
		return new ResponseEntity<ExceptionEntity>(entity, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ExceptionEntity> dbError(SQLException ex){
		return new ResponseEntity<ExceptionEntity>(new ExceptionEntity(HttpStatus.BAD_REQUEST,ex), HttpStatus.BAD_REQUEST);
	}

}
