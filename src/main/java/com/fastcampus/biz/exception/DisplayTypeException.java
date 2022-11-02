package com.fastcampus.biz.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DisplayTypeException extends Exception{

	private static final long serialVersionUID = 1L;

	public DisplayTypeException(String message){
		super(message);
	}
}
