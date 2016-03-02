package com.wzhang.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;  

@Controller
public class HelloController {

	protected static Logger logger = Logger.getLogger("controller");
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public ModelAndView welcome() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Welcome - Spring Security Hello World");
		model.addObject("message", "This is welcome page!");
		model.setViewName("hello");
		return model;
 
	}
 
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Admin - Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
 
		return model;
 
	}
 
	//Spring Security see this :
	@RequestMapping(value = "/login**", method = RequestMethod.GET)
	public ModelAndView loginPage(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
 
		return model;
 
	}
	
	@RequestMapping(value = "/index**", method = RequestMethod.GET)
	public ModelAndView indexPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "User - Home Page");
		model.addObject("message", "This is User access page!");
		model.setViewName("index");
 
		return model;
 
	}
	
	  
    /** 
     * 指定无访问额权限页面 
     *  
     * @return 
     */  
    @RequestMapping(value = "/denied**", method = RequestMethod.GET)  
    public String deniedPage() {  
  
        logger.debug("Received request to show denied page");  
  
        return "denied";  
  
    }  
 
}
