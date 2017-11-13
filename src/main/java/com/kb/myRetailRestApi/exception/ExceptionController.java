package com.kb.myRetailRestApi.exception;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionEntity> handleException(NullPointerException ex){
		
		ExceptionEntity entity = new ExceptionEntity();
		entity.setErrorMessage(ex.getMessage());
		
		return new ResponseEntity<ExceptionEntity>(entity,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionEntity> resourceNotFound(ResourceNotFoundException ex){
		
		ExceptionEntity entity = new ExceptionEntity();
		entity.setErrorMessage("Product Details not found");
		
		return new ResponseEntity<ExceptionEntity>(entity, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionEntity> resourceNotFound(MethodArgumentNotValidException ex){
		BindingResult errors = ex.getBindingResult();
		
		ExceptionEntity entity = new ExceptionEntity();
		entity.setErrorMessage("Please crosscheck Methods Arguments");
		entity.setExceptionList(errors.getAllErrors());
		
		return new ResponseEntity<ExceptionEntity>(entity, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ExceptionEntity> dbError(SQLException ex){
		
		ExceptionEntity entity = new ExceptionEntity();
		entity.setErrorMessage(ex.getMessage());
		entity.setErrorCode(ex.getErrorCode());
		
		return new ResponseEntity<ExceptionEntity>(entity, HttpStatus.BAD_REQUEST);
	}

}
