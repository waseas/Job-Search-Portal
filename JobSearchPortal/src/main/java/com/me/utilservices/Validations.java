package com.me.utilservices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
	
	
	public static boolean firstName( String firstName ) {
	      return firstName.matches( "[A-Z][a-z]*" );
	   }
	
	public static boolean emailValidation(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	 
		Pattern p = Pattern.compile(regex);
	    Matcher match = p.matcher(email);
	    return match.matches();

}}
