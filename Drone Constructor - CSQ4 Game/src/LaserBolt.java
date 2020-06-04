import java.awt.Color;
import java.awt.Graphics2D;

public class LaserBolt extends Projectile{
	static double maxLifeSpan = 90;

	public LaserBolt(Point pos, Vec2 vel, double maxLifeSpan, double damage) {
		super(pos, vel, maxLifeSpan, damage);
	}

	@Override
	public void update() {
		pos.add(vel);
			age++;
		if(age > maxLifeSpan)
			alive = false;
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		Camera.toScreen(pos).fillCircle(g, (int) (12*Camera.scale));
	}

	

}
