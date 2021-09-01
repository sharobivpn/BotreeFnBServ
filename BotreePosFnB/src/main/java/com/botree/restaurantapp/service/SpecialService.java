package com.botree.restaurantapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.SpecialsDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class SpecialService {

  @Autowired
	private SpecialsDAO specialsDAO;

	public SpecialService() {
		
	}

	public List<DaySpecial> getSpecialItems() throws ServiceException {
		List<DaySpecial> specialItemList = null;
		try {

			System.out.println("Enter SpecialService.getSpecialItems ");
			// get special items
			specialItemList = specialsDAO.getSpecialItem();
			System.out.println("exit SpecialService.getSpecialItems ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error trying to get special items", e);

		}
		return specialItemList;

	}

	public List<DaySpecial> getSpecialItemsByStoreId(String storeId, String language)
			throws ServiceException {
		List<DaySpecial> specialItemList = null;
		try {

			System.out
					.println("Enter SpecialService.getSpecialItemsByStoreId ");
			// get special items by store id
			specialItemList = specialsDAO.getSpecialItemByStoreId(storeId,language);
			System.out.println("exit SpecialService.getSpecialItemsByStoreId ");

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("error trying to get special items", e);

		}
		return specialItemList;

	}

	public void addSpecial(List<DaySpecial> itemList) throws ServiceException {
		try {
			System.out.println("Enter SpecialService.createCustomer ");
			// create a new special item
			specialsDAO.addSpeciaItem(itemList);
			System.out.println("exit SpecialService.createCustomer ");

		} catch (DAOException e) {
			throw new ServiceException("error trying to add special items", e);

		}

	}

	public void deleteSpecial(List<DaySpecial> itemList)
			throws ServiceException {
		try {
			System.out.println("Enter SpecialService.deleteSpecial ");
			// delete special item
			specialsDAO.deleteSpecialItem(itemList);
			System.out.println("exit SpecialService.deleteSpecial ");

		} catch (DAOException e) {
			throw new ServiceException("error trying to delete special items",
					e);

		}

	}

	public SpecialsDAO getSpecialsDAO() {
		return specialsDAO;
	}

	public void setSpecialsDAO(SpecialsDAO specialsDAO) {
		this.specialsDAO = specialsDAO;
	}

}
