import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BuildScene extends Scene {
	Ship s;
	Part toBuild = null;
	Part selection = null;
	int direction = 0;
	int rotation = 1000;
	Button selectHull;
	Button selectArmor;
	Button selectLaser;
	Button selectThruster;
	Button repairAll;
	boolean[] modes = new boolean[4]; // deleting - 0, selection - 1, build - 2, repair - 3
	
	boolean paused = true;
	BufferedImage backgroundUI;
	BufferedImage backgroundUISelection;
	BufferedImage backgroundUIRepair;
	BufferedImage helpUI;

	BufferedImage laserPic;
	BufferedImage armorPic;
	BufferedImage hullPic;
	BufferedImage thrusterPic;

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(new Color(36, 36, 36));
		g.fillRect(0, 0, Driver.screenWidth, Driver.screenHeight);

		// draw lines
		g.setColor(new Color(87, 87, 87));
		for (int i = 0; i < Driver.screenWidth; i += 50) {
			new Point(i, 0).drawLine(g, new Point(i, Driver.screenHeight));
			if (i < Driver.screenHeight)
				new Point(0, i).drawLine(g, new Point(Driver.screenWidth, i));
		}

		// draw parts on ship
		g.setStroke(new BasicStroke((float) (3)));
		Camera.scale = 1.25;
		for (Part p : s.parts) {
			p.draw(g, new Point(50 * 18, 50 * 9), 0, new Point(Driver.screenWidth / 2, Driver.screenHeight / 2), false);
		}
		// draw health bars if mode 3
		if (modes[3]) {
			g.drawImage(backgroundUIRepair, 0, 0, 1920, 1080, null);

			int total = 0;
			for (Part p : s.parts) {
				if (p.health != p.baseHealth)
					total += (p.baseHealth - p.health) / 4 + 1;
			}
			g.setFont(Misc.arialSmall);
			g.setColor(Color.white);
			g.drawString("" + total, 195, 803);

			Part repairHover = getSelected();
			for (Part p : s.parts) {
				Point pPos = new Point(50 * 18 + (p.pos.x * Part.SQUARE_WIDTH * 1.25) + 5,
						50 * 9 + (p.pos.y * Part.SQUARE_WIDTH * 1.25) + 5);

				g.setFont(Misc.tiny);
				drawPartHealth(g, p, pPos, 40, 15, p.health, p.baseHealth);

				if (repairHover != null && repairHover.equals(p)) {
					g.setColor(Color.green);
					g.setStroke(new BasicStroke(3));
					g.drawRect((int) pPos.x - 5, (int) pPos.y - 5, (int) (repairHover.width * Part.SQUARE_WIDTH * 1.25),
							(int) (repairHover.height * Part.SQUARE_WIDTH * 1.25));
				}

			}
		}

		// drawing the free model if in build mode
		if (modes[2]) {
			if (InputManager.mPos.y < 845) {
				toBuild.drawFree(g, new Point((int) ((InputManager.mPos.x / 50)) * Part.SQUARE_WIDTH * 1.25,
						(int) ((InputManager.mPos.y / 50)) * Part.SQUARE_WIDTH * 1.25));

			}
		}

		// draw background on top of lines
		g.drawImage(backgroundUI, 0, 0, 1920, 1080, null);

		// draw selection area
		if (modes[1] && selection != null) {
			g.drawImage(backgroundUISelection, 0, 0, 1920, 1080, null);
			g.setColor(Color.white);
			g.setFont(Misc.font);
			g.drawString("" + selection.type, 1658, 381);
			g.drawString("Health: " + selection.health + "/" + selection.baseHealth, 1658, 415);
			g.drawString(
					"Sell Price: "
							+ (selection.type
									.equals("Reactor")
											? "-----"
											: (int) (((double) selection.cost) * .5
													* ((double) selection.health / (double) selection.baseHealth))),
					1658, 450);
			g.setFont(Misc.fpsFont);
			Point pPos = new Point(50 * 18 + (selection.pos.x * Part.SQUARE_WIDTH * 1.25) + 5,
					50 * 9 + (selection.pos.y * Part.SQUARE_WIDTH * 1.25) + 5);
			g.setFont(Misc.tiny);
			drawPartHealth(g, selection, pPos, 40, 15, selection.health, selection.baseHealth);
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(3));
			g.drawRect((int) pPos.x - 5, (int) pPos.y - 5, (int) (selection.width * Part.SQUARE_WIDTH * 1.25),
					(int) (selection.height * Part.SQUARE_WIDTH * 1.25));

		}

		// draw player scrap amt
		g.setFont(Misc.arialBig);
		g.setColor(Color.white);
		g.drawString("" + Driver.playerScrap, 1733, 72);

		// TODO remove before flight: to draw where mouse is - temp
		if (InputManager.mouse[2]) {
			System.out.println(InputManager.mPos.toString());
		}

		// draw mode - bottom right
		g.setPaint(new Color(50, 168, 82, 128));
		if (modes[0]) {
			g.fillRect(76, 930, 55, 68);
		}
		if (modes[1]) {
			g.fillRect(15, 860, 50, 63);
		}
		if (modes[2]) {
			g.fillRect(11, 933, 60, 60);
		}
		if (modes[3]) {
			g.fillRect(78, 860, 55, 63);
		}
		g.setColor(Color.BLACK);

		g.setStroke(new BasicStroke(1));

		// draw ship stats
		g.setFont(Misc.arialVerySmall);
		g.setColor(Color.WHITE);
		g.drawString("Max Foward Speed: " + (int) ((s.transForces[0] * 2462.0 / s.mass / Sector.sectorDrag)) + " mph",
				1534, 925);
		g.drawString("Max Rotation Speed: " + (int) ((s.rotForce * 2462.0 * 181.0 / s.mass / Sector.sectorDrag))
				+ " deg/sec", 1534, 950);
		g.drawString("Mass: " + s.mass / 10 + "k lbs", 1534, 975);

		selectHull.draw(g, 10, 30);
		selectArmor.draw(g, 10, 30);
		selectLaser.draw(g, 10, 30);
		selectThruster.draw(g, 10, 30);
		
