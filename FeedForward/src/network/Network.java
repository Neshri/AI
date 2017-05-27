package network;

import java.text.DecimalFormat;

import network.Inputs.Input;
import network.Outputs.Output;

public class Network {

	// input i to neuron j(has function g) weight is
	// w = a(target-actual)*g'(sum)*x_i

	public static final DecimalFormat FORMAT = new DecimalFormat("#.###");

	private Inputs in;
	private Outputs out;
	private PerceptronLayer[] layers;
	private IDFactory idFact;

	public Network(String[] input, String[] output, int layerWidth, int layers) {
		idFact = new IDFactory();
		in = new Inputs(idFact, input);
		out = new Outputs(idFact, output);
		this.layers = new PerceptronLayer[layers];
		for (int i = 0; i < layers; i++) {
			this.layers[i] = new PerceptronLayer(idFact, layerWidth);
		}
		connectLayers(in, this.layers[0]);
		for (int i = 1; i < layers; i++) {
			connectLayers(this.layers[i - 1], this.layers[i]);
		}
		connectLayers(this.layers[layers - 1], out);
	}

	public void fire() {
		for (PerceptronLayer a : layers) {
			a.fire();
		}
		out.fire();
	}
	
	public void train(double[] in, double[] target) {
		if (in.length != this.in.getNodes().length || out.getNodes().length != target.length) {
			System.out.println("Invalid  training set.");
		} else {
			
		}
	}

	private void connectLayers(Layer a, Layer b) {
		Node[] nA = a.getNodes();
		Node[] nB = b.getNodes();
		for (int i = 0; i < nA.length; i++) {
			for (int j = 0; j < nB.length; j++) {
				long id = nA[i].getID();
				id = id << 32;
				id += nB[j].getID();
				Link l = new Link(id);
				nA[i].connectToOutput(l);
				nB[j].connectToInput(l);
			}
		}
	}

	public Input[] getInputs() {
		return (Input[]) in.getNodes();
	}

	public Output[] getOutputs() {
		return (Output[]) out.getNodes();
	}

	@Override
	public String toString() {
		String str = in.toString() + "\n";
		for (PerceptronLayer a : layers) {
			str += a.toString() + "\n";
		}
		str += out.toString();
		return str;
	}

	public static void main(String[] args) {
		Network n = new Network(null, null, 2, 2);
		Input[] ins = n.getInputs();
		Output[] outs = n.getOutputs();
		for (int i = 0; i < 100; i++) {
			System.out.println(n.toString() + "\n\n");
			for (Input a : ins) {
				a.setValue(i);
			}
			n.fire();
		}
	}

}
