package com.botree.restaurantapp.dao;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import com.botree.restaurantapp.commonUtil.CommonProerties;
import com.botree.restaurantapp.commonUtil.CommonResultSetMapper;
import com.botree.restaurantapp.commonUtil.DateUtil;
import com.botree.restaurantapp.commonUtil.RefundBillPosPrinterMain;
import com.botree.restaurantapp.commonUtil.ResponseObj;
import com.botree.restaurantapp.commonUtil.ReturnConstant;
import com.botree.restaurantapp.commonUtil.StoredProcedures;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Bill;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.model.FgInvoicePayment;
import com.botree.restaurantapp.model.FgItemCurrentStock;
import com.botree.restaurantapp.model.FgReturn;
import com.botree.restaurantapp.model.FgReturnItem;
import com.botree.restaurantapp.model.InventoryInvoicePayment;
import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.model.InventoryReturn;
import com.botree.restaurantapp.model.InventoryReturnItem;
import com.botree.restaurantapp.model.InventoryStockIn;
import com.botree.restaurantapp.model.InventoryStockInItem;
import com.botree.restaurantapp.model.InventoryStockOut;
import com.botree.restaurantapp.model.InventoryStockOutItem;
import com.botree.restaurantapp.model.InventoryType;
import com.botree.restaurantapp.model.InventoryVendor;
import com.botree.restaurantapp.model.ItemCurrentStock;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.Payment;
import com.botree.restaurantapp.model.ReturnTypes;
import com.botree.restaurantapp.model.SalesReturn;
import com.botree.restaurantapp.model.SalesReturnItem;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.User;
import com.botree.restaurantapp.model.account.AccountDTO;
import com.botree.restaurantapp.model.account.AccountGroupDTO;
import com.botree.restaurantapp.model.account.AccountMaster;
import com.botree.restaurantapp.model.account.AccountTypeDTO;
import com.botree.restaurantapp.model.account.ChartOfAccountDTO;
import com.botree.restaurantapp.model.account.JournalDTO;
import com.botree.restaurantapp.model.account.JournalEntry;
import com.botree.restaurantapp.model.account.JournalListDTO;
import com.botree.restaurantapp.model.account.ParaJournalTypeMaster;
import com.botree.restaurantapp.model.inv.FgStockIn;
import com.botree.restaurantapp.model.inv.FgStockInItemsChild;
import com.botree.restaurantapp.model.rm.EstimateDailyProd;
import com.botree.restaurantapp.model.rm.EstimateType;
import com.botree.restaurantapp.model.rm.Ingredient;
import com.botree.restaurantapp.model.rm.MetricUnit;
import com.botree.restaurantapp.model.rm.RecipeIngredient;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.print.PrintKotMaster;
import com.botree.restaurantapp.webservice.print.PoPosPrinterMain;

import net.sf.resultsetmapper.ReflectionResultSetMapper;

