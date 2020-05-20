import java.util.ArrayList;

public class EnemyShip extends Ship {

	public EnemyShip(Point pos, ArrayList<Part> parts) {
		super(pos, 0, parts);
		isPlayer = true;
	}

	public EnemyShip(Point pos) {
		super(pos, 0);
		isPlayer = true;
	}

	public void update(Sector s) {
		pos.add(vel);
		rotation += rVel;
		for (Part p : parts) {
			p.bounds.translate(vel);
			p.bounds.rotate(rVel);
			p.update(this);
			p.checkProjectileCollision(s);
		}
		checkBrokenParts();
		checkDestroyed(s);
		cm.add(vel);
		addProjectiles(s);
	}

	private void addProjectiles(Sector s) {
		for (Projectile p : projectiles) {
			s.projectiles.add(p);
		}
		projectiles.clear();
	}

}
