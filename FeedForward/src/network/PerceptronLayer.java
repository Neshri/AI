package network;

import math.Sigmoid;

public class PerceptronLayer implements Layer {

	private Perceptron[] layer;
	
	
	public PerceptronLayer(int[] IDs) {
		layer = new Perceptron[IDs.length];
		for (int i = 0; i < IDs.length; i++) {
			layer[i] = new Perceptron(IDs[i], new Sigmoid());
		}
	}
	
	public PerceptronLayer(IDFactory idFact, int n) {
		layer = new Perceptron[n];
		for (int i = 0; i < n; i++) {
			layer[i] = new Perceptron(idFact.getID(), new Sigmoid());
		}
	}
	
	@Override
	public void fire() {
		for (Perceptron a : layer) {
			a.fire();
		}
	}
	

	@Override
	public Node[] getNodes() {
		return layer;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Perceptron a : layer) {
			str += a.toString() + "\n";
		}
		return str;
	}

	@Override
	public void backPropagate(double learningRate) {
		for (Perceptron a : layer) {
			a.backPropagate(learningRate);
		}
	}
}
