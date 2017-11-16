package com.kb.myRetailRestApi.exception;


/*
 * Created ResourceNotFoundException class to handle exceptions occurred when Product/Price details not found
 */
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg){
		super(msg);
	}

}
