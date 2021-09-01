/**
 * 
 */
package com.botree.restaurantapp.commonUtil;

import java.util.Timer;

/**
 * @author Habib
 *
 */
public class AutoEmailSenderScheduler {
	
	public void callScheduler() throws Exception
	{

		System.out.println("Scheduler Starterd...");
		ReadAutoEmailProps.readConfig();
		Timer timer = new Timer();

		timer.schedule(new AutoEmailSenderTask(),1000,60*1000);

	}
	
	public static void main(String a[]) throws Exception
	{
		AutoEmailSenderScheduler dbs = new AutoEmailSenderScheduler();
		dbs.callScheduler();
	}

}
