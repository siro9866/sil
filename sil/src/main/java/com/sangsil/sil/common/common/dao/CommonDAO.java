package com.sangsil.sil.common.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangsil.sil.common.util.ComMap;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO {

	public Object insert(String queryId, ComMap commandMap) {
		return super.insert(queryId, commandMap);
	}

	public Object update(String queryId, ComMap commandMap) {
		return super.update(queryId, commandMap);
	}

	public Object delete(String queryId, ComMap commandMap) {
		return super.delete(queryId, commandMap);
	}

	public ComMap selectOne(String queryId) {
		return (ComMap) super.selectOne(queryId);
	}

	public ComMap selectOne(String queryId, ComMap commandMap) {
		return (ComMap) super.selectOne(queryId, commandMap);
	}

	@SuppressWarnings("unchecked")
	public List<ComMap> selectList(String queryId) {
		return (List<ComMap>) super.selectList(queryId);
	}

	@SuppressWarnings("unchecked")
	public List<ComMap> selectList(String queryId, ComMap commandMap) {
		return (List<ComMap>) super.selectList(queryId, commandMap);
	}

}
