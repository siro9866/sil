package com.sangsil.sil.common.login.service;

import java.util.Map;

import org.json.simple.JSONObject;

public interface LoginService {
	
	JSONObject login(Map<String, Object> map) throws Exception;

}
