package dao.interfaces;

import model.Prestito;

public interface PrestitoDao extends DaoInterface {
	public Prestito getPrestitoByIdLibro(int idLibro);
	public Prestito getPrestitoById(int idUtente, int idLibro);
	public boolean exists();
	public boolean presta();
	public boolean restituisci();
}
