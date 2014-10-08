package com.coomia.model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.coomia.util.DoubleBallConstant;

//球号码 类
public class BallNum {
	TreeSet<Integer> reds = new TreeSet<Integer>();
	int blue;

	public TreeSet<Integer> getReds() {
		return reds;
	}

	public void setReds(TreeSet<Integer> reds) {
		this.reds = reds;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public void addRed(int red) {
		reds.add(red);
	}

	/**
	 * 用于用户构造一期的球，红球之间用,分隔
	 * 
	 * @param red
	 * @param blue
	 */
	public BallNum(String red, String blue) {
		if (null != red) {
			String[] redstr = red.split(",");
			for (String string : redstr) {
				this.reds.add(Integer.parseInt(string.trim()));
			}
			this.blue = Integer.parseInt(blue.trim());
		}

	}

	public BallNum() {
		super();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		BallNum b = (BallNum) o;
		return this.getReds().containsAll(b.getReds())
				&& this.getBlue() == b.getBlue();

	}

	@Override
	public int hashCode() {
		return reds.hashCode() * 6 + blue;
	}

	public int getSmallBallCount() {
		int count = 0;
		for (int red : this.getReds()) {
			if (red <= 17)
				count++;
		}
		return count;
	}

	public int getOddBallCount() {
		int count = 0;
		for (int red : this.getReds()) {
			if (red % 2 == 1)
				count++;
		}
		return count;
	}

	public int getDWMBallCount() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.dwm().contains(red))
				count++;
		}
		return count;
	}

	public int getGoldenBallCount() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.goldenBalls().contains(red))
				count++;
		}
		return count;
	}

	public int getCFMBallCount() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.cfm().contains(red))
				count++;
		}
		return count;
	}
	
	public int getBlueOddCount()
	{
		if(this.getBlue() %2 == 1)
			return 1;
		else
			return 0;
	}
	
	public int getZone41()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4zone1().contains(red))
				count++;
		}
		return count;
	}
	public int getZone42()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4zone2().contains(red))
				count++;
		}
		return count;
	}
	public int getZone43()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4zone3().contains(red))
				count++;
		}
		return count;
	}
	public int getZone44()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4zone4().contains(red))
				count++;
		}
		return count;
	}
	
	public int getZone4mod1()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4modzone1().contains(red))
				count++;
		}
		return count;
	}
	public int getZone4mod2()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4modzone2().contains(red))
				count++;
		}
		return count;
	}
	public int getZone4mod3()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4modzone3().contains(red))
				count++;
		}
		return count;
	}
	public int getZone4mod4()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red4modzone4().contains(red))
				count++;
		}
		return count;
	}

	public double getZone31() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3zone1().contains(red))
				count++;
		}
		return count;
	}
	public double getZone32() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3zone2().contains(red))
				count++;
		}
		return count;
	}
	public double getZone33() {
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3zone3().contains(red))
				count++;
		}
		return count;
	}
	
	public int getZone3mod3()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3modzone3().contains(red))
				count++;
		}
		return count;
	}
	
	public int getZone3mod2()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3modzone2().contains(red))
				count++;
		}
		return count;
	}
	
	public int getZone3mod1()
	{
		int count = 0;
		for (int red : this.getReds()) {
			if (DoubleBallConstant.red3modzone1().contains(red))
				count++;
		}
		return count;
	}

	public double getBlueSmallCount() {
		if(this.getBlue() <= 8)
			return 1;
		else
			return 0;
	}
	
	public double redavgsum()
	{
		int odd = 0;
		for (int red: this.getReds()) 
		{
			 odd +=red;
		}
		return odd/(double)6;
					
	}
	
	public double redtailavgsum() {
		int odd = 0;
		for (int red: this.getReds()) 
		{
			 odd +=getTail(red);
		}
		return odd/(double)6;
	}
	
	private int getTail(int value)
	{
		return value%10;
	}
	
	public int countQuadI()
	{
		int count = 0;
		for (int red: this.getReds()) 
		{
			 if(QuadrantBalls.getQuadI().getAllQuads().contains(red))
				 count++;
		}
		return count;
	}
	
	public int countQuadII()
	{
		int count = 0;
		for (int red: this.getReds()) 
		{
			 if(QuadrantBalls.getQuadII().getAllQuads().contains(red))
				 count++;
		}
		return count;
	}
	
	public int countQuadIII()
	{
		int count = 0;
		for (int red: this.getReds()) 
		{
			 if(QuadrantBalls.getQuadIII().getAllQuads().contains(red))
				 count++;
		}
		return count;
	}
	
	public int countQuadIV()
	{
		int count = 0;
		for (int red: this.getReds()) 
		{
			 if(QuadrantBalls.getQuadIV().getAllQuads().contains(red))
				 count++;
		}
		return count;
	}
	
	public int countsum34()
	{
		int counter = 0;
		for (int red: this.getReds()) 
		{
			if(red == 17)
				continue;
			if(this.getReds().contains(34-red))
				counter++;
		}
		return counter/2;
	}
	
	public int contains15and30()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.add(15);
		set.add(30);
		if(this.getReds().containsAll(set))
			return 1;
		else
			return 0;
	}
	
	public int contains20and30()
	{
		Set<Integer> set = new HashSet<Integer>();
		set.add(20);
		set.add(30);
		if(this.getReds().containsAll(set))
			return 1;
		else
			return 0;
	}
	
	public int countzs()
	{
		int counter = 0;
		for (int red: this.getReds()) 
		{
			if(DoubleBallConstant.zs().contains(red))
				counter++;
		}
		return counter;
	}
	
	public int counths()
	{
		int counter = 0;
		for (int red: this.getReds()) 
		{
			if(DoubleBallConstant.hs().contains(red))
				counter++;
		}
		return counter;
	}
	
	public int counttail()
	{
		Set<Integer> tails = new HashSet<Integer>();
		for (int red: this.getReds()) 
		{
			tails.add(getTail(red));
		}
		return tails.size();
	}
	
}
