package dao.interfaces;

import java.util.List;

import model.Libro;

public interface CatalogoDao {
	public List<Libro> getLibri();
	public List<Libro> getLibriDisponibili();
}
