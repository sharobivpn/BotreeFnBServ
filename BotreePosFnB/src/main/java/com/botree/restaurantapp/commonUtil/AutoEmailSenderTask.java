/**
 * 
 */
package com.botree.restaurantapp.commonUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import com.botree.restaurantapp.model.StoreMaster;

/**
 * @author Habib
 *
 */
public class AutoEmailSenderTask extends TimerTask{
	List<String> myList;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		AutoEmailSender sender = new AutoEmailSender(AutoEmailPropsConstant.setFrom, AutoEmailPropsConstant.setPassword,AutoEmailPropsConstant.port,AutoEmailPropsConstant.smtp);
	       try {
	    	   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
	    	   myList=Arrays.asList(AutoEmailPropsConstant.storeList.split(","));
	    	   Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
	    	   String now=sdf.format(cal.getTime());
	    	   System.out.println("now:"+now);
				if(now.equals("12:05 PM")){
					StoreMaster store=null;
					SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
					String nowdate=sdfdate.format(cal.getTime());
				for(int i=0;i<myList.size();i++)
				{
					store=sender.getStoreData(Integer.parseInt(myList.get(i)));
					System.out.println(store.getId()+":"+store.getRoomBooking()+":"+nowdate+":"+store.getStoreName());
					String body=sender.createBodyData(""+store.getId(),store.getRoomBooking(),nowdate,store.getStoreName());
					sender.sendMail("Snapshot "+nowdate,body,store.getEmailId());
				    System.out.println("Email Sent Succesfully on schedule to "+store.getStoreName());
				}
			    
			   }
	       }
         catch (Exception e) {
			     e.printStackTrace();
			}  
				System.out.println("Timer not matched..."+AutoEmailPropsConstant.setFrom+AutoEmailPropsConstant.storeList);
	        }

}
