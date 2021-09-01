package com.botree.restaurantapp.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.TableMaster;
import com.botree.restaurantapp.model.util.PersistenceListener;

@Component("tableBookingDAO")
public class TableBookingDAOImpl implements TableBookingDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public String updateTableStatus(TableMaster table) throws DAOException {
		String status = "";
		EntityManager em = null;
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TableMaster table1 = em.find(TableMaster.class, table.getId());
			table1.setStatus(table.getStatus());
			em.merge(table1);
			em.getTransaction().commit();

			status = "success";

		} catch (Exception e) {
			status = "failure";
			throw new DAOException("Check data to be inserted", e);

		} finally {
			em.close();
		}

		return status;

	}

	@Override
	public String addTable(TableMaster tableMaster) throws DAOException {
		EntityManager em = null;
		String message = "";
		TableMaster tableToBeDeleted;
		try {
			int availblForOnlyn = 1;
			short s1 = (short) availblForOnlyn;
			// persist the table
			tableMaster.setAvailableForOnlineBbooking(s1);
			tableMaster.setStyleId("1");
			tableMaster.setDeleteFlag("N");
			tableMaster.setStatus("Y");
			tableMaster.setMultiOrder("N");
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			try {
				Query qry = em
						.createQuery("SELECT t FROM TableMaster t WHERE t.storeId=:storeid and t.tableNo=:tableNo");
				qry.setParameter("storeid", tableMaster.getStoreId());
				qry.setParameter("tableNo", tableMaster.getTableNo());

				tableToBeDeleted = (TableMaster) qry.getSingleResult();

				if (tableToBeDeleted != null && tableToBeDeleted.getId() > 0) {

					em.remove(tableToBeDeleted);
				}
				em.getTransaction().commit();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				em.getTransaction().commit();
				// em.close();
			}
			em.getTransaction().begin();
			em.persist(tableMaster);

			em.getTransaction().commit();
			message = "Table Created Successfully.";
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return message;
	}

	@Override
	public String updateTable(TableMaster tableMaster) throws DAOException {
		EntityManager em = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update table
			em.merge(tableMaster);
			em.getTransaction().commit();

			messaString = "Table Updated Successfully.";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Table Updation Failed.";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return messaString;
	}

	@Override
	public String updateTablePosition(List<TableMaster> tableList)
			throws DAOException {
		EntityManager em = null;
		TableMaster tableToBeUpdated = null;

		String messaString = "";
		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// update table
			Iterator<TableMaster> iterator = tableList.iterator();
			while (iterator.hasNext()) {
				TableMaster table = (TableMaster) iterator.next();
				int id=table.getId();
				int storeId=table.getStoreId();
				Query qry = em
						.createQuery("SELECT t FROM TableMaster t WHERE t.storeId=:storeid and t.id=:id");
				qry.setParameter("storeid", storeId);
				qry.setParameter("id", id);
				tableToBeUpdated = (TableMaster) qry.getSingleResult();
				tableToBeUpdated.setxPos(table.getxPos());
				tableToBeUpdated.setyPos(table.getyPos());
				em.merge(tableToBeUpdated);

			}

			em.getTransaction().commit();

			messaString = "Table position Updated Successfully.";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Table position Updation Failed.";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return messaString;
	}

	@Override
	public String deleteTable(String id, String storeId) throws DAOException {

		int tblId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		TableMaster tableToBeDeleted = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT t FROM TableMaster t WHERE t.storeId=:storeid and t.id=:id");
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", tblId);

			tableToBeDeleted = (TableMaster) qry.getSingleResult();
			tableToBeDeleted.setDeleteFlag("Y");
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return message;

	}

	@Override
	public String addMultipleTable(String noOfTable, String capacity,
			String storeId) throws DAOException {

		int noOfTable1 = Integer.parseInt(noOfTable);
		int capacity1 = Integer.parseInt(capacity);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			int availblForOnlyn = 1;
			short s1 = (short) availblForOnlyn;
			// create tables
			for (int i = 0; i < noOfTable1; i++) {

				TableMaster tableMaster = new TableMaster();
				tableMaster.setAvailableForOnlineBbooking(s1);
				tableMaster.setSeatingCapacity(capacity1);
				tableMaster.setStyleId("1");
				tableMaster.setDeleteFlag("N");
				tableMaster.setStatus("Y");
				tableMaster.setMultiOrder("N");
				tableMaster.setSection(0);
				tableMaster.setxPos(0);
				tableMaster.setyPos(0);
				tableMaster.setStoreId(storeid);
				// calculate table no.
				if (i <= 8) {
					String tabNo = "0" + "" + (i + 1);
					tableMaster.setTableNo(tabNo);
					tableMaster.setTableDescription(tabNo);
				} else {
					String tabNo = "" + (i + 1);
					tableMaster.setTableNo(tabNo);
					tableMaster.setTableDescription(tabNo);
				}
				em.persist(tableMaster);
			}

			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return message;

	}

	@Override
	public String setMultiOrder(String id, String status, String storeId)
			throws DAOException {

		int tblId = Integer.parseInt(id);
		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		TableMaster tableToUpdated = null;
		String message = "";

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT t FROM TableMaster t WHERE t.storeId=:storeid and t.id=:id");
			qry.setParameter("storeid", storeid);
			qry.setParameter("id", tblId);

			tableToUpdated = (TableMaster) qry.getSingleResult();
			tableToUpdated.setMultiOrder(status);
			em.getTransaction().commit();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return message;

	}

	public TableMaster getTableById(String tableNo, String storeId)
			throws DAOException {

		int storeid = Integer.parseInt(storeId);
		EntityManager em = null;
		TableMaster table = null;

		try {
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			Query qry = em
					.createQuery("SELECT t FROM TableMaster t WHERE t.storeId=:storeid and t.tableNo=:tableNo");
			qry.setParameter("storeid", storeid);
			qry.setParameter("tableNo", tableNo);

			table = (TableMaster) qry.getSingleResult();
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return table;

	}

}
