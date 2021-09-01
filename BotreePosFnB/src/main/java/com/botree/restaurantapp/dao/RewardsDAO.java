package com.botree.restaurantapp.dao;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.RewardPoint;

public interface RewardsDAO {

	public void createReward () throws DAOException;
	
	public void updateReward (RewardPoint reward) throws DAOException;
	
	public void deleteReward (RewardPoint reward) throws DAOException;
}
