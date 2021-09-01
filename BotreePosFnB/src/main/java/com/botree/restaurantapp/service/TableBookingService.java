package com.botree.restaurantapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botree.restaurantapp.dao.TableBookingDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.TableMaster;
import com.botree.restaurantapp.model.dto.BookTableBean;
import com.botree.restaurantapp.model.dto.BookTableFormBean;
import com.botree.restaurantapp.model.dto.Table;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.exception.ServiceException;

@Service
public class TableBookingService {
  
	private final static Logger logger = LogManager.getLogger(TableBookingService.class);
	
  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();
  
	private final static String GET_AVAILABLE_TABLES = "select id_pk, table_no, table_description, seating_capacity from tb_table_master where available_for_online_booking=true and id_pk not in (select distinct(table_id) from tb_transaction where is_available=false and booking_date=? and ((? >= booking_time_from and ? < booking_time_to) or (? > booking_time_from and ? <= booking_time_to)))";
	private final static String CONFIRM_BOOKING_TABLE = "INSERT INTO tb_transaction (user_id, table_id, booking_date, booking_time_from, booking_time_to, transaction_timestamp, is_available) VALUES (? ,? ,? ,? ,? ,? ,? )";
	private final static String GET_TABLE_LIST = "select * from tb_table_master where store_id = ? and delete_flag=? order by table_no";

  @Autowired
	private TableBookingDAO tableBookingDAO;

	// private final static String HNT_TABLE_MASTER =
	// "SELECT ttm,tt FROM TbTableMaster ttm left join TblTransaction tt on ttm.id=tt.tableId";

	public List<Table> getAvailableTables(Date fromDate, Date toDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
    EntityManager em = null;

		List<Table> tables = new ArrayList<Table>();

		java.sql.Date date = new java.sql.Date(fromDate.getTime());
		Time fromTime = new Time(fromDate.getTime());
		Time toTime = new Time(toDate.getTime());

		try {
      em = entityManagerFactory.createEntityManager();

      //get connection object from entity manager
      Session ses = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

      conn = sessionFactory.getConnectionProvider().getConnection();
		  
			ps = conn.prepareStatement(GET_AVAILABLE_TABLES);
			ps.setDate(1, date);
			ps.setTime(2, fromTime);
			ps.setTime(3, fromTime);
			ps.setTime(4, toTime);
			ps.setTime(5, toTime);
			rs = ps.executeQuery();

			while (rs.next()) {
				tables.add(new Table(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getInt(4)));
			}
			logger.debug("Available table list populated");

			return tables;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		} finally {
		  
	    try {
	      if (rs != null) { rs.close(); }
        if (ps != null) { ps.close(); }
        if (conn != null) { conn.close(); }
	    } catch (Exception e) {
	      logger.error("Exception", e);
	    }
		}
	}

	public List<Table> getSuggestedTables(int noOfPerson,
			List<Table> availableTables) {
		List<Table> suggestedTables = new ArrayList<Table>();
		if (isSufficientSeatsAvailable(noOfPerson, availableTables)) {

			Collections.sort(availableTables, new Comparator<Table>() {
				@Override
				public int compare(Table o1, Table o2) {
					return o1.getSeatingCapacity() > o2.getSeatingCapacity() ? 1
							: -1;
				}
			});
			suggestedTables = this.getTablesList(noOfPerson,
					new ArrayList<Table>(), availableTables);
		}
		return suggestedTables;
	}

	private boolean isSufficientSeatsAvailable(int noOfPerson,
			List<Table> availableTables) {
		int totalAvailableSeat = 0;
		for (Table tempTable : availableTables) {
			totalAvailableSeat += tempTable.getSeatingCapacity();
		}

		return noOfPerson <= totalAvailableSeat;
	}

	private List<Table> getTablesList(int noOfPerson,
			List<Table> gatheredTable, List<Table> availableTables) {
		int counter = 1;
		for (Table tempTable : availableTables) {
			if (noOfPerson <= tempTable.getSeatingCapacity()) {
				gatheredTable.add(tempTable);
				break;
			} else if (availableTables.size() == counter) {
				gatheredTable.add(tempTable);
				availableTables.remove(counter - 1);
				this.getTablesList(
						noOfPerson -= tempTable.getSeatingCapacity(),
						gatheredTable, availableTables);
				break;
			}
			counter++;
		}
		return gatheredTable;
	}

