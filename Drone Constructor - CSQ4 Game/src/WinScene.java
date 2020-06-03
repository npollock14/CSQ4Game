import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class WinScene extends Scene{
BufferedImage pic = Misc.loadImage("/winScreen.png");
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.drawImage(pic, 0, 0, null);
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void init() {
	}

}
