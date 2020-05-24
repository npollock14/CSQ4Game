import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuScene extends Scene {
	PlayerShip p;
	EnemyShip e1;

	TestSector s = new TestSector();

	int num = 0;
	int num2 = 0;

	@Override
	public void draw(Graphics2D g) {
		g.setFont(Misc.f);
		g.drawString(p.shoot + " " + s.projectiles.size(), 100, 100);
		s.draw(g);
		g.setColor(Color.BLACK);
		Camera.toScreen(new Point(0, 0)).fillCircle(g, (int) (20 * Camera.scale));
	}

	@Override
	public void update() {

		Camera.changeScale(InputManager.scroll / 2);
		if (InputManager.keys[81])
			p.cmdRotate(true);
		if (InputManager.keys[69])
			p.cmdRotate(false);
		if (InputManager.keys[38])
			p.cmdMove(0);
		if (InputManager.keys[40])
			p.cmdMove(2);
		if (InputManager.keys[37])
			p.cmdMove(3);
		if (InputManager.keys[39])
			p.cmdMove(1);
		if (InputManager.keys[87])
			Camera.yOff += 3;
		if (InputManager.keys[83])
			Camera.yOff -= 3;
		if (InputManager.keys[68])
			Camera.xOff -= 3;
		if (InputManager.keys[65])
			Camera.xOff += 3;

		if (InputManager.keysToggled[32])
			p.cmdRotateTo(0);
		
		
		System.out.println("Rotation: " + Math.toDegrees(p.rotation));
		e1.shoot(p);

		s.update();
		// System.out.println(Math.toDegrees(p.rotation));
		Camera.focus(p.cm);
	}

	@Override
	public void init() {
		Point sPos = new Point(Driver.screenWidth / 2, Driver.screenHeight / 2);

		p = new PlayerShip(sPos);
		p.addPart(new Thruster(new Point(0,2), 2), new Thruster(new Point(1,2), 2));

		e1 = new EnemyShip(new Point(300, 200));
		e1.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
				new Armor(new Point(0, 1)), new Laser(new Point(0,2), 2));
		// e1.vel.x += 5;
		s.ships.add(e1);
		s.ships.add(p);
		
	}

}
