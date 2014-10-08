package com.coomia.service.impl;

import com.coomia.dao.IBaseDao;
import com.coomia.model.DoubleBall;
import com.coomia.service.IForcastService;

public class ForcastService implements IForcastService {

	private IBaseDao baseDao;
	
	
	
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public void save(String forcast) {
		String sql = "insert into tb_forcast(forcast) values (?)";
		Object[] params = new Object[]{forcast};
		baseDao.update(sql, params);
	}


	@Override
	public void save(DoubleBall current) {
		String sql = "insert into  tb_doubleball(period, value, type) values (?, ?, ?)";
		for (int red: current.getBallNum().getReds())
		{
			Object[] params = new Object[]{current.getNum(), red, 1};
			baseDao.update(sql, params);
		}
		Object[] params = new Object[]{current.getNum(), current.getBallNum().getBlue(), 2};
		baseDao.update(sql, params);
		
	}

}
