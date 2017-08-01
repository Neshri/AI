package math;

public class CustomMath {

	public static double nonZeroRandom(double randomRate) {
		if (randomRate == 0.0) {
			return 1.0;
		}
		double send = 0.5+Math.random()*randomRate;
		if (Math.random() > 0.5) send *= -1.0;
		return send;
	}
}
