import java.awt.Color;
import java.awt.Graphics2D;

public class Laser extends Part {
	static int width = 1; // in blocks
	static int height = 2;
	static int health = 10;
	static String type = "Lazer";
	double timeSinceLastShot = 0;
	double fireRate = 60; //dps = fireRate * damage
	double damage = 3;
	boolean active = true;
	static double projectileSpeed = 7.0;

	public Laser(Point pos, Point sPos, Point cm) {
		super(width, height, health, pos, type, sPos, cm);
	}
	public Laser(Point pos) {
		super(width, height, health, pos, type);
	}

	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {
		g.setColor(Color.green);
		bounds.draw(g, false);
		g.setColor(Color.red);
		g.rotate(sRot, cm.x, cm.y);
		g.drawRect((int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale),
				(int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH), (int) (width * Camera.scale * SQUARE_WIDTH),
				(int) (height * Camera.scale * SQUARE_WIDTH));
		g.rotate(-sRot, cm.x, cm.y);
	}
	
	@Override
	public void update(Ship s) {
		if(s.shoot && timeSinceLastShot >= fireRate && active) {
			Point firePoint = new Point(bounds.segs.get(0).getP1().x + SQUARE_WIDTH/2, bounds.segs.get(0).getP1().y);
			if(canHitTarget(s.target, firePoint, s.vel)) {
			s.projectiles.add(new LaserBolt(firePoint, getVel(s.target, firePoint, s.vel), 1000, damage));
			timeSinceLastShot = 0;
			}
		}else {
			timeSinceLastShot++;
		}
		
	}
	private Vec2 getVel(Point target, Point currPos, Vec2 sVel) {
		
		Vec2 totarget =  target.subtract(currPos).toVec2();
		totarget.print();

		double a = sVel.dot(sVel) - (projectileSpeed * projectileSpeed);
		double b = 2 * sVel.simpleMult(-1).dot(totarget);
		double c = totarget.dot(totarget);

		double p = -b / (2 * a);
		double q = Math.sqrt((b * b) - 4 * a * c) / (2 * a);

		double t1 = p - q;
		double t2 = p + q;
		double t;

		if (t1 > t2 && t2 > 0)
		{
		    t = t2;
		}
		else
		{
		    t = t1;
		}
		System.out.println(t);
		Vec2 aimSpot = target.toVec2().add(sVel.simpleMult(t));
		
		System.out.println(Math.toDegrees(currPos.angleTo(aimSpot.toPoint())) + "degrees");
		target.toVec2().print();
		return aimSpot.subtract(currPos.toVec2()).setMagnitude(projectileSpeed);
	}
private boolean canHitTarget(Point target, Point currPos, Vec2 sVel) {
		return true;
	} 
	

}
