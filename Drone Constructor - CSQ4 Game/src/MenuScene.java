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
		Camera.toScreen(new Point(0,0)).fillCircle(g, (int) (20*Camera.scale));
	}

	@Override
	public void update() {
		
		Camera.changeScale(InputManager.scroll / 2);
		if (InputManager.keys[81])
			p.rVel += .001;
		if (InputManager.keys[69])
			p.rVel -= .001;
		if (InputManager.keys[38])
			p.vel.y -= .1;
		if (InputManager.keys[40])
			p.vel.y += .1;
		if (InputManager.keys[37])
			p.vel.x -= .1;
		if (InputManager.keys[39])
			p.vel.x += .1;
		if (InputManager.keys[87])
			Camera.yOff += 3;
		if (InputManager.keys[83])
			Camera.yOff -= 3;
		if (InputManager.keys[68])
			Camera.xOff -= 3;
		if (InputManager.keys[65])
			Camera.xOff += 3;
		
		if(InputManager.keysToggled[32]) p.shoot(new Point(0,0));
		if(!InputManager.keysToggled[32] && p.shoot) p.ceaseFire();
		

		s.update();
		//Camera.focus(p.cm);
	}

	@Override
	public void init() {
		Point sPos = new Point(Driver.screenWidth / 2, Driver.screenHeight / 2);
		
		p = new PlayerShip(sPos);
		p.addPart(new Hull(new Point(0, 0)), new Hull(new Point(1, 0)), new Hull(new Point(-1, 0)),
				new Hull(new Point(0, 1)), new Hull(new Point(0, -1)), new Armor(new Point(0, 2)),
				new Laser(new Point(1, -2)), new Laser(new Point(-1, -2)));
		
		
		e1 = new EnemyShip(new Point(300, 200));
		e1.addPart(new Hull(new Point(0, 0)), new Hull(new Point(1, 0)), new Hull(new Point(-1, 0)),
				new Hull(new Point(0, 1)));
		
		s.ships.add(e1);
		s.ships.add(p);
	}

}
