package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.Database;
import model.Libro;
import model.LibroUtente;

public class LibroUtenteDaoImpl {
	private static final Logger logger = LogManager.getLogger(new Object() {
	}.getClass().getEnclosingClass());

	public LibroUtente getLibroUtente(int idLibro) {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			String query = new StringBuffer()
					.append("SELECT l.id,l.titolo,l.autori,l.descrizione,l.image_url,l.editore,l.disponibile, COALESCE(p.id_utente,0) AS id_utente ")
					.append("FROM libri l LEFT JOIN prestiti p ON l.id = p.id_libro AND p.restituito = 0 ")
					.append("WHERE l.cancellato is NOT true and l.id = ? ").append("ORDER BY  titolo;").toString();

			PreparedStatement pst = connection.prepareStatement(query);
			pst.clearParameters();
			pst.setInt(1, idLibro);
			ResultSet rs = pst.executeQuery();

			return fetchResultSetWithUtente(rs);
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getMessage(), ex);
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}

	private LibroUtente fetchResultSetWithUtente(ResultSet rs) throws SQLException {
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

		LibroUtente libroUtente = new LibroUtente();
		libroUtente.setIdUtente(rs.getInt(++index));
		libroUtente.setLibro(libro);

		return libroUtente;
	}
}
