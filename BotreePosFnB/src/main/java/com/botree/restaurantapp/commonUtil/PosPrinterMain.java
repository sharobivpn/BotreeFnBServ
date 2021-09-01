package com.botree.restaurantapp.commonUtil;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

import com.botree.restaurantapp.model.StoreMaster;

public class PosPrinterMain {

	/*
	 * public static void main(String[] args) { new PosPrinterMain().a(strName,
	 * secndStrng, thrdStrng, mapToHoldItems); }
	 */

	public void a(Object[] elements, StoreMaster store, String printerName) {
		PageFormat format = new PageFormat();
		Paper paper = new Paper();

		double paperWidth = 3;// 3.25
		double paperHeight = 3.69;// 11.69
		double leftMargin = 0.12;
		double rightMargin = 0.10;
		double topMargin = 0;
		double bottomMargin = 0.01;

		paper.setSize(paperWidth * 200, paperHeight * 200);
		paper.setImageableArea(leftMargin * 200, topMargin * 200, (paperWidth
				- leftMargin - rightMargin) * 200,
				(paperHeight - topMargin - bottomMargin) * 200);

		format.setPaper(paper);

		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(OrientationRequested.PORTRAIT);
		// testing

		PrintService[] service = PrinterJob.lookupPrintServices(); // list of
																	// printers
		int count = service.length;
		DocPrintJob docPrintJob = null;
		for (int i = 0; i < count; i++) {
			System.out.println("printers:" + service[i].getName());
			if (service[i].getName().trim().equalsIgnoreCase(printerName)) {
				System.out.println("-------------" + service[i].getName());
				docPrintJob = service[i].createPrintJob();
				i = count;
			}
		}

		PrinterJob printerJob = PrinterJob.getPrinterJob();
		try {
			if (docPrintJob != null)
				printerJob.setPrintService(docPrintJob.getPrintService());
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}
		printerJob.setJobName("job" + printerName);

		ReceiptPrint printable = new ReceiptPrint();
		printable.setPrintData(elements, store.getId());
		format = printerJob.validatePage(format);
		// boolean don = printerJob.printDialog();
		printerJob.setPrintable(printable, format);

		// System.out.println("kitchen printer name:: "+printerJob.getPrintService().getName());
		try {
			for (int c = 0; c < store.getKotCount(); c++) { // count kot
				System.out.println(c + "Printer name: ======== " + printerName);
				printerJob.print(aset);
			}

			if (store.getKotDefaultPrinter().equalsIgnoreCase("Y")) { // print
																		// to
																		// default
																		// printer
				PrintRequestAttributeSet aset1 = new HashPrintRequestAttributeSet();

				PrinterJob printerJob1 = PrinterJob.getPrinterJob();

				ReceiptPrint printable1 = new ReceiptPrint();
				
				printable1.setPrintData(elements, store.getId());
				format = printerJob1.validatePage(format);

				printerJob1.setPrintable(printable1, format);

				try {
					printerJob1.print(aset1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
