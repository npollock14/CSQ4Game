import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Laser extends Part {
	int width = 1; // in blocks
	int height = 2;
	static int baseHealth = 10;
	static double mass = 15;
	static String type = "Lazer";
	double timeSinceLastShot = 0;
	double fireRate = 60; // dps = fireRate * damage
	double damage = 3;
	double maxAngle = 60; // degrees
	boolean active = true;
	static double projectileSpeed = 12.0;
	int direction = 0; // 0 = up, 1 = right, 2 = down, 3 = left - clockwise pattern

	public Laser(Point pos, Point sPos, Point cm, double mass, int dir) {
		super(getW(dir), getH(dir), baseHealth, pos, type, sPos, cm, mass);
		this.direction = dir;
		this.width = getW(dir);
		this.height = getH(dir);
	}

	public Laser(Point pos, int dir) {
		super(getW(dir), getH(dir), baseHealth, pos, type, mass);
		this.direction = dir;
		this.width = getW(dir);
		this.height = getH(dir);
	}

	private static int getW(int dir) {
		if (dir == 1 || dir == 3) {
			return 2;
		} else
			return 1;
	}

	private static int getH(int dir) {
		if (dir == 0 || dir == 2) {
			return 2;
		} else
			return 1;
	}

	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {

		g.rotate(sRot, cm.x, cm.y);

		int x1 = (int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale);
		int y1 = (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH);
		int w1 = (int) (width * Camera.scale * SQUARE_WIDTH);
		int h1 = (int) (height * Camera.scale * SQUARE_WIDTH);

		g.setColor(new Color(255, 145, 145));
		g.fillRect(x1, y1, w1, h1);
		g.setColor(Color.red);
		g.drawRect(x1, y1, w1, h1);
		if (direction == 0) {
			g.drawLine((int) x1, (int) (y1 + SQUARE_WIDTH * Camera.scale / 4), (int) (x1 + SQUARE_WIDTH * Camera.scale),
					(int) (y1 + SQUARE_WIDTH * Camera.scale / 4));
		}
		if (direction == 1) {
			g.drawLine((int) (x1 + SQUARE_WIDTH * 7 * Camera.scale / 4), (int) (y1),
					(int) (x1 + SQUARE_WIDTH * 7 * Camera.scale / 4), (int) (y1 + SQUARE_WIDTH * Camera.scale));
		}
		if (direction == 2) {
			g.drawLine((int) x1, (int) (y1 + SQUARE_WIDTH * 7 * Camera.scale / 4),
					(int) (x1 + SQUARE_WIDTH * Camera.scale), (int) (y1 + SQUARE_WIDTH * 7 * Camera.scale / 4));
		}
		if (direction == 3) {
			g.drawLine((int) (x1 + SQUARE_WIDTH*Camera.scale/4), (int) (y1),
					(int) (x1 + SQUARE_WIDTH*Camera.scale/4), (int) (y1 + SQUARE_WIDTH * Camera.scale));
		}

		g.rotate(-sRot, cm.x, cm.y);

		// g.setColor(Color.green);
		// Camera.toScreen(getCM()).fillCircle(g, (int) (2*Camera.scale));
		// bounds.draw(g, false);

	}

	@Override
	public void update(Ship s) {

		if (s.shoot && timeSinceLastShot >= fireRate && active && (s.target != null || s.pTarget != null)) {
			Point firePoint = null;
			if (direction == 0) {
				firePoint = bounds.segs.get(0).getP1().avg(bounds.segs.get(0).getP2());
			}
			if (direction == 1) {
				firePoint = bounds.segs.get(1).getP1().avg(bounds.segs.get(1).getP2());
			}
			if (direction == 2) {
				firePoint = bounds.segs.get(2).getP1().avg(bounds.segs.get(2).getP2());
			}
			if (direction == 3) {
				firePoint = bounds.segs.get(3).getP1().avg(bounds.segs.get(3).getP2());
			}
			if(s.target != null) {
			if (canHitTarget(s.target.parts.get(0).getCM(), firePoint, s.vel, s.target.vel, s.rotation)) {
				s.projectiles.add(new LaserBolt(firePoint,
						getVel(s.target.parts.get(0).getCM(), firePoint, s.vel, s.target.vel).add(s.vel), 1000,
						damage));
				timeSinceLastShot = 0;
			}
			}else if(s.pTarget != null) {
				if (canHitTarget(s.pTarget, firePoint, s.vel, new Vec2(0,0), s.rotation)) {
					s.projectiles.add(new LaserBolt(firePoint,
							getVel(s.pTarget, firePoint, s.vel, new Vec2(0,0)).add(s.vel), 1000,
							damage));
					timeSinceLastShot = 0;
				}
			}
		} else {
			timeSinceLastShot++;
		}

	}

	private Vec2 getVel(Point target, Point source, Vec2 sVel, Vec2 tVel) {
		Vec2 overall = tVel.subtract(sVel);

		Vec2 totarget = target.subtract(source).toVec2();

		double a = Math.pow(overall.getMagnitude(), 2) - (projectileSpeed * projectileSpeed);
		double b = 2 * overall.dot(totarget);
		double c = totarget.dot(totarget);

		double p = -b / (2 * a);
		double q = Math.sqrt((b * b) - 4 * a * c) / (2 * a);

		double t1 = p - q;
		double t2 = p + q;
		double t;

		if (t1 > t2 && t2 > 0) {
			t = t2;
		} else {
			t = t1;
		}

		Vec2 aimSpot = target.toVec2().add(overall.simpleMult(t));
		double angle = source.angleTo(aimSpot.toPoint());
		return new Vec2(-Math.cos(angle) * projectileSpeed, -Math.sin(angle) * projectileSpeed);
	}

	private boolean canHitTarget(Point target, Point source, Vec2 sVel, Vec2 tVel, double sRot) {
		Vec2 overall = tVel.subtract(sVel);

		Vec2 totarget = target.subtract(source).toVec2();

		double a = Math.pow(overall.getMagnitude(), 2) - (projectileSpeed * projectileSpeed);
		double b = 2 * overall.dot(totarget);
		double c = totarget.dot(totarget);

		double p = -b / (2 * a);
		double q = Math.sqrt((b * b) - 4 * a * c) / (2 * a);

		double t1 = p - q;
		double t2 = p + q;
		double t;

		if (t1 > t2 && t2 > 0) {
			t = t2;
		} else {
			t = t1;
		}
		if (!(t > 0 && t < 180))
			return false;
		Vec2 aimSpot = target.toVec2().add(overall.simpleMult(t));
		double angle = source.angleTo(aimSpot.toPoint());
		// System.out.println("Delta Angle: " + Math.toDegrees(Math.abs(angle - sRot -
		// Math.toRadians(90))) % 360);
		if (direction == 0)
			return Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(90))) % 360 < 60;
		if (direction == 1)
			return Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(180))) % 360 < 60
					|| Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(180))) % 360 > 300;
		if (direction == 2)
			return Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(270))) % 360 < 60
					|| Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(270))) % 360 > 300;
		if (direction == 3)
		return Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(0))) % 360 < 60
		|| Math.toDegrees(Math.abs(angle - sRot - Math.toRadians(0))) % 360 > 300;
		return false;
	}

	@Override
	public void checkProjectileCollision(Sector s) {
		for (Projectile p : s.projectiles) {
			if (p.age > 5 && bounds.surrounds(p.pos)) {
				s.projectiles.remove(p);
				health -= p.damage;
				break;
			}
		}
	}

}
