package com.sangsil.sil.common.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sangsil.sil.common.login.service.LoginService;
import com.sangsil.sil.common.util.ComMap;

@Controller
public class LoginController {
	Logger logger = Logger.getLogger(this.getClass());

	@Value("#{message['SUCCESS_CODE']}") String SUCCESS_CODE;
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@RequestMapping(value="/common/login/loginForm")
	public ModelAndView loginForm(ComMap comMap) throws Exception{
		ModelAndView mav = new ModelAndView("/common/login/loginForm");
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/common/login/login")
	public ModelAndView login(ComMap comMap) throws Exception{
		Map<String, Object> map = comMap.getMap();
		JSONObject json = new JSONObject();
		Map<String, Object> rMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		
		json = loginService.login(map);
		rMap = (Map<String, Object>) json.get("rMap");
		String rCode = (String) json.get("rCode");
		String rMsg = (String) json.get("rMsg");
		
		if(rCode.equals(SUCCESS_CODE)){
			mav = new ModelAndView("redirect:/");
		}else{
			mav.addObject("rMsg", rMsg);
			mav.setViewName("/common/login/loginForm");
		}
		
		return mav;
	}
	
}
