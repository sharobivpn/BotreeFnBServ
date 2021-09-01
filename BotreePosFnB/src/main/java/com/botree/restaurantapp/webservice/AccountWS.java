package com.botree.restaurantapp.webservice;

import com.botree.restaurantapp.model.DailyExpenditure;

public interface AccountWS {

	String addDailyExpenditure(DailyExpenditure dailyExpenditure);

	public String getDailyExpenditureByDate(String date, String storeId);

	public String getDailyExpenditureByPeriod(String date, String toDate,
			String storeId);
	
	public String deleteDailyExpenditure(DailyExpenditure dailyExpenditure);

	public String updateDailyExpenditure(DailyExpenditure dailyExpenditure);
	
	//added on 15.05.2018
	public String getExpenditureTypes();
}
