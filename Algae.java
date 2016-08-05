package application;

import application.Critter.TestCritter;
import javafx.scene.paint.Color;

public class Algae extends TestCritter 
{	
	@Override
	public boolean fight(String not_used) { return false; }
	
	@Override
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	@Override
	public String toString() { return "@"; }
	
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.GREEN; }
	
	@Override
	public String getShape(){
		return "Circle";
	}

	@Override
	public Color getColor() {
		return javafx.scene.paint.Color.GREEN;
	}
	
}
