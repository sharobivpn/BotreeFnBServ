package com.botree.restaurantapp.dao;

import java.util.List;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Feedback;

public interface FeedbackDAO {
	
	public void addFeedback (Feedback feedback) throws DAOException;
	
	public List<Feedback> getFeedbacks() throws DAOException;
	
	public void delete(Feedback feedback) throws DAOException;
	public List<Feedback> getFeedbackByStore(String storeid) throws DAOException;
}
