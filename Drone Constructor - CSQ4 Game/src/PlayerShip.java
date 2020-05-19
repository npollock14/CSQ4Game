import java.util.ArrayList;

public class PlayerShip extends Ship{

	public PlayerShip(Point pos, ArrayList<Part> parts) {
		super(pos, 0, parts);
	}

	public void update() {
		pos.add(vel);
		rotation += rVel;
		for(Part p : parts) {
			p.bounds.translate(vel);
			p.bounds.rotate(rVel);
		}
		cm.add(vel);
	}
	
}
