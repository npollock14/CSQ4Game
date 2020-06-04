import java.util.ArrayList;

public class EnemyShip extends Ship {
	Point wander;
	boolean wanderComplete = true;
	int waitTime = 0;
	boolean waitComplete = false;

	public EnemyShip(Point pos, ArrayList<Part> parts) {
		super(pos, 0, parts);
	}

	public EnemyShip(Point pos) {
		super(pos, 0);
	}

	public void update(Sector sec) {
		pos.add(vel);
		rotation += rVel;
		for (Part p : parts) {
			p.bounds.translate(vel);
			p.bounds.rotate(rVel);
			p.update(this);
			p.checkProjectileCollision(sec);
		}
		checkBrokenParts();
		checkDestroyed(sec);
		cm.add(vel);
		addProjectiles(sec);
		for (Ship s : sec.ships) {
			if (s.isPlayer && !s.destroyed) {

				if (cm.distanceTo(s.cm) < 2500) {
					this.shoot(s);
					if (cm.distanceTo(s.cm) > 900) {
						this.cmdMoveTo(s.cm);
					} else {
						this.cmdRotateTo(cm.angleTo(s.cm) - Math.PI / 2);
					}
				} else {
					this.ceaseFire();
				}
			}
		}
		if (!shoot) {
			if (!waitComplete) {
				waitTime--;
				if (waitTime <= 0)
					waitComplete = true;
			}
			if (waitComplete) {
				wander = new Point(cm.x + 2 * (Math.random() - .5) * 1000.0, cm.y + 2 * (Math.random() - .5) * 1000.0);
				wanderComplete = false;
			}
			if (wanderComplete) {
				waitComplete = false;
				waitTime = Misc.rBt(200, 600);
			}
			
			
			
			//check if done wandering
			if(wander != null) {
				if (cm.distanceTo(wander) < 75) {
					wanderComplete = true;
				}
			}

			if (!wanderComplete && waitComplete) {
				this.cmdMoveTo(wander);
				if (cm.distanceTo(wander) < 75) {
					wanderComplete = true;
				}
			}
		}

	}

	private void addProjectiles(Sector s) {
		for (Projectile p : projectiles) {
			s.projectiles.add(p);
		}
		projectiles.clear();
	}

}
