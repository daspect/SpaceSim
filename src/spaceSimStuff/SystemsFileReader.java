package spaceSimStuff;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemsFileReader {
	Scanner reader;
	public SystemsFileReader() throws FileNotFoundException {
		reader = new Scanner(new File("SpaceSystems.txt"));
	}

	public ArrayList<SpaceSystem> readFile (String fileName) {
		String next = reader.nextLine();
		ArrayList<SpaceSystem> systems = new ArrayList<SpaceSystem>();
		int index = 0;
		while(next.charAt(0) == '*') {	//move through opening text
			next = reader.nextLine();
		}
		while(reader.hasNextLine()){ //while there are still more systems to be read
			systems.add(new SpaceSystem());
			systems.get(index).setTitle(next);
			reader.nextLine();
			systems.get(index).setFieldOfView(reader.nextInt());
			reader.nextLine();
			reader.nextLine();
			systems.get(index).setTimeAccel(reader.nextInt());
			reader.nextLine();
			reader.nextLine();
			systems.get(index).setGenerateSatellites(reader.nextBoolean());
			reader.nextLine();
			reader.nextLine();
			
			while(next.charAt(0) != 'x' ){
				String name = reader.nextLine();
				double x = reader.nextDouble();
				double y = reader.nextDouble();
				double vx = reader.nextDouble();
				double vy = reader.nextDouble();
				reader.nextLine();
				int radius = reader.nextInt();
				reader.nextLine();
				BigInteger mass = new BigInteger(reader.nextLine());
				int r = reader.nextInt();
				int g = reader.nextInt();
				int b = reader.nextInt();
				reader.nextLine();
				int timeGen = reader.nextInt();
				reader.nextLine();
				next = reader.nextLine();
//				systems.get(index).addPlanet(new Planet(x,y,vx,vy,radius,mass,new Color(r,g,b), timeGen,spaceSystem));
			}
			if(reader.hasNextLine()) {
				next = reader.nextLine();
			}
			++index;
		}
		return systems;
		
	}
}
