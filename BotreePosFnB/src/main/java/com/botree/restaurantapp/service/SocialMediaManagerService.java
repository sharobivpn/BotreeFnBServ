package com.botree.restaurantapp.service;

import com.botree.restaurantapp.dao.SocialMediaDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.service.exception.ServiceException;

public class SocialMediaManagerService {

	private SocialMediaDAO mediaDAO = null;

	public SocialMediaManagerService() {
		
	}

	public void addSocialMedia(String url) throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerService.addSocialMedia ");
			mediaDAO.addSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerService.addSocialMedia ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add social media", e);

		}

	}

	public void updateSocialMedia(String url) throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerService.updateSocialMedia ");
			mediaDAO.updateSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerService.updateSocialMedia ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to update social media", e);

		}

	}

	public void deleteSocialMedia(String url) throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerService.deleteSocialMedia ");
			mediaDAO.deleteSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerService.deleteSocialMedia ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to delete social media", e);

		}

	}

	public void getSocialMedia() throws ServiceException {
		try {
			System.out
					.println("Enter SocialMediaManagerService.getSocialMedia ");
			mediaDAO.getSocialMedia();
			System.out
					.println("Enter SocialMediaManagerService.getSocialMedia ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to get social media", e);

		}

	}
}
