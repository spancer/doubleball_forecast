package com.coomia.crawler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.coomia.model.BallNum;
import com.coomia.model.BallRule;
import com.coomia.model.DoubleBall;
import com.coomia.model.ForcastBall;
import com.coomia.util.DoubleBallConstant;
import com.coomia.util.FormulaParser;


public class CrawlBalls {


	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		//1. crawl balls info.
		int endYear = 2014;
		List<DoubleBall> allBalls = crawlAll(endYear);
		//打印各年份的数据概率
		for(int startYear = 2014; startYear <= endYear; startYear++)
		{
			List<DoubleBall> yearBalls = rangeBalls(allBalls, startYear);
			double avgOddRate = avgOddRate(yearBalls);
			System.out.println("年份："+startYear + " 偶数球的平均概率是:" +avgOddRate);
			
			double small = avgRateBalls(yearBalls, 0, 11);
			System.out.println("小球 - 年份 -"+startYear +" 概率-"+small);
			
			small = avgRateBalls(yearBalls, 12, 22);
			System.out.println("中球 - 年份 -"+startYear +" 概率-"+small);
			
			small = avgRateBalls(yearBalls, 23, 33);
			System.out.println("大球 - 年份 -"+startYear +" 概率-"+small);
			
			small = avgRateBlueInRed(yearBalls);
			System.out.println("蓝球 在 红球中 - 年份 -"+startYear +" 概率-"+small);
			
			boolean blue = true;
			Map<String, Integer> blueMap = countNum(yearBalls, blue);
			List<Map.Entry<String, Integer>> l = new ArrayList<Map.Entry<String, Integer>>(blueMap.entrySet());

			Collections.sort(l, new Comparator<Map.Entry<String, Integer>>()
			{
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
				{
					return (o2.getValue() - o1.getValue());
				}
			});
			for (Map.Entry<String, Integer> e : l)
			{
				if(blue)
					System.out.println("蓝球:" + e.getKey() + "-["+startYear+"年]-频次：" + e.getValue());
				else
					System.out.println("红球:" + e.getKey() + "-["+startYear+"年]-频次：" + e.getValue());
			}
			
			blue = false;
			Map<String, Integer> redMap = countNum(yearBalls, blue);
			l = new ArrayList<Map.Entry<String, Integer>>(redMap.entrySet());

			Collections.sort(l, new Comparator<Map.Entry<String, Integer>>()
			{
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
				{
					return (o2.getValue() - o1.getValue());
				}
			});
			for (Map.Entry<String, Integer> e : l)
			{
				if(blue)
					System.out.println("蓝球:" + e.getKey() + "-["+startYear+"年]-频次：" + e.getValue());
				else
					System.out.println("红球:" + e.getKey() + "-["+startYear+"年]-频次：" + e.getValue());
			}
		}
		
		for(int startYear = 2014001; startYear <= 2014104; startYear+=10)
		{
			List<DoubleBall> yearBalls = rangeBalls(allBalls, startYear, startYear + 9);
			double avgOddRate = avgOddRate(yearBalls);
			System.out.println("区间："+startYear+"~"+(startYear+9) + " 偶数球的平均概率是:" +avgOddRate);
			
			double small = avgRateBalls(yearBalls, 0, 11);
			System.out.println("小球 - 区间 -"+startYear+"~"+(startYear+9) +" 概率-"+small);
			
			small = avgRateBalls(yearBalls, 12, 22);
			System.out.println("中球 - 区间 -"+startYear+"~"+(startYear+9) +" 概率-"+small);
			
			small = avgRateBalls(yearBalls, 23, 33);
			System.out.println("大球 - 区间 -"+startYear+"~"+(startYear+9) +" 概率-"+small);
			
			small = avgRateBlueInRed(yearBalls);
			System.out.println("蓝球 在 红球中 - 区间 -"+startYear+"~"+(startYear+9) +" 概率-"+small);
			
			boolean blue = true;
			Set<String> totalNum = new HashSet<String>();
			Map<String, Integer> blueMap = countNum(yearBalls, blue);
			List<Map.Entry<String, Integer>> l = new ArrayList<Map.Entry<String, Integer>>(blueMap.entrySet());

			Collections.sort(l, new Comparator<Map.Entry<String, Integer>>()
			{
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
				{
					return (o2.getValue() - o1.getValue());
				}
			});
		
			for (Map.Entry<String, Integer> e : l)
			{			
				totalNum.add(e.getKey());
				if(blue)
					System.out.println("蓝球:" + e.getKey() + "-["+startYear+"~"+(startYear+9)+"]-频次：" + e.getValue());
				else
					System.out.println("红球:" + e.getKey() + "-["+startYear+"~"+(startYear+9)+"]-频次：" + e.getValue());
			}
			
			if(blue)
				System.out.println("["+startYear+"~"+(startYear+9)+"] 共开出蓝球数：" +l.size() );
			else
				System.out.println("["+startYear+"~"+(startYear+9)+"] 共开出红球数：" +l.size() );
			
			blue = false; 
			Map<String, Integer> redMap = countNum(yearBalls, blue);
			l = new ArrayList<Map.Entry<String, Integer>>(redMap.entrySet());

			Collections.sort(l, new Comparator<Map.Entry<String, Integer>>()
			{
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
				{
					return (o2.getValue() - o1.getValue());
				}
			});
			
			for (Map.Entry<String, Integer> e : l)
			{
				totalNum.add(e.getKey());
				if(blue)
					System.out.println("蓝球:" + e.getKey() + "-["+startYear+"~"+(startYear+9)+"]-频次：" + e.getValue());
				else
					System.out.println("红球:" + e.getKey() + "-["+startYear+"~"+(startYear+9)+"]-频次：" + e.getValue());
			}
			
			if(blue)
				System.out.println("["+startYear+"~"+(startYear+9)+"] 共开出蓝球数：" +l.size() );
			else
				System.out.println("["+startYear+"~"+(startYear+9)+"] 共开出红球数：" +l.size() );
			System.out.println("["+startYear+"~"+(startYear+9)+"] 共开出球数：" +totalNum.size() );
			double avgSmall = avgsum(yearBalls, 1, 11);
			System.out.println("["+startYear+"~"+(startYear+9)+"] 小球平均值：" +avgSmall );
			
			avgSmall = avgsum(yearBalls, 12, 22);
			System.out.println("["+startYear+"~"+(startYear+9)+"] 中球平均值：" +avgSmall );
			
			avgSmall = avgsum(yearBalls, 23, 33);
			System.out.println("["+startYear+"~"+(startYear+9)+"] 大球平均值：" +avgSmall );
			
			double shouldKeepRate = rateRedShouldKeep(yearBalls);
			System.out.println("["+startYear+"~"+(startYear+9)+"] 可能会出现在下期红球中的概率为：" +shouldKeepRate );
			
			double shouldBlueKeepRate = rateBlueShouldKeep(yearBalls);
			System.out.println("["+startYear+"~"+(startYear+9)+"] 可能会出现在下期蓝球中的概率为：" +shouldBlueKeepRate );
			
		}
		//选取某一年的数据进行计算。
		int doyear = 2014;
		List<DoubleBall> balls = rangeBalls(allBalls, doyear);
		
