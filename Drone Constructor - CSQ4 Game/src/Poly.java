import java.awt.Graphics2D;
import java.util.ArrayList;

public class Poly {
	ArrayList<Point> verts = new ArrayList<Point>();
	ArrayList<Segment> segs = new ArrayList<Segment>();
	ArrayList<Double> distances = new ArrayList<Double>();
	ArrayList<Double> rotations = new ArrayList<Double>();
	double rotation = Math.PI;
	Point center = new Point(0, 0);
	double area;

	public Poly(double... pts) {
		if (pts.length % 2 != 0 || pts.length < 6)
			throw new IllegalArgumentException("Polygon must have at least 3 sets of 2 points");
		for (int i = 0; i < pts.length - 1; i += 2) {
			verts.add(new Point((double) pts[i], (double) (pts[i + 1])));
		}
		for (int i = 0; i < verts.size() - 1; i++) {
			segs.add(new Segment(verts.get(i), verts.get(i + 1)));
		}
		updateCenter();
		for (int i = 0; i < verts.size(); i++) {
			distances.add(center.distanceTo(verts.get(i)));
			rotations.add(center.angleTo(verts.get(i)));
		}
	}

	public void draw(Graphics2D g, boolean drawPoints) {
		for (Segment s : segs) {
			s.draw(g, drawPoints);
			if(drawPoints) {
			g.drawString(s.getP1().toString(), (int) s.getP1().x + 10, (int) s.getP1().y + 10);
			g.drawString(s.getP2().toString(), (int) s.getP2().x + 10, (int) s.getP2().y + 10);
			}
		}
		if(drawPoints) {
		for (Point p : verts) {
			p.fillCircle(g, 10);
		}
		
		drawCenter(g, 20);
		}
	}
	
	public void deIntersectSimple(Poly p) {
		//for()
	}

	public void translate(Vec2 d) {
		for (Point p : verts) {
			p.add(d);
		}
		for (Segment s : segs) {
			s.tUpdate(d);
		}
		center.add(d);
	}

	public void rotate(double rad) { //TODO fix
		rotation += rad;
		for (int i = 0; i < verts.size(); i++) {
			verts.get(i).x = center.x + distances.get(i) * Math.cos(rotation + rotations.get(i));
			verts.get(i).y = center.y + distances.get(i) * Math.sin(rotation + rotations.get(i)); //full calculation - can be approximated
		}
		for(Segment s : segs) {
			s.rUpdate();
		}
	}
	public void rotate(double rad, Point about) { //TODO fix
		rotation += rad;
		for (int i = 0; i < verts.size(); i++) {
			verts.get(i).x = about.x + distances.get(i) * Math.cos(rotation + rotations.get(i));
			verts.get(i).y = about.y + distances.get(i) * Math.sin(rotation + rotations.get(i)); //full calculation - can be approximated
		}
		for(Segment s : segs) {
			s.rUpdate();
		}
	}

	public void updateCenter() {
		updateArea();
		double cX = 0.0;
		double cY = 0.0;
		for (int i = 0; i < verts.size() - 1; i++) {
			Point c = verts.get(i);
			Point n = verts.get(i + 1);
			cX += (c.x + n.x) * (c.x * n.y - n.x * c.y);
			cY += (c.y + n.y) * (c.x * n.y - n.x * c.y);
		}
		cX /= (6 * area);
		cY /= (6 * area);
		center.x = cX;
		center.y = cY;

	}
	public void setCenter(Point p) {
		distances.clear();
		rotations.clear();
		center.x = p.x;
		center.y = p.y;
		for (int i = 0; i < verts.size(); i++) {
			distances.add(center.distanceTo(verts.get(i)));
			rotations.add(center.angleTo(verts.get(i)));
		}
	}

	public void drawCenter(Graphics2D g, int r) {
		center.fillCircle(g, r);
		new Point(center.x - r / 2, center.y).drawLine(g, new Point(center.x + r / 2, center.y));
		new Point(center.x, center.y + r / 2).drawLine(g, new Point(center.x, center.y - r / 2));
	}

	public void updateArea() {
		double a = 0.0;
		for (int i = 0; i < verts.size() - 1; i++) {
			Point c = verts.get(i);
			Point n = verts.get(i + 1);
			a += c.x * n.y - n.x * c.y;
		}
		a /= 2.0;
		area = a;
	}

	public void addVert(int pos, Point pt) {
		verts.add(pos, new Point(pt.x, pt.y));
		segs.set(pos, new Segment(segs.get(pos).getP1(), pt));
		segs.add(new Segment(pt, segs.get(pos == segs.size() - 1 ? 0 : pos + 1).getP1()));
		updateCenter();
	}

	public int numVerts() {
		return verts.size();
	}

	public int numEdges() {
		return segs.size();
	}

	public boolean surrounds(Point p) {

		int ct = 0;
		double offset = .0001;

		for (Segment seg : segs) {
			if (p.y + offset > seg.yLow && p.y + offset < seg.yHi) {

				if ((p.y + offset - seg.getP1().y) / seg.getSlope() + seg.getP1().x + offset > p.x) {
					ct++;
				}
			}
		}
		return ct % 2 == 1;
	}

