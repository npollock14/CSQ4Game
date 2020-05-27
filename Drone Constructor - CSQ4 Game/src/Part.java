import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Part {
	public static final double SQUARE_WIDTH = 40;
	int width, height; // in blocks
	int health;
	Point pos; // in blocks from center of ship
	String type;
	Poly bounds;
	double mass = 1;
	ArrayList<Projectile> projectilesToAdd = new ArrayList<Projectile>();
	double[] transForces = {0.0,0.0,0.0,0.0};
	double rotForce = 0.0;

	public Part(int width, int height, int health, Point pos, String type, Point sPos, Point cm, double mass) {
		this.width = width;
		this.height = height;
		this.health = health;
		this.pos = pos;
		this.type = type;
		this.mass = mass;
		double tlx = sPos.x + pos.x * SQUARE_WIDTH;
		double tly = sPos.y + pos.y * SQUARE_WIDTH;
		bounds = new Poly(tlx, tly, tlx + SQUARE_WIDTH * width, tly, tlx + SQUARE_WIDTH * width,
				tly + SQUARE_WIDTH * height, tlx, tly + SQUARE_WIDTH * height, tlx, tly);
		bounds.setCenter(cm);
	}
	public Part(int width, int height, int health, Point pos, String type, double mass) {
		this.width = width;
		this.height = height;
		this.health = health;
		this.pos = pos;
		this.type = type;	
		this.mass = mass;
	}
	
	public Point getCM() {
		return bounds.getCenter();
	}
	
	public abstract void update(Ship s);
	
	public void checkProjectileCollision(Sector s) {
		for(Projectile p : s.projectiles) {
			if(bounds.surrounds(p.pos)) {
				health -= p.damage;
				s.projectiles.remove(p);
				break;
			}
		}
	}

	public abstract void draw(Graphics2D g, Point sPos, double sRot, Point cm);
	
	public abstract void drawFree(Graphics2D g, Point p);

}
