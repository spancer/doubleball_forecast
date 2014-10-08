package com.coomia.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IBaseDao {
	
	public int getCount(String sql);
	
	public String getResultValue(String sql, String column);

	public List getResult(String sql);

	public void update(String sql);
	public void update(String sql,Object[] params);

	public void delete(String sql);
	
	public JdbcTemplate getJt();

}