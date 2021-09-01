package com.botree.restaurantapp.webservice;

public interface DaysSpecialWS {

	String getSpecialItems();

	String getSpecialItemsByStoreId(String storeid,
									String language);
}