		//3. 定义一组公式算概率
		//3.1 定义一组公式， 因为红 与 蓝 球 的公式的准确率可能不一样。
		//因此，将表达式式的作用范围分为红和蓝。
		List<String> red_expressions = new ArrayList<String>();
		List<String> blue_expressions = new ArrayList<String>();
		red_expressions.add("(#{red6}-#{red1})@kill-red"); //1
		red_expressions.add("#{red3}-#{red2}@kill-red"); //2
		red_expressions.add("#{red5}-#{red2}@kill-red"); //3
		red_expressions.add("(#{red1}-4)*2@kill-red"); //4
		red_expressions.add("(#{red1}+#{blue})*3@kill-red"); //5
		red_expressions.add("#{red1}+9@kill-red"); //6
		red_expressions.add("#{red2}+5@kill-red");//7
		red_expressions.add("#{red3}+4@kill-red"); //8
		red_expressions.add("#{red3}+7@kill-red"); //9
		red_expressions.add("#{red6}+4@kill-red"); //10
		red_expressions.add("#{red5}-#{red4}+#{blue}+1@kill-red");//11
		red_expressions.add("(#{red1}+#{red2})@kill-red");
		red_expressions.add("#{blue}+#{red1}@kill-red");//18
		red_expressions.add("#{blue}+#{red2}-1@kill-red"); //19
		red_expressions.add("#{blue}-#{red4}+1@kill-red"); //20
		red_expressions.add("#{blue}-#{red5}@kill-red");
		red_expressions.add("#{blue}*#{red1}@kill-red");
		red_expressions.add("#{red5}+5@kill-red");
		red_expressions.add("#{red5}@kill-red");
		red_expressions.add("22-#{red2}@kill-red");
		red_expressions.add("54-#{red5}@kill-red");
		red_expressions.add("#{red1}+#{red2}@kill-red");
		red_expressions.add("#{red1}+#{red3}@kill-red");
		red_expressions.add("#{blue}+7@kill-red");
		red_expressions.add("#{blue}+9@kill-red");
		red_expressions.add("#{blue}*5+2@kill-red");//当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。
		red_expressions.add("#{blue}*2+2@kill-red");//当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出，到目前为止，此条件无错误
		red_expressions.add("#{red1}*4-2@kill-red");
		red_expressions.add("(#{red1}+#{red3}+#{red5})/2@kill-red");
		red_expressions.add("(#{red6}-#{blue})@kill-red");
		red_expressions.add("(#{red1}*3)/2@kill-red");
		
		
		//3.2取 概率大于 指定值的公式
		double red_ratio = 0.8;
		double blue_ratio = 0.8;
		//上期
		int index = balls.size() -1;
		DoubleBall current = balls.get(index);
		DoubleBall beforeCurrent = balls.get(index -1);
		DoubleBall earlierCurrent = balls.get(index-2);
		//有用的表达式， 大于 指定概率阈值的均为有效表达式。
		//红色的有效表达式。
		List<String> red_efficient = effectExperssions(red_expressions, balls, red_ratio); 
				
		//装载结果的对象
		ForcastBall forcast = new ForcastBall(current.getNum() +1);
		//红球预测后结果
		forcast = forecast(current, red_efficient, forcast);
			
		
		//再殺红球 
		//2. 特殊的概率
		double r1 = specialRateRed01(balls);
		System.out.println("当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出"+r1);
		double r2 = specialRateRed02(balls);
		System.out.println("当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。"+r2);
		double r3 = specialRateRed03(balls);
		System.out.println("开奖红号按从小到大排序(第三位+第六位)除以33的余数+14,如果结果大于33,就减去33。"+r3);
		double r4 = specialRateRed04(balls);
		System.out.println("上期红1位尾加上期红2位尾。 "+r4);
		
		double r5 = specialRateRed05(balls);
		System.out.println("上期蓝码+上上期蓝码 "+r5);
		int r5kill = beforeCurrent.getBallNum().getBlue() + current.getBallNum().getBlue();
		
		double r6 = specialRateRed06(balls);
		System.out.println("33-上上期蓝码 "+r6);
		int r6kill = 33 - beforeCurrent.getBallNum().getBlue();
		
		double r7 = specialRateRed07(balls);
		System.out.println("上上期蓝码 "+r7);
		int r7kill =  beforeCurrent.getBallNum().getBlue();
		
		double r8 = specialRateRed08(balls);
		System.out.println("33-上期蓝码 "+r8);
		int r8kill =  33-current.getBallNum().getBlue();
		
		double r9 = specialRateRed09(balls);
		System.out.println("上期蓝码 "+r9);
		int r9kill =  current.getBallNum().getBlue();
		
