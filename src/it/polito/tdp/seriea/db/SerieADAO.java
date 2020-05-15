package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams(int anno) {
		String sql = "SELECT DISTINCT m.HomeTeam " + 
				"FROM matches m " + 
				"WHERE m.Season=? " + 
				"ORDER BY m.HomeTeam ASC  " ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("m.HomeTeam"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public double getRisultato(int anno, Team t1, Team t2) {
		String sql = "SELECT m.FTR AS ftr " + 
				"FROM matches m " + 
				"WHERE m.Season=? AND  m.HomeTeam=? AND   m.AwayTeam=? " ;
		
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setString(2, t1.getTeam());
			st.setString(3, t2.getTeam());
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				if (res.getString("ftr").equals("H")) {
					return 1;
				}else if (res.getString("ftr").equals("A")) {
					return -1;
				}
				else return 0;
			}
			
			conn.close();
			return 0; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0 ;
		}
	}

	public List <Match> getMatches(int anno, Map <String, Team> mappaT) {
		String sql = "SELECT  m.HomeTeam, m.AwayTeam, m.FTR " + 
				"FROM matches m " + 
				"WHERE m.Season=?" ;
		
		List <Match> l = new LinkedList <>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
		   
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Team ht = mappaT.get(res.getString("m.HomeTeam"));
				Team at = mappaT.get(res.getString("m.AwayTeam"));
				String ftr = res.getString("m.FTR");
				
				l.add(new Match (ht, at,ftr));
			}
			
			conn.close();
			return l; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}
	}



}
