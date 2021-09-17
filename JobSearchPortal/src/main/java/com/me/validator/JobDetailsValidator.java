package com.me.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.pojo.JobDetails;
import com.me.pojo.User;

public class JobDetailsValidator implements Validator {
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(JobDetails.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		JobDetails job = (JobDetails) obj;
		
		System.out.println("inside JobDetails validator");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required", "Title Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "companyName.required", "Company Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.required", "Country Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "state.required", "State Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "industry", "industry.required", "Industry Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "major", "major.required", "Major Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minGpa", "minGpa.required", "MinGpa Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required", "Description Required");
		
		
		
		
		Pattern p = Pattern.compile("^[a-zA-Z ]*$");
		System.out.println("inside JobDetails validator _-----" +p.matcher(job.getTitle()).matches());
		 if(!p.matcher(job.getTitle()).matches()){
			 errors.rejectValue("title", "title-invalid", "Enter a valid value.Only Alphabets !!");
		 }
		 if(!p.matcher(job.getIndustry()).matches()){
			 errors.rejectValue("industry", "industry-invalid", "Enter a valid value.Only Alphabets!!");
		 }
		 if(!p.matcher(job.getCompanyName()).matches()){
			 errors.rejectValue("companyName", "companyName-invalid", "Enter a valid value.Only Alphabets!!");
		 }
		
	}



}
