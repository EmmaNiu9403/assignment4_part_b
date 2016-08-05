package application;

import application.Critter.TestCritter;

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
	@Override
	public String getShape(){
		return "Circle";
	}
	@Override
	public String getColor(){
		return "RED";
	}
 
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.CIRCLE;
	}
}