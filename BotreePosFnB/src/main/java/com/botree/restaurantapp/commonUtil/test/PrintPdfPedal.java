package com.botree.restaurantapp.commonUtil.test;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;


public class PrintPdfPedal {

	
public static void main(String args[]) throws Exception{  
        new PrintPdfPedal().imageLookupDefaultPrintService();
       //new PrintPdfPedal().textLookupDefaultPrintService();
   } 

   public void imageLookupDefaultPrintService() throws Exception {
	  System.out.println("======= START LookupDefaultPrintServiceTest ======");
	  DocFlavor psFlavor=DocFlavor.INPUT_STREAM.GIF;
	  PrintService service;
	  InputStream fis;
	  PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
	  HashDocAttributeSet daset=new HashDocAttributeSet();
	  DocPrintJob pj;
	  Doc doc;
	  aset.add(new Copies(1));
	  aset.add(MediaSizeName.ISO_A4);
	  daset.add(MediaName.ISO_A4_WHITE);
	  daset.add(Sides.TWO_SIDED_LONG_EDGE);
	  service=PrintServiceLookup.lookupDefaultPrintService();
	  if (service != null) {
	    if (service.isDocFlavorSupported(psFlavor)) {
	      if (service.getUnsupportedAttributes(psFlavor,aset) == null) {
	       // fis=this.getClass().getResourceAsStream("add.txt");
	    	   fis = new FileInputStream("c://cc_logo.png");
	        //fis=this.getClass().getResourceAsStream("cc_logo.png");
	        doc=new SimpleDoc(fis,psFlavor,daset);
	        pj=service.createPrintJob();
	        
	        pj.print(doc,aset);
	        System.out.println(fis.toString() + " printed on " + service.getName());
	      }
	    }
	 else {
	      System.out.println("flavor is not supported");
	    }
	  }
	 else {
	    System.out.println("service not found");
	  }
	  System.out.println("======= END LookupDefaultPrintServiceTest =======");
	}
   
   public void textLookupDefaultPrintService() throws Exception {
		  System.out.println("======= START LookupDefaultPrintServiceTest ======");
		  //DocFlavor psFlavor=DocFlavor.INPUT_STREAM.GIF;
		  DocFlavor psFlavor=DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_8;
		  PrintService service;
		  InputStream fis;
		  PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
		  HashDocAttributeSet daset=new HashDocAttributeSet();
		  DocPrintJob pj;
		  Doc doc;
		  aset.add(new Copies(1));
		  aset.add(MediaSizeName.ISO_A4);
		  daset.add(MediaName.ISO_A4_WHITE);
		  daset.add(Sides.TWO_SIDED_LONG_EDGE);
		  service=PrintServiceLookup.lookupDefaultPrintService();
		  if (service != null) {
		    if (service.isDocFlavorSupported(psFlavor)) {
		      if (service.getUnsupportedAttributes(psFlavor,aset) == null) {
		        //String str = "<html><body>Susim Maiti <br> Bill No 98657</body> </html>";
		        //fis = new ByteArrayInputStream(str.getBytes());
		        // fis=this.getClass().getResourceAsStream("StLogo.png");
		    	
		        fis=this.getClass().getResourceAsStream("add.txt");
		        doc=new SimpleDoc(fis,psFlavor,daset);
		        pj=service.createPrintJob();
		        
		        pj.print(doc,aset);
		        System.out.println(fis.toString() + " printed on " + service.getName());
		      }
		    }
		 else {
		      System.out.println("flavor is not supported");
		    }
		  }
		 else {
		    System.out.println("service not found");
		  }
		  System.out.println("======= END LookupDefaultPrintServiceTest =======");
		}
}
