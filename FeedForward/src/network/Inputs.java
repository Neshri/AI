package network;

import java.util.ArrayList;

public class Inputs implements Layer {

	public static class Input implements Node {

		private long ID;
		private double value;
		private ArrayList<Link> list = new ArrayList<Link>();

		public Input(long ID) {
			this.ID = ID;
		}
		
		public void setValue(double v) {
			value = v;
			for (Link a : list) {
				a.value = value;
			}
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
			return ""+Network.FORMAT.format(value);
		}

		@Override
		public long getID() {
			return ID;
		}
		
	}

	private Input[] in;

	public Inputs(IDFactory idFact, int n) {
		in = new Input[n];
		for (int i = 0; i < n; i++) {
			in[i] = new Input(idFact.getID());
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
			str += a.toString() + " ";
		}
		return str;
	}

}
