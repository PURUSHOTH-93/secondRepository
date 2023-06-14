package com.oas.webservice.dbutil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

public class CommonDbUtil {
	private String jdbcDriver = null;
	private String dbUrl = null;
	private String usernameGO = null;
	private String passwordGO = null;
	private String usernameCWA = null;
	private String passwordCWA = null;
	private Properties properties = null;

	public CommonDbUtil() {
		try {
			properties = new Properties();
			InputStream is = Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
							"com/oas/webservice/dbutil/Database.properties");
			properties.load(is);
			jdbcDriver = properties.getProperty("database.driver");
			dbUrl = properties.getProperty("database.driverParameters");
			usernameGO = properties.getProperty("database.user1");
			passwordGO = properties.getProperty("database.password1");
			usernameCWA = properties.getProperty("database.user2");
			passwordCWA = properties.getProperty("database.password2");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public String getDatabaseUrl() {
		return dbUrl;
	}

	public String getUsernameGO() {
		return usernameGO;
	}

	public void setUsernameGO(String usernameGO) {
		this.usernameGO = usernameGO;
	}

	public String getPasswordGO() {
		return passwordGO;
	}

	public void setPasswordGO(String passwordGO) {
		this.passwordGO = passwordGO;
	}

	public String getUsernameCWA() {
		return usernameCWA;
	}

	public void setUsernameCWA(String usernameCWA) {
		this.usernameCWA = usernameCWA;
	}

	public String getPasswordCWA() {
		return passwordCWA;
	}

	public void setPasswordCWA(String passwordCWA) {
		this.passwordCWA = passwordCWA;
	}

	public Connection openConnectionGO(Connection connection) {
		return this.connection;
		/*
		 * try { connection = DriverManager.getConnection(dbUrl, usernameGO,
		 * passwordGO); } catch (SQLException e) { e.printStackTrace(); } return
		 * connection;
		 */
	}

	// Check DB connection
	private Connection connection;

	public boolean isDbConnectionCheck() {
		try {
			this.connection = DriverManager.getConnection(dbUrl, usernameGO,
					passwordGO);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Check Network Connections
	public boolean isConnectedToNetwork() {
		Enumeration<NetworkInterface> interfaceEnumeration;
		try {
			interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
			while (interfaceEnumeration.hasMoreElements()) {
				NetworkInterface each = interfaceEnumeration.nextElement();
				if (!each.isLoopback() && each.isUp()) {
					/*
					 * try { if
					 * (InetAddress.getByName("192.168.1.78:80").isReachable
					 * (4000) || InetAddress.getByName("www.google.com")
					 * .isReachable(4000)) return true; } catch
					 * (UnknownHostException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } catch (IOException e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); }
					 */
					return true;
				}
			}
		} catch (SocketException e) {
			// e.printStackTrace();
			return false;
		}
		return false;
	}

	public Connection openConnectionCWA(Connection connection) {
		try {
			connection = DriverManager.getConnection(dbUrl, usernameCWA,
					passwordCWA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeAllConnections(ResultSet resultSet,
			PreparedStatement pstatement, Statement statement,
			Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (pstatement != null) {
				pstatement.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
