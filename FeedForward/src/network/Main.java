package network;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network n = new Network(1,2,2,1,2.5);
		System.out.println(n);
		n.save("file.txt");
		n = Network.load("file.txt");
		System.out.println(n);
	}

}
