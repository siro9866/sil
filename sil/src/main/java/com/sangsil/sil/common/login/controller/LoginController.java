package com.sangsil.sil.common.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sangsil.sil.common.login.service.LoginService;

@Controller
public class LoginController {
	Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "loginService")
	private LoginService loginService;
	
	
	@RequestMapping(name="/common/login/loginForm")
	public ModelAndView loginForm() throws Exception{
		ModelAndView mav = new ModelAndView("/common/login/loginForm");
		
		return mav;
	}
	
}
