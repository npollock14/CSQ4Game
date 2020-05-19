import java.util.ArrayList;

public class PlayerShip extends Ship{

	public PlayerShip(Point pos, double rotation, ArrayList<Part> parts) {
		super(pos, rotation, parts);
	}

	public void update() {
		pos.add(vel);
	}
	
}
