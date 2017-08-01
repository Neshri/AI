package programs;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sepia {

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			try {
				BufferedImage img = ImageIO.read(new File("networkPics/pic" + i + ".jpg"));
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						int g = img.getRGB(x, y);
						Color c = new Color(g);
						g = c.getBlue();
						if (c.getGreen() > g)
							g = c.getGreen();
						if (c.getRed() > g)
							g = c.getRed();
						c = new Color(g, g, g);
						img.setRGB(x, y, c.getRGB());
					}
				}
				File outputfile = new File("networkPics/greypic" + i + ".jpg");
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
