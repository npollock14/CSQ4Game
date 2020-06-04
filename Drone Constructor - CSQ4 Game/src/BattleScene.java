import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BattleScene extends Scene {
	PlayerShip p;
	EnemyShip e1;

	Point camFocus = null;

	Ship selected = null;

	Ship target = null;
	Part partTarget = null;

	BufferedImage ui = Misc.loadImage("/battleScene.png");

	MiniMap minimap = new MiniMap(1605, 695, 310, 315, .03);

	Button shipYard;
	Button starMap;

	BufferedImage eye = Misc.loadImage("/eye.png");
	boolean eyeFocused = false;
	
	Point eyeLoc = new Point(0,0);

	boolean canBuild = false; // if out of range of an enemy ship ur good

	@Override
	public void draw(Graphics2D g) {

		
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

		if (target != null) {
			Point avg = Camera.toScreen(target.cm.avg(p.cm));
			Point mapAvg = target.cm.avg(p.cm);
			eyeLoc.setXY(mapAvg.x, mapAvg.y);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, eyeFocused ? .1f : .3f));
			g.drawImage(eye, (int)(avg.x - eye.getWidth() * Camera.scale / 2), (int)(avg.y - eye.getHeight() * Camera.scale / 2),
					(int)(eye.getWidth() * Camera.scale), (int)(eye.getHeight() * Camera.scale), null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			
		}

		minimap.mc.focus(Camera.toMap(Driver.screenWidth / 2, Driver.screenHeight / 2));
		minimap.draw(g, SceneManager.sm.currSector.ships);

		if (!running) {
			g.setPaint(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, Driver.screenWidth, Driver.screenHeight);
			g.setColor(Color.white);
			g.setFont(Misc.arialSmall);
			g.drawString("Paused - [p] to resume", 900, 50);
			g.setColor(Color.cyan);
			g.drawString(
					"Destroy enemies to collect scrap, build up your ship, and make your way to the end of the galaxy!",
					Driver.screenWidth / 2 - 800, Driver.screenHeight / 2 - 400);
			g.setColor(Color.WHITE);
			g.drawString("CONTROL BASICS:", Driver.screenWidth / 2 - 940, Driver.screenHeight / 2 - 350);
			
			g.drawString("MOVEMENT: Rotation = Q and E, Translation = WASD", Driver.screenWidth / 2 - 600, Driver.screenHeight / 2 - 300);
			g.drawString("COMBAT: Right Click on enemy = set as a target: lasers will shoot target when in range",
					Driver.screenWidth / 2 - 600, Driver.screenHeight / 2 - 250);
			g.drawString("CAMERA: Middle Mouse Click on ship = set the camera to that ship",
					Driver.screenWidth / 2 - 600, Driver.screenHeight / 2 - 200);
			g.drawString("Scroll Wheel = zoom", Driver.screenWidth / 2 - 600, Driver.screenHeight / 2 - 150);
			g.drawString("Arrow Keys = pan", Driver.screenWidth / 2 - 600, Driver.screenHeight / 2 - 100);
			
			g.drawString("GAMEPLAY + STRATEGY:", Driver.screenWidth / 2 - 930, Driver.screenHeight / 2 - 0);
			g.setColor(new Color(217, 52, 52));
			g.drawString("If you just started, your ship is weak! Upgrade it before entering combat!", Driver.screenWidth / 2 - 600,
					Driver.screenHeight / 2 - -50);
			g.setColor(Color.white);
			g.drawString("Cannot jump to the next sector if current sector is not clear", Driver.screenWidth / 2 - 600,
					Driver.screenHeight / 2 - -100);
			g.drawString("Enemies will attack you when in range & can't edit ship if in enemy range", Driver.screenWidth / 2 - 600,
					Driver.screenHeight / 2 - -150);
			
			
			//advanced
			g.drawString("ADVANCED:", Driver.screenWidth / 2 - 930, Driver.screenHeight / 2 - -200);
			g.drawImage(eye, Driver.screenWidth / 2 - 340, Driver.screenHeight / 2 + 225, eye.getWidth()/4, eye.getHeight()/4, null);
			g.drawString("Middle Click the        icon: set the camera to focus on the center of battle", Driver.screenWidth / 2 - 600,
					Driver.screenHeight / 2 - -250);
			g.drawString("Spacebar = recenter camera on player", Driver.screenWidth / 2 - 600,
					Driver.screenHeight / 2 - -300);
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

	}

	@Override
	public void update() {
		
		
		
		// check if player can build
		canBuild = true;
		for (Ship s : SceneManager.sm.currSector.ships) {
			if (!s.isPlayer) {
				if (s.cm.distanceTo(p.cm) < 2500) {
					canBuild = false;
					break;
				}
			}
		}

		if (SceneManager.sm.endSector.clear && SceneManager.sm.currSector.pos.isSame(SceneManager.sm.endSector.pos)) {
			System.out.println("Game WON");
			this.setActive(false);
			SceneManager.ws.setActive(true);
		}
		if (p.destroyed) {
			System.out.println("Game LOST");
			this.setActive(false);
			SceneManager.ls.setActive(true);
		}

		shipYard.update();
		starMap.update();

		// TODO remove before flight: to draw where mouse is - temp
		// if (InputManager.mouse[2]) {
		// System.out.println(InputManager.mPos.toString());
		// }

		if (InputManager.keys[81])
			p.cmdRotate(false);
		if (InputManager.keys[69])
			p.cmdRotate(true);
		if (InputManager.keys[87])
			p.cmdMove(0, 1);
		if (InputManager.keys[83])
			p.cmdMove(2, 1);
		if (InputManager.keys[65])
			p.cmdMove(3, 1);
		if (InputManager.keys[68])
			p.cmdMove(1, 1);
		if (InputManager.keys[38]) {
			Camera.yOff += 10 * (1 / Camera.scale);
			camFocus = null;
			eyeFocused = false;
		}
		if (InputManager.keys[40]) {
			Camera.yOff -= 10 * (1 / Camera.scale);
			camFocus = null;
			eyeFocused = false;
		}
		if (InputManager.keys[39]) {
			camFocus = null;
			eyeFocused = false;
			Camera.xOff -= 10 * (1 / Camera.scale);
		}
		if (InputManager.keys[37]) {
			camFocus = null;
			eyeFocused = false;
			Camera.xOff += 10 * (1 / Camera.scale);
		}

		// key to go to build area - b
		if (canBuild && InputManager.keysReleased[66] || shipYard.clicked) {
			swtichToBuild();
		}
		if (InputManager.keysReleased[77] || starMap.clicked) {
			swtichToStarMap();
		}
		
		// key to select a ship - no use atm
		if (InputManager.mouse[1]) {
			//selected = SceneManager.sm.currSector.getClickShip();
			// now have a ship selected
		}
		
		if(InputManager.keysReleased[32]) {
			camFocus = p.cm;
		}

		// key to move the camera to a ship or area
		if (InputManager.mouseReleased[2]) {
			eyeFocused = false;
			if(SceneManager.sm.currSector.getClickShip() != null)
			{
				camFocus = SceneManager.sm.currSector.getClickShip().cm;
			}else if(Camera.toScreen(eyeLoc).distanceTo(InputManager.mPos) < 40){
				camFocus = eyeLoc;
				eyeFocused = true;
			}else {
				camFocus = Camera.toMap(InputManager.mPos.x, InputManager.mPos.y);
			}
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
			Camera.focus(camFocus);
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

		camFocus = p.cm;

	}

}
