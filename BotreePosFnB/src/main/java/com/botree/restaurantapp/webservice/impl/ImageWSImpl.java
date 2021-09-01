package com.botree.restaurantapp.webservice.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.botree.restaurantapp.webservice.ImageWS;

@Controller
@ResponseBody
@RequestMapping(value = "/image")
public class ImageWSImpl implements ImageWS {
	
	/*private String path="";
	

	public ImageWSImpl() {
		super();
		//String path = "";
		String ops = System.getProperty("os.name").toLowerCase();
		System.out.println("operating system is: " + ops);
		if (ops.startsWith("windows")) {
			System.out.println("windows machine");
			path = "c:/restaurantImages";
		}

		if (ops.startsWith("unix") || ops.startsWith("linux")) {
			System.out.println("unix machine");
			path = "/home/ubuntu/.resturant/images";
		}
		// TODO Auto-generated constructor stub
	}*/

	@Override
	@RequestMapping(value = "/store/get", method = RequestMethod.GET)
	public void getStoreImage(	@RequestParam(value = "name") int name,
							HttpServletRequest request,
							HttpServletResponse response) {
		
		try {
			//String path = "/home/ubuntu/.resturant/logo";
			String path = "/home/ubuntu/restwebposlite/Images/logo";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out.println("getStoreImage: windows machine");
				path = "C:/restaurantImages/logo";
			}

			/*if (ops.startsWith("unix") || ops.startsWith("linux")) {
				System.out.println("unix machine");
				path = "/home/ubuntu/.resturant/images";
			}*/
			
			//File file = new File(request.getRealPath("/images/gallery/" + name + ".png"));
			File file = new File(path + "/" + name + ".png");
			InputStream is = new FileInputStream(file);
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@RequestMapping(value = "/fooditem/get", method = RequestMethod.GET)
	public void getFoodItemImage(	@RequestParam(value = "name") int name,
							HttpServletRequest request,
							HttpServletResponse response) {
		
		try {
			//String path = "/home/ubuntu/.resturant/fooditems"; //note: this(fooditems) folder needs to created in amazon
			String path = "/home/ubuntu/restwebposlite/Images/fooditems";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out.println("getFoodItemImage: windows machine");
				path = "C:/restaurantImages/fooditems";
				//path = "D:/2016-02-22";
			}

			/*if (ops.startsWith("unix") || ops.startsWith("linux")) {
				System.out.println("unix machine");
				path = "/home/ubuntu/.resturant/images";
			}*/
			
			//File file = new File(request.getRealPath("/images/gallery/" + name + ".png"));
			File file = new File(path + "/" + name + ".png");
			InputStream is = new FileInputStream(file);
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			response.setStatus(404);
			System.out.println("file not found exception! "+name+".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void getImage(	@RequestParam(value = "name") int name,
							HttpServletRequest request,
							HttpServletResponse response) {
		
		try {
			//String path = "/home/ubuntu/.resturant/images";
			String path = "/home/ubuntu/restwebposlite/Images/menu";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out.println("getImage: windows machine");
				path = "C:/restaurantImages/menu";
				//path = "D:/2016-02-22";
			}

			/*if (ops.startsWith("unix") || ops.startsWith("linux")) {
				System.out.println("unix machine");
				path = "/home/ubuntu/.resturant/images";
			}*/
			
			//File file = new File(request.getRealPath("/images/gallery/" + name + ".png"));
			File file = new File(path + "/" + name + ".png");
			InputStream is = new FileInputStream(file);
			List<Byte> buf = new ArrayList<Byte>();
			int ch = -1;
			while ((ch = is.read()) != -1) {
				buf.add((byte) ch);
			}
			byte[] array = new byte[buf.size()];
			for (int i = 0; i < buf.size(); i++) {
				array[i] = buf.get(i);
			}
			ServletOutputStream os = response.getOutputStream();
			os.write(array);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value = "/getNames",
	method = RequestMethod.GET,
	produces = "text/plain")
	public String getImageNames(HttpServletRequest request) {
		String reply = "";
		try {
			//String path = "/home/ubuntu/.resturant/images";
			String path = "/home/ubuntu/restwebposlite/Images/menu";
			String ops = System.getProperty("os.name").toLowerCase();
			System.out.println("operating system is: " + ops);
			if (ops.startsWith("windows")) {
				System.out.println("windows machine");
				path = "C:/restaurantImages/menu";
			}

			/*if (ops.startsWith("unix") || ops.startsWith("linux")) {
				System.out.println("unix machine");
				path = "/home/ubuntu/.resturant/images";
			}*/
		
			//File file = new File(request.getRealPath("/images/gallery/"));
			File file = new File(path+ "/");
			File[] files = file.listFiles();
			String fileName = null;
			for (int i = 0; i < files.length; i++) {
				fileName = files[i].getName();
				fileName = fileName.substring(0, fileName.indexOf('.'));
				reply += (fileName + ",");
			}
			reply = reply.substring(0, reply.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reply;
	}
	
	
}
