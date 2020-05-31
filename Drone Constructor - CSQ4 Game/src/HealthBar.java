import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class HealthBar {
	int curr, total;
	Point pos;
	int w, h;

	public HealthBar(int curr, int total, Point pos, int w, int h) {
		super();
		this.curr = curr;
		this.total = total;
		this.pos = pos;
		this.w = w;
		this.h = h;
	}

	public void draw(Graphics2D g) {
		
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.white);
		g.fillRect(Camera.toXScreen(pos.x), Camera.toYScreen(pos.y), (int)(w * Camera.scale), (int)(h * Camera.scale));
		g.setColor(Color.black);
		g.drawRect(Camera.toXScreen(pos.x), Camera.toYScreen(pos.y), (int)(w * Camera.scale), (int)(h * Camera.scale));
		g.setColor(Color.green);
		g.fillRect(Camera.toXScreen(pos.x+1), Camera.toYScreen(pos.y + 1), (int)(w * Camera.scale / (curr/total)-2), (int)(h * Camera.scale-2));
		
	
	}
}
