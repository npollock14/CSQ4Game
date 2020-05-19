import java.util.ArrayList;

public class PlayerShip extends Ship{

	public PlayerShip(Point pos, ArrayList<Part> parts) {
		super(pos, 0, parts);
	}
	public PlayerShip(Point pos) {
		super(pos, 0);
	}

	public void update(Sector s) {
		pos.add(vel);
		rotation += rVel;
		for(Part p : parts) {
			p.bounds.translate(vel);
			p.bounds.rotate(rVel);
		}
		cm.add(vel);
		addProjectiles(s);
	}
	public void addProjectiles(Sector s) {
		
	}
	
}
