package programs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import network.Network;

public class Colourize {

	public static void main(String[] args) {
		Network n = new Network(25, 75, 75, 2, 2.0);
		BufferedImage[] c = new BufferedImage[10];
		BufferedImage[] g = new BufferedImage[10];
		try {
			for (int i = 0; i < 10; i++) {
				c[i] = ImageIO.read(new File("networkPics/pic" + i + ".jpg"));
				g[i] = ImageIO.read(new File("networkPics/greypic" + i + ".jpg"));
			}
			int mod = 0;
			while (true) {
				for (int i = 0; i < 10; i++) {
					
				}
				n.save("colorNet" + mod);
				mod++;
				mod %= 2;
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
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
