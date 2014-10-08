package com.coomia.bpneuro.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.coomia.bpneuro.neuro.Point;

public class DrawTest extends JFrame
{
	private TestPanel tp;

	public DrawTest(String title, int i)
	{
		super(title);
		setSize(620, 280);
		setLocation(400, 240 *i);
		setVisible(true);
		setResizable(false);

		tp = new TestPanel();
		add(tp);
		tp.setVisible(true);
	}

	public void setMixData(double[][] data)
	{
		tp.setMixData(data);
	}
}

class TestPanel extends JScrollPane
{
	// 实际输出、相对误差
	private double[][] mixArray;

	// 回想，泛化执行结果对应的坐标
	private List<Point> expecPoints;

	// 实际值值对应的坐标
	private List<Point> realPoints;

	// 边缘距
	private int edgeDis = 20;

	public TestPanel()
	{
		setSize(600, 240);
		expecPoints = new LinkedList<Point>();
		realPoints = new LinkedList<Point>();
	}

	public void setMixData(double[][] data)
	{
		mixArray = data;
		valueChange();
		// 将曲线画出
		repaint();
	}

	private void valueChange()
	{
		this.setSize(600, 240);
		Dimension size = getSize();
		int width = size.width;
		int height = size.height;
		for (int i = 0; i <mixArray[0].length; i++)
		{
			// 将误差转换为坐标
			int X = (i *(width -2 *edgeDis) /mixArray[0].length) +edgeDis;
			int expY = height -edgeDis
					-(int) (mixArray[0][i] *(height -2 *edgeDis));
			int realY = height -edgeDis
					-(int) (mixArray[1][i] *(height -2 *edgeDis));
			// 将坐标压入链表
			Point p = new Point(X, expY);
			expecPoints.add(p);
			p = new Point(X, realY);
			realPoints.add(p);
		}
	}

	private void drawCurve(Iterator<Point> it, Graphics g)
	{
		Point pFir, pSec;
		if (it.hasNext())
		{
			pFir = it.next();
			while (it.hasNext())
			{
				pSec = it.next();
				g.drawLine(pFir.getX(), pFir.getY(), pSec.getX(), pSec.getY());
				pFir = pSec;
			}
		}
	}

	public void drawYAxis(int height, Graphics g)
	{
		// 画y轴
		g.drawLine(edgeDis, height -edgeDis, edgeDis, edgeDis);
		// 画y轴上的刻度
		for (int i = 0; i <=10; i++)
		{
			// 画标度
			g.drawLine(edgeDis, height -edgeDis
					-(int) (i *(height -2.0 *edgeDis) /10), edgeDis -3, height
					-edgeDis -(int) (i *(height -2.0 *edgeDis) /10));
			// 画值
			g.drawString("" +i, 2, height -edgeDis
					-(int) (i *(height -2.0 *edgeDis) /10) +5);
		}
		// 标识Y轴
		g.drawString("值E-1", 10, 15);
	}

	public void drawXAxis(int height, int width, Graphics g)
	{
		// 画X轴
		g.drawLine(edgeDis, height -edgeDis, width -edgeDis, height -edgeDis);
		// 标识X轴
		g.drawString("n次输入", width -2 *edgeDis, height -edgeDis +10);
		if (mixArray !=null)
		{
			// 数据条数
			int count = mixArray[0].length;
			// 画x轴上的刻度
			for (int i = 0; i <count; i++)
			{
				if (i %10 ==0)
				{
					// 画标度
					g.drawLine(edgeDis +i *(width -2 *edgeDis) /count, height
							-edgeDis, edgeDis +i *(width -2 *edgeDis) /count,
							height -edgeDis +5);
					// 画值
					g.drawString("" +i, edgeDis +i *(width -2 *edgeDis) /count
							-10, height -edgeDis +15);
				}
			}
		}
	}

	private void drawErr(Graphics g)
	{
		double sum=0,avg;
		for(int i=0;i<mixArray[0].length;i++)
		{
//			System.out.println("mixArray[i][2]:i="+i+"value"+mixArray[2][i]);
			sum+=mixArray[2][i];
		}
		avg=sum/mixArray[0].length;
//		System.out.println("mixArray[0].length:"+mixArray[0].length);
		g.drawString("平均相对误差："+avg, 200, 10);
	}

	public void paint(Graphics g)
	{
		super.paintComponents(g);
		setBackground(Color.WHITE);
		this.setSize(600, 240);
		Dimension size = getSize();
		g.setColor(Color.BLACK);
		// 画X轴
		drawXAxis(size.height, size.width, g);
		// 画Y轴
		drawYAxis(size.height, g);
		// 画曲线
		g.setColor(Color.RED);
		// 先画实际运算得的曲线
		Iterator<Point> it = realPoints.iterator();
		drawCurve(it, g);
		// 再画预期曲线
		g.setColor(Color.BLUE);
		it = expecPoints.iterator();
		drawCurve(it, g);
        //写出平均误差
		drawErr(g);
	}

	public void update(Graphics g)
	{
		paint(g);
	}
}
