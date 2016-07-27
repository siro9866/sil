package com.sangsil.sil.common.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sangsil.sil.common.dao.CommonDAO;

@Service("commonService")
public class CommonServiceImple implements CommonService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		String queryId = "common.selectFileInfo";
		return (Map<String, Object>) commonDAO.selectOne(queryId, map);

	}

	@Override
	public String getAutoIncreamentId(Map<String, String> map) throws Exception {
		// Mysql sequence 대신 오토인크리스값 가져옴
		String autoIncreamentId = "";
		String queryId_seq = "common.autoSeq";
		map.put("tableSC", map.get("tablesc"));
		map.put("tableNM", map.get("table_t_favority"));
		autoIncreamentId = (String) commonDAO.selectOne(queryId_seq, map);
		return autoIncreamentId; 
	}

}
