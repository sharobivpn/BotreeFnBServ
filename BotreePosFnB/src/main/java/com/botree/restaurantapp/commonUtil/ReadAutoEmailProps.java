/**
 * 
 */
package com.botree.restaurantapp.commonUtil;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Habib
 *
 */
public class ReadAutoEmailProps {
	
	public  static void readConfig() throws Exception
	{
		try
		{
		    Properties pro = new Properties();
		    String path = System.getProperty("user.home")+"/autoemail.properties";
		    pro.load(new FileInputStream(path));	   
		    AutoEmailPropsConstant.delay = pro.getProperty("delay");
		    AutoEmailPropsConstant.interval = pro.getProperty("interval");
		    AutoEmailPropsConstant.setFrom = pro.getProperty("setFrom");
		    AutoEmailPropsConstant.setPassword = pro.getProperty("setPassword");
		    AutoEmailPropsConstant.emailTO = pro.getProperty("emailTO");	  		   
		    AutoEmailPropsConstant.storeList = pro.getProperty("storeList");
		    AutoEmailPropsConstant.smtp = pro.getProperty("smtp");
		    AutoEmailPropsConstant.port = pro.getProperty("port");
		    AutoEmailPropsConstant.sendtime = pro.getProperty("sendtime");
		}
		catch(Exception e)
		{
            //throw new Exception();
            System.out.println("Can't get properties file::"+e.getMessage());
		}

	}

}
