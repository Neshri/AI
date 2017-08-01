package network;

import java.util.ArrayList;

import math.CustomMath;

public class ConstantNode implements Node {

	private ArrayList<Link> links = new ArrayList<Link>();
	private int id;
	private double value = 1.0;

	public ConstantNode(int id) {
		this.id = id;
	}

	public void addLayer(Layer l, double randomRate) {
		Node[] n = l.getNodes();
		for (int i = 0; i < n.length; i++) {
			Link li = new Link(Link.createLinkID(id, n[i].getID()), CustomMath.nonZeroRandom(randomRate));
			li.value = 1.0;
			connectToOutput(li);
			n[i].connectToInput(li);

		}
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void connectToInput(Link l) {
		// No
	}

	@Override
	public void connectToOutput(Link l) {
		l.value = 1.0;
		links.add(l);
	}

	@Override
	public int getNbrOutput() {
		return links.size();
	}

	@Override
	public String[] getOutputLinkWeights() {
		String[] send = new String[links.size()];
		for (int i = 0; i < send.length; i++) {
			send[i] = links.get(i).getID() + " " + links.get(i).weight;
		}
		return send;
	}

	@Override
	public void fire() {
		for (Link a : links) {
			a.value = value;
		}
	}

	@Override
	public void backPropagate(double learningRate) {
		// Do nothing
	}

	@Override
	public String toString() {
		String str = "Constant: ";
		for (Link a : links) {
			str += a.toString();
		}
		return str;
	}

}
