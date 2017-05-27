package network;

public interface Node {
	public String getName();
	public long getID();
	public void connectToInput(Link l);
	public void connectToOutput(Link l);
}
