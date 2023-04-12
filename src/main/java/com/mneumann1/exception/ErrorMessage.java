/**
 * 
 */
package com.mneumann1.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author MNEUMANN1
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

}
