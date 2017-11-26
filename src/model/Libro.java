package model;

import java.util.List;

public class Libro {
	private int id;
	private String titolo;
	private String descrizione;
	private String imageUrl;
	private List<Integer> autori;
	private String editore;

	public Libro() {
	}

	public Libro(int id) {
		this.id = id;
	}

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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
