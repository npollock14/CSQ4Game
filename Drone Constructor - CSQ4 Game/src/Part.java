import java.awt.Graphics2D;

public abstract class Part {
	public static final double SQUARE_WIDTH = 20;
	int width, height; // in blocks
	int health;
	Point pos; // in blocks from center of ship
	String type;
	Poly bounds;

	public Part(int width, int height, int health, Point pos, String type, Point sPos, Point cm) {
		this.width = width;
		this.height = height;
		this.health = health;
		this.pos = pos;
		this.type = type;
		double tlx = sPos.x + pos.x * SQUARE_WIDTH;
		double tly = sPos.y + pos.y * SQUARE_WIDTH;
		bounds = new Poly(tlx, tly, tlx + SQUARE_WIDTH * width, tly, tlx + SQUARE_WIDTH * width,
				tly + SQUARE_WIDTH * height, tlx, tly + SQUARE_WIDTH * height, tlx, tly);
		bounds.setCenter(cm);
	}

	public abstract void draw(Graphics2D g, Point sPos, double sRot, Point cm);

}
