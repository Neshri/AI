package network;

import java.util.ArrayList;

import math.Function;
import math.Sigmoid;

public class Perceptron implements Node {
	private ArrayList<Link> in, out;
	private int ID;
	private Function f;

	public Perceptron(int ID, Function f) {
		in = new ArrayList<Link>();
		out = new ArrayList<Link>();
		this.ID = ID;
		if (f != null)
			this.f = f;
		else
			this.f = new Sigmoid();
	}

	@Override
	public void connectToInput(Link l) {
		in.add(l);
	}

	@Override
	public void connectToOutput(Link l) {
		out.add(l);
	}

	@Override
	public void fire() {
		double sum = 0;
		for (Link a : in) {
			sum += a.weight * a.value;
		}
		// System.out.println(sum);
		f.fire(sum);
		for (Link a : out) {
			a.value = f.getResult();
		}
	}

	@Override
	public void backPropagate(double learningRate) {
		// implement
		double wMult = f.getGradient();
		wMult *= -learningRate;
		double sum = 0;
		for (Link a : out) {
			sum += a.gradient * a.weight;
		}
		wMult *= sum;
		double grad = f.getGradient() * sum;
		for (Link a : in) {
			a.weight += a.value * wMult;
			// update gradient
			a.gradient = grad;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		String str = "Perceptron:" + ID;
		for (Link a : out) {
			str += "\n " + a;
		}

		return str;
	}

	@Override
	public int getNbrOutput() {
		return out.size();
	}

	@Override
	public String[] getOutputLinkWeights() {
		String[] send = new String[out.size()];
		for (int i = 0; i < send.length; i++) {
			send[i] = out.get(i).getID() + " " + out.get(i).weight;
		}
		return send;
	}

}
