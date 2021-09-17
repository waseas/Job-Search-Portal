package com.me.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class GlobalExceptionHandler {

	//commons-fileupload
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleError2(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
		System.out.println("*****INside Exception Handler");
		redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getCause().getMessage());
		mv.setViewName("student-message");
		return mv;

	}

}
