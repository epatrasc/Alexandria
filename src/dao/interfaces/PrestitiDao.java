package dao.interfaces;

import model.Prestito;

import java.util.List;

public interface PrestitiDao extends DaoInterface {
	public List<Prestito> getPrestitiByUserId(int idUtente);
}
