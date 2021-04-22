package it.polito.tdp.corsi.model;

import java.util.HashMap;
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
	
	public List<Studente> getStudenteByCorso(String codice){
		return corsoDAO.getStudentiByCorso(new Corso(codice,0,null,0));
	}

	public boolean esisteCorso(String codice) {
		return corsoDAO.esisteCorso(new Corso(codice,0,null,0));
	}
	public  Map<String,Integer> getDivisioneCDS(String codice) {
		// cosa ci aspettiamo
		//dato il corso con il nome abc 
		//gest -> 50
		//inf -> 40
		
		//soluzione 1
		/*
		Map<String,Integer> divisione= new HashMap<String,Integer>();
		List<Studente> studenti = this.getStudenteByCorso(codice);
		for (Studente s: studenti) {
			if(s.getCDS()!=null && !s.getCDS().equals("")) {
				
			
			if(divisione.get(s.getCDS())==null) {
				divisione.put(s.getCDS(),1);
			}else {
				divisione.put(s.getCDS(), divisione.get(s.getCDS())+1);
			}
			}
		}*/
		
		return corsoDAO.getDivisioneStudenti(new Corso(codice,0,null,0));
 	}
}

