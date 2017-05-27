package network;

public class IDFactory {

	private long idCount;
	
	public IDFactory() {
		idCount = 1;
	}
	
	public synchronized long getID() {
		long send = idCount;
		idCount++;
		return send;
	}
	
}
