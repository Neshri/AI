package filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrainParser {

	public static class TrainSet {
		public double[] in, target;
		public TrainSet(double[] in, double[] target) {
			this.in = in;
			this.target = target;
		}
	}
	
	public static TrainSet[] getSets(String filePath) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filePath));
		
		int n = sc.nextInt();
		TrainSet[] send = new TrainSet[n];
		int ins = sc.nextInt();
		int outs = sc.nextInt();
		for (int i = 0; i < n; i++) {
			double[] in = new double[ins];
			for (int j = 0; j < ins; j++) {
				in[j] = sc.nextDouble();
			}
			double[] target = new double[outs];
			for (int j = 0; j < outs; j++) {
				target[j] = sc.nextDouble();
			}
			send[i] = new TrainSet(in, target);
		}
		
		sc.close();
		return send;
	}
}
