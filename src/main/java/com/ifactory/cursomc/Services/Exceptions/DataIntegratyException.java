package com.ifactory.cursomc.Services.Exceptions;

public class DataIntegratyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegratyException(String msg) {
		super(msg);
	}
	
	public DataIntegratyException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
