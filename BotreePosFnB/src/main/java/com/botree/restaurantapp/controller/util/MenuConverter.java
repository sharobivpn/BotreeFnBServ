package com.botree.restaurantapp.controller.util;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.MenuDAO;
import com.botree.restaurantapp.dao.MenuDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;

@ManagedBean(name = "menuConverter")
public class MenuConverter implements Converter {
	private final static Logger logger = LogManager.getLogger(MenuConverter.class);

	public MenuConverter() {}

	private MenuDAO menuDAO = new MenuDAOImpl();
	private MenuCategory menuCategory = new MenuCategory();

	@Override
	public Object getAsObject(	FacesContext context,
								UIComponent component,
								String value) {
		logger.debug("In getAsObject");
		if (value == null || value.isEmpty()) {
			logger.debug("Value:" + value);
			return null;
		}

		String categoryId = value.toString();
		logger.debug("Category id:" + categoryId);
		int category_id = Integer.parseInt(categoryId);
		logger.debug("Category id:" + category_id);
		try {
			menuCategory = menuDAO.getCategoryById(category_id);

			List<MenuCategory> catList = menuCategory.getMenucategory();
			logger.debug("Category size is :" + catList.size());

			List<MenuItem> orderList = menuCategory.getItems();
			logger.debug("menuItem size is :" + orderList.size());

		} catch (DAOException e) {
			logger.error("In getAsObject DAOException......", e);
		}
		return menuCategory;
	}

	@Override
	public String getAsString(	FacesContext context,
								UIComponent component,
								Object value) {
		logger.debug("In getAsString");
		if (value == null) {
			return null;
		}

		return null;
	}

}
