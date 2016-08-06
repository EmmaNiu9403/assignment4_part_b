package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.*;

public class FXMLController implements Initializable 
{
	private ArrayList<String> list = new ArrayList<String>();
	private ObservableList<String> makeCritterList;
	private boolean animation = false;
	private boolean animationRunning = false;
	private ArrayList<Critter> shapes = new ArrayList<Critter>();
	private boolean showAllStats = false;
	private int rowCon;
	private int colCon;
	
	@FXML private GridPane critterWorld; 
	@FXML private ChoiceBox<String> makeCritterType;
	@FXML private ChoiceBox<String> statsCritter;
	@FXML private Spinner<Integer> numOfCritters;
	@FXML private Spinner<Integer> addSeedNum;
	@FXML private Spinner<Integer> rowSpinner;
	@FXML private Spinner<Integer> colSpinner;
	@FXML private Slider stepSpeed;
	@FXML private Slider scaleGridSlider;
	@FXML private Text stats;
	@FXML private Text errorMessage;
	@FXML private TextField numOfSteps;
	@FXML private Button runButton;
	@FXML private Button addButton;
	@FXML private Button oneWorldStep;
	@FXML private Button addSeedButton;
	@FXML private Button quitButton;
	@FXML private Button showStatsButton;
	@FXML private Button updateGrid;
	@FXML private ToggleButton animationButton;
	@FXML private ToggleButton showAllStatsButton;
	@FXML private AnchorPane gridPane;
	@FXML private ScrollPane scrollPane;
	
	@FXML protected void handleUpdateGridButtonAction(ActionEvent event){
		Params.world_height =  Integer.parseInt(rowSpinner.getEditor().getText());
		Params.world_width = Integer.parseInt(colSpinner.getEditor().getText());
		updateGridPane();
		initializeScrollAndArchorPane();
		displayWorld();
	}
	
	private void updateGridPane() {
		if(critterWorld.getColumnConstraints().size() < Params.world_width)
		{
			for (int col = critterWorld.getColumnConstraints().size(); col < Params.world_width; col++) {
				ColumnConstraints column = new ColumnConstraints(Params.size_of_grid_blocks+1);
				critterWorld.getColumnConstraints().add(column);
			}
		}
		else if(critterWorld.getColumnConstraints().size() > Params.world_width)
		{
			for(int col = Params.world_width;col < critterWorld.getColumnConstraints().size(); col++ )
			{
				critterWorld.getColumnConstraints().get(col).setMinWidth(0);
				critterWorld.getColumnConstraints().get(col).setPrefWidth(0);
				critterWorld.getColumnConstraints().get(col).setMaxWidth(0);
			}
			
		}
		if(critterWorld.getRowConstraints().size() < Params.world_height)
		{
			for (int rows = critterWorld.getRowConstraints().size(); rows < Params.world_height; rows++) {
				RowConstraints row = new RowConstraints(Params.size_of_grid_blocks+1);
				critterWorld.getRowConstraints().add(row);
			}
		}
		else if(critterWorld.getRowConstraints().size() > Params.world_height)
		{
			for(int col = Params.world_width;col < critterWorld.getRowConstraints().size(); col++ )
			{
				critterWorld.getRowConstraints().get(col).setMinHeight(0);
				critterWorld.getRowConstraints().get(col).setPrefHeight(0);
				critterWorld.getRowConstraints().get(col).setMaxHeight(0);
			}
		}
		
		critterWorld.autosize();
		
	}

	@FXML protected void handleShowStatsButtonAction(ActionEvent event){
		String critter = statsCritter.getValue();
	}
	
	@FXML protected void handleOneWorldStepButtonAction(ActionEvent event){
		try {
			Critter.worldTimeStep();
			displayWorld();
		} catch (Exception e) {
			errorMessage.setText("Something went wrong with the One Step Button");
		} 
		
	}
	
	@FXML protected void handleQuitButtonAction(ActionEvent event){
		System.exit(0);
	}
	
	@FXML protected void handleAddSeedButtonAction(ActionEvent event){
		int seed = Integer.parseInt(addSeedNum.getEditor().getText());
		Critter.setSeed(seed);
	}
	
	@FXML protected void handleRunButtonAction(ActionEvent event){
		try{
			if(animation == false)
			{
				int numSteps = Integer.parseInt(numOfSteps.getText());
				for(int i = 0;i < numSteps;i++){
					Critter.worldTimeStep();
				}
			}
			else{
				
			}
		} catch(Exception e){
			errorMessage.setText("Something went wrong with the Run Button");
		}
		
		displayWorld();
	}
	
