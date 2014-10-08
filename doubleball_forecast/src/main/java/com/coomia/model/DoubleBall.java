package com.coomia.model;

import java.util.Set;

import com.coomia.util.DoubleBallConstant;

//双色球 类
public class DoubleBall implements Comparable<DoubleBall> {
	int num;
	BallNum ballNum = new BallNum();

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public BallNum getBallNum() {
		return ballNum;
	}

	public void setBallNum(BallNum ballNum) {
		this.ballNum = ballNum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("No:").append(num).append(
				"Reds: [");
		for (Integer integer : ballNum.getReds())
			sb.append(integer).append(" ");
		sb.append("]").append(" - Blue: ").append(ballNum.getBlue());
		return sb.toString();
	}

	/**
	 * added 2014-09-15
	 * 
	 * @return
	 */

	public String getDoubleBall() {
		StringBuilder sb = new StringBuilder(this.getBallNum().getBlue())
				.append("-");
		Set<Integer> reds = this.getBallNum().getReds();
		for (Integer integer : reds) {
			sb.append(integer).append("-");
		}
		return sb.toString();
	}
	
	public String getReds() {
		StringBuilder sb = new StringBuilder();
		Set<Integer> reds = this.getBallNum().getReds();
		for (Integer integer : reds) {
			sb.append(integer).append("-");
		}
		return sb.toString();
	}

	public boolean equals(DoubleBall ball) {
		if (this.getDoubleBall().equals(ball.getDoubleBall()))
			return true;
		else
			return false;
	}

	/**
	 * edded added 2014-09-15
	 * 
	 * @return
	 */
	public int bluezone4() {
		if (DoubleBallConstant.blue4zone1().contains(ballNum.getBlue()))
			return 1;
		else if (DoubleBallConstant.blue4zone2().contains(ballNum.getBlue()))
			return 2;
		else if (DoubleBallConstant.blue4zone3().contains(ballNum.getBlue()))
			return 3;
		else
			return 4;

	}

	public int bluemodzone4() {
		if (DoubleBallConstant.blue4modzone1().contains(ballNum.getBlue()))
			return 1;
		else if (DoubleBallConstant.blue4modzone2().contains(ballNum.getBlue()))
			return 2;
		else if (DoubleBallConstant.blue4modzone3().contains(ballNum.getBlue()))
			return 3;
		else
			return 4;

	}

	public String info() {
		StringBuilder sb = new StringBuilder();
		sb.append("第" + num + "期");
		sb.append("\t");
		sb.append("奇号个数：" + this.getOdd());
		sb.append("\t");
		sb.append("小号个数：" + this.getSmall());
		sb.append("\t");
		sb.append("对望码个数：" + this.getDWM());
		sb.append("\t");
		sb.append("重复码个数：" + this.getCFM());
		sb.append("\t");
		sb.append("黄金码个数：" + this.getgolden());
		sb.append("\t");
		sb.append("连续4区第1区个数：" + this.red4zone1());
		sb.append("\t");
		sb.append("连续4区第2区个数：" + this.red4zone2());
		sb.append("\t");
		sb.append("连续4区第3区个数：" + this.red4zone3());
		sb.append("\t");
		sb.append("连续4区第4区个数：" + this.red4zone4());
		sb.append("\t");
		sb.append("取余4区第1区个数：" + this.red4modzone1());
		sb.append("\t");
		sb.append("取余4区第2区个数：" + this.red4modzone2());
		sb.append("\t");
		sb.append("取余4区第3区个数：" + this.red4modzone3());
		sb.append("\t");
		sb.append("取余4区第4区个数：" + this.red4modzone4());
		sb.append("\t");
		sb.append("蓝球连续4区第：" + this.bluezone4() + "区");
		sb.append("\t");
		sb.append("蓝球取余4区第：" + this.bluemodzone4() + "区");
		return sb.toString();
	}

	public int compareTo(DoubleBall o) {
		if (this.num > o.getNum())
			return 1;
		else if (this.num == o.getNum())
			return 0;
		else
			return -1;
	}

	public int getOdd() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.odd().contains(red))
				odd++;
		}
		return odd;
	}

	public int getSmall() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (red < 17)
				odd++;
		}
		return odd;
	}

	public int getDWM() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.dwm().contains(red))
				odd++;
		}
		return odd;
	}

	public int getCFM() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.cfm().contains(red))
				odd++;
		}
		return odd;
	}

	public int getgolden() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.goldenBalls().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4zone1() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4zone1().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4modzone1() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4modzone1().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4zone2() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4zone2().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4modzone2() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4modzone2().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4zone3() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4zone3().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4modzone3() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4modzone3().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4zone4() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4zone4().contains(red))
				odd++;
		}
		return odd;
	}

	public int red4modzone4() {
		int odd = 0;
		for (int red : this.ballNum.getReds()) {
			if (DoubleBallConstant.red4modzone4().contains(red))
				odd++;
		}
		return odd;
	}

}