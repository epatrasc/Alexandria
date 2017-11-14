package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.AutoreLibroDao;
import model.AutoreLibro;

public class AutoreLibroDaoImpl implements AutoreLibroDao {
	private static final Logger logger = Logger.getLogger(AutoreLibroDaoImpl.class.getName());
	private AutoreLibro autoreLibro;

	public AutoreLibroDaoImpl(int idAutore, int idLibro) {
		this.autoreLibro = new AutoreLibro(idAutore, idLibro);

		if (autoreLibro == null) {
			throw new IllegalArgumentException(
					String.format("AutoreLibro con idAutore %d | idLibro %d inesistente", idAutore, idLibro));
		}
	}

	public AutoreLibroDaoImpl(AutoreLibro autoreLibro) {
		this.autoreLibro = autoreLibro;

		if (autoreLibro == null) {
			throw new IllegalArgumentException("AutoreLibro non inzializzato, passare un autoreLibro valido");
		}
	}

	@Override
	public boolean insert() {
		Connection connection = null;
		String query = "INSERT INTO autoreLibro (id_autore, id_libro) VALUES (?, ?)";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, autoreLibro.getIdAutore());
			pst.setInt(2, autoreLibro.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating autoreLibro failed, no rows affected.");
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
	public boolean update() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean delete() {
		Connection connection = null;
		String query = "DELETE FROM autoreLibro WHERE id_autore=? and id_libro=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, autoreLibro.getIdAutore());
			pst.setInt(2, autoreLibro.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Delete autoreLibro failed, no rows affected.");
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
	public List<Integer> getLibriByAutore(int idAutore) {
		Connection connection = null;
		List<Integer> libri = null;
		
		String query = "SELECT id_libro FROM autoreLibro WHERE id_autore=?";
		
		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, autoreLibro.getIdAutore());

			ResultSet rs = pst.executeQuery();
			
			libri = new ArrayList<Integer>();
			while(rs.next()){
				libri.add(rs.getInt(1));
			}
			
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return libri;
	}

	@Override
	public List<Integer> getAutoriByLibro(int idAutore) {
		Connection connection = null;
		List<Integer> autori = null;
		
		String query = "SELECT id_autore FROM autoreLibro WHERE id_libro=?";
		
		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, autoreLibro.getIdAutore());

			ResultSet rs = pst.executeQuery();
			
			autori = new ArrayList<Integer>();
			while(rs.next()){
				autori.add(rs.getInt(1));
			}
			
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return autori;
	}

}
