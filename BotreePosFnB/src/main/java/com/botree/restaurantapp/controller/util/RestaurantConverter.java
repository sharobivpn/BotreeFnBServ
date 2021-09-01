package com.botree.restaurantapp.controller.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.StoreAddressDAO;
import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.model.RestaurantMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;

@ManagedBean(name = "restaurantConverter")
public class RestaurantConverter implements Converter {
	private final static Logger logger = LogManager.getLogger(RestaurantConverter.class);

	public RestaurantConverter() {

	}

	private StoreAddressDAO storeAddressDAO = new StoreAddressDAOImpl();
	private RestaurantMaster restMaster = null;
	EntityManager em = null;
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Object getAsObject(	FacesContext context,
								UIComponent component,
								String value) {
		System.out.println("In RestaurantConverter: getAsObject");
		logger.debug("In getAsObject");
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();

		if (value == null || value.isEmpty()) {
			System.out.println("value: " + value);
			logger.debug("vlue:" + value);
			return null;
		}

		String restId = value.toString();

		System.out.println("store id: " + restId);

		restMaster = em.find(RestaurantMaster.class, Integer.parseInt(restId));
		
		//Integer restId1=restMaster.getId();

		return restMaster;
	}

	@Override
	public String getAsString(	FacesContext context,
								UIComponent component,
								Object value) {
		System.out.println("In RestaurantConverter: getAsString");
		logger.debug("In getAsString");
		if (value == null) {
			return null;
		}
		String id = Integer.toString(((RestaurantMaster) value).getId());
		RestaurantMaster rest = (RestaurantMaster) value;
		return Integer.toString(rest.getId()) != null ? String.valueOf(rest.getId()) : null;
		//System.out.println("id is: " + id);
		//return id;
	}
}
