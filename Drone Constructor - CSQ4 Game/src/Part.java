import java.awt.Graphics2D;

public abstract class Part {
	public static final double SQUARE_WIDTH = 20;
	int width, height; // in blocks
	int health;
	Point pos; // in blocks from center of ship
	
	public Part(int width, int height, int health, Point pos) {
		this.width = width;
		this.height = height;
		this.health = health;
		this.pos = pos;
	}

	public void draw(Graphics2D g, Point sPos, double sRot) {
		g.rotate(sRot);
		g.drawRect((int) (sPos.x + pos.x * SQUARE_WIDTH), (int) (sPos.y + pos.y * SQUARE_WIDTH),
				(int) (width * SQUARE_WIDTH), (int) (height * SQUARE_WIDTH)); //TODO add camera
	}

}
