package it.polito.tdp.seriea.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		m.creaGrafo(2017);
		System.out.println(m.getClassifica().toString());
		System.out.println(m.trovaSequenza(m.getAllTeam().get(0)).toString());
	}

}
