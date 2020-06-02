import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HyperspaceScene extends Scene {
	BufferedImage jump = Misc.loadImage("/hyperspace.gif");
	int time = 0;
	int screenTime = 0;

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(jump, 0, 0, jump.getWidth(), jump.getHeight(), null);

	}

	@Override
	public void update() {
		time++;
		if (time > screenTime) {
			SceneManager.hs.setActive(false);
			SceneManager.bs.setActive(true);
		}
	}

	@Override
	public void init() {
		time = 0;
		screenTime = 100;
	}

}
