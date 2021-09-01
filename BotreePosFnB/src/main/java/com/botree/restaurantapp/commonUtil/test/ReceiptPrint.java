package com.botree.restaurantapp.commonUtil.test;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;

class ReceiptPrint implements Printable {

    SimpleDateFormat df = new SimpleDateFormat();
    String receiptDetailLine;
    public static final String pspace = "               ";//15-spaces

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        df.applyPattern("dd/MM/yyyy HH:mm:ss");
        String strText = null;
        final String LF = "\n";// text string to output
        int lineStart;           // start index of line in textarea
        int lineEnd;             // end index of line in textarea
        int lineNumber;
        int lineCount;
        final String SPACE = "          ";//10 spaces
        final String SPACES = "         ";//9
        final String uline = "________________________________________";
        final String dline = "----------------------------------------";
        String greetings = "THANKS FOR YOUR VISIT";
        receiptDetailLine = "asdasdasda";

        Graphics2D g2d = (Graphics2D) graphics;
        Font font = new Font("MONOSPACED", Font.BOLD, 9);

        if (pageIndex < 0 || pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        
       

        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        g2d.setFont(font);
       
        
        /*MediaTracker mt = new MediaTracker(textarea);
         URL imageURL = null;
         try {

         imageURL = new URL(mainDirectory+"quindell.png");
         } catch (MalformedURLException me) {
         me.printStackTrace();
         }

         //--- Load the image and wait for it to load
         Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
         mt.addImage(image, 0);
         */

       
        
        //Print Table
        int y = 10;
        g2d.drawString("Anglo Indian Cafe & Bar", 0, y); y = y+10;
        g2d.drawString("25/03 17:50 (148)", 0, y); y = y+10;
        g2d.drawString("Order No:2102, T-01", 0, y); y = y+10;
        
        String mod[][] = new String[][] {{"ITEM NAME","QTY"}, {"Ceaser Salad","1"}, {"MANCHOW SOUP","10"}, {"Tomato Shorba","4"}, {"Chicken Tikka W","2"}};
    	int cH = 0;
    	y=50;
    	for(int i = 0;i < mod.length ; i++){
	    	String itemname = mod[i][0];
	    	String quantity = mod[i][1];
	    	cH = (y) + (10*i); //shifting drawing line
	    	g2d.drawString(itemname,00, cH);
	    	g2d.drawString(quantity , 150, cH);
    	} 
        
        return Printable.PAGE_EXISTS;
    }
}
