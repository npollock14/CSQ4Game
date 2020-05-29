import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BuildScene extends Scene {
	Ship s;
	Part toBuild = null;
	int direction = 0;
	Button selectHull;
	Button selectArmor;
	Button selectLaser;
	Button selectThruster;
	boolean[] modes = new boolean[4]; // deleting - 0, selection - 1, build - 2, repair - 3
	BufferedImage backgroundUI;
	BufferedImage backgroundUISelection;

	@Override
	public void draw(Graphics2D g) {
		

		
		
		//draw lines
		g.setColor(Color.black);
		for (int i = 0; i < Driver.screenWidth; i += 50) {
			new Point(i, 0).drawLine(g, new Point(i, Driver.screenHeight));
			if (i < Driver.screenHeight)
				new Point(0, i).drawLine(g, new Point(Driver.screenWidth, i));
		}
		
		//draw background on top of lines
		g.drawImage(backgroundUI, 0, 0, 1920, 1080, null);
		
		//draw parts on ship
		g.setStroke(new BasicStroke((float) (3)));
		Camera.scale = 1.25;
		for (Part p : s.parts) {
			p.draw(g, new Point(50 * 18, 50 * 9), 0, new Point(Driver.screenWidth / 2, Driver.screenHeight / 2));
		}
		
		
		// drawing the free model if in build mode
		if (modes[2]) {
			if (InputManager.mPos.y < 845) {
				toBuild.drawFree(g, new Point((int) ((InputManager.mPos.x / 50)) * Part.SQUARE_WIDTH * 1.25,
						(int) ((InputManager.mPos.y / 50)) * Part.SQUARE_WIDTH * 1.25));
			}
		}
		
		

		// to draw where mouse is - temp
		if (InputManager.mouse[2]) {
			System.out.println(InputManager.mPos.toString());
		}
		
		//draw mode - bottom right
		g.setFont(Misc.f);
		g.setColor(Color.WHITE);
		if (modes[0]) {
			g.drawString("Delete", 1645, 970);
		}
		if (modes[1]) {
			g.drawString("Select", 1645, 970);
		}
		if (modes[2]) {
			g.drawString("Build", 1645, 970);
		}
		if (modes[3]) {
			g.drawString("Repair", 1645, 970);
		}
		
		selectHull.draw(g, 10, 30);
		selectArmor.draw(g, 10, 30);
		selectLaser.draw(g, 10, 30);
		selectThruster.draw(g, 10, 30);
		

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

		// check for broken parts
		s.checkBrokenParts();

		if (modes[2]) {
			if (InputManager.mouseReleased[1] && InputManager.mPos.y < Driver.screenHeight - 150) {
				if (toBuild.type == "Hull") {
					s.addPart(new Hull(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
				}
				if (toBuild.type == "Armor") {
					s.addPart(new Armor(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
				}
				if (toBuild.type == "Lazer") {
					s.addPart(new Laser(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
				}
				if (toBuild.type == "Thruster") {
					s.addPart(new Thruster(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
				}
			}
		}

		// handle rotation
		if (modes[2]) {
			if (InputManager.keysReleased[81]) {
				direction--;
			}
			if (InputManager.keysReleased[69]) {
				direction++;
			}

			direction = Math.abs(direction % 4);
		}

		// keybind to switch to delete mode and back to select mode
		if (InputManager.keysReleased[68]) {
			if (modes[0]) {
				changeMode(1);
			} else {
				changeMode(0);
			}
			System.out.println(modes[0]);
		}

		// handling switching of scenes
		if (InputManager.keysReleased[66]) {
			InputManager.keysReleased[66] = false;
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
					p.health = -1;
					s.updateCM();
					break;
				}

			}
		}

	}

	@Override
	public void init() {
		
		backgroundUI = Misc.loadImage("/buildSceneUI.png");
		backgroundUISelection = Misc.loadImage("/buildSceneUISelection.png");

		changeMode(1);

		selectHull = new Button(new Rect(331, 889, 80, 80), null, 0, "Hull", null, Color.WHITE,
				true, false);
		selectArmor = new Button(new Rect(175, Driver.screenHeight - 100, 50, 50), null, 0, "Armor", null, Color.WHITE,
				true, false);
		selectLaser = new Button(new Rect(250, Driver.screenHeight - 100, 50, 50), null, 0, "Laser", null, Color.WHITE,
				true, false);
		selectThruster = new Button(new Rect(325, Driver.screenHeight - 100, 50, 50), null, 0, "Thruster", null,
				Color.WHITE, true, false);

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
