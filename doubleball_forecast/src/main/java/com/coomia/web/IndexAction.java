package com.coomia.web;

import com.coomia.crawler.CoomiaDoubleBallForcast;
import com.coomia.service.impl.DoubleBallForcastService;
import com.coomia.service.impl.DoubleBallService;
import com.opensymphony.xwork2.Action;

public class IndexAction {

	private String forcast;
	
	  public String getForcast() {
		return forcast;
	}

	public void setForcast(String forcast) {
		this.forcast = forcast;
	}

	public String forcast() throws Exception {
	    	forcast = CoomiaDoubleBallForcast.forcast(new DoubleBallForcastService(), new DoubleBallService().findByYear(2014), 0.9, 0.9, 0.1, 0.4, 0.4, 0.1, 0.1).toString();
	    	return Action.SUCCESS;
	    }
}
