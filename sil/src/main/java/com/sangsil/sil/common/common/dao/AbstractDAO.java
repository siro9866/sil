package com.sangsil.sil.common.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sangsil.sil.common.paging.pagination.PaginationInfo;

public class AbstractDAO {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if(logger.isDebugEnabled()){
			logger.debug("\t @@@@@@@@@@@@@@@@ QueryId:" + queryId);
		}
	}
	
	public Object insert(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId){
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId,params);
	}

	/**
	 * 전자정부프레임워크 기능 사용
	 * Paging 사용하는 리스트
	 * @param queryId
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectPagingList(String queryId, Object params){
		printQueryId(queryId);
		
		Map<String,Object> map = (Map<String,Object>)params;
		PaginationInfo paginationInfo = null;
		
		if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
			map.put("currentPageNo","1");
		
		paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
		if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
			paginationInfo.setRecordCountPerPage(15);
			map.put("PAGE_ROW",15);
		}
		else{
			paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
		}
		paginationInfo.setPageSize(10);
		
		int start = paginationInfo.getFirstRecordIndex();
		int end = start + paginationInfo.getRecordCountPerPage();
		map.put("START",start);
		map.put("END",end - 1);
		
		params = map;
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = sqlSession.selectList(queryId,params);
		
		if(list.size() == 0){
			map = new HashMap<String,Object>();
			map.put("TOTAL_COUNT",0);  
			list.add(map);
			
			if(paginationInfo != null){
				paginationInfo.setTotalRecordCount(0);
				returnMap.put("paginationInfo", paginationInfo);
			}
		}
		else{
			if(paginationInfo != null){
				paginationInfo.setTotalRecordCount((int) Double.parseDouble(list.get(0).get("TOTAL_COUNT").toString()));
				returnMap.put("paginationInfo", paginationInfo);
			}
		}
		returnMap.put("result", list);
		return returnMap;
	}

	/**
	 * Ajax json 사용 페이징 리스트
	 * @param queryId
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object selectPagingAList(String queryId, Object params){
		printQueryId(queryId);
		Map<String,Object> map = (Map<String,Object>)params;
		
		String strPageIndex = (String)map.get("PAGE_INDEX");
		String strPageRow = (String)map.get("PAGE_ROW");
		int nPageIndex = 0;
		int nPageRow = 20;
		
		if(StringUtils.isEmpty(strPageIndex) == false){
			nPageIndex = Integer.parseInt(strPageIndex)-1;
		}
		if(StringUtils.isEmpty(strPageRow) == false){
			nPageRow = Integer.parseInt(strPageRow);
		}
		map.put("START", (nPageIndex * nPageRow));
		map.put("END", (nPageIndex * nPageRow) + nPageRow - 1);
		
		return sqlSession.selectList(queryId, map);
	}
	
	
}
