package spaceSimStuff;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

/**	This program simulates a system of bodies in space. Bodies are of the 'Planet' class
 * and have properties and initial conditions set in the main method. The simulation and calculations
 * are carried out one frame at a time. In each frame the acceleration on each body (due to every other body in the system)
 * is calculated and the velocity updated. After the velocities are updated every planet's position is updated. The user is
 * presented with a selection of preset systems. Once selected a jframe is created and the simulation runs until the user closes
 * the frame. The outcome of the simulation is highly dependent on initial conditions.
 * 
 * @author Dane A. Schoelen
 * @version 1.0
 */
public class OrbitFinder {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException	{
		
		
		final int FRAME_SIZE = 850; 		// Pixels  : Frame on screen size (Square)
		
		SpaceSystem spaceSystem = new SpaceSystem(); //Container for the planets
		Sim sim = new Sim(FRAME_SIZE); 
		
		//Constants
		BigInteger EARTH_MASS = new BigInteger("5972190000000000000000000"); //kg
		BigInteger SUPER_EARTH_MASS = new BigInteger("59721900000000000000000000"); //kg
		BigInteger MOON_MASS = new BigInteger("73477000000000000000000"); //kg
		//BigInteger SUN_MASS = new BigInteger("1989000000000000000000000000000"); //kg
		int EARTH_RADIUS = 6371; //km
		int MOON_RADIUS = 1737; //km
	
		sim.setFieldOfView(200000); //km field of view
		sim.setTimeAccel(4000); 
		Planet supEarth10 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
		spaceSystem.addPlanet(supEarth10);
		Planet supEarth11 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.pink, 0);
		spaceSystem.addPlanet(supEarth11);
		sim.setSatMode(0);
		sim.setTitle("Two Super Earth and Comets");
		sim.setFrameDelay(0);
		sim.startSimulation(spaceSystem);
		
		
		for(int i = 0; i < 5; ++i) {
			spaceSystem = new SpaceSystem(); //Container for the planets
			sim = new Sim(FRAME_SIZE); 
			sim.setSatMode(2);
			sim.setFieldOfView(200000); //km field of view
			sim.setTimeAccel(4000); 
			sim.setFrameDelay(0);
			Planet supEarth1 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
			spaceSystem.addPlanet(supEarth1);
			Planet supEarth2 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.pink, 0);
			spaceSystem.addPlanet(supEarth2);
			sim.setTitle("Two Super Earth and Comets");
			sim.startSimulation(spaceSystem);
		}
		
		
		spaceSystem = new SpaceSystem(); //Container for the planets
		sim = new Sim(FRAME_SIZE); 
		sim.setSatMode(1);
		sim.setFieldOfView(200000); //km field of view
		sim.setTimeAccel(4000); 
		Planet supEarth1 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
		spaceSystem.addPlanet(supEarth1);
		Planet supEarth2 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.pink, 0);
		spaceSystem.addPlanet(supEarth2);
		sim.setTitle("Two Super Earth and Comets");
		sim.startSimulation(spaceSystem);
	}
}
