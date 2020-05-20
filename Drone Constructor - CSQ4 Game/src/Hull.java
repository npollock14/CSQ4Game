
import java.awt.Color;
import java.awt.Graphics2D;

public class Hull extends Part{
	static int width = 1; // in blocks
	static int height = 1;
	static int health = 10;
	static String type = "Hull";

	
	
	public Hull(Point pos, Point sPos, Point cm) {
		super(width, height, health, pos,type, sPos, cm);
	}
	public Hull(Point pos) {
		super(width, height, health, pos, type);
	}
	
	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {
		g.setColor(Color.green);
		bounds.draw(g, false);
		g.setColor(Color.GRAY);
		g.rotate(sRot, cm.x, cm.y);
		g.drawRect((int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale), (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH),
				(int) (width * Camera.scale * SQUARE_WIDTH), (int) (height * Camera.scale * SQUARE_WIDTH)); 
		g.rotate(-sRot, cm.x, cm.y);
	}
	@Override
	public void update(Ship s) {
		// TODO Auto-generated method stub
		
	}
	
	
}

