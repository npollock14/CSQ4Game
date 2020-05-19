import java.awt.Graphics2D;

public abstract class Projectile {
	Point pos;
	Vec2 vel;
	boolean alive = true;
	double maxLifeSpan = 1000;
	double age = 0;
	double damage;

	public Projectile(Point pos, Vec2 vel, double maxLifeSpan, double damage) {
		this.pos = pos;
		this.vel = vel;
		this.maxLifeSpan = maxLifeSpan;
		this.damage = damage;
	}

	public abstract void update();

	public abstract void draw(Graphics2D g);
	
	public boolean intersects(Part p) {
		return p.bounds.surrounds(pos);
	}
	
}
