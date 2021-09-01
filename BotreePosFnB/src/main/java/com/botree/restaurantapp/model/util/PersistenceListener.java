package com.botree.restaurantapp.model.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PersistenceListener implements ServletContextListener {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Restaurant1");

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		System.out.println(context.getContextPath() + ":: In contextInitialized: "+ entityManagerFactory);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("In contextDestroyed: " + entityManagerFactory.isOpen());
		entityManagerFactory.close();
	}

	public static EntityManagerFactory getEntityManager() {
		return entityManagerFactory;
	}
}
