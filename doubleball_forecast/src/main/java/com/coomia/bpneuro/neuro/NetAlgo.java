package com.coomia.bpneuro.neuro;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.coomia.bpneuro.ui.DrawLearn;
import com.coomia.bpneuro.ui.DrawTest;

//BP算法类
public class NetAlgo extends JPanel implements Runnable
{
	// 第一个数记录有几层网络，后面记录每一层有多少个神经元
	private int[] netLayers;

	// 二维数组存放神经元，动态分配，每一维不一定等长
	private Neuron[][] neuroArray;

	// 用三维数组存放网络的权重，每一维不一定等长Delta
	private double[][][] netWeight;

	// 用于存储前一次的变化权重,用作动量影子
	private double[][][] deltaWeight;

	// 原始数据学习集
	private double[][] origLearnData;

	// 归一化后的学习数据集
	private double[][] learnData;

	// 原始泛化数据集
	private double[][] origGenerData;

	// 归一化后的泛化数据集
	private double[][] generData;

	// 存放最大最小值，用于归一化,0行存最小值，1行存最大值
	private double[][] minMax;

	// 均方差数组，
	private double[] meanErrSum;

	// 相对误差和数组
	private double[] relaErrSum;

	// 学习速率
	private double eta = 0.8;

	// 动量项因子
	private double alpha = 0.7;

	// 学习要达到的误差精度
	private double precise = 0.0005;

	// 最多允许学习次数
	private int maxCount = 2000;

	// 存放输出曲线的平均误差率的曲线的类
	private DrawLearn[] learnCurv;

	// 存放回想实际输出与期望输出的关系的曲线
	private DrawTest[] reCallCurv;

	// 存放泛化实际输出与期望输出的关系的曲线
	private DrawTest[] generCurv;

	// 是否利用原有网络
	private boolean isHaveNet;

	// 算法执行类型,1表示学习，2表示回想，3表示泛化
	private int execType = 1;

	// 回想期望值，实际值，相对误差\表,第一维表示输出个数
	private double mixReCall[][][];

	// 泛化期望值，实际值，相对误差表
	private double mixGeneral[][][];

	// 显示网络参数
	private JLabel jlPara;

	// 显示数据
	private JTextArea showData;
	private JScrollPane showPanel;

	public NetAlgo()
	{
		setLayout(new BorderLayout());

		jlPara = new JLabel();
		jlPara.setText("  你好！  你已进入BP神经网络！");
		add(jlPara, BorderLayout.SOUTH);

		showData = new JTextArea();
		showPanel = new JScrollPane(showData);
		showData.setAutoscrolls(true);
		showData.setLineWrap(true);
		add(showPanel, BorderLayout.CENTER);
	}

	// 设置网络结构---层数和每层节点个数
	public void setLayers(int[] layers)
	{
		netLayers = layers;
	}

	// 将网络参数写入算法类
	public void setParameter(double[] parameter)
	{
		// 学习速率
		eta = parameter[0];
		// 动量项因子
		alpha = parameter[1];
		// 学习要达到的误差精度
		precise = parameter[2];
		// 最多允许学习次数
		maxCount = (int) parameter[3];
	}

	// 设置网络结构---初始化网络
	public void setNeuros(Neuron[][] neurons)
	{
		neuroArray = neurons;
	}

	// 设置网络的连接权值
	public void setWeight(double[][][] weights)
	{
		netWeight = weights;
	}

