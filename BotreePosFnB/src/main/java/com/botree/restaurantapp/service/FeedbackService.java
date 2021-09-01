package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.FeedbackDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Feedback;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class FeedbackService {

  @Autowired
	private FeedbackDAO feedbackDAO;

	public FeedbackService() {
		
	}

	public void addFeedback(Feedback feedback) throws ServiceException {
		try {

			System.out.println("Enter FeedbackService.addFeedback ");

			feedbackDAO.addFeedback(feedback);
			System.out.println("exit FeedbackService.addFeedback ");

		} catch (DAOException e) {

			throw new ServiceException("feedback error", e);

		}

	}

	public List<Feedback> getFeedbacks() throws ServiceException {
		List<Feedback> feedbacks = null;
		try {

			System.out.println("Enter FeedbackService.getFeedbacks ");

			feedbacks = feedbackDAO.getFeedbacks();
			System.out.println("exit FeedbackService.getFeedbacks ");

		} catch (DAOException e) {
			throw new ServiceException("feedback error", e);

		}
		return feedbacks;
	}
	
	public List<Feedback> getFeedbackByStore(String storeid) throws ServiceException {
		List<Feedback> feedbacks = null;
		try {

			feedbacks = feedbackDAO.getFeedbackByStore(storeid);
			
		} catch (DAOException e) {
			throw new ServiceException("feedback error", e);

		}
		return feedbacks;
	}
	public void delete(Feedback feedback) throws ServiceException {
		
		try {

			System.out.println("Enter FeedbackService.delete ");
			feedbackDAO.delete(feedback);
			System.out.println("exit FeedbackService.delete ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("feedback error", e);

		}
		
	}

	public FeedbackDAO getFeedbackDAO() {
		return feedbackDAO;
	}

	public void setFeedbackDAO(FeedbackDAO feedbackDAO) {
		this.feedbackDAO = feedbackDAO;
	}

}
