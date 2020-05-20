import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuScene extends Scene {
	PlayerShip p;
	
	TestSector s = new TestSector();

	@Override
	public void draw(Graphics2D g) {
		g.setFont(Misc.f);
		g.drawString("MENU", 100, 100);
		s.draw(g);

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
		
		

		s.update();
		// Camera.focus(p.pos);
	}

	@Override
	public void init() {
		// ArrayList<Part> playerParts = new ArrayList<Part>();
		Point sPos = new Point(Driver.screenWidth / 2, Driver.screenHeight / 2);
		// Point cm = new Point(sPos.x + Part.SQUARE_WIDTH/2, sPos.y);
		// playerParts.add(new Hull(new Point(0,0),sPos,cm));
		// playerParts.add(new Hull(new Point(1,0),sPos,cm));
		// playerParts.add(new Hull(new Point(-1,0),sPos,cm));
		// playerParts.add(new Hull(new Point(0,1),sPos,cm));
		// playerParts.add(new Hull(new Point(0,-1),sPos,cm));
		// playerParts.add(new Armor(new Point(0,2),sPos,cm));
		// playerParts.add(new Laser(new Point(1,-2),sPos,cm));
		// playerParts.add(new Laser(new Point(-1,-2),sPos,cm));
		// p = new PlayerShip(sPos, playerParts);
		p = new PlayerShip(sPos);
		p.addPart(new Hull(new Point(0, 0)), new Hull(new Point(1, 0)), new Hull(new Point(-1, 0)),
				new Hull(new Point(0, 1)), new Hull(new Point(0, -1)), new Armor(new Point(0, 2)),
				new Laser(new Point(1, -2)), new Laser(new Point(-1, -2)));
		
		s.ships.add(p);
	}

}
