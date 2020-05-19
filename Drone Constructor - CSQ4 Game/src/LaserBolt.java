import java.awt.Color;
import java.awt.Graphics2D;

public class LaserBolt extends Projectile{

	public LaserBolt(Point pos, Vec2 vel, double maxLifeSpan, double damage) {
		super(pos, vel, maxLifeSpan, damage);
	}

	@Override
	public void update() {
		pos.add(vel);
		if(age < maxLifeSpan) {
			age++;
		}else {
			alive = false;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		pos.fillCircle(g, 2);
	}

	

}
