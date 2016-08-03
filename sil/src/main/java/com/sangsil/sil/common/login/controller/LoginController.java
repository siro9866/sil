package com.sangsil.sil.common.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.sangsil.sil.common.login.service.LoginService;

@Controller
public class LoginController {
	Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "loginService")
	private LoginService loginService;
	

}
