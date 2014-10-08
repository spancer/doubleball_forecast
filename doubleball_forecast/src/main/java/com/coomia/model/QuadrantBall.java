package com.coomia.model;

import java.util.Set;

public class QuadrantBall {

	private int quadNum; //第几象限
	private int edge; //象限中的边码
	private int origin; //象限的中心码
	private Set<Integer> mids; //中心球
	private Set<Integer> allQuads;//所有球
	public int getQuadNum() {
		return quadNum;
	}
	public void setQuadNum(int quadNum) {
		this.quadNum = quadNum;
	}
	public int getEdge() {
		return edge;
	}
	public void setEdge(int edge) {
		this.edge = edge;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public Set<Integer> getMids() {
		return mids;
	}
	public void setMids(Set<Integer> mids) {
		this.mids = mids;
	}
	public Set<Integer> getAllQuads() {
		return allQuads;
	}
	public void setAllQuads(Set<Integer> allQuads) {
		this.allQuads = allQuads;
	}
	
	
	
}
