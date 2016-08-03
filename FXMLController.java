package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {
	@FXML
	private ChoiceBox<String> critters;
	@FXML
	private Spinner<Integer> number;
	@FXML
	private GridPane CritterPane;
	@FXML
	private Text stats;
	@FXML
	private TextField steps;
	@FXML
	private Button start;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		ObservableList<String> critterList = FXCollections.observableArrayList("Craig", "Algae", "Emma", "Niu", "Gary",
				"Bob");
		critters.setItems(critterList);
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
		number.setValueFactory(svf);
		number.setEditable(true);
		CritterPane.setMaxWidth(new Double(Params.world_width));
		CritterPane.setMaxHeight(new Double(Params.world_height));
		stats.setText("stats");	
		
	}

}
