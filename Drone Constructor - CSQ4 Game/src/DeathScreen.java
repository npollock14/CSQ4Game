import java.awt.Graphics;

public class DeathScreen extends Scene{
long startTime;
	@Override
	public void draw(Graphics g) {
		g.setFont(Misc.fancyTitleFont);
		g.drawString("U DIED", 600, 200);
	}

	@Override
	public void update() {
		if(System.currentTimeMillis() - startTime > 2500) {
			this.setActive(false);
			SceneManager.ms.init();
			SceneManager.ms.setActive(true);
		}
	}

	@Override
	public void init() {
		startTime = System.currentTimeMillis();
	}

}
