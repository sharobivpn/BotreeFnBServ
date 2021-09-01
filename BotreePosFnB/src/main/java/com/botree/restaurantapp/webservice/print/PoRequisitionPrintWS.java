package com.botree.restaurantapp.webservice.print;

import com.botree.restaurantapp.print.PrintKotMaster;



public interface PoRequisitionPrintWS {

	public String printPurchaseOrderById(Integer storeid,Integer id);
	public String getAllInstalledPrinters();
	public String getAllServerPrinters(Integer storeid);
	public String assignPrinter(PrintKotMaster kotMaster);

	
}
