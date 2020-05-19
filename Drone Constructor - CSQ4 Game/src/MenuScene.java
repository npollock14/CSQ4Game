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
		Camera.changeScale(InputManager.scroll/2);
		if(InputManager.keys[32]) p.rotation += .1;
		if(InputManager.keys[38]) p.vel.y -= .1;
		if(InputManager.keys[40]) p.vel.y += .1;
		if(InputManager.keys[37]) p.vel.x -= .1;
		if(InputManager.keys[39]) p.vel.x += .1;
		
		p.update();
		//Camera.focus(p.pos);
	}

	@Override
	public void init() {
		ArrayList<Part> playerParts = new ArrayList<Part>();
		playerParts.add(new Hull(new Point(0,0)));
		playerParts.add(new Hull(new Point(1,0)));
		playerParts.add(new Hull(new Point(-1,0)));
		playerParts.add(new Hull(new Point(0,1)));
		playerParts.add(new Hull(new Point(0,-1)));
		playerParts.add(new Armor(new Point(0,2)));
		p = new PlayerShip(new Point(Driver.screenWidth/2, Driver.screenHeight/2), 0, playerParts);
	}

}
