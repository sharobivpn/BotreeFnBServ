package com.botree.restaurantapp.commonUtil;

import java.util.Comparator;

import com.botree.restaurantapp.model.MenuCategory;

public class CatComparator implements Comparator<MenuCategory> {

	@Override
	public int compare(MenuCategory m1, MenuCategory m2) {
		if (m1.getMenuCategoryName().equals(m2.getMenuCategoryName())) {
			return 0;
		}
		else {
			return 1;
		}
		
	}
}
