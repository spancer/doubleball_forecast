package com.coomia.crawler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.model.DoubleBall;
import com.coomia.model.ForcastBall;
import com.coomia.service.IDoubleBallForcastService;
import com.coomia.service.IDoubleBallService;
import com.coomia.service.impl.DoubleBallForcastService;
import com.coomia.service.impl.DoubleBallService;

public class CoomiaDoubleBallForcast {

	
	public static ForcastBall forcast(IDoubleBallForcastService dbfs, List<DoubleBall> balls_2014, double selfRedRatio, double selfBlueRatio, double belowRedRatioKept, double redChooseRatio, double blueChooseRatio, double redKillRatio, double blueKillRatio )
	{
		
		int size = balls_2014.size();
		DoubleBall current = balls_2014.get(size-1); 
		/**
		 * 预测一组球 根据自己算的概率来杀球后的结果
		 */
		ForcastBall forcastBall = dbfs.forcastNextBall(balls_2014, selfRedRatio, selfBlueRatio);
		
		/**
		 * 增加可能性大的球 预测
		 * 操作shouldReds &blue
		 */
		forcastBall = dbfs.addShouldKeep(forcastBall, current);
		
		/**
		 * 杀球的时候有些概率很低，实际上这些球不能杀。
		 * 定义这样一个因子将这些球加入到可能出现的球中。
		 */
		forcastBall = dbfs.addShouldReds(forcastBall, balls_2014, belowRedRatioKept);
		
		/**
		 * 我赢杀球后的预测结果
		 */
		forcastBall = dbfs.woyingKill(forcastBall);
		
		/**
		 * 根据保留因子保留一些必会出的球
		 * 就是出了某号后最可能出的球
		 * 操作mustReds mustBlues
		 */
		forcastBall = dbfs.addMust(forcastBall, redChooseRatio, blueChooseRatio, redKillRatio, blueKillRatio, balls_2014);
		
		/**
		 * 最后的整理运算
		 */
		forcastBall = dbfs.finalForcast(forcastBall);
		return forcastBall;
	}
	
	
	
	public static void main(String[] args) 
	{
		//1. 2014年数据
		IDoubleBallService doubleBallService = new DoubleBallService();
		IDoubleBallForcastService dbfs = new DoubleBallForcastService();
		List<DoubleBall> balls_2014 = doubleBallService.crawlAll();//findByYear(2014);
		double selfRedRatio = 0.8;
		double selfBlueRatio = 0.8;
		double belowRedRatioKept = 0.1;
		double redChooseRatio = 0.4;
		double blueChooseRatio = 0.4;
		double redKillRatio = 0.1;
		double blueKillRatio = 0.1;
		ForcastBall forcast = forcast(dbfs, balls_2014, selfRedRatio, selfBlueRatio, belowRedRatioKept, redChooseRatio, blueChooseRatio, redKillRatio, blueKillRatio);
		System.out.println("酷美亚网络科技为您预测双色球："+forcast.toString());
		
		 
	}
	
	

	public static void printNext(IDoubleBallForcastService dbfs, int start, int end)
	{
		
		double smallSize = dbfs.analysisSmallTrend(start, end);
		System.out.println("预测下期红球小号个数为："+smallSize);
		
		double blue = dbfs.analysisBlueOddTrend(start, end);
		double rateBlueOdd = dbfs.rateBlueOdd(start, end);
		System.out.println("预测下期蓝球奇偶可能为："+((blue > rateBlueOdd) == true?"奇":"偶"));
		
		double bulesmall = dbfs.analysisBlueSmallTrend(start, end);
		double bluesmallrate = dbfs.rateBlueSmall(start, end);
		System.out.println("预测下期蓝球奇偶可能为："+((bulesmall > bluesmallrate) == true?"小":"大"));
		
		double oddSize = dbfs.analysisOddTrend(start, end);
		System.out.println("预测下期红球奇号个数为："+oddSize);
		
		double dwm = dbfs.analysisDWMTrend(start, end);
		System.out.println("预测下期红球对望码个数为："+dwm);
		
		double cfm = dbfs.analysisCFMTrend(start, end);
		System.out.println("预测下期红球重复码个数为："+cfm);
		
		double gold = dbfs.analysisGoldenTrend(start, end);
		System.out.println("预测下期红球黄金码个数为："+gold);
		
		double rzone41 = dbfs.analysisZone41(start, end);
		System.out.println("预测下期红球在连续4区1区个数为："+rzone41);
		
		double rzone42 = dbfs.analysisZone42(start, end);
		System.out.println("预测下期红球在连续4区2区个数为："+rzone42);
		
		double rzone43 = dbfs.analysisZone43(start, end);
		System.out.println("预测下期红球在连续4区3区个数为："+rzone43);
		
		double rzone44 = dbfs.analysisZone44(start, end);
		System.out.println("预测下期红球在连续4区4区个数为："+rzone44);
		
		double rzone4mod1 = dbfs.analysisZone4mod1(start, end);
		System.out.println("预测下期红球在取余4区1区个数为："+rzone4mod1);
		
		double rzone4mod2 = dbfs.analysisZone4mod2(start, end);
		System.out.println("预测下期红球在取余4区2区个数为："+rzone4mod2);
		
		double rzone4mod3 = dbfs.analysisZone4mod3(start, end);
		System.out.println("预测下期红球在取余4区3区个数为："+rzone4mod3);
		
		double rzone4mod4 = dbfs.analysisZone4mod4(start, end);
		System.out.println("预测下期红球在取余4区4区个数为："+rzone4mod4);
		
		double rzone31 = dbfs.analysisZone31(start, end);
		System.out.println("预测下期红球在连续3区1区个数为："+rzone31);
		
		double rzone32 = dbfs.analysisZone32(start, end);
		System.out.println("预测下期红球在连续3区2区个数为："+rzone32);
		
		double rzone33 = dbfs.analysisZone33(start, end);
		System.out.println("预测下期红球在连续3区3区个数为："+rzone33);
		
		double rzone3mod1 = dbfs.analysisZone3mod1(start, end);
		System.out.println("预测下期红球在取余3区1区个数为："+rzone3mod1);
		
		double rzone3mod2 = dbfs.analysisZone3mod2(start, end);
		System.out.println("预测下期红球在取余3区2区个数为："+rzone3mod2);
		
		double rzone3mod3 = dbfs.analysisZone3mod3(start, end);
		System.out.println("预测下期红球在取余3区3区个数为："+rzone3mod3);
		
		double mostInSize = dbfs.analysisMostInBalls(start, end);
		System.out.println("预测下期红球一堆球的个数为："+mostInSize);
	}
}
