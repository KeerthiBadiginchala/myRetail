package com.kb.myRetailRestApi.exception;

import java.util.List;

import org.springframework.validation.ObjectError;
public class ExceptionEntity {
	
	private int errorCode;
	private String errorMessage;
	private List<ObjectError> exceptionList;
	
	ExceptionEntity(){
		
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
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
