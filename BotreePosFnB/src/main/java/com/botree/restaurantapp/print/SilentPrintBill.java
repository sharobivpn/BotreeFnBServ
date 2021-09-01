package com.botree.restaurantapp.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobSheets;
import javax.servlet.http.HttpServletRequest;

public class SilentPrintBill {
	public static void main(String args[]) throws PrinterException, IOException {
	/*	int orientation = 1;
		String orderId = "1752";
		String data = "PIZA HUT:2015-01-15::16.18  \nOrder No:1752,T-14\n\nChowin: 3\nChicken Tikka: 2\n--------------------";
		new SilentPrintBill().printByRequest(orientation, orderId, data, request);*/

	}

	public void printByRequest(	int orientation,
								String orderId,
								String data,
								HttpServletRequest request) {

		try {

			String fileName = orderId + ".txt";
			//File file = new File("C:/Users/Raghunath/Desktop/testPrint/" + orderId + ".txt");
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			// if file doesnt exists, then create it 
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

			File ticket = null;
			//boolean bool = false;
			

			ticket = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/" + fileName);

			if (!ticket.exists()) {
				throw new PrinterException("Ticket to print does not exist (" + ticket.getAbsolutePath() + ") !");
			}
			PrinterJob pjob = PrinterJob.getPrinterJob();
			// get printer using PrintServiceLookup.lookupPrintServices(null, null) and looking at the name
			pjob.setPrintService(getPrintService());
			// job title
			pjob.setJobName(ticket.getName());

			// page fomat
			PageFormat pf = pjob.defaultPage();
			// landscape or portrait
			pf.setOrientation(orientation);
			// Paper properties
			Paper a4Paper = new Paper();
			double paperWidth = 8.26;
			double paperHeight = 11.69;
			double margin = 16;
			a4Paper.setSize(paperWidth * 72.0, paperHeight * 72.0);
			a4Paper.setImageableArea(margin,
			//0,
					margin,
					//0,
					a4Paper.getWidth() - 2 * margin,
					//a4Paper.getWidth(),
					a4Paper.getHeight() - 2 * margin
			//a4Paper.getHeight()
			); // no margin = no scaling
			pf.setPaper(a4Paper);
			// Custom class that defines how to layout file text
			TicketPrintPage pages = new TicketPrintPage(ticket);

			// adding the page to a book
			Book book = new Book();
			book.append(pages, pf);
			// Adding the book to a printjob
			pjob.setPageable(book);
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			// No jobsheet (banner page, the page with user name, job name, date and whatnot)
			pras.add(JobSheets.NONE);
			// Printing
			pjob.print(pras);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public PrintService getPrintService() {
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
		return printService;

	}

}

class TicketPrintPage implements Printable {

	private File ticket;

	public TicketPrintPage(File f) {
		ticket = f;
	}

	public int print(	Graphics g,
						PageFormat pf,
						int pageIndex) throws PrinterException {
		int interline = 12;
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("CourierThai", Font.PLAIN, 10));
		int x = (int) pf.getImageableX();
		int y = (int) pf.getImageableY();

    try (FileReader fr = new FileReader(ticket); BufferedReader br = new BufferedReader(fr)) {
			String s;
			while ((s = br.readLine()) != null) {
				y += interline;
				g2.drawString(s, x, y);
			}
		} catch (IOException e) {
			throw new PrinterException("File to print does not exist (" + ticket.getAbsolutePath() + ") !");
		}
		return Printable.PAGE_EXISTS;
	}
}