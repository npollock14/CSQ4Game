import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Ship {
	Point pos = new Point(0, 0);
	Point cm = new Point(0, 0);
	Vec2 vel = new Vec2(0, 0);
	double friction = .95; // drag force = kv^2
	boolean isPlayer = false;
	double rVel = 0;
	double mass = 0.0;
	double rotation = 0;
	ArrayList<Part> parts = new ArrayList<Part>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	boolean shoot = false;
	Ship target;
	Point pTarget;
	boolean destroyed = false;

	double[] transForces = { 0.0, 0.0, 0.0, 0.0 }; // up, right, down, left - clockwise
	double rotForce = 0.0;

	public Ship(Point pos, double rotation, ArrayList<Part> parts) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH / 2, pos.y);
		this.rotation = rotation;
		this.parts = parts;
		this.addPart(new Reactor());
	}

	public Ship(Point pos, double rotation) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH / 2, pos.y);
		this.rotation = rotation;
		this.addPart(new Reactor());
	}

	// commands
	public void cmdRotate(boolean clockwise) {
		rVel += (clockwise ? 1 : -1) * rotForce / mass;
	}
	
	

	public void cmdMove(int direction, double pow) { // up right down left
		//if(pow > 1 || pow < 0) throw new IllegalArgumentException("Power > 1 or < 0");
		if (direction == 0 || direction == 2) {
			vel.x += pow*(transForces[direction] * Math.sin(rotation - (direction * Math.PI / 2)) / mass);
			vel.y -= pow*(transForces[direction] * Math.cos(rotation - (direction * Math.PI / 2)) / mass);
		} else {
			vel.x -= pow*(transForces[direction] * Math.sin(rotation - (direction * Math.PI / 2)) / mass);
			vel.y += pow*(transForces[direction] * Math.cos(rotation - (direction * Math.PI / 2)) / mass);
		}
	}
	public void applyDrag(double coeff) {
		vel.x *= coeff;
		vel.y *= coeff;
		rVel *= coeff;
	}
	
	public void cmdMoveTo(Point p) {
		double mag = vel.getMagnitude();
		double angle = this.cm.angleTo(p) - Math.PI/2;
		double distance = cm.distanceTo(p);
		cmdRotateTo(angle);
		if(Math.abs(getAngleDiff(rotation, angle)) < Math.toRadians(30)) {
			cmdMove(0, 1.0);
		}
		}
	public void cmdStopTranslate() {
		//cmdMove(0,.1);
		if(Math.abs(vel.x) < .1) vel.x = 0;
		if(Math.abs(vel.y) < .1) vel.y = 0;
		if(vel.x == 0 && vel.y == 0) return;
		Vec2 dir = vel.simpleMult(-1);
		if(vel.y != 0) {
			
		}
		double angle = dir.getAngle();
		if(dir.x < -.1 && dir.y < -.1) {
			System.out.println("here");
			cmdMove(3, -Math.cos(angle));
			cmdMove(0, -Math.sin(angle));
		}
		
		
		
	}

	public void cmdRotateTo(double angle) {
		double aDiff = getAngleDiff(rotation, angle);
		int sign = rVel > 0 ? -1 : 1;
		double timeToStop = rVel/(-sign*rotForce/mass);
		double delta = rVel * timeToStop + (sign * .5 * (rotForce/mass) * timeToStop * timeToStop);
		
		if(Math.abs(aDiff - delta) < Math.toRadians(5)) {
			cmdStopRotate();
		
		}else {
			cmdRotate(aDiff > 0);
		}
		
		
			
		

	}

	public static double getAngleDiff(double a1, double a2) { // from a1 to a2 - (final - initial) = (a2 - a1)
		a1 %= (Math.PI * 2);
		a2 %= (Math.PI * 2);
		if (Math.abs(a2 - a1) < Math.toRadians(180)) {
			return a2 - a1;
		} else if (a1 > a2) {
			return (a2 + (Math.PI * 2)) - a1;
		}
		return (a2 - (a1 + (Math.PI * 2)));
	}

	public double positiveLowQuadratic(double a, double b, double c) {
		double t1 = -b / (2 * a) + (Math.sqrt(b * b - 4 * a * c) / (2 * a));
		double t2 = -b / (2 * a) - (Math.sqrt(b * b - 4 * a * c) / (2 * a));
		if (t1 > 0 && t2 > 0)
			return t1 > t2 ? t2 : t1;
		if (t1 < 0 && t2 < 0)
			return Double.NaN;
		if (t1 > 0)
			return t1;
		return t2;
	}

	public void cmdStopRotate() {
		if (Math.abs(rVel) > .001) {
			cmdRotate(rVel < 0);
		} else {
			rVel = 0.0;
		}
	}

	public void rotate(double amt) {
		rotation += amt;
		for (Part p : parts) {
			p.bounds.rotate(amt, cm);
		}
	}

	public void draw(Graphics2D g) {
		for (Part p : parts) {
			g.setStroke(new BasicStroke((float) (3 * Camera.scale)));
			p.draw(g, Camera.toScreen(pos), rotation, Camera.toScreen(cm));

		}
		g.setColor(Color.RED);
		Camera.toScreen(cm).fillCircle(g, (int) (2 * Camera.scale));
		g.setColor(Color.BLACK);
	}

	public abstract void update(Sector s);

	public void checkBrokenParts() {
		for (Part p : parts) {
			if (p.health <= 0) {
				mass -= p.mass;
				if (p.type.equals("Reactor"))
					destroyed = true;
				parts.remove(p);
				updateCM();
				checkBrokenParts();
				//checkDisconnectedParts();
				break;
			}
		}
	}

	public void checkDisconnectedParts() {
		//wow this is a lot of loops and probably really inefficient
		ArrayList<Node> blocked = new ArrayList<Node>();
		ArrayList<Point> unblocked = new ArrayList<Point>();
		Grid gr = new Grid(blocked);
		for(Part p : parts) {
			for(int i = 0; i < p.width; i++) {
				for(int j = 0; j < p.height; j++) {
					unblocked.add(new Point(p.pos.x + i,p.pos.y + j));
				}
			}
		}
		
		for(int i = -3; i < 3; i++) {
			for(int j = -3; j < 3; j++) {
			for(Point p : unblocked) {
				if(!new Point(i,j).isSame(p)) {
					gr.blocked.add(gr.makeBlockerNode(i, j));
				}else {
					p.print();
				}
			}
			}
		}
//		for(Node n : gr.blocked) {
//			System.out.println(n.pos.toString());
//		}
		for(int i = 0; i < parts.size(); i++) {
			if(!parts.get(i).type.equals("Reactor")) {
				gr.getPath(new Point(0,0), parts.get(i).pos);
				if(gr.pathTree.size() == 0) {
					parts.remove(parts.get(i));
				}
				gr.pathTree.clear();
			}
		}
		
		
		
	}
	
	
	public void checkDestroyed(Sector s) {
		if (destroyed == true || parts.size() == 0) {
			s.ships.remove(this);
			this.destroyed = true;
		}
	}

	public boolean canPlace(Part n) { 
		for (Part p : parts) {
			Rect nBounds = new Rect(n.pos.x, n.pos.y, n.width, n.height);
			Rect pBounds = new Rect(p.pos.x, p.pos.y, p.width, p.height);
			if (nBounds.getCM().inside(pBounds, true)) {
				System.out.println(n.type + " @ " + n.pos.toString() + " could not be placed");
				return false;
			}

		}
		return true;
	}

	public void addPart(Part... ps) {
		double rot = rotation;
		rotate(-rot);
		for (Part p : ps) {
			if (canPlace(p)) {
				double tlx = pos.x + p.pos.x * Part.SQUARE_WIDTH;
				double tly = pos.y + p.pos.y * Part.SQUARE_WIDTH;
				p.bounds = new Poly(tlx, tly, tlx + Part.SQUARE_WIDTH * p.width, tly, tlx + Part.SQUARE_WIDTH * p.width,
						tly + Part.SQUARE_WIDTH * p.height, tlx, tly + Part.SQUARE_WIDTH * p.height, tlx, tly);
				mass += p.mass;
				for (int i = 0; i < transForces.length; i++) {
					transForces[i] += p.transForces[i];
				}
				rotForce += p.rotForce;

				p.bounds.setCenter(cm);
				parts.add(p);
				System.out.println(p.type + " @ " + p.pos.toString() + " placed");
			}
		}
		// for(double d : transForces) System.out.println(d);
		// System.out.println("New Mass: " + mass);
		double sumX = 0;
		double sumY = 0;
		for (Part p : parts) { // TODO can keep running list of these values for efficiency on big ships
			sumX += (p.getCM().x * p.mass);
			sumY += (p.getCM().y * p.mass);
		}
		cm = new Point(sumX / mass, sumY / mass);

		for (Part p : parts)
			p.bounds.setCenter(cm);
		rotate(rot);

	}

	public void updateCM() {
		double rot = rotation;
		rotate(-rot);
		// System.out.println("New Mass: " + mass);
		double sumX = 0;
		double sumY = 0;
		for (Part p : parts) { // TODO can keep running list of these values for efficiency on big ships
			sumX += (p.getCM().x * p.mass);
			sumY += (p.getCM().y * p.mass);
		}
		cm = new Point(sumX / mass, sumY / mass);

		for (Part p : parts)
			p.bounds.setCenter(cm);
		rotate(rot);
	}

	// n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2
	public int countComponents(int n, int[][] edges) { // stuff i pulled online
		int count = n;

		int[] root = new int[n];
		// initialize each node is an island
		for (int i = 0; i < n; i++) {
			root[i] = i;
		}

		for (int i = 0; i < edges.length; i++) {
			int x = edges[i][0];
			int y = edges[i][1];

			int xRoot = getRoot(root, x);
			int yRoot = getRoot(root, y);

			if (xRoot != yRoot) {
				count--;
				root[xRoot] = yRoot;
			}

		}

		return count;
	}

	public int getRoot(int[] arr, int i) {
		while (arr[i] != i) {
			arr[i] = arr[arr[i]];
			i = arr[i];
		}
		return i;
	}

	public void shoot(Ship s) {
		shoot = true;
		target = s;
	}

	public void shoot(Point p) {
		shoot = true;
		pTarget = new Point(p.x, p.y);
	}

	public void ceaseFire() {
		shoot = false;
		target = null;
		pTarget = null;
	}

}
