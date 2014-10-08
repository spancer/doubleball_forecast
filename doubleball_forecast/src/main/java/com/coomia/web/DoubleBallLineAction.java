package com.coomia.web;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

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
import com.opensymphony.xwork2.ActionSupport;

public class DoubleBallLineAction extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7869583360099392162L;
	// 用于输出统计图表的属性必须是chart
    private JFreeChart chart;

    // 返回JFreeChart统计图表的getter方法
    public JFreeChart getChart() {
    	DefaultCategoryDataset dataset = getDataset();
    	//chart = ChartFactory.createLineChart("双色球统计历年走势图", "", "百分比", dataset, PlotOrientation.VERTICAL, true, false, false);
    	chart = createChart(dataset, "双色球统计历年走势图", "百分比" );
		
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

    // 返回饼图的底层Dataset的工具方法
    private static DefaultCategoryDataset getDataset() {
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	int endYear = 2014;
    	List<DoubleBall> allBalls = CrawlBalls.crawlAll(endYear);
    	for (int year = 2003; year <=endYear; year ++) 
		{
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
			dataset.addValue(Double.parseDouble(odd_even_blue_year.get("rate").toString()), "蓝球奇偶", String.valueOf(year));
			/**
			 * 统计大小比例
			 */
			Map<String, Object> small_big_blue_year = CrawlBalls.statisticBigSmall(yearBalls, true);
			System.out.println(small_big_blue_year.get("print"));
			dataset.addValue(Double.parseDouble(small_big_blue_year.get("rate").toString()), "蓝球小-大", String.valueOf(year));
			/**
			 * 对望码比例
			 */
			Map<String, Object> dwm_blue_year = CrawlBalls.statisticDWM(yearBalls, true);
			System.out.println(dwm_blue_year.get("print"));
			dataset.addValue(Double.parseDouble(dwm_blue_year.get("rate").toString()), "蓝球对望码", String.valueOf(year));
			/**
			 * 重复码比例
			 */
			Map<String, Object> cfm_blue_year = CrawlBalls.statisticCFM(yearBalls, true);
			System.out.println(cfm_blue_year.get("print"));
			dataset.addValue(Double.parseDouble(cfm_blue_year.get("rate").toString()), "蓝球重复码", String.valueOf(year));
			/**
			 * 黄金 码
			 */
			Map<String, Object> golenm_blue_year = CrawlBalls.statisticGolden(yearBalls, true);
			System.out.println(golenm_blue_year.get("print"));
			dataset.addValue(Double.parseDouble(golenm_blue_year.get("rate").toString()), "蓝球黄金码", String.valueOf(year));
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
			//dataset.addValue(Double.parseDouble(avg_sum_blue_year.get("rate").toString()), "蓝球大小的平均值", String.valueOf(year));
			/**
			 * 蓝球 尾数平均值
			 */
			Map<String, Object> avg_tail_sum_blue_year = CrawlBalls.statisticAvgTailSum(yearBalls, true);
			System.out.println(avg_tail_sum_blue_year.get("print"));
			//dataset.addValue(Double.parseDouble(avg_tail_sum_blue_year.get("rate").toString()), "蓝球 尾数平均值", String.valueOf(year));
			
			/**
			 * 统计红球
			 */
			System.out.println("---统计红球"+year+"---");
			/**
			 * 统计奇偶比例
			 */
			Map<String, Object> odd_even_red_year = CrawlBalls.statisticOddEven(yearBalls, false);
			System.out.println(odd_even_red_year.get("print"));
			dataset.addValue(Double.parseDouble(odd_even_red_year.get("rate").toString()), "红球统计奇偶比例", String.valueOf(year));
			/**
			 * 统计大小比例
			 */
			Map<String, Object> small_big_red_year = CrawlBalls.statisticBigSmall(yearBalls, false);
			System.out.println(small_big_red_year.get("print"));
			dataset.addValue(Double.parseDouble(small_big_red_year.get("rate").toString()), "红球统计小大比例", String.valueOf(year));
			

			/**
			 * 对望码比例
			 */
			Map<String, Object> dwm_red_year = CrawlBalls.statisticDWM(yearBalls, false);
			System.out.println(dwm_red_year.get("print"));
			dataset.addValue(Double.parseDouble(dwm_red_year.get("rate").toString()), "红球对望码比例", String.valueOf(year));
			
			/**
			 * 重复码比例
			 */
			Map<String, Object> cfm_red_year = CrawlBalls.statisticCFM(yearBalls, false);
			System.out.println(cfm_red_year.get("print"));
			dataset.addValue(Double.parseDouble(cfm_red_year.get("rate").toString()), "红球重复码比例", String.valueOf(year));
			
			/**
			 * 黄金 码
			 */
			Map<String, Object> golenm_red_year = CrawlBalls.statisticGolden(yearBalls, false);
			System.out.println(golenm_red_year.get("print"));
			dataset.addValue(Double.parseDouble(golenm_red_year.get("rate").toString()), "红球黄金 码", String.valueOf(year));
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
			//dataset.addValue(Double.parseDouble(avg_sum_red_year.get("rate").toString()), "红球和大小的 平均值", String.valueOf(year));
			
			/**
			 * 红球 尾数和平均值
			 */
			Map<String, Object> avg_tail_sum_red_year = CrawlBalls.statisticAvgTailSum(yearBalls, false);
			System.out.println(avg_tail_sum_red_year.get("print"));
			System.out.println("*****结束统计年份："+year+"******************************");
			//dataset.addValue(Double.parseDouble(avg_tail_sum_red_year.get("rate").toString()), "红球 尾数和平均值", String.valueOf(year));
			
		}
    	
        return dataset;

    }
}
