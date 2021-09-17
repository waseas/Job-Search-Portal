package com.me.exception;

public class JobApplicationException extends Exception {
	
	public JobApplicationException(String message)
	{
		super("JobApplicationException - "+message);
	}
	
	public JobApplicationException(String message, Throwable cause)
	{
		super("JobApplicationException - "+message,cause);
	}

}
