package dao.interfaces;

import model.Utente;

public interface UtenteDao extends DaoInterface {
	public boolean exists(int id);
	public Utente getUtenteById(int id);
}
