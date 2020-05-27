
import java.awt.Color;
import java.awt.Graphics;

public class Rect {
	Point pos;
	double h, w;

	public Rect(double x, double y, double w, double h) {

		this.pos = new Point(x, y);
		this.h = h;
		this.w = w;

	}

	public boolean intersects(Rect r, boolean including) {
		return (pos.inside(r,including) || new Point(pos.x + w, pos.y).inside(r,including) || new Point(pos.x + w, pos.y + h).inside(r,including)
				|| new Point(pos.x, pos.y + h).inside(r,including) || r.pos.inside(this,including)
				|| new Point(r.pos.x + r.w, r.pos.y).inside(this,including)
				|| new Point(r.pos.x + r.w, r.pos.y + r.h).inside(this,including)
				|| new Point(r.pos.x, r.pos.y + r.h).inside(this,including));
	}

	public int classifyCol(Rect r) {
		if (!this.intersects(r, true)) {
			return 0;
		}
		double right = Math.abs(pos.x + w - r.pos.x);
		double left = Math.abs(pos.x - (r.pos.x + r.w));
		double bottom = Math.abs(pos.y + h - (r.pos.y));
		double top = Math.abs(pos.y - (r.pos.y + r.h));
		if (top < right && top < left && top < bottom) {
			return 1;
		}
		if (right < left && right < bottom && right < top) {
			return 2;
		}
		if (bottom < right && bottom < left && bottom < top) {
			return 3;
		}
		if (left < right && left < bottom && left < top) {
			return 4;
		}
		return 0;

	}
	public Point getCM() {
		return new Point(pos.x + w/2, pos.y + h/2);
	}
	public void unIntersect(Rect r, int colType) {
		if(colType == 2) {
			this.pos.x = r.pos.x - this.w;
		}
		if(colType == 4) {
			this.pos.x = r.pos.x + r.w;
		}
		if(colType == 1) {
			this.pos.y = r.pos.y + r.h;
		}
		if(colType == 3) {
			this.pos.y = r.pos.y - this.h;
		}
	}

	public void draw(Graphics g) {
		g.drawRect((int) pos.x, (int) pos.y, (int) w, (int) h);
	}

	public Rect getCopy() {

		return new Rect(pos.x, pos.y, w, h);
	}
}