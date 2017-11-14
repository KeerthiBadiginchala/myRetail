package com.kb.myRetailRestApi.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * Exception model class to be used to ExceptionController to process the error messages whenn exception occurs
 * 
 */
public class ExceptionEntity {

	private HttpStatus status;
	private String errorMessage;
	private List<ObjectError> exceptionList;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private String debugMessage;

	ExceptionEntity(){
		timestamp = LocalDateTime.now();
	}

	ExceptionEntity(HttpStatus status) {
		this();
		this.status = status;
	}
	ExceptionEntity(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.errorMessage = ex.getMessage();
		this.debugMessage = ex.getLocalizedMessage(); 
	}
	ExceptionEntity(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.errorMessage = ex.getMessage();
		this.debugMessage = ex.getLocalizedMessage(); 
	}

	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<ObjectError> getExceptionList() {
		return exceptionList;
	}
	public void setExceptionList(List<ObjectError> exceptionList) {
		this.exceptionList = exceptionList;
	}


}
