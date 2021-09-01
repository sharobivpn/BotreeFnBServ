package com.botree.restaurantapp.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.service.MaintenenceJobService;
import com.botree.restaurantapp.webservice.MaintenenceJobWS;

@Controller
@ResponseBody
@RequestMapping(value = "/maintenence")
public class MaintenenceJobWSImpl implements MaintenenceJobWS {

  @Autowired
	private MaintenenceJobService maintenenceJobService;

	@Override
	@RequestMapping(value = "/cleanLogByPeriod",
	method = RequestMethod.GET,
	consumes = "application/json",
	produces = "text/plain")
	public String cleanLogByPeriod(
			@RequestParam(value = "tomcatDir") String tomcatDir,
			@RequestParam(value = "days") String days) {

		String status = "";

		try {

			status = maintenenceJobService.cleanLogByPeriod(tomcatDir, days);

		} catch (Exception x) {
			status="failure";
			x.printStackTrace();

		}

		return status;
	}

	public MaintenenceJobService getMaintenenceJobService() {
		return maintenenceJobService;
	}

	public void setMaintenenceJobService(
			MaintenenceJobService maintenenceJobService) {
		this.maintenenceJobService = maintenenceJobService;
	}

}
