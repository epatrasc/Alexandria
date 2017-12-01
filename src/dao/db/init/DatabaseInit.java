package dao.db.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;

public class DatabaseInit {
	private static final Logger logger = LogManager.getLogger(new Object() {
	}.getClass().getEnclosingClass());
	private static final String DB_FILE_INIT = "/init/environment/init_db.sql";
	private static final String DATA_FILE_INIT = "/init/environment/init_data.sql";
	private Connection connection;
	private boolean connected;
	private ServletContext servletContext;

	public DatabaseInit(ServletContext servletContext) {
		this.servletContext = servletContext;
		connected = false;
		try {
			connection = Database.getConnection();
			connection.setAutoCommit(false);

			connected = connection != null && !connection.isClosed() ? true : false;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
		}
	}

	public boolean create() {
		return create(false);
	}

	public boolean create(boolean forceInit) {
		try {

			if (!forceInit && isDBConfigured()) {
				logger.info("Database non neccessita inizializzazione.");
				return false;
			}

			logger.info("Avvio creazione oggetti");
			execScript(connection, readFile(DB_FILE_INIT));

			logger.info("Avvio creazione dati");
			execScript(connection, readFile(DATA_FILE_INIT));

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Database.printSQLException(ex);
			return false;

		} catch (FileNotFoundException ex) {
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	private boolean execScript(Connection connection, String scriptSQL) throws SQLException {
		PreparedStatement pst;
		logger.debug("Esecuzione commandi:");
		for (String command : scriptSQL.split(";")) {
			command = command.trim().replace("\n", "");
			logger.debug(command);
			if (!command.isEmpty()) {
				logger.info("command:" + command);
				pst = connection.prepareStatement(command);
				pst.clearParameters();
				pst.execute();
			}
		}

		return true;
	}

	private boolean isDBConfigured() throws SQLException {
		Statement statement = connection.createStatement();
		try {
			ResultSet rs = statement.executeQuery("select flag from db_exist");

			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (SQLException ex) {
			if (ex.getSQLState() != "42S02") {
				Database.printSQLException(ex);
			}
		}
		logger.info("Database non configurato");

		return false;
	}

	private String readFile(String fileName) throws FileNotFoundException {
		String filePath = servletContext.getRealPath(fileName);
		return readFile(new File(filePath));
	}

	private String readFile(File file) throws FileNotFoundException {
		StringBuilder result = new StringBuilder("");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		}
		return result.toString();
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
