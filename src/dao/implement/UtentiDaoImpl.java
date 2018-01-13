package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;
import dao.interfaces.UtentiDao;
import model.Utente;

public class UtentiDaoImpl implements UtentiDao {
	private static final Logger logger = LogManager.getLogger(new Object() {
	}.getClass().getEnclosingClass());

	@Override
	public List<Utente> getUtenti() {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id, nome, ruolo, attivo FROM utenti WHERE attivo = 1 and ruolo = ?");
			pst.setString(1, Utente.cliente());
			
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

	private List<Utente> fetchResultSet(ResultSet rs) throws SQLException {
		List<Utente> utenti = new ArrayList<>();

		while(rs.next()) {
			Utente utente = null;
			utente = new Utente();
			utente.setId(rs.getInt(1));
			utente.setNome(rs.getString(2));
			utente.setRuolo(rs.getString(3));
			utente.setAttivo(rs.getBoolean(4));
			utenti.add(utente);
		}

		return utenti;
	}
}
