package com.botree.restaurantapp.webservice.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.Feedback;
import com.botree.restaurantapp.model.StatusMessage;
import com.botree.restaurantapp.service.FeedbackService;
import com.botree.restaurantapp.webservice.FeedbackWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/feedback")
public class FeedbackWSImpl implements FeedbackWS {

  @Autowired
	private FeedbackService feedbackService;

	@Override
	@RequestMapping(value = "/get",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getFeedback() {
		List<Feedback> feedbacks = null;
		try {
			feedbacks = feedbackService.getFeedbacks();
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<Feedback>>() {}.getType();
		String json = gson.toJson(feedbacks, type);
		//System.out.println(json);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/getFeedbackByStore",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getFeedbackByStore(@RequestParam(value = "storeid") String storeid) {
		List<Feedback> feedbacks = null;
		try {
			feedbacks = feedbackService.getFeedbackByStore(storeid);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<Feedback>>() {}.getType();
		String json = gson.toJson(feedbacks, type);
		//System.out.println(json);
		return json;
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public StatusMessage createFeedback(@RequestBody Feedback feedback) {
		StatusMessage statusMessage = new StatusMessage();
		try {
			Customer customer = new Customer();
			customer.setId(feedback.getId());
			feedback.setFeedbackDate(new Date());
			feedback.setCustomer(customer);
			feedback.setId(0);
			feedbackService.addFeedback(feedback);
		} catch (Exception x) {
			statusMessage.setMessage(StatusMessage.FAILURE);
		}
		statusMessage.setMessage(StatusMessage.SUCCESS);
		return statusMessage;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
}
