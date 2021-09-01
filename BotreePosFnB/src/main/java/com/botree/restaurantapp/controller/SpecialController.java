package com.botree.restaurantapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.service.MenuService;
import com.botree.restaurantapp.service.SpecialService;
import com.botree.restaurantapp.service.exception.ServiceException;

public class SpecialController {
	private List<DaySpecial> specialsList = new ArrayList<DaySpecial>();
	private List<MenuItem> itemList = new ArrayList<MenuItem>();
	private MenuService menuService;
	private SpecialService specialService;
	private MenuItem specialMenuItem;
	private MenuCategory menuCategory;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private Long id;
	private Date frmDate;
	private Date toDate;
	private DaySpecial daySpecial;
	private DaySpecial daySpecial2;
	private String action;

	public SpecialController() {

	}

	@PostConstruct
	public void postConstruct() {

		try {
			System.out.println("In get all specials");
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String param = params.get("addSpcl");
			//	System.out.println("store id: " + storeid);
			specialsList = specialService.getSpecialItems();
			System.out.println("number of specials:" + specialsList.size());
			Iterator<DaySpecial> iterator = specialsList.iterator();
			while (iterator.hasNext()) {
				DaySpecial daySpecial = (DaySpecial) iterator.next();
				System.out.println("special frm date:" + daySpecial.getFromdate());
				System.out.println("special to date:" + daySpecial.getTodate());
				specialMenuItem = daySpecial.getItem();
				System.out.println("items" + specialMenuItem.getName());
				System.out.println("items" + specialMenuItem.getPrice());
			}
			/*if (param != null)
				if (param.equalsIgnoreCase("add_special"))*/
					// get all the menu items
					getMenu();

		} catch (Exception e) {
			// throw new DAOException("Check data to be inserted", e);
			e.printStackTrace();
			System.out.println("test8");
		}

	}

	public void getMenu() {

		try {
			System.out.println("Enter getMenu");
			System.out.println("select items size:" + selectItems.size());
			//daySpecial=new DaySpecial();

			itemList = menuService.getAllItems();

			//System.out.println("number of items:" + itemList.size());
			Iterator<MenuItem> iterator = itemList.iterator();
			while (iterator.hasNext()) {
				SelectItem si = new SelectItem();

				MenuItem menuItem = (MenuItem) iterator.next();

				si.setValue(menuItem.getId());
				System.out.println("item id" + menuItem.getId());
				si.setLabel(menuItem.getName());
				System.out.println("items" + menuItem.getName());

				selectItems.add(si);

			}
			// System.out.println("editing user..:" + user);
			System.out.println("exit getMenu");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("test100");
		}

	}

	public String addDaySpecial() {

		daySpecial = new DaySpecial(new MenuItem());

		return "/page/add_special.xhtml";

	}

	public String dispSpecials() {
		System.out.println(" in display specials");
		String redirect = "disp_special_page";

		// redirect = "disp_special_page";
		System.out.println("going to...");
		// System.out.println("user list size:" + userList.size());
		return redirect;

	}

	public String delete() {
		System.out.println("enter deletespecial");
		List<DaySpecial> spclList = new ArrayList<DaySpecial>();

		try {
			System.out.println("item to b deleted:" + daySpecial.getId());
			spclList.add(daySpecial);
			specialService.deleteSpecial(spclList);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
		System.out.println("exit deletespecial");

		return "";

	}

	public String addSpecial() {

		try {
			System.out.println("enter addSpecial");

			List<DaySpecial> spclList = new ArrayList<DaySpecial>();
			spclList.add(daySpecial);
			specialService.addSpecial(spclList);
			System.out.println("exit addSpecial");
		} catch (ServiceException e) {

			e.printStackTrace();
		}
		//daySpecial2=new DaySpecial();
		return "/page/disp_special_page?redirect=true";
	}

	public List<DaySpecial> getSpecialsList() {
		postConstruct();
		return specialsList;
	}

	public void setSpecialsList(List<DaySpecial> specialsList) {
		this.specialsList = specialsList;
	}

	public SpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(SpecialService specialService) {
		this.specialService = specialService;
	}

	public MenuItem getSpecialMenuItem() {
		return specialMenuItem;
	}

	public void setSpecialMenuItem(MenuItem specialMenuItem) {
		this.specialMenuItem = specialMenuItem;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<MenuItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<MenuItem> itemList) {
		this.itemList = itemList;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public MenuCategory getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}

	public Date getFrmDate() {
		return frmDate;
	}

	public void setFrmDate(Date frmDate) {
		this.frmDate = frmDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public DaySpecial getDaySpecial() {
		return daySpecial;
	}

	public void setDaySpecial(DaySpecial daySpecial) {
		this.daySpecial = daySpecial;
	}

	public DaySpecial getDaySpecial2() {
		return daySpecial2;
	}

	public void setDaySpecial2(DaySpecial daySpecial2) {
		this.daySpecial2 = daySpecial2;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
