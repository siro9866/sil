package com.sangsil.sil.common.common.service;

import java.util.Map;

public interface CommonService {
	
	Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	String getAutoIncreamentId(Map<String, String> map) throws Exception;
	
}