	@FXML protected void handleAnimationOnButtonAction(ActionEvent event){
		if(animation == false)
			animation = true;
		else
			animation = false;
	}
	
	@FXML protected void handleShowAllStatsButtonAction(ActionEvent event){
		if(showAllStats == false)
		{
			showAllStats = true;
			showCurrentStats();
		}
		else
			showAllStats = false;
		
	}
	
	private void showCurrentStats() {
		String printOut = new String();
		try{
			for(String name: list)
			{
				Class<?> critterName = Class.forName(name);
				List<Critter> list = Critter.getInstances(name);
				Method m = critterName.getMethod("runStats",List.class);
				printOut += (String) m.invoke(null, list);
			}
		} catch(Exception e){
			errorMessage.setText("Something went wrong with Stats");
		}
		
		stats.setText(printOut);
	}

	@FXML protected void handleAddButtonAction(ActionEvent event){
		if(animationRunning == false)
		{
			String critterType = makeCritterType.getValue();
			int numOfCritter = Integer.parseInt(numOfCritters.getEditor().getText());
			for(int i = 0;i < numOfCritter;i++){
				try {
					Critter.makeCritter(critterType);

				} catch (Exception e) {
					errorMessage.setText("Something went wrong with the Add Button");
				}
			}
			displayWorld();
		}
		
	}
	
	private void displayWorld()
	{
		for(int row = 0;row < critterWorld.getRowConstraints().size(); row++)
		{
			for(int col = 0; col < critterWorld.getColumnConstraints().size(); col++)
			{
				for(Critter c: Critter.getCritterCollection())
				{
					if(c.getX() == row && c.getY() == col)
					{
						Shape shape = getShape(c, col, row);
						critterWorld.add(shape, col, row);
						c.setCol(col); c.setRow(row); c.setShape(shape);
						shapes.add(c);
					}
				}
			}
		}
	}

