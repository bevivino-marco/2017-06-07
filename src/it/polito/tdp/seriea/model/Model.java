package it.polito.tdp.seriea.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	// definizione grafi;
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> grafo;
	// idMap e liste;
	private List <Team> listaT;
	private List <Match> listaM;
	
	private  SerieADAO dao;
	private Map <String,Team> mappaT;
	public Model() {
		dao = new SerieADAO();
	}
	public void creaGrafo(int anno) {
		// creo il grafo;
		grafo = new SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge> (DefaultWeightedEdge.class);
       
		// definisco i veritici;
		listaT= new LinkedList<Team>();
		mappaT = new HashMap<String , Team >();
		listaT.addAll(dao.listTeams(anno));
		for (Team t : listaT) {
			mappaT.put(t.getTeam(),t);
		}
//		grafo.addVertex(i);
		Graphs.addAllVertices(grafo, listaT);
//		System.out.println("N. vertici : "+grafo.vertexSet().size());
		// creo gli archi;
		listaM = new LinkedList <>(dao.getMatches(anno, mappaT));
		List <Match> l1 = new LinkedList<>();
		l1.addAll(listaM);
		Collections.sort(l1);
		for(Match m : listaM) {
			if (m.getFtr().compareTo("H")==0) {
				Graphs.addEdgeWithVertices(grafo , m.getHomeTeam(), m.getAwayTeam(), 1.0);
			}else if  (m.getFtr().compareTo("A")==0) {
				Graphs.addEdgeWithVertices(grafo , m.getHomeTeam(), m.getAwayTeam(), -1.0);
			}else if  (m.getFtr().compareTo("D")==0) {
				Graphs.addEdgeWithVertices(grafo , m.getHomeTeam(), m.getAwayTeam(), 0.0);

			}
		}

		System.out.println("N. archi : "+grafo.edgeSet().size());

	}
	public List <Team> getClassifica(){
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			double peso = grafo.getEdgeWeight(e);
			Team ht = grafo.getEdgeSource(e);
			Team at = grafo.getEdgeTarget(e);
			if (peso == 1.0)
				ht.setPunteggio(3);
			else if (peso == -1.0)
				at.setPunteggio(3);
			else if (peso == 0.0) {
				ht.setPunteggio(1);
				at.setPunteggio(1);
			}
		}
		
		List <Team> l = new LinkedList<Team>(listaT);
		Collections.sort(l);
		return l;
	}
    public List<Season> getAllSeason(){
    	return dao.listSeasons();
    }
	public List<Team> getAllTeam() {
		return listaT;
	}
    public List<DefaultWeightedEdge> trovaSequenza(Team team){
    	List <DefaultWeightedEdge> parziale = new LinkedList<>();
    	List <DefaultWeightedEdge> finale = new LinkedList<>();
    	int livello =0;
//    	parziale.add(grafo.getEdge(team, Graphs.neighborListOf(grafo, team).get(0)));
    	cerca (parziale, finale, team);
    	return finale;
    }
	private void cerca(List<DefaultWeightedEdge> parziale, List<DefaultWeightedEdge> finale, Team team) {
		Set <DefaultWeightedEdge> dwe = new HashSet<>();
	
		dwe.addAll(grafo.edgeSet());
		dwe.removeAll(parziale);
		if (parziale.size()>finale.size()) {
			finale.clear();
			finale.addAll(parziale);
		}
		
		
		
		
		
		for (DefaultWeightedEdge e : dwe) {
			if (parziale.size()==0) {
				if (grafo.containsEdge(team, grafo.getEdgeSource(e)) && grafo.getEdgeWeight(grafo.getEdge(team, grafo.getEdgeSource(e)))==1.0) {
					 parziale.add(e);
		    		  cerca(parziale, finale, team);
		    		  parziale.remove(parziale.size()-1);
				}
			}else {
			DefaultWeightedEdge last = parziale.get(parziale.size()-1);
			
		      if (!parziale.contains(e) && grafo.getEdgeTarget(last).equals(grafo.getEdgeSource(e))) {
		    	  double peso = grafo.getEdgeWeight(e);
		    	  if (peso==1) {
		    		  parziale.add(e);
		    		  System.out.println(parziale.toString());
		    		  cerca(parziale, finale, team);
		    		  parziale.remove(parziale.size()-1);
		    		 
		    	  }
		        }
		      }	
		}
		
		
		
		
		
		
		
		
		
		/*Team ultimo = parziale.get(parziale.size()-1);
		
		
			if (parziale.size()>finale.size()) {
				finale.clear();
				finale.addAll(parziale);
			}
			return;
		}
		
		
	
		
		for (Team t : listaT) {
			if (!ultimo.equals(t)) {
			double peso = grafo.getEdgeWeight(grafo.getEdge(ultimo, t));
				if (peso==1.0) {
				parziale.add(t);
				cerca(parziale, finale);
				parziale.remove(parziale.size()-1);
				}
			}
		}*/
	}
}

