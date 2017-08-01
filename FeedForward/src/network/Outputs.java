package network;

import java.util.ArrayList;

import math.Function;
import math.Sigmoid;

public class Outputs implements Layer {

	public static class Output implements Node {

		public double target;
		private double value;
		private ArrayList<Link> in = new ArrayList<Link>();
		private int ID;
		private Function activation;

		public Output(int ID, Function act) {
			this.ID = ID;
			if (act != null)
				activation = act;
			else
				activation = new Sigmoid();
		}

		public void fire() {
			double sum = 0;
			for (Link a : in) {
				sum += a.weight * a.value;
			}
			activation.fire(sum);
			value = activation.getResult();
		}

		public double getValue() {
			return value;
		}

		@Override
		public void backPropagate(double learningRate) {
			// (dE/dO)*(dO/dSum)
			double mult = (value - target) * activation.getGradient();
			double grad = mult;
			mult *= -learningRate;
			for (Link a : in) {
				a.weight = a.weight + mult * a.value;
				// update gradient
				a.gradient = grad;
			}
		}

		@Override
		public void connectToInput(Link l) {
			in.add(l);
		}

		@Override
		public void connectToOutput(Link l) {
		}

		@Override
		public String toString() {
			String str = "Output:"+ID+"\nt" + Network.FORMAT.format(target) + ":v" + Network.FORMAT.format(value);
			return str;
		}

		@Override
		public int getID() {
			return ID;
		}

		@Override
		public int getNbrOutput() {
			return 0;
		}

		@Override
		public String[] getOutputLinkWeights() {
			return new String[0];
		}
	}

	private Output[] out;

	public Outputs(int[] IDs) {
		out = new Output[IDs.length];
		for (int i = 0; i < IDs.length; i++) {
			out[i] = new Output(IDs[i], new Sigmoid());
		}
	}

	public Outputs(IDFactory idFact, int n) {
		out = new Output[n];
		for (int i = 0; i < n; i++) {
			out[i] = new Output(idFact.getID(), new Sigmoid());
		}
	}

	public void fire() {
		for (Output a : out) {
			a.fire();
		}
	}

	@Override
	public void backPropagate(double learningRate) {
		for (Output a : out) {
			a.backPropagate(learningRate);
		}
	}

	public void setTarget(double[] vals) {
		if (vals != null && vals.length == out.length) {
			for (int i = 0; i < out.length; i++) {
				out[i].target = vals[i];
			}
		}
	}

	@Override
	public Node[] getNodes() {
		return out;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < out.length; i++) {
			str += out[i].toString() + "\n";
		}
		return str;
	}

}
