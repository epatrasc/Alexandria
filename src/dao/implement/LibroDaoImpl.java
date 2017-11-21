package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.LibroDao;
import model.Libro;

public class LibroDaoImpl implements LibroDao {
	private static final Logger logger = Logger.getLogger(LibroDaoImpl.class.getName());
	private Libro libro;

	public LibroDaoImpl(int id) {
		this.libro = getLibroById(id);

		if (libro == null) {
			throw new IllegalArgumentException(String.format("Libro con id %d inesistente", id));
		}
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
		String query = "INSERT INTO libri (titolo, descrizione, image_url, editore, cancellato, disponibile) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			connection = Database.getConnection();
			
			
			
			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			int index = 1;
			pst.setString(index, libro.getTitolo());
			pst.setString(index, libro.getDescrizione());
			pst.setString(index, libro.getImageUrl());
			pst.setString(++index, libro.getEditore());
			pst.setBoolean(++index, libro.isCancellato());
			pst.setBoolean(++index, libro.isDisponibile());

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
			
			// TODO inserire gli autori

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean update() {
		Connection connection = null;
		String query = "UPDATE libri set titolo = ?, descrizione = ?, image_url = ?, editore = ?, cancellato = ?, disponibile = ?) WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			int index = 1;
			pst.setString(index, libro.getTitolo());
			pst.setString(index, libro.getDescrizione());
			pst.setString(index, libro.getImageUrl());
			pst.setString(++index, libro.getEditore());
			pst.setBoolean(++index, libro.isCancellato());
			pst.setBoolean(++index, libro.isDisponibile());
			pst.setInt(++index, libro.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Update libro failed, no rows affected.");
				return false;
			}
			
			// TODO aggiornare gli autori
			
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
		String query = "UPDATE libri set cancellato = true WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, libro.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Delete libro failed, no rows affected.");
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
	public Libro getLibroById(int id) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection
					.prepareStatement("SELECT id, titolo, descrizione, image_url, editore, cancellato, disponibile FROM libri WHERE id=?");
			pst.clearParameters();
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			// TODO retrieve lista autori
			
			return fetchResultSet(rs);
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
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
		libro.setDescrizione(rs.getString(++index));
		libro.setImageUrl(rs.getString(++index));
		libro.setEditore(rs.getString(++index));
		libro.setCancellato(rs.getBoolean(++index));
		libro.setDisponibile(rs.getBoolean(++index));

		return libro;
	}

}
