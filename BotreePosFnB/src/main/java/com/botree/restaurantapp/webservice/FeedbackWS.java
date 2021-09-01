package com.botree.restaurantapp.webservice;

import com.botree.restaurantapp.model.Feedback;
import com.botree.restaurantapp.model.StatusMessage;

public interface FeedbackWS {

	String getFeedback();

	String getFeedbackByStore(String storeid);

	StatusMessage createFeedback(Feedback feedback);
}
