package com.botree.restaurantapp.dao;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;

@Component("socialMediaDAO")
public class SocialMediaDAOImpl implements SocialMediaDAO {

	@Override
	public void addSocialMedia(String url) throws DAOException {
		
		try{
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		
	}

	@Override
	public void updateSocialMedia(String url) throws DAOException {
		
    try{
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		
	}

	@Override
	public void deleteSocialMedia(String url) throws DAOException {
		
    try{
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		
	}

	@Override
	public void getSocialMedia() throws DAOException {
		
     try{
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		
	}

	

}
