
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Reactor extends Part{
	static int width = 2; // in blocks
	static int height = 2;
	static int baseHealth = 30;
	static double mass = 50;
	static String type = "Reactor";
	static int cost = 50;

	@Override
	public Part clone() {
		
		return new Reactor();
	}
	@Override
	public String instantiateCMD() {
		return "";
	}
	
	public Reactor(Point pos, Point sPos, Point cm, double mass) {
		super(width, height, baseHealth, pos,type, sPos, cm,mass,cost);
	}
	public Reactor() {
		super(width, height, baseHealth, new Point(0,0), type,mass,cost);
	}
	
	public void draw(Graphics2D g, Point sPos, double sRot, Point cm, boolean drawHealth) {
		g.setStroke(new BasicStroke((float) (3 * Camera.scale)));
		g.rotate(sRot, cm.x, cm.y);
		
		int x1 = (int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale);
		int y1 = (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH);
		int w1 = (int) (width * Camera.scale * SQUARE_WIDTH);
		int h1 = (int) (height * Camera.scale * SQUARE_WIDTH);
		
		//hb.curr = health;
		//hb.pos = new Point(x1, y1);
		
		g.setColor(new Color(172, 255, 143));
		g.fillRect(x1,y1,w1,h1); 
		g.setColor(new Color(23, 87, 0));
		g.drawRect(x1, y1,w1, h1);
		
		if(drawHealth) {
			//hb.draw(g);
//			g.setFont(Misc.smallTitleFont);
//			g.setColor(Color.black);
//			g.drawString(""+health, x1+7, y1 + 35);
			}
		
		g.rotate(-sRot, cm.x, cm.y);
		
		//g.setColor(Color.green);
		//Camera.toScreen(getCM()).fillCircle(g, (int) (2*Camera.scale));
		//bounds.draw(g, false);
		
	}
	@Override
	public void update(Ship s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drawFree(Graphics2D g, Point p) {
		//never used
		
	}
	
	
}