	private Shape getShape(Critter c,int col, int row) {
		String rc = c.getShape();
		switch(rc)
		{
			case "Square":
				Rectangle r = new Rectangle();
				r.setX(row);
				r.setY(col);
				r.setWidth(Params.size_of_grid_blocks);
				r.setHeight(Params.size_of_grid_blocks);
				r.setFill(c.getColor());
				r.setStroke(Color.BLACK);
				return r;
			case "Circle":
				Circle cr = new Circle();
				cr.setCenterX(row);
				cr.setCenterY(col);
				cr.setRadius(Params.size_of_grid_blocks/2);
				cr.setFill(c.getColor());
				cr.setStroke(Color.BLACK);
				return cr;
			case "Ellipse":
				Ellipse e = new Ellipse();
				e.setCenterX(row);
				e.setCenterY(col);
				e.setRadiusX(Params.size_of_grid_blocks/2);
				e.setRadiusY(Params.size_of_grid_blocks/4);
				e.setFill(c.getColor());
				e.setStroke(Color.BLACK);
				return e;
			case "Rectangle":
				Rectangle rt = new Rectangle();
				rt.setX(row);
				rt.setY(col);
				rt.setWidth(Params.size_of_grid_blocks);
				rt.setHeight(Params.size_of_grid_blocks/2);
				rt.setFill(c.getColor());
				rt.setStroke(Color.BLACK);
				return rt;
			case "Bird":
				Polygon d = new Polygon();
				d.getPoints().addAll(new Double[] { 0.0, 0.0, Params.size_of_grid_blocks, 0.0,
								Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks / 2, 0.0,
								Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks,
								Params.size_of_grid_blocks / 2, Params.size_of_grid_blocks / 2,
								Params.size_of_grid_blocks, Params.size_of_grid_blocks / 3,
								Params.size_of_grid_blocks / 2, 4 * Params.size_of_grid_blocks / 3,
								Params.size_of_grid_blocks / 2, });
				d.setFill(c.getColor());
				d.setStroke(Color.BLACK);
				return d;
			case "Egg":
				Ellipse eg = new Ellipse();
				eg.setCenterX(row);
				eg.setCenterY(col);
				eg.setRadiusX(Params.size_of_grid_blocks/4);
				eg.setRadiusY(Params.size_of_grid_blocks/2);
				eg.setFill(c.getColor());
				eg.setStroke(Color.BLACK);
				return eg;
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		initializeChoiceBox();
		initializeChoiceBoxStats();
		initializeGridPane();
		initializeScrollAndArchorPane();
		initializeSliderListener();
	}

	private void initializeChoiceBoxStats() {
		assert statsCritter  != null : "fx:id=\"statsCritter\" was not injected: check your FXML file 'FXMLgui.fxml'.";		
		statsCritter.setItems(makeCritterList);		
	}

	private void initializeSliderListener() {
		// Listen for Slider value changes
		scaleGridSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
		    Params.size_of_grid_blocks = newValue.intValue();
		    initializeGridPane();
		    displayRescaledShapes();
		});
		
	}

	private void displayRescaledShapes() {
		critterWorld.getChildren().removeAll(critterWorld.getChildren());
		for(Critter c: shapes)
		{
			c.setShape(getShape(c,c.getCol(),c.getRow()));
			critterWorld.add(c.getCurShape(), c.getCol(), c.getRow());
		}
		
	}

	private void initializeScrollAndArchorPane() 
	{
		gridPane.setMinSize(critterWorld.getMinWidth(), critterWorld.getMinHeight());
		gridPane.setMaxSize(critterWorld.getMaxWidth(), critterWorld.getMaxHeight());
		gridPane.setPrefSize(critterWorld.getPrefWidth(), critterWorld.getPrefHeight());
		
		scrollPane.setMinSize(critterWorld.getMinWidth(), critterWorld.getMinHeight());
		scrollPane.setMaxSize(critterWorld.getMaxWidth(), critterWorld.getMaxHeight());
		scrollPane.setPrefSize(critterWorld.getPrefWidth(), critterWorld.getPrefHeight());
	}

	private void initializeGridPane()
	{
		for (int col = critterWorld.getColumnConstraints().size(); col < Params.world_width; col++) {
            ColumnConstraints column = new ColumnConstraints(Params.size_of_grid_blocks+1);
            critterWorld.getColumnConstraints().add(column);
        }
		for(int col = 0; col < critterWorld.getColumnConstraints().size(); col++)
		{
			critterWorld.getColumnConstraints().get(col).setMinWidth(Params.size_of_grid_blocks+1);
			critterWorld.getColumnConstraints().get(col).setPrefWidth(Params.size_of_grid_blocks+1);
			critterWorld.getColumnConstraints().get(col).setMaxWidth(Params.size_of_grid_blocks+1);
		}
		for (int rows = critterWorld.getRowConstraints().size(); rows < Params.world_height; rows++) {
            RowConstraints row = new RowConstraints(Params.size_of_grid_blocks+1);
            critterWorld.getRowConstraints().add(row);
        }
		for(int row = 0; row < critterWorld.getRowConstraints().size(); row++)
		{
			critterWorld.getRowConstraints().get(row).setMinHeight(Params.size_of_grid_blocks+1);
			critterWorld.getRowConstraints().get(row).setPrefHeight(Params.size_of_grid_blocks+1);
			critterWorld.getRowConstraints().get(row).setMaxHeight(Params.size_of_grid_blocks+1);
		}
		critterWorld.autosize();
	}

	private void initializeChoiceBox()
	{
		assert makeCritterType != null : "fx:id=\"makeCritterType\" was not injected: check your FXML file 'FXMLgui.fxml'.";		
		try {
			createListOfCritter();
			makeCritterList = FXCollections.observableList(list);
		} catch (Exception e) {
			errorMessage.setText("Something went wrong with the Critter Type Choice Box");
		}
		makeCritterType.setItems(makeCritterList);
	}
	
	private void createListOfCritter() throws Exception
	{
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "/bin/application");
        File[] files = file.listFiles(new ClassFileFilter());
        for(File f: files)
        {
        	if(f.canRead() && (!(f.isDirectory())))
        	{
        		String[] filenames =f.getName().split(".class");
        		String filename = "application." + filenames[0];
        		Class<?> critterName = Class.forName(filename);
        		Class<?> critter = Class.forName("application.Critter");
        		
				if(!(critterName.isInterface()) && !(Modifier.isAbstract( critterName.getModifiers() )) && critter.isAssignableFrom(critterName))
        		{
        			list.add(filename);
        		}
        	}
        }
	}
	
	private class ClassFileFilter implements FileFilter
	{
	  private final String[] okFileExtensions = 
	    new String[] {"class"};

	  public boolean accept(File file)
	  {
	    for (String extension : okFileExtensions)
	    {
	      if (file.getName().endsWith(extension))
	      {
	        return true;
	      }
	    }
	    return false;
	  }
	}
	
}
