package com.coomia.model;

import java.util.HashSet;
import java.util.Set;

public class QuadrantBalls {

	/**
	 * 第一象限
	 * @return
	 */
	public static QuadrantBall getQuadI()
	{
		QuadrantBall quad1 = new QuadrantBall();
		Set<Integer> all = new HashSet<Integer>();
		all.add(1);
		all.add(2);
		all.add(3);
		all.add(7);
		all.add(8);
		all.add(9);
		all.add(13);
		all.add(14);
		all.add(15);
		quad1.setAllQuads(all);
		quad1.setOrigin(15);
		quad1.setEdge(1);
		quad1.setQuadNum(1);
		Set<Integer> mids = new HashSet<Integer>();
		mids.add(8);
		mids.add(9);
		mids.add(14);
		mids.add(15);
		quad1.setMids(mids);
		return quad1;
	}
	
	public static QuadrantBall getQuadII()
	{
		QuadrantBall quad = new QuadrantBall();
		Set<Integer> all = new HashSet<Integer>();
		all.add(4);
		all.add(5);
		all.add(6);
		all.add(10);
		all.add(11);
		all.add(12);
		all.add(16);
		all.add(17);
		all.add(18);
		quad.setAllQuads(all);
		quad.setOrigin(16);
		quad.setEdge(6);
		quad.setQuadNum(2);
		Set<Integer> mids = new HashSet<Integer>();
		mids.add(10);
		mids.add(11);
		mids.add(16);
		mids.add(17);
		quad.setMids(mids);
		return quad;
	}
	
	public static QuadrantBall getQuadIII()
	{
		QuadrantBall quad = new QuadrantBall();
		Set<Integer> all = new HashSet<Integer>();
		all.add(22);
		all.add(23);
		all.add(24);
		all.add(28);
		all.add(29);
		all.add(30);
		quad.setAllQuads(all);
		quad.setOrigin(22);
		quad.setEdge(30);
		quad.setQuadNum(3);
		Set<Integer> mids = new HashSet<Integer>();
		mids.add(22);
		mids.add(23);
		quad.setMids(mids);
		return quad;
	}
	
	public static QuadrantBall getQuadIV()
	{
		QuadrantBall quad = new QuadrantBall();
		Set<Integer> all = new HashSet<Integer>();
		all.add(19);
		all.add(20);
		all.add(21);
		all.add(25);
		all.add(26);
		all.add(27);
		all.add(31);
		all.add(32);
		all.add(33);
		quad.setAllQuads(all);
		quad.setOrigin(21);
		quad.setEdge(31);
		quad.setQuadNum(4);
		Set<Integer> mids = new HashSet<Integer>();
		mids.add(20);
		mids.add(21);
		mids.add(26);
		mids.add(27);
		quad.setMids(mids);
		return quad;
	}
}
