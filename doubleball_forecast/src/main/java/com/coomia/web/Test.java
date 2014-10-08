package com.coomia.web;

import java.util.HashSet;
import java.util.Set;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Integer> a = new HashSet<Integer>();
		a.add(1);
		a.add(2);
		a.add(3);
		Set<Integer> b = new HashSet<Integer>();
		b.add(2);
		b.add(4);
		Set<Integer> c = new HashSet<Integer>();
		c.addAll(a);
		 c.retainAll(b);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}

}
