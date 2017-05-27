package network;

public class Link {

	public double weight, value;
	private long id;

	public Link(long id) {
		this.id = id;
		weight = 1.0;
	}

	public long getID() {
		return id;
	}

	@Override
	public String toString() {
		String str = "Link:" + id + " w=" + Network.FORMAT.format(weight) + "v=" + Network.FORMAT.format(value);
		return str;
	}
}
