import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BattleScene extends Scene {
	PlayerShip p;
	EnemyShip e1;

	Ship camFocus = null;

	Ship selected = null;

	Ship target = null;
	Part partTarget = null;

	BufferedImage ui = Misc.loadImage("/battleScene.png");

	MiniMap minimap = new MiniMap(1605, 695, 310, 315, .03);

	Button shipYard;
	Button starMap;

	int num = 0;
	int num2 = 0;

	@Override
	public void draw(Graphics2D g) {

		if (InputManager.keys[75]) {
			this.running = false;
		}
		if (InputManager.keys[76]) {
			this.running = true;
		}
		g.setColor(new Color(36, 36, 36));
		g.fillRect(0, 0, Driver.screenWidth, Driver.screenHeight);

		g.setColor(new Color(87, 87, 87));
		SceneManager.sm.currSector.drawBasicGrid(g, 1000000, (int) (100 * (1 / Math.sqrt((Camera.scale)))), 2);

		if (target != null) {
			g.setPaint(new Color(255, 0, 0, 200));
			g.setStroke(new BasicStroke((int) (3 * Camera.scale)));
			Camera.toScreen(target.cm).drawCircle(g, (int) (150 * Camera.scale));
		}

		SceneManager.sm.currSector.draw(g);

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
		g.setFont(Misc.font);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString((int) (p.vel.getMagnitude() * 20) + " mph", 1760, 564 - (int) (564
				* ((p.vel.getMagnitude() * 20) / (int) ((p.transForces[0] * 2462.0 / p.mass / Sector.sectorDrag))))
				+ 120);

		// show selected ship
		if (selected != null && !selected.destroyed) {
			g.setColor(Color.LIGHT_GRAY);
			Camera.toScreen(selected.cm).drawCircle(g, (int) (150 * Camera.scale));
		}

		for (Ship s : SceneManager.sm.currSector.ships) {
			for (Part p : s.parts) {
				if (p.health < p.baseHealth && Camera.scale > .5) {
					g.rotate(s.rotation, Camera.toXScreen(s.cm.x), Camera.toYScreen(s.cm.y));
					drawPartHealth(g, p,
							Camera.toScreen(new Point(s.pos.x + p.pos.x * Part.SQUARE_WIDTH,
									s.pos.y + p.pos.y * Part.SQUARE_WIDTH)),
							(int) (40 * Camera.scale), (int) (15 * Camera.scale), p.health, p.baseHealth);
					g.rotate(-s.rotation, Camera.toXScreen(s.cm.x), Camera.toYScreen(s.cm.y));
				}
			}
		}

		if (partTarget != null) {
			g.rotate(target.rotation, Camera.toXScreen(target.cm.x), Camera.toYScreen(target.cm.y));
			g.setColor(Color.red);
			outlinePart(g,
					Camera.toScreen(new Point(target.pos.x + partTarget.pos.x * Part.SQUARE_WIDTH,
							target.pos.y + partTarget.pos.y * Part.SQUARE_WIDTH)),
					(int) (partTarget.width * Part.SQUARE_WIDTH * Camera.scale),
					(int) (partTarget.height * Part.SQUARE_WIDTH * Camera.scale));
			g.rotate(-target.rotation, Camera.toXScreen(target.cm.x), Camera.toYScreen(target.cm.y));
		}

		// shipYard.draw(g);
		// starMap.draw(g);

		minimap.mc.focus(Camera.toMap(Driver.screenWidth / 2, Driver.screenHeight / 2));
		minimap.draw(g, SceneManager.sm.currSector.ships);

		if (!running) {
			g.setPaint(new Color(0,0,0,128));
			g.fillRect(0, 0, Driver.screenWidth, Driver.screenHeight);
			g.setColor(Color.white);
			g.setFont(Misc.arialBig);
			g.drawString("Paused - [L] to resume", Driver.screenWidth/2 - 400, Driver.screenHeight/2);
		}

	}

	public void outlinePart(Graphics2D g, Point pos, int w, int h) {

		g.drawRect((int) pos.x, (int) pos.y, (int) w, (int) h);
		new Point(pos.x + w / 2, pos.y + h / 2).fillCircle(g, (int) (10 * Camera.scale));

	}

	public void drawPartHealth(Graphics2D g, Part p, Point pos, int w, int h, int curr, int total) {
		g.setStroke(new BasicStroke((int) (2 * Camera.scale)));
		g.setColor(Color.white);
		g.fillRect((int) pos.x, (int) pos.y, (int) w, (int) h);
		g.setColor(Color.green);
		g.fillRect((int) pos.x, (int) pos.y, w * curr / total, h);
		g.setColor(Color.black);
		g.drawRect((int) pos.x, (int) pos.y, (int) w, (int) h);
		// g.setFont(new Font("Arial", 0, (int)(12 * Camera.scale)));
		// g.drawString(p.health + "/" + p.baseHealth, (int) (pos.x + 3), (int) (pos.y +
		// 12));

	}

	@Override
	public void update() {
		
		if(SceneManager.sm.endSector.clear && SceneManager.sm.currSector.pos.isSame(SceneManager.sm.endSector.pos)) {
			System.out.println("WON");
			this.setActive(false);
			SceneManager.ws.setActive(true);
		}

		shipYard.update();
		starMap.update();

		// TODO remove before flight: to draw where mouse is - temp
//		if (InputManager.mouse[2]) {
//			System.out.println(InputManager.mPos.toString());
//		}

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
		if (InputManager.keys[87]) {
			Camera.yOff += 10 * (1 / Camera.scale);
			camFocus = null;
		}
		if (InputManager.keys[83]) {
			Camera.yOff -= 10 * (1 / Camera.scale);
			camFocus = null;
		}
		if (InputManager.keys[68]) {
			camFocus = null;
			Camera.xOff -= 10 * (1 / Camera.scale);
		}
		if (InputManager.keys[65]) {
			camFocus = null;
			Camera.xOff += 10 * (1 / Camera.scale);
		}

		// key to go to build area - b
		if (InputManager.keysReleased[66] || shipYard.clicked) {
			swtichToBuild();
		}
		if (InputManager.keysReleased[77] || starMap.clicked) {
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
		if (partTarget != null && partTarget.health <= 0)
			partTarget = null;

		if (partTarget == null && target != null)
			p.shoot(target);

		if (partTarget == null && target == null)
			p.ceaseFire();

		if (target != null && InputManager.mouseReleased[3] && !target.destroyed) {
			partTarget = SceneManager.sm.currSector.getClickShipPart(target);
			p.shoot(partTarget);
		}

		if (InputManager.mouseReleased[3]) {
			target = SceneManager.sm.currSector.getClickShip();
			if (target != null && partTarget == null) {
				p.shoot(target);
			}
		}
		if (target != null && (target.destroyed || target.isPlayer)) {
			target = null;
			partTarget = null;
			p.ceaseFire();
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

		shipYard = new Button(new Rect(0, 912, 307, 100), null, 0, "", null, Color.white, true, false);
		starMap = new Button(new Rect(310, 912, 307, 100), null, 0, "", null, Color.white, true, false);
		Point sPos = new Point(0, 0);

		p = new PlayerShip(sPos);
		p.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
				new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 2),
				new Thruster(new Point(1, 2), 2));

		// e1 = new EnemyShip(new Point(300, 200));
		// e1.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new
		// Armor(new Point(-1, 0)),
		// new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new
		// Point(-1, 2), 2),
		// new Thruster(new Point(1, 2), 2));
		// e1.vel.x += 5;
		// s.ships.add(e1);
		SceneManager.sm.player = p;
		SceneManager.sm.currSector.ships.add(p);

		camFocus = p;

	}

}
