package com.botree.restaurantapp.webservice.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.botree.restaurantapp.dao.StoreAddressDAOImpl;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.model.StoreMaster;

import javawebparts.core.org.apache.commons.lang.WordUtils;

class PoRequisitionPrint implements Printable {

	SimpleDateFormat df = new SimpleDateFormat();
	String receiptDetailLine;
	public static final String pspace = "               ";// 15-spaces
	InventoryPurchaseOrder po = null;
	int storeId;
	StoreMaster store = null;
	private BufferedImage image;
	StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		try {
			int cH = 0;
			df.applyPattern("dd/MM/yyyy HH:mm:ss");
			String strText = null;
			final String LF = "\n";// text string to output
			int lineStart; // start index of line in textarea
			int lineEnd; // end index of line in textarea
			int lineNumber;
			int lineCount;
			final String SPACE = "          ";// 10 spaces
			final String SPACES = "         ";// 9
			final String uline = "________________________________________";
			final String dline = "----------------------------------------";
			String greetings = "THANKS FOR YOUR VISIT";
			receiptDetailLine = "asdasdasda";
			// Object[] dataToPrnt = null;

			Graphics2D g2d = (Graphics2D) graphics;
			Graphics2D g2dKotTxt = (Graphics2D) graphics;
			Graphics2D g2dResTitle = (Graphics2D) graphics;
			Graphics2D g2dDateTime = (Graphics2D) graphics;
			Graphics2D g2dResTable = (Graphics2D) graphics;
			Graphics2D g2dItemTitle = (Graphics2D) graphics;
			Graphics2D g2dItem = (Graphics2D) graphics;
			Graphics2D g2dNoPersons = (Graphics2D) graphics;
			Graphics2D g2dAddress = (Graphics2D) graphics;

			// StoreMaster store = addressDAOImpl.getStoreByStoreId(storeId);
			int kotResTitlFont = store.getKotResTitleFont();
			int kotTxtFont = store.getKotTextFont();
			int kotDateTimeFont = store.getKotDateTimeFont();
			int kotItemFont = store.getKotItemFont();
			int kotItemTitlFont = store.getKotItemTitleFont();
			int kotResTableFont = store.getKotTableFont();
			int kotNoPersonFont = store.getKotNoOfPersonFont();

			Font font = new Font("MONOSPACED", Font.BOLD, 10);
			Font fontKotTxt = new Font("MONOSPACED", Font.BOLD, kotTxtFont);
			Font fontResTitle = new Font("MONOSPACED", Font.BOLD,
					kotResTitlFont);
			Font fontDateTime = new Font("MONOSPACED", Font.BOLD,
					kotDateTimeFont);
			Font fontResTable = new Font("MONOSPACED", Font.BOLD,
					kotResTableFont);
			Font fontItemTitle = new Font("MONOSPACED", Font.BOLD,
					kotItemTitlFont);
			Font fontItem = new Font("MONOSPACED", Font.BOLD, kotItemFont);
			Font fontNoOfPerson = new Font("MONOSPACED", Font.BOLD,
					kotNoPersonFont);
			DecimalFormat decim = new DecimalFormat("00.00");

			if (pageIndex < 0 || pageIndex >= 1) {
				return Printable.NO_SUCH_PAGE;
			}

			// Get Print Data
			// dataToPrnt = getPrintData();

			String strName = store.getStoreName();
			String address = store.getAddress();
			String emailId = store.getEmailId();
			String phNumber = store.getPhoneNo();

			// int length=keys.size();
			// Print Table
			int space = 10;
			int y = 10;
			/*
			 * g2dKotTxt.translate(pageFormat.getImageableX(),
			 * pageFormat.getImageableY());
			 * 
			 * g2dKotTxt.setFont(fontKotTxt); g2dKotTxt.drawString(kot, 0, y);
			 */

			y = y + space;
			g2dResTitle.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2dResTitle.setFont(fontResTitle);
			g2dResTitle.drawString("Purchase Order    ", 0, y);

			y = y + space;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString("Date:" + po.getDate().toString().split(" ")[0]
					+ " " + po.getTime(), 0, y);

			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(fontResTitle);

			y = y + space + 10;
			g2dResTitle.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2dResTitle.setFont(fontResTitle);
			g2dResTitle.drawString(strName, 0, y);

			y = y + space + 10;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString(address, 0, y);

			y = y + space;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString("Email: " + emailId, 0, y);

			y = y + space;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString("Ph: " + phNumber, 0, y);

			y = y + space;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString("Approved By: " + po.getApprovedBy(), 0, y);

			y = y + space;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2d.setFont(font);
			g2d.drawString("PO By: " + po.getPoBy(), 0, y);

			y = y + space;
			g2d.drawString("...................................", 00, y);
			cH = cH + 25;
			// g2d.drawString("Thank you. Visit us again.  ", 00, cH);
			g2dItemTitle.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2dItemTitle.setFont(fontItemTitle);
			y = y + space + 5;
			g2dItemTitle.drawString("Item Name        Qty  Price", 00, y);

			List<InventoryPurchaseOrderItem> items = po
					.getInventoryPurchaseOrderItems();
			Iterator<InventoryPurchaseOrderItem> iterator = items.iterator();

			g2dItem.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2dItem.setFont(fontItem);

			int d3 = 6;
			int d4 = 2 * d3;
			int bx = 160;
			int i = 0;
			String totalPrice = "";
			double totalPric = 0.0;
			while (iterator.hasNext()) {
				InventoryPurchaseOrderItem item = (InventoryPurchaseOrderItem) iterator
						.next();

				i++;
				String itemname = item.getInventoryItems().getName();

				String quantity = "" + item.getItemQuantity()
						+ item.getInventoryItems().getUnit();
				totalPric = totalPric + item.getItemTotalPrice();
				String totalPriceByItem = decim
						.format(item.getItemTotalPrice());
				// totalPrice=totalPrice+totalPriceByItem;

				cH = (y) + (15 * i); // shifting drawing line
				// if (i != (mod.length - 1))
				{
					int length1 = itemname.length();
					if (length1 <= 20) {
						g2dItem.drawString(itemname, 00, cH);
						g2dItem.drawString(quantity, 123, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, bx - d4+3, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, bx - d3+3, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx+3, cH);

					} else {
						String temp = WordUtils.wrap(itemname, 20);
						String split1[] = temp.split("\\n");

						g2dItem.drawString(split1[0], 00, cH);
						g2dItem.drawString(quantity, 123, cH);
						// g2dItem.drawString(totalPriceByItem, 160, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, bx - d4+3, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, bx - d3+3, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx+3, cH);

						for (int k = 1; k < split1.length; k++) {

							cH = (y + 10) + (15 * i);
							String name2 = split1[k];
							g2dItem.drawString(name2, 00, cH);
							g2dItem.drawString("", 130, cH);
							g2dItem.drawString("", 170, cH);
							y = y + 10;
						}

					}
				}
				i++;
				cH = cH + 15;
				g2dItem.drawString("V: "
						+ item.getInventoryItems().getVendorName(), 00, cH);

			}
			
			cH = cH + 10;
			g2d.drawString("...................................", 00, cH);

			cH = cH + 20;
			g2dItemTitle.drawString("Total", 00, cH);

			totalPrice = decim.format(totalPric);
			if (totalPrice.length() > 6) // d4
				g2dItem.drawString(totalPrice, bx - d4+3, cH);
			else if (totalPrice.length() > 5) // d3
				g2dItem.drawString(totalPrice, bx - d3+3, cH);
			else
				g2dItem.drawString(totalPrice, bx+3, cH);

			Calendar cal = Calendar.getInstance();
			cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			String currentTime = sdf.format(cal.getTime());

			cH = cH + 40;
			g2d.drawString(currentTime, 00, cH);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Printable.PAGE_EXISTS;
	}

	public void setPrintData(InventoryPurchaseOrder po, StoreMaster store) {

		this.po = po;
		this.store = store;

	}

}
