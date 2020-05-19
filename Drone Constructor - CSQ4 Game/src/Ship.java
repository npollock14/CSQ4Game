import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Ship {
	Point pos = new Point(0, 0);
	Point cm = new Point(0,0);
	Vec2 vel = new Vec2(0, 0);
	double rVel = 0;
	double mass = 1;
	double rotation = 0;
	ArrayList<Part> parts = new ArrayList<Part>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Ship(Point pos, double rotation, ArrayList<Part> parts) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH/2, pos.y);
		this.rotation = rotation;
		this.parts = parts;
	}
	public Ship(Point pos, double rotation) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH/2, pos.y);
		this.rotation = rotation;
	}
	

	public void draw(Graphics2D g) {
		for (Part p : parts) {
			g.setStroke(new BasicStroke((float) (3*Camera.scale)));
			p.draw(g, Camera.toScreen(pos), rotation, Camera.toScreen(cm));
			
		}
		g.setColor(Color.RED);
		Camera.toScreen(cm).fillCircle(g, (int) (2 * Camera.scale));
		g.setColor(Color.BLACK);
	}
	public abstract void update(Sector s);
	
	public void addPart(Part... ps) {
		for(Part p : ps) {
		double tlx = pos.x + p.pos.x * Part.SQUARE_WIDTH;
		double tly = pos.y + p.pos.y * Part.SQUARE_WIDTH;
		p.bounds = new Poly(tlx, tly, tlx + Part.SQUARE_WIDTH * p.width, tly, tlx + Part.SQUARE_WIDTH * p.width,
				tly + Part.SQUARE_WIDTH * p.height, tlx, tly + Part.SQUARE_WIDTH * p.height, tlx, tly);
		p.bounds.setCenter(cm);
		parts.add(p);
		}
	}
}
