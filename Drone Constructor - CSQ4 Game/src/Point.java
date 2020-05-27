
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Point {
	double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vec2 toVec2() {
		return new Vec2(x,y);
	}
	public Point avg(Point p) {
		return new Point((x+p.x)/2, (y+p.y)/2);
	}
	public void print() {
		System.out.println("(" + x + ", " + y + ")");
	}

	public double distanceTo(Point p2) {
		return Math.sqrt((this.x - p2.x) * (this.x - p2.x) + (this.y - p2.y) * (this.y - p2.y));
	}

	public double angleTo(Point p2) {
		try {
			return Math.atan2(this.y - p2.y, this.x - p2.x);
		} catch (Exception e) {

		}
		return 0;
	}
	public double relDistanceTo(Point p2) {
		return ((this.x - p2.x) * (this.x - p2.x) + (this.y - p2.y) * (this.y - p2.y));
	}

	public boolean inside(Rect r, boolean including) {
if(!including) {
		return (x > r.pos.x && x < r.pos.x + r.w && y > r.pos.y && y < r.pos.y + r.h);
}
return (x >= r.pos.x && x <= r.pos.x + r.w && y >= r.pos.y && y <= r.pos.y + r.h);

	}

	public void add(Vec2 v) {
		this.x += v.x;
		this.y += v.y;
	}

	public boolean isSame(Point p) {
		return (double)p.x == (double)this.x && (double)p.y == (double)this.y;
	}
	public boolean isAdjacentTo(Point p) {
		return ((int)Math.abs(p.x - this.x) <= 1 && (int)Math.abs(p.y - this.y) <= 1 && !p.equals(this));
	}
	public Point subtract(Point p) {
		return new Point(this.x - p.x , this.y - p.y);
	}
	public GridPoint toGP() {
		return new GridPoint((int)x, (int)y);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	public void fillCircle(Graphics2D g, int r) {
		g.fillOval((int)(x - r), (int)(y-r),r*2, r*2);
	}
	
	public void drawLine(Graphics g, Point p) {
		g.drawLine((int)this.x, (int)this.y, (int)p.x, (int)p.y);
	}
	public void drawDashedLine(Graphics g, Point p) {
		Misc.drawDashedLine(g, (int)this.x, (int)this.y, (int)p.x, (int)p.y);
	}
}