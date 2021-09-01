package com.botree.restaurantapp.dao;


import com.botree.restaurantapp.dao.exception.DAOException;

public interface PictureGalleryDAO {
	
	public void uploadPicture(byte[] pic) throws DAOException;
	public void updatePicture(byte[] pic) throws DAOException;
	public void deletePicture(byte[] pic) throws DAOException;
}
