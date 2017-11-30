package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.Database;
import dao.interfaces.PrestitiDao;
import model.Prestito;
import model.SqlError;

public class PrestitiDaoImpl implements PrestitiDao {
    private static final Logger logger = Logger.getLogger(PrestitiDaoImpl.class.getName());

    @Override
    public List<Prestito> getPrestitiByUserId(int idUtente) {
        Connection connection = null;

        try {
            connection = Database.getConnection();

            PreparedStatement pst = connection
                    .prepareStatement("SELECT id_utente, id_libro, data_prestito, data_restituzione, restituito FROM prestiti WHERE id_utente = ?");
            pst.setInt(1, idUtente);
            ResultSet rs = pst.executeQuery();

            List<Prestito> prestiti = new ArrayList<>();
            while (rs.next()) {
            	prestiti.add(fetchResultSet(rs));
            }

            return prestiti.size() > 0 ?prestiti : null;
        } catch (SQLException ex) {
            logger.severe(ex.getMessage());
            Database.printSQLException(new SqlError(ex));
        } finally {
            Database.closeConnection(connection);
        }

        return null;
    }
    
    private Prestito fetchResultSet(ResultSet rs) throws SQLException {
        int index = 1;

        Prestito prestito = new Prestito();
        prestito.setIdUtente(rs.getInt(index));
        prestito.setIdLibro(rs.getInt(++index));
        prestito.setDataPrestito(rs.getDate(++index));
        prestito.setDataRestituzione(rs.getDate(++index));
        prestito.setRestituito(rs.getBoolean(++index));

        return prestito;
    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

	
}

