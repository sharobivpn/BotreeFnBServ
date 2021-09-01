package com.botree.restaurantapp.commonUtil.test;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

public class PosPrinterMain {
	
	public static void main(String[] args){
		new PosPrinterMain().a();
	}
	
    public void a() {
        PageFormat format = new PageFormat();
        Paper paper = new Paper();

        double paperWidth = 3;//3.25
        double paperHeight = 3.69;//11.69
        double leftMargin = 0.12;
        double rightMargin = 0.10;
        double topMargin = 0;
        double bottomMargin = 0.01;

        paper.setSize(paperWidth * 200, paperHeight * 200);
        paper.setImageableArea(leftMargin * 200, topMargin * 200,
                (paperWidth - leftMargin - rightMargin) * 200,
                (paperHeight - topMargin - bottomMargin) * 200);

        format.setPaper(paper);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);
//testing 

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        Printable printable = new ReceiptPrint();

        format = printerJob.validatePage(format);
        //boolean don = printerJob.printDialog();
        printerJob.setPrintable(printable, format);
        try {
        	System.out.println("printer name::: "+printerJob.getPrintService().getName());
        
            printerJob.print(aset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
