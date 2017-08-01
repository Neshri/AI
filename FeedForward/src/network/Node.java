package network;

public interface Node {

	public int getID();

	public void connectToInput(Link l);

	public void connectToOutput(Link l);
	
	public int getNbrOutput();
	
	public String[] getOutputLinkWeights();

	public void fire();

	public void backPropagate(double learningRate);

}
