import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class HyperspaceScene extends Scene {
	BufferedImage jump = Misc.loadImage("/hyperspace.gif");
	int time = 0;
	int screenTime = 0;
	Image icon = new ImageIcon("res/hyperspace.gif").getImage();

	@Override
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)time/(float)screenTime));
		g.setColor(Color.black);
		g.fillRect(0,0,1920,1010);
		
		g.drawImage(icon, 500, 0, 1010, 1010, null);

	}

	@Override
	public void update() {
		time++;
		if (time > screenTime) {
			SceneManager.hs.setActive(false);
			SceneManager.sm.setActive(false);
			SceneManager.ms.setActive(true);
		}
	}

	@Override
	public void init() {
		time = 0;
		screenTime = 150;
		
	}

}
