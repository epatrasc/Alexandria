package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Driver;

public class Database {
	private static final Logger logger = Logger.getLogger(Database.class.getName());
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost/alexandria";
	private static final String CONNECTION_USER = "root";
	private static final String CONNECTION_PSW = "root";

	private static Database instance;
	private static int totalConnections;

	public Database() throws SQLException {
		totalConnections = 0;
	}

	private static Database getInstance() throws SQLException {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null)
			Database.getInstance();

		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PSW);

			if (connection.isClosed()) {
				throw new SQLException("No Connection");
			}

			totalConnections++;

			logger.info(String.format("Connection number %d estabilisced", totalConnections));
		} catch (SQLException ex) {
			printSQLException(ex);

			throw ex;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				totalConnections--;
				logger.info("Connessione chiusa");
			} catch (SQLException ex) {
				printSQLException(ex);
			}
		}
	}

	public static void printSQLException(SQLException ex) {
		logger.severe("SQLException: " + ex.getMessage());
		logger.severe("SQLState: " + ex.getSQLState());
		logger.severe("VendorError: " + ex.getErrorCode());
		logger.log(Level.SEVERE, ex.getMessage(), ex.getCause());
	}
}
