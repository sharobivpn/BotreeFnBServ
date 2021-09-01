package com.botree.restaurantapp.service;

import com.botree.restaurantapp.dao.PictureGalleryDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.service.exception.ServiceException;

public class PictureService {

	PictureGalleryDAO galleryDAO = null;

	public PictureService() {
		
	}

	public void uploadPicture(byte[] pic) throws ServiceException {
		try {

			System.out.println("Enter PictureService.uploadPicture ");

			galleryDAO.uploadPicture(pic);
			System.out.println("exit PictureService.uploadPicture ");

		} catch (DAOException e) {
			throw new ServiceException("picture upload error", e);

		}

	}

	public void updatePicture(byte[] pic) throws ServiceException {
		try {

			System.out.println("Enter PictureService.updatePicture ");

			galleryDAO.updatePicture(pic);
			System.out.println("exit PictureService.updatePicture ");

		} catch (DAOException e) {
			throw new ServiceException("feedback error", e);

		}

	}

	public void deletePicture(byte[] pic) throws ServiceException {
		try {
			System.out.println("Enter PictureService.deletePicture ");

			galleryDAO.deletePicture(pic);
			System.out.println("exit PictureService.deletePicture ");

		} catch (DAOException e) {
			throw new ServiceException("feedback error", e);

		}

	}

}
