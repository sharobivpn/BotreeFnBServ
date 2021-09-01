package com.botree.restaurantapp.controller.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.botree.restaurantapp.dao.BillingDAO;
import com.botree.restaurantapp.dao.BillingDAOImpl;
import com.botree.restaurantapp.dao.OrdersDAO;
import com.botree.restaurantapp.dao.OrdersDAOImpl;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.BillSplitER;
import com.botree.restaurantapp.model.BillSplitManual;
import com.botree.restaurantapp.model.ItemsTableWise;
import com.botree.restaurantapp.model.OrderItem;
import com.botree.restaurantapp.model.OrderMaster;
import com.botree.restaurantapp.model.OrderType;
import com.botree.restaurantapp.model.StoreMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;

@ManagedBean(name = "orderConverter")
//@FacesConverter(value="orderConverter") // TODO: need to check, if it is required.
public class OrderConverter implements Converter {
	private final static Logger logger = LogManager.getLogger(OrderConverter.class);
	private EntityManagerFactory entityManagerFactory;
	private BillingDAO billingDAO = new BillingDAOImpl();

	public OrderConverter() {

	}

	private OrdersDAO ordersDAO = new OrdersDAOImpl();
	private OrderMaster orderMaster = new OrderMaster();
	List<ItemsTableWise> itemsTableWisesLst = new ArrayList<ItemsTableWise>();

	@Override
	public Object getAsObject(	FacesContext context,
								UIComponent component,
								String value) {
		logger.debug("In getAsObject");
		EntityManager em = null;
		OrderType orderedType = null;
		if (value == null || value.isEmpty()) {
			logger.debug("vlue:" + value);
			return null;
		}

		String orderId = value.toString();
		logger.debug("order id:" + orderId);
		int order_id = Integer.parseInt(orderId);
		logger.debug("order id:" + order_id);
		try {
			orderMaster = ordersDAO.getOrderById(order_id);
			List<OrderItem> orerItemList = orderMaster.getOrderitem();
			Iterator<OrderItem> orderItemIterator = orerItemList.iterator();
			while (orderItemIterator.hasNext()) {
				OrderItem orderItem = (OrderItem) orderItemIterator.next();
				int orderTypeId = orderItem.getOrdertype();
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();
				Query qry = em.createQuery("SELECT o FROM OrderType o where o.id=:param");
				qry.setParameter("param", orderTypeId);
				orderedType = (OrderType) qry.getSingleResult();
				orderItem.setOrderTypeName(orderedType.getOrderTypeName());
			}
			// start code to get list of bill spilt by ER
			List<BillSplitER> billSplitERs = billingDAO.getBillSplitERByOrderId(orderId);
			orderMaster.setBillSplitERList(billSplitERs);

			// end code to get list of bill spilt by ER

			// start code to get list of bill spilt manual
			List<BillSplitManual> billSplitmanuals = billingDAO.getBillSplitManualByOrderId(orderId);
			orderMaster.setBillSplitManualList(billSplitmanuals);

			// end code to get list of bill spilt manual

			//code specific to anglo indian restaurant requirement to print bill table wise
			FacesContext context1 = FacesContext.getCurrentInstance();
			StoreMaster storeMaster = (StoreMaster) context1.getExternalContext().getSessionMap().get("selectedstore");
			int storeId = 0;
			if (storeMaster != null)
				storeId = storeMaster.getId();
			if (storeId == 29) {
				Set<String> setOfTbls = new LinkedHashSet<String>();
				String[] arrTabls = null;
				String childTbls = orderMaster.getChildTables();
				List<OrderItem> items = orderMaster.getOrderitem();
				Iterator<OrderItem> iterator1 = items.iterator();
				while (iterator1.hasNext()) {
					OrderItem orderItem = (OrderItem) iterator1.next();
					String tabNo = orderItem.getTableNo();
					setOfTbls.add(tabNo);
				}
				if (setOfTbls.size() > 0) {
					arrTabls = setOfTbls.toArray(new String[0]);
				}
				if (arrTabls != null && arrTabls.length > 0) {
					//String str="01/02/03";
					//get all tables
					String[] arr = childTbls.split("/");
					for (int i = 0; i < arrTabls.length; i++) {
						ItemsTableWise itemsTableWise = new ItemsTableWise();
						List<OrderItem> orderitemLst;
						double totalBillByTabl = 0.0;
						String chldTbl = arrTabls[i];
						//get all items for this table
						orderitemLst = ordersDAO.getAllItemsByOrderNTable(chldTbl, order_id);
						if (orderitemLst != null && orderitemLst.size() > 0) {
							Iterator<OrderItem> iterator = orderitemLst.iterator();
							while (iterator.hasNext()) {
								OrderItem orderItem = (OrderItem) iterator.next();
								double totalPriceByItem = orderItem.getTotalPriceByItem();
								totalBillByTabl = totalBillByTabl + totalPriceByItem;

							}

							itemsTableWise.setTableNo(chldTbl);
							itemsTableWise.setOrderitem(orderitemLst);
							itemsTableWise.setBillAmount(totalBillByTabl);

							itemsTableWisesLst.add(itemsTableWise);

						}

					}
					//set the items table wise list in order
					if (itemsTableWisesLst.size() > 0) {
						orderMaster.setItemsTableWises(itemsTableWisesLst);

					}
				}

			}

		} catch (DAOException e) {
			logger.error("order id: " + order_id, e);
		} finally {
			if (em != null)
			em.close();
		}
		return orderMaster;
	}

	@Override
	public String getAsString(	FacesContext context,
								UIComponent component,
								Object value) {
		logger.debug("In getAsString");
		if (value == null) {
			return null;
		}
		return "";
	}
}
