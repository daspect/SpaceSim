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
public class Interface {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException	{
		
		final int FRAME_SIZE = 850; 		// Pixels  : Frame on screen size (Square)
		
		SpaceSystem spaceSystem = new SpaceSystem(); //Container for the planets
		Sim sim = new Sim(FRAME_SIZE); 
		
		//Constants
		BigInteger EARTH_MASS = new BigInteger("5972190000000000000000000"); //kg
		BigInteger SUPER_EARTH_MASS = new BigInteger("59721900000000000000000000"); //kg
		BigInteger MOON_MASS = new BigInteger("73477000000000000000000"); //kg
		BigInteger SUN_MASS = new BigInteger("1989000000000000000000000000000"); //kg
		int EARTH_RADIUS = 6371; //km
		int MOON_RADIUS = 1737; //km
	
		//Ask the user to select a system.
		Scanner inp = new Scanner(System.in);
		System.out.println("Select a System");
		System.out.println("1) Earth and moon");
		System.out.println("2) Earth and ISS");
		System.out.println("3) Super Earth and Super Earth");
		System.out.println("4) Earth and hyperbolic spaceship");
		System.out.println("5) Two planets and a spaceship");
		int selection = inp.nextInt();
		
		switch(selection)	{
		case 1:	//Earth and Moon
				sim.setFieldOfView(800000); //km field of view
				sim.setTimeAccel(200000);
				Planet earth1 = new Planet (0,0,-0.013232590025901274,0,EARTH_RADIUS,EARTH_MASS,Color.blue,0);
				spaceSystem.addPlanet(earth1);
				Planet moon = new Planet (0,362600,1.022,0,MOON_RADIUS,MOON_MASS, Color.gray, 0);
				spaceSystem.addPlanet(moon);
				sim.setTitle("Earth and Moon");
				break;
		case 2:	//Earth and ISS
				sim.setFieldOfView(20000); //km field of view
				sim.setTimeAccel(800);
				Planet earth2 = new Planet (0,0,0,0,EARTH_RADIUS,EARTH_MASS,Color.blue, 0);
				spaceSystem.addPlanet(earth2);
				Planet iss = new Planet (0,6787,7.66,0,1,new BigInteger("450000"), Color.white, 0);
				spaceSystem.addPlanet(iss);
				sim.setTitle("Earth and ISS");
				break;
		case 3:	//Two Super Earth and Comets
				sim.setFieldOfView(200000); //km field of view
				sim.setTimeAccel(4000);
				Planet supEarth1 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
				spaceSystem.addPlanet(supEarth1);
				Planet supEarth2 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, new Color(255,100,100), 0);
				spaceSystem.addPlanet(supEarth2);
				sim.setSatMode(1); //Generate satellites
				sim.setTitle("Two Super Earth and Comets");
				break;
		case 4: //Earth and Hyperbolic Spaceship
				sim.setFieldOfView(60000); //km field of view
				sim.setTimeAccel(2000);
				Planet earth3 = new Planet (0,0,0,0,EARTH_RADIUS,EARTH_MASS,Color.blue, 0);
				spaceSystem.addPlanet(earth3);
				Planet ship = new Planet (0,6787,9.66,0,1,new BigInteger("100000"), Color.white, 0);
				spaceSystem.addPlanet(ship);
				sim.setTitle("Earth and Hyperbolic Spaceship");
				break;
		case 5:	//Two Super Earth and Two Spaceship
				sim.setFieldOfView(220000); //km field of view
				sim.setTimeAccel(5000);
				Planet superEarth3 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.darkGray, 0);
				spaceSystem.addPlanet(superEarth3);
				Planet superEarth4 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.magenta, 0);
				spaceSystem.addPlanet(superEarth4);
				Planet ship2 = new Planet (0,0,1,0,1000,new BigInteger("100000"), Color.red, 0);
				spaceSystem.addPlanet(ship2);
				Planet ship3 = new Planet (0,60000,7.6555,0,1000,new BigInteger("100000"), Color.blue, 0);
				spaceSystem.addPlanet(ship3);
				sim.setTitle("Two Super Earth and Two Spaceship");
				break;
		case 6:	//Testing
				sim.setFieldOfView(200000); //km field of view
				sim.setTimeAccel(4000);
				Planet supEarth10 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.green, 0);
				spaceSystem.addPlanet(supEarth10);
				Planet supEarth11 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.pink, 0);
				spaceSystem.addPlanet(supEarth11);
				sim.setSatMode(0); //Generate satellites
				sim.setTitle("Two Super Earth and Comets");
				break;
		case 7:	//Two Super Earth and Comets
				sim.setFieldOfView(200000); //km field of view
				sim.setTimeAccel(4000);
				Planet supEarth12 = new Planet (0,35000,7,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
				spaceSystem.addPlanet(supEarth12);
				Planet supEarth13 = new Planet (0,-35000,-7,0,EARTH_RADIUS,SUPER_EARTH_MASS, new Color(255,100,100), 0);
				spaceSystem.addPlanet(supEarth13);
				Planet supEarth14 = new Planet (35000,0,0,-7,EARTH_RADIUS,SUPER_EARTH_MASS,Color.GREEN, 0);
				spaceSystem.addPlanet(supEarth14);
				Planet supEarth15 = new Planet (-35000,0,0,7,EARTH_RADIUS,SUPER_EARTH_MASS, Color.DARK_GRAY, 0);
				spaceSystem.addPlanet(supEarth15);
				sim.setTitle("Two Super Earth and Comets");
				break;
		case 8:	//Two Super Earth and Comets
				sim.setFieldOfView(1000000); //km field of view
				sim.setTimeAccel(32000);
				Planet supEarth16 = new Planet (-300000,15000,10,2,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
				spaceSystem.addPlanet(supEarth16);
				Planet supEarth17 = new Planet (-300000,-15000,-10,2,EARTH_RADIUS,SUPER_EARTH_MASS, new Color(255,100,100), 0);
				spaceSystem.addPlanet(supEarth17);
				Planet supEarth18 = new Planet (300000,15000,10,-2,EARTH_RADIUS,SUPER_EARTH_MASS,Color.GREEN, 0);
				spaceSystem.addPlanet(supEarth18);
				Planet supEarth19 = new Planet (300000,-15000,-10,-2,EARTH_RADIUS,SUPER_EARTH_MASS, Color.DARK_GRAY, 0);
				spaceSystem.addPlanet(supEarth19);
				sim.setTitle("Two Super Earth and Comets");
				break;
		}
		
		
		
		sim.startSimulation(spaceSystem);
		
		if(sim.getSatMode() == 0) {
			sim.setSatMode(1); //Generate satellites;
			spaceSystem = new SpaceSystem(); //Container for the planets
			sim = new Sim(FRAME_SIZE); 
			sim.setTimeAccel(4000);
			sim.setFieldOfView(200000); //km field of view
			Planet supEarth12 = new Planet (0,15000,10,0,EARTH_RADIUS,SUPER_EARTH_MASS,Color.cyan, 0);
			spaceSystem.addPlanet(supEarth12);
			Planet supEarth13 = new Planet (0,-15000,-10,0,EARTH_RADIUS,SUPER_EARTH_MASS, Color.pink, 0);
			spaceSystem.addPlanet(supEarth13);
			sim.setTitle("Two Super Earth and Comets");
			sim.startSimulation(spaceSystem);
		}
	}
}
