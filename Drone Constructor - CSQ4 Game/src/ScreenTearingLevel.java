import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.ArrayList;
import javax.swing.border.StrokeBorder;

public class ScreenTearingLevel extends Scene {
	Button b;
	Robot r;
	boolean locked = false;
	Player p;
	double g = .15;
	ArrayList<Rect> obstacles = new ArrayList<Rect>();
	Button tear;
	boolean tearing = false;

	boolean draggingWindow = false;
	Point startDrag;
	int mX;

	Window rSide;

	boolean tore = false;

	double lastTime = 0;
	Point oldPos = new Point(Driver.screenWidth / 2, 0);

	@Override
	public void draw(Graphics g) {

		g.setFont(Misc.titleFont);
		g.drawString("Press green button", 100, 150);
		g.drawString("[Space] to move jimbo", 100, 250);
		g.drawString("[r] to restart", 100, 350);

		if (!tore) {
			tear.draw(g);

		}
		Misc.drawDashedLine(g, Driver.screenWidth / 2, 60 - 26, Driver.screenWidth / 2, Driver.screenHeight);
		Misc.drawDashedLine(g, Driver.screenWidth / 2, 60 - 26, Driver.screenWidth, 60 - 26);

		Driver.drawUI(g);

		p.draw(g);
		for (Rect r : obstacles) {
			r.draw(g);
		}
		b.draw(g);
	}

	@Override
	public void update() {
		Driver.frame.setLocation(0, 0);
		Driver.frame.setState(Frame.NORMAL);
		if (tore)
			rSide.jframe.setState(Frame.NORMAL);

		if (tore && System.currentTimeMillis() - lastTime > 500
				&& oldPos.distanceTo(new Point(rSide.jframe.getLocation().x, rSide.jframe.getLocation().y)) != 0) {
			draggingWindow = true;
			mX = MouseInfo.getPointerInfo().getLocation().x;
			lastTime = System.currentTimeMillis();
			oldPos = new Point(rSide.jframe.getLocation().x, rSide.jframe.getLocation().y);
		} else if (tore && System.currentTimeMillis() - lastTime > 500
				&& oldPos.distanceTo(new Point(rSide.jframe.getLocation().x, rSide.jframe.getLocation().y)) == 0) {
			draggingWindow = false;
			lastTime = System.currentTimeMillis();
		}

		if (draggingWindow) {
			r.mouseMove(mX, MouseInfo.getPointerInfo().getLocation().y);
		}

		if (tore)
			rSide.jframe.setLocation(Driver.screenWidth / 2, rSide.jframe.getLocation().y);
		if (!tore && InputManager.mPos.x > Driver.screenWidth / 2 && !locked
				&& MouseInfo.getPointerInfo().getLocation().y - Driver.frame.getLocationOnScreen().y > 60) {
			r.mouseMove(Driver.screenWidth / 2, MouseInfo.getPointerInfo().getLocation().y);
		}

		if (InputManager.keys[32]) {
			locked = true;
			p.running = true;
		}
		if (tore && rSide.keys[82])
			InputManager.keys[82] = true;
		if (InputManager.keys[82]) {
			// restart
			if (tore)
				rSide.jframe.dispose();
			Driver.frame.setBounds(0, 0, Driver.screenWidth, Driver.screenHeight);
			locked = false;
			g = .15;
			obstacles = new ArrayList<Rect>();
			tearing = false;
			draggingWindow = false;
			tore = false;
			lastTime = 0;
			oldPos = new Point(Driver.screenWidth / 2, 0);

			init();
		}

		if (locked) {
			if (p.bounds.pos.x < Driver.screenWidth / 2) {// MouseInfo.getPointerInfo().getLocation().x <
															// Driver.screenWidth / 2) {
				r.mouseMove((int) (p.bounds.pos.x + p.bounds.w / 2), (int) (p.bounds.pos.y + 26 + p.bounds.h / 2));
			} else if (tore) {
				r.mouseMove((int) (rSide.rw.p.bounds.pos.x + rSide.rw.p.bounds.w / 2),
						(int) (rSide.rw.p.bounds.pos.y + 26 + rSide.rw.p.bounds.h / 2 + rSide.jframe.getLocation().y));
			} else {
				r.mouseMove((int) (p.bounds.pos.x + p.bounds.w / 2), (int) (p.bounds.pos.y + 26 + p.bounds.h / 2));
			}
		}

		if (tore) {
			if (rSide.keys[32]) {
				locked = true;
				p.running = true;
			}
		}
		p.update(obstacles, g);
		b.update();

		tear.update();
		if (tear.pressing) {
			tearing = true;
		} else if (InputManager.mouseReleased[1]) {
			tearing = false;
		}
		if (tearing) {
			tear.bounds.pos.y = InputManager.mPos.y;
		}

		if (tear.bounds.pos.y > Driver.screenHeight - 200 && !tore) {
			tore = true;
			rSide = new Window(Driver.screenWidth / 2, Driver.screenHeight, Driver.screenWidth / 2, 0);
			rSide.jframe.setAlwaysOnTop(true);
			Driver.frame.setBounds(0, 0, Driver.screenWidth / 2, Driver.screenHeight);
		}

		if (tore) {
			obstacles.get(0).pos.y = 500 + rSide.getLocationOnScreen().y - 25;
			obstacles.get(2).pos.y = 300 + rSide.getLocationOnScreen().y - 25;
		}

	}

