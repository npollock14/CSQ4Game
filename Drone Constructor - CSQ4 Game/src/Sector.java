import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Sector {
	ArrayList<Ship> ships = new ArrayList<Ship>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public abstract void init();

}
