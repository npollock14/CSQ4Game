import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MainMenuScene extends Scene{
BufferedImage texture = Misc.loadImage("/menuScene.png");
Button start;
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(texture, 0, 0, null);
		//start.draw(g);
	}

	@Override
	public void update() {
		start.update();
		
		if(start.clicked) {
			this.setActive(false);
			SceneManager.ms.setActive(true);
		}
		
		if(InputManager.mouse[2]) {
			System.out.println(InputManager.mPos.toString());
		}
	}

	@Override
	public void init() {
		start = new Button(new Rect(671,462, 578,194), null, 0, null, Misc.arialBig, Color.WHITE, false, false);
	}

}
