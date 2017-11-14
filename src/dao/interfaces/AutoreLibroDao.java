package dao.interfaces;

import java.util.List;

public interface AutoreLibroDao extends DaoInterface {
	public List<Integer> getLibriByAutore(int idAutore);
	public List<Integer> getAutoriByLibro(int idAutore);
}
