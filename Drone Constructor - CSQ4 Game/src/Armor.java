import java.awt.Color;
import java.awt.Graphics2D;

public class Armor extends Part {
	static int width = 1; // in blocks
	static int height = 1;
	static int health = 10;
	static double mass = 20;
	static String type = "Armor";
	static double deflectionChance = .3;

	public Armor(Point pos, Point sPos, Point cm, double mass) {
		super(width, height, health, pos, type, sPos, cm, mass);
	}
	public Armor(Point pos) {
		super(width, height, health, pos, type, mass);
	}

	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {
		//g.setColor(Color.green);
		//bounds.draw(g, false);
		
		g.setColor(Color.green);
		Camera.toScreen(getCM()).fillCircle(g, (int) (2*Camera.scale));
		
		g.setColor(Color.BLACK);
		g.rotate(sRot, cm.x, cm.y);
		g.drawRect((int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale),
				(int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH),
				(int) (width * Camera.scale * SQUARE_WIDTH), (int) (height * Camera.scale * SQUARE_WIDTH));
		g.rotate(-sRot, cm.x, cm.y);
	}
	@Override
	public void update(Ship s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkProjectileCollision(Sector s) {
		for(Projectile p : s.projectiles) {
			if(bounds.surrounds(p.pos)) {
				if(Math.random() < deflectionChance) {
					s.projectiles.remove(p);
				}else {
					health -= p.damage;
				}
			}
		}
		
	}

}
