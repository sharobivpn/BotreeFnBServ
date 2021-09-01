package com.botree.restaurantapp.webservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.service.SpecialService;
import com.botree.restaurantapp.webservice.DaysSpecialWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@ResponseBody
@RequestMapping(value = "/specialItems")
public class DaysSpecialWSImpl implements DaysSpecialWS {

  @Autowired
	private SpecialService specialService;

	@Override
	@RequestMapping(value = "/get", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getSpecialItems() {
		List<DaySpecial> items = null;
		try {
			items = specialService.getSpecialItems();
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<DaySpecial>>() {}.getType();
		String json = gson.toJson(items, type);
		return json;
	}

	@Override
	@RequestMapping(value = "/storeId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	//produces = "application/json")
			public
			String getSpecialItemsByStoreId(@RequestParam(value = "id") String storeid,
											@RequestParam(value = "language") String language) {

		List<DaySpecial> specialList = null;
		try {
			//System.out.println("enter DaysSpecialWSImpl: getSpecialItemsByStoreId: storeid  " + storeid);
			specialList = specialService.getSpecialItemsByStoreId(storeid, language);
			//System.out.println("exit DaysSpecialWSImpl getSpecialItemsByStoreId");
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		java.lang.reflect.Type type = new TypeToken<List<DaySpecial>>() {}.getType();
		String json = gson.toJson(specialList, type);
		//return specialList;
		return json;
	}

	public SpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(SpecialService specialService) {
		this.specialService = specialService;
	}

}
