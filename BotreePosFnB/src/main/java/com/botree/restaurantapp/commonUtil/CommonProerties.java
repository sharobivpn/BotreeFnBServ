package com.botree.restaurantapp.commonUtil;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;

public class CommonProerties {

	// public static String mainServerUrl =
	// "http://http://54.187.126.219:8080/Restaurant"; //amazon

	// public static String mainServerUrl = "http://localhost:8080/Restaurant";
	// //192.168.1.87 server

  // Store IDs
  public static int SARAVANA1 = 37; // SARAVANA1
  public static int SARAVANA2 = 38; // SARAVANA2
  public static int HATAM_AL_TAAI = 39; // HATAM AL TAAI
  public static int FAVA_BARISTO = 157;
  public static int YUMMY = 158;
  // Store IDs

	public static String billSplitEqual = "A";
	public static String billSplitRatio = "R";
	public static String salesDaily = "daily";
	public static String salesMonthly = "monthly";
	public static String salesYearly = "yearly";
	public static String salesWeekly = "weekly";
	public static String duplicate_phone_number = "100";

	public static boolean test = true;
	public static boolean testLicense = true;
	public static boolean longWaitingOrder = false;
	private static EntityManager em = null;
	private static EntityManagerFactory entityManagerFactory;

	public String getMainServerUrl() {

		FacesContext context = FacesContext.getCurrentInstance();
		StoreMaster storeMaster = (StoreMaster) context.getExternalContext()
				.getSessionMap().get("selectedstore");
		String storeIp = storeMaster.getIp();

		String mainServerUrl = "http://54.187.126.219:8080/Restaurant"; // amazon
																		// main
																		// server
		String ops = System.getProperty("os.name").toLowerCase();
		System.out.println("operating system is: " + ops);
		if (ops.startsWith("windows")) {
			System.out
					.println("menucontroller:windows machine: upload image: ");
			mainServerUrl = "http://" + storeIp + "/Restaurant";
		}

		return mainServerUrl;
	}

	public static double roundOffUptoTwoPlacesDouble(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		double tmp = value;
		if (value % 1 != 0.5) {
			tmp = Math.round(value);
		}

		return (double) tmp / factor;
	}

	public static String[] getCatSubcatNames(Integer itemId, Integer storeid) {
		String[] names = new String[2];
		try {

			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry1 = em
					.createQuery("SELECT i FROM MenuItem i WHERE i.id=:itemId and i.storeId=:storeId");
			qry1.setParameter("itemId", itemId);
			qry1.setParameter("storeId", storeid);
			MenuItem item = (MenuItem) qry1.getSingleResult();
			MenuCategory subCat = item.getMenucategory();
			String subcatName = subCat.getMenuCategoryName();
			MenuCategory cat = subCat.getMenutype();
			String catName = cat.getMenuCategoryName();
			names[0] = catName;
			names[1] = subcatName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			em.close();
		}
		return names;
	}

}
