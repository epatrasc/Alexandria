package model;

public class AutoreLibro {
	private int idAutore;
	private int idLibro;

	public AutoreLibro(int idAutore, int idLibro) {
		this.idAutore = idAutore;
		this.idLibro = idLibro;
	}

	public int getIdAutore() {
		return idAutore;
	}

	public void setIdAutore(int idAutore) {
		this.idAutore = idAutore;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
}
