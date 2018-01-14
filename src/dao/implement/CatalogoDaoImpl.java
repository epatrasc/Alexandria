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

public class CatalogoDaoImpl implements CatalogoDao {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());

	public List<Libro> getLibri() {
		Connection connection = null;

		try {
			connection = Database.getConnection();
			
			PreparedStatement pst = connection.prepareStatement("SELECT id, titolo, autori, descrizione, image_url, editore, disponibile FROM libri where cancellato is not true");
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
	
	@Override
	public List<Libro> getLibriDisponibili() {
		Connection connection = null;

		try {
			connection = Database.getConnection();
			
			String query = new StringBuffer()
					.append("SELECT id, titolo, autori, descrizione, image_url, editore, disponibile ")
					.append("FROM libri WHERE cancellato is not true and disponibile is true").toString();
			
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
