package com.kb.myRetailRestApi.exception;

import javassist.SerialVersionUID;

/*
 * Created ResourceNotFoundException class to handle exceptions occurred when Product/Price details not found
 */
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String msg){
		super(msg);
	}

}
