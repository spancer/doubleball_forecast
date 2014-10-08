package com.coomia.bpneuro.neuro;

//神经元类
public class Neuron
{
	//输入值
	private double input;
	//输出值
	private double output;
	//期望输出值
	private double desirOut;
	// 渐降因子
	private double delta;

	public Neuron()
	{
		input = 0;
	}

	// 采用sigmoid函数做传递函数
	public void transfer()
	{
		double temp;
		temp = 1 +Math.exp(-(input-0.5));//0.5 为阈值
		output = 1 /temp;
	}
	
	public void setInput(double inValue)
	{
		input=inValue;
	}
	
	public void straight()
	{
		output = input;
	}

	void addInput(double in)
	{
		input += in;
	}

	public double getOutput()
	{
		return output;
	}

	public void setDesireOut(double out)
	{
		desirOut = out;
	}

	public double getDesireOut()
	{
		return desirOut;
	}

	public double getDelta()
	{
		return delta;
	}

	public void setDelta(double newDelt)
	{
		delta = newDelt;
	}
}
