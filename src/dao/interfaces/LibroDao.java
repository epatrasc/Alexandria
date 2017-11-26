package dao.interfaces;

import model.Libro;

public interface LibroDao extends DaoInterface {
	public Libro getLibroById(int id);
	public boolean exists();
}
