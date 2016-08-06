package application;

import javafx.scene.paint.Color;

public class Emma extends Critter {
	
	@Override
	public String toString() { return "E"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Emma() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = 4;
	}
	
	@Override
	public boolean fight(String not_used) { 
		return true; 
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		if (getEnergy() > 150&&look(dir)!=null) {
			Emma child = new Emma();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static String runStats(java.util.List<Critter> Emmas) {
		String printOut = new String();
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Emmas) {
			Emma c = (Emma) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		printOut += ("" + Emmas.size() + " total Emmas    ");
		printOut += ("" + total_straight / (GENE_TOTAL * 0.01 * Emmas.size()) + "% straight   ");
		printOut += ("" + total_back / (GENE_TOTAL * 0.01 * Emmas.size()) + "% back   ");
		printOut += ("" + total_right / (GENE_TOTAL * 0.01* Emmas.size()) + "% right   ");
		printOut += ("" + total_left / (GENE_TOTAL * 0.01 * Emmas.size()) + "% left   ");
		printOut += "\n";
		return printOut;
	}
	
	public CritterShape viewShape() { return CritterShape.DIAMOND; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.PLUM; }
	
	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return "Ellipse";
	}

	@Override
	public Color getColor() {
		return javafx.scene.paint.Color.PLUM;
	}
}