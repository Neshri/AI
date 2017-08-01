package programs;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class ScreenReader {

	private class TargetFrame extends JFrame {

		private static final long serialVersionUID = -5349117148934929975L;

		public TargetFrame(Rectangle rect) {

			setBackground(new Color(0, 0, 0, 0));
			setSize(rect.width, rect.height);
			setLocationRelativeTo(null);
			setLocation(rect.x, rect.y);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new GridBagLayout());
			setVisible(true);
		}

	}

	private Robot robot;
	private BufferedImage img;
	private TargetFrame target;

	public ScreenReader(Rectangle rect) throws AWTException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		robot = new Robot();
		img = robot.createScreenCapture(rect);

		target = new TargetFrame(rect);
	}

	public BufferedImage getImage() {
		img = robot.createScreenCapture(new Rectangle(target.getLocation().x, target.getLocation().y + 50,
				target.getWidth(), target.getHeight() - 50));
		return img;
	}

	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ScreenReader sr = new ScreenReader(new Rectangle(50, 50, 975, 575));

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
