import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BattleScene extends Scene {
	PlayerShip p;
	EnemyShip e1;

	Ship camFocus = null;

	Ship selected = null;

	Ship target = null;

	BufferedImage ui = Misc.loadImage("/battleScene.png");
	
	Button shipYard;
	Button starMap;

	int num = 0;
	int num2 = 0;

	@Override
	public void draw(Graphics2D g) {
		

		SceneManager.sm.currSector.drawBasicGrid(g, 1000000, (int) (100 * (1 / Math.sqrt((Camera.scale)))), 2);
		

		if (target != null) {
			g.setPaint(new Color(255, 0, 0, 70));
			Camera.toScreen(target.cm).fillCircle(g, (int) (150 * Camera.scale));
		}
		g.setColor(Color.BLACK);

		g.drawImage(ui, 0, 0, 1920, 1010, null);

		// draw player scrap amt
		g.setFont(Misc.arialBig);
		g.setColor(Color.white);
		g.drawString("" + Driver.playerScrap, 1733, 72);

		// draw speed
		g.setColor(new Color(0, 119, 166));
		g.fillRect(1872, (int) (113 + 564 - 564
				* ((p.vel.getMagnitude() * 20) / (int) ((p.transForces[0] * 2462.0 / p.mass / Sector.sectorDrag)))), 33,
				(int) (564 * ((p.vel.getMagnitude() * 20)
						/ (int) ((p.transForces[0] * 2462.0 / p.mass / Sector.sectorDrag)))));
	//	System.out.println(((int)((p.transForces[0]/p.mass)*110 - (p.vel.getMagnitude() * Sector.sectorDrag))));
		//g.fillRect(1825, (int)(677 - 564*((int)((p.transForces[0]/p.mass)*110 - (p.vel.getMagnitude() * Sector.sectorDrag)) / (p.transForces[0]/p.mass))), 33, 564);
		g.setFont(Misc.font);
		g.setColor(Color.BLACK);
		g.drawString((int) (p.vel.getMagnitude() * 20) + " mph", 1760, 564 - (int) (564 * ((p.vel.getMagnitude() * 20)
				/ (int) ((p.transForces[0] * 2462.0 / p.mass / Sector.sectorDrag)))) + 120);
		SceneManager.sm.currSector.draw(g);
		
		//shipYard.draw(g);
		//starMap.draw(g);

	}

	@Override
	public void update() {
		
		shipYard.update();
		starMap.update();

		// TODO remove before flight: to draw where mouse is - temp
		if (InputManager.mouse[2]) {
			System.out.println(InputManager.mPos.toString());
		}

		if (InputManager.keys[81])
			p.cmdRotate(false);
		if (InputManager.keys[69])
			p.cmdRotate(true);
		if (InputManager.keys[38])
			p.cmdMove(0, 1);
		if (InputManager.keys[40])
			p.cmdMove(2, 1);
		if (InputManager.keys[37])
			p.cmdMove(3, 1);
		if (InputManager.keys[39])
			p.cmdMove(1, 1);
		if (InputManager.keys[87])
			Camera.yOff += 10 * (1 / Camera.scale);
		if (InputManager.keys[83])
			Camera.yOff -= 10 * (1 / Camera.scale);
		if (InputManager.keys[68])
			Camera.xOff -= 10 * (1 / Camera.scale);
		if (InputManager.keys[65])
			Camera.xOff += 10 * (1 / Camera.scale);

		// key to go to build area - b
		if (InputManager.keysReleased[66] || shipYard.clicked) {
			swtichToBuild();
		}
		if (InputManager.keysReleased[83] || starMap.clicked) {
			swtichToStarMap();
		}
		// key to select a ship
		if (InputManager.mouse[1]) {
			selected = SceneManager.sm.currSector.getClickShip();
			// now have a ship selected
		}

		// key to move the camera to a ship or area
		if (InputManager.mouseReleased[2]) {
			camFocus = SceneManager.sm.currSector.getClickShip();
			if (camFocus == null)
				Camera.focus(Camera.toMap(InputManager.mPos.x, InputManager.mPos.y));
		}
		if (InputManager.mouseReleased[3]) {
			target = SceneManager.sm.currSector.getClickShip();
			if (target != null)
				p.shoot(target);
		}
		if (camFocus != null)
			Camera.focus(camFocus.cm);
		// if(selected != null) selected.vel.print();

		SceneManager.sm.currSector.update();
	}
	public void swtichToStarMap() {
		SceneManager.ms.setActive(false);
		SceneManager.sm.setActive(true);
		
	}

	public void swtichToBuild() {
		InputManager.keysReleased[66] = false;
		SceneManager.ms.setActive(false);
		SceneManager.bs.setActive(true);
		SceneManager.bs.edit(p);
	}

	@Override
	public void init() {
		
		SceneManager.sm.init();
		
		shipYard = new Button(new Rect(0,912,307,100), null, 0, "", null, Color.white, true, false);
		starMap = new Button(new Rect(310,912,307,100), null, 0, "", null, Color.white, true, false);
		Point sPos = new Point(Driver.screenWidth / 2, Driver.screenHeight / 2);

		p = new PlayerShip(sPos);
		p.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
				new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 2),
				new Thruster(new Point(1, 2), 2));

//		e1 = new EnemyShip(new Point(300, 200));
//		e1.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
//				new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 2),
//				new Thruster(new Point(1, 2), 2));
		// e1.vel.x += 5;
		// s.ships.add(e1);
		SceneManager.sm.currSector.ships.add(p);

		camFocus = p;

	}

}
