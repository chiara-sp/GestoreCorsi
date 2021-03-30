package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	
	public Model() {
		corsoDAO= new CorsoDAO();
	}
	
	public List<Corso> getCorsoByPeriodo(int pd){
		return corsoDAO.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(int pd){
		return corsoDAO.getIscrittiByPeriodo(pd);
	}
}
