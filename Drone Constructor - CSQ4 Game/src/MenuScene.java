import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MenuScene extends Scene {
	Button start;
	BufferedImage b;
	int rMx, rMy;
	int arrSize = 0;
	int spacing = 35;

	@Override
	public void draw(Graphics g) {
		g.setFont(Misc.fBig);
		g.setColor(Color.BLACK);
		g.drawString("Welcome to the Game", Driver.screenWidth / 2 - 400, Driver.screenHeight / 3 - 100);
		g.setFont(Misc.smallTitleFont);
		g.drawString("Press The Button To Start", Driver.screenWidth / 2 - 200, Driver.screenHeight / 3);

		start.draw(g, 10, 100);
		if (start.clicked) {
			Driver.gameTimer = System.currentTimeMillis();
			Driver.nextLevel();

			g.drawString("CLICK", Driver.screenWidth / 2, Driver.screenHeight / 2 + 500);
		}

		for (int i = 0; i < arrSize; i++) {
			for (int y = 0; y < arrSize; y++) {
				if (i == rMx && y == rMy)
					continue;
				g.drawImage(b, (int) InputManager.mPos.x + spacing * i - spacing * rMx,
						(int) InputManager.mPos.y + spacing * y - spacing * rMy, 17, 23, null);
			}
		}

	}

	@Override
	public void update() {
		start.update();
		if (InputManager.keysReleased[32]) {
			Driver.changeInvis.add(!Driver.invisCursor);
		}

	}

	@Override
	public void init() {

		rMx = (int) (Math.random() * arrSize + 1);
		rMy = (int) (Math.random() * arrSize + 1);
		b = Misc.loadImage("/mouse2.png");
		start = new Button(new Rect(Driver.screenWidth / 2 - 100, Driver.screenHeight / 2 - 100, 200, 200), null, 0, "",
				Driver.fb, Color.GREEN, true, true);
		start.glowColor = new Color(0,102,0);
		// start = new Button(new Rect(900 - 150, 500-150, 300,300), Color.red, true);

	}

}
