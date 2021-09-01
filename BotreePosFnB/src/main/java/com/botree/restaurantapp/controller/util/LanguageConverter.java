package com.botree.restaurantapp.controller.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.model.MasterLanguage;
import com.botree.restaurantapp.model.util.PersistenceListener;

@ManagedBean(name = "languageConverter")
public class LanguageConverter implements Converter {
	private final static Logger logger = LogManager.getLogger(LanguageConverter.class);

	public LanguageConverter() {

	}

	private MasterLanguage language = null;
	EntityManager em = null;
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Object getAsObject(	FacesContext context,
								UIComponent component,
								String value) {
		System.out.println("In languageConverter: getAsObject");
		logger.debug("In getAsObject");
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();

		if (value == null || value.isEmpty()) {
			System.out.println("value: " + value);
			logger.debug("vlue:" + value);
			return null;
		}

		String langId = value.toString();

		System.out.println("lang id: " + langId);

		language = em.find(MasterLanguage.class, Integer.parseInt(langId));

		return language;
	}

	@Override
	public String getAsString(	FacesContext context,
								UIComponent component,
								Object value) {
		System.out.println("In languageConverter: getAsString");
		logger.debug("In getAsString");
		if (value == null) {
			return null;
		}
		String id = Integer.toString(((MasterLanguage) value).getId());
		MasterLanguage language = (MasterLanguage) value;
		return Integer.toString(language.getId()) != null ? String.valueOf(language.getId()) : null;

	}
}
