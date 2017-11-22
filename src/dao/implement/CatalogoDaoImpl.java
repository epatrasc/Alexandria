package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.CatalogoDao;
import model.Libro;

public class CatalogoDaoImpl implements CatalogoDao {
	private static final Logger logger = Logger.getLogger(CatalogoDaoImpl.class.getName());

	public List<Libro> getLibri() {
		Connection connection = null;

		try {
			connection = Database.getConnection();

			PreparedStatement pst = connection.prepareStatement("SELECT id, titolo, descrizione, image_url, editore FROM libri WHERE cancellato is not true and disponibile is true");
			ResultSet rs = pst.executeQuery();

			List<Libro> libri = new ArrayList<>();
			while (rs.next()) {
				libri.add(fetchResultSet(rs));
			}
			
			return libri;
		} catch (SQLException ex) {
			logger.severe(ex.getMessage());
			Database.printSQLException(ex);
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
		libro.setDescrizione(rs.getString(++index));
		libro.setImageUrl(rs.getString(++index));
		libro.setEditore(rs.getString(++index));

		return libro;
	}
}
