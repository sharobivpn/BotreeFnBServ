/**
 * 
 */
package com.botree.restaurantapp.model.util;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Sharobi
 * 
 */

public class DbConnection {

	InitialContext ic;
	private static EntityManagerFactory factory;
	private EntityManager em;

	public synchronized EntityManager getEntityManager() throws Exception,
			SQLException {

		try {
			
			factory = Persistence.createEntityManagerFactory("Restaurant1");
		    em = factory.createEntityManager();
		   
		} 
		
		catch (Exception e) {
			System.out.println("Exception thrown " + e);
			e.printStackTrace();
		} 
		
		return em;
	}

}
