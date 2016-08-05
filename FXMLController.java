package application;

import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
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

	@FXML
	private static Slider gridControl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		ObservableList<String> critterList = FXCollections.observableArrayList("application.Craig", "application.Algae",
				"application.Emma", "application.Niu", "application.Bird", "application.Bob");
		critters.setItems(critterList);
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
		number.setValueFactory(svf);
		number.setEditable(true);
		// CritterPane.setMaxWidth(new Double(Params.world_width));
		// CritterPane.setMaxHeight(new Double(Params.world_height));
		gridControl = new Slider();
		gridControl.setMin(0);
		gridControl.setMax(100);
	}
	public static double getScale(){
		return gridControl.getValue();
	}

	public void displayWorld() {
		for (int row = 0; row < CritterPane.getRowConstraints().size(); row++) {
			for (int column = 0; column < CritterPane.getColumnConstraints().size(); column++) {
				for (Critter c : Critter.getCollection()) {
					if (c.getX() == row && c.getY() == column) {
						String rc = c.getShape();
						switch (rc) {
						case "Rectangle":
							Rectangle r = new Rectangle();
							r.setX(row / 2);
							r.setY(column);
							r.setWidth(Params.size_of_grid_blocks * 2);
							r.setHeight(Params.size_of_grid_blocks);
							r.setStroke(Color.BLACK);
							CritterPane.add(r, row, column);
							break;
						case "Circle":
							Circle cr = new Circle();
							cr.setCenterX(row);
							cr.setCenterY(column);
							cr.setRadius(Params.size_of_grid_blocks);
							CritterPane.add(cr, row, column);
							cr.setFill(Color.RED);
							cr.setStroke(Color.RED);
							break;
						case "Ellipse":
							Ellipse e = new Ellipse();
							e.setCenterX(row);
							e.setCenterY(column);
							e.setRadiusX(Params.size_of_grid_blocks);
							e.setRadiusY(Params.size_of_grid_blocks / 2);
							e.setFill(Color.GREEN);
							e.setStroke(Color.GREEN);
							CritterPane.add(e, row, column);
							break;
						case "Diamond":
							Rectangle rt = new Rectangle();
							rt.setX(row / 2);
							rt.setY(column);
							rt.setWidth(Params.size_of_grid_blocks * 2);
							rt.setHeight(Params.size_of_grid_blocks);
							rt.setRotate(45);
							rt.setFill(Color.ALICEBLUE);
							rt.setStroke(Color.BLACK);
							CritterPane.add(rt, row, column);
							break;
						case "Bird":
							Polygon d = new Polygon();
							d.getPoints()
									.addAll(new Double[] { 0.0, 0.0, Params.size_of_grid_blocks, 0.0,
											Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks / 2, 0.0,
											Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks,
											Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks / 2,
											Params.size_of_grid_blocks, Params.size_of_grid_blocks / 3,
											Params.size_of_grid_blocks / 2, 4 * Params.size_of_grid_blocks / 3,
											Params.size_of_grid_blocks / 2, });
							d.setFill(Color.BLUE);
							d.setStroke(Color.BLACK);
							CritterPane.add(d, row, column);
							break;
						case "Egg":
							Ellipse eg = new Ellipse();
							eg.setCenterX(row);
							eg.setCenterY(column);
							eg.setRadiusX(Params.size_of_grid_blocks);
							eg.setRadiusY(Params.size_of_grid_blocks / 2);
							eg.setFill(Color.BISQUE);
							eg.setStroke(Color.BISQUE);
							eg.setRotate(90);
							CritterPane.add(eg, row, column);
							break;

						}
					}
				}
			}
		}

	}

}