//		g.setFont(Misc.arialSmall);
//		g.setColor(Color.white);
//		g.drawString("Q and E to rotate parts || Modes: select [s], delete/sell [d], repair[r], build[b]", 300, 40);
//		g.drawString("Grab a part by clicking it from the toolbar and place it next to or diagonal to an existing part", 300, 80);
//		g.drawString("Placing a part in a valid location will charge you that parts cost, can then be sold/deleted for up to 50% value", 10, 120);
//		g.drawString("[Esc] to return to space", 10, 160);

		// repairAll.draw(g);
		
		if(paused) {
			g.drawImage(helpUI, 0, 0, null);
		}

	}

	public void drawPartHealth(Graphics2D g, Part p, Point pos, int w, int h, int curr, int total) {
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.white);
		g.fillRect((int) pos.x, (int) pos.y, (int) w, (int) h);
		g.setColor(Color.green);
		g.fillRect((int) pos.x, (int) pos.y, w * curr / total, h);
		g.setColor(Color.black);
		g.drawRect((int) pos.x, (int) pos.y, (int) w, (int) h);
		g.drawString(p.health + "/" + p.baseHealth, (int) (pos.x + 3), (int) (pos.y + 12));

	}

	public void changeMode(int m) {
		for (int i = 0; i < modes.length; i++) {
			if (m == i) {
				modes[i] = true;
			} else
				modes[i] = false;
		}
	}

	@Override
	public void update() {
		// update all buttons
		selectHull.update();
		selectArmor.update();
		selectLaser.update();
		selectThruster.update();
		
		if(InputManager.keysReleased[80]) {
			paused = !paused;
		}

		if (modes[3]) {
			repairAll.update();
			if (repairAll.clicked) {
				int total = 0;
				boolean canRepair = false;
				for (Part p : s.parts) {

					if (p.baseHealth - p.health != 0) {
						canRepair = true;
						total += (p.baseHealth - p.health) / 4 + 1;
					}
				}
				if (canRepair && Driver.playerScrap >= total) {
					for (Part p : s.parts) {
						p.health = p.baseHealth;
					}
					Driver.playerScrap -= total;
				}
			}
		}

		// check for broken parts
		s.checkBrokenParts();

		if (modes[2]) {
			if (InputManager.mouseReleased[1] && InputManager.mPos.y < Driver.screenHeight - 150) {
				int startingSize = s.parts.size();
				if (toBuild.type == "Hull" && Driver.playerScrap >= Hull.cost) {
					s.addPart(new Hull(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
					if (s.parts.size() > startingSize)
						Driver.playerScrap -= Hull.cost;
				}
				if (toBuild.type == "Armor" && Driver.playerScrap >= Armor.cost) {
					s.addPart(new Armor(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
					if (s.parts.size() > startingSize)
						Driver.playerScrap -= Armor.cost;
				}
				if (toBuild.type == "Lazer" && Driver.playerScrap >= Laser.cost) {
					s.addPart(new Laser(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
					if (s.parts.size() > startingSize)
						Driver.playerScrap -= Laser.cost;
				}
				if (toBuild.type == "Thruster" && Driver.playerScrap >= Thruster.cost) {
					s.addPart(new Thruster(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
					if (s.parts.size() > startingSize)
						Driver.playerScrap -= Thruster.cost;
				}
			}
		}

		// handle rotation
		if (modes[2]) {
			if (InputManager.keysReleased[81]) {
				rotation--;
			}
			if (InputManager.keysReleased[69]) {
				rotation++;
			}

			direction = rotation % 4;
		}

		// keybind to switch to delete mode and back to select mode
		if (InputManager.keysReleased[68]) {
			if (modes[0]) {
				changeMode(1);
			} else {
				changeMode(0);
			}
		}
		if (InputManager.keysReleased[66] && toBuild != null) {
			if (modes[2]) {
				changeMode(1);
			} else {
				changeMode(2);
			}
		}
		if (InputManager.keysReleased[82]) {
			if (modes[3]) {
				changeMode(1);
			} else {
				changeMode(3);
			}
		}
		if (InputManager.keysReleased[83]) {
			changeMode(1);
		}

		// handling switching of scenes
		if (InputManager.keysReleased[27]) {
			InputManager.keysReleased[27] = false;
			SceneManager.bs.setActive(false);
			SceneManager.ms.setActive(true);
		}

		// handling selection of buttons and rotations
		if (selectHull.clicked) {
			toBuild = new Hull(new Point(0, 0));
			changeMode(2);
		}
		if (selectArmor.clicked) {
			toBuild = new Armor(new Point(0, 0));
			changeMode(2);
		}
		if (selectLaser.clicked || (modes[2] && toBuild.type.equals("Lazer")
				&& (InputManager.keysReleased[81] || InputManager.keysReleased[69]))) {
			toBuild = new Laser(new Point(0, 0), direction);
			changeMode(2);
		}
		if (selectThruster.clicked || (modes[2] && toBuild.type.equals("Thruster")
				&& (InputManager.keysReleased[81] || InputManager.keysReleased[69]))) {
			toBuild = new Thruster(new Point(0, 0), direction);
			changeMode(2);
		}

		// if deleting
		if (modes[0] && InputManager.mouse[1]) {
			for (Part p : s.parts) {
				if (p.type.equals("Reactor"))
					continue;
				Rect r = new Rect((int) (p.pos.x * Part.SQUARE_WIDTH * 1.25 + (50 * 18)),
						p.pos.y * Part.SQUARE_WIDTH * 1.25 + (50 * 9), p.width * Part.SQUARE_WIDTH * 1.25,
						p.height * Part.SQUARE_WIDTH * 1.25);
				if (InputManager.mPos.inside(r, true)) {
					// selling adds scrap
					Driver.playerScrap += (int) ((double) p.cost) * .5 * ((double) p.health / (double) p.baseHealth);
					p.health = -1;

					s.updateCM();
					break;
				}

			}
		}
		if (modes[1] && InputManager.mouse[1]) {
			selection = getSelected();
		}

		if (modes[3] && InputManager.mouseReleased[1]) {
			Part hov = getSelected();
			if (hov != null && hov.health != hov.baseHealth) {
				if (Driver.playerScrap >= (hov.baseHealth - hov.health) / 4 + 1) {
					Driver.playerScrap -= (hov.baseHealth - hov.health) / 4 + 1;
					hov.health = hov.baseHealth;
				}
			}
		}
		
		

	}

	@Override
	public void init() {

		backgroundUI = Misc.loadImage("/buildSceneUI.png");
		backgroundUISelection = Misc.loadImage("/buildSceneUISelection.png");
		backgroundUIRepair = Misc.loadImage("/buildSceneRepairUI.png");

		laserPic = Misc.loadImage("/laser.png");
		armorPic = Misc.loadImage("/armor.png");
		hullPic = Misc.loadImage("/hull.png");
		thrusterPic = Misc.loadImage("/thruster.png");
		helpUI = Misc.loadImage("/buildSceneHelp.png");

		changeMode(1);

		selectHull = new Button(new Rect(331, 889, 80, 80), hullPic, 0, "", null, Color.WHITE, true, false);
		selectArmor = new Button(new Rect(479, 889, 80, 80), armorPic, 0, "", null, Color.WHITE, true, false);
		selectLaser = new Button(new Rect(481 + 148, 889, 80, 80), laserPic, 0, "", null, Color.WHITE, true, false);
		selectThruster = new Button(new Rect(483 + 148 * 2, 889, 80, 80), thrusterPic, 0, "", null, Color.WHITE, true, false);
		repairAll = new Button(new Rect(16, 763, 258, 58), null, 0, "", null, Color.WHITE, true, false);

	}

	public Part getSelected() {
		for (Part p : s.parts) {
			Rect r = new Rect((int) (p.pos.x * Part.SQUARE_WIDTH * 1.25 + (50 * 18)),
					p.pos.y * Part.SQUARE_WIDTH * 1.25 + (50 * 9), p.width * Part.SQUARE_WIDTH * 1.25,
					p.height * Part.SQUARE_WIDTH * 1.25);
			if (InputManager.mPos.inside(r, true)) {
				return p;
			}
		}
		return null;
	}

	public void startNew() {
		s = new PlayerShip(new Point(0, 0));
	}

	public void edit(Ship p) {
		s = p;
	}

	public void setShip(Ship ship) {
		s = ship;
	}

}
