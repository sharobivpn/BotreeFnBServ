package com.botree.restaurantapp.commonUtil;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.model.StoreMaster;

public class BillSplitPosPrinterMain {

	/*
	 * public static void main(String[] args) { new PosPrinterMain().a(strName,
	 * secndStrng, thrdStrng, mapToHoldItems); }
	 */

	public void a(Object[] elements, int storeId) {
		PageFormat format = new PageFormat();
		Paper paper = new Paper();
		StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();
		StoreMaster store =null;
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
		aset.add(MediaSizeName.ISO_B0);

		PrinterJob printerJob = PrinterJob.getPrinterJob();

		SplitBillPrint printable = new SplitBillPrint();
		printable.setPrintData(elements, storeId);
		// format = printerJob.validatePage(format);
		// boolean don = printerJob.printDialog();
		// printerJob.setPrintable(printable, format);
		printerJob.setPrintable(printable);

		try {
			store = addressDAOImpl.getStoreByStoreId(storeId);
			for (int c = 0; c < store.getBillCount(); c++)
			{
				System.out.println("Print split bill job...");
				printerJob.print(aset);
			}
			//printerJob.print(aset);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("kitchen printer name:: "+printerJob.getPrintService().getName());

		/*
		 * //non-default printer
		 */
		if (false) {
			try {
				String printerNameDesired = "\\\\192.168.1.144\\Canon LBP2900";

				PrintService[] service = PrinterJob.lookupPrintServices(); // list
																			// of
																			// printers

				int count = service.length;

				DocPrintJob docPrintJob = null;

				for (int i = 0; i < count; i++) {
					System.out.println(service[i].getName());
					if (service[i].getName().trim()
							.equalsIgnoreCase(printerNameDesired)) {
						System.out.println("-------------"
								+ service[i].getName());
						docPrintJob = service[i].createPrintJob();
						i = count;
					}
				}
				aset = new HashPrintRequestAttributeSet();
				aset.add(OrientationRequested.PORTRAIT);
				PrinterJob pjob = PrinterJob.getPrinterJob();
				pjob.setPrintService(docPrintJob.getPrintService());
				pjob.setJobName("job" + printerNameDesired);
				printable = new SplitBillPrint();
				printable.setPrintData(elements, storeId);
				format = pjob.validatePage(format);
				// boolean don = printerJob.printDialog();
				pjob.setPrintable(printable, format);

				try {
					pjob.print(aset);
				} catch (Exception e) {
					e.printStackTrace();
				}

				printerNameDesired = "\\\\192.168.1.157\\THERMAL Receipt Printer";
				for (int i = 0; i < count; i++) {
					System.out.println(service[i].getName());
					if (service[i].getName().trim()
							.equalsIgnoreCase(printerNameDesired)) {
						System.out.println("================"
								+ service[i].getName());
						docPrintJob = service[i].createPrintJob();
						i = count;
					}
				}
				aset = new HashPrintRequestAttributeSet();
				aset.add(OrientationRequested.PORTRAIT);
				pjob = PrinterJob.getPrinterJob();
				pjob.setPrintService(docPrintJob.getPrintService());
				pjob.setJobName("job" + printerNameDesired);
				printable = new SplitBillPrint();
				printable.setPrintData(elements, storeId);
				format = pjob.validatePage(format);
				// boolean don = printerJob.printDialog();
				pjob.setPrintable(printable, format);

				try {
					pjob.print(aset);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//

	}

	

}
