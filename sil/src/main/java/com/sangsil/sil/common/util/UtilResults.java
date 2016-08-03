package com.sangsil.sil.common.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class UtilResults {
	static Logger logger = Logger.getLogger(UtilResults.class);
	
	public static final String SUCCESS_CODE = "2000";
	public static final String SUCCESS_MSG = "성공";
	public static final String FAIL_CODE = "9999";
	public static final String FAIL_MSG = "실패";
	
	
	/**
	 * 성공값 리턴
	 * @param json
	 */
	@SuppressWarnings("unchecked")
	public static void setReturnCodeSuc(JSONObject json){
		json.put("rCode", SUCCESS_CODE);
		json.put("rMsg", SUCCESS_MSG);
	}
	
	/**
	 * 오류는 아니지만 특별한 메세지가 필요할 경우
	 */
	@SuppressWarnings("unchecked")
	public static void setReturnCodeSuc(JSONObject json, String rMsg){
		json.put("rCode", SUCCESS_CODE);
		json.put("rMsg", rMsg);
	}
	
	/**
	 * 실패 값 리턴
	 * @param json
	 */
	@SuppressWarnings("unchecked")
	public static void setReturnCodeFail(JSONObject json){
		json.put("rCode", FAIL_CODE);
		json.put("rMsg", FAIL_MSG);
	}
	
	/**
	 * Exception 내용
	 * @param json
	 */	
	@SuppressWarnings("unchecked")
	public static void setReturnCodeFail(JSONObject json, Exception e){
		json.put("rCode", FAIL_CODE);
		json.put("rMsg", FAIL_MSG);
		json.put("rReason", e.toString());
		logger.info("FAIL_PROCESS:"+json.toJSONString());
	}

	/**
	 * 실패사유전
	 * @param json
	 * @param str
	 */
	@SuppressWarnings("unchecked")
	public static void setReturnCodeFail(JSONObject json, String str){
		json.put("rCode", FAIL_CODE);
		json.put("rMsg", FAIL_MSG);
		json.put("rReason", str);
		logger.info("FAIL_PROCESS:"+json.toJSONString());
	}
	
}
