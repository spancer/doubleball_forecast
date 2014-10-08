package com.coomia.web;

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import com.coomia.crawler.CrawlBalls;
import com.coomia.model.DoubleBall;
import com.coomia.service.IDoubleBallForcastService;
import com.coomia.service.impl.DoubleBallForcastService;
import com.opensymphony.xwork2.ActionSupport;

public class DoubleBallForcastAction extends ActionSupport {
    /**
	 * 
	 */
	
	
	private static final long serialVersionUID = -7869583360099392162L;
	// 用于输出统计图表的属性必须是chart
    private JFreeChart chart;
    private String type;
    private String forcast;
    private IDoubleBallForcastService doubleBallForcastService = new DoubleBallForcastService(); 
    public String getForcast() {
		return forcast;
	}

	public void setForcast(String forcast) {
		this.forcast = forcast;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 返回JFreeChart统计图表的getter方法
    public JFreeChart getChart() {
    	DefaultCategoryDataset dataset = getDataset(type);
    	chart = createChart(dataset, "双色球预测走势图", "球个数" );
        return chart;
    }
    
    
    public JFreeChart createChart(DefaultCategoryDataset lineDataset, String title, String ylabel)
    {
        // 构造数据集合
        JFreeChart chart = ChartFactory.createLineChart(title, // 标题
            "", // X坐标
            ylabel, // Y坐标
            lineDataset, // 数据集合
            PlotOrientation.VERTICAL, // orientation
            true, // 是否显示legend
            true, // 是否显示tooltip
            false // 是否使用url链接
        );
        // 字体是否模糊边界
        chart.setTextAntiAlias(false);
        chart.setBackgroundPaint(Color.WHITE);
        // 设置图标题的字体重新设置title
        Font font = new Font("隶书", Font.BOLD, 25);
        TextTitle chartTitle = new TextTitle(title);
        chartTitle.setFont(font);
        chart.setTitle(chartTitle);
       
        // 背景色
        chart.setBackgroundPaint(Color.WHITE);
        // 配置字体（解决中文乱码的通用方法）
        Font xfont = new Font("宋体", Font.PLAIN, 16); // X轴
        Font yfont = new Font("宋体", Font.PLAIN, 16); // Y轴
        Font kfont = new Font("宋体", Font.PLAIN, 14); // 底部
        Font titleFont = new Font("隶书", Font.BOLD, 20); // 图片标题
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.getDomainAxis().setLabelFont(xfont);
        categoryPlot.getDomainAxis().setLabelFont(xfont);
        categoryPlot.getRangeAxis().setLabelFont(yfont);
        chart.getLegend().setItemFont(kfont);
        chart.getTitle().setFont(titleFont);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        // 设置网格显示
        // plot.setDomainGridlinesVisible(true);
        // x轴 // 分类轴网格是否可见
        categoryPlot.setDomainGridlinesVisible(true);
        // y轴 //数据轴网格是否可见
        categoryPlot.setRangeGridlinesVisible(true);
        // 设置网格竖线颜色
        categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        // 设置网格横线颜色
        categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        // 没有数据时显示的文字说明
        categoryPlot.setNoDataMessage("没有数据显示");
        // 设置曲线图与xy轴的距离
        categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
        // 设置面板字体
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
        // 取得Y轴
        NumberAxis rangeAxis = (NumberAxis)categoryPlot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        // rangeAxis.setUpperMargin(0.20);
        // rangeAxis.setLabelAngle(Math.PI / 2.0);
        // 取得X轴
        CategoryAxis categoryAxis = (CategoryAxis)categoryPlot.getDomainAxis();
        // 设置X轴坐标上的文字
        categoryAxis.setTickLabelFont(labelFont);
        // 设置X轴的标题文字
        categoryAxis.setLabelFont(labelFont);
        // 横轴上的 Lable 45度倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // 设置距离图片左端距离
        categoryAxis.setLowerMargin(0.0);
        // 设置距离图片右端距离
        categoryAxis.setUpperMargin(0.0);
        // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)categoryPlot.getRenderer();
        lineandshaperenderer.setBaseShapesVisible(true);
        // series 点（即数据点）可见
        lineandshaperenderer.setBaseLinesVisible(true);
        // series 点（即数据点）间有连线可见 显示折点数据
        lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineandshaperenderer.setBaseItemLabelsVisible(true);
       
        return chart;
    }

    public static void analysis(List<DoubleBall> yearBalls)
	{
		/**
		 * 统计蓝球
		 */
		System.out.println("---统计蓝球 ---");
		/**
		 * 统计奇偶比例
		 */
		Map<String, Object> odd_even_blue_year = CrawlBalls.statisticOddEven(yearBalls, true);
		System.out.println(odd_even_blue_year.get("print"));
		
		/**
		 * 统计大小比例
		 */
		Map<String, Object> small_big_blue_year = CrawlBalls.statisticBigSmall(yearBalls, true);
		System.out.println(small_big_blue_year.get("print"));
		
		/**
		 * 对望码比例
		 */
		Map<String, Object> dwm_blue_year = CrawlBalls.statisticDWM(yearBalls, true);
		System.out.println(dwm_blue_year.get("print"));
		
		/**
		 * 重复码比例
		 */
		Map<String, Object> cfm_blue_year = CrawlBalls.statisticCFM(yearBalls, true);
		System.out.println(cfm_blue_year.get("print"));
		
		/**
		 * 黄金 码
		 */
		Map<String, Object> golenm_blue_year = CrawlBalls.statisticGolden(yearBalls, true);
		System.out.println(golenm_blue_year.get("print"));
		/**
		 * 4区分区
		 */
		Map<String, Object> zone_4_continuous_year = CrawlBalls.statistic4zone(yearBalls, true, true);
		System.out.println(zone_4_continuous_year.get("print"));
		Map<String, Object> zone_4_continuous_year_not = CrawlBalls.statistic4zone(yearBalls, true, false);
		System.out.println(zone_4_continuous_year_not.get("print"));
		
		/**
		 * 蓝球大小的 平均值
		 */	
		Map<String, Object> avg_sum_blue_year = CrawlBalls.statisticAvgSum(yearBalls, true);
		System.out.println(avg_sum_blue_year.get("print"));
		
		/**
		 * 蓝球 尾数平均值
		 */
		Map<String, Object> avg_tail_sum_blue_year = CrawlBalls.statisticAvgTailSum(yearBalls, true);
		System.out.println(avg_tail_sum_blue_year.get("print"));
		
		/**
		 * 统计红球
		 */
		System.out.println("---统计红球 ---");
		/**
		 * 统计奇偶比例
		 */
		Map<String, Object> odd_even_red_year = CrawlBalls.statisticOddEven(yearBalls, false);
		System.out.println(odd_even_red_year.get("print"));
		
		/**
		 * 统计大小比例
		 */
		Map<String, Object> small_big_red_year = CrawlBalls.statisticBigSmall(yearBalls, false);
		System.out.println(small_big_red_year.get("print"));
		

		/**
		 * 对望码比例
		 */
		Map<String, Object> dwm_red_year = CrawlBalls.statisticDWM(yearBalls, false);
		System.out.println(dwm_red_year.get("print"));
		
		/**
		 * 重复码比例
		 */
		Map<String, Object> cfm_red_year = CrawlBalls.statisticCFM(yearBalls, false);
		System.out.println(cfm_red_year.get("print"));
		
		/**
		 * 黄金 码
		 */
		Map<String, Object> golenm_red_year = CrawlBalls.statisticGolden(yearBalls, false);
		System.out.println(golenm_red_year.get("print"));
		/**
		 * 3区红球分区 连续
		 */
		Map<String, Object> zone_3_continus_year = CrawlBalls.statistic3zone(yearBalls, true);
		System.out.println(zone_3_continus_year.get("print"));
		Map<String, Object> zone_3_continus_year_not = CrawlBalls.statistic3zone(yearBalls, false);
		System.out.println(zone_3_continus_year_not.get("print"));
		
		/**
		 * 4区分区
		 */
		Map<String, Object> zone_4_continuous_red_year = CrawlBalls.statistic4zone(yearBalls, false, true);
		System.out.println(zone_4_continuous_red_year.get("print"));
		Map<String, Object> zone_4_continuous_red_year_not = CrawlBalls.statistic4zone(yearBalls, false, false);
		System.out.println(zone_4_continuous_red_year_not.get("print"));
		
		/**
		 * 红球和大小的 平均值
		 */	
		Map<String, Object>  avg_sum_red_year = CrawlBalls.statisticAvgSum(yearBalls, false);
		System.out.println(avg_sum_red_year.get("print"));
		
		/**
		 * 红球 尾数和平均值
		 */
		Map<String, Object> avg_tail_sum_red_year = CrawlBalls.statisticAvgTailSum(yearBalls, false);
		System.out.println(avg_tail_sum_red_year.get("print"));
	}
    
    // 返回饼图的底层Dataset的工具方法
    private DefaultCategoryDataset getDataset(String type) {
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	int endYear = 2014;
    	List<DoubleBall> allBalls = CrawlBalls.crawlAll(endYear);
    	/**
    	 * 统计2014年数据
    	 * 
    	 */
    	int year = 2014;
		int currentSize = 0;
		double blue_odd_even = 0; //蓝球：奇:偶
		double blue_small_big = 0; //蓝球：小:大
		int blue_dwm_count = 0; //蓝球：对望码个数
		int blue_cfm_count = 0; //蓝球：重复码个数
		int blue_golden_count = 0;//蓝球：黄金码个数
		double blue_avg_sum = 0; //蓝球：和平均值
		double blue_tail_avg_sum = 0; //蓝球：尾数和平均值
		String blue_4zone_continous = "";
		String blue_4zone_continous_not = "";
		
		double red_odd_even = 0; //蓝球：奇:偶
		double red_small_big = 0; //蓝球：小:大
		int red_dwm_count = 0; //蓝球：对望码个数
		int red_cfm_count = 0; //蓝球：重复码个数
		int red_golden_count = 0;//蓝球：黄金码个数
		double red_avg_sum = 0; //蓝球：和平均值
		double red_tail_avg_sum = 0; //蓝球：尾数和平均值
		String red_4zone_continous = "";
		String red_4zone_continous_not = "";
		String red_3zone_continous = "";
		String red_3zone_continous_not = "";
    	
		List<DoubleBall> yearBalls = CrawlBalls.rangeBalls(allBalls, year);
    	/**
		 * 输出年份
		 */
		System.out.println("*****开始统计年份："+year+"********");
		
		/**
		 * 统计蓝球
		 */
		System.out.println("---统计蓝球"+year+"---");
		/**
		 * 统计奇偶比例
		 */
		Map<String, Object> odd_even_blue_year = CrawlBalls.statisticOddEven(yearBalls, true);
		System.out.println(odd_even_blue_year.get("print"));
		
		/**
		 * 统计大小比例
		 */
		Map<String, Object> small_big_blue_year = CrawlBalls.statisticBigSmall(yearBalls, true);
		System.out.println(small_big_blue_year.get("print"));
		
		/**
		 * 对望码比例
		 */
		Map<String, Object> dwm_blue_year = CrawlBalls.statisticDWM(yearBalls, true);
		System.out.println(dwm_blue_year.get("print"));
		
		/**
		 * 重复码比例
		 */
		Map<String, Object> cfm_blue_year = CrawlBalls.statisticCFM(yearBalls, true);
		System.out.println(cfm_blue_year.get("print"));
		
		/**
		 * 黄金 码
		 */
		Map<String, Object> golenm_blue_year = CrawlBalls.statisticGolden(yearBalls, true);
		System.out.println(golenm_blue_year.get("print"));
		/**
		 * 4区分区
		 */
		Map<String, Object> zone_4_continuous_year = CrawlBalls.statistic4zone(yearBalls, true, true);
		System.out.println(zone_4_continuous_year.get("print"));
		Map<String, Object> zone_4_continuous_year_not = CrawlBalls.statistic4zone(yearBalls, true, false);
		System.out.println(zone_4_continuous_year_not.get("print"));
		
		/**
		 * 蓝球大小的 平均值
		 */	
		Map<String, Object> avg_sum_blue_year = CrawlBalls.statisticAvgSum(yearBalls, true);
		System.out.println(avg_sum_blue_year.get("print"));
		
		/**
		 * 蓝球 尾数平均值
		 */
		Map<String, Object> avg_tail_sum_blue_year = CrawlBalls.statisticAvgTailSum(yearBalls, true);
		System.out.println(avg_tail_sum_blue_year.get("print"));
		
		/**
		 * 统计红球
		 */
		System.out.println("---统计红球"+year+"---");
		/**
		 * 统计奇偶比例
		 */
		Map<String, Object> odd_even_red_year = CrawlBalls.statisticOddEven(yearBalls, false);
		System.out.println(odd_even_red_year.get("print"));
		
		/**
		 * 统计大小比例
		 */
		Map<String, Object> small_big_red_year = CrawlBalls.statisticBigSmall(yearBalls, false);
		System.out.println(small_big_red_year.get("print"));
		

		/**
		 * 对望码比例
		 */
		Map<String, Object> dwm_red_year = CrawlBalls.statisticDWM(yearBalls, false);
		System.out.println(dwm_red_year.get("print"));
		
		/**
		 * 重复码比例
		 */
		Map<String, Object> cfm_red_year = CrawlBalls.statisticCFM(yearBalls, false);
		System.out.println(cfm_red_year.get("print"));
		
		/**
		 * 黄金 码
		 */
		Map<String, Object> golenm_red_year = CrawlBalls.statisticGolden(yearBalls, false);
		System.out.println(golenm_red_year.get("print"));
		/**
		 * 3区红球分区 连续
		 */
		Map<String, Object> zone_3_continus_year = CrawlBalls.statistic3zone(yearBalls, true);
		System.out.println(zone_3_continus_year.get("print"));
		Map<String, Object> zone_3_continus_year_not = CrawlBalls.statistic3zone(yearBalls, false);
		System.out.println(zone_3_continus_year_not.get("print"));
		
		/**
		 * 4区分区
		 */
		Map<String, Object> zone_4_continuous_red_year = CrawlBalls.statistic4zone(yearBalls, false, true);
		System.out.println(zone_4_continuous_red_year.get("print"));
		Map<String, Object> zone_4_continuous_red_year_not = CrawlBalls.statistic4zone(yearBalls, false, false);
		System.out.println(zone_4_continuous_red_year_not.get("print"));
		
		/**
		 * 红球和大小的 平均值
		 */	
		Map<String, Object>  avg_sum_red_year = CrawlBalls.statisticAvgSum(yearBalls, false);
		System.out.println(avg_sum_red_year.get("print"));
		
		/**
		 * 红球 尾数和平均值
		 */
		Map<String, Object> avg_tail_sum_red_year = CrawlBalls.statisticAvgTailSum(yearBalls, false);
		System.out.println(avg_tail_sum_red_year.get("print"));
		System.out.println("*****结束统计年份："+year+"******************************");
		if(year == endYear)
		{
			currentSize = yearBalls.size(); //当前年已出期数
			blue_odd_even = Double.parseDouble(odd_even_blue_year.get("rate").toString());
			blue_small_big = Double.parseDouble(small_big_blue_year.get("rate").toString());
			blue_dwm_count = Integer.parseInt(dwm_blue_year.get("count").toString());
			blue_cfm_count = Integer.parseInt(cfm_blue_year.get("count").toString());
			blue_golden_count = Integer.parseInt(golenm_blue_year.get("count").toString());
			blue_avg_sum = Double.parseDouble(avg_sum_blue_year.get("rate").toString());
			blue_tail_avg_sum = Double.parseDouble(avg_tail_sum_blue_year.get("rate").toString());
			blue_4zone_continous = zone_4_continuous_year.get("count").toString();
			blue_4zone_continous_not = zone_4_continuous_year_not.get("count").toString();
			red_odd_even = Double.parseDouble(odd_even_red_year.get("rate").toString());
			red_small_big = Double.parseDouble(small_big_red_year.get("rate").toString());
			red_dwm_count = Integer.parseInt(dwm_red_year.get("count").toString());
			red_cfm_count = Integer.parseInt(cfm_red_year.get("count").toString());
			red_golden_count = Integer.parseInt(golenm_red_year.get("count").toString());
			red_avg_sum = Double.parseDouble(avg_sum_red_year.get("rate").toString());
			red_tail_avg_sum = Double.parseDouble(avg_tail_sum_red_year.get("rate").toString());
			if(type.trim().equals("avg"))
				dataset.addValue(red_small_big, "小号个数", "2014年均");
			if(type.trim().equals("avg"))
				dataset.addValue(red_odd_even, "奇号个数", "2014年均");
			if(type.trim().equals("avg"))
				dataset.addValue(Double.parseDouble(dwm_red_year.get("rate").toString()), "对望码个数", "2014年均");
			if(type.trim().equals("avg"))
				dataset.addValue(Double.parseDouble(cfm_red_year.get("rate").toString()), "重复码个数", "2014年均");
			if(type.trim().equals("avg"))
				dataset.addValue(Double.parseDouble(golenm_red_year.get("rate").toString()), "黄金码个数", "2014年均");
			//dataset.addValue(red_avg_sum, "红球和平均值", "2014年均");
			//dataset.addValue(red_tail_avg_sum, "红球尾和平均值", "2014年均");
			
			red_3zone_continous = zone_3_continus_year.get("count").toString();
			red_3zone_continous_not = zone_3_continus_year_not.get("count").toString();
			red_4zone_continous = zone_4_continuous_red_year.get("count").toString();
			red_4zone_continous_not = zone_4_continuous_red_year_not.get("count").toString();
			
			
		}
    	
    	/**
    	 * end 统计2014年数据
    	 */
    	int index = allBalls.size() -1;
		DoubleBall current = allBalls.get(index);
    	//取最近10期， 进行统计
		List<DoubleBall> recentBalls = CrawlBalls.rangeBalls(allBalls, current.getNum()-55, current.getNum());
		System.out.println("********开始统计最近10期数据************");
		analysis(recentBalls);
		System.out.println("********结束统计最近10期数据************");
		
		/**
		 * 概率打印完毕====================================================================================
		 * 开始对近10的球 结合概率进行比较
		 */
		
		for (int i =0; i< recentBalls.size(); i++) 
		{
			DoubleBall doubleBall = recentBalls.get(i);
			int num = doubleBall.getNum();
			System.out.println("第"+num +"期");
			System.out.println("奇号个数："+doubleBall.getOdd());
			if(type.trim().equals("odd"))
				dataset.addValue(doubleBall.getOdd(), "奇号个数, 趋势:"+doubleBallForcastService.analysisOddTrend(recentBalls), String.valueOf(num));
			System.out.println("小号个数："+doubleBall.getSmall());
			if(type.trim().equals("small"))
				dataset.addValue(doubleBall.getSmall(), "小号个数, 趋势:"+doubleBallForcastService.analysisSmallTrend(recentBalls), String.valueOf(num));
			System.out.println("对望码个数："+doubleBall.getDWM());
			if(type.trim().equals("dwm"))
				dataset.addValue(doubleBall.getDWM(), "对望码个数, 趋势:"+doubleBallForcastService.analysisDWMTrend(recentBalls), String.valueOf(num));
			System.out.println("重复码个数："+doubleBall.getCFM());
			if(type.trim().equals("cfm"))
				dataset.addValue(doubleBall.getCFM(), "重复码个数, 趋势:"+doubleBallForcastService.analysisCFMTrend(recentBalls), String.valueOf(num));
			System.out.println("黄金码个数："+doubleBall.getgolden());
			if(type.trim().equals("gold"))	
				dataset.addValue(doubleBall.getgolden(), "黄金码个数, 趋势:"+doubleBallForcastService.analysisGoldenTrend(recentBalls), String.valueOf(num));
			System.out.println("连续4区第1区个数："+doubleBall.red4zone1());
			if(type.trim().equals("red41"))
				dataset.addValue(doubleBall.red4zone1(), "连续4区第1区个数", String.valueOf(num));
			System.out.println("连续4区第2区个数："+doubleBall.red4zone2());
			if(type.trim().equals("red42"))
				dataset.addValue(doubleBall.red4zone2(), "连续4区第2区个数", String.valueOf(num));
			System.out.println("连续4区第3区个数："+doubleBall.red4zone3());
			if(type.trim().equals("red43"))
				dataset.addValue(doubleBall.red4zone3(), "连续4区第3区个数", String.valueOf(num));
			System.out.println("连续4区第4区个数："+doubleBall.red4zone4());
			if(type.trim().equals("red44"))
				dataset.addValue(doubleBall.red4zone4(), "连续4区第4区个数", String.valueOf(num));
			System.out.println("取余4区第1区个数："+doubleBall.red4modzone1());
			if(type.trim().equals("red4mod1"))
				dataset.addValue(doubleBall.red4modzone1(), "取余4区第1区个数", String.valueOf(num));
			System.out.println("取余4区第2区个数："+doubleBall.red4modzone2());
			if(type.trim().equals("red4mod2"))
				dataset.addValue(doubleBall.red4modzone2(), "取余4区第2区个数", String.valueOf(num));
			System.out.println("取余4区第3区个数："+doubleBall.red4modzone3());
			if(type.trim().equals("red4mod3"))
				dataset.addValue(doubleBall.red4modzone3(), "取余4区第3区个数", String.valueOf(num));

			if(type.trim().equals("red3mod1"))
				dataset.addValue(doubleBall.getBallNum().getZone3mod1(), "取余3区第1区个数", String.valueOf(num));
			if(type.trim().equals("red3mod2"))
				dataset.addValue(doubleBall.getBallNum().getZone3mod2(), "取余3区第2区个数", String.valueOf(num));
			if(type.trim().equals("red3mod3"))
				dataset.addValue(doubleBall.getBallNum().getZone3mod3(), "取余3区第3区个数", String.valueOf(num));
			if(type.trim().equals("red31"))
				dataset.addValue(doubleBall.getBallNum().getZone31(), "连续3区第1区个数", String.valueOf(num));
			if(type.trim().equals("red32"))
				dataset.addValue(doubleBall.getBallNum().getZone32(), "连续3区第2区个数", String.valueOf(num));
			if(type.trim().equals("red33"))
				dataset.addValue(doubleBall.getBallNum().getZone33(), "连续3区第3区个数", String.valueOf(num));
			
			System.out.println("取余4区第4区个数："+doubleBall.red4modzone4());
			if(type.trim().equals("red4mod4"))
				dataset.addValue(doubleBall.red4modzone4(), "取余4区第4区个数", String.valueOf(num));
			if(type.trim().equals("redsumavg"))
				dataset.addValue(doubleBall.getBallNum().redavgsum(), "红球和平均值", String.valueOf(num));
			if(type.trim().equals("redtailsumavg"))
				dataset.addValue(doubleBall.getBallNum().redtailavgsum(), "红球尾和平均值", String.valueOf(num));
			if(type.trim().equals("blue4zone"))
				dataset.addValue(doubleBall.bluezone4(), "蓝球连续4区走势", String.valueOf(num));
			if(type.trim().equals("blue4modzone"))
				dataset.addValue(doubleBall.bluemodzone4(), "蓝球取余4区走势", String.valueOf(num));
			System.out.println("蓝球连续4区第："+doubleBall.bluezone4()+"区");
			System.out.println("蓝球取余4区第："+doubleBall.bluemodzone4()+"区");
			System.out.println(doubleBall.info());
			if(type.trim().equals("quad1"))
				dataset.addValue(doubleBall.getBallNum().countQuadI(), "象限I球个数", String.valueOf(num));
			if(type.trim().equals("quad2"))
				dataset.addValue(doubleBall.getBallNum().countQuadII(), "象限II球个数", String.valueOf(num));
			if(type.trim().equals("quad3"))
				dataset.addValue(doubleBall.getBallNum().countQuadIII(), "象限III球个数", String.valueOf(num));
			if(type.trim().equals("quad4"))
				dataset.addValue(doubleBall.getBallNum().countQuadIV(), "象限IV球个数", String.valueOf(num));
			if(type.trim().equals("sum34"))
				dataset.addValue(doubleBall.getBallNum().countsum34(), "和值为34球对数", String.valueOf(num));
			
			if(type.trim().equals("chi1530"))
				dataset.addValue(doubleBall.getBallNum().contains15and30(), "15与30互斥的次数", String.valueOf(num));
			if(type.trim().equals("chi2030"))
				dataset.addValue(doubleBall.getBallNum().contains20and30(), "20与30互斥的次数", String.valueOf(num));
			if(type.trim().equals("zs"))
				dataset.addValue(doubleBall.getBallNum().countzs(), "质数个数趋势", String.valueOf(num));
			if(type.trim().equals("hs"))
				dataset.addValue(doubleBall.getBallNum().counths(), "合数个数趋势", String.valueOf(num));
			
			if(type.trim().equals("tail"))
				dataset.addValue(doubleBall.getBallNum().counttail(), "红球尾数个数趋势", String.valueOf(num));
			
			if(i == recentBalls.size()-1)
				break;
			DoubleBallForcastService dbfs = new DoubleBallForcastService();
			Set<Integer> mosts = dbfs.mostRedBalls(doubleBall);
			DoubleBall next = recentBalls.get(i+1);
			Set<Integer> nextReds = next.getBallNum().getReds();
			Set<Integer> inMost = new HashSet<Integer>();
			inMost.addAll(mosts);
			inMost.retainAll(nextReds);
			if(type.trim().equals("mustSize"))
				dataset.addValue(inMost.size(), "红球在一堆中占的个数，官方："+doubleBallForcastService.analysisMostInBalls(recentBalls), String.valueOf(next.getNum()));
			System.out.println(next.getNum()+":"+inMost);
			
		}
    	
        return dataset;

    }
    
}
