package com.coomia.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.model.DoubleBall;

public interface IDoubleBallService {
	
	/**
	 * 抓取所有数据并保存。
	 * @return
	 */
	List<DoubleBall> crawlAll();
	
	List<DoubleBall> findByYear(int year);

	List<DoubleBall> findByInterval(int start, int end);
	
	/**
	 * 根据历年的数据，找到每个球的互斥球
	 * @return
	 */
	Map<Integer, Set<Integer>> findMutex();
	
}
