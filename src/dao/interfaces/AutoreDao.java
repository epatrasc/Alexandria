package dao.interfaces;

import model.Autore;

public interface AutoreDao extends DaoInterface {
	public Autore getAutoreById(int id);
}
