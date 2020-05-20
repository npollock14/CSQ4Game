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
			s.projectiles.add(new LaserBolt(bounds.segs.get(0).getP1(), new Vec2(0,-1), 1000, damage));
			timeSinceLastShot = 0;
		}else {
			timeSinceLastShot++;
		}
		
	}

}
