package network;

import java.util.ArrayList;

public class Outputs implements Layer {

	public static class Output implements Node {

		public double target;
		private double value;
		private ArrayList<Link> in = new ArrayList<Link>();
		private long ID;
		private String name;
		private Function activation;

		public Output(long ID, String name, Function act) {
			this.ID = ID;
			this.name = name;
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

		@Override
		public void connectToInput(Link l) {
			in.add(l);
		}

		@Override
		public void connectToOutput(Link l) {
		}

		@Override
		public String toString() {
			String str = "t" + Network.FORMAT.format(target) + ":v" + Network.FORMAT.format(value);
			return str;
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

	private Output[] out;

	public Outputs(IDFactory idFact, String[] names) {
		out = new Output[names.length];
		for (int i = 0; i < names.length; i++) {
			out[i] = new Output(idFact.getID(), names[i], new Sigmoid());
		}
	}

	public void fire() {
		for (Output a : out) {
			a.fire();
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
			str += out[i].toString() + " ";
		}
		return str;
	}

}
