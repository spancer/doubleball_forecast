package com.coomia.service;

import com.coomia.model.DoubleBall;


public interface IForcastService {
	void save(String forcast);
	
	void save(DoubleBall current);
}
