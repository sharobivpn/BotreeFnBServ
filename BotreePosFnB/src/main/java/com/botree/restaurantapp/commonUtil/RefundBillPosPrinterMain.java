/**
 * 
 */
package com.botree.restaurantapp.commonUtil;

/**
 * @author admin
 *
 */


import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.StoreMaster;

public class RefundBillPosPrinterMain {

	/*
	 * public static void main(String[] args) { new PosPrinterMain().a(strName,
	 * secndStrng, thrdStrng, mapToHoldItems); }
	 */

	public void a(Object[] elements, int storeId) {
		PageFormat format = new PageFormat();
		Paper paper = new Paper();
		StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();
		double paperWidth = 3;// 3.25
		double paperHeight = 3.69;// 11.69
		double leftMargin = 0.12;
		double rightMargin = 0.10;
		double topMargin = 0;
		double bottomMargin = 0.01;
		StoreMaster store =null;

		paper.setSize(paperWidth * 200, paperHeight * 200);
		paper.setImageableArea(leftMargin * 200, topMargin * 200, (paperWidth
				- leftMargin - rightMargin) * 200,
				(paperHeight - topMargin - bottomMargin) * 200);

		format.setPaper(paper);

		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

		aset.add(OrientationRequested.PORTRAIT);
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		RefundBillPrint printable = new RefundBillPrint();
		RefundBillPrintArabic billPrintArabic=new RefundBillPrintArabic();
		try {
			store = addressDAOImpl.getStoreByStoreId(storeId);
			if(store.getPrintBillPaperSize().equalsIgnoreCase("2100.00")){
				aset.add(MediaSizeName.ISO_A4);
				RefundBillPrintA4 printableA4 = new RefundBillPrintA4();
				printableA4.setPrintData(elements, storeId);
				printerJob.setPrintable(printableA4);
			}
			else {
				aset.add(MediaSizeName.ISO_B0);
				if(store.getIsArabicBill().equalsIgnoreCase("Y")){
					billPrintArabic.setPrintData(elements, storeId);
					printerJob.setPrintable(billPrintArabic);
				}
				else {
					printable.setPrintData(elements, storeId);
					printerJob.setPrintable(printable);
				}
				
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		try {
			
				System.out.println("Refund Print job...");
				printerJob.print(aset);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("kitchen printer name:: "+printerJob.getPrintService().getName());

		/*
		 * //non-default printer
		 */
		
		//

	}

	

}
