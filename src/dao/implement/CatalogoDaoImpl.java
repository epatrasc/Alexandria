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
import dao.interfaces.CatalogoDao;
import model.Libro;
import model.LibroUtente;

public class CatalogoDaoImpl implements CatalogoDao {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());

	public List<Libro> getLibri() {
		Connection connection = null;

		try {
			connection = Database.getConnection();
			
			PreparedStatement pst = connection.prepareStatement("SELECT id, titolo, autori, descrizione, image_url, editore, disponibile FROM libri where cancellato is not true order by titolo");
			ResultSet rs = pst.executeQuery();

			List<Libro> libri = new ArrayList<>();
			while (rs.next()) {
				libri.add(fetchResultSet(rs));
			}
			
			return libri;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getStackTrace());
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}
	
	public List<LibroUtente> getLibriUtente() {
		Connection connection = null;

		try {
			connection = Database.getConnection();
			
			String query = new StringBuffer()
					.append("SELECT l.id,l.titolo,l.autori,l.descrizione,l.image_url,l.editore,l.disponibile, COALESCE(p.id_utente,0) AS id_utente ")
					.append("FROM libri l LEFT JOIN prestiti p ON l.id = p.id_libro AND p.restituito = 0 ")
					.append("WHERE l.cancellato is NOT true ")
					.append("ORDER BY  titolo;").toString();
			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			List<LibroUtente> libriUtente = new ArrayList<>();
			while (rs.next()) {
				libriUtente.add(fetchResultSetWithUtente(rs));
			}
			
			return libriUtente;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getStackTrace());
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}
		
	@Override
	public List<Libro> getLibriDisponibili() {
		Connection connection = null;

		try {
			connection = Database.getConnection();
			
			String query = new StringBuffer()
					.append("SELECT id, titolo, autori, descrizione, image_url, editore, disponibile ")
					.append("FROM libri WHERE cancellato is not true and disponibile is true order by titolo").toString();
			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			List<Libro> libri = new ArrayList<>();
			while (rs.next()) {
				libri.add(fetchResultSet(rs));
			}
			
			return libri;
		} catch (SQLException ex) {
			Database.printSQLException(ex);
			logger.error(ex.getStackTrace());
		} finally {
			Database.closeConnection(connection);
		}

		return null;
	}
	
	private LibroUtente fetchResultSetWithUtente(ResultSet rs) throws SQLException {
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
	
	private Libro fetchResultSet(ResultSet rs) throws SQLException {
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
