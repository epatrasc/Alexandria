package model;

import java.util.List;

public class Libro {
	private int id;
	private String titolo;
	private List<Integer> autori;
	private String editore;
	private boolean cancellato;
	private boolean disponibile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<Integer> getAutori() {
		return autori;
	}

	public void setAutori(List<Integer> autori) {
		this.autori = autori;
	}

	public String getEditore() {
		return editore;
	}

	public void setEditore(String editore) {
		this.editore = editore;
	}

	public boolean isCancellato() {
		return cancellato;
	}

	public void setCancellato(boolean cancellato) {
		this.cancellato = cancellato;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
}
