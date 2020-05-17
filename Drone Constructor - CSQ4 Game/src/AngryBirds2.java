import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Robot;

public class AngryBirds2 extends Scene {
	Button b;
	Button launch;
	Robot r;
	double g = .15;
	int size = 50;
	boolean locked = false;
	boolean aiming = false;
	Point launchStartPos = new Point(250, 700);
	Vec2 vel;
	Rect obstacle = new Rect(1250, 560, 30, 200);

	@Override
	public void draw(Graphics g) {
		b.draw(g);
		launch.draw(g);
		Driver.drawUI(g);
		Misc.drawDashedLine(g, Driver.screenWidth / 2, 0, Driver.screenWidth / 2, Driver.screenHeight);
		
		obstacle.draw(g);

		if (launch.pressing && !locked) {
			aiming = true;
			launchStartPos.drawLine(g,
					new Point(launch.bounds.pos.x + launch.bounds.w / 2, launch.bounds.pos.y + launch.bounds.h / 2));
		}
		if (!launch.pressing && aiming && !locked && !InputManager.keysToggled[32]) {
			locked = true;
			vel = new Vec2((launchStartPos.x - (launch.bounds.pos.x + launch.bounds.w / 2)) / 15,
					(launchStartPos.y - (launch.bounds.pos.y + launch.bounds.h / 2)) / 15);
		}
		// new Vec2((launchStartPos.x - launch.bounds.pos.x + launch.bounds.w / 2)/20,
		// (launchStartPos.y - launch.bounds.pos.y + launch.bounds.h / 2)/20).print();

	}

	@Override
	public void update() {
		Driver.frame.setLocation(0, 0); // MouseInfo.getPointerInfo().
		b.update();
		launch.update();
		if (InputManager.mPos.x > Driver.screenWidth / 2 && !locked) {
			r.mouseMove(Driver.screenWidth / 2, MouseInfo.getPointerInfo().getLocation().y);
		}
		if (locked) {
			r.mouseMove((int) (launch.bounds.pos.x + launch.bounds.w / 2),
					(int) (launch.bounds.pos.y + launch.bounds.h / 2));
			launch.bounds.pos.add(vel);
			vel.y += g;
		}
		
		if(locked) {
		if(launch.bounds.pos.x + size >= Driver.screenWidth) {
			vel.x *=-.75;
			launch.bounds.pos.x = Driver.screenWidth - size-1;
		}
		if(launch.bounds.pos.x <= 0) {
			vel.x *=-.75;
			launch.bounds.pos.x = 1;
		}
		if(launch.bounds.pos.y + size >= Driver.screenHeight) {
			vel.y *=-.75;
			launch.bounds.pos.y = Driver.screenHeight - size-1;
		}
		if(launch.bounds.pos.y <= 0) {
			vel.y *=-.75;
			launch.bounds.pos.y = 1;
		}
		}

		if (launch.pressing && !locked) {
			launch.bounds.pos.x = InputManager.mPos.x - launch.bounds.w / 2;
			launch.bounds.pos.y = InputManager.mPos.y - launch.bounds.h / 2;
		}

		if (InputManager.keys[82]) {
			g = .15;
			locked = false;
			aiming = false;
			launchStartPos = new Point(250, 700);
			vel = new Vec2(0,0);
			init();
		}
		if(b.clicked) {
			Driver.nextLevel();
		}
		
		
		if(launch.bounds.intersects(obstacle)) {
			int colType = launch.bounds.classifyCol(obstacle);
			launch.bounds.unIntersect(obstacle, colType);
			if(colType == 2 || colType == 4) {
				vel.x *= -.75;
			}else {
				vel.y *= -.75;
			}
			
		}
		if(InputManager.mouseReleased[1] && !launch.clicked) {
			Driver.loseLife();
		}
		if(InputManager.mouseReleased[1] && launch.clicked && !b.clicked && locked) {
			Driver.loseLife();
		}

	}

	@Override
	public void init() {
		b = new Button(new Rect(1500, 700, 150, 150), Color.green, true);
		launch = new Button(new Rect(launchStartPos.x - size / 2, launchStartPos.y - size / 2, size, size), Color.LIGHT_GRAY,
				true);
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
