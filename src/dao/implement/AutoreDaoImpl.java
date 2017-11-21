package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.AutoreDao;
import model.Autore;

public class AutoreDaoImpl implements AutoreDao {
	private static final Logger logger = Logger.getLogger(AutoreDaoImpl.class.getName());
	private Autore autore;

	public AutoreDaoImpl(int id) {
		this.autore = getAutoreById(id);

		if (autore == null) {
			throw new IllegalArgumentException(String.format("Autore con id %d inesistente", id));
		}
	}

	public AutoreDaoImpl(Autore autore) {
		this.autore = autore;

		if (autore == null) {
			throw new IllegalArgumentException("Autore non inzializzato, passare un autore valido");
		}
	}
	
	@Override
	public boolean insert() {
		Connection connection = null;
		String query = "INSERT INTO autori (nome, cognome) VALUES (?, ?)";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, autore.getNome());
			pst.setString(2, autore.getCognome());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating autore failed, no rows affected.");
				return false;
			}

			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
				if (!generatedKeys.next()) {
					logger.info("Creating autore failed, no ID obtained.");
					return false;
				}

				autore.setId(generatedKeys.getInt(1));
			}

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			logger.severe("SQLException: " + ex.getMessage());
			logger.severe("SQLState: " + ex.getSQLState());
			logger.severe("VendorError: " + ex.getErrorCode());
			logger.log(Level.SEVERE, ex.getMessage(), ex.getCause());
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean update() {
		Connection connection = null;
		String query = "UPDATE autori set nome = ?, cognome = ?) WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setString(1, autore.getNome());
			pst.setString(2, autore.getCognome());
			pst.setInt(3, autore.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Update autore failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean delete() {
		Connection connection = null;
		String query = "DELETE FROM autori WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, autore.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Delete autore failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public Autore getAutoreById(int id) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection
					.prepareStatement("SELECT id, nome, cognome FROM autori WHERE id=?");
			pst.clearParameters();
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			return fetchResultSet(rs);
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	private Autore fetchResultSet(ResultSet rs) throws SQLException {
		rs.next();

		Autore autore = new Autore();
		autore.setId(rs.getInt(1));
		autore.setNome(rs.getString(2));
		autore.setCognome(rs.getString(3));

		return autore;
	}
}
