package com.botree.restaurantapp.controller;

import com.botree.restaurantapp.service.SocialMediaManagerService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class SocialMediaManagerController {

	private String url;
	private SocialMediaManagerService mediaManagerService = null;

	public SocialMediaManagerController() {
		
	}

	public void addSocialMedia() throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerController.addSocialMedia ");
			mediaManagerService.addSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerController.addSocialMedia ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to add social media", e);

		}

	}

	public void updateSocialMedia() throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerController.updateSocialMedia ");
			mediaManagerService.updateSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerController.updateSocialMedia ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to update social media", e);

		}

	}

	public void deleteSocialMedia() throws ServiceException {
		try {

			System.out
					.println("Enter SocialMediaManagerController.deleteSocialMedia ");
			mediaManagerService.deleteSocialMedia(url);
			System.out
					.println("Enter SocialMediaManagerController.deleteSocialMedia ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to delete social media", e);

		}

	}

	public void getSocialMedia() throws ServiceException {
		try {
			System.out
					.println("Enter SocialMediaManagerController.getSocialMedia ");
			mediaManagerService.getSocialMedia();
			System.out
					.println("Enter SocialMediaManagerController.getSocialMedia ");

		} catch (ServiceException e) {
			throw new ServiceException(
					"problem occured while trying to get social media", e);

		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
