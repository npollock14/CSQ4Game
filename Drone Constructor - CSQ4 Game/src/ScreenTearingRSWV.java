import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.ArrayList;
import javax.swing.border.StrokeBorder;

public class ScreenTearingRSWV extends Scene {
	Button b;
	Robot r;
	boolean locked = false;
	Player p;
	double g = .15;
	ArrayList<Rect> obstacles = new ArrayList<Rect>();
	Button tear;
	boolean tearing = false;

	boolean tore = true;

	@Override
	public void draw(Graphics g) {

		Driver.drawUI(g);
		Misc.drawDashedLine(g, Driver.screenWidth / 2, 60 - 26, Driver.screenWidth / 2, Driver.screenHeight);
		Misc.drawDashedLine(g, Driver.screenWidth / 2, 60 - 26, Driver.screenWidth, 60 - 26);
		p.draw(g);
		for (Rect r : obstacles) {
			r.draw(g);
		}
		b.draw(g);
	}

	@Override
	public void update() {
		try {
		this.locked = SceneManager.stl.locked;
		this.running = SceneManager.stl.running;

		this.p.running = SceneManager.stl.p.running;
		if (p.bounds.pos.x < Driver.screenWidth / 2) {
			this.p.bounds.pos.y = SceneManager.stl.p.bounds.pos.y - SceneManager.stl.rSide.getLocationOnScreen().y + 25;
			this.p.vel.y = SceneManager.stl.p.vel.y;
		}
		b.update(SceneManager.stl.rSide.mouseReleased, SceneManager.stl.rSide.mouse, SceneManager.stl.rSide.mPos,
				Driver.screenWidth / 2, 0);
		
		if (b.clicked) {
			SceneManager.stl.rSide.jframe.dispose();
			Driver.frame.setBounds(0, 0, Driver.screenWidth, Driver.screenHeight);
			Driver.frame.setAlwaysOnTop(false);
			Driver.nextLevel();
		}
		if (MouseInfo.getPointerInfo().getLocation().x > 1920 / 2 && !locked
				&& MouseInfo.getPointerInfo().getLocation().y - SceneManager.stl.rSide.getLocationOnScreen().y > 40) {
			r.mouseMove(Driver.screenWidth / 2, MouseInfo.getPointerInfo().getLocation().y);
		}
		if (InputManager.keys[32]) {
			locked = true;
			p.running = true;
		}
		p.update(obstacles, g);
		}catch(Exception e) {
		}

	}

	@Override
	public void init() {
		tear = new Button(new Rect(Driver.screenWidth / 2 - 10, 10, 20, 40), null, 0, null, Misc.f, Color.black, true,
				false);
		b = new Button(new Rect(1300, 350, 150, 150), Color.green, true);
		obstacles.add(new Rect(Driver.screenWidth / 2 + 5, 500, 600, 340));
		obstacles.add(new Rect(0, Driver.screenHeight - 200, Driver.screenWidth, 50));
		obstacles.add(new Rect(1465, 300, 100, 200));
		p = new Player(new Rect(100 - 45, 600, 40, 140), new Vec2(0, 0), false);
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
