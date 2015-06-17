package spaceSimStuff;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**The planet class contains all of the properties of a body in a space system as well as methods for updating those properties.
 * It is intended to be used with a simulator that updates on a per frame basis. Every parameter is initialized when the planet
 * object is created. The acceleration (and corresponding velocity change) due to other bodies then the planets position are to
 * be updated, in that order, every frame. Note that the simulator update the acceleration/velocity of every planet first, then
 * update their positions.
 * 
 * @author Dane Schoelen
 * @version 1.0
 */
public class Planet {
	public static BigDecimal GRAV_CST = new BigDecimal("0.0000000000667384"); //m^3 * s^-2 * kg^-1  : Universal Gravitational Constant

	private double x = 0;			//km  : position in x plane relative to the center of the field of view (0,0)
	private double y = 0;			//km  : position in y plane relative to the center of the field of view (0,0)
	private double vx = 0;			//km per second  : in the x plane
	private double vy = 0;			//km per second  : in the y plane
	private double ax = 0;			//km per second per second  : in the x plane
	private double ay = 0;			//km per second per second  : in the y plane
	private BigInteger mass;		//kg
	private int planetRadius = 0;	//km
	private Color color;			//Color of the planet
	private double timeGenerated;
	private String name;
	private boolean inactive;
	private boolean satellite;
	
	private double initX = 0;			//km  : position in x plane relative to the center of the field of view (0,0)
	private double initY = 0;			//km  : position in y plane relative to the center of the field of view (0,0)
	private double initVx = 0;			//km per second  : in the x plane
	private double initVy = 0;			//km per second  : in the y plane
	

	/**Construct Planet with initial conditions
	 * 
	 * @param initialX  km : Starting location in X axis: From the center of the screen
	 * @param initialY	km : Starting location in Y axis: From the center of the screen (y values ascend from top to bottom)
	 * @param initialVx km per second : Starting velocity in X axis: From the center of the screen
	 * @param initialVy km per second : Starting velocity in Y axis: From the center of the screen (positive yv is towards the bottom of the screen)
	 * @param planetRadius km
	 * @param mass kg
	 * @param color The Color the planet will be rendered in
	 */
	public Planet (double initialX, double initialY, double initialVx, double initialVy,
													int planetRadius, BigInteger mass, Color color, double timeGenerated) {
		x = initialX;
		y = initialY;
		vx = initialVx;
		vy = initialVy;
		this.mass = mass;
		this.planetRadius = planetRadius;
		this.color = color;
		this.timeGenerated = timeGenerated;
		inactive = false;
		
		initX = initialX;
		initY = initialY;
		initVx = initialVx;
		initVy = initialVy;
		
		if(mass.equals(new BigInteger("0")))
			satellite = true;
	}
	
	/**Update the velocity vector of the planet due to the accelerations of every other body in the system
	 * 
	 * @param planets The planets array
	 * @param size The current number of planets in the planets array
	 * @param deltaT The time that is passed in this update increment (frame)
	 */
	public void updateAccVel (Planet[] planets, int size, double deltaT) {
		if(inactive)
			return;
		//Calculate the acceleration on the planet due to every other planet in the system, one at a time.
		for(int i = 0; i < size; ++i){ //For every other body in the system
			if (planets[i].equals(this))	// Check to prevent calculating acceleration on the planet due to itself
				continue;
			else {
				//Compute radii
					//radius as a scalar
				double hypotenuse = Math.sqrt(Math.pow(planets[i].x - this.x, 2) + Math.pow(planets[i].y - this.y, 2));
					//radius as vector components
				if(satellite && (hypotenuse < planets[i].getPlanetRadius() + this.planetRadius)) {
					this.deactivate();
					return;
				}
				double radX = planets[i].x - this.x;
				double radY = planets[i].y - this.y;
					//radius as unit vector components
				double scalarX = radX / hypotenuse;
				double scalarY = radY / hypotenuse;
				
				//Compute acceleration as a scalar
				BigDecimal otherBMassBD = new BigDecimal(planets[i].mass);
				BigDecimal gCstProductMass = GRAV_CST.multiply(otherBMassBD);
				BigDecimal radiusSqrBD = new BigDecimal(Math.pow((hypotenuse * 1000), 2)); //convert radius in km to m
				BigDecimal divide = gCstProductMass.divide(radiusSqrBD, 25, RoundingMode.HALF_UP);
				double a = divide.doubleValue(); // m per sec^2
				a = a / 1000.0; //convert to km
				//Compute acceleration as component vectors
				ax = a * scalarX;
				ay = a * scalarY;
				//Compute velocity change due to (acceleration * time) and update velocity
				vx += ax * deltaT;
				vy += ay * deltaT;
			}
		}
		//Velocity is now the vector sum of the initial velocity and the velocity vectors due to every other body in the system.
	}
	
	/** Update the planets position by calculating its travel along its current velocity vector for an increment of time
	 * @param deltaT The time that is passed in this update increment (frame)
	 */
	public void updatePos (double deltaT) {
		if(inactive)
			return;
		//Compute position change due to (velocity * time) and update position
		x += vx * deltaT;
		y += vy * deltaT;
	}
	
	public void deactivate() {
		inactive = true;
	}
	
	public void activate() {
		inactive = false;
	}
	
	public boolean getInactive() {
		return inactive;
	}
	
	public double getInitX() {
		return initX;
	}

	public double getInitY() {
		return initY;
	}

	public double getInitVx() {
		return initVx;
	}

	public double getInitVy() {
		return initVy;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getPlanetRadius() {
		return planetRadius;
	}

	public Color getColor() {
		return color;
	}
	
	public BigInteger getMass() {
		return mass;
	}
	
	public void setMass(BigInteger mass) {
		this.mass = mass;
	}
	
	public double getTimeGenerated() {
		return timeGenerated;
	}
}
