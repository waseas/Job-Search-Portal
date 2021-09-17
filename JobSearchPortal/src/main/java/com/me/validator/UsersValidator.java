package com.me.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.pojo.User;

public class UsersValidator implements Validator {
	
	@Override
    public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
    }

	@Override
	public void validate(Object obj, Errors errors) {
		
		User user = (User) obj;
		
		System.out.println("inside Users validator");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "UserName Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password Required");
		
		String username = user.getUsername();
		System.out.println(username);
		
		Pattern p = Pattern.compile("^[a-zA-Z ]*$");
		 if(!p.matcher(user.getFirstName()).matches()){
			 errors.rejectValue("firstName", "firstName-invalid", "Enter a valid value");
		 }
		 if(!p.matcher(user.getLastName()).matches()){
			 errors.rejectValue("lastName", "lastName-invalid", "Enter a valid value");
		 }
		
		 Pattern p1 = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		 if(!p1.matcher(user.getUsername()).matches()){
			 errors.rejectValue("username", "username-invalid", "Enter a valid email");
	}

}

}