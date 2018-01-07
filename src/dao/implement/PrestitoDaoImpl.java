package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;
import dao.interfaces.PrestitoDao;
import model.Prestito;

public class PrestitoDaoImpl implements PrestitoDao {
	private static final Logger logger = LogManager.getLogger(new Object() {
	}.getClass().getEnclosingClass());
	private Prestito prestito;

	public PrestitoDaoImpl() {
	}

	public PrestitoDaoImpl(int idUtente, int idLibro) {
		this.prestito = new Prestito(idUtente, idLibro);
	}

	public PrestitoDaoImpl(int idLibro) {
		this.prestito = new Prestito(idLibro);
	}

	public PrestitoDaoImpl(Prestito prestito) {
		if (prestito == null) {
			throw new IllegalArgumentException("Prestito non inzializzato, passare un prestito valido");
		}

		this.prestito = prestito;
	}

	@Override
	public boolean presta() {
		return insert();
	}

	@Override
	public boolean restituisci() {
		return update();
	}

	@Override
	public boolean insert() {
		Connection connection = null;
		String queryAddPestito = "INSERT INTO prestiti (id_utente, id_libro) VALUES (?, ?)";
		String queryUpdateLibro = "UPDATE libri set disponibile = false where id = ?";

		try {
			connection = Database.getConnection();
			connection.setAutoCommit(false);

			PreparedStatement pst = connection.prepareStatement(queryAddPestito, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, prestito.getIdUtente());
			pst.setInt(2, prestito.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating prestito failed, no rows affected.");
				connection.rollback();
				return false;
			}
			pst.clearParameters();
			pst = connection.prepareStatement(queryUpdateLibro);
			pst.setInt(1, prestito.getIdLibro());

			if (pst.executeUpdate() != 1) {
				logger.info("Updating libro failed, no rows affected.");
				connection.rollback();
				return false;
			}

			logger.info(String.format("Inserito un nuovo prestito idUtente: %d, idLibro: %d", prestito.getIdUtente(), prestito.getIdLibro()));
			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);

			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean update() {
		Connection connection = null;
		String queryUpdatePrestito = "UPDATE prestiti set data_restituzione = CURRENT_TIMESTAMP, restituito = true WHERE id_libro = ? and (restituito= 0 or data_restituzione is null)";
		String queryUpdateLibro = "UPDATE libri set disponibile = true WHERE id = ?";

		try {
			connection = Database.getConnection();
			connection.setAutoCommit(false);

			PreparedStatement pst = connection.prepareStatement(queryUpdatePrestito);
			pst.setInt(1, prestito.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Update prestito failed, no rows affected.");
				connection.rollback();
				return false;
			}

			pst.clearParameters();
			pst = connection.prepareStatement(queryUpdateLibro);
			pst.setInt(1, prestito.getIdLibro());

			if (pst.executeUpdate() != 1) {
				logger.info("Updating libro failed, no rows affected.");
				connection.rollback();
				return false;
			}
			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean delete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Prestito getPrestitoByIdLibro(int idLibro) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id_utente, id_libro, data_prestito, data_restituzione, restituito FROM prestiti WHERE id_libro = ? and restituito = 0");
			pst.setInt(1, idLibro);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return fetchResultSet(rs);
			}

			return null;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	@Override
	public Prestito getPrestitoById(int idUtente, int idLibro) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id_utente, id_libro, data_prestito, data_restituzione, restituito FROM prestiti WHERE id_utente = ? and id_libro = ? and restituito = 0");
			pst.setInt(1, idUtente);
			pst.setInt(2, idLibro);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return fetchResultSet(rs);
			}

			return null;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	public boolean exists() {
		return exists(prestito.getIdLibro());
	}

	public boolean exists(int idLibro) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("select count(1) as cnt_prestiti from prestiti where id_libro = ? and restituito = 0");
			pst.setInt(1, idLibro);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0 ? true : false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return false;
	}

	private Prestito fetchResultSet(ResultSet rs) throws SQLException {
		int index = 0;

		Prestito prestito = new Prestito();
		prestito.setIdUtente(rs.getInt(++index));
		prestito.setIdLibro(rs.getInt(++index));
		prestito.setDataPrestito(rs.getDate(++index));
		prestito.setDataRestituzione(rs.getDate(++index));
		prestito.setRestituito(rs.getBoolean(++index));

		return prestito;
	}

}
