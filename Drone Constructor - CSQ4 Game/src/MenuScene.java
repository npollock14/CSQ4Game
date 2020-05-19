import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuScene extends Scene {
PlayerShip p;

	@Override
	public void draw(Graphics2D g) {
		g.setFont(Misc.f);
		g.drawString("MENU", 100, 100);
		p.draw(g);
		
	}

	@Override
	public void update() {
	}

	@Override
	public void init() {
		ArrayList<Part> playerParts = new ArrayList<Part>();
		playerParts.add(new Hull(new Point(0,0)));
		playerParts.add(new Hull(new Point(1,0)));
		p = new PlayerShip(new Point(1920/2, 1040/2), 0, playerParts);
	}

}
