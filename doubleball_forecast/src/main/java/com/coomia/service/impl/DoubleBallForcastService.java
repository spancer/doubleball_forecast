package com.coomia.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.crawler.CrawlBalls;
import com.coomia.model.DoubleBall;
import com.coomia.model.ForcastBall;
import com.coomia.service.IDoubleBallForcastService;
import com.coomia.service.IDoubleBallService;
import com.coomia.util.TrendForcastUtil;

public class DoubleBallForcastService implements IDoubleBallForcastService {
	
	private IDoubleBallService doubleBallService = new DoubleBallService();

	public double analysisSmallTrend(int interval, int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = (int)Math.floor(data.size()/(double)interval);
		int forcast = (int)Math.ceil(data.size()/(double)interval); 
		double[] x = new double[len];
		double[] y = new double[len];
		int count = 0;
		int totalSmallCount = 0;
		int index = 0;
		for (DoubleBall current: data)
		{
			count++;
			totalSmallCount += current.getBallNum().getSmallBallCount();
			if(count == interval)
			{
				x[index] = index;
				y[index] = totalSmallCount / interval;
				index ++;
				totalSmallCount = 0;
				count = 0;
			}
			
		}
 		return TrendForcastUtil.estimate(x, y, forcast);
	}

	@Override
	public double analysisOddTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisOddTrend(data);
	}
	public double analysisOddTrend(List<DoubleBall> data) {

		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getOddBallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}
	@Override
	public double analysisGoldenTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisGoldenTrend(data);
	}
	public double analysisGoldenTrend(List<DoubleBall> data) {
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getGoldenBallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}
	@Override
	public double analysisDWMTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisDWMTrend(data);
	}
	
	

	public double analysisDWMTrend(List<DoubleBall> data) {
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getDWMBallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);		
	}

	@Override
	public double analysisCFMTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisCFMTrend(data);
	}
	public double analysisCFMTrend(List<DoubleBall> data) {

		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getCFMBallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
 		}
	@Override
	public double analysisBlueOddTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisBlueOddTrend(data);
	}
		public double analysisBlueOddTrend(List<DoubleBall> data) {
			int len = data.size();
			double[] x = new double[len];
			double[] y = new double[len];
			for (int i=0; i< len; i++)
			{
				x[i] = i;
				y[i] = data.get(i).getBallNum().getBlueOddCount();
				
			}
	 		return TrendForcastUtil.estimate(x, y, len);
		}

	@Override
	public double analysisSmallTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisSmallTrend(data);
	}
	
	public double analysisSmallTrend(List<DoubleBall> data)
	{
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getSmallBallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}
	

	@Override
	public double rateBlueOdd(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		double rateTotal = 0;
		for (int i = 0; i< data.size()-1; i++)
		{			
			DoubleBall current = data.get(i);
			rateTotal+= current.getBallNum().getBlueOddCount(); 
		}
		return rateTotal/data.size();
	}
	
	public double rateBlueSmall(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		double rateTotal = 0;
		for (int i = 0; i< data.size()-1; i++)
		{			
			DoubleBall current = data.get(i);
			rateTotal+= current.getBallNum().getBlueSmallCount(); 
		}
		return rateTotal/data.size();
	}
	

	@Override
	public double analysisZone41(int start, int end) {
		
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone41();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone42(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone42();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone43(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone43();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone44(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone44();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone4mod1(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone4mod1();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone4mod2(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone4mod2();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone4mod3(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone4mod3();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone4mod4(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone4mod4();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone31(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone31();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone32(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone32();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone33(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone33();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone3mod1(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone3mod1();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone3mod2(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone3mod2();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisZone3mod3(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getZone3mod3();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public double analysisBlueSmallTrend(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len; i++)
		{
			x[i] = i;
			y[i] = data.get(i).getBallNum().getBlueSmallCount();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public Set<Integer> mostRedBalls(DoubleBall current) {
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

	@Override
	public double analysisMostInBalls(int start, int end) {
		List<DoubleBall> data = doubleBallService.findByInterval(start, end);
		return analysisMostInBalls(data);
	}
	public double analysisMostInBalls(List<DoubleBall> data ) {
		int len = data.size();
		double[] x = new double[len];
		double[] y = new double[len];
		for (int i=0; i< len-2; i++)
		{
			x[i] = i;
			Set<Integer> mosts = mostRedBalls(data.get(i));
			Set<Integer> nextReds = data.get(i+1).getBallNum().getReds();
			Set<Integer> inMost = new HashSet<Integer>();
			inMost.addAll(mosts);
			inMost.retainAll(nextReds);
			y[i] = inMost.size();
			
		}
 		return TrendForcastUtil.estimate(x, y, len);
	}

	@Override
	public ForcastBall forcastNextBall(List<DoubleBall> balls, double red_ratio, double blue_ratio) {
	
		int index = balls.size() -1;
		DoubleBall current = balls.get(index);
		DoubleBall beforeCurrent = balls.get(index -1);
		DoubleBall earlierCurrent = balls.get(index-2);
		/*
		 * 3. 定义一组公式算概率
		 * 定义一组公式， 因为红 与 蓝 球 的公式的准确率可能不一样,
		 * 因此，将表达式式的作用范围分为红和蓝。
		 */
		List<String> blue_expressions = new ArrayList<String>();
		blue_expressions.add("15-#{blue}@kill-blue");// 1
		blue_expressions.add("19-#{blue}@kill-blue"); // 2
		blue_expressions.add("21-#{blue}@kill-blue"); // 3
		blue_expressions.add("#{blue}*2@kill-blue"); // 8
		blue_expressions.add("#{blue}+2@kill-blue"); //11
		blue_expressions.add("#{blue}+6@kill-blue"); //12
		
		//红球
		List<String> red_expressions = new ArrayList<String>();
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
		
		
		/**
		 * 取 概率大于 指定值的公式
		 * 有用的表达式， 大于 指定概率阈值的均为有效表达式。
		 */
		 /**
		  * 红色的有效表达式。
		  */
		List<String> red_efficient = CrawlBalls.effectExperssions(red_expressions, balls, red_ratio); 
				
		//装载结果的对象
		ForcastBall forcast = new ForcastBall(current.getNum() + 1);
		
		/**
		 * 杀红球 
		 * 红球预测后结果
		 */
		forcast = CrawlBalls.forecast(current, red_efficient, forcast);
		System.out.println("杀红球后，预测结果："+forcast.toString());
		//再殺红球 
		//2. 特殊的概率
		double r1 = CrawlBalls.specialRateRed01(balls);
		System.out.println("当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出"+r1);
		double r2 = CrawlBalls.specialRateRed02(balls);
		System.out.println("当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。"+r2);
		double r3 = CrawlBalls.specialRateRed03(balls);
		System.out.println("开奖红号按从小到大排序(第三位+第六位)除以33的余数+14,如果结果大于33,就减去33。"+r3);
		double r4 = CrawlBalls.specialRateRed04(balls);
		System.out.println("上期红1位尾加上期红2位尾。 "+r4);
		
		double r5 = CrawlBalls.specialRateRed05(balls);
		System.out.println("上期蓝码+上上期蓝码 "+r5);
		int r5kill = beforeCurrent.getBallNum().getBlue() + current.getBallNum().getBlue();
		
		double r6 = CrawlBalls.specialRateRed06(balls);
		System.out.println("33-上上期蓝码 "+r6);
		int r6kill = 33 - beforeCurrent.getBallNum().getBlue();
		
		double r7 = CrawlBalls.specialRateRed07(balls);
		System.out.println("上上期蓝码 "+r7);
		int r7kill =  beforeCurrent.getBallNum().getBlue();
		
		double r8 = CrawlBalls.specialRateRed08(balls);
		System.out.println("33-上期蓝码 "+r8);
		int r8kill =  33-current.getBallNum().getBlue();
		
		double r9 = CrawlBalls.specialRateRed09(balls);
		System.out.println("上期蓝码 "+r9);
		int r9kill =  current.getBallNum().getBlue();
		
		double r10 = CrawlBalls.specialRateRed10(balls);
		System.out.println("上期红1位尾加上期红2位尾 "+r10);
		int r10kill =  CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(0), true) + CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(1), true);
		
		forcast = CrawlBalls.killByRed01(forcast, current, red_ratio, r1);
		forcast = CrawlBalls.killByRed02(forcast, current, red_ratio, r2);
		forcast = CrawlBalls.killByRed03(forcast, current, red_ratio, r3);
		forcast = CrawlBalls.killByRed04(forcast, current, red_ratio, r4);
		forcast = CrawlBalls.killByRed(forcast, r5kill, red_ratio, r5);
		forcast = CrawlBalls.killByRed(forcast, r6kill, red_ratio, r6);
		forcast = CrawlBalls.killByRed(forcast, r7kill, red_ratio, r7);
		forcast = CrawlBalls.killByRed(forcast, r8kill, red_ratio, r8);
		forcast = CrawlBalls.killByRed(forcast, r9kill, red_ratio, r9);
		forcast = CrawlBalls.killByRed(forcast, r10kill, red_ratio, r10);
		System.out.println("再杀一系列红球后，预测结果："+forcast.toString());
		/**
		 * 
		 * 蓝色的有效表达式。
		 * 
		 */
		List<String> blue_efficient = CrawlBalls.effectExperssions(blue_expressions, balls, blue_ratio); 
		
		/**
		 * 杀蓝球
		 */
		forcast = CrawlBalls.forecast(current, blue_efficient, forcast);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		//蓝球特殊 //4
		double b1 = CrawlBalls.specialRateBlue01(balls);
		System.out.println(b1);
		int b1kill = CrawlBalls.calTail(CrawlBalls.calTailRelated(beforeCurrent.getBallNum().getBlue(), false, current.getBallNum().getBlue(), true), true);
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, b1, b1kill);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		//5
		double b2 = CrawlBalls.specialRateBlue02(balls);
		System.out.println(b2);
		int b2kill = CrawlBalls.calTail(CrawlBalls.calTailRelated(beforeCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), false), true);
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, b2, b2kill);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		//6
		double b3 = CrawlBalls.specialRateBlue03(balls);
		System.out.println(b3);
		int b3kill = CrawlBalls.calTail(CrawlBalls.calTailRelated(beforeCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
		forcast = CrawlBalls.killByBlueTail(forcast,  blue_ratio, b3, b3kill);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		// 7 
		double b4 = CrawlBalls.specialRateBlue04(balls);
		System.out.println(b4);
		int b4kill = CrawlBalls.calTail(CrawlBalls.calTailRelated(earlierCurrent.getBallNum().getBlue(), true, current.getBallNum().getBlue(), true), true);
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, b4, b4kill);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		//9 
		double b5 = CrawlBalls.specialRateBlue05(balls);
		System.out.println(b5);
		int b5kill = CrawlBalls.calTail(CrawlBalls.calTail(current.getBallNum().getBlue(), true) * 4, true);
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, b5, b5kill);
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
		//10
		double b6 = CrawlBalls.specialRateBlue06(balls);
		System.out.println(b6);
		int b6kill = 0;
		if(current.getBallNum().getBlue() > 14)
			b6kill = current.getBallNum().getBlue() -7;
		else
			b6kill = current.getBallNum().getBlue() +7;
		b6kill = CrawlBalls.calTail(b6kill, true);
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, b6, b6kill);
		
		//new 01
		double bnew01 = CrawlBalls.specialRateBlueNew01(balls);
		int bnew01Tail = current.getNum()%10 +1; 
		forcast = CrawlBalls.killByBlueTail(forcast, blue_ratio, bnew01, bnew01Tail);
		
		//new 02 blue kill
		double bnew02 = CrawlBalls.specialRateBlueNew02(balls);
		int bnew02Kill =  new ArrayList<Integer>(current.getBallNum().getReds()).get(0) +3;
		forcast = CrawlBalls.killByBlue(forcast, blue_ratio, bnew02, bnew02Kill);
		
		//new 03 blue kill
		double bnew03 = CrawlBalls.specialRateBlueNew03(balls);
		int bnew03Kill = beforeCurrent.getBallNum().getBlue()+earlierCurrent.getBallNum().getBlue();
		if(bnew03Kill > 16)
			bnew03Kill -=16;
		forcast = CrawlBalls.killByBlue(forcast, blue_ratio, bnew03, bnew03Kill);
		
		//new 04 blue kill
		double bnew04 = CrawlBalls.specialRateBlueNew04(balls);
		int bnew04Kill = CrawlBalls.swapPosition(current.getBallNum().getBlue());
		if(bnew04Kill > 16)
			bnew04Kill -=16;
		forcast = CrawlBalls.killByBlue(forcast, blue_ratio, bnew04, bnew04Kill);
		
		//new 05 blue kill
		double bnew05 = CrawlBalls.specialRateBlueNew05(balls);
		int bnew05Kill = earlierCurrent.getBallNum().getBlue()%3 + beforeCurrent.getBallNum().getBlue();
		forcast = CrawlBalls.killByBlue(forcast, blue_ratio, bnew05, bnew05Kill);
		
		System.out.println("杀蓝球后，预测结果："+forcast.toString());
	 	System.out.println("预测双色球第"+(current.getNum()+1)+"期："+forcast.toString());
	 	
		return forcast;
	}

	@Override
	public ForcastBall woyingKill(ForcastBall forcastBall) {

	 	/**
	 	 * woying 网站杀红球
	 	 */
	 	Set<Integer> woyingRedkill = CrawlBalls.woyingKillRed();
	 	for (Integer red : woyingRedkill) 
	 	{
	 		forcastBall.killRed(red);
	 		System.out.println("我赢网站杀红球："+red);
		}
	 	/**
	 	 * woying 网站杀蓝球
	 	 */
	 	Set<Integer> woyingBluekill = CrawlBalls.woyingKillBlue();
	 	for (Integer blue : woyingBluekill) 
	 	{
	 		forcastBall.killBlueWithTail(blue);
	 		System.out.println("我赢网站杀蓝球："+blue);
		}
	 	
	 	return forcastBall;
	}

	@Override
	public ForcastBall addShouldKeep(ForcastBall forcastBall, DoubleBall current) {
		Set<Integer> shoudKeep = mostRedBalls(current);
	 	Set<Integer> redsToAdd = new HashSet<Integer>();
	 	redsToAdd.addAll(shoudKeep);
	 	redsToAdd.retainAll(forcastBall.getReds());
	 	for (Integer integer : redsToAdd) 
	 	{
	 		forcastBall.shouldRed(integer);
		}
	 	return forcastBall;
	}

	@Override
	public ForcastBall addMust(ForcastBall forcastBall, double redRatio, double blueRatio, double redRatioKill, double blueRatioKill, List<DoubleBall> balls) {
		DoubleBall current = balls.get(balls.size() -1);
		DoubleBall before = balls.get(balls.size() -2);
		Set<Integer> reds = current.getBallNum().getReds();
		int key = Math.abs(current.getBallNum().getBlue() - before.getBallNum().getBlue());
		int min = key-4;
		if(min < 0)
			min = 1;
		int max = key +4;
		if(max> 16)
			max = 16;
		double mustKeepRed01 = CrawlBalls.mustKeepRed01(balls);
		if(mustKeepRed01 > redRatio && reds.contains(1))
		{
			forcastBall.mustRed(11);
			forcastBall.mustRed(21);
			forcastBall.mustRed(31);
		}
		
		if(redRatioKill >= mustKeepRed01 && reds.contains(1))
		{
			forcastBall.killRed(11);
			forcastBall.killRed(21);
			forcastBall.killRed(31);
		}
		
		double mustKeepRed0101 = CrawlBalls.mustKeepRed0101(balls);
		System.out.println("下下期出现11 21 31的概率是"+mustKeepRed0101);
		
		double mustKeepRed0102 = CrawlBalls.mustKeepRed0102(balls);
		System.out.println("下下期出现05的概率是"+mustKeepRed0102);
		
		double mustKeepRed0103 = CrawlBalls.mustKeepRed0103(balls);
		if(mustKeepRed0103 > redRatio && reds.contains(1))
		{
			forcastBall.mustRed(5);
		}
		if(redRatioKill >= mustKeepRed0103 && reds.contains(1))
		{
			forcastBall.killRed(5);
		}
		
		double mustKeepRed0201 = CrawlBalls.mustKeepRed0201(balls);
		if(mustKeepRed0201 > redRatio && reds.contains(5))
		{
			forcastBall.mustRed(5);
			forcastBall.mustRed(15);
			forcastBall.mustRed(25);
		}
		
		if(redRatioKill >= mustKeepRed0201 && reds.contains(5))
		{
			forcastBall.killRed(5);
			forcastBall.killRed(15);
			forcastBall.killRed(25);
		}
		
		double mustKeepRed0202 = CrawlBalls.mustKeepRed0202(balls);
		if(mustKeepRed0202 > redRatio && reds.contains(5))
		{
			forcastBall.mustRed(8);
		}
		
		if(redRatioKill >= mustKeepRed0202 && reds.contains(5))
		{
			forcastBall.killRed(8);
		}
		
		double mustKeepRed0301 = CrawlBalls.mustKeepRed0301(balls);
		if(mustKeepRed0301 > redRatio && reds.contains(7))
		{
			forcastBall.mustRed(17);
			forcastBall.mustRed(27);
		}
		if(redRatioKill >= mustKeepRed0301 && reds.contains(7))
		{
			forcastBall.killRed(17);
			forcastBall.killRed(27);
		}
		
		double mustKeepRed0302 = CrawlBalls.mustKeepRed0302(balls);
		if(mustKeepRed0302 > redRatio && reds.contains(7))
		{
			forcastBall.mustRed(8);
		}
		if(redRatioKill >= mustKeepRed0302 && reds.contains(7))
		{
			forcastBall.killRed(8);
		}
		double mustKeepRed0303= CrawlBalls.mustKeepRed0303(balls);
		if(mustKeepRed0303 > redRatio && reds.contains(7))
		{
			forcastBall.mustRed(10);
		}
		if(redRatioKill >= mustKeepRed0303 && reds.contains(7))
		{
			forcastBall.killRed(10);
		}
		double mustKeepRed0304 = CrawlBalls.mustKeepRed0304(balls);
		if(mustKeepRed0304 > redRatio && reds.contains(7))
		{
			forcastBall.mustRed(11);
		}
		if(redRatioKill >= mustKeepRed0304 && reds.contains(7))
		{
			forcastBall.killRed(11);
		}
		double mustKeepRed0401 = CrawlBalls.mustKeepRed0401(balls);
		if(mustKeepRed0401 > redRatio && reds.contains(10))
		{
			forcastBall.mustRed(20);
			forcastBall.mustRed(30);
		}
		if(redRatioKill >= mustKeepRed0401 && reds.contains(10))
		{
			forcastBall.killRed(20);
			forcastBall.killRed(30);
		}
		double mustKeepRed0402 = CrawlBalls.mustKeepRed0402(balls);
		if(mustKeepRed0402 > redRatio  && reds.contains(10))
		{
			forcastBall.mustRed(17);
		}
		if(redRatioKill >= mustKeepRed0402 && reds.contains(10))
		{
			forcastBall.killRed(17);
		}
		
		double mustKeepRed0501 = CrawlBalls.mustKeepRed0501(balls);
		if(mustKeepRed0501 > redRatio  && reds.contains(17))
		{
			forcastBall.mustRed(16);
		}
		if(redRatioKill >= mustKeepRed0501 && reds.contains(17))
		{
			forcastBall.killRed(16);
		}
		double mustKeepRed0502 = CrawlBalls.mustKeepRed0502(balls);
		if(mustKeepRed0502 > redRatio  && reds.contains(17))
		{
			forcastBall.mustRed(18);
		}
		if(redRatioKill >= mustKeepRed0502 && reds.contains(17))
		{
			forcastBall.killRed(18);
		}
		
		double mustKeepRed0503 = CrawlBalls.mustKeepRed0503(balls);
		if(mustKeepRed0503 > redRatio  && reds.contains(17))
		{
			forcastBall.mustRed(20);
		}
		if(redRatioKill >= mustKeepRed0503 && reds.contains(17))
		{
			forcastBall.killRed(20);
		}
		
		double mustKeepRed0504 = CrawlBalls.mustKeepRed0504(balls);
		if(mustKeepRed0504 > redRatio  && reds.contains(17))
		{
			forcastBall.mustRed(21);
		}
		if(redRatioKill >= mustKeepRed0504 && reds.contains(17))
		{
			forcastBall.killRed(21);
		}
		
		double mustKeepRed0505 = CrawlBalls.mustKeepRed0505(balls);
		if(mustKeepRed0505 > redRatio  && reds.contains(17))
		{
			forcastBall.mustRed(22);
		}
		if(redRatioKill >= mustKeepRed0505 && reds.contains(17))
		{
			forcastBall.killRed(22);
		}
		 
		double mustKeepRed06 = CrawlBalls.mustKeepRed06(balls);
		if(mustKeepRed06 > redRatio  && reds.contains(20))
		{
			forcastBall.mustRed(10);
			forcastBall.mustRed(30);
		}
		if(redRatioKill >= mustKeepRed06 && reds.contains(20))
		{
			forcastBall.killRed(10);
			forcastBall.killRed(30);
		}
		
		double mustKeepRed07 = CrawlBalls.mustKeepRed07(balls);
		if(mustKeepRed07 > redRatio  && reds.contains(11))
		{
			forcastBall.mustRed(31);
			forcastBall.mustRed(21);
			forcastBall.mustRed(1);
		}
		
		if(redRatioKill >= mustKeepRed07 && reds.contains(11))
		{
			forcastBall.killRed(31);
			forcastBall.killRed(21);
			forcastBall.killRed(1);
		}
		double mustKeepRed0801 = CrawlBalls.mustKeepRed0801(balls);
		if(mustKeepRed0801 > redRatio  && reds.contains(31))
		{
			forcastBall.mustRed(1);
		}
		if(redRatioKill >= mustKeepRed0801 && reds.contains(31))
		{
			forcastBall.killRed(1);
		}
		double mustKeepRed0802 = CrawlBalls.mustKeepRed0802(balls);
		if(mustKeepRed0802 > redRatio  && reds.contains(31))
		{
			forcastBall.mustRed(28);
		}
		if(redRatioKill >= mustKeepRed0802 && reds.contains(31))
		{
			forcastBall.killRed(28);
		}
		double mustKeepRed09 = CrawlBalls.mustKeepRed09(balls);
		if(mustKeepRed09 > redRatio  && reds.contains(28))
		{
			forcastBall.mustRed(25);
		}
		if(redRatioKill >= mustKeepRed09 && reds.contains(28))
		{
			forcastBall.killRed(25);
		}
		
		double mustKeepRed10 = CrawlBalls.mustKeepRed10(balls);
		if(mustKeepRed10 > redRatio  && reds.contains(25))
		{
			forcastBall.mustRed(5);
		}
		if(redRatioKill >= mustKeepRed10 && reds.contains(25))
		{
			forcastBall.killRed(5);
		}
		double mustKeepRed11 = CrawlBalls.mustKeepRed11(balls);
		if(mustKeepRed11 > redRatio  && reds.contains(19))
		{
			forcastBall.mustRed(9);
			forcastBall.mustRed(29);
		}
		if(redRatioKill >= mustKeepRed11 && reds.contains(19))
		{
			forcastBall.killRed(9);
			forcastBall.killRed(29);
		}
		double mustKeepRed12 = CrawlBalls.mustKeepRed12(balls);
		if(mustKeepRed12 > redRatio  && reds.contains(20))
		{
			forcastBall.mustRed(14);
		}
		if(redRatioKill >= mustKeepRed12 && reds.contains(20))
		{
			forcastBall.killRed(14);
		}
		
		double mustKeepBlue01 = CrawlBalls.mustKeepBlue01(balls);
		if(mustKeepBlue01 > blueRatio)
		{
			for (int i = min; i <= max; i++)
			{
				forcastBall.mustBlue(i);
			}
		}
		return forcastBall;
	}

	@Override
	//最终一次统计预测
	/**
	 * 主要针对shouldReds mustReds， reds 进行操作。
	 */
	public ForcastBall finalForcast(ForcastBall forcastBall) {
		Set<Integer> reds = forcastBall.getReds();
		Set<Integer> shouldReds = forcastBall.getRedsKeep();
		Set<Integer> mustReds = forcastBall.getMustReds();
		Set<Integer> blues = forcastBall.getBlues();
		Set<Integer> shouldBlues = forcastBall.getBluesKeep();
		Set<Integer> mustBlues = forcastBall.getMustBlues();
		
		Set<Integer> finalReds = new HashSet<Integer>();
		//如果shouldReds和mustReds都包含，则加入finalReds
		finalReds.addAll(shouldReds);
		finalReds.retainAll(mustReds);
		forcastBall.getFinalReds().addAll(finalReds);
		
		
		Set<Integer> finalBlues = new HashSet<Integer>();
		finalBlues.addAll(shouldBlues);
		finalBlues.retainAll(mustBlues);
		forcastBall.getFinalBlues().addAll(finalBlues);
		
		//reds & mustReds都包含的加入finalReds
		finalReds = new HashSet<Integer>();
		finalReds.addAll(reds);
		finalReds.retainAll(mustReds);
		forcastBall.getFinalReds().addAll(finalReds);
		
		finalBlues = new HashSet<Integer>();
		finalBlues.addAll(blues);
		finalBlues.retainAll(mustBlues);
		forcastBall.getFinalBlues().addAll(finalBlues);
		
		
		return forcastBall;
	}

	@Override
	public Map<Integer, Double> collectRedKilledBallsMapBelowRatio(List<DoubleBall> balls, double ratio) {
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		int index = balls.size() -1;
		DoubleBall current = balls.get(index);
		DoubleBall beforeCurrent = balls.get(index -1);
		
		//红球
		List<String> red_expressions = new ArrayList<String>();
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
		
		
		for (String exp : red_expressions) 
		{
			String[] rule = exp.split("@");
			if(rule.length != 2)
				continue;
			String expression = rule[0].trim();
			double calRatio = CrawlBalls.caculateRate(balls, expression);
			int calball = CrawlBalls.calBallByRule(current.getBallNum(), rule[0].trim());
			putBelow(result, calball, calRatio, ratio);
		}
		//再殺红球 
		//2. 特殊的概率
		double r1 = CrawlBalls.specialRateRed01(balls);
		System.out.println("当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出"+r1);
		double r2 = CrawlBalls.specialRateRed02(balls);
		System.out.println("当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。"+r2);
		double r3 = CrawlBalls.specialRateRed03(balls);
		System.out.println("开奖红号按从小到大排序(第三位+第六位)除以33的余数+14,如果结果大于33,就减去33。"+r3);
		double r4 = CrawlBalls.specialRateRed04(balls);
		System.out.println("上期红1位尾加上期红2位尾。 "+r4);
		
		double r5 = CrawlBalls.specialRateRed05(balls);
		System.out.println("上期蓝码+上上期蓝码 "+r5);
		int r5kill = beforeCurrent.getBallNum().getBlue() + current.getBallNum().getBlue();
		putBelow(result, r5kill, r5, ratio);
		
		double r6 = CrawlBalls.specialRateRed06(balls);
		System.out.println("33-上上期蓝码 "+r6);
		int r6kill = 33 - beforeCurrent.getBallNum().getBlue();
		putBelow(result, r6kill, r6, ratio);
		
		double r7 = CrawlBalls.specialRateRed07(balls);
		System.out.println("上上期蓝码 "+r7);
		int r7kill =  beforeCurrent.getBallNum().getBlue();
		putBelow(result, r7kill, r7, ratio);
		
		double r8 = CrawlBalls.specialRateRed08(balls);
		System.out.println("33-上期蓝码 "+r8);
		int r8kill =  33-current.getBallNum().getBlue();
		putBelow(result, r8kill, r8, ratio);
		
		double r9 = CrawlBalls.specialRateRed09(balls);
		System.out.println("上期蓝码 "+r9);
		int r9kill =  current.getBallNum().getBlue();
		putBelow(result, r9kill, r9, ratio);
		
		double r10 = CrawlBalls.specialRateRed10(balls);
		System.out.println("上期红1位尾加上期红2位尾 "+r10);
		int r10kill =  CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(0), true) + CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(1), true);
		putBelow(result, r10kill, r10, ratio);
		
		int r1kill = current.getBallNum().getBlue()*2+2;
		putBelow(result, r1kill, r1, ratio);
		
		int r2kill = current.getBallNum().getBlue()*5+2;
		putBelow(result, r2kill, r2, ratio);
		
		ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
		int r3kill = (reds.get(2)+ reds.get(5))%33 +14;
		putBelow(result, r3kill, r3, ratio);
		
		int r4kill = CrawlBalls.calTailRelated(reds.get(0), true, reds.get(1), true);
		putBelow(result, r4kill, r4, ratio);

		return result;
		 
	}
	
	
	private void putBelow(Map<Integer, Double> result, int ball, double ratio, double belowRatio)
	{
		if(ratio <= belowRatio)
		{
			Double exists = result.get(ball);
			if(null == exists)
				result.put(ball, ratio);
			else
			{
				if(exists > ratio)
					result.put(ball, ratio);
			}
		}
		
	}

	@Override
	public Map<Integer, Double> blueKilledBallsMap(List<DoubleBall> balls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForcastBall addShouldReds(ForcastBall forcastBall,
			List<DoubleBall> balls_2014, double belowRetio) {
		Map<Integer, Double> ballMap = collectRedKilledBallsMapBelowRatio(balls_2014, belowRetio);
		Set<Integer> toAdd = new HashSet<Integer>();
		for (int ball: ballMap.keySet())
		{
			if(ball >=1 && ball <= 33)
			{
				toAdd.add(ball);
				forcastBall.mustRed(ball);
				System.out.println("杀球概率为【"+ballMap.get(ball)+"】的红球【"+ball+"】被加入到了大概率出现的球中。");
			}
		}
		System.out.println("本次重新加入了如下球：【"+toAdd.toString()+"】 到mustReds列表中");
		return forcastBall;
	}

	@Override
	public Map<Integer, Double> collectRedKilledBallsMapUpRatio(
			List<DoubleBall> balls, double ratio) {
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		int index = balls.size() -1;
		DoubleBall current = balls.get(index);
		DoubleBall beforeCurrent = balls.get(index -1);
		
		//红球
		List<String> red_expressions = new ArrayList<String>();
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
		
		
		for (String exp : red_expressions) 
		{
			String[] rule = exp.split("@");
			if(rule.length != 2)
				continue;
			String expression = rule[0].trim();
			double calRatio = CrawlBalls.caculateRate(balls, expression);
			int calball = CrawlBalls.calBallByRule(current.getBallNum(), rule[0].trim());
			putUpper(result, calball, calRatio, ratio);
		}
		//再殺红球 
		//2. 特殊的概率
		double r1 = CrawlBalls.specialRateRed01(balls);
		System.out.println("当期开奖的兰号，如为偶数时，乘上2，再加02，计算的结果在下一期有可能不出"+r1);
		double r2 = CrawlBalls.specialRateRed02(balls);
		System.out.println("当期开奖的蓝号，如为奇数时，乘上5，再加+02，计算的结果在下一期有可能不出。"+r2);
		double r3 = CrawlBalls.specialRateRed03(balls);
		System.out.println("开奖红号按从小到大排序(第三位+第六位)除以33的余数+14,如果结果大于33,就减去33。"+r3);
		double r4 = CrawlBalls.specialRateRed04(balls);
		System.out.println("上期红1位尾加上期红2位尾。 "+r4);
		
		double r5 = CrawlBalls.specialRateRed05(balls);
		System.out.println("上期蓝码+上上期蓝码 "+r5);
		int r5kill = beforeCurrent.getBallNum().getBlue() + current.getBallNum().getBlue();
		putUpper(result, r5kill, r5, ratio);
		
		double r6 = CrawlBalls.specialRateRed06(balls);
		System.out.println("33-上上期蓝码 "+r6);
		int r6kill = 33 - beforeCurrent.getBallNum().getBlue();
		putUpper(result, r6kill, r6, ratio);
		
		double r7 = CrawlBalls.specialRateRed07(balls);
		System.out.println("上上期蓝码 "+r7);
		int r7kill =  beforeCurrent.getBallNum().getBlue();
		putUpper(result, r7kill, r7, ratio);
		
		double r8 = CrawlBalls.specialRateRed08(balls);
		System.out.println("33-上期蓝码 "+r8);
		int r8kill =  33-current.getBallNum().getBlue();
		putUpper(result, r8kill, r8, ratio);
		
		double r9 = CrawlBalls.specialRateRed09(balls);
		System.out.println("上期蓝码 "+r9);
		int r9kill =  current.getBallNum().getBlue();
		putUpper(result, r9kill, r9, ratio);
		
		double r10 = CrawlBalls.specialRateRed10(balls);
		System.out.println("上期红1位尾加上期红2位尾 "+r10);
		int r10kill =  CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(0), true) + CrawlBalls.calTail(new ArrayList<Integer>(current.getBallNum().getReds()).get(1), true);
		putUpper(result, r10kill, r10, ratio);
		
		int r1kill = current.getBallNum().getBlue()*2+2;
		putUpper(result, r1kill, r1, ratio);
		
		int r2kill = current.getBallNum().getBlue()*5+2;
		putUpper(result, r2kill, r2, ratio);
		
		ArrayList<Integer> reds = new ArrayList<Integer>(current.getBallNum().getReds());
		int r3kill = (reds.get(2)+ reds.get(5))%33 +14;
		putUpper(result, r3kill, r3, ratio);
		
		int r4kill = CrawlBalls.calTailRelated(reds.get(0), true, reds.get(1), true);
		putUpper(result, r4kill, r4, ratio);

		return result;
	}

	private void putUpper(Map<Integer, Double> result, int ball, double ratio,
			double up) {
		if(ratio >= up)
		{
			Double exists = result.get(ball);
			if(null == exists)
				result.put(ball, ratio);
			else
			{
				if(exists < ratio)
					result.put(ball, ratio);
			}
		}
		
	}
	
	 
}
