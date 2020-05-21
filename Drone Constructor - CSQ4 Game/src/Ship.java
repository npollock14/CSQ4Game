import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Ship {
	Point pos = new Point(0, 0);
	Point cm = new Point(0,0);
	Vec2 vel = new Vec2(0, 0);
	double friction = .95;
	boolean isPlayer = false;
	double rVel = 0;
	double mass = 0;
	double rotation = 0;
	ArrayList<Part> parts = new ArrayList<Part>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	boolean shoot = false;
	Ship target;
	boolean destoryed = false;

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
	public void rotate(double amt) {
		rotation += amt;
		for(Part p : parts) {
			p.bounds.rotate(amt, cm);
		}
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
	
	public void checkBrokenParts() {
		for(Part p : parts) {
			if(p.health <= 0) {
				parts.remove(p);
				checkBrokenParts();
				break;
			}
		}
	}
	
	public void checkDestroyed(Sector s) {
		if(parts.size() == 0) {
			s.ships.remove(this);
			this.destoryed = true;
		}
	}
	
	public void addPart(Part... ps) {
		double rot = rotation;
		rotate(-rot);
		for(Part p : ps) {
		double tlx = pos.x + p.pos.x * Part.SQUARE_WIDTH;
		double tly = pos.y + p.pos.y * Part.SQUARE_WIDTH;
		p.bounds = new Poly(tlx, tly, tlx + Part.SQUARE_WIDTH * p.width, tly, tlx + Part.SQUARE_WIDTH * p.width,
				tly + Part.SQUARE_WIDTH * p.height, tlx, tly + Part.SQUARE_WIDTH * p.height, tlx, tly);
		mass += p.mass;
		p.bounds.setCenter(cm);
		parts.add(p);
		}
		System.out.println("New Mass: " + mass);
		double sumX = 0;
		double sumY = 0;
		for(Part p : parts) {
			sumX += (p.getCM().x * p.mass);
			sumY += (p.getCM().y * p.mass);
		}
		cm = new Point(sumX/mass, sumY/mass);
		
		for(Part p : parts) p.bounds.setCenter(cm);
		rotate(rot);
		
	}
	
	public void shoot(Ship s) {
		shoot = true;
		target = s;
	}
	public void ceaseFire() {
		shoot = false;
	}
	
}
