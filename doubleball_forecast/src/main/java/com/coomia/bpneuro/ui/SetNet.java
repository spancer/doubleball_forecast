package com.coomia.bpneuro.ui;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class SetNet extends JFrame
{
	// 网络层数和每层神经元个数
	int[] layers;

	// 网络层数设置域
	final JTextField netSizeTe = new JTextField();

	// 每一层神经元个数设置域
	final JTextArea layerSumTe = new JTextArea();

	// 参数设置存放以下三项
	double[] parameter = new double[4];

	// 学习速率设置域,依次存放速率、动量因子、精度要求
	final JTextField rateFactTe = new JTextField();

	// 动量因子设置域
	final JTextField motionFactTe = new JTextField();

	// 精度要求设置域
	final JTextField precisTe = new JTextField();

	// //最多允许学习次数设置域
	final JTextField maxCountTe = new JTextField();

	public SetNet()
	{
		super("设置BP网络");
		// 界面位置大小设置
		setLocation(140, 120);
		setSize(290, 270);
		setLayout(null);
		// 初始化，填写域
		initField();
		// 设置默认的网络
		initlayers();
		// 指示用户不能改变大小
		setResizable(false);
		setVisible(true);
	}

	// 设置默认的网络
	private void initlayers()
	{
		// 初始化网络设置
		layers = new int[4];
		layers[0] = 2;
		layers[1] = 20;
		layers[2] = 10;
		layers[3] = 1;
		// 初始化参数设置
		// 学习速率
		parameter[0] = 0.9;
		// 动量因子
		parameter[1] = 0.7;
		// 误差精度要求
		parameter[2] = 0.00001;
		// 最大允许的循环次数
		parameter[3] = 1000;
	}

	public void initField()
	{
		// 以下设置网络层数
		JLabel netSizeLa = new JLabel("网络层数：");
		netSizeLa.setLocation(40, 10);
		netSizeLa.setSize(80, 20);
		add(netSizeLa);
		netSizeLa.setVisible(true);

		netSizeTe.setLocation(120, 10);
		netSizeTe.setSize(120, 20);
		add(netSizeTe);
		netSizeTe.setText("3");
		netSizeTe.setVisible(true);

		// 以下设置网络层数及每层神经元个数
		JLabel netLayersLa = new JLabel("每层神经元个数设置，以空格分隔:");
		netLayersLa.setLocation(40, 35);
		netLayersLa.setSize(200, 20);
		add(netLayersLa);
		netLayersLa.setVisible(true);

		layerSumTe.setLocation(40, 55);
		layerSumTe.setSize(200, 50);
		layerSumTe.setLineWrap(true);
		add(layerSumTe);
		layerSumTe.setText("2 15 1");
		layerSumTe.setVisible(true);

		// 以下设置学习速率
		JLabel studyRate = new JLabel("学习速率：");
		studyRate.setLocation(40, 110);
		studyRate.setSize(80, 20);
		add(studyRate);
		studyRate.setVisible(true);

		rateFactTe.setLocation(120, 110);
		rateFactTe.setSize(120, 20);
		add(rateFactTe);
		rateFactTe.setText("0.9");
		rateFactTe.setVisible(true);

		// 以下设置动量因子
		JLabel motFactLa = new JLabel("动量因子：");
		motFactLa.setLocation(40, 135);
		motFactLa.setSize(80, 20);
		add(motFactLa);
		motionFactTe.setText("0.7");
		motFactLa.setVisible(true);

		motionFactTe.setLocation(120, 135);
		motionFactTe.setSize(120, 20);
		add(motionFactTe);
		precisTe.setText("0.00001");
		motionFactTe.setVisible(true);

		// 以下为均方差进度设置
		JLabel preciseLa = new JLabel("精度设置：");
		preciseLa.setLocation(40, 160);
		preciseLa.setSize(80, 20);
		add(preciseLa);
		preciseLa.setVisible(true);

		precisTe.setLocation(120, 160);
		precisTe.setSize(120, 20);
		add(precisTe);
		maxCountTe.setText("1000");
		precisTe.setVisible(true);

		// 以下为允许最多循环次数设置
		JLabel maxCountLa = new JLabel("循环次数：");
		maxCountLa.setLocation(40, 185);
		maxCountLa.setSize(80, 20);
		add(maxCountLa);
		maxCountLa.setVisible(true);

		maxCountTe.setLocation(120, 185);
		maxCountTe.setSize(120, 20);
		add(maxCountTe);
		maxCountTe.setVisible(true);

		// 以下为按钮设置
		JButton resetBut = new JButton("重置");
		resetBut.setLocation(120, 210);
		resetBut.setSize(60, 20);
		resetBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				netSizeTe.setText("3");
				layerSumTe.setText("2 15 1");
				rateFactTe.setText("0.9");
				motionFactTe.setText("0.7");
				precisTe.setText("0.00001");
				maxCountTe.setText("1000");
			}
		});
		add(resetBut);
		resetBut.setVisible(true);

		JButton subBut = new JButton("确定");
		subBut.setLocation(180, 210);
		subBut.setSize(60, 20);
		subBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// 设置网络
				setLayers();
				// 设置参数
				setParameter();
				setVisible(false);
			}
		});
		add(subBut);
		subBut.setVisible(true);
	}

	// 将刚填上的网络层数及每层神经元个数写入
	public void setLayers()
	{
		try
		{
			Scanner in = new Scanner(netSizeTe.getText().toString());
			int count = in.nextInt();
			layers = new int[count +1];
			layers[0] = count;

			in = new Scanner(layerSumTe.getText().toString());
			for (int i = 1; i <=count; i++)
			{
				layers[i] = in.nextInt();
			}
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(SetNet.this, "网络设置有错误\n请确认！");
		}
	}

	// 将外部传来的网络层数及每层神经元个数写入
	public void setLayers(int[] netLayers)
	{
		layers = netLayers;
	}

	// 获取网络层数及每层神经元个数
	public int[] getLayers()
	{
		return layers;
	}

	// 将面板上的网络参数设置
	public void setParameter()
	{
		try
		{
			// 学习速率
			Scanner in = new Scanner(rateFactTe.getText().toString());
			parameter[0] = in.nextDouble();
			// 动量因子
			in = new Scanner(motionFactTe.getText().toString());
			parameter[1] = in.nextDouble();
			// 误差精度
			in = new Scanner(precisTe.getText().toString());
			parameter[2] = in.nextDouble();
			// 最多允许循环的次数
			in = new Scanner(maxCountTe.getText().toString());
			parameter[3] = in.nextDouble();
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(SetNet.this, "参数设置有错误\n请确认！");
		}
	}

	// 将外部传来网络参数设置
	public void setParameter(double[] para)
	{
		parameter = para;
	}

	// 获取网络参数
	public double[] getParameter()
	{
		return parameter;
	}

	public static void main(String[] args)
	{
		new SetNet();
	}
}
