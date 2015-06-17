package spaceSimStuff;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class SpaceSystem {
	private static int MAX_PLANETS = 100;				// Simulation slows significantly with more than 100 bodies.
	private Planet[] planets = new Planet[MAX_PLANETS];	// Container for every body in the simulated system.
	private ArrayList<Planet> satellites = new ArrayList<Planet>();
	private int size;								// Number of bodies stored in planets
	private String title;
	private int fieldOfView;
	private double timeAccel;
	private boolean generateSatellites;
	private int numActiveSats;
	
	
	SpaceSystem() {}
	
	public void addPlanet(Planet planet) {
		planets[size] = planet;
		++size;
	}
	
	public void addAsteroid(Planet satellite) {
		satellites.add(satellite);
		++numActiveSats;
	}

	public void updateSystem(double deltaT) {
		for(int i = 0; i < size; ++i)	{			//Update acceleration and velocity of every planet in the system
			planets[i].updateAccVel(planets, size, deltaT);
		}
		for(int i = 0; i < satellites.size(); ++i)	{			//Update acceleration and velocity of every satellite in the system
			satellites.get(i).updateAccVel(planets, size, deltaT);
		}
		for(int i = 0; i < size; ++i)	{			//Update the position of every planet in the system
			planets[i].updatePos(deltaT);
		}
		for(int i = 0; i < satellites.size(); ++i)	{			//Update the position of every satellite in the system
			satellites.get(i).updatePos(deltaT);
		}
	}
	
	public PositionVector getCentroid() {
		BigDecimal sumWeightedX = new BigDecimal("0");
		for(int i = 0; i < size; ++i) {
			BigDecimal mass = new BigDecimal(planets[i].getMass());
			BigDecimal xVector = new BigDecimal(planets[i].getX());
			BigDecimal singleWeightedX = (mass.multiply(xVector));
			sumWeightedX = sumWeightedX.add(singleWeightedX);
		}
		BigDecimal sumWeightedY = new BigDecimal("0");
		for(int i = 0; i < size; ++i) {
			BigDecimal mass = new BigDecimal(planets[i].getMass());
			BigDecimal yVector = new BigDecimal(planets[i].getY());
			BigDecimal singleWeightedY = (mass.multiply(yVector));
			sumWeightedY = sumWeightedY.add(singleWeightedY);
		}
		BigDecimal sumMass = new BigDecimal("0");
		for(int i = 0; i < size; ++i) {
			BigDecimal mass = new BigDecimal(planets[i].getMass());
			sumMass = sumMass.add(mass);
		}
		BigDecimal xAvg = sumWeightedX.divide(sumMass, 25, RoundingMode.HALF_UP);
		BigDecimal yAvg = sumWeightedY.divide(sumMass, 25, RoundingMode.HALF_UP);
		PositionVector pos = new PositionVector(xAvg.doubleValue(), yAvg.doubleValue());
		
		return pos;
	}
	
	public int getNumActiveSats() {
		int num = 0;
		for (int i = 0; i < satellites.size(); ++i) {
			if(!satellites.get(i).getInactive())
				++num;
		}
		return num;
	}
	
	public Planet getPlanet(int index) {
		return planets[index];
	}
	
	public Planet getAsteroid(int index) {
		return satellites.get(index);
	}
	
	public void decrementNumActiveSats() {
		--numActiveSats;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getNumSats() {
		return satellites.size();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(int fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	public double getTimeAccel() {
		return timeAccel;
	}

	public void setTimeAccel(double timeAccel) {
		this.timeAccel = timeAccel;
	}

	public boolean isGenerateSatellites() {
		return generateSatellites;
	}

	public void setGenerateSatellites(boolean generateSatellites) {
		this.generateSatellites = generateSatellites;
	}
	
	public void removeAsteroid(int index) {
		//depricated
	}
}

