package isaac;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import network.IDFactory;
import programs.ScreenReader;

public class EnemyTracker {

	private static class Shape {

	}

	private ScreenReader sr;

	public EnemyTracker(ScreenReader sr, String networkFilePath) {
		this.sr = sr;

	}

	public static int[] getShapes(BufferedImage img, int thresh) {
		IDFactory fact = new IDFactory();
		int[] board = new int[img.getHeight() * img.getWidth()];
		for (int y = 1; y < img.getHeight() - 1; y++) {
			for (int x = 1; x < img.getWidth() - 1; x++) {
				int color = img.getRGB(x, y);
				int diff = getColorDiff(color, img.getRGB(x - 1, y - 1));
				int[] p = { x - 1, y - 1 };
				int tmpDiff = getColorDiff(color, img.getRGB(x, y - 1));
				if (tmpDiff < diff) {
					diff = tmpDiff;
					p[0] = x;
					p[1] = y - 1;
				}
				tmpDiff = getColorDiff(color, img.getRGB(x + 1, y - 1));
				if (tmpDiff < diff) {
					diff = tmpDiff;
					p[0] = x + 1;
					p[1] = y - 1;
				}
				tmpDiff = getColorDiff(color, img.getRGB(x - 1, y));
				if (tmpDiff < diff) {
					diff = tmpDiff;
					p[0] = x - 1;
					p[1] = y;
				}
				// diff += getColorDiff(color, img.getRGB(x + 1, y));
				// diff += getColorDiff(color, img.getRGB(x - 1, y + 1));
				// diff += getColorDiff(color, img.getRGB(x, y + 1));
				// diff += getColorDiff(color, img.getRGB(x + 1, y + 1));
				if (diff < thresh) {
					board[(img.getWidth() - 1) * y + x] = board[(img.getWidth()) * (p[1]) + p[0]];

				} else {
					board[(img.getWidth() - 1) * y + x] = fact.getID();
				}
				// System.out.println(board[(img.getWidth() - 1) * y + x]);
			}
		}

		return board;
	}

	public static int getColorDiff(int a, int b) {
		int tmp = (a & 0xFF) - (b & 0xFF);
		int diff = tmp * tmp;
		tmp = ((a & 0xFF00) - (b & 0xFF00)) >> 8;
		diff += tmp * tmp;
		tmp = ((a & 0xFF0000) - (b & 0xFF0000)) >> 16;
		diff += tmp * tmp;
		return diff;
	}

	public static void main(String[] args) {
		try {
			
			BufferedImage img = ImageIO.read(new File("networkPics/pic" + 10 + ".png"));
			long time = System.currentTimeMillis();
			int thresh = 2;
			int[] list = getShapes(img, thresh * thresh);
			System.out.println(System.currentTimeMillis()-time+"ms");
			for (int i = 0; i < list.length; i++) {
				if (list[i] == 0)
					list[i] = 150;
				img.setRGB(i % img.getWidth(), i / img.getWidth(), 0xFF000000 + 0x00FFFFFF / (list[i]*25));
			}
			File outputfile = new File("superImage.png");
			ImageIO.write(img, "png", outputfile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
