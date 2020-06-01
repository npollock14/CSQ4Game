import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Sector {
	static double sectorDrag = .992;
	
	Point pos;
	
	ArrayList<Ship> ships = new ArrayList<Ship>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<ScrapEntity> scrap = new ArrayList<ScrapEntity>();
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public abstract void init();
	
	public void drawBasicGrid(Graphics2D g, int s, int spacing, int sens) {
		for (int i = (int)(-s/spacing); i <= (int)(s / spacing); i++) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(Camera.toXScreen(i * (spacing)), Camera.toYScreen(-s), Camera.toXScreen(i * (spacing)),
					Camera.toYScreen(s));
			g.drawLine(Camera.toXScreen(-s), Camera.toYScreen(i * (spacing)), Camera.toXScreen(s),
					Camera.toYScreen(i * (spacing)));
		}
	}
	
	public Ship getClickShip() {
		for(Ship s : ships) {
			for(Part p : s.parts) {
				if(p.bounds.surrounds(Camera.toMap(InputManager.mPos.x, InputManager.mPos.y))) return s;
			}
		}
		return null;
	}
	public Part getClickShipPart(Ship s) {
		for(Part p : s.parts) {
			if(p.bounds.surrounds(Camera.toMap(InputManager.mPos.x, InputManager.mPos.y))) return p;
		}
		return null;
	}

}
