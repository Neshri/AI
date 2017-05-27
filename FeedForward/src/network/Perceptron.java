package network;

import java.util.ArrayList;

public class Perceptron implements Node {
	private ArrayList<Link> in, out;
	private long ID;
	private Function f;

	public Perceptron(long ID, Function f) {
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

	public void fire() {
		double sum = 0;
		for (Link a : in) {
			sum += a.weight * a.value;
		}
		//System.out.println(sum);
		f.fire(sum);
		for (Link a : out) {
			a.value = f.getResult();
		}
	}

	@Override
	public long getID() {
		return ID;
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	public String toString() {
		String str = "Perceptron:" + ID;
		for (Link a : out) {
			str += "\n " + a;
		}

		return str;
	}

}
