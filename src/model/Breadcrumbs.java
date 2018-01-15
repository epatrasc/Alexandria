package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Breadcrumbs {
	private List<Map<String,String>> percorso;
	
	public static final String [][] DEFAULT_HOME = {{"Home", "#"}};
	public static final String [][] DEFAULT_PRESTITI = {{"Home", "/home"}, {"Prestiti", "#"}};
	public static final String [][] CATALOGO_VISUALIZZA = {{"Home", "/home"}, {"Catologo", "#"}};
	public static final String [][] CATALOGO_DETTAGLIO = {{"Home", "/home"}, {"Catologo", "/catalogo/visualizza"},{"Dettaglio", "#"}};
	public static final String [][] CATALOGO_MODIFICA = {{"Home", "/home"}, {"Catologo", "/catalogo/visualizza"},{"Modifica", "#"}};
	public static final String [][] CATALOGO_AGGIUNGI = {{"Home", "/home"}, {"Catologo", "/catalogo/visualizza"},{"Aggiungi", "#"}};
	
	public Breadcrumbs(String [][] percorso){
		this.percorso = new ArrayList<>();
		setPercorso(percorso);
	}

	public List<Map<String,String>> getPercorso() {
		return percorso;
	}
	
	private void setPercorso(final String [][] percorso){
		for (int r=0; r < percorso.length; r++) {
			String name = percorso[r][0];
			String url =  percorso[r][1];
			add(name,url);
		}
	}
	
	private void add(final String name, final String url){
		Map<String,String> posizione = new HashMap<String,String>();
		posizione.put("name", name);
		posizione.put("url", url);

		add(posizione);
	}
	
	private void add(final Map<String,String> posizione){
		percorso.add(posizione);
	}
	
	public void clear(){
		this.percorso = new ArrayList<>();
	}
}