	// 初始化deltaWeight
	public void initDeltaWeight()
	{
		deltaWeight = new double[neuroArray.length -1][][];
		for (int i = 0; i <neuroArray.length -1; i++)
		{
			deltaWeight[i] = new double[neuroArray[i].length][];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				deltaWeight[i][j] = new double[neuroArray[i +1].length];
				for (int k = 0; k <neuroArray[i +1].length; k++)
				{
					deltaWeight[i][j][k] = Math.random();
				}
			}
		}
	}

	// 设置网络权值
	public void setweights()
	{
		// 初始化神经网络连接权值 neuroArray
		netWeight = new double[neuroArray.length -1][][];
		for (int i = 0; i <neuroArray.length -1; i++)
		{
			netWeight[i] = new double[neuroArray[i].length][];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				netWeight[i][j] = new double[neuroArray[i +1].length];
				for (int k = 0; k <neuroArray[i +1].length; k++)
				{
					netWeight[i][j][k] = Math.random();
				}
			}
		}
	}

	// 获得权值表
	public double[][][] getWeight()
	{
		return netWeight;
	}

	// 载入学习数据集
	public void setLearnData(double[][] data)
	{
		// 导入学习数据集
		origLearnData = data;
	}

	// 载入测试数据
	public void setGenarData(double[][] data)
	{
		// 导入泛化数据集
		origGenerData = data;
	}

	// 设置使用原有网络学习
	public void setHavaNet()
	{
		isHaveNet = true;
	}

	// 设置使用新的网络学习
	public void setNoNet()
	{
		isHaveNet = false;
	}

	// 算法执行类型,1表示学习，2表示回想，3表示泛化
	public void setExecType(int type)
	{
		execType = type;
	}

	// 初始化最大最小值表,将用于存放归一化后的数据
	private void initMaxMin()
	{
		int col;
		if (origLearnData !=null)
		{
			col = origLearnData[0].length;
		} else
		{
			col = origGenerData[0].length;
		}
		minMax = new double[2][col];
		for (int i = 0; i <col; i++)
		{
			minMax[0][i] = minMax[1][i] = origLearnData[0][i];
		}
	}

	// 找出数据集中最大最小值为归一化做准备
	private void findMaxMin(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// 在学习数据中找最大最小值
			tempData = origLearnData;
		} else
		{
			// 在泛化数据中找最大最小值
			tempData = origGenerData;
		}
		if (tempData !=null)
		{
			for (int i = 0; i <tempData.length; i++)
			{
				for (int j = 0; j <tempData[i].length; j++)
				{
					if (tempData[i][j] <minMax[0][j])
					{
						minMax[0][j] = tempData[i][j];
					}
					if (tempData[i][j] >minMax[1][j])
					{
						minMax[1][j] = tempData[i][j];
					}
				}
			}
		}
	}

	// 对数据进行归一化
	private void unitaryData(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// 对学习数据进行归一化
			tempData = origLearnData;
		} else
		{
			// 对泛化数据进行归一化
			tempData = origGenerData;
		}
		int row, col;
		if (tempData !=null)
		{
			row = tempData.length;
			col = tempData[0].length;
		} else
		{
			row = col = 0;
		}
		double[][] data = new double[row][col];
		// 对泛化数据进行归一化
		for (int i = 0; i <row; i++)
		{
			for (int j = 0; j <col; j++)
			{
				data[i][j] = (tempData[i][j] -minMax[0][j])
						/(minMax[1][j] -minMax[0][j]);
			}
		}
		if (flag)
		{
			// 返回学习数据
			learnData = data;
		} else
		{
			// 返回泛化数据
			generData = data;
		}
	}

	// 数据(包括学习数据和泛化数据)归一化
	public void normal()
	{
		// 初始化最大最小值表
		initMaxMin();
		// 先在学习数据集中找到最大最小值
		findMaxMin(true);
		// 在泛化数据集中找到最大最小值
		findMaxMin(false);
		// 对学习数据进行归一化
		unitaryData(true);
		// 对学习数据进行归一化
		unitaryData(false);
	}

	// 初始化神经网络
	public void initNet()
	{
		// 初始化神经网络各层节点
		neuroArray = new Neuron[netLayers[0]][];
		for (int i = 0; i <netLayers[0]; i++)
		{
			neuroArray[i] = new Neuron[netLayers[i +1]];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				neuroArray[i][j] = new Neuron();
			}
		}
		// 初始化神经网络连接权值
		setweights();
	}

	// 从输入到输出层进行前向计算
	public void forwardCacu(double[] sample)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		// 将学习数据输入输入层
		for (int j = 0; j <inCount; j++)
		{
			neuroArray[0][j].setInput(sample[j]);
		}
		// 将期望输出存于输出层
		for (int j = 0; j <outCount; j++)
		{
			neuroArray[layerCount -1][j].setDesireOut(sample[inCount +j]);
		}

		// 向前计算每一层网络
		// 输入层特殊处理
		for (int j = 0; j <neuroArray[0].length; j++)
		{
			neuroArray[0][j].straight();
		}
		// 计算其它层
		for (int j = 1; j <layerCount; j++)
		{
			// 对于每一层的每一个神经元
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				// 将前一层的每一个神经元的输出与权重的乘积加入
				double inValue = 0;
				for (int l = 0; l <neuroArray[j -1].length; l++)
				{
					// 要乘的权值
					double weight = netWeight[j -1][l][k];
					inValue += neuroArray[j -1][l].getOutput() *weight;
				}
				neuroArray[j][k].setInput(inValue);
				// 该神经进行函数传递
				neuroArray[j][k].transfer();
			}
		}
	}

	// 误差类和数组清空
	public void clsErrArray(int outCount)
	{
		for (int i = 0; i <outCount; i++)
		{
			meanErrSum[i] = 0;
			relaErrSum[i] = 0;
		}
	}

	// 误差计算
	public void errorCacu()
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		for (int j = 0; j <outCount; j++)
		{
			// 求均方差
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			meanErrSum[j] += 0.5 *(desireOut -newOut) *(desireOut -newOut);
			// 反归一化，求相对误差
			// 实际输出值
			double realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			relaErrSum[j] += Math.abs((realNewOut -realDesireOut) /realNewOut);
		}
	}

	// 计算平均相对误差是否达到足够精度,满足精度要求则返回true
	public boolean isOk(int sum)
	{
		boolean ok = true;
		int outCount = netLayers[netLayers.length -1];
		for (int i = 0; i <outCount; i++)
		{
			double meanErrRate = meanErrSum[i] /learnData.length;
			double relaErrRate = relaErrSum[i] /learnData.length;
			learnCurv[i].drawLine(sum, relaErrRate);
			// 将误差以文本方式显示
			if (sum %30 ==0)
			{
				showData.append("     " +sum +"     " +meanErrRate +"     "
						+relaErrRate +"\n");
			}
		}
		for (int i = 0; i <outCount; i++)
		{
			double meanErrRate = meanErrSum[i] /learnData.length;
			if (meanErrRate >precise)
			{
				ok = false;
				break;
			}
		}
		// 不满足条件则返回true
		return ok;
	}

	// 反向计算下降因子
	public void deltaCacu()
	{
		int layerCount = netLayers[0];
		int outCount = netLayers[netLayers.length -1];

		// 误差反传计算；
		// 对于最后一层的delta
		for (int j = 0; j <outCount; j++)
		{
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			double temDelta = newOut *(1 -newOut) *(desireOut -newOut);
			neuroArray[layerCount -1][j].setDelta(temDelta);
		}

		// 对于前面各层的delta的更新
		for (int j = layerCount -2; j >=0; j--)
		{
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				double newOut = neuroArray[j][k].getOutput();
				double desireOut = 0;
				for (int l = 0; l <neuroArray[j +1].length; l++)
				{
					double tempWeight = netWeight[j][k][l];
					desireOut += neuroArray[j +1][l].getDelta() *tempWeight;
				}
				double temDelta = newOut *(1 -newOut) *desireOut;
				neuroArray[j][k].setDelta(temDelta);
			}
		}
	}

	// 调节权重
	public void renewWeight()
	{
		int layerCount = netLayers[0];
		for (int j = 0; j <layerCount -1; j++)
		{
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				for (int l = 0; l <neuroArray[j +1].length; l++)
				{
					double deWeight = eta *neuroArray[j +1][l].getDelta()
							*neuroArray[j][k].getOutput();
					netWeight[j][k][l] = netWeight[j][k][l] +deWeight +alpha
							*deltaWeight[j][k][l];
					deltaWeight[j][k][l] = deWeight;
				}
			}
		}
	}

	public void printWeight()
	{
		System.out.println("  权值表：");
		for (int i = 0; i <netWeight.length; i++)
		{
			for (int j = 0; j <netWeight[i].length; j++)
			{
				for (int k = 0; k <netWeight[i][j].length; k++)
				{
					System.out.print("  " +netWeight[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

	// 神经网络学习算法
	private void BpLearn()
	{
		// 输出值个数，初始化存放类和误差的数组
		int outCount = netLayers[netLayers.length -1];
		// 均方差数组初始化
		meanErrSum = new double[outCount];
		// 相对误差数组初始化
		relaErrSum = new double[outCount];
		// 初始化曲线类数组
		learnCurv = new DrawLearn[outCount];
		for (int i = 0; i <outCount; i++)
		{
			learnCurv[i] = new DrawLearn(i);
		}
		if (!isHaveNet)
		{
			initNet();
		}
		// 初始化DeltaWeight
		initDeltaWeight();
		// 归一化
		normal();
		int sum = 0;
		showData.append("学习\n");
		showData.append("学习次数                   均方差                  相对误差\n");
		do
		{
			// 误差类和数组清空
			clsErrArray(outCount);
			for (int i = 0; i <learnData.length; i++)
			{
				// 前向计算
				forwardCacu(learnData[i]);
				// 反向计算下降因子
				deltaCacu();
				// 调整权值；
				renewWeight();
				// 误差计算
				errorCacu();
			}
			sum++;
			// printWeight();
		} while (!isOk(sum) &&sum <maxCount);
	}

	// 回想误差计算,i表示数据集的编号
	public void reCallErr(int i)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		for (int j = 0; j <outCount; j++)
		{
			// 期望输出
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			// 实际输出
			double newOut = neuroArray[layerCount -1][j].getOutput();
			// 反归一化，求相对误差
			// 实际输出值
			double realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			double relaErr = Math.abs((realNewOut -realDesireOut) /realNewOut);

			mixReCall[j][0][i] = desireOut;
			mixReCall[j][1][i] = newOut;
			mixReCall[j][2][i] = relaErr;
		}
	}

	// 泛化误差计算
	public void generErr(int i)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		for (int j = 0; j <outCount; j++)
		{
			// 期望输出
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			// 实际输出
			double newOut = neuroArray[layerCount -1][j].getOutput();
			// 反归一化，求相对误差
			// 实际输出值
			double realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			double relaErr = Math.abs((realNewOut -realDesireOut) /realNewOut);

			mixGeneral[j][0][i] = desireOut;
			mixGeneral[j][1][i] = newOut;
			mixGeneral[j][2][i] = relaErr;
		}
	}

	// Bp神经网络回想
	private void BpReCall()
	{
		int outCount = netLayers[netLayers.length -1];
		reCallCurv = new DrawTest[outCount];
		for (int i = 0; i <outCount; i++)
		{
			reCallCurv[i] = new DrawTest("回想第Y" +i +"条曲线:红色计算值，蓝色期望值", i);
		}
		// 初始化，期望值、误差值，先对误差表，第一维表示输出个数
		mixReCall = new double[outCount][3][learnData.length];
		for (int i = 0; i <learnData.length; i++)
		{
			// 前向计算
			forwardCacu(learnData[i]);
			// 误差计算
			reCallErr(i);
		}
		// 画出回想曲线
		showData.append("回想\n");
		showData.append("编号     " +"期望值     " +"计算值     " +"相对误差\n");
		for (int i = 0; i <outCount; i++)
		{
			showData.append("            回想Y" +i +"\n");
			reCallCurv[i].setMixData(mixReCall[i]);
			// 将实验结果以文本方式显示
			for (int j = 0; j <mixReCall[i][0].length; j++)
			{
				showData.append("  " +j);
				showData.append("     " +mixReCall[i][0][j]);
				showData.append("     " +mixReCall[i][1][j]);
				showData.append("     " +mixReCall[i][2][j] +"\n");
			}
		}
	}

	// Bp神经网络泛化
	private void BpGeneral()
	{
		int outCount = netLayers[netLayers.length -1];
		generCurv = new DrawTest[outCount];
		for (int i = 0; i <outCount; i++)
		{
			generCurv[i] = new DrawTest("泛化第Y" +i +"条曲线:红色计算值，蓝色期望值", i);
		}
		// 初始化，期望值、误差值，先对误差表
		mixGeneral = new double[outCount][3][generData.length];
		for (int i = 0; i <generData.length; i++)
		{
			// 前向计算
			forwardCacu(generData[i]);
			// 误差计算
			generErr(i);
		}
		// 画出泛化曲线
		showData.append("      泛化\n");
		showData.append("编号     " +"期望值     " +"计算值     " +"相对误差\n");
		for (int i = 0; i <outCount; i++)
		{
			showData.append("            泛化Y" +i +"\n");
			generCurv[i].setMixData(mixGeneral[i]);
			// 将实验结果以文本方式显示
			for (int j = 0; j <mixGeneral[i][0].length; j++)
			{
				showData.append("  " +j);
				showData.append("     " +mixGeneral[i][0][j]);
				showData.append("     " +mixGeneral[i][1][j]);
				showData.append("     " +mixGeneral[i][2][j] +"\n");
			}
		}
	}

	// 显示网络参数
	private void showPara()
	{
		String showStr = "";
		showStr += "  网络层数：" +netLayers[0] +"  每层神经元个数：";
		for (int i = 1; i <=netLayers[0]; i++)
		{
			showStr += " :" +netLayers[i];
		}
		showStr += "  学习速率：" +eta +"  \n动量因子：" +alpha +"  学习精度：" +precise
				+"  最大学习次数：" +maxCount;
		jlPara.setText(showStr);
	}

	public void run()
	{
		showPara();
		switch (execType)
		{
			case 1:
				BpLearn();
				break;
			case 2:
				BpReCall();
				break;
			case 3:
				BpGeneral();
				break;
			default:
				break;
		}

	}
}