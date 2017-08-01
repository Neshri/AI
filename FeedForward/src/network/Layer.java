package network;

public interface Layer {
	public Node[] getNodes();
	public void fire();
	public void backPropagate(double learningRate);
}