		double r10 = specialRateRed10(balls);
		System.out.println("上期红1位尾加上期红2位尾 "+r10);
		int r10kill =  calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(0), true) + calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(1), true);
		
		forcast = killByRed01(forcast, current, red_ratio, r1);
		forcast = killByRed02(forcast, current, red_ratio, r2);
		forcast = killByRed03(forcast, current, red_ratio, r3);
		forcast = killByRed04(forcast, current, red_ratio, r4);
		forcast = killByRed(forcast, r5kill, red_ratio, r5);
		forcast = killByRed(forcast, r6kill, red_ratio, r6);
		forcast = killByRed(forcast, r7kill, red_ratio, r7);
		forcast = killByRed(forcast, r8kill, red_ratio, r8);
		forcast = killByRed(forcast, r9kill, red_ratio, r9);
		forcast = killByRed(forcast, r10kill, red_ratio, r10);
		
		//蓝色
		blue_expressions.add("15-#{blue}@kill-blue");// 1
		blue_expressions.add("19-#{blue}@kill-blue"); // 2
		blue_expressions.add("21-#{blue}@kill-blue"); // 3
		blue_expressions.add("#{blue}*2@kill-blue"); // 8
		blue_expressions.add("#{blue}+2@kill-blue"); //11
		blue_expressions.add("#{blue}+6@kill-blue"); //12
		
		//蓝色的有效表达式。
		List<String> blue_efficient = effectExperssions(blue_expressions, balls, blue_ratio); 
		forcast = forecast(current, blue_efficient, forcast);
		
		//蓝球特殊 //4
		double b1 = specialRateBlue01(balls);
		System.out.println(b1);
		int b1kill = calTail(calTailRelated(beforeCurrent.getBallNum().getBlue(), false, current.getBallNum().getBlue(), true), true);
		forcast = killByBlueTail(forcast, blue_ratio, b1, b1kill);
		//5
		double b2 = specialRateBlue02(balls);
		System.out.println(b2);
		int b2kill = calTail(calTailRelated(beforeCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), false), true);
		forcast = killByBlueTail(forcast, blue_ratio, b2, b2kill);
		
		//6
		double b3 = specialRateBlue03(balls);
		System.out.println(b3);
		int b3kill = calTail(calTailRelated(beforeCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
		forcast = killByBlueTail(forcast,  blue_ratio, b3, b3kill);
		
		// 7 
		double b4 = specialRateBlue04(balls);
		System.out.println(b4);
		int b4kill = calTail(calTailRelated(earlierCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
		forcast = killByBlueTail(forcast, blue_ratio, b4, b4kill);
		
		//9 
		double b5 = specialRateBlue05(balls);
		System.out.println(b5);
		int b5kill = calTail(calTail(current.getBallNum().getBlue(), true) * 4, true);
		forcast = killByBlueTail(forcast, blue_ratio, b5, b5kill);
		
		//10
		double b6 = specialRateBlue06(balls);
		System.out.println(b6);
		int b6kill = 0;
		if(current.getBallNum().getBlue() > 14)
			b6kill = current.getBallNum().getBlue() -7;
		else
			b6kill = current.getBallNum().getBlue() +7;
		b6kill = calTail(b6kill, true);
		forcast = killByBlueTail(forcast, blue_ratio, b6, b6kill);
		
	 	System.err.println("预测双色球第"+(current.getNum()+1)+"期："+forcast.toString());
	 	
	 	Set<Integer> shoudKeep = shouldKeepRed(current);
	 	Set<Integer> redsToAdd = new HashSet<Integer>();
	 	Set<Integer> bluesToAdd = new HashSet<Integer>();
	 	redsToAdd.addAll(shoudKeep);
	 	bluesToAdd.addAll(shoudKeep);
	 	redsToAdd.retainAll(forcast.getReds());
	 	for (Integer integer : redsToAdd) 
	 	{
	 		forcast.mustRed(integer);
		}
	 	
	 	bluesToAdd.retainAll(forcast.getBlues());
	 	for (Integer integer : bluesToAdd) 
	 	{
	 		forcast.mustBlue(integer);
		}
	 	System.err.println("预测双色球第"+(current.getNum()+1)+"期："+forcast.toString());
	 	
	 	
	}
	
	public static List<DoubleBall> crawlAll(int endYear)
	{
		List<DoubleBall> allBalls = new ArrayList<DoubleBall>();		
		int year =2003;
		int start = Integer.parseInt(year+"001");
		int end = Integer.parseInt(year+"999");
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
		return allBalls;
	}
	
	/**
	 * wo ying 网站杀红球
	 * @return
	 */
	public static Set<Integer> woyingKillRed()
	{
		Map<Integer, Integer> balls = woyingKillBall("http://www.woying.com/Lotterys/Fc/ssqsh?type=2");
		return new HashSet<Integer>(balls.keySet());
	}
	
	/**
	 * wo ying 网站杀蓝球
	 * @return
	 */
	public static Set<Integer> woyingKillBlue()
	{
		Map<Integer, Integer> balls = woyingKillBall("http://www.woying.com/Lotterys/Fc/ssqsh");
		return new HashSet<Integer>(balls.keySet());
	}
	/**
	 * wo ying kill ball
	 * @param url
	 * @return
	 */
	public static Map<Integer, Integer> woyingKillBall(String url)
	{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	 	String domain = "http://www.woying.com";
	 	Map<String, String> cookies = CoomiHttpFetcher.initVisit(domain);
	 	String body = CoomiHttpFetcher.getBodyUsingJsoup(url, domain, cookies, null);
	 	Document doc = Jsoup.parse(body);
	 	Elements rows = doc.select("tr td.right_bottom_1").first().siblingElements();
	 	for (Element element : rows) 
	 	{
	 		String killRed = element.text().trim();
	 		if(" ".equals(killRed) || "".equals(killRed))
	 			continue;
	 		int red = 0;
	 		try {
	 			red = Integer.parseInt(killRed);
			} catch (Exception e) {
			}
 			
 			if(red >= 1 && red <=33)
 			{
 				if(null != map.get(red))
 					map.put(red, map.get(red)+1);
 				else
 					map.put(red, 1);
 			}
		}
	 	return map;
	}
	/**
	 * okooo 网站杀球
	 * @param url
	 * @return
	 */
	public static Map<Integer, Integer> okoooKillBall(String url)
	{
		url = "http://www.okooo.com/shuangseqiu/ssqsh/";
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	 	String domain = "http://www.okooo.com";
	 	Map<String, String> cookies = CoomiHttpFetcher.initVisit(domain);
	 	String body = CoomiHttpFetcher.getBodyUsingJsoup(url, domain, cookies, null);
	 	Document doc = Jsoup.parse(body);
	 	Elements rows = doc.select("td.winning_red").last().siblingElements();
	 	for (Element element : rows) 
	 	{
	 		String killRed = element.text().trim();
	 		if(!"".equals(killRed))
	 		{
	 			int red = Integer.parseInt(killRed);
	 			if(red >= 1 && red <=16)
	 			{
	 				if(null != map.get(red))
	 					map.put(red, map.get(red)+1);
	 				else
	 					map.put(red, 1);
	 			}
	 		}
		}
	 	return map;
	}
	
	//计算红球 大小 在 start-end区间的球 的和
	private static int sum(DoubleBall current, int start, int end)
	{
		int total = 0;
		for (int red :current.getBallNum().getReds()) 
		{
			if(red >=start && red<= end)
				total+=red;
		}
		return total;
	}
	
	/**
	 * 返回下期中必会出的球
	 * @param current
	 * @return
	 */
	public static Set<Integer> shouldKeepRed(DoubleBall current)
	{
		Set<Integer> result = new HashSet<Integer>();
		Set<Integer> reds = current.getBallNum().getReds();
		int total = 0;
		for (Integer red : reds) 
		{
			total += red;
		}
		for (Integer red : reds) 
		{
			int divided = new Double(Math.floor(total/red)).intValue();
			String s = String.valueOf(divided);
			int tail = Integer.parseInt(s.substring(s.length() -1));
			if(tail <= 3)
				result.add(tail + 30);
			result.add(tail);
			result.add(tail + 10);
			result.add(tail + 20);
		}
		return result;
	}
	
	public static double rateRedShouldKeep(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0; //用于记录在balls中， 总共有多么个球 出现。
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			//下期有可能出现的红球列表
			Set<Integer> shouldHave = shouldKeepRed(current);
			Set<Integer> nextReds = next.getBallNum().getReds();
			shouldHave.retainAll(nextReds);
			if(shouldHave.size() > 0)
			{
				count++;
				total += shouldHave.size();
				System.err.println("有："+shouldHave.size()+" 个红球在"+next.getNum()+"期出现。");
			}
		}
		System.err.println(balls.size() +" 期中，共出现过："+total+" 个红球在下期出现。平均每期出现:"+total/(double)balls.size() +"个红球。");
		return count/(double) balls.size();
	}
	
	public static double rateBlueShouldKeep(List<DoubleBall> balls)
	{
		int count = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			//下期有可能出现的红球列表
			Set<Integer> shouldHave = shouldKeepRed(current);
			int blue = next.getBallNum().getBlue();
			if(shouldHave.contains(blue))
			{
				count++;
				System.out.println("蓝球在"+next.getNum()+"期出现。");
			}
		}
		System.err.println(balls.size() +" 期中，共出现过："+count+" 次蓝球是在上期红球中的应该出现的列表中的。");
		return count/(double) balls.size();
	}
	
	public static double avgsum(List<DoubleBall> balls, int start, int end)
	{
		int total = 0;
		for (DoubleBall doubleBall : balls) {
			total += sum(doubleBall, start, end);
		}
		System.err.println("["+start+"~"+end+"] 总个数为:"+total);
		return total / (double) balls.size();
	}

	private static double calOddSize(TreeSet<Integer> reds) {
		int count = 0;
		for (Integer integer : reds) 
		{
			if(integer %2 == 0)
				count++;
		}
		return count/(double)6;
	}
	
	//红球中 偶数球的平均概率。
	public static double avgOddRate(List<DoubleBall> balls) {
				
		double rateTotal = 0;
		for (int i = 0; i< balls.size()-1; i++)
		{			
			DoubleBall current = balls.get(i);
			rateTotal+= calOddSize(current.getBallNum().getReds()); 
		}
		return rateTotal/balls.size();
	}
	
	//蓝球在 红球中的概率
	public static double avgRateBlueInRed(List<DoubleBall> balls) {
		
		double rateTotal = 0;
		for (int i = 0; i< balls.size()-1; i++)
		{
			DoubleBall current = balls.get(i);
			if(current.getBallNum().getReds().contains(current.getBallNum().getBlue()))
				rateTotal++;
		}
		return rateTotal/balls.size();
	}
	
	
	public static TreeMap<String, Integer> countNum(List<DoubleBall> balls, boolean blue)
	{
		TreeMap<String, Integer> map = new TreeMap<String, Integer>( new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj2.compareTo(obj1);
            }
        });
		for (DoubleBall doubleBall : balls) 
		{
			String key = ""+doubleBall.getBallNum().getBlue();
			if(!blue)
			{
				for (Integer red : doubleBall.getBallNum().getReds()) 
				{
					key = ""+red;
					map.put(key, (map.get(key) == null ?0:map.get(key))+1);
				}
			}
			else
				map.put(key, (map.get(key) == null ?0:map.get(key))+1);
			
		}
		return map;
	}
	
	 
	
	/**
	 * 小球 概率 （0，11）
	 * 中球  （12， 22）
	 * 大球 （23， 33）
	 * @param balls
	 * @return
	 */
	public static double avgRateBalls(List<DoubleBall> balls, int start, int end) {
		
		int index = 0;
		double rateTotal = 0;
		for (int i = 0; i< balls.size()-1; i++)
		{
			index ++;
			DoubleBall current = balls.get(i);
			rateTotal+= calBallsRate(current.getBallNum().getReds(), start, end); 
		}
		return rateTotal/index;
	}


	private static double calBallsRate(TreeSet<Integer> reds, int start, int end) {
		int count = 0;
		for (Integer integer : reds) 
		{
			if(integer <=end && integer >= start)
				count++;
		}
		return count/(double)6;
	}


	/**
	 * 
	 * @param rules
	 * @param balls
	 * @param ratio
	 * @return
	 */
	public static List<String> effectExperssions(List<String> rules,
			List<DoubleBall> balls, double ratio) 
	{
		List<String> result = new ArrayList<String>();
		for (String exp : rules) 
		{
			String[] rule = exp.split("@");
			if(rule.length != 2)
				continue;
			String expression = rule[0].trim();
			double calRatio = caculateRate(balls, expression);
			System.out.println("公式：【"+exp +"】 概率为:"+calRatio);
			if(ratio <= calRatio)
				result.add(exp);
		}
		return result;
		
	}



	public static List<String> effectExperssions(List<String> expressions,
			List<DoubleBall> balls) 
			{
		// TODO Auto-generated method stub
		return null;
	}

	
	//取某一年的双色球结果
	public static List<DoubleBall> rangeBalls(List<DoubleBall> balls, int year)
	{
		List<DoubleBall> range = new ArrayList<DoubleBall>();
		for (DoubleBall doubleBall : balls)
		{
			if(String.valueOf(doubleBall.getNum()).startsWith(String.valueOf(year)))
				range.add(doubleBall);
		}
		return range;
	}
	//取一个区间的双色球 如2013001-2013010
	public static List<DoubleBall> rangeBalls(List<DoubleBall> balls, int start, int end)
	{
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
	//从开始位置开始， 取指定个数个球 
	public static List<DoubleBall> fixSizeBalls(List<DoubleBall> balls, int start, int size)
	{
		List<Integer> nums = new ArrayList<Integer>();
		for (int i = start; i < size; i++) 
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
	
	public static boolean validRedBall(int red)
	{
		if(red >= 1 && red <= 33)
			return true;
		else
			return false;
	}	
	
	//根据公式算球 
	public static int calBallByRule(BallNum ball, String expression)
	{
		ArrayList<Integer> reds = new ArrayList<Integer>(ball.getReds());
		//基础数据
        Map<String, BigDecimal> values = new HashMap<String, BigDecimal>();
        values.put("blue", BigDecimal.valueOf(ball.getBlue()));
        values.put("red1", BigDecimal.valueOf(reds.get(0)));
        values.put("red2", BigDecimal.valueOf(reds.get(1)));
        values.put("red3", BigDecimal.valueOf(reds.get(2)));
        values.put("red4", BigDecimal.valueOf(reds.get(3)));
        values.put("red5", BigDecimal.valueOf(reds.get(4)));
        values.put("red6", BigDecimal.valueOf(reds.get(5)));
	  
        //需要依赖的其他公式
        /**
        Map<String, String> formulas = new HashMap<String, String>();
        formulas.put("eeee", "#{red2}-#{red1}*2");
	  	//需要计算的公式
      	String expression = "#{eeee}-#{blue}";
	    **/
        BigDecimal result = FormulaParser.parse(expression, null, values);
        return result.intValue();
	}
	
	/**
	 * 预测下一期
	 * @param current 当前一期的双色球
	 * @param rules 要杀、保留的规则
	 * @return 
	 */
	public static ForcastBall forecastNext(DoubleBall current, List<BallRule> rules)
	{
		ForcastBall forcast = new ForcastBall(current.getNum()+1);
		for (BallRule ballRule : rules) 
		{
			int calball = calBallByRule(current.getBallNum(), ballRule.getRule());
			String ruleType = ballRule.getBallType();
			if(ruleType.equals("kill-red"))
				forcast.killRed(calball);
			else if(ruleType.equals("kill-blue"))
				forcast.killBlueWithTail(calball);
			else if(ruleType.equals("keep-red"))
				forcast.shouldRed(calball);
			else if(ruleType.equals("keep-blue"))
				forcast.keepBlue(calball);
		}
		
		return forcast;
		
	}
	
	public static ForcastBall forecastRed(DoubleBall current, List<String> rules, ForcastBall forcast)
	{
		for (String ballRule : rules) 
		{
			String[] rule = ballRule.split("@");
			if(rule.length != 2)
				continue;
			int calball = calBallByRule(current.getBallNum(), rule[0].trim());
			String ruleType = rule[1].trim();
			if(ruleType.equals("kill-red"))
				forcast.killRed(calball);
			else if(ruleType.equals("kill-blue"))
				forcast.killBlueWithTail(calball);
			else if(ruleType.equals("keep-red"))
				forcast.shouldRed(calball);
			else if(ruleType.equals("keep-blue"))
				forcast.keepBlue(calball);
			System.out.println(ruleType+" 【"+calball+ "】--用公式：+【"+rule[0]+"】");
		}
		
		return forcast;
		
	}
	
	public static ForcastBall forecast(DoubleBall current, List<String> rules, ForcastBall forcast)
	{
		for (String ballRule : rules) 
		{
			String[] rule = ballRule.split("@");
			if(rule.length != 2)
				continue;
			int calball = calBallByRule(current.getBallNum(), rule[0].trim());
			String ruleType = rule[1].trim();
			if(ruleType.equals("kill-red"))
				forcast.killRed(calball);
			else if(ruleType.equals("kill-blue"))
				forcast.killBlueWithTail(calball);
			else if(ruleType.equals("keep-red"))
				forcast.shouldRed(calball);
			else if(ruleType.equals("keep-blue"))
				forcast.keepBlue(calball);
			System.out.println(ruleType+" 【"+calball+ "】--用公式：+【"+rule[0]+"】");
		}
		
		return forcast;
		
	}
	
	//用有效的蓝球 杀球 公式去杀蓝球 来预测蓝球 
	public static ForcastBall forecastBlue(DoubleBall current, List<String> rules, ForcastBall forcast)
	{
		for (String ballRule : rules) 
		{
			String[] rule = ballRule.split("@");
			if(rule.length != 2)
				continue;
			int calball = calBallByRule(current.getBallNum(), rule[0].trim());
			String ruleType = rule[1].trim();
			if(ruleType.equals("kill-red"))
				forcast.killRed(calball);
			else if(ruleType.equals("kill-blue"))
				forcast.killBlueWithTail(calball);
			else if(ruleType.equals("keep-red"))
				forcast.shouldRed(calball);
			else if(ruleType.equals("keep-blue"))
				forcast.keepBlue(calball);
			System.out.println(ruleType+" 【"+calball+ "】--用公式：+【"+rule[0]+"】");
		}
		
		return forcast;
		
	}
	
	
	
	public static int calTailRelated(int before, boolean isBeforeTail, int current, boolean isCurrentTail)
	{
		int br = 0;
		int cr = 0;
		String beforeNum = before +"";
		String currentNum = current + "";
		if(before < 10) 
			beforeNum = "0"+before;
		if(current < 10)
			currentNum = "0"+ current;
		if(isBeforeTail)
			br = Integer.parseInt(beforeNum.substring(beforeNum.length()-1,beforeNum.length()));
		else
			br = Integer.parseInt(beforeNum.substring(0,1));
		if(isCurrentTail)
			cr = Integer.parseInt(currentNum.substring(currentNum.length()-1,currentNum.length()));
		else
			cr = Integer.parseInt(currentNum.substring(0,1));
		
		return br + cr;
			
			
	}
	
	//取 ball 值 的尾数， tail为true时取尾， 否则取头
	public static int calTail(int ball, boolean tail)
	{
		int br = 0;
		String beforeNum = ball +"";
		if(ball < 10) 
			beforeNum = "0"+ball;
		if(tail)
			br = Integer.parseInt(beforeNum.substring(beforeNum.length()-1,beforeNum.length()));
		else
			br = Integer.parseInt(beforeNum.substring(0,1));
		return br ;
			
			
	}
	
	//根据规则算 公式的准确度 
	public static double caculateRate(List<DoubleBall> balls, String ruleExpression)
	{
		int size = balls.size();
		int count = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = calBallByRule(current.getBallNum(), ruleExpression);
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)size;
	}
	
	//特殊杀球方式
	/**
	 * 当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出，到目前为止，此条件无错误。
	 * @param balls
	 * @return
	 */
	public static double specialRateRed01(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = 0;
			//偶数
			boolean odd = current.getBallNum().getBlue()%2 ==0;
			if(odd)
			{
				total ++;
				ball = current.getBallNum().getBlue()*2+2;
				if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
					count++;
			}
		}
		return count/(double)total;
	}
	
	public static ForcastBall killByRed01(ForcastBall ball, DoubleBall current, double ratio, double actual)
	{
		//kill while
		if(ratio < actual)
		{
			ball.killRed(current.getBallNum().getBlue()*2+2);
			System.out.println("kill-red: "+current.getBallNum().getBlue()*2+2);
		}
		return ball;
	}
	
	//当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。
	public static double specialRateRed02(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = 0;
			//偶数
			boolean odd = current.getBallNum().getBlue()%2 ==1;
			if(odd)
			{
				total ++;
				ball = current.getBallNum().getBlue()*5+2;
				if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
					count++;
			}
		}
		return count/(double)total;
	}
	public static ForcastBall killByRed02(ForcastBall ball, DoubleBall current, double ratio, double actual)
	{
		//kill while
		if(ratio < actual)
		{
			ball.killRed(current.getBallNum().getBlue()*5+2);
			System.out.println("kill-red: "+current.getBallNum().getBlue()*5+2);
		}
		return ball;
	}
	//开奖红号按从小到大排序(第三位+第六位)除以33的余数+14,如果结果大于33,就减去33。
	public static double specialRateRed03(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
			int ball = (reds.get(2)+ reds.get(5))%33 +14;
			if(ball > 33)
				ball = ball -33;
			//偶数
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	public static ForcastBall killByRed03(ForcastBall ball, DoubleBall current, double ratio, double actual)
	{
		//kill while
		if(ratio < actual)
		{
			ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
			ball.killRed((reds.get(2)+ reds.get(5))%33 +14);
		}
		return ball;
	}
	//上期红1位尾加上期红2位尾
	public static double specialRateRed04(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
			int ball = calTailRelated(reds.get(0), true, reds.get(1), true);
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	
	public static ForcastBall killByRed04(ForcastBall ball, DoubleBall current, double ratio, double actual)
	{
		//kill while
		if(ratio < actual)
		{
			ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
			ball.killRed(calTailRelated(reds.get(0), true, reds.get(1), true));
		}
		return ball;
	}
	
	//上期蓝码+上上期蓝码
	public static double specialRateRed05(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall current = balls.get(i+1);
			DoubleBall next = balls.get(i+2);
			int ball = before.getBallNum().getBlue() + current.getBallNum().getBlue();
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	
	public static double specialRateRed06(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall next = balls.get(i+2);
			int ball = 33 - before.getBallNum().getBlue();
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	public static double specialRateRed07(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall next = balls.get(i+2);
			int ball = before.getBallNum().getBlue();
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	public static double specialRateRed08(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = 33 - current.getBallNum().getBlue();
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	
	public static double specialRateRed09(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = before.getBallNum().getBlue();
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	public static double specialRateRed10(List<DoubleBall> balls)
	{
		int count = 0;
		int total = balls.size();
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(0), true) + calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(1), true);
			if(validRedBall(ball) && (!next.getBallNum().getReds().contains(ball)))
				count++;
		}
		return count/(double)total;
	}
	
	public static ForcastBall killByRed(ForcastBall ball,int kill, double ratio, double actual)
	{
		//kill while
		if(ratio < actual)
		{
			ball.killRed(kill);
		}
		return ball;
	}
	
	////4、用上两期蓝号的头和尾相加的数即为下期要杀的蓝号尾数。
	public static double specialRateBlue01(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int ball = calTail(calTailRelated(current.getBallNum().getBlue(), false, next.getBallNum().getBlue(), true), true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(nextNext.getBallNum().getBlue() != ball || nextNext.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("4、用上两期蓝号的头和尾相加的数即为下期要杀的蓝号尾数。");
		return count/(double)total;
	}

	
	//5、用上两期蓝号的尾和头相加的数即为下期要杀的尾数。
	public static double specialRateBlue02(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall current= balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int ball = calTail(calTailRelated(before.getBallNum().getBlue(), true, current.getBallNum().getBlue(), false), true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(nextNext.getBallNum().getBlue() != ball || nextNext.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("5、用上两期蓝号的尾和头相加的数即为下期要杀的尾数。 ");
		return count/(double)total;
	}
	
	 
	//6、用上二期蓝号尾相加得出的数就是下期要杀的尾数。
	public static double specialRateBlue03(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall before = balls.get(i);
			DoubleBall current= balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int ball = calTail(calTailRelated(before.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(nextNext.getBallNum().getBlue() != ball || nextNext.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("6、用上二期蓝号尾相加得出的数就是下期要杀的尾数。");
		return count/(double)total;
	}
	//7、用上期蓝号尾与隔一期蓝号尾相加得出的数即为下期要杀的尾数。
	public static double specialRateBlue04(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-4; i++)
		{
			DoubleBall early = balls.get(i);
			DoubleBall current = balls.get(i+2);
			DoubleBall next = balls.get(i+3); 
			int ball = calTail(calTailRelated(early.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(next.getBallNum().getBlue() != ball || next.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("7、用上期蓝号尾与隔一期蓝号尾相加得出的数即为下期要杀的尾数。："+ count/(double)total);
		return count/(double)total;
	}
	
	//9、用上期蓝号尾乘4得出的数即是下期要杀的尾数。
	public static double specialRateBlue05(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = calTail(calTail(current.getBallNum().getBlue(), true) * 4, true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(next.getBallNum().getBlue() != ball || next.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("9、用上期蓝号尾乘4得出的数即是下期要杀的尾数。");
		return count/(double)total;
	}
	
	//10、用上期蓝号加7或减7，注意蓝号大于14则减7，小于14则加7，得出的数即为下期要杀的尾数。
	public static double specialRateBlue06(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int ball = 0;
			if(current.getBallNum().getBlue() > 14)
				ball = current.getBallNum().getBlue() -7;
			else
				ball = current.getBallNum().getBlue() +7;
			ball = calTail(ball, true);
			total ++;			
			if(validBlueBall(ball))
			{
				if(next.getBallNum().getBlue() != ball || next.getBallNum().getBlue() != ball+10)
					count++;
			}				
		
		}
		System.out.println("10、用上期蓝号加7或减7，注意蓝号大于14则减7，小于14则加7，得出的数即为下期要杀的尾数。");
		return count/(double)total;
	}
	
	//杀蓝球 新方法01  运用上期期号的个位数加1后的尾数
	public static double specialRateBlueNew01(List<DoubleBall> balls)
	{
		int count = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int tail = current.getNum() % 10;
			int kill = tail +1;
			if(next.getBallNum().getBlue() !=  kill)
				count++;
		
		}
		System.out.println("杀蓝球 新方法01  运用上期期号的个位数加1后的尾数");
		return count/(double)balls.size();
	}
	//new 02 运用上一期中第1位的红球号码加3，得数就是本期中可以排除的蓝球号码
	public static double specialRateBlueNew02(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			total ++;
			int kill = new ArrayList<Integer>(current.getBallNum().getReds()).get(0) +3;
			if(next.getBallNum().getBlue() !=  kill)
				count++;
			
		}
		System.out.println("杀蓝球 新方法02  上一期中第1位的红球号码加3，得数就是本期中可以排除的蓝球号码");
		return count/(double)total;
	}
	//运用上两期蓝球相加的和，作为本期排除的蓝球，在得数大于16的时候，要减去16
	public static double specialRateBlueNew03(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			total ++;
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int kill = (current.getBallNum().getBlue() + next.getBallNum().getBlue()) %16;
			if(kill == 0)
				kill = 16;
			if(nextNext.getBallNum().getBlue() !=  kill)
				count++;
			
		}
		System.out.println("运用上两期蓝球相加的和，作为本期排除的蓝球，在得数大于16的时候，要减去16");
		return count/(double)total;
	}
	
	//4.用上一期蓝球号码的个位和十位互换位置，换位后的号码就是当期可以排除的蓝球号码，如果互换位置后的号码大于16的话，就要减去16
	public static double specialRateBlueNew04(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			total ++;
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			int kill = swapPosition(current.getBallNum().getBlue()) % 16;
			if(kill == 0)
				kill = 16;
			if(next.getBallNum().getBlue() !=  kill)
				count++;
			
		}
		System.out.println("用上一期蓝球号码的个位和十位互换位置，换位后的号码就是当期可以排除的蓝球号码，如果互换位置后的号码大于16的话，就要减去16");
		return count/(double)total;
	}
	//5.用上上期的蓝球号码除3后取余数，再加上期蓝球，最后的得数就是当期可以排除的蓝球
	public static double specialRateBlueNew05(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			total ++;
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int kill = current.getBallNum().getBlue()%3 + next.getBallNum().getBlue();
			if(nextNext.getBallNum().getBlue() !=  kill)
				count++;
			
		}
		System.out.println("用上上期的蓝球号码除3后取余数，再加上期蓝球，最后的得数就是当期可以排除的蓝球");
		return count/(double)total;
	}
	
	public static int swapPosition(int blue) 
	{
		String s = String.valueOf(blue);
		if(blue < 10)
			s = "0"+blue;
		s = new StringBuilder(s).reverse().toString();
		return Integer.parseInt(s);
	}

	//通过上一期 上上一期 阈值 及 实际计算的概率值 要杀的球 来杀一个蓝球
	public static ForcastBall killByBlueTail(ForcastBall ball,  double ratio, double actual, int tail)
	{
		//kill while
		if(ratio < actual)
		{
			ball.killBlueWithTail(tail);
			System.out.println("kill-blue: "+tail +"尾数的");
		}
		return ball;
	}
	
	public static ForcastBall killByBlue(ForcastBall ball,  double ratio, double actual, int toKill)
	{
		//kill while
		if(ratio < actual)
		{
			ball.killBlue(toKill);
			System.out.println("kill-blue: "+toKill);
		}
		return ball;
	}
	
	
	//校验球合法性
	public static boolean validBlueBall(int blue)
	{
		if(blue >= 1 && blue <= 16)
			return true;
		else
			return false;
	}
	
	//将 数字前面的0 去掉。
	public static int convert(String ball)
	{
		if(null == ball || ball.trim().equals(""))
			return 0;
		if(ball.startsWith("0"))
			return Integer.parseInt(ball.substring(1, ball.length()));
		else
			return Integer.parseInt(ball);
	}
	
	public static Map<Integer, BallNum> toMap(List<DoubleBall> balls)
	{
		Map<Integer, BallNum> ballsMap = new TreeMap<Integer, BallNum>();
		for (DoubleBall doubleBall : balls)
		{
			ballsMap.put(doubleBall.getNum(), doubleBall.getBallNum());
		}
		return ballsMap;
	}
	

	
	
	
	
	
	
	
 
	

	/**
	 * 统计奇偶比例
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticOddEven(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int odd = 0;
		int even = 0;
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				if(blue %2 == 0) //even
					even ++;
				else
					odd ++;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(red %2 == 0) //even
						even ++;
					else
						odd ++;
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("奇:偶=").append(odd).append(":")
				.append(even).append(" 比率：").append(odd/(double)even).toString());
		result.put("count",odd+":"+even);
		result.put("rate",String.valueOf(odd/(double)even));
		return result;
	}
	
	
	/**
	 * 判断 i 是否为对望码
	 * @param i
	 * @return
	 */
	public static boolean isDWM(int i)
	{
		return DoubleBallConstant.dwm().contains(i);
	}
	
	/**
	 * 是否为重复码
	 * @param i
	 * @return
	 */
	public static boolean isCFM(int i)
	{
		return DoubleBallConstant.cfm().contains(i);
	}
	
	public static boolean isGolden(int i)
	{
		return DoubleBallConstant.goldenBalls().contains(i);
	}
	
	/**
	 * 对望码统计
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticDWM(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		int size = allBalls.size();
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				if(isDWM(blue)) 
					count ++;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(isDWM(red)) 
						count ++;
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("对望码个数：")
				.append(count).append(" 比率：").append(count/(double)size).toString());
		result.put("count",count);
		result.put("rate",String.valueOf(count/(double)size));
		return result;
	}
	
	/**
	 * 统计重复码
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticCFM(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		int size = allBalls.size();
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				if(isCFM(blue)) 
					count ++;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(isCFM(red)) 
						count ++;
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("重复码个数：")
				.append(count).append(" 比率：").append(count/(double)size).toString());
		result.put("count",count);
		result.put("rate",String.valueOf(count/(double)size));
		return result;
	}
	/**
	 * 黄金码
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticGolden(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		int size = allBalls.size();
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				if(isGolden(blue)) 
					count ++;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(isGolden(red)) 
						count ++;
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("黄金码个数：").append(count).append(" 比率：").append(count/(double)size).toString());
		result.put("count",count);
		result.put("rate",String.valueOf(count/(double)size));
		return result;
	}
	
	/**
	 * 统计大小 
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticBigSmall(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int small = 0;
		int big = 0;
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				if(blue > 8) //big
					big ++;
				else
					small ++;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(red > 16) //big
						big ++;
					else
						small ++;
				}
				
			}	
		}
		result.put("print",new StringBuilder(isBlue == true?"蓝球：":"红球：").append("小:大=").append(small).append(":")
				.append(big).append(" 比率：").append(small/(double)big).toString());
		result.put("count",small +":"+big);
		result.put("rate",String.valueOf(small/(double)big));
		return result;
	}
	
	/**
	 * 统计
	 * @param allBalls
	 * @param isBlue
	 * @param continuous 代表分区方式， 为true时，为普通分区， 否则为取余分区
	 * @return
	 */
	public static Map<String, Object> statistic4zone(List<DoubleBall> allBalls, boolean isBlue, boolean continuous) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int zone1 = 0;
		int zone2 = 0;
		int zone3 = 0;
		int zone4 = 0;
		if(isBlue)
		{
			//连续，即1-4 5-8 9-12 13-16
			if(continuous)
			{
				for (DoubleBall doubleBall : allBalls) 
				{
					int blue = doubleBall.getBallNum().getBlue();
					if(blue >= 1 && blue <=4) 
						zone1 ++;
					else if(blue >= 5 && blue <=8) 
						zone2 ++;
					else if(blue >= 9 && blue <=12) 
						zone3 ++;
					else
						zone4 ++;
				}	
			}
			else
			{
				for (DoubleBall doubleBall : allBalls) 
				{
					int blue = doubleBall.getBallNum().getBlue();
					if(blue%4 ==0) 
						zone4 ++;
					else if(blue%4 ==1) 
						zone1 ++;
					else if(blue%4 ==2) 
						zone2 ++;
					else
						zone3 ++;
				}	
			}
			
		}
		else
		{
			if(continuous)
			{
				for (DoubleBall doubleBall : allBalls) 
				{
					Set<Integer> reds = doubleBall.getBallNum().getReds();
					for (Integer red : reds) 
					{
						if(red >= 1 && red <=8) 
							zone1 ++;
						else if(red >= 9 && red <=16) 
							zone2 ++;
						else if(red >= 17 && red <=24) 
							zone3 ++;
						else
							zone4 ++;
					}
					
				}	
			}
			else
			{
				for (DoubleBall doubleBall : allBalls) 
				{
					Set<Integer> reds = doubleBall.getBallNum().getReds();
					for (Integer red : reds) 
					{
						if(red%4 ==0) 
							zone4 ++;
						else if(red%4 ==1) 
							zone1 ++;
						else if(red%4 ==2) 
							zone2 ++;
						else
							zone3 ++;
					}
					
				}
				
			}
			
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append(continuous == true?"【连续4区】":"【取余4区】").append("1区:2区:3区:4区=").append(zone1).append(":")
				.append(zone2).append(":").append(zone3).append(":").append(zone4).toString() );
		result.put("count", new StringBuilder().append(zone1).append(":")
				.append(zone2).append(":").append(zone3).append(":").append(zone4).toString());
		return result;
		
	}
	
	
	public static Map<String, Object> statistic3zone(List<DoubleBall> allBalls, boolean continuous) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int zone1 = 0;
		int zone2 = 0;
		int zone3 = 0;
		if(continuous)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(red >= 1 && red <=11) 
						zone1 ++;
					else if(red >= 12 && red <=22) 
						zone2 ++;
					else if(red >= 23 && red <=33) 
						zone3 ++;
				}
				
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					if(red%4 ==1) 
						zone1 ++;
					else if(red%4 ==2) 
						zone2 ++;
					else
						zone3 ++;
				}
				
			}
			
		}
			
		result.put("print", new StringBuilder("红球：").append(continuous == true?"【连续3区】":"【取余3区】").append("1区:2区:3区=").append(zone1).append(":")
				.append(zone2).append(":").append(zone3).toString());
		result.put("count", new StringBuilder().append(zone1).append(":")
				.append(zone2).append(":").append(zone3).toString());
		return result;
	}
	
	/**
	 * 求和平均值
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticAvgSum(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int sum = 0;
		int size = allBalls.size();
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				sum +=blue;
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					sum += red;
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("和平均值=").append(sum/(double)size).toString());
		result.put("count", sum);
		result.put("rate", sum/(double)size);
		return result;
	}
	
	/**
	 * 求尾数的和平均值
	 * @param allBalls
	 * @param isBlue
	 * @return
	 */
	public static Map<String, Object> statisticAvgTailSum(List<DoubleBall> allBalls, boolean isBlue) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		int sum = 0;
		int size = allBalls.size();
		if(isBlue)
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				int blue = doubleBall.getBallNum().getBlue();
				sum +=getTail(blue);
			}	
		}
		else
		{
			for (DoubleBall doubleBall : allBalls) 
			{
				Set<Integer> reds = doubleBall.getBallNum().getReds();
				for (Integer red : reds) 
				{
					sum += getTail(red);
				}
				
			}	
		}
		result.put("print", new StringBuilder(isBlue == true?"蓝球：":"红球：").append("尾数和平均值=").append(sum/(double)size).toString());
		result.put("count", sum);
		result.put("rate", sum/(double)size);
		return result;
	}
	
	private static int getTail(int value)
	{
		 return value%10;
	}
	
	/*当 01 号球开出后，下期除考虑它的同尾号球 11 、 21 、 31 号球出号外，
	也极其喜欢带出 05 号球。一般来讲， 01 出号后， 05 下期紧随其后出号，
	有时也在下二期出号。时常 01 号球的同尾号球与 05 号球同时出现在奖号里。
	例如：第 010 期 01 出号后，带出第 011 期同尾号球 11 和 05 号球同时出号；
	又比如：第 033 期出号后，带出下二期 035 期，同尾号球 11 与 05 号球同时出号等。
	**/
	public static double mustKeepRed01(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(1))
			{
				total ++;
				if(next.getBallNum().getReds().contains(11) || next.getBallNum().getReds().contains(21) || next.getBallNum().getReds().contains(31) )
					count++;
			}
			
		}
		System.out.println("出01后， 下期内会出11 21 31 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 下下期出现11 21 31 概率
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0101(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall nextNext = balls.get(i+2);
			if(current.getBallNum().getReds().contains(1))
			{
				total ++;
				if(nextNext.getBallNum().getReds().contains(1) || nextNext.getBallNum().getReds().contains(11) || nextNext.getBallNum().getReds().contains(21) || nextNext.getBallNum().getReds().contains(31) )
					count++;
			}
			
		}
		System.out.println("出01后， 下下期内会出11 21 31 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0102(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall nextNext = balls.get(i+2);
			if(current.getBallNum().getReds().contains(1))
			{
				total ++;
				if(nextNext.getBallNum().getReds().contains(5) )
					count++;
			}
			
		}
		System.out.println("出01后， 下下期内会出05的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	public static double mustKeepRed0103(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(1))
			{
				total ++;
				if(next.getBallNum().getReds().contains(5) )
					count++;
			}
			
		}
		System.out.println("出01后， 下期内会出05的概率是:"+count/(double)total);
		return count/(double)total;
	}
	/**
	 * 当 05 号球出号后，下期除带出同尾号球外，
	 *  08 号球也极喜欢跟在 05 号球后，形成 05-08 右下斜的走势，
	 *  或经常在 02-05-08 隔一或隔二右下斜。如果 02 、 05 号球同时开出，
	 *  那么下二期 08 号球出现几率极高。那么这期 08 号球完全可以做心水胆号投注。
	 *  例如： 05-08 右下斜走势第 009 期 —— 第 010 期； 02——05——08 隔一右下斜走势第 024 、 025 、 026 期，
	 * 第 041 、 042 、 043 期； 02+05 出号，
	 * 下二期 08 出号，第 044 期、 046 期，第 054 期、 056 期等都是这样的模式。
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0201(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(5))
			{
				total ++;
				if(next.getBallNum().getReds().contains(5) || next.getBallNum().getReds().contains(15) || next.getBallNum().getReds().contains(25) )
					count++;
			}
			
		}
		System.out.println("出05后， 下期内会出5尾的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0202(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(5))
			{
				total ++;
				if( next.getBallNum().getReds().contains(8) )
					count++;
			}
			
		}
		System.out.println("出05后， 下期内会出8的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 7 带出17 27 10 11 8
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0301(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(7))
			{
				total ++;
				if(next.getBallNum().getReds().contains(17) || next.getBallNum().getReds().contains(27) )
					count++;
			}
			
		}
		System.out.println("出07后， 下期内会出17 27 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0302(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(7))
			{
				total ++;
				if(next.getBallNum().getReds().contains(8) )
					count++;
			}
			
		}
		System.out.println("出07后， 下期内会出8  的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0303(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(7))
			{
				total ++;
				if(next.getBallNum().getReds().contains(10))
					count++;
			}
			
		}
		System.out.println("出07后， 下期内会出  10   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0304(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(7))
			{
				total ++;
				if( next.getBallNum().getReds().contains(11))
					count++;
			}
			
		}
		System.out.println("出07后， 下期内会出 11 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 出10后， 下期内会出20 30 17
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0401(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(10))
			{
				total ++;
				if(  next.getBallNum().getReds().contains(20) || next.getBallNum().getReds().contains(30) )
					count++;
			}
			
		}
		System.out.println("出10后， 下期内会出20 30   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0402(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(10))
			{
				total ++;
				if(next.getBallNum().getReds().contains(17) )
					count++;
			}
			
		}
		System.out.println("出10后， 下期内会出  17 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 17 16 18 20 21 22
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0501(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(17))
			{
				total ++;
				if(next.getBallNum().getReds().contains(16))
					count++;
			}
			
		}
		System.out.println("出17后， 下期内会出16   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0502(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(17))
			{
				total ++;
				if(next.getBallNum().getReds().contains(18)  )
					count++;
			}
			
		}
		System.out.println("出17后， 下期内会出  18  的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0503(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(17))
			{
				total ++;
				if(next.getBallNum().getReds().contains(20) )
					count++;
			}
			
		}
		System.out.println("出17后， 下期内会出 20   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0504(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(17))
			{
				total ++;
				if(next.getBallNum().getReds().contains(21) )
					count++;
			}
			
		}
		System.out.println("出17后， 下期内会出 21   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0505(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(17))
			{
				total ++;
				if(next.getBallNum().getReds().contains(22)  )
					count++;
			}
			
		}
		System.out.println("出17后， 下期内会出 22 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 20 后 10 30
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed06(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(20))
			{
				total ++;
				if(next.getBallNum().getReds().contains(10) || next.getBallNum().getReds().contains(30))
					count++;
			}
			
		}
		System.out.println("出20后， 下期内会出10 30 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 11 同尾 31 21 01
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed07(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(11))
			{
				total ++;
				if(next.getBallNum().getReds().contains(31) || next.getBallNum().getReds().contains(21)|| next.getBallNum().getReds().contains(1))
					count++;
			}
			
		}
		System.out.println("出11后， 下期内会出31 21 1 的概率是:"+count/(double)total);
		return count/(double)total;
	}
	/**
	 * 31 后 1 28
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed0801(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(31))
			{
				total ++;
				if(next.getBallNum().getReds().contains(1) )
					count++;
			}
			
		}
		System.out.println("出31后， 下期内会出1   的概率是:"+count/(double)total);
		return count/(double)total;
	}
	public static double mustKeepRed0802(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(31))
			{
				total ++;
				if(  next.getBallNum().getReds().contains(28))
					count++;
			}
			
		}
		System.out.println("出31后， 下期内会出 28 的概率是:"+count/(double)total);
		return count/(double)total;
	}

	/**
	 * 28 后 25
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed09(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			if(current.getBallNum().getReds().contains(28))
			{
				total ++;
				if(next.getBallNum().getReds().contains(25) || nextNext.getBallNum().getReds().contains(25) )
					count++;
			}
			
		}
		System.out.println("出28后， 下两期内会出25的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	public static double mustKeepRed10(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(25))
			{
				total ++;
				if(next.getBallNum().getReds().contains(5) )
					count++;
			}
			
		}
		System.out.println("出25后， 下期内会出5的概率是:"+count/(double)total);
		return count/(double)total;
	}
	/**
	 * 19 后 9 或29
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed11(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(19))
			{
				total ++;
				if(next.getBallNum().getReds().contains(9) || next.getBallNum().getReds().contains(29))
					count++;
			}
			
		}
		System.out.println("出19后， 下期内会出9 29的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 20后 14
	 * @param balls
	 * @return
	 */
	public static double mustKeepRed12(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-2; i++)
		{
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			if(current.getBallNum().getReds().contains(20))
			{
				total ++;
				if(next.getBallNum().getReds().contains(14))
					count++;
			}
			
		}
		System.out.println("出20后， 下期内会出14的概率是:"+count/(double)total);
		return count/(double)total;
	}
	
	/**
	 * 蓝 范围 
	 * @param balls
	 * @return
	 */
	public static double mustKeepBlue01(List<DoubleBall> balls)
	{
		int count = 0;
		int total = 0;
		for (int i = 0; i< balls.size()-3; i++)
		{
			total ++;
			DoubleBall current = balls.get(i);
			DoubleBall next = balls.get(i+1);
			DoubleBall nextNext = balls.get(i+2);
			int key = Math.abs(current.getBallNum().getBlue() - next.getBallNum().getBlue());
			int min = key-4;
			if(min < 0)
				min = 1;
			int max = key +4;
			if(max> 16)
				max = 16;
			if(nextNext.getBallNum().getBlue() >= min && nextNext.getBallNum().getBlue() <= max)
				count++;
		}
		System.out.println("蓝球在 上两期blue差 -4 为min, +4为max:"+count/(double)total);
		return count/(double)total;
	}
}
