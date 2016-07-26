package com.sangsil.sil.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sangsil.sil.common.dao.CommonDAO;
import com.sangsil.sil.common.util.UtilFiles;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{

	Logger log = Logger.getLogger(this.getClass());
	
	final String BOARD_ID = "1234";
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name="fileUtils")
	private UtilFiles fileUtils;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> selectBoardList(Map<String, Object> map) throws Exception {
		String queryId = "sample.list";
		return (Map<String, Object>) commonDAO.selectPagingList(queryId, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectBoardListA(Map<String, Object> map) throws Exception {
		String queryId = "sample.list";
		return (List<Map<String, Object>>) commonDAO.selectPagingAList(queryId, map);
	}
	
	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		String queryId = "sample.insert";
		commonDAO.insert(queryId, map);
		
		//임시-파일저장시 아이디를 알아야 하는데 오토인크리라 코드 추가되어야함
		map.put("board_id", BOARD_ID);
		
		
		String queryId_file = "sample.insertFile";
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			commonDAO.insert(queryId_file, list.get(i));
		}
		
	}

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		// 조회수 추가
		String queryIdUpdate = "sample.updateHitCnt";
		commonDAO.update(queryIdUpdate, map);
		
		// 상세내역 가져오기
		String queryId = "sample.detail";
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = (Map<String, Object>) commonDAO.selectOne(queryId, map);
		resultMap.put("map", tempMap);

		// 파일내역 가져오기
		String queryId_files = "sample.selectFileList";
		map.put("board_id", BOARD_ID);
		List<Map<String,Object>> list = (List<Map<String, Object>>) commonDAO.selectList(queryId_files, map);
		resultMap.put("list", list);
		
		return resultMap;

	}

	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		String queryId = "sample.update";
		commonDAO.update(queryId, map);
		
		map.put("board_id", BOARD_ID);
		String queryId_file="sample.deleteFileList";
		commonDAO.delete(queryId_file, map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				queryId_file = "sample.insertFile";
				commonDAO.insert(queryId_file, tempMap);
			}
			else{
				queryId_file = "sample.updateFile";
				commonDAO.update(queryId_file, tempMap);
			}
		}
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		String queryId = "sample.delete";
		commonDAO.update(queryId, map);
		
	}
}
