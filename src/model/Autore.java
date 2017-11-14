package model;

import java.util.List;

public class Autore {
	private int id;
	private String nome;
	private String cognome;
	private List<Integer> libri;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Integer> getLibri() {
		return libri;
	}

	public void setLibri(List<Integer> libri) {
		this.libri = libri;
	}

}
