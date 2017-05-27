package network;

import java.util.ArrayList;

public class Inputs implements Layer {

	public static class Input implements Node {

		private long ID;
		private String name;
		private double value;
		private ArrayList<Link> list = new ArrayList<Link>();

		public Input(long ID, String name) {
			this.ID = ID;
			this.name = name;
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
		
		@Override
		public String getName() {
			return name;
		}
		
	}

	private Input[] in;

	public Inputs(IDFactory idFact, String[] name) {
		in = new Input[name.length];
		for (int i = 0; i < name.length; i++) {
			in[i] = new Input(idFact.getID(), name[i]);
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
