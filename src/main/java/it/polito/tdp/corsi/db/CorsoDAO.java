package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(int periodo){
		String sql ="select * from corso where pd = ? ";
		
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				Corso c= new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				result.add(c);
			}
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(int periodo){
String sql ="select c.codins, c.nome, c.crediti, c.pd, count(*) as dot "
		+ "from corso c, iscrizione i "
		+ "where c.codins = i.codins and c.pd= ? "
		+ "group by c.codins, c.nome, c.crediti, c.pd";
		
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				Corso c= new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				int tot = rs.getInt("dot");
				result.put(c, tot);
			}
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	
	}
	
	public List<Studente> getStudentiByCorso (Corso corso){
		
		String sql="SELECT s.`matricola`, s.cognome, s.nome, s.cds "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola "
				+ "AND i.codins= ?";
		List<Studente> result = new LinkedList<Studente>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs= st.executeQuery();
			while (rs.next()) {
				Studente s= new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),rs.getString("CDS"));
				result.add(s);
			}
			rs.close();
			st.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return result;
		
	}

	public Map<String,Integer> getDivisioneStudenti(Corso corso){
		String sql="SELECT s.CDS, COUNT(*) as tot "
				+ "FROM studente s, iscrizione i "
				+ "where s.matricola=i.matricola and i.codins = ? and s.cds<>''"
				+ "group by s.cds";
		Map<String,Integer> result= new HashMap<String, Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs= st.executeQuery();
			while (rs.next()) {
				result.put(rs.getString("CDS"),  rs.getInt("tot"));
			}
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	public boolean esisteCorso(Corso corso) {
		String sql="select * from corso where codins= ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs= st.executeQuery();
			if(rs.next()) {
				st.close();
				rs.close();
				conn.close();
				return true;
			}else {
				st.close();
				rs.close();
				conn.close();
				return false;
			}
				
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
