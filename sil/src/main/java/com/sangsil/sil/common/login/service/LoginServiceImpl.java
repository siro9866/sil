package com.sangsil.sil.common.login.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.sangsil.sil.common.common.dao.CommonDAO;
import com.sangsil.sil.common.util.UtilCommons;
import com.sangsil.sil.common.util.UtilResults;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject login(Map<String, Object> map) throws Exception {
		Map<String, Object> rMap = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		
		String queryId = "login.login";
		rMap = (Map<String, Object>) commonDAO.selectOne(queryId, map);
		json.put("rMap", rMap);
		
		if(UtilCommons.isEmpty(rMap)){
			UtilResults.setReturnCodeFail(json, "아이디가 없습니다.");
		}else{
			logger.debug("아이디가 있습니다.");
			if(UtilCommons.isEmpty(rMap.get("p_pw"))){
				UtilResults.setReturnCodeFail(json, "비밀번호가 없습니다.");
			}else if(map.get("user_pw").equals(rMap.get("p_pw"))){
				UtilResults.setReturnCodeSuc(json);
			}else{
				UtilResults.setReturnCodeFail(json, "계정정보 불일치!");
			}
		}
		
		return json; 
	}
	
}
