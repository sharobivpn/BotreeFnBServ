package com.botree.restaurantapp.commonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {

	public ImageUploader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This is used to get the Connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://192.168.1.100:3306/restaurant", "root", "root");
		} catch (Exception e) {
			System.out.println("Error Occured While Getting the Connection: - " + e);
		}
		return connection;
	}

	/**
	 * Insert Image
	 */
	public void insertImage() {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;

		try {
			System.out.println("In insertImage ");
			File image = new File("D:/images/Desert.jpg");

			inputStream = new FileInputStream(image);

			connection = getConnection();

			statement = connection.prepareStatement("insert into fm_m_image(image) " + "values(?)");

			//statement.setString(1, "1");

			statement.setBinaryStream(1, (InputStream) inputStream, (int) (image.length()));

			statement.executeUpdate();
			System.out.println("hello77 ");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}
		}

	}

	/***
	 * Execute Program
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ImageUploader imageTest = new ImageUploader();
		imageTest.insertImage();
	}

}
