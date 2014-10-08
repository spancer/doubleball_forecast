package com.coomia.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.model.DoubleBall;
import com.coomia.model.ForcastBall;


public interface IDoubleBallForcastService {
	
	/**
	 * 根据一组球，来预测这些球（连续球）的下一期结果。
	 * @param balls
	 * @return
	 */
	public ForcastBall forcastNextBall(List<DoubleBall> balls, double red_ratio, double blue_ratio);
	
	/**
	 * 返回红球杀球的球号及概率
	 * @param balls
	 * @return
	 */
	public Map<Integer,Double> collectRedKilledBallsMapBelowRatio(List<DoubleBall> balls, double ratio);
	
	/**
	 * 返回概率大于ratio 的球集合
	 * @param balls
	 * @param ratio
	 * @return
	 */
	public Map<Integer,Double> collectRedKilledBallsMapUpRatio(List<DoubleBall> balls, double ratio);
	
	/**
	 * 返回蓝球球号 杀的球 及概率
	 * @param balls
	 * @return
	 */
	public Map<Integer,Double> blueKilledBallsMap(List<DoubleBall> balls);
	 
	 /**
	  * 我赢网站杀球后的预测
	  * @param forcastBall
	  * @return
	  */
	 ForcastBall woyingKill(ForcastBall forcastBall);
	 
	 /**
	  * 加上一些可能会出的球。
	  * @param forcastBall
	  * @param current 当前
	  * @return
	  */
	 ForcastBall addShouldKeep(ForcastBall forcastBall, DoubleBall current) ;
	 
	 /**
	  * 根据ratio的值，即概率来添加必会出的球。
	  * @param forcastBall
	  * @param ratio
	  * @return
	  */
	 ForcastBall addMust(ForcastBall forcastBall, double redRatioKeep, double blueRatioKeep, double redRatioKill, double blueRatioKill, List<DoubleBall> balls);
	 
	/**
	 * 红球 球规格（大或小 个数平均值走势）
	 * @param start 表示开始位置，如2003001 或2014001
	 * @param end 表示结束位置 如当前期 2014108
	 * 表示连续
	 * @return
	 */
	
	double analysisSmallTrend(int start, int end);
	
	/**
	 * 红球 指定期奇偶
	 * @param interval
	 * @param start
	 * @param end
	 * @return
	 */
	double analysisOddTrend(int start, int end);
	
	/**
	 * 红球  黄金码个数
	 * @param interval
	 * @param start
	 * @param end
	 * @return
	 */
	double analysisGoldenTrend(int start, int end);
	
	/**
	 * 红球 对望码个数
	 * @param interval
	 * @param start
	 * @param end
	 * @return
	 */
	double analysisDWMTrend(int start, int end);
	
	/**
	 * 红球 重复码个数
	 * @param interval
	 * @param start
	 * @param end
	 * @return
	 */
	double analysisCFMTrend(int start, int end);
	
	
	double analysisZone41(int start, int end);
	
	double analysisZone42(int start, int end);
	
	double analysisZone43(int start, int end);
	
	double analysisZone44(int start, int end);
	
	double analysisZone31(int start, int end);
	
	double analysisZone32(int start, int end);
	
	double analysisZone33(int start, int end);
	
	
	double analysisZone4mod1(int start, int end);
	
	double analysisZone4mod2(int start, int end);
	
	double analysisZone4mod3(int start, int end);
	
	double analysisZone4mod4(int start, int end);
	
	double analysisZone3mod1(int start, int end);
	
	double analysisZone3mod2(int start, int end);
	
	double analysisZone3mod3(int start, int end);
	
	
	/**
	 * 蓝球 趋势
	 * @param start
	 * @param end
	 * @param forcastIndex
	 * @return
	 */
	double analysisBlueOddTrend(int start, int end);
	
	double analysisBlueSmallTrend(int start, int end);
	
	double rateBlueOdd(int start, int end);
	
	double rateBlueSmall(int start, int end);
	
	/**
	 * 返回一组下期肯定会出现2次以上的球
	 * @return
	 */
	Set<Integer> mostRedBalls(DoubleBall current);
	
	double analysisMostInBalls(int start, int end);
	
	public double analysisSmallTrend(List<DoubleBall> data);
	
	public double analysisOddTrend(List<DoubleBall> data);
	
	public double analysisGoldenTrend(List<DoubleBall> data);
	
	public double analysisDWMTrend(List<DoubleBall> data);
	
	public double analysisCFMTrend(List<DoubleBall> data);
	
	public double analysisBlueOddTrend(List<DoubleBall> data) ;
	
	public double analysisMostInBalls(List<DoubleBall> data ) ;

	public ForcastBall finalForcast(ForcastBall forcastBall);

	public ForcastBall addShouldReds(ForcastBall forcastBall,
			List<DoubleBall> balls_2014, double belowRetio);
	
	
}
