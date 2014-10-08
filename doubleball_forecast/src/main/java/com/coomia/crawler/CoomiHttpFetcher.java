package com.coomia.crawler;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class CoomiHttpFetcher {

	final static String USER_AGENT_H = "User-Agent";
	final static String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:24.0) Gecko/20100101 Firefox/24.0";
	/**
	 * Generate the cookie.
	 * 
	 * @param website
	 * @return
	 */
	public static Map<String, String> initVisit(String website)
	{
		Response response = null;
		Map<String, String> cookies = null;
		try
		{
			response = Jsoup.connect(website).userAgent(USER_AGENT).ignoreHttpErrors(true).method(Method.GET)
					.timeout(0).execute();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (null != response)
			cookies = response.cookies();
		return cookies;
	}
	
	/**
	 * Get the body content as string.
	 * 
	 * @param siteAddress
	 * @param cookies
	 * @param params
	 * 
	 * @return
	 */
	public static String getBodyUsingJsoup(String siteAddress, String domain, Map<String, String> cookies,
			Map<String, String> params)
	{
		if(!isValidUrl(siteAddress))
			return null;
		String content = null;
		Response response = null;
		Connection con;
		try
		{
			con = Jsoup.connect(siteAddress).ignoreContentType(true).ignoreHttpErrors(true).userAgent(USER_AGENT)
					.referrer(domain).cookies(cookies).method(Method.GET);
			if (null != params && !params.isEmpty())
				con.data(params);
			response = con.execute();
		} catch (IOException e)
		{
			
			while(true)
			{
				con = Jsoup.connect(siteAddress).ignoreContentType(true).ignoreHttpErrors(true).userAgent(USER_AGENT)
						.referrer(domain).cookies(cookies).method(Method.GET);
				if (null != params && !params.isEmpty())
					con.data(params);
				try {
					response = con.execute();
				} catch (IOException e1) {
					
				}
				if (null != response)
				{
					content = response.body();
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					
				}
			}
		}
		if (null != response)
			content = response.body();
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return content;
	}

	private static  boolean isValidUrl(String urlString) {
		boolean valid = true;
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
		Pattern patt = Pattern. compile(regex );
		Matcher matcher = patt.matcher(urlString);
		boolean isMatch = matcher.matches();
		if (!isMatch) 
		    valid = false;
		return valid;
	}
}
