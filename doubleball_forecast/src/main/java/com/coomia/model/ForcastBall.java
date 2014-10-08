package com.coomia.model;

import java.util.Iterator;
import java.util.TreeSet;

import com.coomia.util.DoubleBallConstant;

public class ForcastBall
{
	int num; //期数
	TreeSet<Integer> reds = DoubleBallConstant.allReds();
	TreeSet<Integer> blues = DoubleBallConstant.allBules();
	TreeSet<Integer> shouldReds = new TreeSet<Integer>();
	TreeSet<Integer> shouldBlues = new TreeSet<Integer>();
	TreeSet<Integer> mustReds =  new TreeSet<Integer>();
	TreeSet<Integer> mustBlues =  new TreeSet<Integer>();
	
	TreeSet<Integer> finalReds =  new TreeSet<Integer>();
	TreeSet<Integer> finalBlues =  new TreeSet<Integer>();
	
	public void addFinalRed(Integer red)
	{
		this.finalReds.add(red);
	}
	
	public void addFinalBlue(Integer blue)
	{
		this.finalBlues.add(blue);
	}
	public TreeSet<Integer> getFinalReds() {
		return finalReds;
	}
	public void setFinalReds(TreeSet<Integer> finalReds) {
		this.finalReds = finalReds;
	}
	public TreeSet<Integer> getFinalBlues() {
		return finalBlues;
	}
	public void setFinalBlues(TreeSet<Integer> finalBlues) {
		this.finalBlues = finalBlues;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public TreeSet<Integer> getRedsKeep() {
		return shouldReds;
	}
	public void setRedsKeep(TreeSet<Integer> redsKeep) {
		this.shouldReds = redsKeep;
	}
	public TreeSet<Integer> getBluesKeep() {
		return shouldBlues;
	}
	public void setBluesKeep(TreeSet<Integer> bluesKeep) {
		this.shouldBlues = bluesKeep;
	}
	public TreeSet<Integer> getMustReds() {
		return mustReds;
	}
	public void setMustReds(TreeSet<Integer> mustReds) {
		this.mustReds = mustReds;
	}
	public TreeSet<Integer> getMustBlues() {
		return mustBlues;
	}
	public void setMustBlues(TreeSet<Integer> mustBlues) {
		this.mustBlues = mustBlues;
	}
	public TreeSet<Integer> getReds() {
		return reds;
	}
	public void setReds(TreeSet<Integer> reds) {
		this.reds = reds;
	}
	public TreeSet<Integer> getBlues() {
		return blues;
	}
	public void setBlues(TreeSet<Integer> blues) {
		this.blues = blues;
	}
	
	public void killBlue(int toKill)
	{
		this.blues.remove(toKill);
	}
	
	public void killBlueWithTail(int num)
	{
		int tail = num;
		if(num > 10)
			tail = num -10;
		this.blues.remove(tail);
		this.blues.remove(10+tail);
	}
	
	
	public void killRed(int num)
	{
		this.reds.remove(num);
	}
	public void keepBlue(int num)
	{
		this.shouldBlues.add(num);
	}
	public void shouldRed(int num)
	{
		this.shouldReds.add(num);
	}
	
	public void mustRed(int num)
	{
		this.mustReds.add(num);
	}
	
	public void mustBlue(int num)
	{
		this.mustBlues.add(num);
	}
	
	
	public ForcastBall(int num) {
		super();
		this.num = num;
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("第【"+getNum()+"】期预测:【红球 "+reds.size()+"个】【");
		for (Iterator<Integer> iterator = reds.iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
			sb.append(" ");
		}
		sb.append("】 【Should的红球】【");
		for (Iterator<Integer> it = shouldReds.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		sb.append("】 【蓝球"+blues.size()+"个】【");
		for (Iterator<Integer> it = blues.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		sb.append("】 【Should的蓝球】【");
		for (Iterator<Integer> it = shouldBlues.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		
		sb.append("】 【Must红球】【");
		for (Iterator<Integer> it = mustReds.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		
		sb.append("】 【Must的蓝球】【");
		for (Iterator<Integer> it = mustBlues.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		
		sb.append("】 【Final的红球】【");
		for (Iterator<Integer> it = finalReds.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		
		sb.append("】 【Final的蓝球】【");
		for (Iterator<Integer> it = finalBlues.iterator(); it.hasNext();) 
		{
			sb.append(it.next());
			sb.append(" ");
		}
		
		sb.append("】");
		return sb.toString();
	}
}
