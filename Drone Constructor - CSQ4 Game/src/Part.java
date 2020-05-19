import java.awt.Graphics2D;

public abstract class Part {
	public static final double SQUARE_WIDTH = 20;
	int width, height; // in blocks
	int health;
	Point pos; // in blocks from center of ship
	String type;
	
	public Part(int width, int height, int health, Point pos, String type) {
		this.width = width;
		this.height = height;
		this.health = health;
		this.pos = pos;
		this.type = type;
	}
	public abstract void draw(Graphics2D g, Point sPos, double sRot);

	

}
