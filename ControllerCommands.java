package application;


import java.lang.reflect.Method;
import java.util.List;

public class ControllerCommands
{
	public static void processCommand(String str)
	{
		switch(str)
		{
			case "quit":
				System.exit(0);
			case "show":
				Critter.displayWorld();
				return;
			case "step":
				try {
					Critter.worldTimeStep();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| InvalidCritterException e1) {
					System.out.println("error processing: " + str);
				}
				return;
		}
		
		String[] input = str.split(" ");
		if(input.length <= 3 && input.length > 0)
		{
			switch(input[0])
			{
				case "step":
					if(input.length == 2)
					{
						processStep(str,input[1]);
						return;
					}
				case "seed":
					if(input.length == 2)
					{
						try{
							long seed = Integer.parseInt(input[1]);
							Critter.setSeed(seed);
						} catch (Exception e) {
							System.out.println("error processing: " + str);
						} 
						return;
					}
					
				case "make":
					if(input.length == 3)
					{
						processMakeWithCount(str,input[1],input[2]);
						return;
					}
					else if(input.length == 2)
					{
						processMake(str,input[1]);
						return;
					}
				case "stats":
					if(input.length == 2)
					{
						try{
							processStats(str,input[1]);
						}catch (Exception e) {
							System.out.println("error processing: " + str);
						} 
						return;
					}
					
			}
		}
		System.out.println("Invalid command: " + str);
	}
	
	
	private static void processMake(String str, String name)
	{
		try{
			String className = name;
			Critter.makeCritter(className);
		} catch (Exception e) {
			System.out.println("error processing: " + str);
		} 
	}
	
	
	private static void processMakeWithCount(String str, String name, String num)
	{
		try{
			String className = name;
			int count = Integer.parseInt(num);
			for(int i = 0;i < count;i++)
				Critter.makeCritter(className);
		} catch (Exception e) {
			System.out.println("error processing: " + str);
		} 
	}
	
	
	private static void processStep(String str,String num)
	{
		try{
			int count = Integer.parseInt(num);
			for(int i = 0;i < count;i++)
				Critter.worldTimeStep();
		}catch(Exception e){
			System.out.println("error processing: " + str);
		}
	}
	
	
	private static void processStats(String str, String name)
	{
		try{
			Class<?> critterName = Class.forName(name);
			List<Critter> list = Critter.getInstances(name);
			Method m = critterName.getMethod("runStats",List.class);
			m.invoke(null, list);
		} catch (Exception e) {
			System.out.println("error processing: " + str);
		} 
	}
}