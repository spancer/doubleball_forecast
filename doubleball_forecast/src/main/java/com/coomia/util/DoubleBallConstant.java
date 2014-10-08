package com.coomia.util;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DoubleBallConstant {

	public static final TreeSet<Integer> ALL_RED = new TreeSet<Integer>();
	public static final TreeSet<Integer> ALL_BLUE = new TreeSet<Integer>();
	public static final HashSet<Integer> DWM = new HashSet<Integer>();
	public static final HashSet<Integer> CFM = new HashSet<Integer>();
	public static final HashSet<Integer> GOLDENBALL = new HashSet<Integer>();
	
	public static final HashSet<Integer> ODD = new HashSet<Integer>();
	public static final HashSet<Integer> EVEN = new HashSet<Integer>();
	
	public static final HashSet<Integer> RED3ZONE_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区连续1区
	public static final HashSet<Integer> RED3ZONE_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> RED3ZONE_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	
	public static final HashSet<Integer> RED4ZONE_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区连续1区
	public static final HashSet<Integer> RED4ZONE_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> RED4ZONE_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	public static final HashSet<Integer> RED4ZONE_CONTINOUS_ZONE4 = new HashSet<Integer>();//3区
	
	
	public static final HashSet<Integer> RED3ZONE_NON_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区取余1区
	public static final HashSet<Integer> RED3ZONE_NON_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> RED3ZONE_NON_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	
	public static final HashSet<Integer> RED4ZONE_NON_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区取余1区
	public static final HashSet<Integer> RED4ZONE_NON_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> RED4ZONE_NON_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	public static final HashSet<Integer> RED4ZONE_NON_CONTINOUS_ZONE4 = new HashSet<Integer>();//3区
	
	public static final HashSet<Integer> BLUE4ZONE_NON_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区取余1区
	public static final HashSet<Integer> BLUE4ZONE_NON_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> BLUE4ZONE_NON_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	public static final HashSet<Integer> BLUE4ZONE_NON_CONTINOUS_ZONE4 = new HashSet<Integer>();//3区
	
	public static final HashSet<Integer> BLUE4ZONE_CONTINOUS_ZONE1 = new HashSet<Integer>();//红球3区连续1区
	public static final HashSet<Integer> BLUE4ZONE_CONTINOUS_ZONE2 = new HashSet<Integer>();//2区
	public static final HashSet<Integer> BLUE4ZONE_CONTINOUS_ZONE3 = new HashSet<Integer>();//3区
	public static final HashSet<Integer> BLUE4ZONE_CONTINOUS_ZONE4 = new HashSet<Integer>();//3区
	
	public static final HashSet<Integer> ZS = new HashSet<Integer>();//质数
	public static final HashSet<Integer> HS = new HashSet<Integer>();//合数
	
	
	public static HashSet<Integer> zs()
	{
		if(ZS.isEmpty())
		{
			ZS.add(2);
			ZS.add(3);
			ZS.add(5);
			ZS.add(7);
			ZS.add(11);
			ZS.add(13);
			ZS.add(17);
			ZS.add(19);
			ZS.add(23);
			ZS.add(29);
			ZS.add(31);
		}
		return ZS;
	}
	
	public static HashSet<Integer> hs()
	{
		if(HS.isEmpty())
		{
			HS.addAll(allReds());
			HS.remove(1);
			HS.removeAll(zs());
		}
		return HS;
	}
	
	public static TreeSet<Integer> allReds()
	{
		if(ALL_RED.isEmpty())
		{
			fitSet(ALL_RED, 33);
		}
		return ALL_RED;
	}
	
	public static TreeSet<Integer> allBules()
	{
		if(ALL_BLUE.isEmpty())
		{
			fitSet(ALL_BLUE, 16);
		}
		return ALL_BLUE;
	}


	private static void fitSet(TreeSet<Integer> set, int size) {
		
		for (int i = 1; i <= size; i++) {
			set.add(i);
		}
		
	}
	
	/**
	 * 对望码
	 * @return
	 */
	public static Set<Integer> dwm()
	{
		if(DWM.isEmpty())
		{
			DWM.add(12);
			DWM.add(21);
			DWM.add(1);
			DWM.add(10);
			DWM.add(13);
			DWM.add(31);
			DWM.add(2);
			DWM.add(20);
			DWM.add(3);
			DWM.add(30);
			DWM.add(23);
			DWM.add(32);
		}
		return DWM;
	}
	
	/**
	 * 重复码
	 * @return
	 */
	public static Set<Integer> cfm()
	{
		if(CFM.isEmpty())
		{
			CFM.add(11);
			CFM.add(22);
			CFM.add(33);
		}
		return CFM;
	}
	
	/**
	 * 黄金码
	 * @return
	 */
	public static Set<Integer> goldenBalls()
	{
		if(GOLDENBALL.isEmpty())
		{
			GOLDENBALL.add(1);
			GOLDENBALL.add(2);
			GOLDENBALL.add(3);
			GOLDENBALL.add(5);
			GOLDENBALL.add(8);
			GOLDENBALL.add(13);
			GOLDENBALL.add(21);
		}
		return GOLDENBALL;
	}
	
	public static Set<Integer> odd()
	{
		if(ODD.isEmpty())
		{
			for(int i =1; i<=33; i+=2)
			{
				ODD.add(i);
			}
		}
		return ODD;
	}
	public static Set<Integer> red3modzone1()
	{
		if(RED3ZONE_NON_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%3 == 1)
					RED3ZONE_NON_CONTINOUS_ZONE1.add(i);
			}
		}
		return RED3ZONE_NON_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> red3modzone2()
	{
		if(RED3ZONE_NON_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%3 == 2)
					RED3ZONE_NON_CONTINOUS_ZONE2.add(i);
			}
		}
		return RED3ZONE_NON_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> red3modzone3()
	{
		if(RED3ZONE_NON_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%3 == 0)
					RED3ZONE_NON_CONTINOUS_ZONE3.add(i);
			}
		}
		return RED3ZONE_NON_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> red3zone1()
	{
		if(RED3ZONE_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=11; i++)
			{
				RED3ZONE_CONTINOUS_ZONE1.add(i);
			}
		}
		return RED3ZONE_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> red3zone2()
	{
		if(RED3ZONE_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =12; i<=22; i++)
			{
				RED3ZONE_CONTINOUS_ZONE2.add(i);
			}
		}
		return RED3ZONE_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> red3zone3()
	{
		if(RED3ZONE_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =23; i<=33; i++)
			{
				RED3ZONE_CONTINOUS_ZONE3.add(i);
			}
		}
		return RED3ZONE_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> red4modzone1()
	{
		if(RED4ZONE_NON_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%4 == 1)
					RED4ZONE_NON_CONTINOUS_ZONE1.add(i);
			}
		}
		return RED4ZONE_NON_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> red4modzone2()
	{
		if(RED4ZONE_NON_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%4 == 2)
					RED4ZONE_NON_CONTINOUS_ZONE2.add(i);
			}
		}
		return RED4ZONE_NON_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> red4modzone3()
	{
		if(RED4ZONE_NON_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%4 == 3)
					RED4ZONE_NON_CONTINOUS_ZONE3.add(i);
			}
		}
		return RED4ZONE_NON_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> red4modzone4()
	{
		if(RED4ZONE_NON_CONTINOUS_ZONE4.isEmpty())
		{
			for(int i =1; i<=33; i++)
			{
				if(i%4 == 0)
					RED4ZONE_NON_CONTINOUS_ZONE4.add(i);
			}
		}
		return RED4ZONE_NON_CONTINOUS_ZONE4;
	}
	
	public static Set<Integer> red4zone1()
	{
		if(RED4ZONE_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=8; i++)
			{
				RED4ZONE_CONTINOUS_ZONE1.add(i);
			}
		}
		return RED4ZONE_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> red4zone2()
	{
		if(RED4ZONE_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =9; i<=16; i++)
			{
				RED4ZONE_CONTINOUS_ZONE2.add(i);
			}
		}
		return RED4ZONE_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> red4zone3()
	{
		if(RED4ZONE_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =17; i<=24; i++)
			{
				RED4ZONE_CONTINOUS_ZONE3.add(i);
			}
		}
		return RED4ZONE_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> red4zone4()
	{
		if(RED4ZONE_CONTINOUS_ZONE4.isEmpty())
		{
			for(int i =25; i<=33; i++)
			{
				RED4ZONE_CONTINOUS_ZONE4.add(i);
			}
		}
		return RED4ZONE_CONTINOUS_ZONE4;
	}
	
	public static Set<Integer> even()
	{
		if(EVEN.isEmpty())
		{
			for(int i =0; i<=33; i+=2)
			{
				EVEN.add(i);
			}
		}
		return EVEN;
	}
	
	
	public static Set<Integer> blue4modzone1()
	{
		if(BLUE4ZONE_NON_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=16; i++)
			{
				if(i%4 == 1)
					BLUE4ZONE_NON_CONTINOUS_ZONE1.add(i);
			}
		}
		return BLUE4ZONE_NON_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> blue4modzone2()
	{
		if(BLUE4ZONE_NON_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =1; i<=16; i++)
			{
				if(i%4 == 2)
					BLUE4ZONE_NON_CONTINOUS_ZONE2.add(i);
			}
		}
		return BLUE4ZONE_NON_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> blue4modzone3()
	{
		if(BLUE4ZONE_NON_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =1; i<=16; i++)
			{
				if(i%4 == 3)
					BLUE4ZONE_NON_CONTINOUS_ZONE3.add(i);
			}
		}
		return BLUE4ZONE_NON_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> blue4modzone4()
	{
		if(BLUE4ZONE_NON_CONTINOUS_ZONE4.isEmpty())
		{
			for(int i =1; i<=16; i++)
			{
				if(i%4 == 0)
					BLUE4ZONE_NON_CONTINOUS_ZONE4.add(i);
			}
		}
		return BLUE4ZONE_NON_CONTINOUS_ZONE4;
	}
	
	public static Set<Integer> blue4zone1()
	{
		if(BLUE4ZONE_CONTINOUS_ZONE1.isEmpty())
		{
			for(int i =1; i<=4; i++)
			{
				BLUE4ZONE_CONTINOUS_ZONE1.add(i);
			}
		}
		return BLUE4ZONE_CONTINOUS_ZONE1;
	}
	
	public static Set<Integer> blue4zone2()
	{
		if(BLUE4ZONE_CONTINOUS_ZONE2.isEmpty())
		{
			for(int i =5; i<=8; i++)
			{
				BLUE4ZONE_CONTINOUS_ZONE2.add(i);
			}
		}
		return BLUE4ZONE_CONTINOUS_ZONE2;
	}
	
	public static Set<Integer> blue4zone3()
	{
		if(BLUE4ZONE_CONTINOUS_ZONE3.isEmpty())
		{
			for(int i =9; i<=12; i++)
			{
				BLUE4ZONE_CONTINOUS_ZONE3.add(i);
			}
		}
		return BLUE4ZONE_CONTINOUS_ZONE3;
	}
	
	public static Set<Integer> blue4zone4()
	{
		if(BLUE4ZONE_CONTINOUS_ZONE4.isEmpty())
		{
			for(int i =13; i<=16; i++)
			{
				BLUE4ZONE_CONTINOUS_ZONE4.add(i);
			}
		}
		return BLUE4ZONE_CONTINOUS_ZONE4;
	}
}
