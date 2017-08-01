package network;

public class Link {

	public double weight, value;
	public double gradient = 0.0;
	private long id;

	public Link(long id) {
		this.id = id;
		weight = 1.0;
	}
	
	public Link(long id, double weight) {
		this.id = id;
		this.weight = weight;
	}

	public long getID() {
		return id;
	}
	
	public static long createLinkID(int nodeA, int nodeB) {
		long send = nodeA;
		send = send << 32;
		send += nodeB;
		return send;
	}
	
	public static int[] getNodeIDs(long id) {
		int b = (int)id;
		int a = (int)(id >> 32);
		int[] send = {a, b};
		return send;
	}

	@Override
	public String toString() {
		String str = "Link:" + id + " w=" + Network.FORMAT.format(weight) + "v=" + Network.FORMAT.format(value);
		return str;
	}
}
