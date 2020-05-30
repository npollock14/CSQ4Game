import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BuildScene extends Scene {
	Ship s;
	Part toBuild = null;
	Part selection = null;
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
		
		//draw selection area
		if(modes[1] && selection != null) {
			g.drawImage(backgroundUISelection, 0, 0, 1920, 1080, null);
			g.setColor(Color.white);
			g.setFont(Misc.font);
			g.drawString("" + selection.type, 1658, 381);
			g.drawString("Health: " + selection.health + "/" + selection.baseHealth, 1658, 415);
			g.drawString("Sell Price: " + (selection.type.equals("Reactor") ? "-----" : (int)(((double)selection.cost) * .5 * ((double)selection.health / (double)selection.baseHealth))), 1658, 450);
		}
		
		//draw player scrap amt
		g.setFont(Misc.arialBig);
		g.setColor(Color.white);
		g.drawString("" + Driver.playerScrap, 1733, 72);
		
		//draw parts on ship
		g.setStroke(new BasicStroke((float) (3)));
		Camera.scale = 1.25;
		for (Part p : s.parts) {
			p.draw(g, new Point(50 * 18, 50 * 9), 0, new Point(Driver.screenWidth / 2, Driver.screenHeight / 2), modes[3]);
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
		g.setPaint(new Color(50, 168, 82,128));
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
				int startingSize = s.parts.size();
				if (toBuild.type == "Hull" && Driver.playerScrap >= Hull.cost) {
					s.addPart(new Hull(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
					if(s.parts.size() > startingSize) Driver.playerScrap -= Hull.cost;
				}
				if (toBuild.type == "Armor" && Driver.playerScrap >= Armor.cost) {
					s.addPart(new Armor(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9)));
					if(s.parts.size() > startingSize) Driver.playerScrap -= Armor.cost;
				}
				if (toBuild.type == "Lazer" && Driver.playerScrap >= Laser.cost) {
					s.addPart(new Laser(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
					if(s.parts.size() > startingSize) Driver.playerScrap -= Laser.cost;
				}
				if (toBuild.type == "Thruster" && Driver.playerScrap >= Thruster.cost) {
					s.addPart(new Thruster(
							new Point((int) (InputManager.mPos.x / 50) - 18, (int) (InputManager.mPos.y / 50) - 9),
							direction));
					if(s.parts.size() > startingSize) Driver.playerScrap -= Thruster.cost;
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
					//selling adds scrap
					Driver.playerScrap += (int)((double)p.cost) * .5 * ((double)p.health / (double)p.baseHealth);
					p.health = -1;
					
					s.updateCM();
					break;
				}

			}
		}
		if(modes[1] && InputManager.mouse[1]) {
			selection = getSelected();
		}

	}

	@Override
	public void init() {
		
		backgroundUI = Misc.loadImage("/buildSceneUI.png");
		backgroundUISelection = Misc.loadImage("/buildSceneUISelection.png");

		changeMode(3);

		selectHull = new Button(new Rect(331, 889, 80, 80), null, 0, "", null, Color.WHITE,
				true, false);
		selectArmor = new Button(new Rect(479, 889, 80, 80), null, 0, "", null, Color.WHITE,
				true, false);
		selectLaser = new Button(new Rect(481 + 148, 889, 80, 80), null, 0, "", null, Color.WHITE,
				true, false);
		selectThruster = new Button(new Rect(483 + 148*2, 889, 80, 80), null, 0, "", null,
				Color.WHITE, true, false);

	}
	public Part getSelected() {
		for(Part p : s.parts) {
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
