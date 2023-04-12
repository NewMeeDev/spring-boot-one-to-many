/**
 * 
 */
package com.mneumann1.exception;

/**
 * @author MNEUMANN1
 *
 */
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
