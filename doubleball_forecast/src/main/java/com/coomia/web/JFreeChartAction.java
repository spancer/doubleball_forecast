package com.coomia.web;

import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import com.opensymphony.xwork2.ActionSupport;

public class JFreeChartAction extends ActionSupport {
    // 用于输出统计图表的属性必须是chart
    private JFreeChart chart;

    // 返回JFreeChart统计图表的getter方法
    public JFreeChart getChart() {
        chart = ChartFactory.createPieChart3D("双色球历年", // 图表标题
                getDataset(), // 数据
                true, // 是否显示图例
                false,// 是否显示工具提示
                false// 是否生成URL
                );
        // 重新设置图表标题，改变字体
        chart.setTitle(new TextTitle("机电销量统计图", new Font("黑体", Font.ITALIC, 22)));
        // 取得统计图表的第一个图例
        LegendTitle legendTitle = chart.getLegend(0);
        // 改变图例的字体
        legendTitle.setItemFont(new Font("宋体", Font.BOLD, 14));
        // 获得饼图的Plot对象
        PiePlot3D plot3D = (PiePlot3D) chart.getPlot();
        // 设置饼图各部分的标签字体
        plot3D.setLabelFont(new Font("隶书", Font.BOLD, 18));
        // 设定背景透明度（0-1.0之间）
        plot3D.setBackgroundAlpha(0.9f);
        // 设定前景透明度（0-1.0之间）
        plot3D.setForegroundAlpha(0.5f);
        return chart;
    }

    // 返回饼图的底层Dataset的工具方法
    private DefaultPieDataset getDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("联想笔记本电脑G450", 2000);
        dataset.setValue("索尼手机", 1900);
        dataset.setValue("诺基亚手机", 1980);
        dataset.setValue("戴尔笔记本", 1230);
        return dataset;

    }
}
