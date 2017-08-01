package math;

public class Sigmoid implements Function {

	private double lastResult = 0;
	private double lastGradient = 0;
	private boolean newGradient = false;

	@Override
	public void fire(double... vals) {
		double sum = 0;
		for (double a : vals) {
			sum += a;
		}
		lastResult = 1.0 / (1.0 + Math.pow(Math.E, -sum));
		newGradient = true;
	}

	// lastResult*(1-lastResult)
	@Override
	public double getGradient() {
		if (newGradient) {
			newGradient = false;
			lastGradient = lastResult * (1 - lastResult);
		}
		return lastGradient;
	}

	@Override
	public double getResult() {
		return lastResult;
	}

}
