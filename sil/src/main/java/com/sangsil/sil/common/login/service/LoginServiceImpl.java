package com.sangsil.sil.common.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sangsil.sil.common.common.dao.CommonDAO;
import com.sangsil.sil.common.util.UtilFiles;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	Logger log = Logger.getLogger(this.getClass());
	
	@Value("#{config['TABLESC']}") String TABLESC;
	@Value("#{config['TABLE_T_FAVORITY']}") String TABLE_T_FAVORITY;
	@Value("#{config['FILEUPLOADPATH_SAMPLE']}") String FILEUPLOADPATH;
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name="fileUtils")
	private UtilFiles fileUtils;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> boardList(Map<String, Object> map) throws Exception {
		String queryId = "board.list";
		return (Map<String, Object>) commonDAO.selectPagingList(queryId, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectBoardListA(Map<String, Object> map) throws Exception {
		String queryId = "board.list";
		return (List<Map<String, Object>>) commonDAO.selectPagingAList(queryId, map);
	}
	
	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		// 글 저장
		String queryId = "board.insert";
		commonDAO.insert(queryId, map);

		// 파일저장
		String queryId_file = "board.insertFile";
		map.put("fileUploadPath", FILEUPLOADPATH);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			commonDAO.insert(queryId_file, list.get(i));
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		// 조회수 추가
		String queryIdUpdate = "board.updateHitCnt";
		commonDAO.update(queryIdUpdate, map);
		
		// 상세내역 가져오기
		String queryId = "board.detail";
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = (Map<String, Object>) commonDAO.selectOne(queryId, map);
		resultMap.put("map", tempMap);

		// 파일내역 가져오기
		String queryId_files = "board.selectFileList";
		map.put("board_id", tempMap.get("favority_id"));
		List<Map<String,Object>> list = (List<Map<String, Object>>) commonDAO.selectList(queryId_files, map);
		resultMap.put("list", list);
		
		return resultMap;

	}

	@Override
	public void boardUpdate(Map<String, Object> map, HttpServletRequest request) throws Exception {
		String queryId = "board.update";
		commonDAO.update(queryId, map);
		
		String queryId_file="board.deleteFileList";
		map.put("board_id", map.get("favority_id"));
		map.put("fileUploadPath", FILEUPLOADPATH);
		commonDAO.delete(queryId_file, map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				queryId_file = "board.insertFile";
				commonDAO.insert(queryId_file, tempMap);
			}
			else{
				queryId_file = "board.updateFile";
				commonDAO.update(queryId_file, tempMap);
			}
		}
	}

	@Override
	public void boardDelete(Map<String, Object> map) throws Exception {
		String queryId = "board.delete";
		commonDAO.update(queryId, map);
		
	}
}
