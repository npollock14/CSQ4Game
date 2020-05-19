import java.awt.Color;
import java.awt.Graphics2D;

public class Laser extends Part{
	static int width = 1; // in blocks
	static int height = 2;
	static int health = 10;
	static String type = "Lazer";

	
	public Laser(Point pos) {
		super(width, height, health, pos,type);
	}
	
	public void draw(Graphics2D g, Point sPos, double sRot) {
		g.setColor(Color.RED);
		g.rotate(sRot, sPos.x, sPos.y);
		g.drawRect((int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale - (Camera.scale *SQUARE_WIDTH/2)), (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH - (Camera.scale *SQUARE_WIDTH/2)),
				(int) (width * Camera.scale * SQUARE_WIDTH), (int) (height * Camera.scale * SQUARE_WIDTH)); 
		g.rotate(-sRot, sPos.x, sPos.y);
	}
	
}
