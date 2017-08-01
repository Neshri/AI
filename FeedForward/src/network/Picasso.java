package network;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picasso {

	public static void main(String[] args) {
		try {
			trainNet();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public static void trainNet() throws IOException {
		Network n = new Network(9, 3, 11, 5, 5.0);
		BufferedImage[] trainImages = new BufferedImage[10];
		for (int i = 0; i < 10; i++) {
			trainImages[i] = ImageIO.read(new File("networkPics/pic" + i + ".jpg"));
		}
		
		System.out.println("pics loaded!");
		
	}

	public static double[] separateColor(int c) {
		double[] send = new double[3];
		send[2] = c & 0xFF;
		send[2] /= 255.0;
		c >>= 8;
		send[1] = c & 0xFF;
		send[1] /= 255.0;
		c >>= 8;
		send[0] = c & 0xFF;
		send[0] /= 255.0;
		return send;
	}

}
