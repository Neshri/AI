package network;

public class Sigmoid implements Function {

	private double lastResult = 0;
	
	@Override
	public void fire(double ...vals) {
		double sum = 0;
		for (double a : vals) {
			sum += a;
		}
		lastResult = 1.0/(1.0+Math.pow(Math.E, -sum));
	}
	
	@Override
	public double getResult() {
		return lastResult;
	}

}
