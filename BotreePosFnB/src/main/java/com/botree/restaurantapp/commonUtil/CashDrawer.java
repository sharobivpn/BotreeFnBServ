package com.botree.restaurantapp.commonUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

/**
 * The PrintText application expands on the PrintExample application in that it
 * images text on to the single page printed.
 */
public class CashDrawer implements Printable {
	/**
	 * The text to be printed.
	 */
	private static String mText = "Four score and seven years ago our fathers brought forth on this "

			+ "not perish from the earth.";
	/**
	 * Our text in a form for which we can obtain a AttributedCharacterIterator.
	 */
	private static final AttributedString mStyledText = new AttributedString(
			mText);

	/**
	 * Print a single page containing some sample text.
	 */
	static public void main(String args[]) {
		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		book.append(new CashDrawer(), new PageFormat(), 2);
		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);
		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				//printerJob.print();
				//

				CashDrawer p = new CashDrawer();
				
				//p.feedPrinter();

			} catch (Exception exception) {
				System.err.println("Printing error: " + exception);
			}
		}
	}

	ByteArrayOutputStream commandSet;

	public void openDrawer() {
		final byte[] openCD = { 27, 112, 0, 60, 120 };
		try {
			commandSet.write(openCD);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean feedPrinter(String cdCode) {
		try {
			String[] codeArr=cdCode.split(",");
			String code1=codeArr[0];
			
			String code2=codeArr[1];
			String code3=codeArr[2];
			String code4=codeArr[3];
			String code5=codeArr[4];
			//final byte[] b = cdCode.getBytes();
			final byte[] b = {new Byte(code1), new Byte(code2), new Byte(code3), new Byte(code4), new Byte(code5) };
			PrintService service = 
				    PrintServiceLookup.lookupDefaultPrintService();
			String printServiceName=null;
				if (service != null) {
				    printServiceName = service.getName();
				    System.out.println(" Defualt Print Service Name is " + printServiceName);
				} else {
				    System.out.println("No default print service found");
				}
			AttributeSet attrSet = new HashPrintServiceAttributeSet(
					new PrinterName(printServiceName, null));
			// what should I change PRINTERNAME to connect directly to cash
			// drawer
			
			DocPrintJob job = PrintServiceLookup.lookupPrintServices(null,attrSet)[0].createPrintJob();
			/*PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null,attrSet);
			for(int i=0;i<printServices.length;i++){
				
				System.out.println("print sevice nae:: "+printServices[i]);
			}
			//PrintService printService=printServices[0];
			//DocPrintJob job=printService.createPrintJob();
			DocPrintJob job = PrintServiceLookup.lookupPrintServices(null,attrSet)[0].createPrintJob();*/
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			Doc doc = new SimpleDoc(b, flavor, null);
			job.print(doc, null);
			System.out.println("cash drawer open Done !");
		} catch (javax.print.PrintException pex) {

			System.out.println("Printer Error " + pex.getMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Print a page of text.
	 */
	public int print(Graphics g, PageFormat format, int pageIndex) {
		/*
		 * We'll assume that Jav2D is available.
		 */
		Graphics2D g2d = (Graphics2D) g;
		/*
		 * Move the origin from the corner of the Paper to the corner of the
		 * imageable area.
		 */
		g2d.translate(format.getImageableX(), format.getImageableY());
		/*
		 * Set the text color.
		 */
		g2d.setPaint(Color.blue);
		/*
		 * Use a LineBreakMeasurer instance to break our text into lines that
		 * fit the imageable area of the page.
		 */
		Point2D.Float pen = new Point2D.Float();

		if (pageIndex > 0)
			mText = "<html><body><b>susim maiti"
					+ "conceived and so dedicated can long endure. We are met on a great "
					+ "battlefield of that war. We have come to dedicate a portion of "

					+ "The brave men, living and dead who struggled here have consecrated </b></body></html> ";

		AttributedString mStyledText = new AttributedString(mText);

		AttributedCharacterIterator charIterator = mStyledText.getIterator();
		LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator,
				g2d.getFontRenderContext());
		float wrappingWidth = (float) format.getImageableWidth();
		while (measurer.getPosition() < charIterator.getEndIndex()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			pen.y += layout.getAscent();
			float dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout
					.getAdvance());
			layout.draw(g2d, pen.x + dx, pen.y);
			pen.y += layout.getDescent() + layout.getLeading();
		}
		System.out.println("height" + pen.y + "  image height:"
				+ format.getImageableHeight());
		return Printable.PAGE_EXISTS;
	}
}
