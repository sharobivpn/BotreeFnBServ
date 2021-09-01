package com.botree.restaurantapp.commonUtil;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.botree.restaurantapp.dao.StoreAddressDAOImpl;

import javawebparts.core.org.apache.commons.lang.WordUtils;

class BillPrint2 implements Printable {

	SimpleDateFormat df = new SimpleDateFormat();
	String receiptDetailLine;
	public static final String pspace = "               ";// 15-spaces
	Object[] printData = null;
	int storeId;
	private BufferedImage image;
	StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		try {
			df.applyPattern("dd/MM/yyyy HH:mm:ss");
			
			receiptDetailLine = "asdasdasda";
			Object[] dataToPrnt = null;

			
			Graphics2D g2dItem = (Graphics2D) graphics;
			int space = 10;
			int y = 20;
			

			
			Font font = new Font("MONOSPACED", Font.BOLD, 10);
			
			Font fontItem = new Font("MONOSPACED", Font.BOLD, 12);
			

			if (pageIndex < 0 || pageIndex >= 3) {
				return Printable.NO_SUCH_PAGE;
			}

			// Get Print Data
			dataToPrnt = getPrintData();

			// String kot = (String) dataToPrnt[0];
			
			Map<Integer, List<String>> itemsMap = (Map<Integer, List<String>>) dataToPrnt[8];
			int length = itemsMap.size();
			System.out.println("map length is..." + length);
			Set<Integer> keys = itemsMap.keySet();
			
			String mod[][][] = new String[length + 2][length + 2][length + 2];
			mod[0][0][0] = "Item Name";
			mod[0][0][1] = "Qty";
			mod[0][0][2] = "Amount";
			
			int j = 1;
			Iterator<Integer> iterateKeys = keys.iterator();
			while (iterateKeys.hasNext()) {

				Integer itemId = (Integer) iterateKeys.next();
				
				List<String> itemLst = itemsMap.get(itemId);
				String itemName = itemLst.get(0);
				String quantity = itemLst.get(1);
				System.out.println("item id is::  " + itemId + "  " + itemName);
				System.out.println("item quantity is::  " + quantity);
				String totalPriceByItem = itemLst.get(2);

				while (j < (mod.length - 1)) {
					mod[j][0][0] = itemName;
					mod[j][0][1] = quantity;
					mod[j][0][2] = totalPriceByItem;
					break;
				}
				j = j + 1;
			}
			mod[j][0][0] = "";
			mod[j][0][1] = ".............";
			mod[j][0][2] = "";

			int cH = 0;
			double start = 0.0;

			g2dItem.translate(start,
					pageFormat.getImageableY());
			g2dItem.setFont(fontItem);

			int d3 = 6;
			int d4 = 2 * d3;

			int bx = 160;

			for (int i = 1; i < mod.length; i++) { // items
				String itemname = mod[i][0][0];
				String quantity = mod[i][0][1];
				String totalPriceByItem = mod[i][0][2];
				cH = (y) + (15 * i); // shifting drawing line
				if (i != (mod.length - 1)) {
					int length1 = itemname.length();
					if (length1 <= 20) {
						g2dItem.drawString(itemname, 00, cH);
						g2dItem.drawString(quantity, 131, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, bx - d4, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, bx - d3, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx, cH);

					} else {
						String temp = WordUtils.wrap(itemname, 20);
						String split1[] = temp.split("\\n");

						g2dItem.drawString(split1[0], 00, cH);
						g2dItem.drawString(quantity, 131, cH);
						// g2dItem.drawString(totalPriceByItem, 160, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, bx - d4, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, bx - d3, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx, cH);

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
			}

	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Printable.PAGE_EXISTS;
	}

	public void setPrintData(Object[] data, int storeId) {

		this.printData = data;
		this.storeId = storeId;

	}

	public Object[] getPrintData() {

		return printData;

	}
}
