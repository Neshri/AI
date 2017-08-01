package network;

public class IDFactory {

	private int idCount;
	
	public IDFactory() {
		idCount = 1;
	}
	
	public synchronized int getID() {
		int send = idCount;
		idCount++;
		return send;
	}
	
}
