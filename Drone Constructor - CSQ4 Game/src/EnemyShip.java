import java.util.ArrayList;

public class EnemyShip extends Ship {
	

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
		for(Ship s : sec.ships) {
			if(s.isPlayer) {
				this.shoot(s);
				if(cm.distanceTo(s.cm) < 1500) {
					if(cm.distanceTo(s.cm) > 800) {
					this.cmdMoveTo(s.cm);
					}else {
						this.cmdRotateTo(cm.angleTo(s.cm) - Math.PI/2);
					}
				}
				break;
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
