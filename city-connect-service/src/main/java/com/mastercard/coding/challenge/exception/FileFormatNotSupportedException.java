package com.mastercard.coding.challenge.exception;

/**
 * User exception class incase the city routes input file format is not as per the specified format 
 *
 */
public class FileFormatNotSupportedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileFormatNotSupportedException(String message){
		
		super(message);
	}

}
