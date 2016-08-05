package application;

import java.util.*;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

	public abstract class Critter {
		/* NEW FOR PROJECT 5 */ 
		public enum CritterShape { CIRCLE, SQUARE, TRIANGLE, DIAMOND, STAR} /* the default color is white, which I hope makes critters invisible by
		default
		 * If you change the background color of your View component, then update
		the default
		 * color to be the same as you background  *  * critters must override at least one of the following three methods, it
		is not
		 * proper for critters to remain invisible in the view  *  * If a critter only overrides the outline color, then it will look like a
		non-filled
		 * shape, at least, that's the intent. You can edit these default methods
		however you
		 * need to, but please preserve that intent as you implement them.  */ 
		public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
		}
		public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); } public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
		public abstract CritterShape viewShape(); 
		protected String look(int direction, boolean steps) {
			return null;
		}
	private static ArrayList<Critter> critterCollection = new ArrayList<Critter>();
	private static java.util.Random rand = new java.util.Random();
//	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private int energy = 0;
	private int x_coord;
	private int y_coord;
	private boolean moved = false;
	private position pos = new position();
	
	public abstract String getShape();
	public abstract String getColor();
	public int getX(){
		return x_coord;
	}
	public int getY(){
		return y_coord;
	}
	public static ArrayList<Critter> getCollection(){
		return critterCollection;
	} 
	private class position {
		int x;
		int y;

		public boolean equals(position other) {
			if (x == other.x && y == other.y) {
				return true;
			} else
				return false;
		}
		public position()
		{
			this.x = x_coord;
			this.y = y_coord;
		}
		
	}
	Critter.position position = new position();
	private static Map<position, Critter> map = new HashMap<position, Critter>();

	public static int getRandomInt(int max) 
	{
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) 
	{
		rand = new java.util.Random(new_seed);
	}
	
	protected int getEnergy() { return energy; }
	
	protected final void walk(int direction) 
	{
		this.energy -= Params.walk_energy_cost;
		this.moved = true;
		if(this.energy <= 0)
			critterCollection.remove(this);
		else
			calculateMovement(direction, 1);
	}
	
	protected final void run(int direction) 
	{
		this.energy -= Params.run_energy_cost;
		this.moved = true;
		if(this.energy <= 0)
			critterCollection.remove(this);
		else
			calculateMovement(direction, 2);
	}
	
	private position calculateMovement(int dir, int movement)
	{
		switch(dir)
		{
			case 0:
				if(x_coord + movement < Params.world_width - 1)
					x_coord += movement;
				else
					x_coord = ((x_coord + movement)%Params.world_width);
				break;
			case 1:
				if(x_coord + movement < Params.world_width - 1)
					x_coord += movement;
				else
					x_coord = ((x_coord + movement)%Params.world_width);
				if(y_coord - movement < 0)
					y_coord = ((y_coord - movement)%Params.world_height);
				else
					y_coord -= movement;
				break;
			case 2:
				if(y_coord - movement < 0)
					y_coord = ((y_coord - movement)%Params.world_height);
				else
					y_coord -= movement;
				break;
			case 3:
				if(x_coord - movement < 0)
					x_coord = ((x_coord - movement)%Params.world_width);
				else
					x_coord -= movement;
				if(y_coord - movement < 0)
					y_coord = ((y_coord - movement)%Params.world_height);
				else
					y_coord -= movement;
				break;
			case 4:
				if(x_coord - movement < 0)
					x_coord = ((x_coord - movement)%Params.world_width);
				else
					x_coord -= movement;
				break;
			case 5:
				if(x_coord - movement < 0)
					x_coord = ((x_coord - movement)%Params.world_width);
				else
					x_coord -= movement;
				if(y_coord + movement > Params.world_height - 1)
					y_coord = ((y_coord + movement)%Params.world_height);
				else
					y_coord += movement;
				break;
			case 6:
				if(y_coord + movement > Params.world_height - 1)
					y_coord = ((y_coord + movement)%Params.world_height);
				else
					y_coord += movement;
				break;
			case 7:
				if(x_coord + movement > Params.world_width - 1)
					x_coord = ((x_coord + movement)%Params.world_width);
				else
					x_coord += movement;
				if(y_coord + movement > Params.world_height - 1)
					y_coord = ((y_coord + movement)%Params.world_height);
				else
					y_coord += movement;
				break;
		}
		pos.x = x_coord;
		pos.y = y_coord;
		return pos;
	}
	
	
	protected final void reproduce(Critter offspring, int direction) 
	{
		if(this.energy < Params.min_reproduce_energy)
			return;
		this.energy -= Params.min_reproduce_energy;
		
		offspring.energy = (int) Math.floor(this.energy/2);
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.calculateMovement(direction, 1);
		if(this.energy >= 0)
			critterCollection.remove(this);
		else
			this.energy = (int) Math.ceil(this.energy/2);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		Class<?> cName = Class.forName(critter_class_name);
		Critter critter = (Critter) cName.newInstance();
		critter.energy = Params.start_energy;
		critter.x_coord = getRandomInt(Params.world_width);
		critter.y_coord = getRandomInt(Params.world_height);
		critterCollection.add(critter);
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException, ClassNotFoundException 
	{
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		for(Critter c: critterCollection)
		{
			if(Class.forName(critter_class_name).isInstance(c))
			{
				result.add(c);
			}
		}
		return result;
	}
	
	/**
	 * @param critters
	 */
	public static void runStats(List<Critter> critters)
	{
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		
		for (Critter crit : critters) 
		{
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) 
			{
				critter_count.put(crit_string,  1);
			} 
			else 
			{
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) 
		{
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
		
	public static void worldTimeStep() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidCritterException 
	{   
		for (int i = 0; i < critterCollection.size(); i++) {
			critterCollection.get(i).doTimeStep();
		}
		Iterator<Critter> iterator = critterCollection.iterator();
		while(iterator.hasNext()){
			Critter c1 =iterator.next();
			if(!map.containsKey(c1.pos)){
				map.put(c1.position, c1);
			}
			else{
				Critter c2 = map.get(c1.pos);
				doEncounter(c1,c2);
			}
		}

		for(Critter c : critterCollection)
		{
			if(c.moved == false)
			{
				c.energy -= Params.rest_energy_cost;
			}
		}
		
		for(int i=0;i<Params.refresh_algae_count;i++){
				makeCritter("project4.Algae");
		}

		Iterator <Critter> iterator1 = critterCollection.iterator();
		while(iterator1.hasNext()){
			Critter c = iterator1.next();
			if(c.energy<=0) iterator1.remove();
		}

		critterCollection.addAll(babies);
		
		for(Critter c: critterCollection)
		{
			c.moved = false;
		}
	}
	
	private static void doEncounter(Critter A, Critter B) {
		boolean Awin;
		int rollsA = 0, rollsB = 0;
		boolean killB = A.fight(B.toString());
		boolean killA = B.fight(A.toString());
		if(killB==false){
			if(A.moved==false){
				int dir = Critter.getRandomInt(8);
				position prepos = null;
				prepos.x=A.calculateMovement(dir, 2).x;
				prepos.y=A.calculateMovement(dir, 2).y;
                if(!map.containsKey(prepos)){
                	int rand = getRandomInt(2);
                	if(rand == 0)
                		A.walk(dir);
                	else
                		A.run(dir);
                	
                }
				
			}
		}
		
		if(killA==false){
			if(B.moved==false){
				int dir = Critter.getRandomInt(8);
				position prepos=null;
				prepos.x=B.calculateMovement(dir, 2).x;
				prepos.y=B.calculateMovement(dir, 2).y;
                if(!map.containsKey(prepos)){
                	int rand = getRandomInt(2);
                	if(rand == 0)
                		B.walk(dir);
                	else
                		B.run(dir);
                }
				
			}
		}
		

		if (killA == true && killB == true && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = Critter.getRandomInt(A.energy);
			rollsB = Critter.getRandomInt(B.energy);
		}
		if (killA == false && killB == true && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = 0;
			rollsB = Critter.getRandomInt(B.energy);
		}
		if (killA == true && killB == false && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = Critter.getRandomInt(A.energy);
			rollsB = 0;
		} else {
			rollsA = 0;
			rollsB = 0;
		}
		if (rollsA > rollsB)
		   Awin = true;
		else if (rollsB > rollsA)
			Awin = false;
		else
			Awin= true;
		
		if(Awin) {A.energy = (int) (A.energy+0.5*B.energy);B.energy = (int) (0.5*B.energy);}
		else {
			B.energy = (int) (B.energy+0.5*A.energy);
			A.energy=(int) (0.5*A.energy);
		}
}
	
	public static void displayWorld() 
	{
		int rows = Params.world_height;
		int cols= Params.world_width;

		for(int j = 0;j < cols+2;j++)
		{
			if(j==0 || j == cols+1)
				System.out.print("+");
			else
				System.out.print("-");
		}
		System.out.print("\n");
		for(int i = 0; i < rows;i++)
		{
			System.out.print("|");
			for(int j = 0; j < cols;j++)
			{
				boolean critterFlag = false;
				for(Critter c : critterCollection)
				{
					if((c.y_coord == i) && (c.x_coord == j) && (critterFlag == false))
					{
						System.out.print(c);
						critterFlag = true;
					}	
				}
				if(critterFlag == false)
					System.out.print(" ");

			}
			System.out.print("|");
			System.out.print("\n");
		}

		for(int j = 0;j < cols+2;j++)
		{
			if(j==0 || j == cols+1)
				System.out.print("+");
			else
				System.out.print("-");
		}
		System.out.print("\n");
		return;
	}
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	@Override
	public String toString() { return ""; }
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter 
	{
		protected void setEnergy(int new_energy_value) 
		{
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) 
		{
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) 
		{
			super.y_coord = new_y_coord;
		}
	}
	
	
}