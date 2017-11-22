package dao.implement;

import dao.Database;
import dao.interfaces.PrestitiDao;
import model.Prestito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

            List<Prestito> libri = new ArrayList<>();
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

