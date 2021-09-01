import java.util.ArrayList;
import java.util.List;

import com.botree.restaurantapp.model.InventoryItems;
import com.botree.restaurantapp.model.InventoryPurchaseOrder;
import com.botree.restaurantapp.model.InventoryPurchaseOrderItem;
import com.botree.restaurantapp.webservice.impl.InventoryWSImpl;

public class WebserviceTest {

	public void testPurchaseOrder() {

		// test purchase order save
		InventoryWSImpl inventoryWSImpl = new InventoryWSImpl();
		InventoryPurchaseOrder purchaseOrder = new InventoryPurchaseOrder();
		List<InventoryPurchaseOrderItem> poItemList = new ArrayList<InventoryPurchaseOrderItem>();
		String time = "15:42:00";
		java.sql.Time myTime = java.sql.Time.valueOf(time);
		//purchaseOrder.setTime(myTime);
		purchaseOrder.setUserId("admin");
		purchaseOrder.setShippingCharge(160.52);
		//purchaseOrder.setTotalQuantity(12);
		purchaseOrder.setStoreId(29);
		purchaseOrder.setPoBy("admin");
		purchaseOrder.setApprovedBy("admin");
		purchaseOrder.setCreatedBy("admin");
		purchaseOrder.setCreatedDate("26/02/2015");
		purchaseOrder.setUpdatedBy("admin");
		purchaseOrder.setUpdatedDate("26/02/2015");
		
		//inventoryWSImpl.createPO(purchaseOrder);
		
		for (int i = 0; i < 2; i++) {

			InventoryPurchaseOrderItem purchaseOrderItem = new InventoryPurchaseOrderItem();

			InventoryItems item = new InventoryItems();
			item.setId(1);
			purchaseOrderItem.setInventoryItems(item);
			purchaseOrderItem.setVendorId(1);
			purchaseOrderItem.setItemQuantity(25.0);
			purchaseOrderItem.setRate(120.00);
			purchaseOrderItem.setItemTotalPrice(purchaseOrderItem.getRate()
					* purchaseOrderItem.getItemQuantity());
			purchaseOrderItem.setStoreId(29);
			purchaseOrderItem.setCreatedBy("admin");
			purchaseOrderItem.setCreatedDate("26/02/2015");
			purchaseOrderItem.setUpdatedBy("admin");
			purchaseOrderItem.setUpdatedDate("26/02/2015");

		}

	}
}
