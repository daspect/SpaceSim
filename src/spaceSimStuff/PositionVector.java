package spaceSimStuff;

public class PositionVector {
	private double[] vector = new double[2];
	
	PositionVector (double x, double y) {
		vector[0] = x;
		vector[1] = y;
	}
	
	public double x () {
		return vector[0];
	}
	public double y () {
		return vector[1];
	}
}
