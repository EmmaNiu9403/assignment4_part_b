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
}
