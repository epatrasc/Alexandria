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
import dao.interfaces.PrestitiDao;
import model.Prestito;

public class PrestitiDaoImpl implements PrestitiDao {
	private static final Logger logger = LogManager.getLogger(new Object() { }.getClass().getEnclosingClass());

    @Override
    public List<Prestito> getPrestitiByUserId(int idUtente) {
        Connection connection = null;

        try {
            connection = Database.getConnection();
            String query = new StringBuffer()
            		.append("SELECT id_utente, u.nome, id_libro, l.titolo, l.image_url,data_prestito, data_restituzione, restituito ") 
            		.append("FROM prestiti p inner join utenti u on p.id_utente=u.id and u.attivo=1 ")
            		.append("inner join libri l on p.id_libro = l.id and l.cancellato = 0 ")
            		.append("WHERE id_utente = ? ")
            		.append("ORDER BY data_restituzione,data_prestito desc, id_utente, id_libro ").toString();
            
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, idUtente);
            ResultSet rs = pst.executeQuery();

            List<Prestito> prestiti = new ArrayList<>();
            while (rs.next()) {
            	prestiti.add(fetchResultSet(rs));
            }

            return prestiti.size() > 0 ?prestiti : null;
        } catch (SQLException ex) {
        	Database.printSQLException(ex);
			logger.error(ex.getStackTrace());
        } finally {
            Database.closeConnection(connection);
        }

        return null;
    }
    
    @Override
    public List<Prestito> getPrestiti() {
        Connection connection = null;

        try {
            connection = Database.getConnection();
            String query = new StringBuffer()
            		.append("SELECT id_utente, u.nome, id_libro, l.titolo, l.image_url,data_prestito, data_restituzione, restituito ") 
            		.append("FROM prestiti p inner join utenti u on p.id_utente=u.id and u.attivo=1 ")
            		.append("inner join libri l on p.id_libro = l.id and l.cancellato = 0 ")
            		.append("ORDER BY data_restituzione,data_prestito desc, id_utente, id_libro ").toString();
            		
            PreparedStatement pst = connection
                    .prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            List<Prestito> prestiti = new ArrayList<>();
            while (rs.next()) {
            	prestiti.add(fetchResultSet(rs));
            }

            return prestiti.size() > 0 ?prestiti : null;
        } catch (SQLException ex) {
        	Database.printSQLException(ex);
			logger.error(ex.getStackTrace());
        } finally {
            Database.closeConnection(connection);
        }

        return null;
    }
    
    private Prestito fetchResultSet(ResultSet rs) throws SQLException {
        int index = 0;

        Prestito prestito = new Prestito();
        prestito.setIdUtente(rs.getInt(++index));
        prestito.setNomeUtente(rs.getString(++index));
        prestito.setIdLibro(rs.getInt(++index));
        prestito.setTitoloLibro(rs.getString(++index));
        prestito.setUrlImageLibro(rs.getString(++index));
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

