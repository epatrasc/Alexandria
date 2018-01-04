package dao.interfaces;

import model.Libro;

public interface LibroDao extends DaoInterface {
	public Libro getLibro();
	public boolean exists();
}
