package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.PrestitoDao;
import model.Prestito;

public class PrestitoDaoImpl implements PrestitoDao {
	private static final Logger logger = Logger.getLogger(PrestitoDaoImpl.class.getName());
	private Prestito prestito;

	public PrestitoDaoImpl() {
	}

	public PrestitoDaoImpl(int idUtente, int idLibro) {
		this.prestito = new Prestito(idUtente, idLibro);
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
		String query = "INSERT INTO prestiti (id_utente, id_libro) VALUES (?, ?)";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, prestito.getIdUtente());
			pst.setInt(2, prestito.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating prestito failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	@Override
	public boolean update() {
		Connection connection = null;
		String query = "UPDATE prestiti set data_restituzione = CURRENT_TIMESTAMP, restituito = true WHERE id_utente = ? and id_libro = ?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, prestito.getIdUtente());
			pst.setInt(2, prestito.getIdLibro());

			if (pst.executeUpdate() == 0) {
				logger.info("Update prestito failed, no rows affected.");
				return false;
			}

			// TODO aggiornare gli autori

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
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
	public Prestito getPrestitoById(int idUtente, int idLibro) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id_utente, id_libro, data_prestito, data_restituzione, restituito FROM prestiti WHERE id_utente = ? and id_libro = ?");
			pst.setInt(1, idUtente);
			pst.setInt(2, idLibro);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return fetchResultSet(rs);
			}

			return null;
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
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
				return rs.getInt(1)> 0 ? true : false;
			}

		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
			return false;
		} finally {
			Database.closeConnection(connection);
		}

		return false;
	}
	
	private Prestito fetchResultSet(ResultSet rs) throws SQLException {
		rs.next();

		int index = 1;

		Prestito prestito = new Prestito();
		prestito.setIdUtente(rs.getInt(index));
		prestito.setIdLibro(rs.getInt(++index));
		prestito.setDataPrestito(rs.getDate(++index));
		prestito.setDataRestituzione(rs.getDate(++index));
		prestito.setRestituito(rs.getBoolean(++index));

		return prestito;
	}

}
