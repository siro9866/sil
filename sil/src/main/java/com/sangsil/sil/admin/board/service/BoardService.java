package com.sangsil.sil.admin.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {
	
	Map<String,Object> boardList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectBoardListA(Map<String, Object> map) throws Exception;
	void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;
	void boardUpdate(Map<String, Object> map, HttpServletRequest request) throws Exception;
	void boardDelete(Map<String, Object> map) throws Exception;

}
