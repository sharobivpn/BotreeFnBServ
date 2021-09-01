package com.botree.restaurantapp.commonUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* Singleton class. */
public class DbConnection {
	private final static Logger logger = LogManager.getLogger(DbConnection.class);
	private static DataSource ds = null;

	public synchronized static void init() {
		if (ds == null) {
			try {
				ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/ResApp");
				printDbConnMetadata();
			} catch (Exception e) {

				logger.error("Unable to create datasource", e);
			}
		}
	}

	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (Exception e) {
			logger.error("Unable to create connection", e);
			return null;
		}
	}

	public static void closeConnections(Connection con,
										PreparedStatement ps,
										ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	private static void printDbConnMetadata() throws SQLException {
		Connection con = getConnection();
		DatabaseMetaData dmd = con.getMetaData();
		logger.debug(dmd.toString());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Singleton class, clone not supported");
	}
}
