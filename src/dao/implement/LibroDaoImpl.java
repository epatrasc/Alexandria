package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;
import dao.interfaces.LibroDao;
import model.Libro;

public class LibroDaoImpl implements LibroDao {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());
	private Libro libro;
	
	public LibroDaoImpl(int id) {
		this.libro = new Libro(id);
	}

	public LibroDaoImpl(Libro libro) {
		this.libro = libro;

		if (libro == null) {
			throw new IllegalArgumentException("Libro non inzializzato, passare un libro valido");
		}
	}

	@Override
	public boolean insert() {
		Connection connection = null;
		String query = "INSERT INTO libri (titolo, autori, descrizione, image_url, editore) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int index = 0;
			pst.setString(++index, libro.getTitolo());
			pst.setString(++index, libro.getAutori());
			pst.setString(++index, libro.getDescrizione());
			pst.setString(++index, libro.getImageUrl());
			pst.setString(++index, libro.getEditore());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating libro failed, no rows affected.");
				return false;
			}

			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
				if (!generatedKeys.next()) {
					logger.info("Creating libro failed, no ID obtained.");
					return false;
				}

				libro.setId(generatedKeys.getInt(1));
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean update() {
		Connection connection = null;
		String query = "UPDATE libri set titolo = ?, autori= ?, descrizione = ?, image_url = ?, editore = ? WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			int index = 0;
			pst.setString(++index, libro.getTitolo());
			pst.setString(++index, libro.getAutori());
			pst.setString(++index, libro.getDescrizione());
			pst.setString(++index, libro.getImageUrl());
			pst.setString(++index, libro.getEditore());
			pst.setInt(++index, libro.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Update libro failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean delete() {
		Connection connection = null;
		String query = "UPDATE libri SET cancellato = 1, disponibile = 0 WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, libro.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Delete libro failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	public boolean exists() {
		return exists(libro.getId());
	}
	
	public boolean existsByTitolo() {
		return exists(libro.getTitolo());
	}
	
	public boolean exists(String titolo) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("select count(1) as cnt_libri from libri where titolo = ?");
			pst.setString(1, titolo);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0 ? true : false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return false;
	}
	
	public boolean exists(int id) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("select count(1) as cnt_libri from libri where id = ? and cancellato is false");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0 ? true : false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
		} finally {
			Database.closeConnection(connection);
		}

		return false;
	}

	@Override
	public Libro getLibro() {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id, titolo, autori, descrizione, image_url, editore, disponibile, cancellato FROM libri WHERE id=?");
			pst.clearParameters();
			pst.setInt(1, libro.getId());
			ResultSet rs = pst.executeQuery();

			return fetchResultSet(rs);
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(),ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	private Libro fetchResultSet(ResultSet rs) throws SQLException {
		rs.next();

		int index = 1;

		Libro libro = new Libro();
		libro.setId(rs.getInt(index));
		libro.setTitolo(rs.getString(++index));
		libro.setAutori(rs.getString(++index));
		libro.setDescrizione(rs.getString(++index));
		libro.setImageUrl(rs.getString(++index));
		libro.setEditore(rs.getString(++index));
		libro.setDisponibile(rs.getBoolean(++index));

		return libro;
	}
}
