package com.coomia.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.coomia.crawler.CoomiHttpFetcher;
import com.coomia.model.DoubleBall;
import com.coomia.service.IDoubleBallService;
import com.coomia.util.DoubleBallConstant;

public class DoubleBallService implements IDoubleBallService {

	List<DoubleBall> allBalls = new ArrayList<DoubleBall>();
	@Override
	public List<DoubleBall> crawlAll() {
		List<DoubleBall> allBalls = new ArrayList<DoubleBall>();		
		int year =2003;
		int start = Integer.parseInt(year+"001");
		int end = Integer.parseInt(year+"999");
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		while (year <= endYear) 
		{
			String url = "http://zx.caipiao.163.com/trend/ssq_basic.html?beginPeriod="+start+"&endPeriod="+end+"&year="+year;
		 	String domain = "http://zx.caipiao.163.com";
		 	Map<String, String> cookies = CoomiHttpFetcher.initVisit(domain);
		 	String body = CoomiHttpFetcher.getBodyUsingJsoup(url, domain, cookies, null);
		 	Document doc = Jsoup.parse(body);
		 	Elements rows = doc.select("tbody#cpdata tr");
		 	for (Element row : rows)
		 	{
		 		if(row.select("td").size() > 7)
		 		{
		 			DoubleBall db = new DoubleBall();
					db.setNum(Integer.parseInt(row.select("td").first().text()));
					Elements balls01 = row.select("td.chartBall07");
					for (Element ball : balls01) 
						db.getBallNum().getReds().add(convert(ball.text()));
					Elements balls07 = row.select("td.chartBall01");
					for (Element ball07 : balls07) 
						db.getBallNum().getReds().add(convert(ball07.text()));
					db.getBallNum().setBlue(convert(row.select("td.chartBall02").text()));
					allBalls.add(db);
		 		}
		 		
			}
		 	year ++;
		}
		this.allBalls = allBalls;
		return allBalls;
	}
	
	//将 数字前面的0 去掉。
	public  int convert(String ball)
	{
		if(null == ball || ball.trim().equals(""))
			return 0;
		if(ball.startsWith("0"))
			return Integer.parseInt(ball.substring(1, ball.length()));
		else
			return Integer.parseInt(ball);
	}

	@Override
	public List<DoubleBall> findByYear(int year) {
		List<DoubleBall> range = new ArrayList<DoubleBall>();
		List<DoubleBall> balls = crawlAll();
		for (DoubleBall doubleBall : balls)
		{
			if(String.valueOf(doubleBall.getNum()).startsWith(String.valueOf(year)))
				range.add(doubleBall);
		}
		return range;
	}

	@Override
	public List<DoubleBall> findByInterval(int start, int end) {
		List<DoubleBall> balls = crawlAll();
		List<Integer> nums = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) 
		{
			nums.add(i);
		}
		List<DoubleBall> range = new ArrayList<DoubleBall>();
		for (DoubleBall doubleBall : balls)
		{
			if(nums.contains(doubleBall.getNum()))
				range.add(doubleBall);
		}
		return range;
	}

	@Override
	public Map<Integer, Set<Integer>> findMutex() {
		Map<Integer, Set<Integer>> mutex = new HashMap<Integer, Set<Integer>>();
		List<DoubleBall> balls = crawlAll();
		for (int i = 1; i <=33; i++) 
		{
			Set<Integer> contains = new HashSet<Integer>();
			for (DoubleBall doubleBall : balls)
			{
				if(doubleBall.getBallNum().getReds().contains(i))
				{
					contains.addAll(doubleBall.getBallNum().getReds());
				}
			}
			Set<Integer> mutexSet = new HashSet<Integer>(DoubleBallConstant.allReds());
			mutexSet.removeAll(contains);
			mutex.put(i, mutexSet);
		}
		return mutex;
	}

	

}
