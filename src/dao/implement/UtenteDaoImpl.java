package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;
import dao.interfaces.UtenteDao;
import model.Utente;

public class UtenteDaoImpl implements UtenteDao {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());
	private Utente utente;

	public UtenteDaoImpl() {

	}

	public UtenteDaoImpl(int id) {
		this.utente = getUtenteById(id);

		if (utente == null) {
			throw new IllegalArgumentException(String.format("Utente con id %d inesistente", id));
		}
	}

	public UtenteDaoImpl(Utente utente) {
		if (utente == null) {
			throw new IllegalArgumentException("Utente non inzializzato, passare un utente valido");
		}

		this.utente = utente;
	}

	public UtenteDaoImpl(String nome, String password) {
		if (nome == null || password == null) {
			throw new IllegalArgumentException("Utente non inzializzato, passare un nome e una password");
		}

		utente = new Utente(nome, password);
	}

	public boolean insert() {
		Connection connection = null;
		String query = "INSERT INTO utenti (nome, password, ruolo, attivo) VALUES (?, MD5(?), ?, ?)";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, utente.getNome());
			pst.setString(2, utente.getPassword());
			pst.setString(3, utente.getRuolo());
			pst.setBoolean(4, utente.isAttivo());

			if (pst.executeUpdate() == 0) {
				logger.info("Creating utente failed, no rows affected.");
				return false;
			}

			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
				if (!generatedKeys.next()) {
					logger.info("Creating utente failed, no ID obtained.");
					return false;
				}

				utente.setId(generatedKeys.getInt(1));
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	public boolean update() {
		Connection connection = null;
		String query = "UPDATE utenti set nome = ?, password = MD5(?), ruolo = ?, attivo = ?) WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setString(1, utente.getNome());
			pst.setString(2, utente.getPassword());
			pst.setString(3, utente.getRuolo());
			pst.setBoolean(4, utente.isAttivo());
			pst.setInt(5, utente.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Update utente failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}

	public boolean delete() {
		Connection connection = null;
		String query = "DELETE FROM utenti WHERE id=?";

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement(query);

			pst.setInt(1, utente.getId());

			if (pst.executeUpdate() == 0) {
				logger.info("Delete utente failed, no rows affected.");
				return false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return true;
	}
	
	public Utente getUtenteById(int id) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id, nome, ruolo, attivo FROM utenti WHERE id=?");
			pst.clearParameters();
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			return fetchResultSet(rs);
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}
	
	public boolean exists(int id) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("select count(1) as cnt from utenti where id = ? ");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0 ? true : false;
			}

		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return false;
	}

	public Utente getUtente() {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id, nome, ruolo, attivo FROM utenti WHERE nome = ? and password = MD5(?)");
			pst.clearParameters();
			pst.setString(1, utente.getNome());
			pst.setString(2, utente.getPassword());
			ResultSet rs = pst.executeQuery();

			return fetchResultSet(rs);
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	private Utente fetchResultSet(ResultSet rs) throws SQLException {
		Utente utente = null;
		if (rs.next()) {
			utente = new Utente();
			utente.setId(rs.getInt(1));
			utente.setNome(rs.getString(2));
			utente.setRuolo(rs.getString(3));
			utente.setAttivo(rs.getBoolean(4));
		}
		
		return utente;
	}
}
