package com.botree.restaurantapp.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.dao.SpecialsDAO;
import com.botree.restaurantapp.model.DaySpecial;
import com.botree.restaurantapp.webservice.SpecialWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@ResponseBody
@RequestMapping(value = "/specialItem")
public class SpecialWSImpl implements SpecialWS{
	
  @Autowired
	private SpecialsDAO specialsDAO;

	@Override
	@RequestMapping(value = "/get", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String getSpecialItems() {
		
		DaySpecial daySpecial=null;
		try {
			daySpecial = specialsDAO.getSpecialItemAsObj();
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(daySpecial, DaySpecial.class);
		//System.out.println(json);
		return json;
	}

	
	public SpecialsDAO getSpecialsDAO() {
		return specialsDAO;
	}

	public void setSpecialsDAO(SpecialsDAO specialsDAO) {
		this.specialsDAO = specialsDAO;
	}
	
	

}
