package com.botree.restaurantapp.webservice;


public interface MaintenenceJobWS {

	public String cleanLogByPeriod(String tomcatDir, String days);

}
