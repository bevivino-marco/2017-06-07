package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class Match implements Comparable<Match>{
	
	
	private Team homeTeam ;
	private Team awayTeam ;

	private String ftr ; // full time result (H, A, D)
	// è possibile aggiungere altri campi, se risulteranno necessari
	
	/**
	 * New match
	 * 
	 * @param id
	 * @param season
	 * @param div
	 * @param date
	 * @param homeTeam
	 * @param awayTeam
	 * @param fthg
	 * @param ftag
	 * @param ftr
	 */
	public Match( Team homeTeam, Team awayTeam, 
			String ftr) {
		super();
		
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		
		this.ftr = ftr;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}

	public String getFtr() {
		return ftr;
	}

	public void setFtr(String ftr) {
		this.ftr = ftr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((awayTeam == null) ? 0 : awayTeam.hashCode());
		result = prime * result + ((homeTeam == null) ? 0 : homeTeam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (awayTeam == null) {
			if (other.awayTeam != null)
				return false;
		} else if (!awayTeam.equals(other.awayTeam))
			return false;
		if (homeTeam == null) {
			if (other.homeTeam != null)
				return false;
		} else if (!homeTeam.equals(other.homeTeam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s\n", homeTeam, awayTeam, ftr);
	}

	@Override
	public int compareTo(Match o) {
		// TODO Auto-generated method stub
		return o.getHomeTeam().compareTo(this.homeTeam);
	}

	
	

}
