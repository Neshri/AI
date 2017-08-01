package network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

import filehandling.TrainParser;
import filehandling.TrainParser.TrainSet;
import math.CustomMath;
import network.Inputs.Input;
import network.Outputs.Output;

public class Network {

	private static class SaveHandler {
		public static boolean save(Inputs in, Outputs out, PerceptronLayer[] layers, ConstantNode constant,
				String filePath) {
			try {
				File file = new File(filePath);
				if (!file.exists()) {
					file.createNewFile();
				}
				int nbrLinks = 0;
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				Input[] inArr = (Input[]) in.getNodes();
				bw.write(inArr.length + " ");
				bw.write(layers.length + " ");
				Output[] outArr = (Output[]) out.getNodes();
				bw.write(outArr.length + " ");
				bw.newLine();
				for (Input a : inArr) {
					bw.write(a.getID() + " ");
					nbrLinks += a.getNbrOutput();
				}
				bw.newLine();
				for (PerceptronLayer a : layers) {
					Node[] nodes = a.getNodes();
					bw.write(nodes.length + "");
					bw.newLine();
					for (Node b : nodes) {
						bw.write(b.getID() + " ");
						nbrLinks += b.getNbrOutput();
					}
					bw.newLine();
				}
				for (Output a : outArr) {
					bw.write(a.getID() + " ");
				}
				bw.newLine();
				// print links
				nbrLinks += constant.getNbrOutput();
				bw.write(nbrLinks + "");
				bw.newLine();
				for (Input a : inArr) {
					String[] str = a.getOutputLinkWeights();
					for (String b : str) {
						bw.write(b + " ");
					}
				}
				for (PerceptronLayer a : layers) {
					Node[] nodes = a.getNodes();
					for (Node b : nodes) {
						String[] str = b.getOutputLinkWeights();
						for (String c : str) {
							bw.write(c + " ");
						}
					}
				}
				String[] constVals = constant.getOutputLinkWeights();
				for (String c : constVals) {
					bw.write(c + " ");
				}
				bw.newLine();

				bw.write("End of save");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		}

		public static Network load(String filePath) {
			try {
				Scanner sc = new Scanner(new File(filePath));
				int in = sc.nextInt();
				int hidden = sc.nextInt();
				int out = sc.nextInt();
				int[] inIDs = new int[in];
				for (int i = 0; i < in; i++) {
					inIDs[i] = sc.nextInt();
				}
				int[][] hiddenIDs = new int[hidden][];
				for (int i = 0; i < hidden; i++) {
					hiddenIDs[i] = new int[sc.nextInt()];
					for (int j = 0; j < hiddenIDs[i].length; j++) {
						hiddenIDs[i][j] = sc.nextInt();
					}
				}
				int[] outIDs = new int[out];
				for (int i = 0; i < out; i++) {
					outIDs[i] = sc.nextInt();
				}
				int nbrLinks = sc.nextInt();
				Link[] links = new Link[nbrLinks];
				for (int i = 0; i < links.length; i++) {
					links[i] = new Link(sc.nextLong(), sc.nextDouble());
				}
				sc.close();
				return new Network(inIDs, hiddenIDs, outIDs, links);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	public static final DecimalFormat FORMAT = new DecimalFormat("#.###");

	private Inputs in;
	private Outputs out;
	private PerceptronLayer[] layers;
	private ConstantNode constant;
	// private IDFactory idFact;
	public int generation = 0;

	public Network(int[] input, int[][] hiddenLayers, int[] output, Link[] links) {
		constant = new ConstantNode(1);
		HashMap<Integer, Node> map = new HashMap<Integer, Node>();
		map.put(constant.getID(), constant);
		in = new Inputs(input);
		Node[] tmp = in.getNodes();
		for (int i = 0; i < tmp.length; i++) {
			map.put(tmp[i].getID(), tmp[i]);
		}
		out = new Outputs(output);
		tmp = out.getNodes();
		for (int i = 0; i < tmp.length; i++) {
			map.put(tmp[i].getID(), tmp[i]);
		}
		layers = new PerceptronLayer[hiddenLayers.length];
		for (int i = 0; i < hiddenLayers.length; i++) {
			layers[i] = new PerceptronLayer(hiddenLayers[i]);
			tmp = layers[i].getNodes();
			for (int j = 0; j < tmp.length; j++) {
				map.put(tmp[j].getID(), tmp[j]);
			}
		}
		for (int i = 0; i < links.length; i++) {
			int[] IDs = Link.getNodeIDs(links[i].getID());
			map.get(IDs[0]).connectToOutput(links[i]);
			map.get(IDs[1]).connectToInput(links[i]);
		}
	}

	public Network(int input, int output, int layerWidth, int layers, double randomRate) {
		IDFactory idFact = new IDFactory();
		constant = new ConstantNode(idFact.getID());
		in = new Inputs(idFact, input);
		out = new Outputs(idFact, output);
		constant.addLayer(out, randomRate);
		this.layers = new PerceptronLayer[layers];
		if (layers > 0) {
			for (int i = 0; i < layers; i++) {
				this.layers[i] = new PerceptronLayer(idFact, layerWidth);
				constant.addLayer(this.layers[i], randomRate);
			}
			connectLayers(in, this.layers[0], 0);
			for (int i = 1; i < layers; i++) {
				connectLayers(this.layers[i - 1], this.layers[i], randomRate);
			}
			connectLayers(this.layers[layers - 1], out, randomRate);
		} else {
			connectLayers(in, out, randomRate);
		}
	}

	public void save(String filePath) {
		if (SaveHandler.save(in, out, layers, constant, filePath)) {
			System.out.println("Network successfully saved as: " + filePath);
		} else {
			System.out.println("Network save was unsuccessful");
		}
	}

	public static Network load(String filePath) {
		Network n = SaveHandler.load(filePath);
		if (n == null)
			System.out.println("Could not load: " + filePath);
		return n;
	}

	public void fire() {
		for (PerceptronLayer a : layers) {
			a.fire();
		}
		out.fire();
	}

	public void train(double[] in, double[] target, double learningRate) {
		Input[] networkIn = (Input[]) this.in.getNodes();
		Output[] networkOut = (Output[]) out.getNodes();
		if (in == null || target == null || in.length > networkIn.length || target.length > networkOut.length) {
			System.out.println("Invalid  training set.");
		} else {
			for (int i = 0; i < in.length; i++) {
				networkIn[i].setValue(in[i]);
			}
			fire();
			out.setTarget(target);
			out.backPropagate(learningRate);
			for (PerceptronLayer a : layers) {
				a.backPropagate(learningRate);
			}
		}
	}

	public double train(TrainSet[] sets, double learningRate) {
		double errorMargin = 0;
		for (TrainSet a : sets) {
			train(a.in, a.target, learningRate);
			Output[] b = getOutputs();
			double sum = 0;
			for (Output c : b) {
				sum += Math.abs(c.target - c.getValue());
			}
			sum /= (double) b.length;
			errorMargin += sum;
		}
		errorMargin /= (double) sets.length;
		return errorMargin;
	}

	private void connectLayers(Layer a, Layer b, double randomRate) {
		Node[] nA = a.getNodes();
		Node[] nB = b.getNodes();
		for (int i = 0; i < nA.length; i++) {
			for (int j = 0; j < nB.length; j++) {
				long id = Link.createLinkID((int) nA[i].getID(), (int) nB[j].getID());
				Link l = new Link(id, CustomMath.nonZeroRandom(randomRate));
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
		str += constant.toString() + "\n";
		for (PerceptronLayer a : layers) {
			str += a.toString() + "\n";
		}
		str += out.toString();
		return str;
	}

	public static void main(String[] args) {
		double accurracy = 0.05;
		String[] ans = new String[4];
		Network n = new Network(1, 1, 0, 0, 2);
		try {
			TrainSet[] sets = TrainParser.getSets("notTrainSet.txt");
			int count = 0;
			double marginError = 1.0;
			while (marginError > accurracy) {
				marginError = n.train(sets, 0.05);
				count++;
			}
			ans[0] = "NOT\n" + n.toString() + "\n" + count + "\n";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		n = new Network(2, 1, 0, 0, 2);
		try {
			TrainSet[] sets = TrainParser.getSets("andTrainSet.txt");
			int count = 0;
			double marginError = 1.0;
			while (marginError > accurracy) {
				marginError = n.train(sets, 0.05);
				count++;
			}
			ans[1] = "AND\n" + n.toString() + "\n" + count + "\n";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		n = new Network(2, 1, 0, 0, 2);
		try {
			TrainSet[] sets = TrainParser.getSets("orTrainSet.txt");
			int count = 0;
			double marginError = 1.0;
			while (marginError > accurracy) {
				marginError = n.train(sets, 0.05);
				count++;
			}
			ans[2] = "OR\n" + n.toString() + "\n" + count + "\n";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		n = new Network(2, 1, 2, 1, 2);
		try {
			TrainSet[] sets = TrainParser.getSets("xorTrainSet.txt");
			int count = 0;
			double marginError = 1.0;
			while (marginError > accurracy) {
				marginError = n.train(sets, 0.05);
				count++;
			}
			ans[3] = "XOR\n" + n.toString() + "\n" + count + "\n";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String a : ans) {
			System.out.println(a);
		}
	}

}
