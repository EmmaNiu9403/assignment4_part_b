package application;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.Critter.CritterShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		stats.setText("The number of Critters: " + number.getEditor().getText() + "\n" + "The total steps:"
				+ steps.getText());

	}

	@FXML
	private Button add;

	@FXML
	protected void handleAddButtonAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String cri = critters.getValue();
		String num = number.getEditor().getText();
		Integer n = Integer.parseInt(num);
		for (int i = 0; i < n; i++)
			Critter.makeCritter(cri);
		displayWorld();
	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		ObservableList<String> critterList = FXCollections.observableArrayList("application.Craig", "application.Algae",
				"application.Emma", "application.Niu", "application.Gary", "application.Bob");
		critters.setItems(critterList);
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
		number.setValueFactory(svf);
		number.setEditable(true);
		// CritterPane.setMaxWidth(new Double(Params.world_width));
		// CritterPane.setMaxHeight(new Double(Params.world_height));
    
	}

	public void displayWorld() {
		for (int row = 0; row < CritterPane.getRowConstraints().size(); row++) {
			for (int column = 0; column < CritterPane.getColumnConstraints().size(); column++) {
				for (Critter c : Critter.getCollection()) {
					if (c.getX() == row && c.getY() == column) {
						String rc = c.getShape();
						switch(rc){
						case "Rectangle":Rectangle r = new Rectangle();
									  	r.setX(row);
									  	r.setY(column);
									  	r.setWidth(10);
									  	r.setHeight(10);
									  	CritterPane.add(r,row,column);
									  	break;
						case "Circle":Circle cr = new Circle();
									  cr.setCenterX(row);
									  cr.setCenterY(column);
									  cr.setRadius(10);
									  CritterPane.add(cr,row,column);
									  break;
						}
					}
				}
			}
		}

	}

}
