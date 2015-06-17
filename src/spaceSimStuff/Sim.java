package spaceSimStuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**The Sim class handles the simulation and animation of a collection of bodies in space (objcets of the Planet class).
 * Its members hold all the parameters of the simulation as well as the settings for the Jframe window it creates. The user
 * defines several constants when the class is initialized. The rest of the parameters are passed when the simulation is started.
 * 
 * @author Dane Schoelen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Sim extends JPanel	{
	
	private final int FRAME_SIZE; 		// Pixels  : Frame on screen size (Square)
	private final double FRAME_TIME;	// Seconds  : standard simulation time elapsed in one frame.
	private final int MAX_PLANETS;		// Simulation slows significantly with more than 100 bodies.
	
	private int frameDelay;				// Milliseconds  : delay added in each frame (16ms for about 60 fps)
	private SpaceSystem spaceSystem;
	private double timeAccel;			/* multiplier  : Increases the simulation time elapsed in one frame
												(THIS VALUE IMPACTS THE RESULT AND ACCURACY OF THE SIMULATION) */
	private int fieldOfView;			// km  : Square centered on 0,0
	private int satMode;				// Random satellite generator
	private double nextComet;			// Seconds  : Time when next satellite will be generated
	private double deltaT;				// Seconds  : simulation time elapsed in one frame after multiplication with time acceleration
	private double time;				// Seconds  :  Total simulation time elapsed
	private JFrame frame;				// The simulation window object
	private double pixelScale;			// km per pixel
	private String title;		// Title of the frame window, may be changed with setTitle()
	
	
	public Sim (int FRAME_SIZE)	{
		this.FRAME_SIZE = FRAME_SIZE;
		FRAME_TIME = 0.0167; 			// Seconds  : simulation time elapsed in one frame. ( 60 frames = 1 sec)
		MAX_PLANETS = 100;				// Simulation slows significantly with more than 100 bodies.
		frameDelay = 16;				// Milliseconds  : delay added in each frame (16ms for about 60 fps)
		timeAccel = 1;				
		fieldOfView = 100000;			// km
		satMode = -1;					// inactive by default 
		title = "Space";
	}

	public void startSimulation(SpaceSystem spaceSystem) throws InterruptedException, FileNotFoundException	{
		// Initialize simulation parameters
		this.spaceSystem = spaceSystem;
		deltaT = FRAME_TIME * timeAccel; //Calculate the simulation time elapsed in every frame.
		pixelScale = fieldOfView / FRAME_SIZE;	// km per pixel 
		
		// Create the simulation window
		frame = new JFrame(title);
		frame.add(this);
		frame.setSize(FRAME_SIZE, FRAME_SIZE + 22);  // 22 pixels added to account for the size of the window bar at the top of the frame
		frame.setLocationRelativeTo(null); // Center the panel on the screen
		frame.setAlwaysOnTop(true);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.repaint(); 	//first paint: show planets in initial state
		Thread.sleep(300); //lead time to allow the simulation to start without lag
		
		if(satMode == 1)		//Generate satellites if required
			satalliteCreatorFromFile();
		if(satMode == 0)		//Generate satellites if required
			satalliteCreator();
		if(satMode == 2)
			satallitesFromFileRandom();
			
		System.out.println(spaceSystem.getNumSats());
		simulate();	// Begin simulation
	}
	
	private void simulate() throws InterruptedException, FileNotFoundException	{
		while(true)	{	//loop until Jframe is exited by the user
			spaceSystem.updateSystem(deltaT);
			time += deltaT;		//Update total time elapsed
			frame.repaint();
			Thread.sleep(frameDelay);					//Delay to approximately match required frame rate.
			if((satMode == 0 || satMode == 2) && (spaceSystem.getNumActiveSats() < 51)) {
				recordSatallites();
				frame.setTitle("Resetting...");;
				frame.setVisible(false);
				frame.dispose();
				System.out.println("\t" + time);
				return;
			}
		}
	}
	
	public void paint(Graphics g) {
		// Wet the brush
		super.paint(g);	// Clears the screen
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	// Improves render quality
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Helvetica", Font.PLAIN, 20);
        g.setFont(font);
		g.setColor(Color.gray);
		String infoStr = "Time: x" + Double.toString(timeAccel) + "     Scale: " + pixelScale + " km/pixel";
		
		// Paint!
		g.drawString(infoStr, 20, 24);
		if (satMode >= 0)
			g.drawString((Integer.toString(spaceSystem.getNumActiveSats()) + " Satellites"), FRAME_SIZE - 150, 20);
		for(int i = 0; i < spaceSystem.getSize(); ++i)	{ //Paint each planet
			paintPlanet(spaceSystem.getPlanet(i), spaceSystem.getPlanet(i).getTimeGenerated(), false, g2d);
		}
		for(int i = 0; i < spaceSystem.getNumSats(); ++i)	{ //Paint each satellite
			paintPlanet(spaceSystem.getAsteroid(i), spaceSystem.getAsteroid(i).getTimeGenerated(), true, g2d);
		}
	}
	
	private void paintPlanet(Planet planet, double timeGenerated, boolean satMode, Graphics2D g)	{
		if(planet.getInactive())
			return;
		int centerPix = FRAME_SIZE / 2;		// Find the pixel location of the center of the screen
		//PositionVector centroid = spaceSystem.getCentroid();
		double cornerX = (planet.getX() - planet.getPlanetRadius()) / pixelScale;	 //find the upper left corner pixel of the planet sprite
		//double cornerX = (planet.getX() - planet.getPlanetRadius() - centroid.x()) / pixelScale;
		double cornerY = (planet.getY() - planet.getPlanetRadius()) / pixelScale;
		//double cornerY = (planet.getY() - planet.getPlanetRadius() - centroid.y()) / pixelScale;
		
		int pixX = centerPix + ((int) cornerX); 	// Translates imaginary pixel location (center at 0,0) to actual location (center at top left corner of frame)
		int pixY = centerPix + ((int) cornerY);
		if(satMode && (Math.abs(cornerX) > FRAME_SIZE || Math.abs(cornerY) > FRAME_SIZE)) {
			planet.deactivate();
		}
		int diameter = (int)((planet.getPlanetRadius() * 2) / pixelScale); 	// Find the pixel diameter of the planet
		if (diameter < 8)		// If the planet is too small make it bigger so it can be seen
			diameter = 8;
		g.setColor(planet.getColor());
		g.fillOval(pixX, pixY, diameter, diameter);		// Paint the planet
		g.setColor(Color.ORANGE);
		//g.drawString(Integer.toString((int)((time - timeGenerated)/1000)), pixX, pixY);
	}
	
	private void satalliteCreator() {
		for (int i = 0; i < 10000; ++i) {
			spaceSystem.addAsteroid(new Planet (((fieldOfView / 2) - (Math.random() * fieldOfView)), ((fieldOfView / 2) - (Math.random() * fieldOfView)), (Math.random() * 12) - 6, (Math.random() * 12) - 6,
															100,new BigInteger("100000"), Color.white, time));
		}
	}
	
	private void satalliteCreatorFromFile() throws FileNotFoundException {
		Scanner read = new Scanner(new File("satallites.txt"));
		while(read.hasNext()) {
			spaceSystem.addAsteroid(new Planet (Double.parseDouble(read.next()), Double.parseDouble(read.next()), Double.parseDouble(read.next()), Double.parseDouble(read.next()),
															100,new BigInteger("100000"), Color.white, time));
		}
	}
	
	private void satallitesFromFileRandom() throws FileNotFoundException {
		satalliteCreatorFromFile();
		satalliteCreator();
	}
	
	private void recordSatallites() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("satallites.txt"));
		for(int i = 0; i < spaceSystem.getNumSats(); ++i) {
			if(!spaceSystem.getAsteroid(i).getInactive()) {
				pw.write(Double.toString(spaceSystem.getAsteroid(i).getInitX()) + " ");
				pw.write(Double.toString(spaceSystem.getAsteroid(i).getInitY()) + " ");
				pw.write(Double.toString(spaceSystem.getAsteroid(i).getInitVx()) + " ");
				pw.write(Double.toString(spaceSystem.getAsteroid(i).getInitVy()) + "\n");
			}
		}
		pw.close();
	}
	
	private void satallites() {		
		if (time > nextComet && spaceSystem.getSize() < MAX_PLANETS) {
			nextComet += Math.random() * 7000;
			spaceSystem.addAsteroid(new Planet (-(fieldOfView / 2) - 1000, 0, Math.random() * 8, (Math.random() * 22) - 11,
															100,new BigInteger("100000"), Color.white, time));
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSatMode(int mode) {
		satMode = mode;
	}
	
	public int getSatMode() {
		return satMode;
	}
	
	public void setFrameDelay(int fRAME_RATE) {
		frameDelay = fRAME_RATE;
	}
	
	public void setTimeAccel(double timeAccel) {
		this.timeAccel = timeAccel;
	}
	
	public void setFieldOfView(int fieldOfView) {
		this.fieldOfView = fieldOfView;
	}
}