	public boolean confirmTableBooking(BookTableFormBean btfb) {
		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint sp = null;
    EntityManager em = null;

		try {
      em = entityManagerFactory.createEntityManager();

			BookTableBean btb = new BookTableBean(btfb);
			List<Table> tables = btfb.getTable();

      //get connection object from entity manager
      Session ses = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

      conn = sessionFactory.getConnectionProvider().getConnection();
			conn.setAutoCommit(false);
			sp = conn.setSavepoint();

			for (Table table : tables) {
				ps = conn.prepareStatement(CONFIRM_BOOKING_TABLE);
				ps.setInt(1, btfb.getUserId());
				ps.setInt(2, table.getTableId());
				ps.setDate(3, new java.sql.Date(btb.getBookingDateObjFrom()
						.getTime()));
				ps.setTime(4, new Time(btb.getBookingDateObjFrom().getTime()));
				ps.setTime(5, new Time(btb.getBookingDateObjTo().getTime()));
				ps.setTimestamp(6, new Timestamp(new Date().getTime()));
				ps.setBoolean(7, false);
				ps.executeUpdate();
			}
			conn.commit();
			logger.debug("Booking completed");
			return true;
		} catch (Exception e) {
			logger.error("Exception", e);
			try {
				conn.rollback(sp);
			} catch (Exception e1) {
			}

			return false;
		} finally {
      try {
        if (ps != null) { ps.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        logger.error("Exception", e);
      }
		}
	}

	public List<Table> getTableList(int storeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
    EntityManager em = null;
    
		List<Table> tables = new ArrayList<Table>();

		try {
		  
      em = entityManagerFactory.createEntityManager();

      //get connection object from entity manager
      Session ses = (Session) em.getDelegate();
      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();

      conn = sessionFactory.getConnectionProvider().getConnection();
      
			String delFlag = "N";
			ps = conn.prepareStatement(GET_TABLE_LIST);
			ps.setInt(1, storeId);
			ps.setString(2, delFlag);
			rs = ps.executeQuery();

			while (rs.next()) {
				tables.add(new Table(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getString("status"), rs
						.getShort("available_for_online_booking"), rs
						.getString("delete_flag"), rs.getString("style_id"), rs
						.getString("multi_order"), rs.getInt("x_pos"), rs
						.getInt("y_pos"), rs.getInt("section"), rs
						.getInt("store_id")));
			}

			logger.debug("Table list populated, storeId=" + storeId);
			return tables;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		} finally {
      try {
        if (rs != null) { rs.close(); }
        if (ps != null) { ps.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        logger.error("Exception", e);
      }

		}
	}

	public String updateTableStatus(TableMaster table) throws ServiceException {
		String status = "";
		try {

		  logger.debug("com.sharobi.restaurantapp.service.TableBookingService.updateTableStatus(TableMaster) :: ", table);
			// update table
			status = tableBookingDAO.updateTableStatus(table);

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occurred while trying to update a table", e);

		}
		return status;

	}

	public String addTable(TableMaster tableMaster) throws ServiceException {
		String message = "";
		try {
			message = tableBookingDAO.addTable(tableMaster);

		} catch (DAOException e) {
			throw new ServiceException("error creating TableMaster", e);

		}
		return message;
	}

	public String updateTable(TableMaster tableMaster) throws ServiceException {

    logger.debug("com.sharobi.restaurantapp.service.TableBookingService.updateTable(TableMaster) :: ", tableMaster);
    
		String messaString = "";
		try {
			messaString = tableBookingDAO.updateTable(tableMaster);

		} catch (DAOException e) {
			throw new ServiceException("error updating table", e);

		}
		return messaString;
	}

	public String updateTablePosition(List<TableMaster> tableList)
			throws ServiceException {

		String messaString = "";
		try {
			messaString = tableBookingDAO.updateTablePosition(tableList);

		} catch (DAOException e) {
			throw new ServiceException("error updating table", e);

		}
		return messaString;
	}

	public String deleteTable(String id, String storeId)
			throws ServiceException {
		String message = "";
		try {
			message = tableBookingDAO.deleteTable(id, storeId);

		} catch (DAOException e) {
			throw new ServiceException("deleteTable error", e);

		}
		return message;
	}

	public String addMultipleTable(String noOfTable, String capacity,
			String storeId) throws ServiceException {
		String message = "";
		try {
			message = tableBookingDAO.addMultipleTable(noOfTable, capacity, storeId);

		} catch (DAOException e) {
			throw new ServiceException("deleteTable error", e);

		}
		return message;
	}

	public String setMultiOrder(String id, String status, String storeId)
			throws ServiceException {
		String message = "";
		try {
			message = tableBookingDAO.setMultiOrder(id, status, storeId);

		} catch (DAOException e) {
			throw new ServiceException("error", e);

		}
		return message;
	}
}
