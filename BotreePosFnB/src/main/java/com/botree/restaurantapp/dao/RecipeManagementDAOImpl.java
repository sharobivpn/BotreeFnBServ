package com.botree.restaurantapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.Restaurant.rahulbean.FgClose;
import com.botree.Restaurant.rahulbean.FgCloseChild;
import com.botree.Restaurant.rahulbean.FgSaleOut;
import com.botree.Restaurant.rahulbean.FgSaleOutItemsChild;
import com.botree.Restaurant.rahulbean.RawClose;
import com.botree.Restaurant.rahulbean.RawCloseChild;
import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.model.InventoryStockOut;
import com.botree.restaurantapp.model.InventoryStockOutItem;
import com.botree.restaurantapp.model.InventoryVendor;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.inv.FgStockIn;
import com.botree.restaurantapp.model.inv.FgStockInItemsChild;
import com.botree.restaurantapp.model.rm.CookingUnit;
import com.botree.restaurantapp.model.rm.EstimateDailyProd;
import com.botree.restaurantapp.model.rm.EstimateDailyProdItem;
import com.botree.restaurantapp.model.rm.FmcgUnit;
import com.botree.restaurantapp.model.rm.IngrdientForAnEdp;
import com.botree.restaurantapp.model.rm.Ingredient;
import com.botree.restaurantapp.model.rm.IngredientList;
import com.botree.restaurantapp.model.rm.MetricUnit;
import com.botree.restaurantapp.model.rm.UnitConversion;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("recipeManagementDAO")
public class RecipeManagementDAOImpl implements RecipeManagementDAO {
  
	//private final static Logger LOGGER = LogManager.getLogger(RecipeManagementDAOImpl.class);

	private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<CookingUnit> getAllCookingUnits(Integer storeId)
			throws DAOException {

		List<CookingUnit> cookingUnits = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<CookingUnit> qry = em
					.createQuery("SELECT c FROM CookingUnit c WHERE c.storeId=:storeid and c.deleteFlag='N'",
					    CookingUnit.class);
			qry.setParameter("storeid", storeid);
			cookingUnits = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return cookingUnits;
	}

	@Override
	public double geMetrictUnitConVersionFactor(Integer storeId,
			Integer fromUnit, Integer toUnit) throws DAOException {

		double factor = 0.0;
		int fromUnit1 = (fromUnit);
		int toUnit1 = (toUnit);
		EntityManager em = null;
		UnitConversion unitConversion = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<UnitConversion> qry = em
					.createQuery("SELECT c FROM UnitConversion c WHERE c.metricUnit1.id=:metricunit1 and c.metricUnit.id=:metricunit and c.deleteFlag='N'",
					    UnitConversion.class);
			qry.setParameter("metricunit1", fromUnit1);
			qry.setParameter("metricunit", toUnit1);
			unitConversion = qry.getSingleResult();
			if (unitConversion != null) {
				factor = unitConversion.getToUnitAmount();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return factor;
	}

	@Override
	public List<FgSaleOutItemsChild> getFgSaleOutByDate(Integer storeId,
			String date) throws DAOException {

		HashMap<Integer, FgSaleOutItemsChild> fgSaleOutMap = new HashMap<Integer, FgSaleOutItemsChild>();
		List<FgSaleOutItemsChild> fgSaleOutItemsChildList = null;

		int storeid = (storeId);
		EntityManager em = null;

		Connection connection = null;
		String selectSQLForCustomerDisc = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			// saved data
			TypedQuery<FgSaleOutItemsChild> qry = em
					.createQuery("SELECT f FROM FgSaleOutItemsChild f WHERE f.storeId=:storeid and f.createdDate=:createdDate and f.deleteFlag='N'",
					    FgSaleOutItemsChild.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("createdDate", date);
			fgSaleOutItemsChildList =qry.getResultList();

			for (FgSaleOutItemsChild fc : fgSaleOutItemsChildList) {
				fgSaleOutMap.put(fc.getItemId(), fc);
			}

			// if (fgSaleOutItemsChildList == null)
			{ // edp in
				selectSQLForCustomerDisc = "select item_id, sum(ed_prod_amount) as edp_quantity  FROM restaurant.im_t_estimate_daily_prod_item_c where created_date='"
						+ date
						+ "' and store_id = "
						+ storeId
						+ " group by item_id";
				System.out.println("selectSQLForCustomerDisc:::"
						+ selectSQLForCustomerDisc);
				st = connection.createStatement();
				rs = st.executeQuery(selectSQLForCustomerDisc);
				while (rs.next()) {
					int i = rs.getInt("item_id");
					FgSaleOutItemsChild fgSaleOutItemsChild = fgSaleOutMap
							.get(i);

					if (fgSaleOutItemsChild != null) {
						fgSaleOutItemsChild.setItemId(rs.getInt("item_id"));
						fgSaleOutItemsChild.setEdpQuantity(rs
								.getDouble("edp_quantity"));

					} else {
						fgSaleOutItemsChild = new FgSaleOutItemsChild();
						fgSaleOutItemsChild.setItemId(rs.getInt("item_id"));
						fgSaleOutItemsChild.setEdpQuantity(rs
								.getDouble("edp_quantity"));
						fgSaleOutMap.put(fgSaleOutItemsChild.getItemId(),
								fgSaleOutItemsChild);
					}
				}
			}

			// stock in
			selectSQLForCustomerDisc = "select item_id, sum(stock_in_quantity) as stock_in  FROM restaurant.im_t_fg_stock_in_items_c where created_date='"
					+ date
					+ "' and store_id = "
					+ storeId
					+ " group by item_id";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				int i = rs.getInt("item_id");
				FgSaleOutItemsChild fgSaleOutItemsChild = fgSaleOutMap.get(i);

				if (fgSaleOutItemsChild != null) {
					fgSaleOutItemsChild.setStockInQuantity(rs
							.getDouble("stock_in"));
				} else {
					fgSaleOutItemsChild = new FgSaleOutItemsChild();
					fgSaleOutItemsChild.setItemId(rs.getInt("item_id"));
					fgSaleOutItemsChild.setStockInQuantity(rs
							.getDouble("stock_in"));
					fgSaleOutMap.put(fgSaleOutItemsChild.getItemId(),
							fgSaleOutItemsChild);
				}

			}

			// stock/sale out
			String dateFormated = date.split("-")[2] + "/" + date.split("-")[1]
					+ "/" + date.split("-")[0]; // "17/08/2015";
			selectSQLForCustomerDisc = "select item_id, sum(quantityof_item) as sale_out from fo_t_orders_item where order_time like '"
					+ dateFormated
					+ "%' and store_id = "
					+ storeId
					+ " group by item_id";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				int i = rs.getInt("item_id");
				FgSaleOutItemsChild fgSaleOutItemsChild = fgSaleOutMap.get(i);

				if (fgSaleOutItemsChild != null) {
					fgSaleOutItemsChild.setSaleOutQuantity(rs
							.getDouble("sale_out"));
				} else {
					fgSaleOutItemsChild = new FgSaleOutItemsChild();
					fgSaleOutItemsChild.setItemId(rs.getInt("item_id"));
					fgSaleOutItemsChild.setSaleOutQuantity(rs
							.getDouble("sale_out"));
					fgSaleOutMap.put(fgSaleOutItemsChild.getItemId(),
							fgSaleOutItemsChild);
				}

			}

			connection.close();

			fgSaleOutItemsChildList = new ArrayList<FgSaleOutItemsChild>(
					fgSaleOutMap.values());
			List<Integer> itemIdList = new ArrayList<Integer>();
			for (FgSaleOutItemsChild fc : fgSaleOutItemsChildList) {
				itemIdList.add(fc.getItemId());
			}
			if (!itemIdList.isEmpty()) {
				TypedQuery<MenuItem> qry1 = em
						.createQuery("SELECT i FROM MenuItem i WHERE i.id IN (:itemIdList) and i.storeId=:storeId",
						    MenuItem.class);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("itemIdList", itemIdList);
				List<MenuItem> items = qry1.getResultList();
				for (FgSaleOutItemsChild fc : fgSaleOutItemsChildList) {
					for (MenuItem item : items) {
						if (fc.getItemId() == item.getId()) {
							MenuCategory subCat = item.getMenucategory();
							MenuCategory cat = subCat.getMenutype();
							String catName = cat.getMenuCategoryName();
							fc.setItemName(item.getName());
							fc.setCategoryName(catName);
							fc.setSubCategoryName(subCat.getMenuCategoryName());
						}
					}
				}
			}
			// fgSaleOutItemsChildList=(List<FgSaleOutItemsChild>)fgSaleOutMap.values();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();		}

		return fgSaleOutItemsChildList;
	}