	public boolean intersects(Poly p) {
		for (Segment s1 : segs) {
			for (Segment s2 : p.segs) {
				if (s1.intersects(s2)) {
					return true;
				}
			}
		}
		if (p.surrounds(verts.get(0))) {
			return true;
		}
		if (this.surrounds(p.verts.get(0))) {
			return true;
			
		}
		return false;

	}

}

class Segment {
	private Point p1, p2;
	private double slope;
	private double b;
	private double rlen;
	private boolean vert = false;
	double yLow, yHi;

	public Segment(double x1, double y1, double x2, double y2) {
		super();
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);

		if (y1 > y2) {
			yHi = y1;
			yLow = y2;
		} else {
			yHi = y2;
			yLow = y1;
		}

		if (p1.x - p2.x == 0)
			vert = true;
		if (!vert) {
			this.slope = (p1.y - p2.y) / (p1.x - p2.x);
		} else {
			this.slope = Double.MAX_VALUE;
		}
		this.b = p1.y - slope * p1.x;

		rlen = p1.relDistanceTo(p2);
	}

	public Segment(Point p1, Point p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;

		if (p1.y > p2.y) {
			yHi = p1.y;
			yLow = p2.y;
		} else {
			yHi = p2.y;
			yLow = p1.y;
		}

		if (p1.x - p2.x == 0)
			vert = true;
		if (!vert) {
			this.slope = (p1.y - p2.y) / (p1.x - p2.x);
		} else {
			this.slope = Double.MAX_VALUE;
		}

		this.b = p1.y - slope * p1.x;
		rlen = p1.relDistanceTo(p2);
	}

	public void tUpdate(Vec2 v) {
		yLow += v.y;
		yHi += v.y;
		this.b = p1.y - slope * p1.x;
	}
	public void rUpdate() {
		if (p1.y > p2.y) {
			yHi = p1.y;
			yLow = p2.y;
		} else {
			yHi = p2.y;
			yLow = p1.y;
		}

		if (p1.x - p2.x == 0)
			vert = true;
		if (!vert) {
			this.slope = (p1.y - p2.y) / (p1.x - p2.x);
		} else {
			this.slope = Double.MAX_VALUE;
		}
		this.b = p1.y - slope * p1.x;
	}

	public void setP1(double x, double y) {
		this.p1 = new Point(x, y);
		if (p1.x - p2.x == 0) {
			vert = true;
		} else {
			vert = false;
		}
		if (p1.y > p2.y) {
			yHi = p1.y;
			yLow = p2.y;
		} else {
			yHi = p2.y;
			yLow = p1.y;
		}

		this.slope = (p1.y - p2.y) / (p1.x - p2.x);
		this.b = p1.y - slope * p1.x;

		rlen = p1.relDistanceTo(p2);
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public double getSlope() {
		return slope;
	}

	public double getB() {
		return b;
	}

	public double getRlen() {
		return rlen;
	}

	public boolean isVert() {
		return vert;
	}

	public void setP2(double x, double y) {
		this.p2 = new Point(x, y);
		if (p1.x - p2.x == 0) {
			vert = true;
		} else {
			vert = false;
		}
		
		if (p1.y > p2.y) {
			yHi = p1.y;
			yLow = p2.y;
		} else {
			yHi = p2.y;
			yLow = p1.y;
		}
		
		if (!vert) {
			this.slope = (p1.y - p2.y) / (p1.x - p2.x);
			this.b = p1.y - slope * p1.x;
		}
		rlen = p1.relDistanceTo(p2);
	}

	public void draw(Graphics2D g, boolean drawPoints) {
		p1.drawLine(g, p2);
		if (drawPoints) {
			p1.fillCircle(g, 3);
			p2.fillCircle(g, 3);
		}
	}

	public boolean intersects(Segment s) {
		if (!vert && !s.vert) {
			double x = (s.b - b) / (slope - s.slope);
			double y = slope * x + b;
			Point c = new Point(x, y);
			return p1.relDistanceTo(c) < rlen && p2.relDistanceTo(c) < rlen && s.p1.relDistanceTo(c) < s.rlen
					&& s.p2.relDistanceTo(c) < s.rlen;
		} else if (vert && s.vert) {
			return false;
		} else if (!vert && s.vert) {
			double x = s.p1.x;
			double y = slope * x + b;
			Point c = new Point(x, y);
			return p1.relDistanceTo(c) < rlen && p2.relDistanceTo(c) < rlen && s.p1.relDistanceTo(c) < s.rlen
					&& s.p2.relDistanceTo(c) < s.rlen;
		} else {
			double x = p1.x;
			double y = s.slope * x + s.b;
			Point c = new Point(x, y);
			return p1.relDistanceTo(c) < rlen && p2.relDistanceTo(c) < rlen && s.p1.relDistanceTo(c) < s.rlen
					&& s.p2.relDistanceTo(c) < s.rlen;
		}

	}

	public String toString() {
		return "<" + p1.toString() + " -> " + p2.toString() + ">";
	}

}
