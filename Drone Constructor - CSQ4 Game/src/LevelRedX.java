import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class LevelRedX extends Scene {
	boolean xHit = false;
	DrawableText[] letters = new DrawableText[40];

	@Override
	public void draw(Graphics g) {
		Driver.drawUI(g);
		g.setFont(Misc.fBig);
		g.drawString("Press the smallest 'x'", 500, 150);
for(DrawableText d : letters) {
			d.draw(g);
		}
	}

	@Override
	public void update() {
		if (xHit) {
			Driver.nextLevel();
			Driver.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		if (InputManager.mouseReleased[1]) {
			Driver.loseLife();
		}
		

	}

	@Override
	public void init() {
		Driver.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Driver.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				xHit = true;
			}
		});

		for (int i = 0; i < letters.length; i++) {
			letters[i] = new DrawableText(new Font((Math.random() > .5 ? "Press Start" : "TimesRoman"), 1, (int) ((Math.random() * 100) + 45)), "x",
					new Point(Misc.rBt(200, Driver.screenWidth-400),
							Misc.rBt(250, Driver.screenHeight-100)));
		}

	}

}

class DrawableText {
	Font f;
	String text;
	Point pos;

	public DrawableText(Font f, String text, Point pos) {
		super();
		this.f = f;
		this.text = text;
		this.pos = pos;
	}

	public void draw(Graphics g) {
		g.setFont(f);
		g.drawString(text, (int) pos.x, (int) pos.y);
	}

}
