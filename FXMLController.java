package application;

import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;

public class FXMLController implements Initializable 
{
	ArrayList<String> list = new ArrayList<String>();
	ObservableList<String> makeCritterList;

	@FXML 
	private ChoiceBox<String> makeCritterType;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		assert makeCritterType != null : "fx:id=\"makeCritterType\" was not injected: check your FXML file 'FXMLgui.fxml'.";		
		
		makeCritterType.setValue("Choose a Critter");
		try {
			createListOfCritter();
			makeCritterList = FXCollections.observableList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        		System.out.println(filename);
        		Class<?> critterName = Class.forName(filename);
        		Class critter = Class.forName("application.Critter");
        		
				if(!(critterName.isInterface()) && !(Modifier.isAbstract( critterName.getModifiers() )) && critter.isAssignableFrom(critterName))
        		{
        			list.add(filename);
        		}
        	}
        }
	}
	
	
//	// fx:id="myButton1"
//	@FXML private Button myButton1; // Value injected by FXMLLoader
//
//	// fx:id="myChoiceBox1"
//	@FXML private ComboBox myChoiceBox1;
//	
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		assert myButton1 != null : "fx:id=\"myButton1\" was not injected: check your FXML file 'FXMLgui.fxml'.";		
//
//		// initialize your logic here: all @FXML variables will have been injected
//		myButton1.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("That was easy, wasn't it?");
//			}
//		});
//		
//		assert myChoiceBox1 != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'FXMLgui.fxml'.";		
//	
//	}
	
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
