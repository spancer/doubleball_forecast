<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在Struts 2应用中使用JFreeChart</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
  </head>
  
  <body>
  	${forcast}
   <div>
   	<a href="jfreechart/blueoddeven.action" target="_blank">双色球历年走势</a>
   </div>
   <hr/>
   <div>
   	<a href="jfreechart/forcast.action?type=small" target="_blank">红球小球走势(即号码<17)</a>
   </div>
   <hr/>
   <div>
   	<a href="jfreechart/forcast.action?type=odd" target="_blank">红球奇数号码走势(如1，3， 5...)</a>
   </div>
   <hr/>
   <div>
   	<a href="jfreechart/forcast.action?type=cfm" target="_blank">红球重复号码走势(如11， 22， 33)</a>
   </div>
   <hr/>
   <div>
   	<a href="jfreechart/forcast.action?type=dwm" target="_blank">红球对望号码走势(如03和30， 23和32 ...)</a>
   </div>
   <hr/>
    <div>
   	<a href="jfreechart/forcast.action?type=gold" target="_blank">红球黄金号码走势(如1, 2, 3, 5... 后数为前两者和)</a>
   </div>
   <hr/>
   
    <div>
   	<a href="jfreechart/forcast.action?type=red31" target="_blank">红球连续3区划分第1区(1-11)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red32" target="_blank">红球连续3区划分第2区(12-22)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red33" target="_blank">红球连续3区划分第3区(23-33)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red3mod1" target="_blank">红球取余3区划分第1区</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red3mod2" target="_blank">红球取余3区划分第2区</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red3mod3" target="_blank">红球取余3区划分第3区(</a>
   </div>
   <hr/> 
   
    <div>
   	<a href="jfreechart/forcast.action?type=red41" target="_blank">红球连续4区划分第1区(1-8)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red42" target="_blank">红球连续4区划分第2区(9-16)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red43" target="_blank">红球连续4区划分第3区(17-24)</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=red44" target="_blank">红球连续4区划分第4区(25-33)</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=red4mod1" target="_blank">红球取余4区划分第1区(即号码除以4余数为1)</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=red4mod2" target="_blank">红球取余4区划分第2区(即号码除以4余数为2)</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=red4mod3" target="_blank">红球取余4区划分第3区(即号码除以4余数为3)</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=red4mod4" target="_blank">红球取余4区划分第4区(即号码除以4整除的)</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=redsumavg" target="_blank">红球和的平均值</a>
   </div>
   <hr/> 
   
   <div>
   	<a href="jfreechart/forcast.action?type=redtailsumavg" target="_blank">红球尾数的和的平均值</a>
   </div>
   <hr/> 
   <div>
   	<a href="jfreechart/forcast.action?type=blue4zone" target="_blank">蓝球连续4区所在区域的走势</a>
   </div>
   <hr/> 
   <div>
   	<a href="jfreechart/forcast.action?type=blue4modzone" target="_blank">蓝球取余4区所在区域的走势</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=avg" target="_blank">2014年各值平均水平</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=mustSize" target="_blank">红球在一堆球中的个数走势</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=quad1" target="_blank">象限1个数走势</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=quad2" target="_blank">象限2个数走势</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=quad3" target="_blank">象限3个数走势</a>
   </div>
   <hr/> 
    <div>
   	<a href="jfreechart/forcast.action?type=quad4" target="_blank">象限4个数走势</a>
   </div>
   <hr/> 
       <div>
   	<a href="jfreechart/forcast.action?type=sum34" target="_blank">红球和为34的球对对数走势</a>
   </div>
   <hr/> 
   
       <div>
   	<a href="jfreechart/forcast.action?type=chi1530" target="_blank">红球15与30互斥走势(1表示同时存在)</a>
   </div>
   <hr/> 
       <div>
   	<a href="jfreechart/forcast.action?type=chi2030" target="_blank">红球20与30互斥走势(1表示同时存在)</a>
   </div>
   <hr/> 
   
          <div>
   	<a href="jfreechart/forcast.action?type=zs" target="_blank">红球质数个数走势(1不为质数)</a>
   </div>
   <hr/> 
   
          <div>
   	<a href="jfreechart/forcast.action?type=hs" target="_blank">红球合数个数走势(1不为合数)</a>
   </div>
   <hr/> 
   
         <div>
   	<a href="jfreechart/forcast.action?type=tail" target="_blank">红球尾数个数走势</a>
   </div>
   <hr/> 
 
  </body>
</html>
