package network;

import java.util.ArrayList;

public class Inputs implements Layer {

	public static class Input implements Node {

		private int ID;
		private double value;
		private ArrayList<Link> list = new ArrayList<Link>();

		public Input(int ID) {
			this.ID = ID;
		}

		public void setValue(double v) {
			value = v;
			fire();
		}

		@Override
		public void connectToInput(Link l) {
		}

		@Override
		public void connectToOutput(Link l) {
			list.add(l);
		}

		@Override
		public String toString() {
			String str = "Input:" + ID + " v" + Network.FORMAT.format(value) + "\n";
			for (Link a : list) {
				str += a.toString() + "\n";
			}
			return str;
		}

		@Override
		public int getID() {
			return ID;
		}

		@Override
		public void fire() {
			for (Link a : list) {
				a.value = value;
			}
		}

		@Override
		public void backPropagate(double learningRate) {
			// Do nothing
		}

		@Override
		public int getNbrOutput() {
			return list.size();
		}

		@Override
		public String[] getOutputLinkWeights() {
			String[] send = new String[list.size()];
			for (int i = 0; i < send.length; i++) {
				send[i] = list.get(i).getID() + " " + list.get(i).weight;
			}
			return send;
		}

	}

	private Input[] in;

	public Inputs(IDFactory idFact, int n) {
		in = new Input[n];
		for (int i = 0; i < n; i++) {
			in[i] = new Input(idFact.getID());
		}
	}

	public Inputs(int[] IDs) {
		in = new Input[IDs.length];
		for (int i = 0; i < IDs.length; i++) {
			in[i] = new Input(IDs[i]);
		}
	}

	@Override
	public Node[] getNodes() {
		return in;
	}

	@Override
	public String toString() {
		String str = "";
		for (Input a : in) {
			str += a.toString() + "\n";
		}
		return str;
	}

	@Override
	public void fire() {
		for (Input a : in) {
			a.fire();
		}
	}

	@Override
	public void backPropagate(double learningRate) {
		// Do nothing
	}

}
