import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BuildScene extends Scene{
	Ship s;
	Part selected = null;
	int direction = 0;
	Part tempHull = new Hull(new Point(0,0));
	Button selectHull;
	Button selectArmor;
	Button selectLaser;
	

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(0, Driver.screenHeight-150, Driver.screenWidth, 150);
		selectHull.draw(g, 10,30);
		selectArmor.draw(g, 10,30);
		selectLaser.draw(g, 10,30);
		for(int i = 0; i < Driver.screenWidth; i += 50) {
			new Point(i, 0).drawDashedLine(g, new Point(i, Driver.screenHeight-150));
			if(i < Driver.screenHeight - 150)
			new Point(0, i).drawDashedLine(g, new Point(Driver.screenWidth, i));
		}
		g.setStroke(new BasicStroke((float) (3 * Camera.scale)));
		Camera.scale = 1.25;
		for(Part p : s.parts) {
			p.draw(g, new Point(50 * 18, 50*9), 0, new Point(Driver.screenWidth/2, Driver.screenHeight/2));
		}
		if(selected != null && InputManager.mPos.y < Driver.screenHeight - 150) {
		selected.drawFree(g, new Point((int)((InputManager.mPos.x /50)) * Part.SQUARE_WIDTH * 1.25, (int)((InputManager.mPos.y /50)) * Part.SQUARE_WIDTH * 1.25));
		}
	}

	@Override
	public void update() {
		selectHull.update();
		selectArmor.update();
		selectLaser.update();
		if(selected != null && InputManager.mouseReleased[1] && InputManager.mPos.y < Driver.screenHeight - 150) {
		if(selected.type == "Hull") {
			s.addPart(new Hull(new Point((int)(InputManager.mPos.x /50) - 18, (int)(InputManager.mPos.y/50) - 9)));
		}
		if(selected.type == "Armor") {
			s.addPart(new Armor(new Point((int)(InputManager.mPos.x /50) - 18, (int)(InputManager.mPos.y/50) - 9)));
		}
		if(selected.type == "Lazer") {
			s.addPart(new Laser(new Point((int)(InputManager.mPos.x /50) - 18, (int)(InputManager.mPos.y/50) - 9), direction));
		}
		}
		
		if(selectHull.clicked) selected = new Hull(new Point(0,0));
		if(selectArmor.clicked) selected = new Armor(new Point(0,0));
		if(selectLaser.clicked) selected = new Laser(new Point(0,0),0);
		
	}

	@Override
	public void init() {
		selectHull = new Button(new Rect(100, Driver.screenHeight - 100, 50, 50), null, 0, "Hull", null, Color.WHITE, true, false);
		selectArmor = new Button(new Rect(175, Driver.screenHeight - 100, 50, 50), null, 0, "Armor", null, Color.WHITE, true, false);
		selectLaser = new Button(new Rect(250, Driver.screenHeight - 100, 50, 50), null, 0, "Laser", null, Color.WHITE, true, false);
		
	}
	public void startNew() {
		s = new PlayerShip(new Point(0,0));
	}
	public void edit(Ship p) {
		s = p;
	}
	public void setShip(Ship ship) {
		s = ship;
	}

}