	@Override
	public void init() {
		Driver.frame.setAlwaysOnTop(true);
		tear = new Button(new Rect(Driver.screenWidth / 2 - 10, 10, 20, 40), null, 0, null, Misc.f, Color.black, true,
				false);
		b = new Button(new Rect(1300, 350, 150, 150), Color.green, true);
		obstacles.add(new Rect(Driver.screenWidth / 2 + 5, 500, 600, 340));
		obstacles.add(new Rect(0, Driver.screenHeight - 200, Driver.screenWidth, 50));
		obstacles.add(new Rect(1465, 300, 100, 200));
		p = new Player(new Rect(100, 670, 40, 140), new Vec2(0, 0), false);
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Player {
	Rect bounds;
	Vec2 vel;
	boolean running = false;

	public Player(Rect bounds, Vec2 vel, boolean running) {
		this.bounds = bounds;
		this.vel = vel;
		this.running = running;
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.w),
				(int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.h - bounds.w));
		g.drawLine((int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.h - bounds.w),
				(int) (bounds.pos.x + bounds.w), (int) (bounds.pos.y + bounds.h));
		g.drawLine((int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.h - bounds.w),
				(int) (bounds.pos.x), (int) (bounds.pos.y + bounds.h));

		g.drawLine((int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.h - 2 * bounds.w),
				(int) (bounds.pos.x + bounds.w), (int) (bounds.pos.y + bounds.h - 2 * bounds.w));
		g.drawLine((int) (bounds.pos.x + bounds.w / 2), (int) (bounds.pos.y + bounds.h - 2 * bounds.w),
				(int) (bounds.pos.x), (int) (bounds.pos.y + bounds.h - 2 * bounds.w));

		g.drawOval((int) (bounds.pos.x), (int) (bounds.pos.y), (int) bounds.w, (int) bounds.w);
		// bounds.draw(g);

	}

	
	
	public void update(ArrayList<Rect> obstacles, double g) {

		if (running) {
			if (vel.x < 5) {
				vel.x += .08;
			}
		}

		vel.y += g;

		bounds.pos.add(vel);

		for (Rect r : obstacles) {
			if (bounds.intersects(r)) {
				int colType = bounds.classifyCol(r);
				bounds.unIntersect(r, colType);
				if (colType == 1 || colType == 3) {
					vel.y = 0;
				} else {
					vel.x *= -.3;
				}

			}
		}
	}

}
