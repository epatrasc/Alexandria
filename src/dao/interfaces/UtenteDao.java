package dao.interfaces;

import model.Utente;

public interface UtenteDao extends DaoInterface {
	public Utente getUtenteById(int id);
}
