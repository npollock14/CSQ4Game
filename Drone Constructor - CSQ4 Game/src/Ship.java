import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Ship {
Point pos = new Point(0,0);
Vec2 vel = new Vec2(0,0);
double mass = 1;
double rotation = 0;
ArrayList<Part> parts = new ArrayList<Part>();

public Ship(Point pos, double rotation, ArrayList<Part> parts) {
	this.pos = pos;
	this.rotation = rotation;
	this.parts = parts;
}

public void draw(Graphics2D g) {
	for(Part p : parts) {
		p.draw(g, pos, rotation);
	}
}
}
