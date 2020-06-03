import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MiniMap {
	Rect bounds;
	double scale; //1 px map = 10 px game
	MiniCam mc;
	
	public MiniMap(int x,int y, int w, int h, double scale) {
		this.bounds = new Rect(x,y,w,h);
		this.scale = scale;
		this.mc = new MiniCam(x, y, scale, w, h);
	}

	public void draw(Graphics2D g, ArrayList<Ship> ships) {
		g.setPaint(new Color(0, 175, 201,128));
		g.fillRect((int)bounds.pos.x, (int)bounds.pos.y, (int)bounds.w, (int)bounds.h);
		for(Ship s : ships) {
		g.setColor(Color.RED);
			if(s.isPlayer) g.setColor(Color.green);
			
			Point loc = new Point(mc.toXScreen((int)(s.cm.x ))  + bounds.pos.x, mc.toYScreen((int)(s.cm.y)) + bounds.pos.y);
			if(loc.x > bounds.pos.x + bounds.w) loc.x = bounds.pos.x + bounds.w;
			if(loc.x < bounds.pos.x) loc.x = bounds.pos.x;
			if(loc.y > bounds.pos.y + bounds.h) loc.y = bounds.pos.y + bounds.h;
			if(loc.y < bounds.pos.y) loc.y = bounds.pos.y;
			loc.fillCircle(g, 5);
			
		}
	}
}
class MiniCam {
	double xOff, yOff, screenW, screenH;
	double scale;
	Point center;
	float scaleNotches = 0;

	public MiniCam(int xOff, int yOff, double scale, int screenW, int screenH) {
		super();
		this.xOff = xOff;
		this.yOff = yOff;
		this.scale = scale;
		this.screenW = screenW;
		this.screenH = screenH;
		center = new Point(screenW / 2, screenH / 2);
	}

	public void focus(Point p) {
		// p = map coordinates
		// place point p in center of screen & determine how displaced that was
		xOff = screenW / 2 - p.x;
		yOff = screenH / 2 - p.y;

	}
	public void changeScale(float notches) {
		scaleNotches += notches;
		scale = Math.pow(2, scaleNotches);
	}

	public int toXScreen(int x) {
		int dx = (int) ((x + xOff - center.x) * scale);
		return (int) (center.x + dx);

	}

	public int toYScreen(int y) {
		int dy = (int) ((y + yOff - center.y) * scale);
		return (int) (center.y + dy);

	}
	public double toXMap(int x) {
		return ((x - center.x)/scale) + center.x - xOff;
		
	}
	
	public double toYMap(int y) {
		return ((y - center.y)/scale) + center.y - yOff;
		
	}

}