	@Override
	public List<FgCloseChild> getFgCloseItemsByDate(Integer storeId, String date)
			throws DAOException {

		HashMap<Integer, FgCloseChild> fgCloseMap = new HashMap<Integer, FgCloseChild>();
		List<FgCloseChild> fgCloseChildList = null;
		FgClose fgClose = null;
		String fgCloseApprove = "";

		EntityManager em = null;

		Connection connection = null;
		String selectSQLForCustomerDisc = null;
		Statement st = null;
		ResultSet rs = null;
		int storeid = (storeId);
		try {

			
			em = entityManagerFactory.createEntityManager();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			// old data from im_t_fg_clse_c

			try {
				TypedQuery<FgCloseChild> qry = em
						.createQuery("SELECT f FROM FgCloseChild f WHERE f.storeId=:storeid and f.createdDate=:createdDate and f.deleteFlag='N'",
						    FgCloseChild.class);
				qry.setParameter("storeid", storeid);
				qry.setParameter("createdDate", date);
				fgCloseChildList = qry.getResultList();

				Iterator<FgCloseChild> iterator = fgCloseChildList.iterator();
				int fgCloseId = 0;
				while (iterator.hasNext()) {
					FgCloseChild fgCloseChild = (FgCloseChild) iterator.next();
					fgCloseId = fgCloseChild.getFgClseId();
					break;

				}

				Query qry1 = em
						.createQuery("SELECT f FROM FgClose f WHERE f.id=:id and f.storeId=:storeid");
				qry1.setParameter("storeid", storeid);
				qry1.setParameter("id", fgCloseId);
				fgClose = (FgClose) qry1.getSingleResult();

				if (fgClose != null) {

					fgCloseApprove = fgClose.getApproved();
				}

				for (FgCloseChild fc : fgCloseChildList) {
					fc.setApproved(fgCloseApprove);
					fgCloseMap.put(fc.getItemId(), fc);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if (fgCloseChildList == null)
			try {
				// edp in

				selectSQLForCustomerDisc = "select item_id, sum(ed_prod_amount) as edp_quantity  FROM restaurant.im_t_estimate_daily_prod_item_c where created_date='"
						+ date
						+ "' and store_id = "
						+ storeId
						+ " group by item_id";
				System.out.println("selectSQLForCustomerDisc:::"
						+ selectSQLForCustomerDisc);
				st = connection.createStatement();
				rs = st.executeQuery(selectSQLForCustomerDisc);
				while (rs.next()) {
					int i = rs.getInt("item_id");
					FgCloseChild fgCloseChild = fgCloseMap.get(i);

					if (fgCloseChild != null) {
						fgCloseChild.setItemId(rs.getInt("item_id"));
						fgCloseChild.setEdpQuantity(rs
								.getDouble("edp_quantity"));
					} else {
						fgCloseChild = new FgCloseChild();
						fgCloseChild.setItemId(rs.getInt("item_id"));
						fgCloseChild.setEdpQuantity(rs
								.getDouble("edp_quantity"));
						fgCloseMap.put(fgCloseChild.getItemId(), fgCloseChild);
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// stock in
				selectSQLForCustomerDisc = "select item_id, sum(stock_in_quantity) as stock_in  FROM restaurant.im_t_fg_stock_in_items_c where created_date='"
						+ date
						+ "' and store_id = "
						+ storeId
						+ " group by item_id";
				System.out.println("selectSQLForCustomerDisc:::"
						+ selectSQLForCustomerDisc);
				st = connection.createStatement();
				rs = st.executeQuery(selectSQLForCustomerDisc);
				while (rs.next()) {
					int i = rs.getInt("item_id");
					FgCloseChild fgCloseChild = fgCloseMap.get(i);

					if (fgCloseChild != null) {
						fgCloseChild.setStockInQuantity(rs
								.getDouble("stock_in"));
					} else {
						fgCloseChild = new FgCloseChild();
						fgCloseChild.setItemId(rs.getInt("item_id"));
						fgCloseChild.setStockInQuantity(rs
								.getDouble("stock_in"));
						fgCloseMap.put(fgCloseChild.getItemId(), fgCloseChild);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// stock out
				String dateFormated = date.split("-")[2] + "/"
						+ date.split("-")[1] + "/" + date.split("-")[0]; // "17/08/2015";
				selectSQLForCustomerDisc = "select item_id, sum(quantityof_item) as sale_out from fo_t_orders_item where order_time like '"
						+ dateFormated
						+ "%' and store_id = "
						+ storeId
						+ " group by item_id";
				System.out.println("selectSQLForCustomerDisc:::"
						+ selectSQLForCustomerDisc);
				st = connection.createStatement();
				rs = st.executeQuery(selectSQLForCustomerDisc);
				while (rs.next()) {
					int i = rs.getInt("item_id");
					FgCloseChild fgCloseChild = fgCloseMap.get(i);

					if (fgCloseChild != null) {
						fgCloseChild.setSaleOutQuantity(rs
								.getDouble("sale_out"));
					} else {
						fgCloseChild = new FgCloseChild();
						fgCloseChild.setItemId(rs.getInt("item_id"));
						fgCloseChild.setSaleOutQuantity(rs
								.getDouble("sale_out"));
						fgCloseMap.put(fgCloseChild.getItemId(), fgCloseChild);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			connection.close();

			fgCloseChildList = new ArrayList<FgCloseChild>(fgCloseMap.values());

			List<Integer> itemIdList = new ArrayList<Integer>();
			for (FgCloseChild fc : fgCloseChildList) {
				itemIdList.add(fc.getItemId());
			}
			if (!itemIdList.isEmpty()) {
				TypedQuery<MenuItem> qry1 = em
						.createQuery("SELECT i FROM MenuItem i WHERE i.id IN (:itemIdList) and i.storeId=:storeId",
						    MenuItem.class);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("itemIdList", itemIdList);
				List<MenuItem> items = qry1.getResultList();
				for (FgCloseChild fc : fgCloseChildList) {
					for (MenuItem item : items) {
						if (fc.getItemId() == item.getId()) {
							MenuCategory subCat = item.getMenucategory();
							MenuCategory cat = subCat.getMenutype();
							String catName = cat.getMenuCategoryName();
							fc.setItemName(item.getName());
							fc.setCategoryName(catName);
							fc.setSubCategoryName(subCat.getMenuCategoryName());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return fgCloseChildList;
	}

	@Override
	public List<RawCloseChild> getRawCloseItemsByDate(Integer storeId,
			String date) throws DAOException {

		HashMap<Integer, RawCloseChild> rawCloseMap = new HashMap<Integer, RawCloseChild>();
		List<RawCloseChild> rawCloseChildList = null;
		List<InventoryItems> itemList = null;
		StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();
		InventoryDAOImpl dao = new InventoryDAOImpl();

		EntityManager em = null;

		Connection connection = null;
		String selectSQLForCustomerDisc = null;
		Statement st = null;
		ResultSet rs = null;
		int storeid = (storeId);
		try {
			StoreMaster store = addressDAOImpl.getStoreByStoreId(storeid);
			int period = store.getStockPeriod();
			
			em = entityManagerFactory.createEntityManager();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			// old data from im_t_raw_clse_c

			TypedQuery<RawCloseChild> qry = em
					.createQuery("SELECT r FROM RawCloseChild r WHERE r.storeId=:storeid and r.createdDate=:createdDate and r.deleteFlag='N'",
					    RawCloseChild.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("createdDate", date);
			rawCloseChildList = qry.getResultList();

			for (RawCloseChild rc : rawCloseChildList) {
				rawCloseMap.put(rc.getItemId(), rc);
			}

			// stock in
			selectSQLForCustomerDisc = "select item_id,unit_id, sum(item_quantity) as stock_in_quantity  FROM restaurant.im_t_raw_stock_in_items_c where created_date='"
					+ date
					+ "' and store_id = "
					+ storeId
					+ " group by item_id";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);

			while (rs.next()) {
				int i = rs.getInt("item_id");
				// get unit id
				int unit = rs.getInt("unit_id");
				RawCloseChild rawCloseChild = rawCloseMap.get(i);
				if (rawCloseChild != null) {
					rawCloseChild.setItemId(rs.getInt("item_id"));
					rawCloseChild.setStockInQuantity(rs
							.getDouble("stock_in_quantity"));
					rawCloseChild.setUnitId(unit);
					rawCloseMap.put(rawCloseChild.getItemId(), rawCloseChild);
				} else {
					RawCloseChild rawClseChild = new RawCloseChild();
					rawClseChild.setItemId(rs.getInt("item_id"));
					rawClseChild.setStockInQuantity(rs
							.getDouble("stock_in_quantity"));
					rawClseChild.setUnitId(unit);
					rawCloseMap.put(rawClseChild.getItemId(), rawClseChild);
				}

			}

			// stock out
			selectSQLForCustomerDisc = "select item_id,unit_id, sum(item_quantity) as stock_out_quantity from restaurant.im_t_raw_stock_out_items_c where created_date='"
					+ date
					+ "' and store_id = "
					+ storeId
					+ " group by item_id";
			System.out.println("selectSQLForCustomerDisc:::"
					+ selectSQLForCustomerDisc);
			st = connection.createStatement();
			rs = st.executeQuery(selectSQLForCustomerDisc);
			while (rs.next()) {
				int i = rs.getInt("item_id");
				// get unit id
				int unit = rs.getInt("unit_id");
				RawCloseChild rawCloseChild = rawCloseMap.get(i);

				if (rawCloseChild != null) {
					rawCloseChild.setStockOutQuantity(rs
							.getDouble("stock_out_quantity"));
					rawCloseChild.setUnitId(unit);
				} else {
					rawCloseChild = new RawCloseChild();
					rawCloseChild.setItemId(rs.getInt("item_id"));
					rawCloseChild.setStockOutQuantity(rs
							.getDouble("stock_out_quantity"));
					rawCloseChild.setUnitId(unit);
					rawCloseMap.put(rawCloseChild.getItemId(), rawCloseChild);
				}

			}

			rawCloseChildList = new ArrayList<RawCloseChild>(
					rawCloseMap.values());
			// get current stock for each item
			Iterator<RawCloseChild> iterator1 = rawCloseChildList.iterator();
			while (iterator1.hasNext()) {
				RawCloseChild rawCloseChild = (RawCloseChild) iterator1.next();
				int itemId = rawCloseChild.getItemId();

				String currentStock = dao.getCurrentStockByItemByPeriod(storeId, itemId, period);
				rawCloseChild
						.setPhysicalStock(Double.parseDouble(currentStock));

			}

			// set approved status
			RawClose rawClose = null;
			Iterator<RawCloseChild> iterator = rawCloseChildList.iterator();
			while (iterator.hasNext()) {
				RawCloseChild rawCloseChild = (RawCloseChild) iterator.next();
				int rawClseId = rawCloseChild.getRawClseId();

				Query qry1 = em
						.createQuery("SELECT r FROM RawClose r WHERE r.id=:id and r.storeId=:storeid and r.deleteFlag='N'");
				qry1.setParameter("storeid", storeid);
				qry1.setParameter("id", rawClseId);

				try {
					rawClose = (RawClose) qry1.getSingleResult();

					if (rawClose != null) {

						rawCloseChild.setApproved(rawClose.getApproved());
					}
				} catch (Exception e) {
				}
			}

			// get item id list
			Iterator<RawCloseChild> iterator4 = rawCloseChildList.iterator();
			List<Integer> itemIdLst = new ArrayList<Integer>();
			while (iterator4.hasNext()) {
				RawCloseChild rawCloseChild = (RawCloseChild) iterator4.next();
				itemIdLst.add(rawCloseChild.getItemId());

			}

			TypedQuery<InventoryItems> qry4 = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.storeId=:storeid and i.id IN (:itemIdLst) and i.deleteFlag='N'",
					    InventoryItems.class);
			qry4.setParameter("storeid", storeid);
			qry4.setParameter("itemIdLst", itemIdLst);
			itemList = qry4.getResultList();

			Iterator<InventoryItems> itr = itemList.iterator();
			while (itr.hasNext()) {
				InventoryItems item = itr.next();
				int itemId = item.getId();

				Iterator<RawCloseChild> iter1 = rawCloseChildList.iterator();
				while (iter1.hasNext()) {
					RawCloseChild rawCloseChild = (RawCloseChild) iter1.next();
					int itmId = rawCloseChild.getItemId();
					if (itmId == itemId) {
						rawCloseChild.setInventoryItems(item);
						break;
					}
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		return rawCloseChildList;
	}

	@Override
	public List<FmcgUnit> getAllFmcgUnits(Integer storeId) throws DAOException {

		List<FmcgUnit> fmcgUnits = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<FmcgUnit> qry = em
					.createQuery("SELECT c FROM FmcgUnit c WHERE c.deleteFlag='N'",
					    FmcgUnit.class);
			fmcgUnits = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return fmcgUnits;
	}

	@Override
	public List<Ingredient> getAllIngredients(Integer storeId, Integer itemId)
			throws DAOException {

		List<Ingredient> ingrediets = null;
		int storeid = (storeId);
		int itemid = (itemId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<Ingredient> qry = em
					.createQuery("SELECT i FROM Ingredient i WHERE i.storeId=:storeid and i.item.id=:itemid and i.deleteFlag='N'",
					    Ingredient.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("itemid", itemid);
			ingrediets = qry.getResultList();

			/*
			 * Iterator<Ingredient> iterator = ingrediets.iterator(); while
			 * (iterator.hasNext()) { Ingredient ingredient = (Ingredient)
			 * iterator.next(); ingredient.setItem(null); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return ingrediets;
	}

	@Override
	public List<Ingredient> getIngredientsForEdp(Integer storeId, Integer edpId)
			throws DAOException {

		List<IngrdientForAnEdp> ingredietForAnEdpList = new ArrayList<IngrdientForAnEdp>();
		List<Ingredient> ingredietForAnEdpFinalList = new ArrayList<Ingredient>();
		int storeid = (storeId);
		int edpid = (edpId);
		List<Integer> itemIdList = new ArrayList<Integer>();
		List<Integer> invTypeItemIdList = new ArrayList<Integer>();
		List<Ingredient> ingredientsList = new ArrayList<>();
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		InventoryDAOImpl daoImpl = new InventoryDAOImpl();
		List<InventoryPurchaseOrderItem> purchaseOrderItems = new ArrayList<InventoryPurchaseOrderItem>();

		EntityManager em = null;
		try {
			StoreMaster store = addressDAO.getStoreByStoreId(storeid);
			int period = store.getStockPeriod();
			List<EstimateDailyProdItem> dailyProdItems = getEstimateDailyProdItemById(
					storeId, edpId);

			Iterator<EstimateDailyProdItem> iterator = dailyProdItems.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProdItem estimateDailyProdItem = iterator.next();
				int itemId = estimateDailyProdItem.getMenuItem().getId();
				itemIdList.add(itemId);
			}
			
			
			em = entityManagerFactory.createEntityManager();

			if(!itemIdList.isEmpty()) {
  			TypedQuery<Ingredient> qry = em
  					.createQuery("SELECT i FROM Ingredient i WHERE i.storeId=:storeid AND i.item.id IN (:itemIdList) and i.deleteFlag='N'",
  					    Ingredient.class);
  			qry.setParameter("storeid", storeid);
        qry.setParameter("itemIdList", itemIdList);
  			ingredientsList = qry.getResultList();
			}

			Iterator<EstimateDailyProdItem> iterator1 = dailyProdItems
					.iterator();
			while (iterator1.hasNext()) {
				EstimateDailyProdItem estimateDailyProdItem = (EstimateDailyProdItem) iterator1
						.next();
				int itemId = estimateDailyProdItem.getMenuItem().getId();
				double requiredQuantity = Double
						.parseDouble(estimateDailyProdItem
								.getRequiredQuantity());

				Iterator<Ingredient> iterator2 = ingredientsList.iterator();
				while (iterator2.hasNext()) {
					Ingredient ingredient = (Ingredient) iterator2.next();
					int ingItemId = ingredient.getItem().getId();

					if (itemId == ingItemId) {
						double metricAmount = ingredient.getMetricAmount();
						double reqQuantityForThisIngrdient = metricAmount
								* requiredQuantity;
						int invTypeItemId = ingredient.getInventoryItem()
								.getId();
						invTypeItemIdList.add(invTypeItemId);
						// System.out.println("raw id:: "+ingredient.getInventoryItem().getId()+"raw namw:: "+ingredient.getInventoryItem().getName()+"raw unit id**:: "+ingredient.getInventoryItem().getMetricUnitId()+"  reqQuantity:: "+reqQuantityForThisIngrdient+"---recipe unit:: "+ingredient.getMetricUnit().getUnit()+" ---id:: "+ingredient.getMetricUnit().getId());
						// check for metric unit id
						try {
							if (ingredient.getInventoryItem().getMetricUnitId() != ingredient
									.getMetricUnit().getId()) {

								// convert to metric unit in ingredient
								TypedQuery<UnitConversion> qry1 = em
										.createQuery("SELECT c FROM UnitConversion c WHERE c.storeId=:storeid and c.metricUnit1.id=:frmUnitId and c.metricUnit.id=:toUnitId and c.deleteFlag='N'",
										    UnitConversion.class);
								qry1.setParameter("storeid", storeid);
								qry1.setParameter("frmUnitId", ingredient
										.getMetricUnit().getId());
								qry1.setParameter("toUnitId", ingredient
										.getInventoryItem().getMetricUnitId());

								UnitConversion conversion = qry1.getSingleResult();
								if (conversion != null) {
									double factor = conversion
											.getToUnitAmount();
									reqQuantityForThisIngrdient = reqQuantityForThisIngrdient
											* factor;
									MetricUnit metricUnit = getMetricUnit(ingredient
											.getInventoryItem()
											.getMetricUnitId()
											);

									ingredient.setMetricUnit(metricUnit);
									// set stock out quantity to 0
									ingredient
											.setCurrentStockOutQuantityEdpWise(0.0);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							// get current quantity
							String currentStock = daoImpl
									.getCurrentStockByItemByPeriod(storeId, ingredient.getInventoryItem()
													.getId(), period);
							ingredient.setCurrentQuantity(Double
									.parseDouble(currentStock));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							// get current stock in quantity by EDP
							Double currentStockInEdpWise = daoImpl
									.getCurrentStockByItemByPeriodByEdp(
											storeId, ingredient
															.getInventoryItem()
															.getId(), period,
											edpid);
							ingredient.setCurrentStockInQuantityEdpWise(currentStockInEdpWise);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							// get current stock out quantity by EDP
							String currentStockOutEdpWise = daoImpl
									.getCurrentStockOutByItemByPeriodByEdp(
											storeId, ingredient
															.getInventoryItem()
															.getId(), period,
											edpid);
							ingredient.setCurrentStockOutQuantityEdpWise(Double
									.parseDouble(currentStockOutEdpWise));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// create a new IngrdientForAnEdp object
						IngrdientForAnEdp ingrdientForAnEdp = new IngrdientForAnEdp();
						ingrdientForAnEdp.setIngredient(ingredient);
						ingrdientForAnEdp.setInvTypeItemId(invTypeItemId);
						ingrdientForAnEdp
								.setReqQuantity(reqQuantityForThisIngrdient);

						// add to list
						ingredietForAnEdpList.add(ingrdientForAnEdp);

					}

				}

			}
			System.out.println("list size:: " + ingredietForAnEdpList.size());
			// check for common ingredients in the list and add them
			Set<Integer> setOfInvTypeItemId = new HashSet<>(invTypeItemIdList);
			Iterator<Integer> iterator3 = setOfInvTypeItemId.iterator();
			while (iterator3.hasNext()) {
				Integer invTypeItmId = iterator3.next();
				Ingredient ingrdientForAnEdpFinal = new Ingredient();
				double edpQuantity = 0.0;
				Iterator<IngrdientForAnEdp> iterator4 = ingredietForAnEdpList
						.iterator();
				while (iterator4.hasNext()) {
					IngrdientForAnEdp ingrdientForAnEdp = (IngrdientForAnEdp) iterator4
							.next();
					int invTypeItemId = ingrdientForAnEdp.getInvTypeItemId();
					Ingredient ingredient = ingrdientForAnEdp.getIngredient();

					if (invTypeItmId == invTypeItemId) {
						edpQuantity = edpQuantity
								+ ingrdientForAnEdp.getReqQuantity();
						ingrdientForAnEdpFinal = ingredient;
						ingrdientForAnEdpFinal.setEdpQuantity(edpQuantity);

					}

				}

				ingredietForAnEdpFinalList.add(ingrdientForAnEdpFinal);

			}
			Iterator<Ingredient> iterator2 = ingredietForAnEdpFinalList
					.iterator();
			while (iterator2.hasNext()) {
				Ingredient ingredient = (Ingredient) iterator2.next();
				ingredient.setItem(null);

			}
			System.out.println("list size edp ingredient:: "
					+ ingredietForAnEdpFinalList.size());

			// get po items for the created edp
			try {
				TypedQuery<InventoryPurchaseOrderItem> qrypo = em
						.createQuery("SELECT p FROM InventoryPurchaseOrderItem p WHERE p.storeId=:storeid and p.estimateDailyProdId=:estimateDailyProdId and p.deleteFlag='N'",
						    InventoryPurchaseOrderItem.class);
				qrypo.setParameter("storeid", storeid);
				qrypo.setParameter("estimateDailyProdId", edpid);
				purchaseOrderItems = qrypo.getResultList();

				Iterator<Ingredient> iterator5 = ingredietForAnEdpFinalList.iterator();

				while (iterator5.hasNext()) {
					Ingredient ingrdient = iterator5.next();
					int itemId = ingrdient.getInventoryItem().getId();

					Iterator<InventoryPurchaseOrderItem> iterator4 = purchaseOrderItems.iterator();
					while (iterator4.hasNext()) {
						InventoryPurchaseOrderItem poItem = iterator4.next();
						int itmId = poItem.getInventoryItems().getId();
						if (itmId == itemId) {
							ingrdient.setPoId(poItem.getInventoryPurchaseOrder().getId());
							ingrdient.setVendorId(poItem.getVendorId());
							break;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return ingredietForAnEdpFinalList;
	}

	@Override
	public List<EstimateDailyProd> getEstimateDailyProdByDate(Integer storeId,
			String date) throws DAOException {

		List<EstimateDailyProd> dailyEstimates = null;
		int storeid = (storeId);
		// int itemid = Integer.parseInt(date);
		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProd> qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date and p.deleteFlag='N'",
					    EstimateDailyProd.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			dailyEstimates = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

	public List<EstimateDailyProd> getEstimateDailyProdByDateNEstimateType(
			Integer storeId, String date, int estimateType) throws DAOException {

		List<EstimateDailyProd> dailyEstimates = null;
		int storeid = (storeId);
		// int itemid = Integer.parseInt(date);
		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProd> qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date and p.estimateType.id=:estimateType and p.deleteFlag='N'",
					    EstimateDailyProd.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			qry.setParameter("estimateType", estimateType);
			dailyEstimates = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

	public EstimateDailyProd getEstimateDailyProdById(int storeId, int id)
			throws DAOException {

		EstimateDailyProd dailyEstimates = null;
		// int storeid = Integer.parseInt(storeId);
		// int itemid = Integer.parseInt(date);
		EntityManager em = null;
		try {

			
			em = entityManagerFactory.createEntityManager();

			Query qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.id=:id and p.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("id", id);
			dailyEstimates = (EstimateDailyProd) qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

	@Override
	public List<EstimateDailyProd> getRequisitionByDate(Integer storeId,
			String date) throws DAOException {

		List<EstimateDailyProd> dailyEstimates = null;
		int storeid = (storeId);
		List<Integer> edpIdList = new ArrayList<Integer>();
		List<InventoryPurchaseOrder> poList = null;

		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProd> qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date",
					    EstimateDailyProd.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			dailyEstimates = qry.getResultList();

			Iterator<EstimateDailyProd> iterator = dailyEstimates.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProd estimateDailyProd = iterator.next();
				String reqPoStatus = estimateDailyProd.getRequisitionPoStatus();
				if ("Y".equalsIgnoreCase(reqPoStatus)) {
					edpIdList.add(estimateDailyProd.getId());
				}
			}
			if (edpIdList != null && edpIdList.size() > 0) {
				TypedQuery<InventoryPurchaseOrder> qry1 = em
						.createQuery("SELECT p FROM InventoryPurchaseOrder p WHERE p.storeId=:storeId  and p.deleteFlag='N' and p.estimateDailyProdId IN (:edpIdList)",
						    InventoryPurchaseOrder.class);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("edpIdList", edpIdList);

				poList = qry1.getResultList();

				Iterator<InventoryPurchaseOrder> iterator2 = poList.iterator();
				while (iterator2.hasNext()) {
					InventoryPurchaseOrder inventoryPurchaseOrder = iterator2.next();
					int edpId = inventoryPurchaseOrder.getEstimateDailyProdId();

					Iterator<EstimateDailyProd> iterator3 = dailyEstimates.iterator();
					while (iterator3.hasNext()) {
						EstimateDailyProd estimateDailyProd = iterator3.next();
						int edpId1 = estimateDailyProd.getId();

						if (edpId == edpId1) {
							String approved = inventoryPurchaseOrder.getApproved();
							estimateDailyProd.setRequisitionPoStatus(approved);
							estimateDailyProd.setPoId(inventoryPurchaseOrder.getId());
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

	@Override
	public List<EstimateDailyProd> getFgStockInByDate(Integer storeId,
			String date) throws DAOException {

		List<EstimateDailyProd> dailyEstimates = null;
		int storeid = (storeId);
		List<Integer> edpIdList = new ArrayList<Integer>();
		List<FgStockIn> fgStockInList = null;

		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProd> qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date and p.approved='Y'",
					    EstimateDailyProd.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			dailyEstimates = qry.getResultList();

			Iterator<EstimateDailyProd> iteraeEdp = dailyEstimates.iterator();
			while (iteraeEdp.hasNext()) {
				double totalEstimatedQty = 0.0;
				EstimateDailyProd estimateDailyProd = iteraeEdp.next();
				List<EstimateDailyProdItem> listEdpItems = estimateDailyProd
						.getEstimateDailyProdItems();
				Iterator<EstimateDailyProdItem> iteratorEdpItems = listEdpItems.iterator();
				while (iteratorEdpItems.hasNext()) {
					EstimateDailyProdItem estimateDailyProdItem = iteratorEdpItems.next();
					double estimatedQuantity = estimateDailyProdItem.getEdProdAmount();
					totalEstimatedQty = totalEstimatedQty + estimatedQuantity;
				}
				estimateDailyProd.setTotalEstimatedQty(totalEstimatedQty);
				estimateDailyProd.setTotalStockInQty(0.0);
			}

			Iterator<EstimateDailyProd> iterator = dailyEstimates.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProd estimateDailyProd = (EstimateDailyProd) iterator
						.next();
				// String fgStockInStatus =
				// estimateDailyProd.getFgStockInStatus();
				Date fgStockInDate = estimateDailyProd.getFgStockInDate();
				if (fgStockInDate != null) { // fg stockin done for that edp

					edpIdList.add(estimateDailyProd.getId());
				}

			}
			if (edpIdList != null && edpIdList.size() > 0) {
				TypedQuery<FgStockIn> qry1 = em
						.createQuery("SELECT p FROM FgStockIn p WHERE p.storeId=:storeId  and p.deleteFlag='N' and p.edpId IN (:edpIdList)",
						    FgStockIn.class);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("edpIdList", edpIdList);

				fgStockInList = qry1.getResultList();

				Iterator<FgStockIn> iterator2 = fgStockInList.iterator();
				while (iterator2.hasNext()) {
					FgStockIn fgStockIn = iterator2.next();
					int edpId = fgStockIn.getEdpId();

					Iterator<EstimateDailyProd> iterator3 = dailyEstimates.iterator();
					while (iterator3.hasNext()) {
						EstimateDailyProd estimateDailyProd = iterator3.next();
						int edpId1 = estimateDailyProd.getId();

						if (edpId == edpId1) {
							double totalEstQnty = 0.0;
							double totalStockInQnty = 0.0;
							String approved = fgStockIn.getApproved();
							estimateDailyProd.setFgStockInStatus(approved);
							estimateDailyProd.setFgStockInId(fgStockIn.getId());
							
							List<FgStockInItemsChild> listItems = fgStockIn.getFgStockInItemsChilds();
							Iterator<FgStockInItemsChild> iterator4 = listItems.iterator();
							while (iterator4.hasNext()) {
								FgStockInItemsChild fgStockInItemsChild = iterator4.next();
								
								double estimatedQuantity = fgStockInItemsChild.getEdProdAmount();
								double stockInQuantity = fgStockInItemsChild.getStockInQuantity();
								totalEstQnty = totalEstQnty + estimatedQuantity;
								totalStockInQnty = totalStockInQnty	+ stockInQuantity;
							}
							estimateDailyProd.setTotalEstimatedQty(totalEstQnty);
							estimateDailyProd.setTotalStockInQty(totalStockInQnty);
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

	/*
	 * @Override public List<EstimateDailyProd> getRawStockOutByDate(String
	 * storeId, String date) throws DAOException {
	 * 
	 * List<EstimateDailyProd> dailyEstimates = null; int storeid =
	 * Integer.parseInt(storeId); List<Integer> edpIdList = new
	 * ArrayList<Integer>(); List<InventoryStockOut> rawStockOutList = null;
	 * 
	 * EntityManager em = null; try { DateFormat dateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd"); Date finaldate = dateFormat.parse(date);
	 *  em =
	 * entityManagerFactory.createEntityManager();
	 * 
	 * Query qry = em .createQuery(
	 * "SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date and p.approved='Y'"
	 * ); qry.setParameter("storeid", storeid); qry.setParameter("date",
	 * finaldate); dailyEstimates = (List<EstimateDailyProd>)
	 * qry.getResultList();
	 * 
	 * Iterator<EstimateDailyProd> iteraeEdp = dailyEstimates.iterator(); while
	 * (iteraeEdp.hasNext()) { double totalEstimatedQty = 0.0; EstimateDailyProd
	 * estimateDailyProd = (EstimateDailyProd) iteraeEdp .next();
	 * List<EstimateDailyProdItem> listEdpItems = estimateDailyProd
	 * .getEstimateDailyProdItems(); Iterator<EstimateDailyProdItem>
	 * iteratorEdpItems = listEdpItems .iterator(); while
	 * (iteratorEdpItems.hasNext()) { EstimateDailyProdItem
	 * estimateDailyProdItem = (EstimateDailyProdItem) iteratorEdpItems .next();
	 * double estimatedQuantity = estimateDailyProdItem .getEdProdAmount();
	 * totalEstimatedQty = totalEstimatedQty + estimatedQuantity;
	 * 
	 * } estimateDailyProd.setTotalEstimatedQty(totalEstimatedQty);
	 * estimateDailyProd.setTotalStockInQty(0.0);
	 * 
	 * }
	 * 
	 * Iterator<EstimateDailyProd> iterator = dailyEstimates.iterator(); while
	 * (iterator.hasNext()) { EstimateDailyProd estimateDailyProd =
	 * (EstimateDailyProd) iterator .next(); // String fgStockInStatus = //
	 * estimateDailyProd.getFgStockInStatus(); Date rawStockOutDate =
	 * estimateDailyProd.getRawStockOutDate(); if (rawStockOutDate != null) { //
	 * raw stock out done for that edp
	 * 
	 * edpIdList.add(estimateDailyProd.getId()); }
	 * 
	 * } if (edpIdList != null && edpIdList.size() > 0) { Query qry1 = em
	 * .createQuery(
	 * "SELECT r FROM InventoryStockOut r WHERE r.storeId=:storeId  and r.deleteFlag='N' and r.edpId IN (:edpIdList)"
	 * ); qry1.setParameter("storeId", storeid); qry1.setParameter("edpIdList",
	 * edpIdList);
	 * 
	 * rawStockOutList = (List<InventoryStockOut>) qry1.getResultList();
	 * 
	 * Iterator<InventoryStockOut> iterator2 = rawStockOutList.iterator(); while
	 * (iterator2.hasNext()) { InventoryStockOut rawStockOut =
	 * (InventoryStockOut) iterator2.next(); int edpId = rawStockOut.getEdpId();
	 * 
	 * Iterator<EstimateDailyProd> iterator3 = dailyEstimates .iterator(); while
	 * (iterator3.hasNext()) { EstimateDailyProd estimateDailyProd =
	 * (EstimateDailyProd) iterator3 .next(); int edpId1 =
	 * estimateDailyProd.getId();
	 * 
	 * if (edpId == edpId1) { double totalEstQnty = 0.0; double
	 * totalStockOutQnty = 0.0; String approved = rawStockOut.getApproved();
	 * estimateDailyProd.setRawStockOutStatus(approved);
	 * estimateDailyProd.setRawStockOutId(rawStockOut.getId());
	 * List<InventoryStockOutItem> listItems =
	 * rawStockOut.getInventoryStockOutItems(); Iterator<InventoryStockOutItem>
	 * iterator4 = listItems .iterator(); while (iterator4.hasNext()) {
	 * InventoryStockOutItem rawStockOutItemsChild = (InventoryStockOutItem)
	 * iterator4 .next(); double estimatedQuantity = rawStockOutItemsChild
	 * .getEdProdAmount(); double stockInQuantity = rawStockOutItemsChild
	 * .getStockInQuantity(); totalEstQnty = totalEstQnty + estimatedQuantity;
	 * totalStockInQnty = totalStockInQnty + stockInQuantity;
	 * 
	 * } estimateDailyProd .setTotalEstimatedQty(totalEstQnty);
	 * estimateDailyProd .setTotalStockInQty(totalStockOutQnty); break;
	 * 
	 * } } } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); throw new
	 * DAOException("Check data to be inserted", e); } finally { em.close(); }
	 * return dailyEstimates; }
	 */

	@Override
	public List<EstimateDailyProdItem> getEstimateDailyProdItemById(Integer storeId, Integer id) throws DAOException {

		List<EstimateDailyProdItem> dailyEstimatesItems = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProdItem> qry = em
					.createQuery("SELECT p FROM EstimateDailyProdItem p WHERE p.storeId=:storeid and p.estimateDailyProdId.id=:estimateDailyProdId and p.deleteFlag='N'",
					    EstimateDailyProdItem.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("estimateDailyProdId", id);
			dailyEstimatesItems = qry.getResultList();
			Iterator<EstimateDailyProdItem> iterator1 = dailyEstimatesItems.iterator();
			while (iterator1.hasNext()) {
				EstimateDailyProdItem estimateDailyProdItem = iterator1.next();
				int itemId = estimateDailyProdItem.getMenuItem().getId();
				String[] names = CommonProerties.getCatSubcatNames(itemId, storeId);

				estimateDailyProdItem.getMenuItem().setSubCategoryName(names[1]);
				estimateDailyProdItem.getMenuItem().setCategoryName(names[0]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return dailyEstimatesItems;
	}

	@Override
	public List<FgStockInItemsChild> getFgStockInItemsById(Integer storeId,
			Integer fgStockInId) throws DAOException {

		List<FgStockInItemsChild> fgStockInItems = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<FgStockInItemsChild> qry = em
					.createQuery("SELECT p FROM FgStockInItemsChild p WHERE p.storeId=:storeid and p.fgStockInId.id=:fgStockInId",
					    FgStockInItemsChild.class);
			
			qry.setParameter("storeid", storeId);
			qry.setParameter("fgStockInId", fgStockInId);
			fgStockInItems = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return fgStockInItems;
	}

	@Override
	public List<InventoryPurchaseOrderItem> getRequisitionByIds(Integer storeId,
			Integer poId) throws DAOException {

		List<InventoryPurchaseOrderItem> poItems = null;
		int storeid = (storeId);
		int poId1 = (poId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryPurchaseOrderItem> qry = em
					.createQuery("SELECT p FROM InventoryPurchaseOrderItem p WHERE p.storeId=:storeid and p.inventoryPurchaseOrder.id=:poId1 and p.deleteFlag='N'",
					    InventoryPurchaseOrderItem.class);
			
			qry.setParameter("storeid", storeid);
			qry.setParameter("poId1", poId1);
			poItems = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		
		return poItems;
	}
	
	//added on 07.05.2018
	@Override
	public List<InventoryPurchaseOrder> getRequisitionByIdsNew(Integer storeId,
			Integer poId) throws DAOException {

		List<InventoryPurchaseOrder> inventoryPoOrdrs = null;
		int storeid = (storeId);
		int poid = (poId);
		List<MetricUnit> metricUnits = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryPurchaseOrder> qry = em
					.createQuery("SELECT p FROM InventoryPurchaseOrder p WHERE p.storeId=:storeid and p.id=:id  and p.deleteFlag='N'",
					    InventoryPurchaseOrder.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", poid);
			inventoryPoOrdrs = qry.getResultList();
			
			TypedQuery<MetricUnit> qry1 = em.createQuery("SELECT u FROM MetricUnit u WHERE u.deleteFlag='N'",
			    MetricUnit.class);
			metricUnits = qry1.getResultList();
			Iterator<InventoryPurchaseOrder> iterator = inventoryPoOrdrs.iterator();
			while (iterator.hasNext()) {
				InventoryPurchaseOrder inventoryPurchaseOrder = iterator.next();
				//added 19.04.2018
				int vendorId = inventoryPurchaseOrder.getVendorId();
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",
						    InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vendrName = vendor.getName();
				inventoryPurchaseOrder.setVendorName(vendrName);
				List<InventoryPurchaseOrderItem> lstItems = inventoryPurchaseOrder
						.getInventoryPurchaseOrderItems();
				Iterator<InventoryPurchaseOrderItem> iterator2 = lstItems.iterator();
				while (iterator2.hasNext()) {
					InventoryPurchaseOrderItem inventoryPurchaseOrderItem = iterator2.next();
					inventoryPurchaseOrderItem.setVendorName(vendrName);
					InventoryItems inventoryItems = inventoryPurchaseOrderItem
							.getInventoryItems();
					inventoryItems.setVendorName(vendrName);
					
					//set metric unit in inventoryStockInItem
					int unitId=inventoryPurchaseOrderItem.getUnitId();
					Iterator<MetricUnit> iterator3=metricUnits.iterator();
					while (iterator3.hasNext()) {
						MetricUnit metricUnit = (MetricUnit) iterator3
								.next();
						int unitid=metricUnit.getId();
						if(unitId==unitid){
							inventoryPurchaseOrderItem.setMetricUnit(metricUnit);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return inventoryPoOrdrs;
	}

	@Override
	public List<InventoryStockOutItem> getRawStockOutById(Integer storeId,
			Integer rawStockOutId) throws DAOException {

		List<InventoryStockOutItem> rawStckOutItems = null;
		InventoryStockOut inventoryStockOut = null;
		int storeid = (storeId);
		int rawStockOutId1 = (rawStockOutId);
		EntityManager em = null;
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		InventoryDAOImpl inventoryDAO = new InventoryDAOImpl();

		try {
			StoreMaster store = addressDAO.getStoreByStoreId(storeid);
			int period = store.getStockPeriod();
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryStockOutItem> qry = em
					.createQuery("SELECT p FROM InventoryStockOutItem p WHERE p.storeId=:storeid and p.inventoryStockOut.id=:rawStockOutId and p.deleteFlag=:deleteflag",
					    InventoryStockOutItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("rawStockOutId", rawStockOutId1);
			qry.setParameter("deleteflag", "N");
			rawStckOutItems = qry.getResultList();

			TypedQuery<InventoryStockOut> qry1 = em
					.createQuery("SELECT p FROM InventoryStockOut p WHERE p.id=:id and p.storeId=:storeid",
					    InventoryStockOut.class);
			qry1.setParameter("storeid", storeid);
			qry1.setParameter("id", rawStockOutId1);
			inventoryStockOut = qry1.getSingleResult();

			int edpId = inventoryStockOut.getEdpId();

			// set current stock out quantity by edp for each item
			Iterator<InventoryStockOutItem> iterator = rawStckOutItems.iterator();
			while (iterator.hasNext()) {
				InventoryStockOutItem inventoryStockOutItem = iterator.next();
				// get current stock out quantity by EDP
				String currentStockOutEdpWise = inventoryDAO
						.getCurrentStockOutByItemByPeriodByEdp(storeId, inventoryStockOutItem.getInventoryItems()
										.getId(), period, edpId);
				inventoryStockOutItem.setCurrentStockOutQuantityEdpWise(Double
						.parseDouble(currentStockOutEdpWise));
			}
			
			try {
				List<Ingredient> ingredients = getIngredientsForEdp(storeId, edpId);
				Iterator<InventoryStockOutItem> iterator2 = rawStckOutItems.iterator();
				while (iterator2.hasNext()) {
					InventoryStockOutItem inventoryStockOutItem = iterator2.next();
					int invItemId = inventoryStockOutItem.getInventoryItems().getId();
					Iterator<Ingredient> iterator3 = ingredients.iterator();
					while (iterator3.hasNext()) {
						Ingredient ingredient = iterator3.next();
						int invItmId = ingredient.getInventoryItem().getId();
						double edpQnty = ingredient.getEdpQuantity();
						
						if (invItemId == invItmId) {
							inventoryStockOutItem.setEdpQuantity(edpQnty);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// get current stock for each item
			Iterator<InventoryStockOutItem> iterator1 = rawStckOutItems.iterator();
			while (iterator1.hasNext()) {
				InventoryStockOutItem stockOutItem = iterator1.next();
				int itemId = stockOutItem.getInventoryItems().getId();

				String currentStock = inventoryDAO.getCurrentStockByItemByPeriod(storeId, itemId,	period);
				stockOutItem.setCurrentStock(Double.parseDouble(currentStock));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return rawStckOutItems;
	}

	@Override
	public List<MetricUnit> getAllMetricUnits(String type) throws DAOException {

		List<MetricUnit> metricUnits = null;
		EntityManager em = null;
		String unitType = type;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<MetricUnit> qry = em
					.createQuery("SELECT m FROM MetricUnit m WHERE m.deleteFlag='N' and m.unitType=:unitType",
					    MetricUnit.class);
			qry.setParameter("unitType", unitType);

			metricUnits = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return metricUnits;
	}

	@Override
	public List<MetricUnit> getAllMetricUnitsbyType(String type) throws DAOException {

		List<MetricUnit> metricUnits = null;
		EntityManager em = null;
		String unitType = type;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<MetricUnit> qry = em
					.createQuery("SELECT m FROM MetricUnit m WHERE m.deleteFlag='N' and m.unitType=:unitType",
					    MetricUnit.class);
			qry.setParameter("unitType", unitType);

			metricUnits = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return metricUnits;
	}
	@Override
	public List<InventoryVendor> getVendorByEdp(String edpId, Integer storeId)
			throws DAOException {

		List<Integer> idList = null;
		Set<Integer> idSet = new HashSet<Integer>();
		List<InventoryPurchaseOrderItem> poItems = null;
		List<InventoryVendor> vendorList = new ArrayList<InventoryVendor>();
		EntityManager em = null;
		String delFlag = "N";

		try {
			int edpid = Integer.parseInt(edpId);
			int storeid = (storeId);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryPurchaseOrderItem> qry = em
					.createQuery("SELECT m FROM InventoryPurchaseOrderItem m WHERE m.deleteFlag='N' and m.estimateDailyProdId=:estimateDailyProdId and m.storeId=:storeId",
					    InventoryPurchaseOrderItem.class);
			qry.setParameter("estimateDailyProdId", edpid);
			qry.setParameter("storeId", storeid);

			poItems = qry.getResultList();

			Iterator<InventoryPurchaseOrderItem> iterator = poItems.iterator();
			while (iterator.hasNext()) {
				InventoryPurchaseOrderItem poItem =iterator.next();
				int vendorId = poItem.getVendorId();
				idSet.add(vendorId);
			}
			idList = new ArrayList<Integer>(idSet);

			if (idList.size() > 0) {
				TypedQuery<InventoryVendor> qry1 = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.storeId=:storeid and v.id IN (:idList) and v.deleteFlag=:delFlag",
						    InventoryVendor.class);
				qry1.setParameter("storeid", storeid);
				qry1.setParameter("delFlag", delFlag);
				qry1.setParameter("idList", idList);
				vendorList = qry1.getResultList();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return vendorList;
	}

	@Override
	public MetricUnit getMetricUnit(Integer id) throws DAOException {

		MetricUnit metricUnits = null;
		EntityManager em = null;
		int id1 = (id);
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<MetricUnit> qry = em
					.createQuery("SELECT m FROM MetricUnit m WHERE m.deleteFlag='N' and m.id=:id",
					    MetricUnit.class);
			qry.setParameter("id", id1);

			metricUnits = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return metricUnits;
	}

	@Override
	public List<UnitConversion> getAllUnitConversions(Integer storeId)
			throws DAOException {

		List<UnitConversion> unitConversions = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<UnitConversion> qry = em
					.createQuery("SELECT u FROM UnitConversion u WHERE u.storeId=:storeid and u.deleteFlag='N'",
					    UnitConversion.class);
			qry.setParameter("storeid", storeid);
			unitConversions = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return unitConversions;
	}

	@Override
	public int addEstimateDailyProd(EstimateDailyProd estimateDailyProds)
			throws DAOException {
		EntityManager em = null;
		int estimateDailyProdsId = 0;
		List<EstimateDailyProd> dailyEstimates = null;

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			int estimateType = estimateDailyProds.getEstimateType().getId();
			if (estimateType == 1 || estimateType==5) {// daily
				Date edpDate = estimateDailyProds.getDate();
				String date = dateFormat.format(edpDate);
				dailyEstimates = getEstimateDailyProdByDateNEstimateType(estimateDailyProds.getStoreId(), date, estimateType);
				if (dailyEstimates != null && dailyEstimates.size() > 0) {
					// isEdpWithEstimateTypeDaily = true;
					if(estimateType == 1)
					estimateDailyProdsId = 999999999;
					else if(estimateType == 5) {
						estimateDailyProdsId = 999999998;
					}
					throw new Exception();
				} else {
					estimateDailyProdsId = createEdp(estimateDailyProds, em,
							dateFormat);
				}
			} else {
				estimateDailyProdsId = createEdp(estimateDailyProds, em,
						dateFormat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (estimateDailyProdsId == 999999999) {
				throw new DAOException("999999999", e);
			} 
			else if (estimateDailyProdsId == 999999998) {
				throw new DAOException("999999998", e);
			}else {
				throw new DAOException("Check data to be inserted", e);
			}

		} finally {
			if(em != null) em.close();
		}
		return estimateDailyProdsId;
	}

	private int createEdp(EstimateDailyProd estimateDailyProds,
			EntityManager em, DateFormat dateFormat) throws ParseException {
		int estimateDailyProdsId = 0;
		if (estimateDailyProds.getId() == 0) { // new insert
			Date finaldate = dateFormat.parse(estimateDailyProds.getDateText());

			estimateDailyProds.setDeleteFlag("N");
			estimateDailyProds.setDate(finaldate);
			em.persist(estimateDailyProds);
			// persist each item
			List<EstimateDailyProdItem> items = estimateDailyProds
					.getEstimateDailyProdItems();
			Iterator<EstimateDailyProdItem> iterator = items.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProdItem item = (EstimateDailyProdItem) iterator
						.next();

				item.setEstimateDailyProdId(estimateDailyProds);
				item.setDeleteFlag("N");
				em.persist(item);

				// get menu item ed prod amt
				int edProdAmt = item.getEdProdAmount();
				int menuItemId = item.getMenuItem().getId();
				int storeId = item.getStoreId();
				// update item min req daily in fm_m_food_items
				Query query = em
						.createNativeQuery("update fm_m_food_items set daily_min_qty=? where Id=? and store_id=?");
				query.setParameter(1, edProdAmt);
				query.setParameter(2, menuItemId);
				query.setParameter(3, storeId);
				query.executeUpdate();

			}
			em.getTransaction().commit();
			estimateDailyProdsId = estimateDailyProds.getId();
		} else if (estimateDailyProds.getId() > 0) { // update
			// merge/persist each item
			List<EstimateDailyProdItem> items = estimateDailyProds
					.getEstimateDailyProdItems();
			Iterator<EstimateDailyProdItem> iterator = items.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProdItem item = (EstimateDailyProdItem) iterator
						.next();

				if (item.getId() != 0) { // update item
					em.merge(item);
				} else {
					item.setEstimateDailyProdId(estimateDailyProds);
					item.setDeleteFlag("N");
					em.persist(item); // insert new item
				}

				// get menu item ed prod amt
				int edProdAmt = item.getEdProdAmount();
				double menuItemId = item.getMenuItem().getId();
				int storeId = item.getStoreId();
				// update item min req daily in fm_m_food_items
				Query query = em
						.createNativeQuery("update fm_m_food_items set daily_min_qty=? where Id=? and store_id=?");
				query.setParameter(1, edProdAmt);
				query.setParameter(2, menuItemId);
				query.setParameter(3, storeId);
				query.executeUpdate();

			}
			em.getTransaction().commit();
			estimateDailyProdsId = estimateDailyProds.getId();

		}
		return estimateDailyProdsId;
	}

	@Override
	public int createFgSaleOut(FgSaleOut fgSaleOut) throws DAOException {
		EntityManager em = null;
		int estimateDailyProdsId = 0;

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(fgSaleOut.getDateText());

			fgSaleOut.setDeleteFlag("N");
			fgSaleOut.setDate(date);
			em.persist(fgSaleOut);
			// persist each item
			List<FgSaleOutItemsChild> items = fgSaleOut
					.getFgSaleOutItemsChilds();
			Iterator<FgSaleOutItemsChild> iterator = items.iterator();
			while (iterator.hasNext()) {
				FgSaleOutItemsChild item = (FgSaleOutItemsChild) iterator
						.next();

				item.setFgSaleOutId(fgSaleOut.getId());
				item.setDeleteFlag("N");
				em.persist(item);

			}
			em.getTransaction().commit();
			estimateDailyProdsId = fgSaleOut.getId();
			/*
			 * else if (estimateDailyProds.getId() > 0) { // update //
			 * merge/persist each item List<EstimateDailyProdItem> items =
			 * estimateDailyProds .getEstimateDailyProdItems();
			 * Iterator<EstimateDailyProdItem> iterator = items.iterator();
			 * while (iterator.hasNext()) { EstimateDailyProdItem item =
			 * (EstimateDailyProdItem) iterator .next();
			 * 
			 * if (item.getId() != 0) { // update item em.merge(item); } else {
			 * item.setEstimateDailyProdId(estimateDailyProds.getId());
			 * item.setDeleteFlag("N"); em.persist(item); // insert new item }
			 * 
			 * } em.getTransaction().commit(); estimateDailyProdsId =
			 * estimateDailyProds.getId();
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return estimateDailyProdsId;
	}

	@Override
	public int createFgClose(FgClose fgClose) throws DAOException {
		EntityManager em = null;
		int fgCloseId = 0;

		try {
			int storeid = fgClose.getStoreId();

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(fgClose.getDateText());

			// check if fg close already present for that date
			/*
			 * Query qry = em .createQuery(
			 * "SELECT f FROM FgClose f WHERE f.storeId=:storeid and f.date=:date and f.deleteFlag='N'"
			 * ); qry.setParameter("storeid", storeid); qry.setParameter("date",
			 * date); fgCloseList = (List<FgClose>) qry.getResultList();
			 */

			Query qry = em
					.createQuery("DELETE FROM FgCloseChild f where f.storeId=:storeid and f.createdDate=:createdDate");
			qry.setParameter("storeid", storeid);
			qry.setParameter("createdDate", fgClose.getDateText());
			qry.executeUpdate();
			em.getTransaction().commit();

			em.getTransaction().begin();

			Query qry1 = em
					.createQuery("DELETE FROM FgClose f where f.storeId=:storeid and f.date=:date");
			qry1.setParameter("storeid", storeid);
			qry1.setParameter("date", date);
			qry1.executeUpdate();
			em.getTransaction().commit();

			em.getTransaction().begin();

			fgClose.setDeleteFlag("N");
			fgClose.setDate(date);
			em.persist(fgClose);
			// persist each item
			List<FgCloseChild> items = fgClose.getFgCloseChilds();
			Iterator<FgCloseChild> iterator = items.iterator();
			while (iterator.hasNext()) {
				FgCloseChild item = (FgCloseChild) iterator.next();

				item.setFgClseId(fgClose.getId());
				item.setDeleteFlag("N");
				em.persist(item);

			}
			em.getTransaction().commit();
			fgCloseId = fgClose.getId();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return fgCloseId;
	}

	@Override
	public int createRawClose(RawClose rawClose) throws DAOException {
		EntityManager em = null;
		int rawClseId = 0;

		try {
			int storeid = rawClose.getStoreId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(rawClose.getDateText());

			Query qry = em
					.createQuery("DELETE FROM RawCloseChild f where f.storeId=:storeid and f.createdDate=:createdDate");
			qry.setParameter("storeid", storeid);
			qry.setParameter("createdDate", rawClose.getDateText());
			qry.executeUpdate();
			em.getTransaction().commit();

			em.getTransaction().begin();

			Query qry1 = em
					.createQuery("DELETE FROM RawClose f where f.storeId=:storeid and f.date=:date");
			qry1.setParameter("storeid", storeid);
			qry1.setParameter("date", date);
			qry1.executeUpdate();
			em.getTransaction().commit();

			em.getTransaction().begin();

			rawClose.setDeleteFlag("N");
			rawClose.setDate(date);
			em.persist(rawClose);
			// persist each item
			List<RawCloseChild> items = rawClose.getRawCloseChilds();
			Iterator<RawCloseChild> iterator = items.iterator();
			while (iterator.hasNext()) {
				RawCloseChild item = (RawCloseChild) iterator.next();

				item.setRawClseId(rawClose.getId());
				item.setDeleteFlag("N");
				em.persist(item);

			}
			em.getTransaction().commit();
			rawClseId = rawClose.getId();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return rawClseId;
	}

	@Override
	public String addRecipeIngredient(IngredientList ingredients)
			throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			List<Ingredient> ingredientList = ingredients.getIngredients();
			Iterator<Ingredient> iterator = ingredientList.iterator();
			while (iterator.hasNext()) {
				Ingredient ingredient2 = (Ingredient) iterator.next();
				ingredient2.setDeleteFlag("N");
				em.persist(ingredient2);
			}

			em.getTransaction().commit();
			status = "success";
			// recipeId = ingredient.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}

	@Override
	public String updateRecipeIngredient(Ingredient ingredient)
			throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			em.merge(ingredient);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateEstimatedDailyProdItem(
			EstimateDailyProdItem estimateDailyProdItem) throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			em.merge(estimateDailyProdItem);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String addEstimatedDailyProdItem(
			EstimateDailyProdItem estimateDailyProdItem) throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			estimateDailyProdItem.setDeleteFlag("N");
			em.merge(estimateDailyProdItem);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String deleteRecipeIngredient(Integer storeid, Integer id)
			throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int ingId = (id);
		Ingredient ingredient = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM Ingredient i WHERE i.id=:ingId and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("ingId", ingId);
			ingredient = (Ingredient) qry.getSingleResult();
			ingredient.setDeleteFlag("Y");
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String approveEstimateDailyProd(Integer storeid, Integer id, String by)
			throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int ingId = (id);
		EstimateDailyProd estimateDailyProd = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM EstimateDailyProd i WHERE i.id=:ingId and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("ingId", ingId);
			estimateDailyProd = (EstimateDailyProd) qry.getSingleResult();
			estimateDailyProd.setApproved("Y");
			estimateDailyProd.setApprovedBy(by);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String approveRawClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int rawClseId = (id);
		RawClose rawClose = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM RawClose i WHERE i.id=:rawClseId and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("rawClseId", rawClseId);
			rawClose = (RawClose) qry.getSingleResult();
			rawClose.setApproved("Y");
			rawClose.setApprovedBy(approvedBy);
			rawClose.setUpdatedBy(updatedBy);
			rawClose.setUpdatedDate(updatedDate);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String approveFgClose(Integer storeid, Integer id, String approvedBy,
			String updatedBy, String updatedDate) throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int fgClseId = (id);
		FgClose fgClose = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM FgClose i WHERE i.id=:fgClseId and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("fgClseId", fgClseId);
			fgClose = (FgClose) qry.getSingleResult();
			fgClose.setApproved("Y");
			fgClose.setApprovedBy(approvedBy);
			fgClose.setUpdatedBy(updatedBy);
			fgClose.setUpdatedDate(updatedDate);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String deleteEstimateDailyProdItem(Integer storeid, Integer id,
			String edpId) throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int ingId = (id);
		int edpId1 = Integer.parseInt(edpId);
		EstimateDailyProdItem estimateDailyProdItem = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM EstimateDailyProdItem i WHERE i.id=:ingId and i.estimateDailyProdId=:edpId and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("ingId", ingId);
			qry.setParameter("edpId", edpId1);
			estimateDailyProdItem = (EstimateDailyProdItem) qry
					.getSingleResult();
			estimateDailyProdItem.setDeleteFlag("Y");
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String deleteEstimateDailyProd(String edpId, Integer storeid)
			throws DAOException {
		EntityManager em = null;
		int storeId = (storeid);
		int edpId1 = Integer.parseInt(edpId);
		EstimateDailyProd estimateDailyProd = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT i FROM EstimateDailyProd i WHERE i.id=:edpId1 and i.storeId=:storeid");
			qry.setParameter("storeid", storeId);
			qry.setParameter("edpId1", edpId1);
			estimateDailyProd = (EstimateDailyProd) qry.getSingleResult();
			estimateDailyProd.setDeleteFlag("Y");
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}

	@Override
	public List<EstimateDailyProd> getRawStockOutByDate(Integer storeId,
			String date) throws DAOException {

		List<EstimateDailyProd> dailyEstimates = null;
		int storeid = (storeId);
		List<Integer> edpIdList = new ArrayList<Integer>();
		List<InventoryStockOut> rawStockOutList = null;

		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateDailyProd> qry = em
					.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.storeId=:storeid and p.date=:date and p.approved='Y'",
					    EstimateDailyProd.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			dailyEstimates = qry.getResultList();

			Iterator<EstimateDailyProd> iteraeEdp = dailyEstimates.iterator();
			while (iteraeEdp.hasNext()) {
				double totalEstimatedQty = 0.0;
				EstimateDailyProd estimateDailyProd = iteraeEdp.next();
				List<EstimateDailyProdItem> listEdpItems = estimateDailyProd.getEstimateDailyProdItems();
				
				Iterator<EstimateDailyProdItem> iteratorEdpItems = listEdpItems.iterator();
				while (iteratorEdpItems.hasNext()) {
					EstimateDailyProdItem estimateDailyProdItem = iteratorEdpItems.next();
					double estimatedQuantity = estimateDailyProdItem.getEdProdAmount();
					totalEstimatedQty = totalEstimatedQty + estimatedQuantity;
				}
				
				estimateDailyProd.setTotalEstimatedQty(totalEstimatedQty);
				estimateDailyProd.setTotalStockInQty(0.0);
			}

			Iterator<EstimateDailyProd> iterator = dailyEstimates.iterator();
			while (iterator.hasNext()) {
				EstimateDailyProd estimateDailyProd = iterator.next(); // String fgStockInStatus = //
				estimateDailyProd.getFgStockInStatus();
				Date rawStockOutDate = estimateDailyProd.getRawStockOutDate();
				if (rawStockOutDate != null) { // raw stock out done for that edp
					edpIdList.add(estimateDailyProd.getId());
				}
			}
			
			if (edpIdList != null && edpIdList.size() > 0) {
				TypedQuery<InventoryStockOut> qry1 = em
						.createQuery("SELECT r FROM InventoryStockOut r WHERE r.storeId=:storeId  and r.deleteFlag='N' and r.edpId IN (:edpIdList)",
						    InventoryStockOut.class);
				qry1.setParameter("storeId", storeid);
				qry1.setParameter("edpIdList", edpIdList);

				rawStockOutList = qry1.getResultList();

				Iterator<InventoryStockOut> iterator2 = rawStockOutList.iterator();
				while (iterator2.hasNext()) {
					InventoryStockOut rawStockOut = iterator2.next();
					int edpId = rawStockOut.getEdpId();

					Iterator<EstimateDailyProd> iterator3 = dailyEstimates.iterator();
					while (iterator3.hasNext()) {
						EstimateDailyProd estimateDailyProd = iterator3.next();
						int edpId1 = estimateDailyProd.getId();

						if (edpId == edpId1) {
							double totalEstQnty = 0.0;
							double totalStockOutQnty = 0.0;
							String approved = rawStockOut.getApproved();
							estimateDailyProd.setRawStockOutStatus(approved);
							estimateDailyProd.setRawStockOutId(rawStockOut.getId());
							
							/*
							 * List<InventoryStockOutItem> listItems =
							 * rawStockOut .getInventoryStockOutItems();
							 * Iterator<InventoryStockOutItem> iterator4 =
							 * listItems .iterator(); while
							 * (iterator4.hasNext()) { InventoryStockOutItem
							 * rawStockOutItemsChild = (InventoryStockOutItem)
							 * iterator4 .next(); double estimatedQuantity =
							 * rawStockOutItemsChild .getEdProdAmount(); double
							 * stockInQuantity = rawStockOutItemsChild
							 * .getStockInQuantity(); totalEstQnty =
							 * totalEstQnty + estimatedQuantity;
							 * totalStockInQnty = totalStockInQnty +
							 * stockInQuantity;
							 * 
							 * }
							 */
							estimateDailyProd.setTotalEstimatedQty(totalEstQnty);
							estimateDailyProd.setTotalStockInQty(totalStockOutQnty);
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return dailyEstimates;
	}

}
