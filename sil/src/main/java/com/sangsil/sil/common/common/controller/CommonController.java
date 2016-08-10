package com.sangsil.sil.common.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sangsil.sil.common.common.service.CommonService;
import com.sangsil.sil.common.util.ComMap;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;

	@Autowired
	BCryptPasswordEncoder passwordEncode;
	
	/**
	 * 파일 인클루드
	 * @param comMap
	 * @return
	 */
	@RequestMapping("/include")
	public ModelAndView include(ComMap comMap){
		ModelAndView mv = new ModelAndView((String) comMap.get("fileName"));
		mv.addObject("depth1", comMap.get("depth1"));
		mv.addObject("depth2", comMap.get("depth2"));
		return mv;
	}
	
	/**
	 * 파일다운로드
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(ComMap commandMap, HttpServletResponse response) throws Exception{
		Map<String,Object> map = commonService.selectFileInfo(commandMap.getMap());
		String file_name = (String)map.get("file_name");
		String originalFileName = (String)map.get("originalFileName");
		String path_name = (String)map.get("path_name");
		
		// 읽어들인 파일을 바이트로 변환: commons-io와 commons-fileupload dependency
		byte fileByte[] = FileUtils.readFileToByteArray(new File(path_name+file_name));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	/**
	 * 패스워드 암호화 테스트
	 * http://localhost:8080/common/encode.do?user_pw=1
	 * @param commandMap
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/common/encode.do")
	@ResponseBody
	public JSONObject encode(ComMap commandMap, HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		Map<String,Object> map = commandMap.getMap();
		String pw_param = (String)map.get("user_pw");
		String pw_enc = passwordEncode.encode(pw_param);
		
		log.debug("pw_param:"+ pw_param);
		log.debug("pw_enc:"+ pw_enc);
		
		json.put("pw_param", pw_param);
		json.put("pw_enc", pw_enc);
		
		return json;
	}
	
}
