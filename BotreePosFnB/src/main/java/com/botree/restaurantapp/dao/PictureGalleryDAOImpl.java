package com.botree.restaurantapp.dao;


import java.io.File;
import java.io.FileInputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.PictureGallery;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("pictureGalleryDAO")
public class PictureGalleryDAOImpl implements PictureGalleryDAO{

	private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
	
	@Override
	public void uploadPicture(byte[] pic) throws DAOException {
		
		File file=new File("D:\\Restaurant 01\\Workspace\\Restaurant\\WebContent\\images\\");
		byte[] bfile=pic;

		PictureGallery picture1=new PictureGallery();
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			FileInputStream fis=new FileInputStream(file);
			fis.read(bfile);
			fis.close();
			em.getTransaction().begin();
			picture1.setPicture(bfile);
			em.persist(picture1);
			em.getTransaction().commit();
			//em.close();
			
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	@Override
	public void updatePicture(byte[] pic) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			//PictureGallery picture=em.find(PictureGallery.class,1);
			em.getTransaction().commit();
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	@Override
	public void deletePicture(byte[] pic) throws DAOException {
		EntityManager em=null;
		try{
			
			em=entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().commit();
			//em.close();
		}catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}finally{
			em.close();
		}
		
	}

	
}
