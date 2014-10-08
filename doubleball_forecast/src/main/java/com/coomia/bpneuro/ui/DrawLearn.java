package com.coomia.bpneuro.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.coomia.bpneuro.neuro.Point;

public class DrawLearn extends JFrame
{
	// 画曲线面板
	private DrawPanel dp;

	public DrawLearn(int i)
	{
		super("Y" +i +"的学习平均相对均误差曲线");
		this.pack();
		setSize(600, 240);
		setLocation(400, 240 *i);
		setVisible(true);
		addComponentListener(new UnChangeHeight());
				
		dp = new DrawPanel();
		add(dp);
		dp.repaint();
	}

	// 画出到下一个点的线
	public void drawLine(int count, double errRate)
	{
		dp.drawLine(count, errRate);
	}

	private class UnChangeHeight implements ComponentListener
	{
		public void componentHidden(ComponentEvent e)
		{

		}

		public void componentMoved(ComponentEvent arg0)
		{

		}

		public void componentResized(ComponentEvent arg0)
		{
			DrawLearn.this.setSize(DrawLearn.this.getWidth(), 240);
		}

		public void componentShown(ComponentEvent arg0)
		{

		}
	}
}

class DrawPanel extends JPanel
{
	// 前一次画到的点
	private int preX = 0;
	private int preY = 0;

	// 边缘距
	private int edgeDis = 20;

	// 存放没有变换的较大的数据
	private List<Point> points;

	// 存放已变换的的数据
	private List<Point> changPoints;

	// 确认是否用大范围画线,默认为true，在曲线跨越范围较大
	private boolean flag = true;

	// 发生Y轴变换的位置
	private int xFlag = 0;

	public DrawPanel()
	{
		setBackground(Color.WHITE);
		this.setSize(600, 240);
		addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				e.getComponent().repaint();
			}
		});
		setVisible(true);
		points = new LinkedList<Point>();
		changPoints = new LinkedList<Point>();
		repaint();
	}

	public void drawYAxis(Dimension size, Graphics g)
	{
		// 画y轴
		g.drawLine(edgeDis, size.height -edgeDis, edgeDis, edgeDis);
		// 画y轴上的刻度
		for (int i = 0; i <=10; i++)
		{
			// 画标度 
			g.drawLine(edgeDis+size.width,  size.height -edgeDis -(int) (i *160 /10),
					edgeDis -3, size.height -edgeDis -(int) (i *160 /10));
			// 画值
			g.drawString("" +i *10, 2, size.height -edgeDis -(int) (i *160 /10)
					+5);
		}
		// 标识Y轴
		g.drawString("相对误差(%)", 10, 15);
	}

	public void drawXAxis(Dimension size, Graphics g)
	{
		// 画X轴
		g.drawLine(edgeDis, size.height -edgeDis, size.width -edgeDis,
				size.height -edgeDis);
		// 画x轴上的刻度
		for (int i = 1; i <20; i++)
		{
			// 画标度
			g.drawLine(edgeDis +i *30 *(600 -2 *edgeDis) /600, 2*edgeDis+13
					-edgeDis, edgeDis +i *30 *(600 -2 *edgeDis) /600,
					size.height -edgeDis +5);
			// 画值
			g.drawString("" +i *30, edgeDis +i *30 *(600 -2 *edgeDis) /600 -10,
					size.height -edgeDis +15);
		}
		// 标识X轴
		g.drawString("次数", 565, 200);
	}

	public void changeYAxis(Dimension size, Graphics g)
	{
		// y轴要移动的距离
		int tempX = xFlag *(600 -2 *edgeDis) /600;
		// 画变换后的y轴
		g.drawLine(tempX +edgeDis, size.height -edgeDis, tempX +edgeDis, 20);
		// 画y轴上的刻度
		for (int i = 1; i <=5; i++)
		{
			// 画标度
			g.drawLine(tempX +edgeDis, size.height -edgeDis -(int) (i *160 /5),
					tempX +edgeDis +503, size.height -edgeDis -(int) (i *160 /5));
			// 画值
			g.drawString("" +i, tempX +edgeDis +3, size.height -edgeDis
					-(int) (i *160 /5) +5);
		}
	}

	public void drawLearnCurve(Dimension size, Graphics g, boolean ischanged)
	{
		Iterator<Point> it;
		if (ischanged)
		{
			it = changPoints.iterator();
		} else
		{
			it = points.iterator();
		}
		// 有元素则画曲线，如果没有元素，则返回
		if (it.hasNext())
		{
			Point p1 = it.next();
			Point p2;
			while (it.hasNext())
			{
				p2 = it.next();
				g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				p1 = p2;
			}
		}
	}

	public void paint(Graphics g)
	{
		// 画Yi的平均误差曲线的x轴y轴：x轴表平均误差值，y表示学习的次数
		super.paintComponents(g);
		// super.paint(g);
		setBackground(Color.WHITE);
		Dimension size = getSize();
		g.setColor(Color.BLACK);
		// 画Y轴
		drawYAxis(size, g);
		// 画x轴
		drawXAxis(size, g);
		// 如果平均相对误差已很少则更换Y轴
		if (!flag)
		{
			// 以下画更换的Y轴
			changeYAxis(size, g);
		}
		// 以下画学习曲线
		g.setColor(Color.blue);
		// 画没有变换坐标的误差曲线
		drawLearnCurve(size, g, false);
		// 达到变换曲线的位置
		if (!flag)
		{
			// 画没有变换坐标的误差曲线
			drawLearnCurve(size, g, true);
		}
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public void drawLine(int count, double errRate)
	{
		if (flag &&errRate >=0.05)// true表示用大范围的曲线
		{
			largeScaleDraw(count, errRate);
		} else
		{
			// 画小范围的曲线
			if (flag)
			{
				// 第一次进入精度较小的区间
				xFlag = count;
				flag = false;
				preX = 0;
				preY = 0;
				repaint();
			}
			smallScaleDraw(count, errRate);
		}
	}

	private void largeScaleDraw(int count, double errRate)
	{
		int x = edgeDis +(int) ((600.0 -2.0 *edgeDis) *((double) count /600.0));
		int y =212 -edgeDis -(int) (errRate *160);
		if (preX ==0 &&preY ==0)
		{
			preX = x;
			preY = y;
			Point p = new Point(x, y);
			points.add(p);
			repaint();
		} else
		{
			Point p = new Point(x, y);
			points.add(p);
			Graphics g = this.getGraphics();
			g.setColor(Color.BLUE);
			g.drawLine(preX, preY, x, y);
			preX = x;
			preY = y;
		}
	}

	private void smallScaleDraw(int count, double errRate)
	{
		int x = edgeDis +(int) ((600.0 -2.0 *edgeDis) *((double) count /600.0));
		int y =212 -edgeDis -(int) (errRate *160 *20);
		if (preX ==0 &&preY ==0)
		{
			preX = x;
			preY = y;
			Point p = new Point(x, y);
			changPoints.add(p);
			repaint();
		} else
		{
			Point p = new Point(x, y);
			changPoints.add(p);
			Graphics g = this.getGraphics();
			g.setColor(Color.BLUE);
			g.drawLine(preX, preY, x, y);
			preX = x;
			preY = y;
		}
	}
}