@Component("inventoryDAO")
public class InventoryDAOImpl implements InventoryDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public List<InventoryType> getInventoryType(Integer storeId)
			throws DAOException {

		List<InventoryType> inventoryTypes = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryType> qry = em
					.createQuery("SELECT i FROM InventoryType i WHERE i.storeId=:storeid and i.deleteFlag='N'",
					    InventoryType.class);
			qry.setParameter("storeid", storeid);
			inventoryTypes = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryTypes;
	}

	@Override
	public List<PrintKotMaster> getAllServerPrinters(Integer storeId)
			throws DAOException {

		List<PrintKotMaster> printers = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			// Ask Habib
			TypedQuery<PrintKotMaster> qry = em
					.createQuery("SELECT p FROM PrintKotMaster p WHERE p.storeId=:storeid and p.deleteFlag='N'",
					    PrintKotMaster.class);
			qry.setParameter("storeid", storeid);
			printers = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return printers;
	}

	@Override
	public List<InventoryItems> getInventoryItems(Integer storeId)
			throws DAOException {

		List<InventoryItems> inventoryItems = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryItems> qry = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.storeId=:storeid and i.deleteFlag='N'",
					    InventoryItems.class);
			qry.setParameter("storeid", storeid);
			inventoryItems = qry.getResultList();
			// get the vendor
			Iterator<InventoryItems> iterator = inventoryItems.iterator();
			while (iterator.hasNext()) {
				InventoryItems inventoryItems2 = iterator.next();
				int vendorId = inventoryItems2.getVendorId();
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:id",
						    InventoryVendor.class);
				qryGetVndr.setParameter("id", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vndrName = vendor.getName();
				// set in the inventory item object
				inventoryItems2.setVendorName(vndrName);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return inventoryItems;

	}

	@Override
	public List<InventoryItems> getInventoryItemsByName(Integer storeId,
			String name) throws DAOException {

		List<InventoryItems> inventoryItems = null;
		int storeid = (storeId);
		EntityManager em = null;
		String nameLike = "%" + name + "%";
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryItems> qry = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.storeId=:storeid and i.name like :namelike",
					    InventoryItems.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("namelike", nameLike);
			inventoryItems = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryItems;

	}

	@Override
	public InventoryItems getInventoryItemsByCode(Integer storeId, String code)
			throws DAOException {

		InventoryItems inventoryItems = null;
		int storeid = (storeId);
		EntityManager em = null;
		String code1 = code.trim();
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryItems> qry = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.storeId=:storeid and i.code=:code",
					    InventoryItems.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("code", code1);
			inventoryItems = qry.getSingleResult();

			// get the vendor
			int vendorId = inventoryItems.getVendorId();
			TypedQuery<InventoryVendor> qryGetVndr = em
					.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:id",
					    InventoryVendor.class);
			qryGetVndr.setParameter("id", vendorId);
			InventoryVendor vendor = qryGetVndr.getSingleResult();
			String vndrName = vendor.getName();
			// set in the inventory item object
			inventoryItems.setVendorName(vndrName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryItems;

	}

	@Override
	public List<InventoryVendor> getVendors(Integer storeId) throws DAOException {

		List<InventoryVendor> inventoryVendors = null;
		int storeid = (storeId);
		String delFlag = "N";
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryVendor> qry = em
					.createQuery("SELECT v FROM InventoryVendor v WHERE v.storeId=:storeid and v.deleteFlag=:delFlag",
					    InventoryVendor.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("delFlag", delFlag);
			inventoryVendors = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryVendors;

	}

	@Override
	public List<InventoryPurchaseOrder> getPurchaseOrdersByDate(Integer storeId,
			String date) throws DAOException {

		List<InventoryPurchaseOrder> inventoryPoOrdrs = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			// Date date2 = new Date(date);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			// String formattedDate = dateFormat.format(date2);
			System.out.println("date is:" + finaldate);
			// Date finaldate = dateFormat.parse(formattedDate);

			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryPurchaseOrder> qry = em
					.createQuery("SELECT p FROM InventoryPurchaseOrder p WHERE p.storeId=:storeid and p.date=:date and p.deleteFlag='N'",
					    InventoryPurchaseOrder.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			inventoryPoOrdrs = qry.getResultList();

			Iterator<InventoryPurchaseOrder> iterator = inventoryPoOrdrs
					.iterator();
			while (iterator.hasNext()) {
				InventoryPurchaseOrder inventoryPurchaseOrder = (InventoryPurchaseOrder) iterator
						.next();
				inventoryPurchaseOrder.setInventoryPurchaseOrderItems(null);
				
				// added on 19.04.2018
				int vendorId = inventoryPurchaseOrder.getVendorId();
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",
						    InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				inventoryPurchaseOrder.setVendorName(vendor.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryPoOrdrs;

	}

	@Override
	public List<InventoryPurchaseOrder> getPurchaseOrdersById(Integer storeId,
			Integer id) throws DAOException {

		List<InventoryPurchaseOrder> inventoryPoOrdrs = null;
		int storeid = (storeId);
		int poId = (id);
		List<MetricUnit> metricUnits = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryPurchaseOrder> qry = em
					.createQuery("SELECT p FROM InventoryPurchaseOrder p WHERE p.storeId=:storeid and p.id=:id  and p.deleteFlag='N'",
					    InventoryPurchaseOrder.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", poId);
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
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId", InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vendrName = vendor.getName();
				inventoryPurchaseOrder.setVendorName(vendrName);
				List<InventoryPurchaseOrderItem> lstItems = inventoryPurchaseOrder.getInventoryPurchaseOrderItems();
				Iterator<InventoryPurchaseOrderItem> iterator2 = lstItems.iterator();
				while (iterator2.hasNext()) {
					InventoryPurchaseOrderItem inventoryPurchaseOrderItem = iterator2.next();
					//added 19.04.2018
					/*int vendorId = inventoryPurchaseOrderItem.getVendorId();
					Query qryGetVndr = em
							.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId");
					qryGetVndr.setParameter("vendorId", vendorId);
					InventoryVendor vendor = (InventoryVendor) qryGetVndr
							.getSingleResult();
					String vendrName = vendor.getName();*/
					inventoryPurchaseOrderItem.setVendorName(vendrName);
					InventoryItems inventoryItems = inventoryPurchaseOrderItem.getInventoryItems();
					inventoryItems.setVendorName(vendrName);
					
					//set metric unit in inventoryStockInItem
					int unitId=inventoryPurchaseOrderItem.getUnitId();
					Iterator<MetricUnit> iterator3=metricUnits.iterator();
					while (iterator3.hasNext()) {
						MetricUnit metricUnit = iterator3.next();
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
			if (em != null) em.close();
		}
		
		return inventoryPoOrdrs;
	}

	@Override
	public void addInventoryType(InventoryType inventoryType)
			throws DAOException {
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			// get the store name
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			// get the logged in user
			User loggedUser = (User) context.getExternalContext()
					.getSessionMap().get("loggeduser");
			String userId = loggedUser.getUserId();

			Date curntDate = new Date();

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(curntDate);

			int storeId = 0;
			int restId = 0;
			if (storeMaster != null) {
				restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			inventoryType.setStoreId(storeId);
			inventoryType.setDeleteFlag("N");
			inventoryType.setCreatedBy(userId);
			inventoryType.setCreatedDate(today);
			inventoryType.setUpdatedBy(userId);
			inventoryType.setUpdatedDate(today);
			em.persist(inventoryType);

			em.getTransaction().commit();

		}

		catch (Exception e) {

			System.out.println("in dao: addInventoryType:");
			Throwable cause = e.getCause();

			if (cause instanceof ConstraintViolationException) {
				System.out.println("Integrity constraint");
				throw new DAOException("Integrity Constraint violation", e);
			}
			e.printStackTrace();
			throw new DAOException("Check Inventory data", e);
		}

		finally {
			if (em != null) em.close();
		}

	}

	@Override
	public int createPO(InventoryPurchaseOrder purchaseOrder)
			throws DAOException {
		EntityManager em = null;
		int poId;
		EstimateDailyProd dailyProd = null;
		
		try {

			poId = 0;
			Date currDate = new Date();
			purchaseOrder.setDate(currDate);

			// persist the PO
			purchaseOrder.setDeleteFlag("N");
			purchaseOrder.setPoStatus("N");
			purchaseOrder.setApproved("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(purchaseOrder);

			// persist each PO item
			List<InventoryPurchaseOrderItem> purchaseOrderItems = purchaseOrder
					.getInventoryPurchaseOrderItems();
			Iterator<InventoryPurchaseOrderItem> iterator = purchaseOrderItems
					.iterator();
			while (iterator.hasNext()) {
				InventoryPurchaseOrderItem purchaseOrderItem = (InventoryPurchaseOrderItem) iterator
						.next();
				InventoryPurchaseOrder inventoryPurchaseOrder = new InventoryPurchaseOrder();
				inventoryPurchaseOrder.setId(purchaseOrder.getId());
				purchaseOrderItem
						.setInventoryPurchaseOrder(inventoryPurchaseOrder);
				purchaseOrderItem.setDeleteFlag("N");
				purchaseOrderItem.setEstimateDailyProdId(purchaseOrder
						.getEstimateDailyProdId());
				em.persist(purchaseOrderItem);

				// get item shipping charge and item id
				int itemId = purchaseOrderItem.getInventoryItems().getId();
				double shipngChrge = purchaseOrderItem.getShippingCharge();
				int storeId = purchaseOrderItem.getStoreId();
				// update inventory item rate in im_m_inv_type_items_c
				Query query = em
						.createNativeQuery("update im_m_inv_type_items_c set shipping_charge=? where id=? and store_id=?");
				query.setParameter(1, shipngChrge);
				query.setParameter(2, itemId);
				query.setParameter(3, storeId);
				query.executeUpdate();

			}
			
			poId = purchaseOrder.getId();

			if (purchaseOrder.getEstimateDailyProdId() > 0) {
				
				try {
					// update po date in estimate daily prod
					TypedQuery<EstimateDailyProd> qry = em
							.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.id=:id and p.storeId=:storeid",
							    EstimateDailyProd.class);
					qry.setParameter("storeid", purchaseOrder.getStoreId());
					qry.setParameter("id", purchaseOrder.getEstimateDailyProdId());
					dailyProd = (EstimateDailyProd) qry.getSingleResult();
					Date poDate = purchaseOrder.getDate();
					dailyProd.setRequisitionPoDate(poDate);
					dailyProd.setRequisitionPoStatus("Y");
					dailyProd.setPoId(poId);
					
					em.merge(dailyProd);
				} catch (Exception e) {
					e.printStackTrace();

				}


				/*EstimateDailyProd estimateDailyProd = recipeManagementDAOImpl
						.getEstimateDailyProdById(purchaseOrder.getStoreId(),
								purchaseOrder.getEstimateDailyProdId());
				estimateDailyProd.setRequisitionPoStatus("Y");
				em.merge(estimateDailyProd);*/
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return poId;
	}

	@Override
	public int updatePOItem(InventoryPurchaseOrder purchaseOrder)
			throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		InventoryPurchaseOrder oldPO = null;
		
		int poId;
		try {

			poId = purchaseOrder.getId();
			/*
			 * purchaseOrder.setDate(currDate);
			 * 
			 * // persist the PO purchaseOrder.setDeleteFlag("N");
			 * purchaseOrder.setPoStatus("N"); purchaseOrder.setApproved("N");
			 */

			
			em = entityManagerFactory.createEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();
			oldPO = em.find(InventoryPurchaseOrder.class, poId);
			//oldPO.setApproved("N");
			oldPO.setTotalQuantity(purchaseOrder.getTotalQuantity());
			oldPO.setItemTotal(purchaseOrder.getItemTotal());
			oldPO.setTaxAmt(purchaseOrder.getTaxAmt());
			oldPO.setTotalPrice(purchaseOrder.getTotalPrice());
			oldPO.setUpdatedBy(purchaseOrder.getUpdatedBy());
			oldPO.setUpdatedDate(purchaseOrder.getUpdatedDate());
			
			em.merge(oldPO);

			Query query = em1
					.createNativeQuery("delete from im_t_raw_purchase_order_items_c  where purchase_order_id=? and store_id=?");
			query.setParameter(1, poId);
			query.setParameter(2, purchaseOrder.getStoreId());
			query.executeUpdate();
			em1.getTransaction().commit();
			
			// persist each PO item
			List<InventoryPurchaseOrderItem> purchaseOrderItems = purchaseOrder
					.getInventoryPurchaseOrderItems();
			
			Iterator<InventoryPurchaseOrderItem> iterator = purchaseOrderItems
					.iterator();
			while (iterator.hasNext()) {
				InventoryPurchaseOrderItem purchaseOrderItem = (InventoryPurchaseOrderItem) iterator
						.next();
				InventoryPurchaseOrder inventoryPurchaseOrder = new InventoryPurchaseOrder();
				inventoryPurchaseOrder.setId(purchaseOrder.getId());
				purchaseOrderItem.setInventoryPurchaseOrder(inventoryPurchaseOrder);
				purchaseOrderItem.setDeleteFlag("N");
				purchaseOrderItem.setStoreId(purchaseOrder.getStoreId());
				em.persist(purchaseOrderItem);

			}
			em.getTransaction().commit();
			poId = purchaseOrder.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
			if(em1 != null) em1.close();
		}
		return poId;
	}

	@Override
	public int updateStockInItem(InventoryStockIn inventoryStockIn)
			throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		InventoryStockIn oldStockIn = null;
		int stckInId;
		try {

			stckInId = inventoryStockIn.getId();
			
			em = entityManagerFactory.createEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();
			oldStockIn = em.find(InventoryStockIn.class, stckInId);
			oldStockIn.setVendorId(inventoryStockIn.getVendorId());
			oldStockIn.setTotalQuantity(inventoryStockIn.getTotalQuantity());
			oldStockIn.setItemTotal(inventoryStockIn.getItemTotal());
			oldStockIn.setDiscPer(inventoryStockIn.getDiscPer());
			oldStockIn.setDiscAmt(inventoryStockIn.getDiscAmt());
			oldStockIn.setTaxAmt(inventoryStockIn.getTaxAmt());
			oldStockIn.setTotalPrice(inventoryStockIn.getTotalPrice());
			oldStockIn.setRoundOffAmt(inventoryStockIn.getRoundOffAmt());
			oldStockIn.setUpdatedBy(inventoryStockIn.getUpdatedBy());
			oldStockIn.setUpdatedDate(inventoryStockIn.getUpdatedDate());
			em.merge(oldStockIn);
			
			Query query1 = em1
					.createNativeQuery("delete from im_t_raw_stock_in_items_c  where stock_in_id=? and store_id=?");
			query1.setParameter(1, stckInId);
			query1.setParameter(2, inventoryStockIn.getStoreId());
			query1.executeUpdate();
			em1.getTransaction().commit();
			// persist each StockIn item
			List<InventoryStockInItem> stockInItems = inventoryStockIn
					.getInventoryStockInItems();
			Iterator<InventoryStockInItem> iterator = stockInItems.iterator();
			while (iterator.hasNext()) {
				InventoryStockInItem stockInItem = (InventoryStockInItem) iterator
						.next();
				InventoryStockIn inventoryStckIn = new InventoryStockIn();
				inventoryStckIn.setId(inventoryStockIn.getId());
				stockInItem.setInventoryStockIn(inventoryStckIn);
				stockInItem.setDeleteFlag("N");
				em.persist(stockInItem);

				// get item rate and item id
				int itemId = stockInItem.getInventoryItems().getId();
				double itemRate = stockInItem.getItemRate();
				int storeId = stockInItem.getStoreId();

				// update inventory item rate in im_m_inv_type_items_c
				Query query = em
						.createNativeQuery("update im_m_inv_type_items_c set rate=? where id=? and store_id=?");
				query.setParameter(1, itemRate);
				query.setParameter(2, itemId);
				query.setParameter(3, storeId);
				query.executeUpdate();

			}
			em.getTransaction().commit();
			stckInId = inventoryStockIn.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		//journalEntry(inventoryStockIn);
		
		return stckInId;
	}

	@Override
	public String updateEachStockInItem(
			InventoryStockInItem inventoryStockInItem) throws DAOException {
		EntityManager em = null;
		String messaString = "";
		
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// persist each StockIn item
			em.merge(inventoryStockInItem);

			// get item rate and item id
			int itemId = inventoryStockInItem.getInventoryItems().getId();
			double itemRate = inventoryStockInItem.getItemRate();
			int storeId = inventoryStockInItem.getStoreId();
			// update inventory item rate in im_m_inv_type_items_c
			Query query = em
					.createNativeQuery("update im_m_inv_type_items_c set rate=? where id=? and store_id=?");
			query.setParameter(1, itemRate);
			query.setParameter(2, itemId);
			query.setParameter(3, storeId);
			query.executeUpdate();

			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateEachRawStockOutItem(
			InventoryStockOutItem inventoryStockOutItem) throws DAOException {
		EntityManager em = null;
		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update each stock out item
			em.merge(inventoryStockOutItem);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateEachPOItem(
			InventoryPurchaseOrderItem inventoryPurchaseOrderItem)
			throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// persist each PO item
			em.merge(inventoryPurchaseOrderItem);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateFgStockInItem(FgStockInItemsChild fgStockInItemsChild)
			throws DAOException {
		EntityManager em = null;
		FgStockInItemsChild fgStockInItem = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT i FROM FgStockInItemsChild i WHERE i.id=:id and i.storeId=:storeid and i.deleteFlag='N'");
			qry.setParameter("storeid", fgStockInItemsChild.getStoreId());
			qry.setParameter("id", fgStockInItemsChild.getId());
			fgStockInItem = (FgStockInItemsChild) qry.getSingleResult();

			fgStockInItemsChild.setOldStockInQuantity(fgStockInItem
					.getStockInQuantity());

			// update each fg stock in item
			em.merge(fgStockInItemsChild);

			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String updateVendor(InventoryVendor inventoryVendor)
			throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update vendor
			em.merge(inventoryVendor);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		updateVendorAccount(inventoryVendor);
		return messaString;
	}

	@Override
	public String updateInventoryItem(InventoryItems inventoryItems)
			throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update inventory item
			em.merge(inventoryItems);
			em.getTransaction().commit();

			messaString = "Successfully Updated.";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Updation Failed.";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public List<InventoryStockIn> getInventoryStockIn(Integer storeId,
			String date) throws DAOException {
		System.out.println("in getInventoryStockIn storeId = "+storeId + " and date = " + date);
		List<InventoryStockIn> inventoryStckInLst = null;
		int storeid = (storeId);

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);

			TypedQuery<InventoryStockIn> qry = em
					.createQuery("SELECT s FROM InventoryStockIn s WHERE s.storeId=:storeid and s.date=:date and s.deleteFlag='N'",
					    InventoryStockIn.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);

			inventoryStckInLst = qry.getResultList();
			
			Iterator<InventoryStockIn> iterator = inventoryStckInLst.iterator();
			while (iterator.hasNext()) {
				InventoryStockIn inventoryStockIn = iterator.next();
				System.out.println("in getInventoryStockIn inventoryStockIn = "+inventoryStockIn);
				int vendorId = inventoryStockIn.getVendorId();
				System.out.println("in getInventoryStockIn vendorId = "+inventoryStockIn.getVendorId());
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",
						    InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vendrName = vendor.getName();
				inventoryStockIn.setVendorName(vendrName);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return inventoryStckInLst;
	}

	@Override
	public List<InventoryStockIn> getInventoryStockInById(Integer storeId, Integer id) throws DAOException {

		List<InventoryStockIn> inventoryStckInLst = null;
		List<MetricUnit> metricUnits = null;
		
    Map<Integer, InventoryVendor> vendorMap = new HashMap<>();
    Map<Integer, MetricUnit> metricUnitMap = new HashMap<>();
    
		int storeid = (storeId);
		int stockInId = (id);
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		
		EntityManager em = null;
		try {
			StoreMaster store = addressDAO.getStoreByStoreId(storeid);
			int period = store.getStockPeriod();
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryStockIn> qryInStockIn = em
					.createQuery("SELECT s FROM InventoryStockIn s WHERE s.storeId=:storeid AND s.id=:id and s.deleteFlag='N'",
					    InventoryStockIn.class);
			qryInStockIn.setParameter("storeid", storeid);
			qryInStockIn.setParameter("id", stockInId);

			inventoryStckInLst = qryInStockIn.getResultList();
			
			TypedQuery<MetricUnit> qryMetricUnit = em.createQuery("SELECT u FROM MetricUnit u WHERE u.deleteFlag='N'", 
			    MetricUnit.class);
			metricUnits = qryMetricUnit.getResultList();
      for(MetricUnit metricUnit : metricUnits) {
        metricUnitMap.put(metricUnit.getId(), metricUnit);
      }
			
			TypedQuery<InventoryVendor> qryGetVendors = em
          .createQuery("SELECT v FROM InventoryVendor v",
              InventoryVendor.class);
      List<InventoryVendor> vendors = qryGetVendors.getResultList();
      for(InventoryVendor vendor : vendors) {
        vendorMap.put(vendor.getId(), vendor);
      }
	
      
			Iterator<InventoryStockIn> iterator = inventoryStckInLst.iterator();
			while (iterator.hasNext()) {
				InventoryStockIn inventoryStockIn = iterator.next();

				// setVendorName
        InventoryVendor vendor = vendorMap.get(inventoryStockIn.getVendorId());
        inventoryStockIn.setVendorName(vendor.getName());
				
				try {
					int edpId = inventoryStockIn.getEdpId();
					
					List<InventoryStockInItem> stockInItems = inventoryStockIn.getInventoryStockInItems();
					Iterator<InventoryStockInItem> iterator2 = stockInItems.iterator();
					
          //set edp quantity in InventoryStockInItem
          RecipeManagementDAO recipeManagementDAO = new RecipeManagementDAOImpl();
          List<Ingredient> ingredients = recipeManagementDAO.getIngredientsForEdp(storeId, edpId);
          Map<Integer, Ingredient> ingredientMap = new HashMap<>();
          for(Ingredient ingredient : ingredients) {
            ingredientMap.put(ingredient.getInventoryItem().getId(), ingredient);
          }

		      // setCurrentStockInQuantityEdpWise
					while (iterator2.hasNext()) {
						InventoryStockInItem inventoryStockInItem = iterator2.next();
						// get current stock in quantity by EDP
						Double currentStockInEdpWise = getCurrentStockByItemByPeriodByEdp(storeId, inventoryStockInItem.getInventoryItems().getId(),
										period, edpId);
						inventoryStockInItem.setCurrentStockInQuantityEdpWise(currentStockInEdpWise);
						
						//set metric unit in inventoryStockInItem
            // setMetricUnit
						int unitId = inventoryStockInItem.getUnitId();
            inventoryStockInItem.setMetricUnit(metricUnitMap.get(unitId));

            /*
						Iterator<MetricUnit> iterator3 = metricUnits.iterator();
						while (iterator3.hasNext()) {
							MetricUnit metricUnit = iterator3.next();
							int unitid=metricUnit.getId();
							if(unitId==unitid) {
								inventoryStockInItem.setMetricUnit(metricUnit);
								break;
							}
						}
						*/
            if(ingredientMap.size()>0)
            {
            	Ingredient ingredient = ingredientMap.get(inventoryStockInItem.getInventoryItems().getId());
            	inventoryStockInItem.setEdpQuantity(ingredient.getEdpQuantity());
            }
            else
            {
            	inventoryStockInItem.setEdpQuantity(0.0);	
            }
            
            	 
            /*
            int invItemId = inventoryStockInItem.getInventoryItems().getId();
            Iterator<Ingredient> iterator3 = ingredients.iterator();
            // setEdpQuantity
            while (iterator3.hasNext()) {
              Ingredient ingredient = iterator3.next();
              int invItmId = ingredient.getInventoryItem().getId();
              double edpQnty = ingredient.getEdpQuantity();
              if(invItemId==invItmId){
                inventoryStockInItem.setEdpQuantity(edpQnty);
              }
            }
            */
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryStckInLst;

	}

	@Override
	public List<InventoryStockOut> getInventoryStockOutById(Integer storeId,
			Integer id) throws DAOException {

		List<InventoryStockOut> inventoryStckOutLst = null;
		int storeid = (storeId);
		int stockOutId = (id);

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryStockOut> qry = em
					.createQuery("SELECT s FROM InventoryStockOut s WHERE s.storeId=:storeid and s.id=:id",
					    InventoryStockOut.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", stockOutId);

			inventoryStckOutLst = qry.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		} finally {
			if (em != null) em.close();
		}
		
		return inventoryStckOutLst;

	}

	@Override
	public List<ItemCurrentStock> getCurrentStockByItem(Integer storeId, Integer itemId)
			throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<ItemCurrentStock> itemCurrentStockList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_ITEM_CUR_STOCK);
			callableStatement.setInt(1, storeId);
			callableStatement.setInt(2, itemId);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<ItemCurrentStock> resultSetMapper1 = new ReflectionResultSetMapper<ItemCurrentStock>(
					ItemCurrentStock.class);

			while (rs.next()) {
				ItemCurrentStock itemCurrentStock=new ItemCurrentStock();
				itemCurrentStock = resultSetMapper1.mapRow(rs);
				itemCurrentStockList.add(itemCurrentStock);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return itemCurrentStockList;

	}

	public String getCurrentStockByItemByPeriod(Integer storeId, Integer itemId,
			int period) throws DAOException {

		List<InventoryStockOutItem> inventoryStckOutItemLst = null;
		List<InventoryStockInItem> inventoryStckInItemLst = null;
		double totalInvStockIn = 0;
		double totalInvStockOut = 0;
		double currentStock = 0;
		int storeid = (storeId);
		int itemid = (itemId);

		EntityManager em = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			// dateFormat.format(date);
			Date currDate = dateFormat.parse(dateFormat.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -period);
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryStockInItem> qry = em
					.createQuery("SELECT s FROM InventoryStockInItem s WHERE s.storeId=:storeid and s.inventoryItems.id=:itemid and s.inventoryStockIn.date BETWEEN :startDate AND :endDate",
					    InventoryStockInItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("itemid", itemid);
			qry.setParameter("startDate", previousDate);
			qry.setParameter("endDate", currDate);

			inventoryStckInItemLst = qry.getResultList();

			Iterator<InventoryStockInItem> iterator = inventoryStckInItemLst.iterator();
			while (iterator.hasNext()) {
				InventoryStockInItem inventoryStockInItem = iterator.next();
				totalInvStockIn = totalInvStockIn + inventoryStockInItem.getItemQuantity();
			}

			TypedQuery<InventoryStockOutItem> qry1 = em
					.createQuery("SELECT s FROM InventoryStockOutItem s WHERE s.storeId=:storeid and s.inventoryItems.id=:itemid and s.inventoryStockOut.date BETWEEN :startDate AND :endDate",
					    InventoryStockOutItem.class);
			qry1.setParameter("storeid", storeid);
			qry1.setParameter("itemid", itemid);
			qry1.setParameter("startDate", previousDate);
			qry1.setParameter("endDate", currDate);

			inventoryStckOutItemLst = qry1.getResultList();

			Iterator<InventoryStockOutItem> iterator1 = inventoryStckOutItemLst.iterator();
			while (iterator1.hasNext()) {
				InventoryStockOutItem inventoryStockOutItem = iterator1.next();
				totalInvStockOut = totalInvStockOut	+ inventoryStockOutItem.getItemQuantity();
			}

			// calculate current stock, assuming that stock in > stock out
			currentStock = totalInvStockIn - totalInvStockOut;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		} finally {
			if (em != null) em.close();
		}
		return currentStock + "";

	}

	public Double getCurrentStockByItemByPeriodByEdp(Integer storeId,
			Integer itemId, int period, int edp) throws DAOException {

//		List<InventoryStockInItem> inventoryStckInItemLst = null;
//		double totalInvStockInEdpWise = 0;

    double currentStockEdpWise = 0;
		EntityManager em = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			// dateFormat.format(date);
			Date currDate = dateFormat.parse(dateFormat.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -period);
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);
			
			em = entityManagerFactory.createEntityManager();

			/*
			TypedQuery<InventoryStockInItem> qry = em
					.createQuery("SELECT s FROM InventoryStockInItem s WHERE s.storeId=:storeid and s.inventoryItems.id=:itemid and s.inventoryStockIn.edpId=:edpId and s.inventoryStockIn.date BETWEEN :startDate AND :endDate",
					    InventoryStockInItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("itemid", itemid);
			qry.setParameter("edpId", edp);
			qry.setParameter("startDate", previousDate);
			qry.setParameter("endDate", currDate);

			inventoryStckInItemLst = qry.getResultList();

			Iterator<InventoryStockInItem> iterator = inventoryStckInItemLst.iterator();
			while (iterator.hasNext()) {
				InventoryStockInItem inventoryStockInItem = iterator.next();
				totalInvStockInEdpWise = totalInvStockInEdpWise	+ inventoryStockInItem.getItemQuantity();
			}
			// calculate current stock, assuming that stock in > stock out
			currentStockEdpWise = totalInvStockInEdpWise;
			*/
			
      TypedQuery<Double> qry = em
          .createQuery("SELECT SUM(s.itemQuantity) FROM InventoryStockInItem s WHERE s.storeId=:storeid and s.inventoryItems.id=:itemid and s.inventoryStockIn.edpId=:edpId and s.inventoryStockIn.date BETWEEN :startDate AND :endDate",
              Double.class);
      qry.setParameter("storeid", storeId);
      qry.setParameter("itemid", itemId);
      qry.setParameter("edpId", edp);
      qry.setParameter("startDate", previousDate);
      qry.setParameter("endDate", currDate);

      currentStockEdpWise = qry.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		} finally {
			if (em != null) em.close();
		}
		
		return currentStockEdpWise;
	}

	public String getCurrentStockOutByItemByPeriodByEdp(Integer storeId,
			Integer itemId, int period, int edp) throws DAOException {

		List<InventoryStockOutItem> inventoryStckOutItemLst = null;
		double totalInvStockOutEdpWise = 0;
		double currentStockOutEdpWise = 0;
		int storeid = (storeId);
		int itemid = (itemId);

		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			// dateFormat.format(date);
			Date currDate = dateFormat.parse(dateFormat.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -period);
			Date previousDate = cal.getTime();

			List<Date> dateList = new ArrayList<Date>();

			dateList.add(currDate);
			dateList.add(previousDate);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryStockOutItem> qry = em
					.createQuery("SELECT s FROM InventoryStockOutItem s WHERE s.storeId=:storeid and s.inventoryItems.id=:itemid and s.inventoryStockOut.edpId=:edpId and s.inventoryStockOut.date BETWEEN :startDate AND :endDate",
					    InventoryStockOutItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("itemid", itemid);
			qry.setParameter("edpId", edp);
			qry.setParameter("startDate", previousDate);
			qry.setParameter("endDate", currDate);

			inventoryStckOutItemLst = qry.getResultList();

			Iterator<InventoryStockOutItem> iterator = inventoryStckOutItemLst.iterator();
			while (iterator.hasNext()) {
				InventoryStockOutItem inventoryStockOutItem = iterator.next();
				totalInvStockOutEdpWise = totalInvStockOutEdpWise + inventoryStockOutItem.getItemQuantity();
			}

			currentStockOutEdpWise = totalInvStockOutEdpWise;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		} finally {
			if (em != null) em.close();
		}
		return currentStockOutEdpWise + "";

	}

	@Override
	public ResultSet getCurrentStockByStore(Integer storeId) throws DAOException {

		int storeid = (storeId);
		ResultSet resultSet = null;
		Connection connection = null;
		Statement st = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			// get current stock in data
			String sql = "select a.created_date,a.name,a.unit, t2.item_id, t2.quantity from im_m_inv_type_items_c a,"

					+ "(select t.item_id, sum(t.item_quantity) as quantity from ("

					+ "(select item_id, sum(item_quantity)  as item_quantity "
					+ "from im_t_raw_stock_in_items_c where store_id="
					+ storeid
					+ " group by item_id ) "
					+ "union (select item_id, sum(ifnull(item_quantity*-1, 0)) from im_t_raw_stock_out_items_c "
					+ "where store_id="
					+ storeid
					+ "  group by item_id )"
					+ ")as t group by item_id) t2 where a.id=t2.item_id";

			System.out.println("Sql is :::" + sql);

			st = connection.createStatement();
			resultSet = st.executeQuery(sql);

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be fetched", e);
		}

		finally {
			if (em != null) em.close();
			/*
			 * try { if (connection!=null) { connection.close(); } if (st!=null)
			 * { st.close(); }
			 * 
			 * } catch (SQLException e) { 
			 * e.printStackTrace(); }
			 */
		}
		return resultSet;

	}

	@Override
	public List<InventoryStockOut> getInventoryStockOut(Integer storeId,
			String date) throws DAOException {

		List<InventoryStockOut> inventoryStckOutLst = null;
		int storeid = (storeId);

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);

			TypedQuery<InventoryStockOut> qry = em
					.createQuery("SELECT s FROM InventoryStockOut s WHERE s.storeId=:storeid and s.date=:date and s.deleteFlag='N'",
					    InventoryStockOut.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);

			inventoryStckOutLst = qry.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return inventoryStckOutLst;
	}

	@Override
	public void addInventoryItem(InventoryItems inventoryItems)
			throws DAOException {
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			// get the store name
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			// get the logged in user
			User loggedUser = (User) context.getExternalContext()
					.getSessionMap().get("loggeduser");
			String userId = loggedUser.getUserId();

			Date curntDate = new Date();

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(curntDate);

			int storeId = 0;
			int restId = 0;
			if (storeMaster != null) {
				restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			inventoryItems.setStoreId(storeId);
			inventoryItems.setDeleteFlag("N");
			inventoryItems.setCreatedBy(userId);
			inventoryItems.setCreatedDate(today);
			inventoryItems.setUpdatedBy(userId);
			inventoryItems.setUpdatedDate(today);
			em.persist(inventoryItems);

			em.getTransaction().commit();

		}

		catch (Exception e) {

			System.out.println("in dao: addInventoryItems:");
			Throwable cause = e.getCause();

			if (cause instanceof ConstraintViolationException) {
				System.out.println("Integrity constraint");
				throw new DAOException("Integrity Constraint violation", e);
			}
			e.printStackTrace();
			throw new DAOException("Check Inventory data", e);
		}

		finally {
			if (em != null) em.close();
		}

	}

	@Override
	public String approvePO(Integer id, String approvedBy, String updatedBy,
			String updatedDate) throws DAOException {

		EntityManager em = null;
		String status = "";
		InventoryPurchaseOrder inventoryPurchaseOrder = null;
		try {
			int poId = (id);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			inventoryPurchaseOrder = em
					.find(InventoryPurchaseOrder.class, poId);

			inventoryPurchaseOrder.setApproved("Y");
			inventoryPurchaseOrder.setApprovedBy(approvedBy);
			inventoryPurchaseOrder.setUpdatedBy(updatedBy);
			inventoryPurchaseOrder.setUpdatedDate(updatedDate);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check MenuItem to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String approveRawStockOut(Integer id, Integer storeId,
			String approvedBy, String updatedBy, String updatedDate)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		InventoryStockOut inventoryStockOut = null;
		
		try {
			int rawStckOutId = (id);
			int storeId1 = (storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<InventoryStockOut> qry = em
					.createQuery("SELECT i FROM InventoryStockOut i WHERE i.id=:id and i.storeId=:storeid and i.deleteFlag='N'",
					    InventoryStockOut.class);
			qry.setParameter("storeid", storeId1);
			qry.setParameter("id", rawStckOutId);
			inventoryStockOut = qry.getSingleResult();

			inventoryStockOut.setApproved("Y");
			inventoryStockOut.setApprovedBy(approvedBy);
			inventoryStockOut.setUpdatedBy(updatedBy);
			inventoryStockOut.setUpdatedDate(updatedDate);
			em.merge(inventoryStockOut);

			// set approve status for raw stock out in corresponding edp
			/*
			 * int edpId=inventoryStockOut.getEdpId(); try { Query qry1 = em
			 * .createQuery(
			 * "SELECT e FROM EstimateDailyProd e WHERE e.id=:id and e.storeId=:storeid and e.deleteFlag='N'"
			 * ); qry1.setParameter("storeid", storeId1);
			 * qry1.setParameter("id", edpId); estimateDailyProd =
			 * (EstimateDailyProd) qry1.getSingleResult();
			 * estimateDailyProd.setRawStockOutStatus("Y"); } catch (Exception
			 * e) {  e.printStackTrace(); }
			 */

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check MenuItem to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String approveFgStockIn(Integer storeId, Integer id, String by)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		FgStockIn fgStockIn = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String updationDate = formatter.format(new Date());
		try {
			int fgId = (id);
			int storeId1 = (storeId);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<FgStockIn> qry = em
					.createQuery("SELECT i FROM FgStockIn i WHERE i.id=:id and i.storeId=:storeid and i.deleteFlag='N'",
					    FgStockIn.class);
			qry.setParameter("storeid", storeId1);
			qry.setParameter("id", fgId);
			fgStockIn = qry.getSingleResult();

			fgStockIn.setApproved("Y");
			fgStockIn.setApprovedBy(by);
			fgStockIn.setUpdatedBy(by);
			fgStockIn.setUpdatedDate(updationDate);
			em.merge(fgStockIn);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String stckInDate = df.format(fgStockIn.getDate());
			// create a payment object
			FgInvoicePayment invoicePayment = new FgInvoicePayment();

			invoicePayment.setFgStockInId(fgStockIn.getId());
			invoicePayment.setVendorId(fgStockIn.getVendorId());
			invoicePayment.setBillAmount(fgStockIn.getTotalPrice());
			invoicePayment.setAmountToPay(fgStockIn.getTotalPrice());
			invoicePayment.setPaidAmount(0.0);
			invoicePayment.setCreditLimit(50000);
			invoicePayment.setDeleteFlag("N");
			invoicePayment.setFgStockInDate(stckInDate);
			invoicePayment.setStoreId(fgStockIn.getStoreId());
			invoicePayment.setIsReturn("N");
			invoicePayment.setCreatedBy(by);
			invoicePayment.setCreatedDate(updationDate);
			em.persist(invoicePayment);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check approve fgstockin to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String closeStockIn(InventoryStockIn inventoryStockIn) throws DAOException {
		System.out.println("closeStockIn inventoryStockIn "+inventoryStockIn);
		EntityManager em = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String status = "";
		InventoryStockIn inventoryStckIn = null;
		List<InventoryStockOutItem> nonstockableitemlist=new ArrayList<InventoryStockOutItem>();
		try {
		    
			int stckInId = inventoryStockIn.getId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			inventoryStckIn = em.find(InventoryStockIn.class, stckInId);
			inventoryStckIn.setClosed("Y");
			
			//for auto stock out of non stockable items
			SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
		    double totQty=0;
			List<InventoryStockInItem> inventoryStockInItems = inventoryStckIn.getInventoryStockInItems();
			Iterator<InventoryStockInItem> iterator = inventoryStockInItems.iterator();
			while (iterator.hasNext()) {
				InventoryStockInItem inventoryStockInItem = (InventoryStockInItem) iterator.next();
				InventoryItems item=inventoryStockInItem.getInventoryItems();
				if("N".equalsIgnoreCase(item.getIsStockable()))
				{
					InventoryStockOutItem stockoutItem=new InventoryStockOutItem();
					stockoutItem.setInventoryItems(item);
					stockoutItem.setItemQuantity(inventoryStockInItem.getItemQuantity());
					stockoutItem.setStoreId(inventoryStockInItem.getStoreId());
					MetricUnit unit=new MetricUnit();
					unit.setId(inventoryStockInItem.getUnitId());
					stockoutItem.setUnit(unit);
					stockoutItem.setTime(strDate);
					stockoutItem.setDeleteFlag("N");
					stockoutItem.setToWhom("--");
					stockoutItem.setCreatedBy(inventoryStockInItem.getCreatedBy());
					stockoutItem.setCreatedDate(inventoryStockInItem.getCreatedDate());
					nonstockableitemlist.add(stockoutItem);
					totQty+=inventoryStockInItem.getItemQuantity();
				}
			}
			
			if(nonstockableitemlist.size()>0)
			{
				InventoryStockOut inventoryStockOut=new InventoryStockOut();
				inventoryStockOut.setDate(inventoryStckIn.getDate());
				inventoryStockOut.setTime(strDate);
				inventoryStockOut.setUserId(inventoryStckIn.getUserId());
				inventoryStockOut.setTotalQuantity(totQty);
				inventoryStockOut.setStoreId(inventoryStckIn.getStoreId());
				inventoryStockOut.setDeleteFlag("N");
				inventoryStockOut.setEdpId(inventoryStckIn.getEdpId());
				inventoryStockOut.setCreatedBy(inventoryStckIn.getCreatedBy());
				inventoryStockOut.setCreatedDate(inventoryStckIn.getCreatedDate());
				inventoryStockOut.setInventoryStockOutItems(nonstockableitemlist);
				em.persist(inventoryStockOut);
				
				List<InventoryStockOutItem> inventoryStockOutItems = inventoryStockOut.getInventoryStockOutItems();
				Iterator<InventoryStockOutItem> iteratorstockout = inventoryStockOutItems.iterator();
				while (iteratorstockout.hasNext()) {
					InventoryStockOutItem inventoryStockOutItem = (InventoryStockOutItem) iteratorstockout.next();
					InventoryStockOut inventryStckOut = new InventoryStockOut();
					inventryStckOut.setId(inventoryStockOut.getId());
					inventoryStockOutItem.setInventoryStockOut(inventryStckOut);
					em.persist(inventoryStockOutItem);
				}
				
			}
			//end for auto stock out of non stockable items
			
			
			String stckInDate = df.format(inventoryStckIn.getDate());
			// create a payment object
			InventoryInvoicePayment invoicePayment = new InventoryInvoicePayment();

			invoicePayment.setStockInId(stckInId);
			invoicePayment.setPoId(inventoryStckIn.getPoId());
			invoicePayment.setVendorId(inventoryStckIn.getVendorId());
			invoicePayment.setBillAmount(inventoryStckIn.getTotalPrice());
			invoicePayment.setAmountToPay(inventoryStckIn.getTotalPrice());
			invoicePayment.setPaidAmount(0.0);
			invoicePayment.setCreditLimit(50000);
			invoicePayment.setDeleteFlag("N");
			invoicePayment.setStockInDate(stckInDate);
			invoicePayment.setStoreId(inventoryStckIn.getStoreId());
			invoicePayment.setIsReturn("N");
			em.persist(invoicePayment);
			
			

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check RawStockIn to be updated", e);
		} finally {
			if (em != null) em.close();
		}
		
		if(("y").equalsIgnoreCase(inventoryStockIn.getIs_account())) {
			journalEntry(inventoryStockIn);
		}
		
		return status;
	}

	@Override
	public String updatePO(Integer id, String poBy, String updatedBy,
			String updatedDate) throws DAOException {

		EntityManager em = null;
		String status = "";
		InventoryPurchaseOrder inventoryPurchaseOrder = null;
		try {
			int poId = (id);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			inventoryPurchaseOrder = em
					.find(InventoryPurchaseOrder.class, poId);

			inventoryPurchaseOrder.setPoBy(poBy);
			inventoryPurchaseOrder.setPoStatus("Y");
			inventoryPurchaseOrder.setUpdatedBy(updatedBy);
			inventoryPurchaseOrder.setUpdatedDate(updatedDate);

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check MenuItem to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return status;
	}

	@Override
	public String deletePOItem(Integer poId, Integer poItemId, Integer storeId)
			throws DAOException {

		int poid = (poId);
		int poItemid = (poItemId);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryPurchaseOrderItem itemToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryPurchaseOrderItem> qry = em
					.createQuery("SELECT i FROM InventoryPurchaseOrderItem i WHERE i.storeId=:storeid and i.inventoryPurchaseOrder.id=:poid and i.id=:poItemid",
					    InventoryPurchaseOrderItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("poid", poid);
			qry.setParameter("poItemid", poItemid);
			itemToBeDeleted = qry.getSingleResult();
			itemToBeDeleted.setDeleteFlag("Y");
			
			//added on 23.04.2018
			InventoryPurchaseOrder inventoryPurchaseOrder = em.find(InventoryPurchaseOrder.class, poid);
			inventoryPurchaseOrder.setTotalQuantity(inventoryPurchaseOrder.getTotalQuantity()-itemToBeDeleted.getItemQuantity());
			inventoryPurchaseOrder.setItemTotal(inventoryPurchaseOrder.getItemTotal()-itemToBeDeleted.getItemTotalPrice());
			inventoryPurchaseOrder.setTaxAmt(inventoryPurchaseOrder.getTaxAmt()-itemToBeDeleted.getTaxAmt());
			inventoryPurchaseOrder.setTotalPrice(inventoryPurchaseOrder.getTotalPrice()-itemToBeDeleted.getTotalAmt());
			
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}
	
	//added on 23.04.2018
	@Override
	public String deletePO(Integer poId, Integer storeId)
			throws DAOException {

		int poid = (poId);
		EntityManager em = null;
		InventoryPurchaseOrder inventoryPurchaseOrder = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			inventoryPurchaseOrder = em
					.find(InventoryPurchaseOrder.class, poid);
			inventoryPurchaseOrder.setDeleteFlag("Y");
			List<InventoryPurchaseOrderItem> lstItems = inventoryPurchaseOrder
					.getInventoryPurchaseOrderItems();
			Iterator<InventoryPurchaseOrderItem> iterator2 = lstItems
					.iterator();
			while (iterator2.hasNext()) {
				InventoryPurchaseOrderItem inventoryPurchaseOrderItem = (InventoryPurchaseOrderItem) iterator2.next();
				inventoryPurchaseOrderItem.setDeleteFlag("Y");
			}
			
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteVendor(Integer id, Integer storeId) throws DAOException {

		int vndorId = (id);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryVendor vendorToBeDeleted = null;
		String message = "";
		ResponseObj resobj = new ResponseObj();

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryVendor> qry = em
					.createQuery("SELECT v FROM InventoryVendor v WHERE v.storeId=:storeid and v.id=:id",
					    InventoryVendor.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", vndorId);

			vendorToBeDeleted = (InventoryVendor) qry.getSingleResult();
			vendorToBeDeleted.setDeleteFlag("Y");
			em.getTransaction().commit();
			
			resobj = deleteCustomerNVendorAccountJournal(id,storeId);
			
			if(resobj.getId()==1) {
				message = "success";
				System.out.print("Vendor data deleted successfully....");
			}
			else {
				message = "inuse";
				System.out.print("Vendor data not deleted successfully....");
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		
		//deleteCustomerNVendorAccountJournal(id,storeid);
		return message;

	}

	@Override
	public String deleteStockInItem(Integer stockInId, Integer stockInItemId,
			Integer storeId) throws DAOException {

		int stockInid = (stockInId);
		int stockInItemid = (stockInItemId);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryStockInItem itemToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryStockInItem> qry = em
					.createQuery("SELECT i FROM InventoryStockInItem i WHERE i.storeId=:storeid and i.inventoryStockIn.id=:stockInId and i.id=:stockInItemid",
					    InventoryStockInItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("stockInId", stockInid);
			qry.setParameter("stockInItemid", stockInItemid);
			itemToBeDeleted = qry.getSingleResult();
			itemToBeDeleted.setDeleteFlag("Y");
			
			//added on 30.04.2018
			InventoryStockIn inventoryStockIn = em.find(InventoryStockIn.class, stockInid);
			inventoryStockIn.setTotalQuantity(inventoryStockIn.getTotalQuantity()-itemToBeDeleted.getItemQuantity());
			inventoryStockIn.setItemTotal(inventoryStockIn.getItemTotal()-itemToBeDeleted.getItemTotalPrice());
			inventoryStockIn.setDiscAmt(inventoryStockIn.getDiscAmt()-itemToBeDeleted.getDiscAmt());
			inventoryStockIn.setTaxAmt(inventoryStockIn.getTaxAmt()-itemToBeDeleted.getTaxAmount());
			inventoryStockIn.setTotalPrice(inventoryStockIn.getTotalPrice()-itemToBeDeleted.getItemGrossAmount());
			
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}
	
	//added on 30.04.2018
	@Override
	public String deleteStockIn(Integer stockInId,
			Integer storeId) throws DAOException {

		int stockInid = (stockInId);
		EntityManager em = null;
		InventoryStockIn inventoryStockIn=null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			inventoryStockIn = em
					.find(InventoryStockIn.class, stockInid);
			inventoryStockIn.setDeleteFlag("Y");
			List<InventoryStockInItem> lstItems = inventoryStockIn
					.getInventoryStockInItems();
			Iterator<InventoryStockInItem> iterator2 = lstItems
					.iterator();
			while (iterator2.hasNext()) {
				InventoryStockInItem inventoryStockInItem = (InventoryStockInItem) iterator2.next();
				inventoryStockInItem.setDeleteFlag("Y");
			}
			
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteStockOutItem(Integer stockOutId, Integer stockOutItemId,
			Integer storeId) throws DAOException {

		int stockOutId1 = (stockOutId);
		int stockOutItemId1 = (stockOutItemId);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryStockOutItem itemToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryStockOutItem> qry = em
					.createQuery("SELECT i FROM InventoryStockOutItem i WHERE i.storeId=:storeid and i.inventoryStockOut.id=:stockOutId and i.id=:stockOutItemId",
					    InventoryStockOutItem.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("stockOutId", stockOutId1);
			qry.setParameter("stockOutItemId", stockOutItemId1);
			itemToBeDeleted = qry.getSingleResult();
			itemToBeDeleted.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}
	
	//added on 16.01.2020
		@Override
		public String deleteStockOut(Integer stockOutId,
				Integer storeId) throws DAOException {

			int stockOutid = (stockOutId);
			EntityManager em = null;
			InventoryStockOut inventoryStockOut=null;
			String message = "";

			try {
				
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				
				inventoryStockOut = em
						.find(InventoryStockOut.class, stockOutid);
				inventoryStockOut.setDeleteFlag("Y");
				List<InventoryStockOutItem> lstItems = inventoryStockOut
						.getInventoryStockOutItems();
				Iterator<InventoryStockOutItem> iterator2 = lstItems
						.iterator();
				while (iterator2.hasNext()) {
					InventoryStockOutItem inventoryStockOutItem = (InventoryStockOutItem) iterator2.next();
					inventoryStockOutItem.setDeleteFlag("Y");
				}
				
				em.getTransaction().commit();
				message = "success";

			} catch (Exception e) {
				e.printStackTrace();
				message = "failure";
				throw new DAOException("Check data to be deleted", e);
			} finally {
				if (em != null) em.close();
			}
			return message;

		}

	@Override
	public void addInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException {
		EntityManager em = null;
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			// get the store name
			StoreMaster storeMaster = (StoreMaster) context
					.getExternalContext().getSessionMap().get("selectedstore");

			// get the logged in user
			User loggedUser = (User) context.getExternalContext()
					.getSessionMap().get("loggeduser");
			String userId = loggedUser.getUserId();

			Date curntDate = new Date();

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String today = formatter.format(curntDate);

			int storeId = 0;
			int restId = 0;
			if (storeMaster != null) {
				restId = storeMaster.getRestaurantId();
				System.out.println("rest id:  " + restId);
				storeId = storeMaster.getId();
			}

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			inventoryVendor.setStoreId(storeId);
			inventoryVendor.setDeleteFlag("N");
			inventoryVendor.setCreatedBy(userId);
			inventoryVendor.setCreatedDate(today);
			inventoryVendor.setUpdatedBy(userId);
			inventoryVendor.setUpdatedDate(today);
			em.persist(inventoryVendor);

			em.getTransaction().commit();

		}

		catch (Exception e) {

			System.out.println("in dao: addInventoryVendor:");
			Throwable cause = e.getCause();

			if (cause instanceof ConstraintViolationException) {
				System.out.println("Integrity constraint");
				throw new DAOException("Integrity Constraint violation", e);
			}
			e.printStackTrace();
			throw new DAOException("Check Inventory data", e);
		}

		finally {
			if (em != null) em.close();
		}

	}

	@Override
	public InventoryType getInventoryTypeById(Integer inventoryTypeId)
			throws DAOException {

		InventoryType inventoryType = null;

		Integer invTypeId;
		EntityManager em = null;
		try {
			invTypeId = inventoryTypeId;
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<InventoryType> qry = em
					.createQuery("SELECT u FROM InventoryType u WHERE u.id= :inventoryTypeId",
					    InventoryType.class);
			qry.setParameter("inventoryTypeId", invTypeId);

			// userLst = (List<User>) qry.getResultList();
			inventoryType = qry.getSingleResult();

			em.getTransaction().commit();
			// em.close();

		} catch (Exception e) {
			throw new DAOException("Check User data", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryType;
	}

	@Override
	public void updateInventoryType(InventoryType inventoryType)
			throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryType inventoryType2 = em.find(InventoryType.class,
					inventoryType.getId());
			inventoryType2.setName(inventoryType.getName());
			em.merge(inventoryType2);
			em.getTransaction().commit();
			System.out.print("Inventory Type updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public int createStockIn(InventoryStockIn inventoryStockIn)
			throws DAOException {
		System.out.println("createStockIn inventoryStockIn = "+inventoryStockIn);
		EntityManager em = null;
		int stockInId;
		InventoryStockInItem invStckInItem = null;
		
		try {
			stockInId = 0;
			// inventoryStockIn.setDate(currDate);

			// persist the stock in
			inventoryStockIn.setDeleteFlag("N");
			inventoryStockIn.setClosed("N");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(inventoryStockIn);

			// persist each stock in item
			List<InventoryStockInItem> inventoryStockInItems = inventoryStockIn.getInventoryStockInItems();
			Iterator<InventoryStockInItem> iterator = inventoryStockInItems
					.iterator();
			while (iterator.hasNext()) {
				InventoryStockInItem inventoryStockInItem = (InventoryStockInItem) iterator
						.next();
				/*System.out.println("name::: "
						+ inventoryStockInItem.getInventoryItems().getName());
				System.out.println("code::: "
						+ inventoryStockInItem.getInventoryItems().getCode());*/

				invStckInItem = inventoryStockInItem;
				InventoryStockIn inventryStckIn = new InventoryStockIn();
				inventryStckIn.setId(inventoryStockIn.getId());
				inventoryStockInItem.setInventoryStockIn(inventryStckIn);
				inventoryStockInItem.setDeleteFlag("N");
				/*if (inventoryStockInItem.getInventoryItems().getTax()
						.equalsIgnoreCase("Y")) {
					inventoryStockInItem.setTaxRate(vatRate);
					double vatAmt = (vatRate * (inventoryStockInItem
							.getItemTotalPrice())) / 100;
					double grossAmt = inventoryStockInItem.getItemTotalPrice()
							+ vatAmt;
					inventoryStockInItem.setTaxAmount(vatAmt);
					inventoryStockInItem.setItemGrossAmount(grossAmt);
				}*/
				// inventoryStockInItem.setCreatedDate(dateCurrnt);
				em.persist(inventoryStockInItem);
				// get item rate and item id
				int itemId = inventoryStockInItem.getInventoryItems().getId();
				double itemRate = inventoryStockInItem.getItemRate();
				int storeId = inventoryStockInItem.getStoreId();
				// update inventory item rate in im_m_inv_type_items_c
				Query query = em
						.createNativeQuery("update im_m_inv_type_items_c set rate=? where id=? and store_id=?");
				query.setParameter(1, itemRate);
				query.setParameter(2, itemId);
				query.setParameter(3, storeId);
				query.executeUpdate();
				
			}

			em.getTransaction().commit();
			stockInId = inventoryStockIn.getId();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error item---"
					+ invStckInItem.getInventoryItems().getName());
			em.getTransaction().rollback();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		//journalEntry(inventoryStockIn);
		
		return stockInId;
	}

	@Override
	public int createFgStockIn(FgStockIn fgStockIn) throws DAOException {
		EntityManager em = null;
		int fgStockInId;
		EstimateDailyProd dailyProd = null;
		
		try {

			fgStockInId = 0;
			Date currDate = new Date();
			fgStockIn.setDate(currDate);

			// persist the fg stock in
			fgStockIn.setDeleteFlag("N");
			fgStockIn.setApproved("N");
			fgStockIn.setStockInType("FRESH STOCKIN");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(fgStockIn);

			// persist each fg stockin item
			List<FgStockInItemsChild> fgStockInItemsChilds = fgStockIn
					.getFgStockInItemsChilds();
			Iterator<FgStockInItemsChild> iterator = fgStockInItemsChilds
					.iterator();
			while (iterator.hasNext()) {
				FgStockInItemsChild fgStockInItemsChild = (FgStockInItemsChild) iterator
						.next();

				fgStockInItemsChild.setFgStockInId(fgStockIn);
				fgStockInItemsChild.setDeleteFlag("N");
				em.persist(fgStockInItemsChild);
				
				// get item rate and item id
				int itemId = fgStockInItemsChild.getMenuItem().getId();
				double itemRate = fgStockInItemsChild.getItemRate();
				int storeId = fgStockInItemsChild.getStoreId();
				// update menu item rate in fm_m_food_items
				Query query = em.createNativeQuery("update fm_m_food_items set purchase_price=? where id=? and store_id=?");
				query.setParameter(1, itemRate);
				query.setParameter(2, itemId);
				query.setParameter(3, storeId);
				query.executeUpdate();
			}
			if(fgStockIn.getEdpId()>0) {
			try {
				// update fg stock in date in estimate daily prod
				TypedQuery<EstimateDailyProd> qry = em
						.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.id=:id and p.storeId=:storeid",
						    EstimateDailyProd.class);
				qry.setParameter("storeid", fgStockIn.getStoreId());
				qry.setParameter("id", fgStockIn.getEdpId());
				
				dailyProd = qry.getSingleResult();
				Date fgStockInDate = fgStockIn.getDate();
				dailyProd.setFgStockInDate(fgStockInDate);
				em.merge(dailyProd);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
			em.getTransaction().commit();
			fgStockInId = fgStockIn.getId();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return fgStockInId;
	}

	@Override
	public void updateInventoryTypeItem(InventoryItems inventoryTypeItem)
			throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryItems inventoryTypeItem2 = em.find(InventoryItems.class,
					inventoryTypeItem.getId());
			inventoryTypeItem2.setName(inventoryTypeItem.getName());
			inventoryTypeItem2.setCode(inventoryTypeItem.getCode());
			inventoryTypeItem2.setQuantity(inventoryTypeItem.getQuantity());
			inventoryTypeItem2.setUnit(inventoryTypeItem.getUnit());
			inventoryTypeItem2.setRate(inventoryTypeItem.getRate());
			inventoryTypeItem2.setBarcode(inventoryTypeItem.getBarcode());
			inventoryTypeItem2.setVendorId(inventoryTypeItem.getVendorId());
			em.merge(inventoryTypeItem2);
			em.getTransaction().commit();
			System.out.print("Inventory Type updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public String createStockOut(InventoryStockOut inventoryStockOut)
			throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		String identifier = "";
		EstimateDailyProd dailyProd = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em1 = entityManagerFactory.createEntityManager();
					// persist the stock in
					inventoryStockOut.setDeleteFlag("N");
					//inventoryStockOut.setApproved("Y");;
					inventoryStockOut.setApprovedBy(inventoryStockOut.getCreatedBy());
					em.getTransaction().begin();
					em1.getTransaction().begin();
					if(inventoryStockOut.getId()>0)
					{
						
						em.merge(inventoryStockOut);
						Query query1 = em1
								.createNativeQuery("delete from im_t_raw_stock_out_items_c  where stock_out_id=? and store_id=?");
						query1.setParameter(1, inventoryStockOut.getId());
						query1.setParameter(2, inventoryStockOut.getStoreId());
						query1.executeUpdate();
						em1.getTransaction().commit();
						List<InventoryStockOutItem> inventoryStockOutItems = inventoryStockOut
								.getInventoryStockOutItems();
						Iterator<InventoryStockOutItem> iterator = inventoryStockOutItems
								.iterator();
						while (iterator.hasNext()) {
							InventoryStockOutItem inventoryStockOutItem = (InventoryStockOutItem) iterator
									.next();
							InventoryStockOut inventryStckOut = new InventoryStockOut();
							inventryStckOut.setId(inventoryStockOut.getId());
							inventoryStockOutItem
									.setInventoryStockOut(inventryStckOut);
							inventoryStockOutItem.setDeleteFlag("N");
							em.persist(inventoryStockOutItem);
						}
						
					}
					else
					{
						em.persist(inventoryStockOut);
						List<InventoryStockOutItem> inventoryStockOutItems = inventoryStockOut
								.getInventoryStockOutItems();
						Iterator<InventoryStockOutItem> iterator = inventoryStockOutItems
								.iterator();
						while (iterator.hasNext()) {
							InventoryStockOutItem inventoryStockOutItem = (InventoryStockOutItem) iterator
									.next();
							InventoryStockOut inventryStckOut = new InventoryStockOut();
							inventryStckOut.setId(inventoryStockOut.getId());
							inventoryStockOutItem
									.setInventoryStockOut(inventryStckOut);
							inventoryStockOutItem.setDeleteFlag("N");
							em.persist(inventoryStockOutItem);
						}
					}
					
					
					
					if(inventoryStockOut.getEdpId()>0)
					{
					try {
						// update stock out date in estimate daily prod
						TypedQuery<EstimateDailyProd> qry = em
								.createQuery("SELECT p FROM EstimateDailyProd p WHERE p.id=:id and p.storeId=:storeid",
								    EstimateDailyProd.class);
						qry.setParameter("storeid",
								inventoryStockOut.getStoreId());
						qry.setParameter("id", inventoryStockOut.getEdpId());
						dailyProd = qry.getSingleResult();
						Date rawStockOutDate = inventoryStockOut.getDate();
						dailyProd.setRawStockOutDate(rawStockOutDate);
						em.merge(dailyProd);
					} catch (Exception e) {
						e.printStackTrace();

					}
					}
					em.getTransaction().commit();
					identifier="success";
			
		} catch (Exception e) {
			identifier="failure";
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}

		return identifier;
	}

	@Override
	public int addVendor(InventoryVendor inventoryVendor) throws DAOException {
		EntityManager em = null;
		int vendorId;
		try {
			vendorId = 0;
			// persist the vendor
			inventoryVendor.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(inventoryVendor);

			em.getTransaction().commit();
			vendorId = inventoryVendor.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		insertDistributorAccount(inventoryVendor);
		
		return vendorId;
	}
	
	@Override
	public int assignPrinter(PrintKotMaster kotMaster) throws DAOException {
		EntityManager em = null;
		int assignedPrinterId = 0;
		PrintKotMaster kotMasterToBeDeleted = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			kotMaster.setDeleteFlag("N");
			try {
				TypedQuery<PrintKotMaster> qry = em
						.createQuery("SELECT t FROM PrintKotMaster t WHERE t.storeId=:storeid and t.cuisineTypeId=:cuisineTypeId",
						    PrintKotMaster.class);
				qry.setParameter("storeid", kotMaster.getStoreId());
				qry.setParameter("cuisineTypeId", kotMaster.getCuisineTypeId());

				kotMasterToBeDeleted = qry.getSingleResult();

				if (kotMasterToBeDeleted != null
						&& kotMasterToBeDeleted.getId() > 0) {

					em.remove(kotMasterToBeDeleted);
				}
				em.getTransaction().commit();

			} catch (Exception e) {
				
				e.printStackTrace();
				em.getTransaction().commit();
				// em.close();
			}
			em.getTransaction().begin();
			em.persist(kotMaster);

			em.getTransaction().commit();
			assignedPrinterId = kotMaster.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}

		return assignedPrinterId;
	}

	@Override
	public int addNewInventoryItem(InventoryItems inventoryItems)
			throws DAOException {
		EntityManager em = null;
		InventoryItems inventoryItem = null;
		int invItemId;
		try {
			invItemId = 0;
			// persist the inventory item
			inventoryItems.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(inventoryItems);

			// em.getTransaction().commit();
			invItemId = inventoryItems.getId();

			// get the item
			TypedQuery<InventoryItems> qry = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.id=:id and i.storeId=:storeid and i.deleteFlag='N'",
					    InventoryItems.class);
			qry.setParameter("id", invItemId);
			qry.setParameter("storeid", inventoryItems.getStoreId());

			inventoryItem = qry.getSingleResult();
			// get code
			if (inventoryItem != null) {
				String code = inventoryItem.getCode();

				if (code == null || code.equalsIgnoreCase("")
						|| code.length() == 0) {
					// generate code
					InventoryType inventoryType = inventoryItem
							.getInventoryType();
					String typeName = inventoryType.getName();
					String subStrng = StringUtils.substring(typeName, 0, 4);
					code = subStrng + "" + invItemId;
					inventoryItem.setCode(code);

				}
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return invItemId;
	}

	@Override
	public List<InventoryVendor> getInventoryVendor(Integer storeId)
			throws DAOException {

		List<InventoryVendor> inventoryVendor = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryVendor> qry = em
					.createQuery("SELECT i FROM InventoryVendor i WHERE i.storeId=:storeid and i.deleteFlag='N'",
					    InventoryVendor.class);
			qry.setParameter("storeid", storeid);
			inventoryVendor = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return inventoryVendor;
	}

	@Override
	public void updateInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException {

		try {
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryVendor inventoryVendor2 = em.find(InventoryVendor.class,
					inventoryVendor.getId());
			inventoryVendor2.setCode(inventoryVendor.getCode());
			inventoryVendor2.setName(inventoryVendor.getName());
			inventoryVendor2.setType(inventoryVendor.getType());
			inventoryVendor2.setDescription(inventoryVendor.getDescription());
			inventoryVendor2.setRating(inventoryVendor.getRating());
			inventoryVendor2.setAccountNumber(inventoryVendor
					.getAccountNumber());
			inventoryVendor2.setContactNo(inventoryVendor.getContactNo());
			inventoryVendor2.setAddress(inventoryVendor.getAddress());
			em.merge(inventoryVendor2);
			em.getTransaction().commit();
			System.out.print("inventory Vendor updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void deleteInventoryItems(InventoryItems inventoryTypeItem)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryItems inventoryTypeItemToDelete = em.find(
					InventoryItems.class, inventoryTypeItem.getId());
			em.remove(inventoryTypeItemToDelete);
			em.getTransaction().commit();
			System.out
					.print("Inventory Type Item  data deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check Inventory Type Item  data", e);
		} finally {
			if (em != null) em.close();
		}

	}

	@Override
	public void deleteInventoryType(InventoryType inventoryType)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryType inventoryTypeToDelete = em.find(InventoryType.class,
					inventoryType.getId());
			em.remove(inventoryTypeToDelete);
			em.getTransaction().commit();
			System.out.print("Inventory Type  data deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check Inventory Type  data", e);
		} finally {
			if (em != null) em.close();
		}

	}

	@Override
	public void deleteInventoryVendor(InventoryVendor inventoryVendor)
			throws DAOException {
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			InventoryVendor inventoryVendorToDelete = em.find(
					InventoryVendor.class, inventoryVendor.getId());
			em.remove(inventoryVendorToDelete);
			em.getTransaction().commit();
			System.out.print("Inventory Vendor  data deleted successfully....");
			// em.close();
		} catch (Exception e) {
			throw new DAOException("Check Inventory Vendor  data", e);
		} finally {
			if (em != null) em.close();
		}

	}

	@Override
	public int addInvntoryType(InventoryType inventoryType) throws DAOException {
		EntityManager em = null;
		int inventoryTypeId;
		try {
			inventoryTypeId = 0;
			// persist the vendor
			inventoryType.setDeleteFlag("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(inventoryType);

			em.getTransaction().commit();
			inventoryTypeId = inventoryType.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return inventoryTypeId;
	}

	@Override
	public String updateInvntryType(InventoryType inventoryType)
			throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update inventory type
			em.merge(inventoryType);
			em.getTransaction().commit();

			messaString = "success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return messaString;
	}

	@Override
	public String deleteInvntryType(Integer id, Integer storeId)
			throws DAOException {

		int invntryTypeId = (id);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryType invntryTypeToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryType> qry = em
					.createQuery("SELECT i FROM InventoryType i WHERE i.storeId=:storeid and i.id=:id",
					    InventoryType.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", invntryTypeId);

			invntryTypeToBeDeleted = qry.getSingleResult();
			invntryTypeToBeDeleted.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}

	@Override
	public String deleteInvntryTypeItem(Integer id, Integer storeId)
			throws DAOException {

		int invntryTypeItemId = (id);
		int storeid = (storeId);
		EntityManager em = null;
		InventoryItems invntryTypeItemToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryItems> qry = em
					.createQuery("SELECT i FROM InventoryItems i WHERE i.storeId=:storeid and i.id=:id",
					    InventoryItems.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", invntryTypeItemId);

			invntryTypeItemToBeDeleted = qry.getSingleResult();
			invntryTypeItemToBeDeleted.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}

	@Override
	public void poPrintInThreeInch(
			List<InventoryPurchaseOrder> inventoryPoOrders) throws DAOException {

		InventoryPurchaseOrder po = inventoryPoOrders.get(0);

		int storeid = po.getStoreId();
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<StoreMaster> qry = em
					.createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid",
					    StoreMaster.class);
			qry.setParameter("storeid", storeid);
			StoreMaster store = qry.getSingleResult();
			String poPrintServerFlag = store.getPoPrintServer();

			if (poPrintServerFlag.equalsIgnoreCase("Y")) {
				new PoPosPrinterMain().a(po, store);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}

	}

	@Override
	public List<RecipeIngredient> getRecipeIngredients(Integer storeId)
			throws DAOException {

		List<RecipeIngredient> recpeIngrdients = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<RecipeIngredient> qry = em
					.createQuery("SELECT i FROM RecipeIngredient i WHERE i.storeId=:storeid and i.deleteFlag='N'",
					    RecipeIngredient.class);
			qry.setParameter("storeid", storeid);
			recpeIngrdients = qry.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return recpeIngrdients;

	}

	@Override
	public List<EstimateType> getEstimateTypes(Integer storeId)
			throws DAOException {

		List<EstimateType> estimateType = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<EstimateType> qry = em
					.createQuery("SELECT i FROM EstimateType i WHERE i.storeId=:storeid and i.deleteFlag='N'",
					    EstimateType.class);
			qry.setParameter("storeid", storeid);
			estimateType = qry.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return estimateType;

	}
	
	//added on 02.05.2018
	@Override
	public List<InventoryInvoicePayment> getCreditInfoByVendor(Integer vendorId,
			Integer storeId) throws DAOException {

		EntityManager em = null;
		boolean isCredit = true;
		List<InventoryInvoicePayment> inventoryStckInLst = null;
		List<InventoryInvoicePayment> inventoryInvoicePayments = null;
		List<InventoryInvoicePayment> invoicePaymentsNoDueList = new ArrayList<InventoryInvoicePayment>();
		List<InventoryInvoicePayment> invoicePaymentsDueList = new ArrayList<InventoryInvoicePayment>();
		List<Integer> stockInIdNoDueLst = new ArrayList<Integer>();
		Set<Integer> stockInTotalIdSet = new HashSet<Integer>();
		Set<Integer> stockInIdNoDueSet = new HashSet<Integer>();

		double billAmt = 0.0;
		try {
			int vendorid = (vendorId);
			int storeid = (storeId);

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryInvoicePayment> qry = em
					.createQuery("SELECT p FROM InventoryInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N'",
					    InventoryInvoicePayment.class);
			qry.setParameter("vendorId", vendorid);
			qry.setParameter("storeid", storeid);
			inventoryStckInLst = qry.getResultList();

			Iterator<InventoryInvoicePayment> iterator = inventoryStckInLst
					.iterator();
			while (iterator.hasNext()) {
				InventoryInvoicePayment inventoryInvoicePayment = iterator.next();
				// add to set to get all separate stock in ids
				stockInTotalIdSet.add(inventoryInvoicePayment.getStockInId());
			}

			// get list of payments with no due
			try {

				TypedQuery<InventoryInvoicePayment> qryNodue = em
						.createQuery("SELECT p FROM InventoryInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N' and p.amountToPay=0.0",
						    InventoryInvoicePayment.class);
				qryNodue.setParameter("vendorId", vendorid);
				qryNodue.setParameter("storeid", storeid);
				invoicePaymentsNoDueList = qryNodue.getResultList();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			if (invoicePaymentsNoDueList.size() > 0) {

				Iterator<InventoryInvoicePayment> iterator1 = invoicePaymentsNoDueList
						.iterator();
				while (iterator1.hasNext()) {
					InventoryInvoicePayment paymentNoDue = (InventoryInvoicePayment) iterator1
							.next();
					stockInIdNoDueLst.add(paymentNoDue.getStockInId());

				}

			}
			// convert no due stockin ids list to set
			if (stockInIdNoDueLst.size() > 0) {
				stockInIdNoDueSet = new HashSet<Integer>(stockInIdNoDueLst);
			}
			// perform a difference between total set and no due set
			if (stockInIdNoDueSet.size() > 0) {
				stockInTotalIdSet.removeAll(stockInIdNoDueSet);
			}
			Set<Integer> stockInIdDueSet = stockInTotalIdSet;
			// stock in ids with due
			if (stockInIdDueSet.size() > 0) {
				Iterator<Integer> iterator3 = stockInIdDueSet.iterator();
				while (iterator3.hasNext()) {
					Integer stockInIdDue = (Integer) iterator3.next();

					Iterator<InventoryInvoicePayment> iterator4 = inventoryStckInLst
							.iterator();
					double paidAmt = 0.0;
					double retAmt = 0.0;
					double paidAmnt = 0.0;
					InventoryInvoicePayment inventoryInvoicePayment1 = null;
					int id = 0;
					int storeId1 = 0;
					int poId = 0;
					int vendorId1 = 0;
					double creditLimit = 0.0;
					String stockInDate = "";
					while (iterator4.hasNext()) {
						inventoryInvoicePayment1 = (InventoryInvoicePayment) iterator4
								.next();
						int stckInId = inventoryInvoicePayment1.getStockInId();

						if (stockInIdDue == stckInId) {
							poId = inventoryInvoicePayment1.getPoId();
							id = inventoryInvoicePayment1.getId();
							vendorId1 = inventoryInvoicePayment1.getVendorId();
							creditLimit = inventoryInvoicePayment1
									.getCreditLimit();
							stockInDate = inventoryInvoicePayment1
									.getStockInDate();
							storeId1 = inventoryInvoicePayment1.getStoreId();
							billAmt = inventoryInvoicePayment1.getBillAmount();
							paidAmnt = inventoryInvoicePayment1.getPaidAmount();
							String isRet = inventoryInvoicePayment1
									.getIsReturn();

							if (isRet.equalsIgnoreCase("N")) {
								paidAmt = paidAmt + paidAmnt;
							}

							else if (isRet.equalsIgnoreCase("Y")) {
								retAmt = retAmt + paidAmnt;
							}

						}

					}
					InventoryInvoicePayment inventoryInvoicePayment2 = new InventoryInvoicePayment();
					inventoryInvoicePayment2.setId(id);
					inventoryInvoicePayment2.setStoreId(storeId1);
					inventoryInvoicePayment2.setPoId(poId);
					inventoryInvoicePayment2.setVendorId(vendorId1);
					inventoryInvoicePayment2.setStockInId(stockInIdDue);
					inventoryInvoicePayment2.setCreditLimit(creditLimit);
					inventoryInvoicePayment2.setBillAmount(billAmt);
					inventoryInvoicePayment2.setPaidAmount(paidAmt);
					inventoryInvoicePayment2.setReturnAmount(retAmt);
					inventoryInvoicePayment2.setAmountToPay(billAmt - paidAmt
							- retAmt);
					inventoryInvoicePayment2.setStockInDate(stockInDate);
					invoicePaymentsDueList.add(inventoryInvoicePayment2);

				}
			} else {
				isCredit = false;
			}

			if (isCredit) {
				inventoryInvoicePayments = invoicePaymentsDueList;
			} else {
				inventoryInvoicePayments = new ArrayList<InventoryInvoicePayment>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check RawStockIn to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return inventoryInvoicePayments;
	}
	
	@Override
	public String invoicePayment(InventoryInvoicePayment payment)
			throws DAOException {
		EntityManager em = null;
		String status = "success";
		try {

			// persist the payment
			payment.setDeleteFlag("N");
			payment.setIsReturn("N");

			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(payment);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		if(("y").equalsIgnoreCase(payment.getIs_account())) {
			VendorPaymentjournalEntry(payment);
		}
		
		return status;
	}
	
	//added on 06.06.2018
	
	@Override
	public List<ReturnTypes> getReturnCauses(Integer storeId)
			throws DAOException {

		List<ReturnTypes> returnTypes = null;
		int storeid = (storeId);
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<ReturnTypes> qry = em
					.createQuery("SELECT r FROM ReturnTypes r WHERE r.storeId=:storeid and r.deleteFlag='N'", 
					    ReturnTypes.class);
			qry.setParameter("storeid", storeid);
			returnTypes = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return returnTypes;
	}
	
	@Override
	public int createSalesReturn(SalesReturn salesReturn) throws DAOException {
		EntityManager em = null;
		int returnId;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			returnId = 0;
			Date currDate = new Date();
			salesReturn.setDate(currDate);
			String dateCurrnt = formatter.format(currDate);

			// persist the sales return
			salesReturn.setApproved("N");
			salesReturn.setIsAdjusted("N");
			salesReturn.setDeleteFlag("N");
			salesReturn.setDate(currDate);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(salesReturn);

			// persist each return item
			List<SalesReturnItem> returnItems = salesReturn
					.getSalesReturnItems();
			Iterator<SalesReturnItem> iterator = returnItems.iterator();
			while (iterator.hasNext()) {
				SalesReturnItem returnItem1 = (SalesReturnItem) iterator.next();
				SalesReturn returnParent = new SalesReturn();
				returnParent.setId(salesReturn.getId());
				returnItem1.setSalesReturn(returnParent);
				returnItem1.setDeleteFlag("N");
				returnItem1.setDate(currDate);
				returnItem1.setCreatedDate(dateCurrnt);
				em.persist(returnItem1);
				//orderIds.add(salesReturn.getOrderMaster().getId());

			}

			// update return status in order
			TypedQuery<OrderMaster> qry = em.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderId",
			    OrderMaster.class);
			qry.setParameter("orderId", salesReturn.getOrderId());
			OrderMaster order = (OrderMaster) qry.getSingleResult();
			order.setRefundStatus("C");
			em.getTransaction().commit();
			returnId = salesReturn.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return returnId;
	}
	
	@Override
	public SalesReturn getSalesReturn(Integer id, Integer storeId)
			throws DAOException {

		SalesReturn salesReturn = null;
		int storeid = (storeId);
		int id1 = (id);

		EntityManager em = null;

		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<SalesReturn> qry = em
					.createQuery("SELECT r FROM SalesReturn r WHERE r.id=:id and r.isAdjusted='N' and r.storeId=:storeid",
					    SalesReturn.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", id1);
			salesReturn = (SalesReturn) qry.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		return salesReturn;

	}
	
	@Override
	public SalesReturn getSalesReturnByOrderId(Integer orderId, Integer storeId)
			throws DAOException {

		SalesReturn salesReturn = null;
		OrderMaster order = null;
		EntityManager em = null;

		try {
			
			em = entityManagerFactory.createEntityManager();
			
			TypedQuery<SalesReturn> qry = em
					.createQuery("SELECT r FROM SalesReturn r WHERE r.orderId=:orderid and r.isAdjusted='N' and r.storeId=:storeid and r.deleteFlag='N'",
					    SalesReturn.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("orderid", orderId);
			salesReturn = qry.getSingleResult();
			TypedQuery<OrderMaster> qryOrder = em
					.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderid and o.storeId=:storeid",
							OrderMaster.class);
			qryOrder.setParameter("storeid", storeId);
			qryOrder.setParameter("orderid", orderId);
			order = qryOrder.getSingleResult();
			salesReturn.setOrderNo(order.getOrderNo());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return salesReturn;

	}
	
	@Override
	public String adjustReturnSales(Integer id, Integer storeId)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		int id1 = (id);
		int storeId1 = (storeId);
		SalesReturn salesReturn = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<SalesReturn> qry = em
					.createQuery("SELECT r FROM SalesReturn r WHERE r.id=:id and r.isAdjusted='N' and r.storeId=:storeid",
					    SalesReturn.class);
			qry.setParameter("storeid", storeId1);
			qry.setParameter("id", id1);

			salesReturn = qry.getSingleResult();
			salesReturn.setIsAdjusted("Y");

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check RawStockIn to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		return status;
	}
	
	@Override
	public String approveSalesReturn(SalesReturn salesReturn1)
			throws DAOException {

		EntityManager em = null;
		String status = "";
		int id1 = salesReturn1.getId();
		int storeId1 = salesReturn1.getStoreId();
		SalesReturn salesReturn = null;
		double tonoofsolditems=0.0;
		double tonoofreturnitems=0.0;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<SalesReturn> qry = em
					.createQuery("SELECT r FROM SalesReturn r WHERE r.id=:id and r.approved='N' and r.storeId=:storeid",
					    SalesReturn.class);
			qry.setParameter("storeid", storeId1);
			qry.setParameter("id", id1);
			salesReturn = qry.getSingleResult();
			salesReturn.setApproved("Y");
			List<SalesReturnItem> lstreturnItems = salesReturn.getSalesReturnItems();
			Iterator<SalesReturnItem> returniterator = lstreturnItems.iterator();
			while (returniterator.hasNext()) {
				SalesReturnItem salesReturnItem = (SalesReturnItem) returniterator.next();
				tonoofreturnitems+=salesReturnItem.getItemReturnQuantity();
			}
			// update return status in order
			TypedQuery<OrderMaster> qryOrder = em.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderId",
			    OrderMaster.class);
			qryOrder.setParameter("orderId", salesReturn.getOrderId());
			OrderMaster order = qryOrder.getSingleResult();
			List<OrderItem> lstsaleItems = order.getOrderitem();
			Iterator<OrderItem> saleiterator = lstsaleItems.iterator();
			while (saleiterator.hasNext()) {
				OrderItem salesItem = (OrderItem) saleiterator.next();
				tonoofsolditems+=Double.parseDouble(salesItem.getQuantityOfItem());
			}
			if(tonoofsolditems==tonoofreturnitems)
			{
				order.setRefundStatus("F");//for full refund 'F' for partial refund 'P'
			}else
			{
				order.setRefundStatus("P");//for full refund 'F' for partial refund 'P'
			}
			
			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check approve sale return to be updated", e);
		} finally {
			if (em != null) em.close();
		}

		if(("y").equalsIgnoreCase(salesReturn1.getIs_account())) {
			saleReturnjournalEntry(salesReturn1);
		}
		
		return status;
	}
	
	@Override
	public String deleteSalesReturn(Integer returnId,  Integer storeId) throws DAOException {

		EntityManager em = null;
		SalesReturn salesReturn=null;
		String message = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			salesReturn = em.find(SalesReturn.class, returnId);
			salesReturn.setDeleteFlag("Y");
			List<SalesReturnItem> lstreturnItems = salesReturn.getSalesReturnItems();
			Iterator<SalesReturnItem> returniterator = lstreturnItems.iterator();
			while (returniterator.hasNext()) {
				SalesReturnItem salesReturnItem = (SalesReturnItem) returniterator.next();
				salesReturnItem.setDeleteFlag("Y");
			}
			
			TypedQuery<OrderMaster> qryOrder = em.createQuery("SELECT o FROM OrderMaster o WHERE o.id=:orderId",
			    OrderMaster.class);
			qryOrder.setParameter("orderId", salesReturn.getOrderId());
			OrderMaster order = qryOrder.getSingleResult();
			order.setRefundStatus("N");
			em.getTransaction().commit();
			message = "success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted in sale return ", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}

  @Override
  public String printRefundBill(String orderid, Integer storeid) throws DAOException {

    EntityManager em = null;
    int orderId = Integer.parseInt(orderid);
    int storeId = (storeid);
    String status = "";
    OrderMaster order = null;
    double totalNonVatItemOrderAmt = 0.0;
    double totalVatItemOrderAmt = 0.0;
    double totalVatAmt = 0.0;
    double totalServceTaxAmt = 0.0;

    String createdBy = null;

    BillingDAO billDao = new BillingDAOImpl();

    try {
      System.out.println("Print refund started in DAO...");
      
      em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();

      TypedQuery<OrderMaster> qry = em
          .createQuery("SELECT c FROM OrderMaster c WHERE c.id=:orderid and c.storeId=:storeId",
              OrderMaster.class);
      qry.setParameter("orderid", orderId);
      qry.setParameter("storeId", storeId);
      order = (OrderMaster) qry.getSingleResult();

      int orderId1 = order.getId();
      int storeId1 = order.getStoreId();

      TypedQuery<Payment> qry2 = em
          .createQuery("SELECT p FROM Payment p WHERE p.orderPayment.id=:orderId1 and p.paidAmount!=0.0 and p.storeId=:storeId1",
              Payment.class);
      qry2.setParameter("orderId1", orderId1);
      qry2.setParameter("storeId1", storeId1);
      List<Payment> paymentList = qry2.getResultList();
      
      createdBy = paymentList.size() > 0 ? paymentList.get(0).getCreatedBy() : createdBy;
      
      TypedQuery<StoreMaster> qrySM = em
          .createQuery("SELECT s FROM StoreMaster s WHERE s.status='Y' AND s.activeFlag='YES' AND s.id=:storeid",
              StoreMaster.class);
      qrySM.setParameter("storeid", storeId);
      StoreMaster store = (StoreMaster) qrySM.getSingleResult();
      String billPrintServerFlag = store.getBillPrint();
      String roundOffStatus = store.getRoundOffTotalAmtStatus();
      String roundOffDouble = store.getDoubleRoundOff();

      // 14.5% items

      // String billPrintMobFlag = store.getMobBillPrint();

      /****************
       * start bill print***********************
       * 
       */

      // if kitchen admin print is true

      if ("Y".equalsIgnoreCase(billPrintServerFlag)) {
        // int storeId = order.getStoreId();
//        String s = "\\u0020"; // for space
        // store name
        String strName = store.getStoreName();
        // store address
        String address = store.getAddress();
        StringBuffer emailId = new StringBuffer();
        if (store.getEmailId() != null
            && !store.getEmailId().trim().equalsIgnoreCase("")) {
          emailId.append("Email:");
          emailId.append(store.getEmailId());
        }
        StringBuffer phoneNumbr = new StringBuffer();
        phoneNumbr.append("Ph:");
        // get phone number
        String phone = store.getPhoneNo();
        phoneNumbr.append(phone);
        StringBuffer vatReg = new StringBuffer();
        StringBuffer gstReg = new StringBuffer();
        StringBuffer serviceTax = new StringBuffer();
        String paxString="";
        if (store.getGstRegNo() != null) { // check GST / VAT, STAX
          if (store.getGstRegNo().trim().length() > 0) {
            gstReg.append(store.getGstText() + ": ");
            gstReg.append(store.getGstRegNo());
          }
        } else {
          if (store.getVatTaxText() == null
              || "".equalsIgnoreCase(store.getVatTaxText().trim())) {
            vatReg.append("Vat" + "  Reg. No: ");
          } else {
            vatReg.append(store.getVatTaxText() + "  Reg. No: ");
          }
          // get vat reg no.
          String vatRegNo = store.getVatRegNo();
          vatReg.append(vatRegNo);

          if (storeId == 120)
            serviceTax.append("S.Tax No: ");
          else {
            serviceTax.append(store.getServiceTaxText()
                + " Tax No: ");
          }
          // get service tax no.
          String serviceTaxNo = store.getServiceTaxNo();
          serviceTax.append(serviceTaxNo);
        }
        StringBuffer invoice = new StringBuffer();
        if (storeId == 120)
          invoice.append("Invoice No:  ");
        else {
          invoice.append("Order No:  ");
        }
        //invoice.append(orderId + "");
        invoice.append(order.getOrderNo() + "");//after incorporating order_no
        if ("Y".equalsIgnoreCase(store.getIsPax())) {
          //invoice.append("     " + "Pax(s): "+order.getNoOfPersons());
          paxString="     Pax(s): "+order.getNoOfPersons();
          //int noOfPersons = order.getNoOfPersons();
          //invoice.append(noOfPersons + "");
        }

        // 14.5 and 20 % tax calculate for elan
        StringBuffer dateTable = new StringBuffer();
        dateTable.append("Date: ");

        // formatted
        String orderDate = order.getOrderTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = sdf.parse(orderDate);
        sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
        orderDate = sdf.format(date);

        dateTable.append(orderDate);
        // add cashier
        Customer loggedCustomer1 = order.getCustomers();
        StringBuffer cashierString = new StringBuffer();
        cashierString.append("Cashier: ");
        cashierString.append(loggedCustomer1.getContactNo());
        
        // Add server name
        StringBuffer serverString = new StringBuffer();
        serverString.append("Server: ");
        serverString.append(loggedCustomer1.getContactNo());
        

        StringBuffer tableNoStrng = new StringBuffer();
        String tableNo = order.getTable_no();
        
        
        if (tableNo != null && tableNo.length() > 0 && !tableNo.equalsIgnoreCase("0")) {
          tableNoStrng.append("Table No. ");
          tableNoStrng.append(tableNo);
          invoice.append(paxString);
        } else {
          tableNoStrng.append(order.getOrdertype().getOrderTypeName());
        }

//        String kitchenPrnt = "";
        String kot = "         KOT";
        StringBuffer kitchenHdrStrng = new StringBuffer();
        StringBuffer kitchenItemStrng = new StringBuffer();
        StringBuffer kitchenEndStrng = new StringBuffer();
        // StringBuffer itemNameStrng = new StringBuffer();
//        String finalItemName = "";
//        String upTo3Characters = strName.substring(0, Math.min(strName.length(), 3));
        kitchenHdrStrng.append(kot);
        kitchenHdrStrng.append("\n");
        kitchenHdrStrng.append(strName); // 1st string
        kitchenHdrStrng.append("\n");
        kitchenHdrStrng.append("\n");
        // get order time
        String ordeTime = order.getOrderTime();

        String[] tempURL = ordeTime.split("/");
        String day = tempURL[0];
        String mnth = tempURL[1];
        String dayMnth = day + "/" + mnth;
        String[] tempURL1 = ordeTime.split(" ");
        String time = tempURL1[1];
        System.out.println("dayMnth.." + dayMnth);
        System.out.println("time.." + time);
        String[] timefull = time.split(":");
        String hr = timefull[0];
        String mins = timefull[1];
        String hrMins = hr + ":" + mins;
        System.out.println("hr mins:::  " + hrMins);
        String dtTime = dayMnth + " " + hrMins;
        System.out.println("dttime:: " + dtTime);
        StringBuffer secndString = new StringBuffer();
        secndString.append(dtTime); // 2nd string start
        secndString.append(" ");
        // get order taken by person info
        Customer customer = order.getCustomers();
        int custId = customer.getId();
        secndString.append("(");
        if (custId > 0) {
          secndString.append("" + custId);
        }
        secndString.append(")"); // 2nd string end
        kitchenHdrStrng.append("\n");
        StringBuffer thrdString = new StringBuffer();
        thrdString.append("Order No:"); // 3rd string start
        //thrdString.append(orderId);
        thrdString.append(order.getOrderNo());//after incorporating order_no
        thrdString.append(",");
        thrdString.append("Table-");
        // get table no.
        String tblNo = order.getTable_no();
        if (tblNo != null && tblNo.length() > 0) {
          thrdString.append(tblNo);
        } else {
          thrdString.append("");
        }
        

        kitchenHdrStrng.append("\n\n");
        // declare a map to hold item name, item quantity
        // get items from order
        Map<Integer, List<String>> mapToHoldItems = new LinkedHashMap<Integer, List<String>>();
        List<OrderItem> orderItemLst = order.getOrderitem();
        Iterator<OrderItem> ordrItr = orderItemLst.iterator();
        while (ordrItr.hasNext()) {
          OrderItem orderItem = (OrderItem) ordrItr.next();
          // get the special note
          String specialNote = orderItem.getSpecialNote();
          MenuItem menuItem = orderItem.getItem();
          String itemName = menuItem.getName();
          int itemId = menuItem.getId();
          StringBuffer nameNSpecl = new StringBuffer();
          nameNSpecl.append(itemName);
          nameNSpecl.append("\n" + "##");
          nameNSpecl.append(specialNote);
//          
          String quantity = orderItem.getQuantityOfItem();
          double totalPriceByItem = orderItem.getTotalPriceByItem();

          DecimalFormat decim = new DecimalFormat("00.00");
          String totalPriceItem = decim.format(totalPriceByItem);
          double rate = orderItem.getRate();
          String itemRate = decim.format(rate);

          List<String> itemLst = new ArrayList<String>();
          itemLst.add(0, itemName);
          itemLst.add(1, quantity);
          itemLst.add(2, totalPriceItem);
          // add rate
          itemLst.add(3, itemRate);

          List<String> itemLstOld = mapToHoldItems.get(itemId);

          if (itemLstOld != null && itemLstOld.size() > 0) {
            itemLst.set(1, new Integer(itemLstOld.get(1))
                + new Integer(itemLst.get(1)) + "");

            double total = new Double(itemLstOld.get(2))
                + new Double(itemLst.get(2));
            DecimalFormat dec = new DecimalFormat("00.00");
            String totalPrice = dec.format(total);
            itemLst.set(2, totalPrice);

          }

          mapToHoldItems.put(itemId, itemLst);

          // calculate non Vat item order amount
          double vatForItem = menuItem.getVat();
//          
          Double itemDiscPer = new Double(
              menuItem.getPromotionValue());
          Double itemDisc = (itemDiscPer * totalPriceByItem) / 100;
          Double itemServiceTax = menuItem.getServiceTax();
          Double serviceTaxForThsItem = (itemServiceTax * (totalPriceByItem - itemDisc)) / 100;
          
          if (order.getTable_no().trim().equalsIgnoreCase("0")) {
            if (store.getParcelServiceTax().equalsIgnoreCase("N")) {
              serviceTaxForThsItem = 0.0;
            }
          }
          
          totalServceTaxAmt = totalServceTaxAmt + serviceTaxForThsItem;

          if (vatForItem <= 0.0) {
            totalNonVatItemOrderAmt = totalNonVatItemOrderAmt + totalPriceByItem;
          }
          else if (vatForItem > 0.0) {
            totalVatItemOrderAmt = totalVatItemOrderAmt + totalPriceByItem;
            double vatForThsItem = (vatForItem * (totalPriceByItem - itemDisc)) / 100;
            
            if (order.getTable_no().trim().equalsIgnoreCase("0")) {
              if (store.getParcelVat().equalsIgnoreCase("N")) {
                vatForThsItem = 0.0;
              }
            }
            totalVatAmt = totalVatAmt + vatForThsItem;
          }

          // difference vat calculation for elan siliguri

          itemName = String.format("%-35s:%s", itemName, quantity);
          kitchenItemStrng.append(itemName);
          kitchenItemStrng.append("\n");
        }

        // create a list to hold the non vat item order amount
        DecimalFormat decim = new DecimalFormat("00.00");
        String totalNonVatItemOrdrAmt = decim
            .format(totalNonVatItemOrderAmt);
        List<String> nonVatItemOrdrAmt = new ArrayList<String>();

        if (store.getVatTaxText().trim().length() == 0) {
          nonVatItemOrdrAmt.add(0, "Item Order Amt");
        } else {
          if("CGST".equalsIgnoreCase(store.getVatTaxText()))
          {
            nonVatItemOrdrAmt.add(0, "Non " + "GST" +" Amt");
          }
          else
          {
            nonVatItemOrdrAmt.add(0, "Non " + store.getVatTaxText() +" Amt");
          }
          // nonVatItemOrdrAmt.add(0, "Non " + store.getVatTaxText() +
          // " Item Order Amt");
          //nonVatItemOrdrAmt.add(0, "Item Order Amt");
        }
        nonVatItemOrdrAmt.add(1, totalNonVatItemOrdrAmt);

        // create a list to hold the vat item order amount
        List<String> vatItemOrdrAmt = new ArrayList<String>();
        DecimalFormat decim1 = new DecimalFormat("00.00");
        String totalVatItemOrdrAmt = decim1
            .format(totalVatItemOrderAmt);

        // vatItemOrdrAmt.add(0, store.getVatTaxText() +
        // " Item Order Amt");
        vatItemOrdrAmt.add(0, "Item Order Amt");
        vatItemOrdrAmt.add(1, totalVatItemOrdrAmt);

        // create a list to hold the total 14.5% vat amount

        // create a list to hold the total 14.5% vat amount

        // create a list to hold the total order amount
        double totalOrderAmt = totalNonVatItemOrderAmt
            + totalVatItemOrderAmt;
        List<String> totalAmt = new ArrayList<String>();
        DecimalFormat decim2 = new DecimalFormat("00.00");
        String totalOrderAmtStr = decim2.format(totalOrderAmt);
        if (store.getId() == 37 || store.getId() == 38) {
          totalAmt.add(0, "Sub Total Amt");
        } else {
          totalAmt.add(0, "Total Order Amt");
        }
        totalAmt.add(1, totalOrderAmtStr);

        // create a list to hold the vat
        List<String> vatAmtLst = new ArrayList<String>();

        if (store.getVatAmt() == 0.0) {

          vatAmtLst.add(0, store.getVatTaxText());
        } else if (store.getVatAmt() > 0.0) {
          vatAmtLst.add(0,
              store.getVatTaxText() + " @" + store.getVatAmt()
                  + "%");
        }
        // double roundOffVat = Math.round(totalVatAmt * 100.0) / 100.0;
        DecimalFormat decim3 = new DecimalFormat("00.00");
        // newly added
        totalVatAmt = order.getOrderBill().getVatAmt();
        String roundOffVatStr = decim3.format(totalVatAmt);
        vatAmtLst.add(1, roundOffVatStr);

        
        List<String> servcTaxLst = new ArrayList<String>();
        // double roundOffStax = Math.round(totalServceTaxAmt * 100.0) /
        // 100.0;
        DecimalFormat decim4 = new DecimalFormat("00.00");
        // newly added
        totalServceTaxAmt = order.getOrderBill().getServiceTaxAmt();
        String roundOffStaxStr = decim4.format(totalServceTaxAmt);
        if (store.getServiceTaxAmt() == 0.0) {
          servcTaxLst.add(0, store.getServiceTaxText());
        } else if (store.getServiceTaxAmt() > 0.0) {

          servcTaxLst.add(
              0,
              store.getServiceTaxText() + " @"
                  + store.getServiceTaxAmt() + "%");

        }
        servcTaxLst.add(1, roundOffStaxStr);

        // create a list to hold the service charge
        double servcCharge = order.getOrderBill().getServiceChargeAmt();
        double servcChargeRate = order.getOrderBill().getServiceChargeRate();
        List<String> servcChrgLst = new ArrayList<String>();
        decim4 = new DecimalFormat("00.00");

        String roundOffServcChargeStr = decim4.format(servcCharge);
        if (store.getServiceChargeRate() == 0.0) {
          servcChrgLst.add(0, store.getServiceChargeText());
        } else if (store.getServiceChargeRate() > 0.0) {

          servcChrgLst.add(0, store.getServiceChargeText() + " @"
              + servcChargeRate + "%");

        }
        servcChrgLst.add(1, roundOffServcChargeStr);

        // create a list to hold the discount
        Double totalDiscnt = 0.0;
        Bill bill = billDao.getBillByOrderId(orderid);
        Double custDiscount = bill.getCustomerDiscount();
        Double itemsDiscount = bill.getTotalDiscount();
        

        List<String> itemDiscntLst = new ArrayList<String>();
        String itemDscntStr = decim4
            .format(itemsDiscount.doubleValue());
        itemDiscntLst.add(0, "-Items Disc.");
        itemDiscntLst.add(1, itemDscntStr);

        List<String> custDiscntLst = new ArrayList<String>();
        String custDscntStr = decim4.format(custDiscount.doubleValue());
        double custDiscPercntage = order.getOrderBill()
            .getDiscountPercentage();
        custDiscntLst.add(
            0,
            "-Customer Disc." + "("
                + Double.toString(custDiscPercntage) + "%)");
        custDiscntLst.add(1, custDscntStr);

        // create a list to hold the gross bill
        List<String> grossLst = new ArrayList<String>();
        double gross = 0.0;
        double dscnt = 0.0;
        // calculate gross
        // if (discount != null & itemDiscount != null)
        {
          dscnt = totalDiscnt;

        }

        if (store.getId() == 35
            && order.getTable_no().trim().equalsIgnoreCase("0")) { // for
                                        // marufaz
          totalServceTaxAmt = 0.0;
        }

        System.out.println("totalOrderAmt   " + totalOrderAmt
            + " totalVatAmt " + totalVatAmt + " totalServceTaxAmt "
            + totalServceTaxAmt + " dscnt " + dscnt);

        String formattedTotalVatStr = decim3.format(totalVatAmt);
        totalVatAmt = Double.parseDouble(formattedTotalVatStr);
        // service charge added to gross
        gross = (totalOrderAmt + totalVatAmt + totalServceTaxAmt + servcCharge)
            - dscnt;

        if (roundOffStatus != null
            && !roundOffStatus.equalsIgnoreCase("")
            && roundOffStatus.equalsIgnoreCase("Y")) {
          gross = new Double(Math.round(gross)); // round
        } else if (roundOffDouble != null
            && !roundOffDouble.equalsIgnoreCase("")
            && roundOffDouble.equalsIgnoreCase("Y")) {
          String formvl = decim4.format(gross);
          gross = Double.parseDouble(formvl);
          gross = new Double(
              CommonProerties.roundOffUptoTwoPlacesDouble(gross,
                  1)); // round
          // off
          // double
        }

        String roundOffGrossStr = decim4.format(gross);
        
        grossLst.add(0, "TOTAL");
        grossLst.add(1, roundOffGrossStr);

        // create a list to hold the paid amount
        List<String> paidAmtLst = new ArrayList<String>();

        double amount = 0.0;
        double paidAmount = 0.0;
        double amtToPay = 0.0;
        double tenderAmt = 0.0;
        double refundAmt = 0.0;
        //String refundAmtUptoTwoDecimal = "";

        Set<String> pTypes = new TreeSet<String>();
        String paymentType = "";

        try {
          List<Payment> paymentLst = billDao
              .getPaymentInfoByOrderId(orderid);
          Iterator<Payment> iterator = paymentLst.iterator();
          while (iterator.hasNext()) {
            Payment payment = (Payment) iterator.next();
            amount = payment.getAmount();
            paidAmount = paidAmount + payment.getPaidAmount();
            tenderAmt = tenderAmt + payment.getTenderAmount();
            if (payment.getPaymentMode() != null)
              pTypes.add(payment.getPaymentMode());

            if (payment.getPaymentMode() != null
                && payment.getPaymentMode().equalsIgnoreCase(
                    "cash")) {
              refundAmt = tenderAmt - paidAmount;
              if (refundAmt < 0) {
                refundAmt = 0.00;
                //DecimalFormat df = new DecimalFormat("00.00");
                //refundAmtUptoTwoDecimal = df.format(refundAmt);
              } else if (refundAmt > 0) {
                //DecimalFormat df = new DecimalFormat("00.00");
                //refundAmtUptoTwoDecimal = df.format(refundAmt);
              }

            }
          }

          if (pTypes.size() == 1) {
            paymentType = pTypes.toArray()[0].toString();
          }
          if (pTypes.size() == 2) {
            paymentType = pTypes.toArray()[0].toString() + " and "
                + pTypes.toArray()[1].toString();
          }

          amtToPay = amount - paidAmount;
        } catch (Exception e1) {
          
          e1.printStackTrace();
        }

        String paidAmountToTwoDecimalPlaces = decim4.format(paidAmount);
        paidAmtLst.add(0, "Paid Amount");
        paidAmtLst.add(1, paidAmountToTwoDecimalPlaces);

        // create a list to hold the amount to pay
        List<String> amtToPayLst = new ArrayList<String>();

        String amtToPayToTwoDecimalPlaces = decim4.format(amtToPay);
        amtToPayLst.add(0, "Amount to Pay");
        amtToPayLst.add(1, amtToPayToTwoDecimalPlaces);

        // create a list to hold the tender amount
        List<String> tenderAmtLst = new ArrayList<String>();

        String tenderAmtToTwoDecimalPlaces = decim4.format(tenderAmt);
        tenderAmtLst.add(0, "Tender Amount");
        tenderAmtLst.add(1, tenderAmtToTwoDecimalPlaces);

        // create a list to hold payment types
        List<String> paymntTypeLst = new ArrayList<String>();
        paymntTypeLst.add(0, "Payment Type");
        paymntTypeLst.add(1, paymentType);

        // create a list to hold refund amount
        List<String> refundAmtLst = new ArrayList<String>();
        refundAmtLst.add(0, "Refund Amount");
        //refundAmtLst.add(1, refundAmtUptoTwoDecimal);
        refundAmtLst.add(1, paidAmountToTwoDecimalPlaces);

        kitchenEndStrng.append("..........................");

        

        Customer loggedCustomer = order.getCustomers();
        String userId = loggedCustomer.getName();
        if (createdBy != null) {
          if (createdBy.trim().length() > 0)
            userId = createdBy;
        } else {
          createdBy = userId;
        }
        // for home delivery
        String homeDeliveryCustName = "";
        String homeDeliveryCustAddr = "";
        String homeDeliveryCustPh = "";
        String homeDeliveryPersonName = "";
        String homeDeliveryLocation = "";
        String homeDeliveryStreet = "";
        String homeDeliveryHouseNo = "";
        String custVatRegNo = "";
        /** New Fields */
//        String carNo = "";
//        Date anniversary_date = new Date();
//        Date dob = new Date();

        if (order.getTable_no().equalsIgnoreCase("0")
            && store.getParcelAddress().equalsIgnoreCase("Y")) {
          homeDeliveryCustName = order.getCustomerName();
          homeDeliveryCustAddr = order.getDeliveryAddress();
          homeDeliveryCustPh = order.getCustomerContact();
          homeDeliveryPersonName = order.getDeliveryPersonName();
          homeDeliveryLocation = order.getLocation();
          homeDeliveryStreet = order.getStreet();
          homeDeliveryHouseNo = order.getHouseNo();
          custVatRegNo = order.getCustVatRegNo();
        }
        
        // print waiter name
        String waiterName=null;
        waiterName=order.getWaiterName();
        /*
         * new SilentPrintBill().printByRequest(orientation, orderId +
         * "", kitchenPrnt, request);
         */
        Object[] arryToHoldElems = new Object[50];
        // arryToHoldElems[0] = kot;
        arryToHoldElems[0] = strName;
        arryToHoldElems[1] = address;
        arryToHoldElems[2] = emailId.toString();
        arryToHoldElems[3] = phoneNumbr.toString();
        arryToHoldElems[4] = vatReg.toString();
        arryToHoldElems[5] = serviceTax.toString();
        arryToHoldElems[6] = invoice.toString();
        arryToHoldElems[7] = dateTable.toString();
        arryToHoldElems[8] = mapToHoldItems;
        arryToHoldElems[9] = nonVatItemOrdrAmt;
        arryToHoldElems[10] = vatItemOrdrAmt;
        arryToHoldElems[11] = totalAmt;
        arryToHoldElems[12] = vatAmtLst;
        arryToHoldElems[13] = servcTaxLst;
        arryToHoldElems[14] = itemDiscntLst;
        arryToHoldElems[15] = grossLst;
        arryToHoldElems[16] = tableNoStrng.toString();
        arryToHoldElems[17] = paidAmtLst;
        arryToHoldElems[18] = amtToPayLst;
        arryToHoldElems[19] = custDiscntLst;
        arryToHoldElems[20] = userId;
        arryToHoldElems[21] = tenderAmtLst;
        arryToHoldElems[22] = paymntTypeLst;
        arryToHoldElems[23] = refundAmtLst;
        arryToHoldElems[24] = homeDeliveryCustName;
        arryToHoldElems[25] = homeDeliveryCustAddr;
        arryToHoldElems[26] = homeDeliveryCustPh;
        arryToHoldElems[27] = homeDeliveryPersonName;
        arryToHoldElems[28] = tblNo;
        arryToHoldElems[29] = store.getBillFooterText();
        arryToHoldElems[30] = store.getThanksLine1();
        arryToHoldElems[31] = store.getThanksLine2();
        arryToHoldElems[32] = order.getOrderBill().getRoundOffAmt();
        arryToHoldElems[33] = cashierString.toString();
        arryToHoldElems[34] = serverString.toString();
        arryToHoldElems[35] = servcChrgLst;
        arryToHoldElems[36] = custVatRegNo;
        arryToHoldElems[37] = homeDeliveryLocation;
        arryToHoldElems[38] = homeDeliveryStreet;
        arryToHoldElems[39] = homeDeliveryHouseNo;
        arryToHoldElems[40] = gstReg.toString();
        arryToHoldElems[41] = storeId;
        arryToHoldElems[42] = waiterName;

        try { // to print
            // if (billPrintServerFlag.equalsIgnoreCase("Y")) {

          new RefundBillPosPrinterMain().a(arryToHoldElems, storeId);
          //updateBillPrint(orderId);

        } catch (Exception e) {
          
          e.printStackTrace();
        }
        System.out.println("Refund Print end in DAO...");
      }
      status = "Success";
    } catch (Exception e) {
      e.printStackTrace();
      status = "Failure";
      throw new DAOException("Error occurred", e);
    } finally {
      if (em != null) em.close();
    }

    return status;
  }
	
	
	@Override
	public List<AccountTypeDTO> getAllAccountType(CommonResultSetMapper mapper)
			throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountTypeDTO> accountTypeDTOs=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ALL_ACCOUNT_TYPE);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			//ReflectionResultSetMapper<AccountTypeDTO> resultSetMapper1 = new ReflectionResultSetMapper<AccountTypeDTO>(AccountTypeDTO.class);

			while (rs.next()) {
				AccountTypeDTO accountTypeDTO=new AccountTypeDTO();
				accountTypeDTO.setId(rs.getInt(1));
				accountTypeDTO.setTypeName(rs.getString(2));
				//accountTypeDTO = resultSetMapper1.mapRow(rs);
				accountTypeDTOs.add(accountTypeDTO);

			}

			//logger.info("getAllAccountType fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				rs.close();
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}

		return accountTypeDTOs;
	}
	
	@Override
	public List<AccountGroupDTO> getAllAccountGroup(CommonResultSetMapper mapper)
			throws DAOException {

		System.out.println("getAllAccountGroup mapper = "+mapper);
		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountGroupDTO> accountGrpDTOs=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ALL_ACCOUNT_GRP);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setString(3, mapper.getAccntGrpName());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<AccountGroupDTO> resultSetMapper1 = new ReflectionResultSetMapper<AccountGroupDTO>(
					AccountGroupDTO.class);

			while (rs.next()) {
				AccountGroupDTO accountGRPDTO=new AccountGroupDTO();
				accountGRPDTO = resultSetMapper1.mapRow(rs);
				accountGrpDTOs.add(accountGRPDTO);

			}

//			logger.info("all accnt grps fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				rs.close();
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}

		System.out.println("getAllAccountGroup result accountGrpDTOs = "+accountGrpDTOs);
		return accountGrpDTOs;
	}
	
	@Override
	public ResponseObj updateAccountGroup(AccountGroupDTO accountGroupDTO)
			throws DAOException {
		
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_UPDATE_ACCOUNT_GROUP);
				callableStatement.setInt(1, accountGroupDTO.getId());
				callableStatement.setString(2, accountGroupDTO.getName());
				callableStatement.setString(3, accountGroupDTO.getCode());
				callableStatement.setString(4, accountGroupDTO.getDescription());
				callableStatement.setInt(5, accountGroupDTO.getAccountTypeId());
				callableStatement.setInt(6, accountGroupDTO.getCompanyId());
				callableStatement.setInt(7, accountGroupDTO.getStoreId());
				callableStatement.setInt(8, accountGroupDTO.getCreatedBy());
				
				
				
				callableStatement.registerOutParameter(9,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(9);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public ResponseObj deleteAccountGroup(CommonResultSetMapper mapper)
			throws DAOException {
		EntityManager em = null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		int result = 0;
		ResponseObj responseObj=new ResponseObj();
		try {
			int accgrpid = mapper.getId();
			int storeId = mapper.getStoreId();
			int cmpnyId = mapper.getCompanyId();

			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_DELETE_ACCOUNT_GROUP);
			callableStatement.setInt(1, cmpnyId);
			callableStatement.setInt(2, storeId);
			callableStatement.setInt(3, accgrpid);

			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

			callableStatement.execute();
			result = callableStatement.getInt(4);
			
			if (result >0) {
				
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(result);
				responseObj.setReason("account group deleted successfully.");
				
			} else {
				
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(result);
				responseObj.setReason("account group not deleted successfully.");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("distributor not deleted successfully.");
			throw new DAOException("Check data to be deleted", e);
		}

		finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public ResponseObj createAccountGroup(AccountGroupDTO accountGroupDTO)
			throws DAOException {
		
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_CREATE_ACCOUNT_GROUP);
				callableStatement.setString(1, accountGroupDTO.getName());
				callableStatement.setString(2, accountGroupDTO.getCode());
				callableStatement.setString(3, accountGroupDTO.getDescription());
				callableStatement.setInt(4, accountGroupDTO.getAccountTypeId());
				callableStatement.setInt(5, accountGroupDTO.getCompanyId());
				callableStatement.setInt(6, accountGroupDTO.getStoreId());
				callableStatement.setInt(7, accountGroupDTO.getCreatedBy());
				
				
				
				callableStatement.registerOutParameter(8,
						java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(9,
						java.sql.Types.VARCHAR);

				callableStatement.execute();
				String code = callableStatement.getString(9);
				status = callableStatement.getInt(8);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setCode(code);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setCode(code);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	

	//23.02.2018
	@Override
	public List<ParaJournalTypeMaster> getJournalTypeByQS(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<ParaJournalTypeMaster> pjtms=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_JOURNALTYPE_BYQS);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setString(3, mapper.getQs());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<ParaJournalTypeMaster> resultSetMapper1 = new ReflectionResultSetMapper<ParaJournalTypeMaster>(
					ParaJournalTypeMaster.class);

			while (rs.next()) {
				ParaJournalTypeMaster pjtm=new ParaJournalTypeMaster();
				pjtm = resultSetMapper1.mapRow(rs);
				pjtms.add(pjtm);

			}

			//logger.info("ParaJournalTypeMaster fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		System.out.println("ParaJournalTypeMaster = "+pjtms);
		return pjtms;
	}

	
	@Override
	public List<AccountDTO> getAllAccounts(CommonResultSetMapper mapper) throws DAOException {
		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountDTO> accnts=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ALL_ACCOUNTS);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setInt(3, mapper.getFinYrId());
			callableStatement.setInt(4, mapper.getId());
			callableStatement.setString(5, mapper.getName());
			if (mapper.getAsOnDate()!=null) {
				java.sql.Date asonDate = DateUtil.convertStringDateToSqlDate(
				mapper.getAsOnDate(), "yyyy-MM-dd");
				callableStatement.setDate(6, asonDate);
			}
			else {
				callableStatement.setDate(6, null);
			}
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<AccountDTO> resultSetMapper1 = new ReflectionResultSetMapper<AccountDTO>(
					AccountDTO.class);

			while (rs.next()) {
				AccountDTO accnt=new AccountDTO();
				accnt = resultSetMapper1.mapRow(rs);
				accnts.add(accnt);

			}

			//logger.info("accnts fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				rs.close();
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}

		return accnts;
	}
	
	@Override
	public ResponseObj createAccount(AccountMaster master)
			throws DAOException {
		
		System.out.println("createAccount AccountMaster = "+master);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_CREATE_ACCOUNT);
				callableStatement.setString(1, master.getName());
				callableStatement.setString(2, master.getCode());
				callableStatement.setString(4, master.getGroup_code());
				callableStatement.setInt(3, master.getGroupId());
				//callableStatement.setInt(3, 0);
				callableStatement.setInt(5, master.getCityId());
				callableStatement.setInt(6, master.getZoneId());
				callableStatement.setInt(7, master.getAreaId());
				callableStatement.setString(8, master.getAddress());
				if(master.getPin()==null)
				{
					//if(master.getPin().equals(""))
						callableStatement.setString(9, "0");
				}
				else
					callableStatement.setString(9, master.getPin());
				
				callableStatement.setString(10, master.getPhone());
				callableStatement.setString(11, master.getEmail());
				callableStatement.setString(12, master.getPanNo());
				callableStatement.setString(13, master.getGstRegistrationNo());
				callableStatement.setString(14, master.getBcdaRegistrationNo());
				callableStatement.setString(15, master.getDlNo());
				callableStatement.setDouble(16, master.getCashDiscountPercentage());
				callableStatement.setDouble(17, master.getTransLimit());
				callableStatement.setDouble(18, master.getIsActive());
				callableStatement.setDouble(19, master.getPst_type_id());
				callableStatement.setDouble(20, master.getOpBalance());
				if (master.getAsOnDate()!=null) {
					java.sql.Date asonDate = DateUtil.convertStringDateToSqlDate(
							master.getAsOnDate(), "yyyy-MM-dd");
					callableStatement.setDate(21, asonDate);
				}
				else {
					callableStatement.setDate(21, null);
				}
				callableStatement.setDouble(22, master.getCompanyId());
				callableStatement.setDouble(23, master.getStoreId());
				callableStatement.setDouble(24, master.getFinyrId());
				callableStatement.setDouble(25, master.getCreatedBy());
				callableStatement.setInt(26, master.getDueDays());
				callableStatement.setDouble(27, master.getDuePer());
				callableStatement.registerOutParameter(28,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(28);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				if(callableStatement!=null)
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public ResponseObj getDuplicateAccount(CommonResultSetMapper mapper)
			throws DAOException {
		ResponseObj responseObj=new ResponseObj();
		int status = 0;
		
		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		//ResultSet rs = null;
		//List<AccountDTO> accnts=new ArrayList<AccountDTO>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.GET_DUPLICATE_ACCOUNT);
			callableStatement.setInt(1, mapper.getId());
			callableStatement.setString(2, mapper.getName());
			callableStatement.setInt(3, mapper.getCompanyId());
			callableStatement.setInt(4, mapper.getStoreId());
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			
			callableStatement.execute();
			
			//rs = callableStatement.getResultSet();

			status = callableStatement.getInt(5);
			
			if (status > 0) {
				
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(status);
				responseObj.setReason("Duplicate accounts found.");
				
			} else {
				
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(status);
				responseObj.setReason("No Dupllicate records found.");
				
			}

//			logger.info("duplicate account fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				//rs.close();
				if(callableStatement!=null)
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}

		return responseObj;
	}
	
	@Override
	public ResponseObj updateAccount(AccountMaster accountMaster) throws DAOException {
		
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_UPDATE_ACCOUNT);
				
				callableStatement.setInt(1,accountMaster.getId());
				callableStatement.setString(2,accountMaster.getName());
				callableStatement.setString(3,accountMaster.getCode());
				callableStatement.setInt(4,accountMaster.getGroupId());
				callableStatement.setString(5,accountMaster.getGroup_code());
				callableStatement.setInt(6,accountMaster.getCityId());
				callableStatement.setInt(7,accountMaster.getZoneId());
				callableStatement.setInt(8,accountMaster.getAreaId());
				callableStatement.setString(9,accountMaster.getAddress());
				if(accountMaster.getPin()==null)
					callableStatement.setString(10,"0");
				else
					callableStatement.setString(10,accountMaster.getPin());
				callableStatement.setString(11,accountMaster.getPhone());
				callableStatement.setString(12,accountMaster.getEmail());
				callableStatement.setString(13,accountMaster.getPanNo());
				callableStatement.setString(14,accountMaster.getGstRegistrationNo());
				callableStatement.setString(15,accountMaster.getBcdaRegistrationNo());
				callableStatement.setString(16,accountMaster.getDlNo());
				callableStatement.setDouble(17,accountMaster.getCashDiscountPercentage());
				callableStatement.setDouble(18,accountMaster.getTransLimit());
				callableStatement.setInt(19,accountMaster.getIsActive());
				callableStatement.setInt(20,accountMaster.getPst_type_id());
				callableStatement.setDouble(21,accountMaster.getOpBalance());
				callableStatement.setString(22,accountMaster.getAsOnDate());
				callableStatement.setInt(23,accountMaster.getCompanyId());
				callableStatement.setInt(24,accountMaster.getStoreId());
				callableStatement.setInt(25,accountMaster.getFinyrId());
				callableStatement.setInt(26,accountMaster.getUpdatedBy());
				callableStatement.setInt(27, accountMaster.getDueDays());
				callableStatement.setDouble(28, accountMaster.getDuePer());
				callableStatement.registerOutParameter(29,java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record updated successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not updated successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not updated successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not updated successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not updated successfully.");
			throw new DAOException("Check data to be updated", e);
		} finally {
			try {
				
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public ResponseObj deleteAccount(CommonResultSetMapper mapper) throws DAOException {
		
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_DELETE_ACCOUNT);
				
				callableStatement.setInt(1, mapper.getCompanyId());
				callableStatement.setInt(2, mapper.getStoreId());
				callableStatement.setInt(3, mapper.getId());
				callableStatement.registerOutParameter(4,java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(4);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record deleted successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not deleted successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not deleted successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not deleted successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not deleted successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public List<ChartOfAccountDTO> getChartOfAccount(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<ChartOfAccountDTO> accnts=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_CHART_OF_ACCOUNT);//chart_of_account
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			
			callableStatement.setInt(3, mapper.getFinYrId());
			callableStatement.setInt(4, mapper.getId()); //groupID
			callableStatement.setInt(5, mapper.getAccountID());
			//callableStatement.setString(6, mapper.getAsOnDate())
			if (mapper.getAsOnDate()!=null) {
				java.sql.Date asonDate = DateUtil.convertStringDateToSqlDate(
				mapper.getAsOnDate(), "yyyy-MM-dd");
				callableStatement.setDate(6, asonDate);
			}
			else {
				callableStatement.setDate(6, null);
			}
			
			/*callableStatement.setString(3, mapper.getName());
			callableStatement.setInt(4, mapper.getId());  //1 bank :: 2 cash :: 3 cashNbank */
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<ChartOfAccountDTO> resultSetMapper1 = new ReflectionResultSetMapper<ChartOfAccountDTO>(
					ChartOfAccountDTO.class);

			while (rs.next()) {
				ChartOfAccountDTO acnt=new ChartOfAccountDTO();
				acnt = resultSetMapper1.mapRow(rs);
				accnts.add(acnt);

			}

			//logger.info("accounts fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		System.out.println("chart of accounts = "+accnts);
		return accnts;
	}
	
	@Override
	public String addJournal(JournalListDTO journallistDTO) throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;

		try {
			journallistDTO.setQs(journallistDTO.getEntry_type());
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				StringWriter sw = new StringWriter();
				//File file = new	 File("D:\\2017-04-25\\file_expiry_invoice_creation.xml");
				JAXBContext jaxbContext = JAXBContext
						.newInstance(JournalListDTO.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				//jaxbMarshaller.marshal(expiry, file);
				//jaxbMarshaller.marshal(sales, System.out);
				jaxbMarshaller.marshal(journallistDTO, sw);
				String result = sw.toString();
				System.out.println("Journal output string:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_JOURNAL);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "journalListDTO");
				callableStatement.setString(3, "journallist");
				
				callableStatement.registerOutParameter(4,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(4);
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		System.out.println("journal insert status = "+status);
		return ""+status;

	}
	
	@Override
	public List<AccountDTO> getAccountsAutocomplete(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountDTO> accnts=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ACCOUNTS_AUTOCOMPLETE);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setString(3, mapper.getName());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<AccountDTO> resultSetMapper1 = new ReflectionResultSetMapper<AccountDTO>(
					AccountDTO.class);

			while (rs.next()) {
				AccountDTO acnt=new AccountDTO();
				acnt = resultSetMapper1.mapRow(rs);
				accnts.add(acnt);

			}

			//logger.info("accounts fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		//System.out.println("auto accounts = "+accnts);
		return accnts;
	}
	
	@Override
	public ResponseObj deleteJournal(CommonResultSetMapper mapper) throws DAOException {
		
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_DELETE_JOURNAL);
				
				callableStatement.setInt(1, mapper.getCompanyId());
				callableStatement.setInt(2, mapper.getStoreId());
				callableStatement.setInt(3, mapper.getId());
				callableStatement.registerOutParameter(4,java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(4);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record deleted successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not deleted successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not deleted successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not deleted successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not deleted successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	@Override
	public List<JournalDTO> getAllJournalByType(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<JournalDTO> journals=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ALLJOURNAL_BYTYPE);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setInt(3, mapper.getFinYrId());
			callableStatement.setInt(4, mapper.getId());
			callableStatement.setString(5, mapper.getQs());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<JournalDTO> resultSetMapper1 = new ReflectionResultSetMapper<JournalDTO>(
					JournalDTO.class);

			while (rs.next()) {
				JournalDTO journal=new JournalDTO();
				journal = resultSetMapper1.mapRow(rs);
				journals.add(journal);

			}

//			logger.info("ParaJournalTypeMaster fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		System.out.println("JournalDTO = "+journals);
		return journals;
	}
	
	@Override
	public List<JournalListDTO> getJournalById(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<JournalDTO> journals=new ArrayList<>();
		List<JournalListDTO> journallsts=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			//JournalDTO list
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_JOURNALDETAILS_BYID);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setInt(3, mapper.getFinYrId());
			callableStatement.setInt(4, mapper.getId());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<JournalDTO> resultSetMapper = new ReflectionResultSetMapper<JournalDTO>(
					JournalDTO.class);

			while (rs.next()) {
				JournalDTO journal=new JournalDTO();
				journal = resultSetMapper.mapRow(rs);
				journals.add(journal);
			}
			
			//header
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_JOURNALHEADER_BYID);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setInt(3, mapper.getFinYrId());
			callableStatement.setInt(4, mapper.getId());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<JournalListDTO> resultSetMapper1 = new ReflectionResultSetMapper<JournalListDTO>(
					JournalListDTO.class);

			while (rs.next()) {
				JournalListDTO journallst=new JournalListDTO();
				journallst = resultSetMapper1.mapRow(rs);
				
				journallst.setJournallist(journals);
				
				journallsts.add(journallst);

			}

//			logger.info("JournalListDTO fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		System.out.println("JournalListDTO = "+journals);
		return journallsts;
	}
	
	@Override
	public List<AccountDTO> getAccountsAutocompleteByGroup(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountDTO> accnts=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_ACCOUNTS_AUTOCOMPLETEBYGROUP);
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setInt(3, mapper.getId());
			callableStatement.setString(4, mapper.getName());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<AccountDTO> resultSetMapper1 = new ReflectionResultSetMapper<AccountDTO>(
					AccountDTO.class);

			while (rs.next()) {
				AccountDTO acnt=new AccountDTO();
				acnt = resultSetMapper1.mapRow(rs);
				accnts.add(acnt);

			}

//			logger.info("accounts fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		//System.out.println("auto accounts = "+accnts);
		return accnts;
	}
	
	@Override
	public List<AccountDTO> getLedgerDetailsByCode(CommonResultSetMapper mapper) throws DAOException {

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<AccountDTO> accountDTOs=new ArrayList<>();

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			//JournalDTO list
			if(mapper.getId()==1)
			{
				System.out.println("PROC_GET_LEDGERDETAILS_BYCODE id = "+1);
				callableStatement = connection.prepareCall(StoredProcedures.PROC_GET_LEDGERDETAILS_BYCODE);
			}
			else {
				System.out.println("PROC_GET_LEDGERDETAILS_BYCODE id = "+0);
				callableStatement = connection.prepareCall(StoredProcedures.PROC_GET_LEDGERDETAILS_BYCODE);
			}
			callableStatement.setInt(1, mapper.getCompanyId());
			callableStatement.setInt(2, mapper.getStoreId());
			callableStatement.setString(3, mapper.getGroupCode());
			callableStatement.setInt(4, mapper.getAccountID());
			callableStatement.setInt(5, mapper.getReferenceID());
//			callableStatement.setInt(4, mapper.getId());
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<AccountDTO> resultSetMapper = new ReflectionResultSetMapper<AccountDTO>(
					AccountDTO.class);

			while (rs.next()) {
				AccountDTO accountDTO=new AccountDTO();
				accountDTO = resultSetMapper.mapRow(rs);
				accountDTOs.add(accountDTO);
			}
			
			

//			logger.info("accountDTOs fetched");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
				{
					rs.close();
				}
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		System.out.println("accountDTOs = "+accountDTOs);
		return accountDTOs;
	}
	
	
	
	//added on 09.07.2018
	
	@Override
	public int createPurchaseReturn(InventoryReturn inventoryReturn)
			throws DAOException {
		System.out.println("createPurchaseReturn inventoryReturn = "+inventoryReturn);
		EntityManager em = null;
		int returnId;
		//StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		Set<Integer> stockInIds = new HashSet<Integer>();
		//StoreMaster store = addressDAO.getStoreByStoreId(inventoryReturn.getStoreId());
		//InventoryReturnItem returnItem = null;
		try {
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			returnId = 0;
			inventoryReturn.setDate(new Date());
			//String dateCurrnt = formatter.format(currDate);

			// persist the return
			inventoryReturn.setDeleteFlag("N");
			inventoryReturn.setApproved("N");
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(inventoryReturn);

			// persist each return item
			List<InventoryReturnItem> returnItems = inventoryReturn
					.getInventoryReturnItems();
			Iterator<InventoryReturnItem> iterator = returnItems.iterator();
			while (iterator.hasNext()) {
				InventoryReturnItem returnItem1 = (InventoryReturnItem) iterator
						.next();
				//returnItem = returnItem1;
				InventoryReturn returnParent = new InventoryReturn();
				returnParent.setId(inventoryReturn.getId());
				returnItem1.setInventoryReturn(returnParent);
				returnItem1.setDeleteFlag("N");

				em.persist(returnItem1);
				stockInIds.add(returnItem1.getInventoryStockIn().getId());

			}

			em.getTransaction().commit();
			returnId = inventoryReturn.getId();
			
			//purchaseReturnJournalEntry(inventoryReturn);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		
		return returnId;
	}
	
	@Override
	public int updatePurchaseReturn(InventoryReturn inventoryReturn)
			throws DAOException {
		EntityManager em = null;
		InventoryReturn oldStockInRet = null;
		int returnId;
		
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			oldStockInRet = em.find(InventoryReturn.class, inventoryReturn.getId());
			
			inventoryReturn.setDate(oldStockInRet.getDate());
			
			em.merge(inventoryReturn);
			
			Query query1 = em
					.createNativeQuery("delete from im_t_raw_purchase_return_items_c  where return_id=? and store_id=?");
			query1.setParameter(1, inventoryReturn.getId());
			query1.setParameter(2, inventoryReturn.getStoreId());
			query1.executeUpdate();
			
			// Persist each StockIn item
			List<InventoryReturnItem> stockInRetItems = inventoryReturn.getInventoryReturnItems();
			for (InventoryReturnItem stockInRetItem : stockInRetItems) {
				InventoryReturn inventoryStckInRet = new InventoryReturn();
				inventoryStckInRet.setId(inventoryReturn.getId());
				stockInRetItem.setInventoryReturn(inventoryStckInRet);
				stockInRetItem.setDeleteFlag("N");
				em.persist(stockInRetItem);
			}
			em.getTransaction().commit();
			returnId = inventoryReturn.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return returnId;
	}
	
	@Override
	public String deletePurchaseReturn(Integer returnId,
			Integer storeId) throws DAOException {

		int returnid = (returnId);
		EntityManager em = null;
		InventoryReturn inventoryReturn=null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			inventoryReturn = em.find(InventoryReturn.class, returnid);
			inventoryReturn.setDeleteFlag("Y");
			List<InventoryReturnItem> lstItems = inventoryReturn.getInventoryReturnItems();
			Iterator<InventoryReturnItem> iterator2 = lstItems.iterator();
			while (iterator2.hasNext()) {
				InventoryReturnItem inventoryReturnItem = (InventoryReturnItem) iterator2.next();
				inventoryReturnItem.setDeleteFlag("Y");
			}
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}
	
	@Override
	public String approvePurchaseReturn(InventoryReturn inventoryReturn1) throws DAOException {

		EntityManager em = null;
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String status = "";
		InventoryReturn inventoryReturn = null;
		try {
			int returnId = inventoryReturn1.getId();
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			inventoryReturn = em.find(InventoryReturn.class, returnId);
			inventoryReturn.setApproved("Y");
			
			//String returnDate = df.format(inventoryReturn.getDate());

			// create a payment object
			/*InventoryInvoicePayment invoicePayment = new InventoryInvoicePayment();

			invoicePayment.setStockInId(stckInId);
			invoicePayment.setPoId(inventoryStckIn.getPoId());
			invoicePayment.setVendorId(inventoryStckIn.getVendorId());
			invoicePayment.setBillAmount(inventoryStckIn.getTotalPrice());
			invoicePayment.setAmountToPay(inventoryStckIn.getTotalPrice());
			invoicePayment.setPaidAmount(0.0);
			invoicePayment.setCreditLimit(50000);
			invoicePayment.setDeleteFlag("N");
			invoicePayment.setStockInDate(stckInDate);
			invoicePayment.setStoreId(inventoryStckIn.getStoreId());
			invoicePayment.setIsReturn("N");
			em.persist(invoicePayment);*/
			// start update inventory payment data
						List<InventoryInvoicePayment> paymentsByVendor = getPaymentInfoByVendor(
								inventoryReturn.getVendorId() + "",
								inventoryReturn.getStoreId() + "");

						Iterator<InventoryInvoicePayment> iterator4 = paymentsByVendor
								.iterator();
						while (iterator4.hasNext()) {
							boolean isReturn = false;
							InventoryInvoicePayment inventoryInvoicePayment = (InventoryInvoicePayment) iterator4
									.next();
							int stockinId = inventoryInvoicePayment.getStockInId();
							double billAmt = inventoryInvoicePayment.getBillAmount();
							double paidAmt = inventoryInvoicePayment.getPaidAmount();
							//double amtToPay = inventoryInvoicePayment.getAmountToPay();
							double totalReturnAmt = 0.0;
							List<InventoryReturnItem> returnItemsList = inventoryReturn
									.getInventoryReturnItems();
							Iterator<InventoryReturnItem> iterator3 = returnItemsList
									.iterator();
							while (iterator3.hasNext()) {
								InventoryReturnItem inventoryReturnItem = (InventoryReturnItem) iterator3
										.next();
								int stockInId = inventoryReturnItem.getInventoryStockIn()
										.getId();
								if (stockinId == stockInId) {
									isReturn = true;
									totalReturnAmt = totalReturnAmt
											+ inventoryReturnItem.getItemGrossAmount();
								}

							}
							if (isReturn) {
								InventoryInvoicePayment returnPayment = new InventoryInvoicePayment();
								returnPayment.setPoId(inventoryInvoicePayment.getPoId());
								returnPayment.setStockInId(stockinId);
								returnPayment.setStockInDate(inventoryInvoicePayment
										.getStockInDate());
								returnPayment.setVendorId(inventoryInvoicePayment
										.getVendorId());
								returnPayment.setStoreId(inventoryInvoicePayment
										.getStoreId());
								returnPayment.setBillAmount(billAmt);
								returnPayment.setPaidAmount(totalReturnAmt);
								returnPayment.setAmountToPay(billAmt - paidAmt
										- totalReturnAmt);
								returnPayment.setIsReturn("Y");
								returnPayment.setCreditLimit(inventoryInvoicePayment
										.getCreditLimit());
								returnPayment.setDeleteFlag("N");
								//returnPayment.setCreatedBy(inventoryReturn.getCreatedBy());
								//returnPayment.setCreatedDate(returnDate);
								// persist
								em.persist(returnPayment);
							}

						}
						// end update inventory payment data

			em.getTransaction().commit();
			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check purchase return approving to be updated", e);
		} finally {
			if (em != null) em.close();
		}
		
		if(("y").equalsIgnoreCase(inventoryReturn1.getIs_account())) {
			purchaseReturnJournalEntry(inventoryReturn1);
		}
		
		return status;
	}
	
	@Override
	public List<InventoryReturn> getPurchaseReturn(Integer storeId,
			String date) throws DAOException {

		List<InventoryReturn> inventoryReturnLst = null;
		int storeid = (storeId);

		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			TypedQuery<InventoryReturn> qry = em
					.createQuery("SELECT r FROM InventoryReturn r WHERE r.storeId=:storeid and r.date=:date and r.deleteFlag='N'",
							InventoryReturn.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);

			inventoryReturnLst = qry.getResultList();
			
			Iterator<InventoryReturn> iterator = inventoryReturnLst.iterator();
			while (iterator.hasNext()) {
				InventoryReturn inventoryReturn = iterator.next();
				int vendorId = inventoryReturn.getVendorId();
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",
						    InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vendrName = vendor.getName();
				inventoryReturn.setVendorName(vendrName);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		return inventoryReturnLst;
	}

	@Override
	public InventoryReturn getPurchaseReturnById(Integer id, Integer storeId)
			throws DAOException {

		InventoryReturn inventoryReturn = null;
		EntityManager em = null;
		Map<Integer, MetricUnit> metricUnitMap = new HashMap<>();
		List<MetricUnit> metricUnits = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			TypedQuery<InventoryReturn> qry = em.createQuery("SELECT r FROM InventoryReturn r WHERE r.id=:id and r.storeId=:storeid",InventoryReturn.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("id", id);
			inventoryReturn = qry.getSingleResult();
			
			TypedQuery<MetricUnit> qryMetricUnit = em.createQuery("SELECT u FROM MetricUnit u WHERE u.deleteFlag='N'", 
				    MetricUnit.class);
				metricUnits = qryMetricUnit.getResultList();
				for(MetricUnit metricUnit : metricUnits) {
					metricUnitMap.put(metricUnit.getId(), metricUnit);
				}

			List<InventoryReturnItem> returnItems = inventoryReturn.getInventoryReturnItems();
			Iterator<InventoryReturnItem> iterator = returnItems.iterator();

			while (iterator.hasNext()) {
				InventoryReturnItem returnItem = (InventoryReturnItem) iterator.next();
				InventoryStockIn inventoryStockIn = returnItem.getInventoryStockIn();
				returnItem.setInventoryStckIn(inventoryStockIn);
				returnItem.setMetricUnit(metricUnitMap.get(returnItem.getUnitId()));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return inventoryReturn;

	}
	
	public List<InventoryInvoicePayment> getPaymentInfoByVendor(
			String vendorId, String storeId) throws DAOException {

		EntityManager em = null;
		List<InventoryInvoicePayment> inventoryStckInLst = null;
		List<InventoryInvoicePayment> invoicePaymentsList = new ArrayList<InventoryInvoicePayment>();

		//List<Integer> stockInIdTotalLst = null;
		Set<Integer> stockInTotalIdSet = new HashSet<Integer>();

		double billAmt = 0.0;
		try {
			int vendorid = Integer.parseInt(vendorId);
			int storeid = Integer.parseInt(storeId);

			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<InventoryInvoicePayment> qry = em
					.createQuery("SELECT p FROM InventoryInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N'",InventoryInvoicePayment.class);
			qry.setParameter("vendorId", vendorid);
			qry.setParameter("storeid", storeid);
			inventoryStckInLst = qry.getResultList();

			Iterator<InventoryInvoicePayment> iterator = inventoryStckInLst.iterator();
			while (iterator.hasNext()) {
				InventoryInvoicePayment inventoryInvoicePayment = (InventoryInvoicePayment) iterator.next();
				// add to set to get all separate stock in ids
				stockInTotalIdSet.add(inventoryInvoicePayment.getStockInId());
			}
			// convert set to list
			//stockInIdTotalLst = new ArrayList<Integer>(stockInTotalIdSet);

			// stock in ids with due
			if (stockInTotalIdSet.size() > 0) {
				Iterator<Integer> iterator3 = stockInTotalIdSet.iterator();
				while (iterator3.hasNext()) {
					Integer stockInId = (Integer) iterator3.next();

					Iterator<InventoryInvoicePayment> iterator4 = inventoryStckInLst.iterator();
					double paidAmt = 0.0;
					InventoryInvoicePayment inventoryInvoicePayment1 = null;
					int id = 0;
					int storeId1 = 0;
					int poId = 0;
					int vendorId1 = 0;
					double creditLimit = 0.0;
					String stockInDate = "";
					while (iterator4.hasNext()) {
						inventoryInvoicePayment1 = (InventoryInvoicePayment) iterator4
								.next();
						int stckInId = inventoryInvoicePayment1.getStockInId();

						if (stockInId == stckInId) {
							poId = inventoryInvoicePayment1.getPoId();
							id = inventoryInvoicePayment1.getId();
							vendorId1 = inventoryInvoicePayment1.getVendorId();
							creditLimit = inventoryInvoicePayment1
									.getCreditLimit();
							stockInDate = inventoryInvoicePayment1
									.getStockInDate();
							storeId1 = inventoryInvoicePayment1.getStoreId();
							billAmt = inventoryInvoicePayment1.getBillAmount();
							paidAmt = paidAmt
									+ inventoryInvoicePayment1.getPaidAmount();

						}

					}
					InventoryInvoicePayment inventoryInvoicePayment2 = new InventoryInvoicePayment();
					inventoryInvoicePayment2.setId(id);
					inventoryInvoicePayment2.setStoreId(storeId1);
					inventoryInvoicePayment2.setPoId(poId);
					inventoryInvoicePayment2.setVendorId(vendorId1);
					inventoryInvoicePayment2.setStockInId(stockInId);
					inventoryInvoicePayment2.setCreditLimit(creditLimit);
					inventoryInvoicePayment2.setBillAmount(billAmt);
					inventoryInvoicePayment2.setPaidAmount(paidAmt);
					inventoryInvoicePayment2.setAmountToPay(billAmt - paidAmt);
					inventoryInvoicePayment2.setStockInDate(stockInDate);
					invoicePaymentsList.add(inventoryInvoicePayment2);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check RawStockIn to be updated", e);
		} finally {
			em.close();
		}

		return invoicePaymentsList;
	}
	
	public ResponseObj insertDistributorAccount(InventoryVendor inventoryVendor)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				Date cdate=new SimpleDateFormat("dd/MM/yyyy").parse(inventoryVendor.getCreatedDate());//13/07/2018'
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String createdDate= formatter.format(cdate);
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_INSERT_ACCOUNT);
				callableStatement.setString(1, inventoryVendor.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SCR");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, inventoryVendor.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, inventoryVendor.getContactNo());
				callableStatement.setString(11, "");
				callableStatement.setString(12, "");
				callableStatement.setString(13, inventoryVendor.getAccountNumber());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, 0);
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, createdDate);
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, inventoryVendor.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, inventoryVendor.getCreatedByid());
				callableStatement.setInt(26, 0);
				callableStatement.setDouble(27, 0);
				callableStatement.setInt(28, inventoryVendor.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}
	
	public ResponseObj updateVendorAccount(InventoryVendor inventoryVendor)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				Date cdate=new SimpleDateFormat("dd/MM/yyyy").parse(inventoryVendor.getCreatedDate());//13/07/2018'
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String createdDate= formatter.format(cdate);
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_UPDATE_ACCOUNT);
				callableStatement.setString(1, inventoryVendor.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SCR");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, inventoryVendor.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, inventoryVendor.getContactNo());
				callableStatement.setString(11, "");
				callableStatement.setString(12, "");
				callableStatement.setString(13, inventoryVendor.getAccountNumber());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, 0);
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, createdDate);
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, inventoryVendor.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, inventoryVendor.getCreatedByid());
				callableStatement.setInt(26, 0);
				callableStatement.setDouble(27, 0);
				callableStatement.setInt(28, inventoryVendor.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}

	//13.07.2018
	public ResponseObj deleteCustomerNVendorAccountJournal(int id, int storeId) throws DAOException {
		EntityManager em = null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		int result = 0;
		ResponseObj responseObj=new ResponseObj();
		try {
			/*int custid = Integer.parseInt(id);
			int storeId1 = Integer.parseInt(storeId);*/

			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_DELETE_CUSTOMER_N_VENDOR);
			callableStatement.setInt(1, storeId);
			callableStatement.setInt(2, id);
			callableStatement.setString(3, "SCR");

			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

			callableStatement.execute();
			result = callableStatement.getInt(4);
			
			if (result >0) {
				
				responseObj.setStatus(ReturnConstant.SUCCESS);
				responseObj.setId(result);
				responseObj.setReason("vendor account deleted successfully.");
				
			} else {
				
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(result);
				responseObj.setReason("vendor account not deleted successfully.");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("vendor account not deleted successfully.");
			throw new DAOException("Check data to be deleted", e);
		}

		finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;
	}
	
	public int journalEntry(InventoryStockIn inventoryStockIn) //FOR PURCHASE ENTRY
			throws DAOException {
		System.out.println("journalEntry inventoryStockIn = "+inventoryStockIn);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		//InventoryDAOImpl inventoryDAOImpl = new InventoryDAOImpl();
		//CommonResultSetMapper mapper = new CommonResultSetMapper();

		try {
			InventoryStockIn stockxml = new InventoryStockIn();
			
			if(inventoryStockIn.getIs_account().equalsIgnoreCase("y") && inventoryStockIn.getDuties_ledger_id()!=0 && inventoryStockIn.getPurchase_ledger_id()!=0 && inventoryStockIn.getCredior_ledger_id()!=0)
			{
			
				List<JournalEntry> jes = new ArrayList<>();
				
				if(inventoryStockIn.getDuties_ledger_id()!=0 && inventoryStockIn.getTaxAmt()>0)
				{
					jes.add(new JournalEntry("dr", inventoryStockIn.getDuties_ledger_id(), inventoryStockIn.getTaxAmt()));
				}
				if(inventoryStockIn.getRound_ledger_id()!=0 && inventoryStockIn.getRoundOffAmt()!=0)
				{
					String type="";
					if(inventoryStockIn.getRoundOffAmt()<0)
						type="dr";//debit
					else
						type="cr";//credit
					
					jes.add(new JournalEntry(type, inventoryStockIn.getRound_ledger_id(), Math.abs(inventoryStockIn.getRoundOffAmt())));
				}
				if(inventoryStockIn.getPurchase_ledger_id()!=0 && inventoryStockIn.getTotGrossAmt()>0)
				{
					jes.add(new JournalEntry("dr", inventoryStockIn.getPurchase_ledger_id(), inventoryStockIn.getTotGrossAmt()));
				}
				if(inventoryStockIn.getCredior_ledger_id()!=0 && inventoryStockIn.getTotalPrice()>0)
				{
					jes.add(new JournalEntry("cr", inventoryStockIn.getCredior_ledger_id(), inventoryStockIn.getTotalPrice()));
				}
				if(inventoryStockIn.getDiscount_ledger_id()!=0 && inventoryStockIn.getDiscAmt()>0)
				{
					jes.add(new JournalEntry("cr", inventoryStockIn.getDiscount_ledger_id(), inventoryStockIn.getDiscAmt()));
				}
				if(inventoryStockIn.getMissllenous_ledger_id()!=0 && inventoryStockIn.getMiscCharge()>0)
				{
					jes.add(new JournalEntry("dr", inventoryStockIn.getMissllenous_ledger_id(), inventoryStockIn.getMiscCharge()));
				}
				
				
				stockxml.setJes(jes);
			}
			else {
				return 0;
			}
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			try {
				StringWriter sw = new StringWriter();
				//File file = new File("D:\\2017-10-04\\file_purchase_invoice_creation.xml");
				JAXBContext jaxbContext = JAXBContext
						.newInstance(InventoryStockIn.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				//jaxbMarshaller.marshal(inventoryStockIn, file);
				// jaxbMarshaller.marshal(inventoryStockIn, System.out);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("InventoryStockIn xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, inventoryStockIn.getQs());
				callableStatement.setString(4, inventoryStockIn.getBillDate());
				callableStatement.setInt(5, inventoryStockIn.getId());
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, inventoryStockIn.getStoreId());
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, inventoryStockIn.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				//invNo = callableStatement.getString(4);
				status = 1;
				/*if (status == 0) {
					result1 = "" + status;
				} else if (status == 1) {
					result1 = invNo;
				}*/
				// result=result.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
				// System.out.println("formatted output::: "+result);

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}
	
	public int purchaseReturnJournalEntry(InventoryReturn inventoryStockIn) //FOR PURCHASE Return ENTRY
			throws DAOException {
		System.out.println("purchaseReturnJournalEntry inventoryStockIn = "+inventoryStockIn);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		//InventoryDAOImpl inventoryDAOImpl = new InventoryDAOImpl();
		//CommonResultSetMapper mapper = new CommonResultSetMapper();

		try {
			InventoryReturn stockxml = new InventoryReturn();
			
			if("y".equalsIgnoreCase(inventoryStockIn.getIs_account()) && inventoryStockIn.getDuties_ledger_id()!=0 && inventoryStockIn.getPurchase_ledger_id()!=0 && inventoryStockIn.getCredior_ledger_id()!=0)
			{
			
				List<JournalEntry> jes = new ArrayList<>();
				
				if(inventoryStockIn.getDuties_ledger_id()!=0 && inventoryStockIn.getTaxAmt()>0)
				{
					jes.add(new JournalEntry("cr", inventoryStockIn.getDuties_ledger_id(), inventoryStockIn.getTaxAmt()));
				}
				if(inventoryStockIn.getRound_ledger_id()!=0 && inventoryStockIn.getRoundOffAmt()!=0)
				{
					String type="";
					if(inventoryStockIn.getRoundOffAmt()>0)
						type="dr";//debit
					else
						type="cr";//credit
					
					jes.add(new JournalEntry(type, inventoryStockIn.getRound_ledger_id(), Math.abs(inventoryStockIn.getRoundOffAmt())));
				}
				if(inventoryStockIn.getPurchase_ledger_id()!=0 && inventoryStockIn.getTotGrossAmt()>0)
				{
					jes.add(new JournalEntry("cr", inventoryStockIn.getPurchase_ledger_id(), inventoryStockIn.getTotGrossAmt()));
				}
				if(inventoryStockIn.getCredior_ledger_id()!=0 && inventoryStockIn.getNetAmt()>0)
				{
					jes.add(new JournalEntry("dr", inventoryStockIn.getCredior_ledger_id(), inventoryStockIn.getNetAmt()));
				}
				if(inventoryStockIn.getDiscount_ledger_id()!=0 && inventoryStockIn.getDisAmt()>0)
				{
					jes.add(new JournalEntry("dr", inventoryStockIn.getDiscount_ledger_id(), inventoryStockIn.getDisAmt()));
				}
				if(inventoryStockIn.getMissllenous_ledger_id()!=0 && inventoryStockIn.getMiscCharge()>0)
				{
					jes.add(new JournalEntry("cr", inventoryStockIn.getMissllenous_ledger_id(), inventoryStockIn.getMiscCharge()));
				}
				
				
				stockxml.setJes(jes);
			}
			else {
				return 0;
			}
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			try {
				StringWriter sw = new StringWriter();
				//File file = new File("D:\\2017-10-04\\file_purchase_invoice_creation.xml");
				JAXBContext jaxbContext = JAXBContext
						.newInstance(InventoryReturn.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				//jaxbMarshaller.marshal(inventoryStockIn, file);
				// jaxbMarshaller.marshal(inventoryStockIn, System.out);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("InventoryReturn xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, inventoryStockIn.getQs());
				callableStatement.setString(4, inventoryStockIn.getBillDate());
				callableStatement.setInt(5, inventoryStockIn.getId());
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, inventoryStockIn.getStoreId());
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, inventoryStockIn.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				//invNo = callableStatement.getString(4);
				status = 1;
				/*if (status == 0) {
					result1 = "" + status;
				} else if (status == 1) {
					result1 = invNo;
				}*/
				// result=result.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
				// System.out.println("formatted output::: "+result);

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}
	
	public int saleReturnjournalEntry(SalesReturn salesReturn)
			throws DAOException {
		System.out.println("saleReturnjournalEntry salesReturn = "+salesReturn);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		try {
			
			Date createddate = new Date();
			SalesReturn stockxml = new SalesReturn();
			
			//new logic
			//For Sale Return Journal entry
			if(salesReturn.getIs_account().equalsIgnoreCase("y") && salesReturn.getSale_ledger_id()!=0 && salesReturn.getDuties_ledger_id()!=0)
			{
				double roundoff = 0;
				
				List<JournalEntry> jes = new ArrayList<>();
				if(salesReturn.getSale_ledger_id()!=0 && salesReturn.getGrossAmt()!=0)
				{
					jes.add(new JournalEntry("dr", salesReturn.getSale_ledger_id(), salesReturn.getGrossAmt()));
					roundoff += salesReturn.getGrossAmt();
				}
				if(salesReturn.getDuties_ledger_id()!=0)
				{
					jes.add(new JournalEntry("dr", salesReturn.getDuties_ledger_id(), salesReturn.getTaxVatAmt()));
					roundoff += salesReturn.getTaxVatAmt();
				}
				if(salesReturn.getService_charge_ledger_id()!=0 && salesReturn.getServiceChargeAmt()>0)
				{
					jes.add(new JournalEntry("dr", salesReturn.getService_charge_ledger_id(), salesReturn.getServiceChargeAmt()));
					roundoff += salesReturn.getServiceChargeAmt();
				}
				if(salesReturn.getDebitor_cash_ledger_id()!=0 && salesReturn.getNetAmt()>0)
				{
					jes.add(new JournalEntry("cr", salesReturn.getDebitor_cash_ledger_id(), salesReturn.getNetAmt()));
					roundoff -= salesReturn.getNetAmt();
				}
				/*if(salesReturn.getCard_ledger_id()!=0 && cardAmt>0)
				{
					//jes.add(new JournalEntry("dr", salesReturn.getDebitor_cash_ledger_id(), salesReturn.getPaidAmount()));
					jes.add(new JournalEntry("dr", salesReturn.getCard_ledger_id(), cardAmt));
					roundoff -= cardAmt;
				}*/
				
				
				if(salesReturn.getDiscount_ledger_id()!=0 && salesReturn.getDiscAmt()>0)
				{
					jes.add(new JournalEntry("cr", salesReturn.getDiscount_ledger_id(), salesReturn.getDiscAmt()));
					roundoff -= salesReturn.getDiscAmt();
				}
				if(salesReturn.getRound_ledger_id()!=0 && Math.abs(roundoff)<1) //as during part salesReturn roundoff will differ
				{
					String type="";
					if(roundoff>0)
						type="cr";
					else
						type="dr";
					
					jes.add(new JournalEntry(type, salesReturn.getRound_ledger_id(), Math.abs(roundoff)));
				}
				
				stockxml.setJes(jes);
			}
			else {
				return 0;
			}
			
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			try {
				StringWriter sw = new StringWriter();
				JAXBContext jaxbContext = JAXBContext
						.newInstance(SalesReturn.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("Customer SalesReturn xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, salesReturn.getQs());
				callableStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(createddate));
				callableStatement.setInt(5, salesReturn.getId());//(5, salesReturn.getOrderId()); //getOrderNo()
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, salesReturn.getStoreId());
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, salesReturn.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				//invNo = callableStatement.getString(4);
				status = 1;

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}
	
	public int VendorPaymentjournalEntry(InventoryInvoicePayment payment)
			throws DAOException {
		System.out.println("VendorPaymentjournalEntry InventoryInvoicePayment payment = "+payment);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		//InventoryDAOImpl inventoryDAOImpl = new InventoryDAOImpl();
		//CommonResultSetMapper mapper = new CommonResultSetMapper();

		try {
			InventoryInvoicePayment stockxml = new InventoryInvoicePayment();
			
			if(payment.getIs_account().equalsIgnoreCase("y"))
			{
			
				List<JournalEntry> jes = new ArrayList<>();
				
				if(payment.getCr_legder_id()!=0 && payment.getCr_amount()>0)
				{
					jes.add(new JournalEntry("cr", payment.getCr_legder_id(), payment.getCr_amount()));
				}
				if(payment.getDr_legder_id()!=0 && payment.getDr_amount()>0)
				{
					jes.add(new JournalEntry("dr", payment.getDr_legder_id(), payment.getDr_amount()));
				}
				stockxml.setJes(jes);
			}
			else
			{
				return 0;
			}
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			try {
				StringWriter sw = new StringWriter();
				JAXBContext jaxbContext = JAXBContext
						.newInstance(InventoryInvoicePayment.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("InventoryInvoicePayment xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, payment.getQs());
				callableStatement.setString(4, payment.getCreatedDate());
				callableStatement.setInt(5, payment.getId());
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, payment.getStoreId());
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, payment.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				status = 1;

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}
	
	@Override
	public List<FgStockIn> getSimpleFgStockInByDate(Integer storeId,
			String date) throws DAOException {

		int storeid = (storeId);

		List<FgStockIn> fgStockInList = null;

		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date finaldate = dateFormat.parse(date);
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<FgStockIn> qry = em
					.createQuery("SELECT p FROM FgStockIn p WHERE p.storeId=:storeid and p.date=:date and p.deleteFlag='N' order by p.id desc",
							FgStockIn.class);
			qry.setParameter("storeid", storeid);
			qry.setParameter("date", finaldate);
			fgStockInList = qry.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return fgStockInList;
	}
	
	@Override
	public List<FgItemCurrentStock> getFgCurrentStockByItem(Integer storeId, Integer itemId)
			throws DAOException {
		

		EntityManager em = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		List<FgItemCurrentStock> fgItemCurrentStockList=new ArrayList<>();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			callableStatement = connection.prepareCall(StoredProcedures.PROC_FG_ITEM_CUR_STOCK);
			callableStatement.setInt(1, storeId);
			callableStatement.setInt(2, itemId);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			ReflectionResultSetMapper<FgItemCurrentStock> resultSetMapper1 = new ReflectionResultSetMapper<FgItemCurrentStock>(
					FgItemCurrentStock.class);

			while (rs.next()) {
				FgItemCurrentStock fgItemCurrentStock=new FgItemCurrentStock();
				fgItemCurrentStock = resultSetMapper1.mapRow(rs);
				fgItemCurrentStockList.add(fgItemCurrentStock);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em.close();
		}
		return fgItemCurrentStockList;

	}
	
	@Override
	public double getFgPrevRetQtyByItem(Integer storeId, Integer itemId,Integer stockinId)
			throws DAOException {
		double precretqty=0.0;
		List<FgReturnItem> fgitemStockInList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			TypedQuery<FgReturnItem> qry = em
					.createQuery("SELECT fg FROM FgReturnItem fg WHERE fg.storeId=:storeid and fg.menuItem.id=:id and fg.fgStockIn.id=:stockinid and fg.deleteFlag='N'",
							FgReturnItem.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("id", itemId);
			qry.setParameter("stockinid", stockinId);
			fgitemStockInList = qry.getResultList();
			
			Iterator<FgReturnItem> iterator = fgitemStockInList.iterator();
			while (iterator.hasNext()) {
				FgReturnItem fgStockInItem = (FgReturnItem) iterator.next();
				double retqty=fgStockInItem.getItemQuantity();
				precretqty+=retqty;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			
			if(em != null) em.close();
		}
		return precretqty;

	}
	
	@Override
	public FgStockIn getFgStockInById(Integer storeId, Integer id) throws DAOException {

		FgStockIn fgStockIn = null;
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();

			TypedQuery<FgStockIn> qry = em
					.createQuery("SELECT p FROM FgStockIn p WHERE p.storeId=:storeid and p.id=:id and p.deleteFlag='N'",
							FgStockIn.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("id", id);
			fgStockIn = qry.getSingleResult();
			//get the cur stock
			List<FgStockInItemsChild> fgstockInItems = fgStockIn.getFgStockInItemsChilds();
			Iterator<FgStockInItemsChild> iterator = fgstockInItems.iterator();
			while (iterator.hasNext()) {
				FgStockInItemsChild fgStockInItem = (FgStockInItemsChild) iterator.next();
				FgItemCurrentStock fgcurstock=getFgCurrentStockByItem(storeId,fgStockInItem.getMenuItem().getId()).get(0);
				fgStockInItem.setCurStock(fgcurstock.getCurStock());
				double prevretqty=getFgPrevRetQtyByItem(fgStockInItem.getStoreId(),fgStockInItem.getMenuItem().getId(),fgStockInItem.getFgStockInId().getId());
				fgStockInItem.setPrevretQty(prevretqty);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return fgStockIn;

	}
	
	@Override
	public List<FgStockInItemsChild> getFgStockInItemsByItemId(Integer storeId, Integer itemId) throws DAOException {

		List<FgStockInItemsChild> fgStockInItems = null;
		EntityManager em = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curdt=dateFormat.format(new Date());
			Date currDate = dateFormat.parse(curdt);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			cal.add(Calendar.DATE, -30);
			Date previousDate = cal.getTime();
			em = entityManagerFactory.createEntityManager();

			TypedQuery<FgStockInItemsChild> qry = em
					.createQuery("SELECT fgi FROM FgStockInItemsChild fgi WHERE fgi.storeId=:storeid and fgi.menuItem.id=:id and fgi.fgStockInId.date between :fromDate and :toDate and fgi.deleteFlag='N'",
							FgStockInItemsChild.class);
			qry.setParameter("storeid", storeId);
			qry.setParameter("id", itemId);
			qry.setParameter("fromDate", previousDate);
			qry.setParameter("toDate", currDate);
			fgStockInItems = qry.getResultList();
			//get the cur stock
			//List<FgStockInItemsChild> fgstockInItems = fgStockIn.getFgStockInItemsChilds();
			Iterator<FgStockInItemsChild> iterator = fgStockInItems.iterator();
			while (iterator.hasNext()) {
				FgStockInItemsChild fgStockInItem = (FgStockInItemsChild) iterator.next();
				FgItemCurrentStock fgcurstock=getFgCurrentStockByItem(storeId,fgStockInItem.getMenuItem().getId()).get(0);
				fgStockInItem.setCurStock(fgcurstock.getCurStock());
				double prevretqty=getFgPrevRetQtyByItem(fgStockInItem.getStoreId(),fgStockInItem.getMenuItem().getId(),fgStockInItem.getFgStockInId().getId());
				fgStockInItem.setPrevretQty(prevretqty);
				fgStockInItem.setStockId(fgStockInItem.getFgStockInId().getId());
				fgStockInItem.setStockDate(fgStockInItem.getFgStockInId().getDate());
				TypedQuery<InventoryVendor> qryGetVndr = em
						.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",
						    InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", fgStockInItem.getFgStockInId().getVendorId());
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				fgStockInItem.setVndrId(fgStockInItem.getFgStockInId().getVendorId());
				fgStockInItem.setVndrName(vendor.getName());
				fgStockInItem.setInvNo(fgStockInItem.getFgStockInId().getBillNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return fgStockInItems;

	}
	
	@Override
	public int updateFgStockIn(FgStockIn fgStockIn)
			throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		FgStockIn oldfgStockIn = null;
		int fgstckInId;
		try {

			fgstckInId = fgStockIn.getId();
			
			em = entityManagerFactory.createEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();
			oldfgStockIn = em.find(FgStockIn.class, fgstckInId);
			oldfgStockIn.setVendorId(fgStockIn.getVendorId());
			oldfgStockIn.setBillNo(fgStockIn.getBillNo());
			oldfgStockIn.setBillDate(fgStockIn.getBillDate());
			oldfgStockIn.setItemTotal(fgStockIn.getItemTotal());
			oldfgStockIn.setDiscPer(fgStockIn.getDiscPer());
			oldfgStockIn.setDiscAmt(fgStockIn.getDiscAmt());
			oldfgStockIn.setVatAmt(fgStockIn.getVatAmt());
			oldfgStockIn.setServiceTaxAmt(fgStockIn.getServiceTaxAmt());
			oldfgStockIn.setRoundOffAmt(fgStockIn.getRoundOffAmt());
			oldfgStockIn.setTotalPrice(fgStockIn.getTotalPrice());
			oldfgStockIn.setUpdatedBy(fgStockIn.getUpdatedBy());
			oldfgStockIn.setUpdatedDate(fgStockIn.getUpdatedDate());
			em.merge(oldfgStockIn);
			
			Query query1 = em1
					.createNativeQuery("delete from im_t_fg_stock_in_items_c  where fg_stock_in_id=? and store_id=?");
			query1.setParameter(1, fgstckInId);
			query1.setParameter(2, fgStockIn.getStoreId());
			query1.executeUpdate();
			em1.getTransaction().commit();
			// persist each StockIn item
			List<FgStockInItemsChild> fgstockInItems = fgStockIn
					.getFgStockInItemsChilds();
			Iterator<FgStockInItemsChild> iterator = fgstockInItems.iterator();
			while (iterator.hasNext()) {
				FgStockInItemsChild fgstockInItem = (FgStockInItemsChild) iterator
						.next();
				FgStockIn fgStckIn = new FgStockIn();
				fgStckIn.setId(fgStockIn.getId());
				fgstockInItem.setFgStockInId(fgStckIn);
				fgstockInItem.setDeleteFlag("N");
				em.persist(fgstockInItem);
				// get item rate and item id
				int itemId = fgstockInItem.getMenuItem().getId();
				double itemRate = fgstockInItem.getItemRate();
				int storeId = fgstockInItem.getStoreId();
				// update menu item rate in fm_m_food_items
				Query query = em.createNativeQuery("update fm_m_food_items set purchase_price=? where id=? and store_id=?");
				query.setParameter(1, itemRate);
				query.setParameter(2, itemId);
				query.setParameter(3, storeId);
				query.executeUpdate();
				
			}
			em.getTransaction().commit();
			fgstckInId = fgStockIn.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if (em != null) em.close();
		}
		
		//journalEntry(inventoryStockIn);
		
		return fgstckInId;
	}
	
	@Override
	public String deleteFgStockIn(Integer fgstockInId,
			Integer storeId) throws DAOException {

		int fgstockInid = (fgstockInId);
		EntityManager em = null;
		FgStockIn fgStockIn=null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			fgStockIn = em
					.find(FgStockIn.class, fgstockInid);
			fgStockIn.setDeleteFlag("Y");
			List<FgStockInItemsChild> lstItems = fgStockIn
					.getFgStockInItemsChilds();
			Iterator<FgStockInItemsChild> iterator2 = lstItems
					.iterator();
			while (iterator2.hasNext()) {
				FgStockInItemsChild fgStockInItem = (FgStockInItemsChild) iterator2.next();
				fgStockInItem.setDeleteFlag("Y");
			}
			
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if (em != null) em.close();
		}
		return message;

	}
	
	//added on 17.06.2019
	
		@Override
		public int createFgReturn(FgReturn fgReturn)
				throws DAOException {
			EntityManager em = null;
			int fgreturnId=0;
			//Set<Integer> fgstockInIds = new HashSet<Integer>();
			try {
				//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				fgReturn.setDate(new Date());
				// persist the fg return
				fgReturn.setDeleteFlag("N");
				fgReturn.setApproved("N");
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				em.persist(fgReturn);
				// persist each return item
				List<FgReturnItem> fgreturnItems = fgReturn.getFgReturnItems();
				Iterator<FgReturnItem> iterator = fgreturnItems.iterator();
				while (iterator.hasNext()) {
					FgReturnItem returnItem1 = (FgReturnItem) iterator.next();
					//returnItem = returnItem1;
					FgReturn fgreturnParent = new FgReturn();
					fgreturnParent.setId(fgReturn.getId());
					returnItem1.setFgReturn(fgreturnParent);
					returnItem1.setDeleteFlag("N");
					em.persist(returnItem1);
					//fgstockInIds.add(returnItem1.getFgStockIn().getId());
				}
				em.getTransaction().commit();
				fgreturnId = fgReturn.getId();
				//purchaseReturnJournalEntry(inventoryReturn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be inserted", e);
			} finally {
				em.close();
			}	
			return fgreturnId;
		}
		
		@Override
		public int updateFgReturn(FgReturn fgReturn)
				throws DAOException {
			EntityManager em = null;
			FgReturn oldfgStockInRet = null;
			int fgreturnId;
			
			try {
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				oldfgStockInRet = em.find(FgReturn.class, fgReturn.getId());
				oldfgStockInRet.setVendorId(fgReturn.getVendorId());
				oldfgStockInRet.setItemTotal(fgReturn.getItemTotal());
				oldfgStockInRet.setDisPer(fgReturn.getDisPer());
				oldfgStockInRet.setDisAmt(fgReturn.getDisAmt());
				oldfgStockInRet.setVatAmt(fgReturn.getVatAmt());
				oldfgStockInRet.setServiceTaxAmt(fgReturn.getServiceTaxAmt());
				oldfgStockInRet.setRoundOffAmt(fgReturn.getRoundOffAmt());
				oldfgStockInRet.setTotalPrice(fgReturn.getTotalPrice());
				oldfgStockInRet.setReturnTypeId(fgReturn.getReturnTypeId());
				oldfgStockInRet.setUpdatedBy(fgReturn.getUpdatedBy());
				oldfgStockInRet.setUpdatedDate(fgReturn.getUpdatedDate());			
				em.merge(oldfgStockInRet);
				
				Query query1 = em
						.createNativeQuery("delete from im_t_fg_purchase_return_items_c  where return_id=? and store_id=?");
				query1.setParameter(1, fgReturn.getId());
				query1.setParameter(2, fgReturn.getStoreId());
				query1.executeUpdate();
				
				// Persist each fg StockIn item
				List<FgReturnItem> fgstockInRetItems = fgReturn.getFgReturnItems();
				for (FgReturnItem fgstockInRetItem : fgstockInRetItems) {
					FgReturn fginventoryStckInRet = new FgReturn();
					fginventoryStckInRet.setId(fgReturn.getId());
					fgstockInRetItem.setFgReturn(fginventoryStckInRet);
					fgstockInRetItem.setDeleteFlag("N");
					em.persist(fgstockInRetItem);
				}
				em.getTransaction().commit();
				fgreturnId = fgReturn.getId();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be inserted", e);
			} finally {
				if (em != null) em.close();
			}
			
			return fgreturnId;
		}
		
		@Override
		public String deleteFgReturn(Integer fgreturnId, Integer storeId)
				throws DAOException {
			EntityManager em = null;
			FgReturn fgReturn=null;
			String message = "";
			try {
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				fgReturn = em.find(FgReturn.class, fgreturnId);
				fgReturn.setDeleteFlag("Y");
				List<FgReturnItem> lstItems = fgReturn.getFgReturnItems();
				Iterator<FgReturnItem> iterator2 = lstItems.iterator();
				while (iterator2.hasNext()) {
					FgReturnItem fgReturnItem = (FgReturnItem) iterator2.next();
					fgReturnItem.setDeleteFlag("Y");
				}
				em.getTransaction().commit();
				message = "success";
			} catch (Exception e) {
				e.printStackTrace();
				message = "failure";
				throw new DAOException("Check data to be deleted", e);
			} finally {
				if (em != null) em.close();
			}
			return message;
		}
		
		@Override
		public String approveFgReturn(FgReturn fgReturn)
				throws DAOException {
			EntityManager em = null;
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String status = "";
			FgReturn oldFgReturn = null;
			try {
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				oldFgReturn = em.find(FgReturn.class, fgReturn.getId());
				oldFgReturn.setApproved("Y");
				
				// start update inventory payment data
				List<FgInvoicePayment> paymentsByVendor = getFgPaymentInfoByVendor(fgReturn.getVendorId() + "",fgReturn.getStoreId() + "");
				Iterator<FgInvoicePayment> iterator4 = paymentsByVendor.iterator();
					while (iterator4.hasNext()) {
							boolean isReturn = false;
							FgInvoicePayment fgInvoicePayment = (FgInvoicePayment) iterator4.next();
								int fgstockinId = fgInvoicePayment.getFgStockInId();
								double billAmt = fgInvoicePayment.getBillAmount();
								double paidAmt = fgInvoicePayment.getPaidAmount();
								//double amtToPay = inventoryInvoicePayment.getAmountToPay();
								double totalReturnAmt = 0.0;
								List<FgReturnItem> fgreturnItemsList = oldFgReturn.getFgReturnItems();
								Iterator<FgReturnItem> iterator3 = fgreturnItemsList.iterator();
								while (iterator3.hasNext()) {
									FgReturnItem fgReturnItem = (FgReturnItem) iterator3.next();
									int fgstockInId = fgReturnItem.getFgStockIn().getId();
									if (fgstockinId == fgstockInId) {
										isReturn = true;
										totalReturnAmt = totalReturnAmt+ fgReturnItem.getTotalPrice();
									}

								}
								if (isReturn) {
									FgInvoicePayment fgreturnPayment = new FgInvoicePayment();
									fgreturnPayment.setFgStockInId(fgstockinId);
									fgreturnPayment.setFgStockInDate(fgInvoicePayment.getFgStockInDate());
									fgreturnPayment.setVendorId(fgInvoicePayment.getVendorId());
									fgreturnPayment.setStoreId(fgInvoicePayment.getStoreId());
									fgreturnPayment.setBillAmount(billAmt);
									fgreturnPayment.setPaidAmount(Math.round(totalReturnAmt));
									fgreturnPayment.setAmountToPay(billAmt - paidAmt- Math.round(totalReturnAmt));
									fgreturnPayment.setIsReturn("Y");
									fgreturnPayment.setCreditLimit(fgInvoicePayment.getCreditLimit());
									fgreturnPayment.setDeleteFlag("N");
									// persist
									em.persist(fgreturnPayment);
								}

							}
							// end update inventory payment data

				em.getTransaction().commit();
				status = "success";

			} catch (Exception e) {
				e.printStackTrace();
				status = "failure";
				throw new DAOException("Check fg purchase return approving to be updated", e);
			} finally {
				if (em != null) em.close();
			}
			
			/*if(("y").equalsIgnoreCase(inventoryReturn1.getIs_account())) {
				purchaseReturnJournalEntry(inventoryReturn1);
			}*/
			
			return status;
		}
		
		@Override
		public List<FgReturn> getFgReturn(Integer storeId,String date)
				throws DAOException {
			List<FgReturn> fgReturnLst = null;
			EntityManager em = null;
			try {
				em = entityManagerFactory.createEntityManager();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date finaldate = dateFormat.parse(date);
				TypedQuery<FgReturn> qry = em.createQuery("SELECT r FROM FgReturn r WHERE r.storeId=:storeid and r.date=:date and r.deleteFlag='N' order by r.id desc",FgReturn.class);
				qry.setParameter("storeid", storeId);
				qry.setParameter("date", finaldate);
				fgReturnLst = qry.getResultList();
				Iterator<FgReturn> iterator = fgReturnLst.iterator();
				while (iterator.hasNext()) {
					FgReturn fgReturn = iterator.next();
					int vendorId = fgReturn.getVendorId();
					TypedQuery<InventoryVendor> qryGetVndr = em.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",InventoryVendor.class);
					qryGetVndr.setParameter("vendorId", vendorId);
					InventoryVendor vendor = qryGetVndr.getSingleResult();
					String vendrName = vendor.getName();
					fgReturn.setVendorName(vendrName);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be inserted", e);
			} finally {
				if (em != null) em.close();
			}
			return fgReturnLst;
		}
		
		@Override
		public FgReturn getFgReturnById(Integer id, Integer storeId)
				throws DAOException {
			FgReturn fgReturn = null;
			EntityManager em = null;
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				TypedQuery<FgReturn> qry = em.createQuery("SELECT r FROM FgReturn r WHERE r.id=:id and r.storeId=:storeid",FgReturn.class);
				qry.setParameter("storeid", storeId);
				qry.setParameter("id", id);
				fgReturn = qry.getSingleResult();
				
				int vendorId = fgReturn.getVendorId();
				TypedQuery<InventoryVendor> qryGetVndr = em.createQuery("SELECT v FROM InventoryVendor v WHERE v.id=:vendorId",InventoryVendor.class);
				qryGetVndr.setParameter("vendorId", vendorId);
				InventoryVendor vendor = qryGetVndr.getSingleResult();
				String vendrName = vendor.getName();
				fgReturn.setVendorName(vendrName);
				
				//get the cur stock
				List<FgReturnItem> fgretItems = fgReturn.getFgReturnItems();
				Iterator<FgReturnItem> iterator = fgretItems.iterator();
				while (iterator.hasNext()) {
					FgReturnItem fgReturnItem = (FgReturnItem) iterator.next();
					/*FgStockIn fgStockIn = fgReturnItem.getFgStockIn();
					fgReturnItem.setFgStockIn(fgStockIn);*/
					fgReturnItem.setFgStockInId(fgReturnItem.getFgStockIn().getId());
					FgItemCurrentStock fgcurstock=getFgCurrentStockByItem(storeId,fgReturnItem.getMenuItem().getId()).get(0);
					fgReturnItem.setCurStock(fgcurstock.getCurStock());	
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be inserted", e);
			} finally {
				em.close();
			}
			return fgReturn;
		}
		
		public List<FgInvoicePayment> getFgPaymentInfoByVendor(
				String vendorId, String storeId) throws DAOException {

			EntityManager em = null;
			List<FgInvoicePayment> fgInvoicePaymentsLst = null;
			List<FgInvoicePayment> fgInvoicePaymentsList = new ArrayList<FgInvoicePayment>();
			Set<Integer> fgstockInTotalIdSet = new HashSet<Integer>();

			double billAmt = 0.0;
			try {
				int vendorid = Integer.parseInt(vendorId);
				int storeid = Integer.parseInt(storeId);

				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();

				TypedQuery<FgInvoicePayment> qry = em.createQuery("SELECT p FROM FgInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N'",FgInvoicePayment.class);
				qry.setParameter("vendorId", vendorid);
				qry.setParameter("storeid", storeid);
				fgInvoicePaymentsLst = qry.getResultList();
				Iterator<FgInvoicePayment> iterator = fgInvoicePaymentsLst.iterator();
				while (iterator.hasNext()) {
					FgInvoicePayment fgInvoicePayment = (FgInvoicePayment) iterator.next();
					// add to set to get all separate stock in ids
					fgstockInTotalIdSet.add(fgInvoicePayment.getFgStockInId());
				}
				// convert set to list
				//stockInIdTotalLst = new ArrayList<Integer>(stockInTotalIdSet);

				// stock in ids with due
				if (fgstockInTotalIdSet.size() > 0) {
					Iterator<Integer> iterator3 = fgstockInTotalIdSet.iterator();
					while (iterator3.hasNext()) {
						Integer fgstockInId = (Integer) iterator3.next();
						Iterator<FgInvoicePayment> iterator4 = fgInvoicePaymentsLst.iterator();
						double paidAmt = 0.0;
						FgInvoicePayment fgInvoicePayment1 = null;
						int id = 0;
						int storeId1 = 0;
						int vendorId1 = 0;
						double creditLimit = 0.0;
						String stockInDate = "";
						while (iterator4.hasNext()) {
							fgInvoicePayment1 = (FgInvoicePayment) iterator4.next();
							int fgstckInId = fgInvoicePayment1.getFgStockInId();
							if (fgstockInId == fgstckInId) {
								id = fgInvoicePayment1.getId();
								vendorId1 = fgInvoicePayment1.getVendorId();
								creditLimit = fgInvoicePayment1.getCreditLimit();
								stockInDate = fgInvoicePayment1.getFgStockInDate();
								storeId1 = fgInvoicePayment1.getStoreId();
								billAmt = fgInvoicePayment1.getBillAmount();
								paidAmt = paidAmt+ fgInvoicePayment1.getPaidAmount();

							}

						}
						FgInvoicePayment fgInvoicePayment2 = new FgInvoicePayment();
						fgInvoicePayment2.setId(id);
						fgInvoicePayment2.setStoreId(storeId1);
						fgInvoicePayment2.setVendorId(vendorId1);
						fgInvoicePayment2.setFgStockInId(fgstockInId);
						fgInvoicePayment2.setCreditLimit(creditLimit);
						fgInvoicePayment2.setBillAmount(billAmt);
						fgInvoicePayment2.setPaidAmount(paidAmt);
						fgInvoicePayment2.setAmountToPay(billAmt - paidAmt);
						fgInvoicePayment2.setFgStockInDate(stockInDate);
						fgInvoicePaymentsList.add(fgInvoicePayment2);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be updated", e);
			} finally {
				em.close();
			}

			return fgInvoicePaymentsList;
		}
		
		//added on 18.06.2019
		@Override
		public List<FgInvoicePayment> getFgCreditInfoByVendor(Integer vendorId,
				Integer storeId) throws DAOException {

			EntityManager em = null;
			boolean isCredit = true;
			List<FgInvoicePayment> inventoryStckInLst = null;
			List<FgInvoicePayment> inventoryInvoicePayments = null;
			List<FgInvoicePayment> invoicePaymentsNoDueList = new ArrayList<FgInvoicePayment>();
			List<FgInvoicePayment> invoicePaymentsDueList = new ArrayList<FgInvoicePayment>();
			List<Integer> stockInIdNoDueLst = new ArrayList<Integer>();
			Set<Integer> stockInTotalIdSet = new HashSet<Integer>();
			Set<Integer> stockInIdNoDueSet = new HashSet<Integer>();

			double billAmt = 0.0;
			try {
				int vendorid = (vendorId);
				int storeid = (storeId);

				
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();

				TypedQuery<FgInvoicePayment> qry = em
						.createQuery("SELECT p FROM FgInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N'",
								FgInvoicePayment.class);
				qry.setParameter("vendorId", vendorid);
				qry.setParameter("storeid", storeid);
				inventoryStckInLst = qry.getResultList();

				Iterator<FgInvoicePayment> iterator = inventoryStckInLst
						.iterator();
				while (iterator.hasNext()) {
					FgInvoicePayment inventoryInvoicePayment = iterator.next();
					// add to set to get all separate stock in ids
					stockInTotalIdSet.add(inventoryInvoicePayment.getFgStockInId());
				}

				// get list of payments with no due
				try {

					TypedQuery<FgInvoicePayment> qryNodue = em
							.createQuery("SELECT p FROM FgInvoicePayment p WHERE p.vendorId=:vendorId and p.storeId=:storeid and p.deleteFlag='N' and p.amountToPay=0.0",
									FgInvoicePayment.class);
					qryNodue.setParameter("vendorId", vendorid);
					qryNodue.setParameter("storeid", storeid);
					invoicePaymentsNoDueList = qryNodue.getResultList();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				if (invoicePaymentsNoDueList.size() > 0) {

					Iterator<FgInvoicePayment> iterator1 = invoicePaymentsNoDueList
							.iterator();
					while (iterator1.hasNext()) {
						FgInvoicePayment paymentNoDue = (FgInvoicePayment) iterator1
								.next();
						stockInIdNoDueLst.add(paymentNoDue.getFgStockInId());

					}

				}
				// convert no due stockin ids list to set
				if (stockInIdNoDueLst.size() > 0) {
					stockInIdNoDueSet = new HashSet<Integer>(stockInIdNoDueLst);
				}
				// perform a difference between total set and no due set
				if (stockInIdNoDueSet.size() > 0) {
					stockInTotalIdSet.removeAll(stockInIdNoDueSet);
				}
				Set<Integer> stockInIdDueSet = stockInTotalIdSet;
				// stock in ids with due
				if (stockInIdDueSet.size() > 0) {
					Iterator<Integer> iterator3 = stockInIdDueSet.iterator();
					while (iterator3.hasNext()) {
						Integer stockInIdDue = (Integer) iterator3.next();

						Iterator<FgInvoicePayment> iterator4 = inventoryStckInLst
								.iterator();
						double paidAmt = 0.0;
						double retAmt = 0.0;
						double paidAmnt = 0.0;
						FgInvoicePayment inventoryInvoicePayment1 = null;
						int id = 0;
						int storeId1 = 0;
						int poId = 0;
						int vendorId1 = 0;
						double creditLimit = 0.0;
						String stockInDate = "";
						while (iterator4.hasNext()) {
							inventoryInvoicePayment1 = (FgInvoicePayment) iterator4
									.next();
							int stckInId = inventoryInvoicePayment1.getFgStockInId();

							if (stockInIdDue == stckInId) {
								//poId = inventoryInvoicePayment1.getPoId();
								id = inventoryInvoicePayment1.getId();
								vendorId1 = inventoryInvoicePayment1.getVendorId();
								creditLimit = inventoryInvoicePayment1
										.getCreditLimit();
								stockInDate = inventoryInvoicePayment1
										.getFgStockInDate();
								storeId1 = inventoryInvoicePayment1.getStoreId();
								billAmt = inventoryInvoicePayment1.getBillAmount();
								paidAmnt = inventoryInvoicePayment1.getPaidAmount();
								String isRet = inventoryInvoicePayment1
										.getIsReturn();

								if (isRet.equalsIgnoreCase("N")) {
									paidAmt = paidAmt + paidAmnt;
								}

								else if (isRet.equalsIgnoreCase("Y")) {
									retAmt = retAmt + paidAmnt;
								}

							}

						}
						FgInvoicePayment inventoryInvoicePayment2 = new FgInvoicePayment();
						inventoryInvoicePayment2.setId(id);
						inventoryInvoicePayment2.setStoreId(storeId1);
						//inventoryInvoicePayment2.setPoId(poId);
						inventoryInvoicePayment2.setVendorId(vendorId1);
						inventoryInvoicePayment2.setFgStockInId(stockInIdDue);
						inventoryInvoicePayment2.setCreditLimit(creditLimit);
						inventoryInvoicePayment2.setBillAmount(billAmt);
						inventoryInvoicePayment2.setPaidAmount(paidAmt);
						inventoryInvoicePayment2.setReturnAmount(retAmt);
						inventoryInvoicePayment2.setAmountToPay(billAmt - paidAmt
								- retAmt);
						inventoryInvoicePayment2.setFgStockInDate(stockInDate);
						invoicePaymentsDueList.add(inventoryInvoicePayment2);

					}
				} else {
					isCredit = false;
				}

				if (isCredit) {
					inventoryInvoicePayments = invoicePaymentsDueList;
				} else {
					inventoryInvoicePayments = new ArrayList<FgInvoicePayment>();
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check getFgCreditInfoByVendor to be updated", e);
			} finally {
				if (em != null) em.close();
			}

			return inventoryInvoicePayments;
		}
		
		@Override
		public String invoiceFgPayment(FgInvoicePayment payment)
				throws DAOException {
			EntityManager em = null;
			String status = "success";
			try {

				// persist the payment
				payment.setDeleteFlag("N");
				payment.setIsReturn("N");

				
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				em.persist(payment);

				em.getTransaction().commit();

			} catch (Exception e) {
				e.printStackTrace();
				status = "failure";
				throw new DAOException("Check data to be inserted", e);
			} finally {
				if (em != null) em.close();
			}
			
			/*if(("y").equalsIgnoreCase(payment.getIs_account())) {
				VendorPaymentjournalEntry(payment);
			}*/
			
			return status;
		}
		
		//added on 30.07.2019
		@Override
		public List<InventoryItems> getRawItemQtytobeSoldOut(Integer storeId, String date)
				throws DAOException {
			

			EntityManager em = null;
			EntityManager em1 = null;
			CallableStatement callableStatement = null;
			Connection connection = null;
			ResultSet rs = null;
			List<InventoryItems> inventoryItemsList=new ArrayList<>();
			try {
				java.sql.Date asonDate = DateUtil.convertStringDateToSqlDate(
						date, "yyyy-MM-dd");
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();
				em1 = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				em1.getTransaction().begin();
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();

				callableStatement = connection.prepareCall(StoredProcedures.PROC_GET_RAW_ITEM_QTY_TOBE_STOCK_OUT);
				callableStatement.setInt(1, storeId);
				callableStatement.setDate(2,asonDate);
				callableStatement.execute();
				rs = callableStatement.getResultSet();

				ReflectionResultSetMapper<InventoryItems> resultSetMapper1 = new ReflectionResultSetMapper<InventoryItems>(
						InventoryItems.class);

				while (rs.next()) {
					InventoryItems inventoryItems=new InventoryItems();
					inventoryItems.setId(rs.getInt(1));
					InventoryType type=em1.find(InventoryType.class, rs.getInt(2));
					inventoryItems.setInventoryType(type);
					inventoryItems.setCode(rs.getString(3));
					inventoryItems.setName(rs.getString(4));
					inventoryItems.setQuantity(rs.getDouble(5));
					inventoryItems.setDailyRequirement(rs.getDouble(6));
					inventoryItems.setUnit(rs.getString(7));
					inventoryItems.setMetricUnitId(rs.getInt(8));
					inventoryItems.setRate(rs.getDouble(9));
					inventoryItems.setBarcode(rs.getString(10));
					inventoryItems.setVendorId(rs.getInt(11));
					inventoryItems.setShippingCharge(rs.getDouble(12));
					inventoryItems.setTax(rs.getString(13));
					inventoryItems.setIsTaxExclusive(rs.getString(14));
					inventoryItems.setTaxRate(rs.getDouble(15));
					inventoryItems.setStoreId(rs.getInt(16));
					inventoryItems.setIsStockable(rs.getString(17));
					inventoryItems.setDeleteFlag(rs.getString(18));
					inventoryItems.setRawQty(rs.getDouble(19));
					//inventoryItems = resultSetMapper1.mapRow(rs);
					inventoryItemsList.add(inventoryItems);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("In DAOException", e);

			} finally {
				try {
					if(rs!=null)
						rs.close();
					if(callableStatement!=null)
						callableStatement.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				em.close();
				em1.close();
			}
			return inventoryItemsList;

		}
}