package com.botree.restaurantapp.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ImageWS {

	void getImage(	int name,
					HttpServletRequest request,
					HttpServletResponse response);

	void getStoreImage(	int name,
						HttpServletRequest request,
						HttpServletResponse response);

	void getFoodItemImage(	int name,
							HttpServletRequest request,
							HttpServletResponse response);

	String getImageNames(HttpServletRequest request);
	
}
