
package com.hotelmanagement.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;


public class DBContext {
    protected Connection connection;
    public DBContext()
    {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/usermange?useSSL=false";
            String username = "root";
            String password = "Goldenhand@008";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public static Date getSQLDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

	public static LocalDate getUtilDate(Date sqlDate) {
		return sqlDate.toLocalDate();
	}
}
