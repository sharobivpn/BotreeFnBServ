package com.botree.restaurantapp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.Feedback;
import com.botree.restaurantapp.service.FeedbackService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class FeedbackController {

	List<Feedback> feedbackList = new ArrayList<Feedback>();
	FeedbackService feedbackService;
	private Customer customer;
	private Feedback feedback;
	private String redirect;

	public FeedbackController() {
		
		System.out.println("in FeedbackController ");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("Inside get all feedbacks");
		try {
			
			feedbackList = feedbackService.getFeedbacks();
			System.out.println("No. of Feedbacks :" + feedbackList.size());
			Iterator<Feedback> iterator = feedbackList.iterator();
			while (iterator.hasNext()) {
				Feedback feedback = (Feedback) iterator.next();
				System.out.println("Feedback Id :" + feedback.getId());
				System.out.println("Feedback Description :" + feedback.getDescription());
				System.out.println("Feedback Rating :" + feedback.getFeedbackRating());
				/*customer=feedback.getCustomer();
				System.out.println("Customer Name :"+customer.getName());*/
			}
			System.out.println("Outside get all feedbacks");
		} catch (ServiceException e) {
			
			e.printStackTrace();
		}
	}

	public String dispFeedback() {
		System.out.println("inside dispFeedback");
		FacesContext context = FacesContext.getCurrentInstance();

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String storeid = params.get("storeid");
		System.out.println("store id: " + storeid);
		redirect = "disp_feedback_page";
		return redirect;

	}

	public String delete() {
		System.out.println("In deleteFeedback");

		try {
			feedbackService.delete(feedback);

		} catch (ServiceException e) {
			
			e.printStackTrace();
		}
		System.out.println("FeedbackController.delete");

		return "/page/disp_feedback.xhtml?faces-redirect=true";

	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

}
