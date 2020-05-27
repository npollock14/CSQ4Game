import java.awt.Color;
import java.awt.Graphics2D;

public class Armor extends Part {
	static int width = 1; // in blocks
	static int height = 1;
	static int baseHealth = 10;
	static double mass = 20;
	static String type = "Armor";
	static double deflectionChance = .3;

	public Armor(Point pos, Point sPos, Point cm, double mass) {
		super(width, height, baseHealth, pos, type, sPos, cm, mass);
	}

	public Armor(Point pos) {
		super(width, height, baseHealth, pos, type, mass);
	}

	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {

		g.rotate(sRot, cm.x, cm.y);

		int x1 = (int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale);
		int y1 = (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH);
		int w1 = (int) (width * Camera.scale * SQUARE_WIDTH);
		int h1 = (int) (height * Camera.scale * SQUARE_WIDTH);

		g.setColor(new Color(70, 70, 70));
		g.fillRect(x1, y1, w1, h1);
		g.setColor(new Color(40, 40, 40));
		g.drawRect(x1, y1, w1, h1);

		g.rotate(-sRot, cm.x, cm.y);

		// g.setColor(Color.green);
		// Camera.toScreen(getCM()).fillCircle(g, (int) (2*Camera.scale));
		// bounds.draw(g, false);
	}

	@Override
	public void update(Ship s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkProjectileCollision(Sector s) {
		for (Projectile p : s.projectiles) {
			if (bounds.surrounds(p.pos)) {
				if (Math.random() < deflectionChance) {
					s.projectiles.remove(p);
					break;
				} else {
					health -= p.damage;
					s.projectiles.remove(p);
					break;
				}
			}
		}

	}

	@Override
	public void drawFree(Graphics2D g, Point p) {
		g.setColor(new Color(70, 70, 70));
		g.fillRect((int)p.x,(int)p.y,(int)(SQUARE_WIDTH * Camera.scale),(int)(SQUARE_WIDTH*Camera.scale)); 
		g.setColor(new Color(40, 40, 40));
		g.drawRect((int)p.x,(int)p.y,(int)(SQUARE_WIDTH * Camera.scale),(int)(SQUARE_WIDTH*Camera.scale));
		
	}

}
