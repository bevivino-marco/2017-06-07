/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSeason"
    private ChoiceBox<Season> boxSeason; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<Team> boxTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void handleCarica(ActionEvent event) {
          txtResult.clear();
          try {
        	  int anno = boxSeason.getValue().getSeason();
        	  model.creaGrafo(anno);
        	  txtResult.appendText(model.getClassifica().toString());
        	  boxTeam.getItems().addAll(model.getAllTeam());
        	  
          }catch (Exception e) {
        	  txtResult.appendText("inserire un annata");
          }
    }

    @FXML
    void handleDomino(ActionEvent event) {
    	 txtResult.appendText("\n\n");
         try {
       	  txtResult.appendText(model.trovaSequenza(boxTeam.getValue()).toString());
       	  
         }catch (Exception e) {
       	  txtResult.appendText("cercare delle partite e selezionare un team");
         }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
    }

	public void setModel(Model model) {
		this.model=model;
		boxSeason.getItems().addAll(model.getAllSeason());
		//boxTeam.getItems().addAll(model.getAllTeam());
	}
}
