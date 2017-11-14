package dao.db.init;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;

import dao.Database;

public class DatabaseInit {
	private static final Logger logger = Logger.getLogger(DatabaseInit.class.getName());
	private static final String FILE_INIT = "db/libreria/init/sql/init_db.sql";

	Connection connection;

	public DatabaseInit() throws SQLException {
		connection = Database.getConnection();
		connection.setAutoCommit(false);
	}

	public boolean create() {
		String query = "";
		PreparedStatement pst;

		try {

			if (isDBConfigured()) {
				logger.info("Database non neccessita inizializzazione.");
				return false;
			}

			logger.info("Avvio creazione oggetti");

			query = readFile(FILE_INIT);

			for (String command : query.split(";")) {
				command = command.trim().replace("\n", "");
				if (!command.isEmpty()) {
					logger.info("command:" + command);
					pst = connection.prepareStatement(command);
					pst.clearParameters();
					pst.execute();
				}
			}

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
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

	private String readFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();

		String filePath = classLoader.getResource(fileName).getFile();
		return readFile(new File(filePath));
	}

	private String readFile(File file) {
		StringBuilder result = new StringBuilder("");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
