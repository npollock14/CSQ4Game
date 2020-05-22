import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Ship {
	Point pos = new Point(0, 0);
	Point cm = new Point(0,0);
	Vec2 vel = new Vec2(0, 0);
	double friction = .95; //drag force = kv^2
	boolean isPlayer = false;
	double rVel = 0;
	double mass = 0.0;
	double rotation = 0;
	ArrayList<Part> parts = new ArrayList<Part>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	boolean shoot = false;
	Ship target;
	boolean destoryed = false;
	
	double[] transForces = {0.0,0.0,0.0,0.0}; //up, right, down, left - clockwise
	double rotForce = 0.0;

	public Ship(Point pos, double rotation, ArrayList<Part> parts) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH/2, pos.y);
		this.rotation = rotation;
		this.parts = parts;
	}
	public Ship(Point pos, double rotation) {
		this.pos = pos;
		this.cm = new Point(pos.x + Part.SQUARE_WIDTH/2, pos.y);
		this.rotation = rotation;
	}
	
	//commands
	public void cmdRotate(boolean clockwise) {
		rVel += (clockwise ? -1 : 1) * rotForce/mass;
	}
	public void cmdMove(int direction) { //up right down left
		if(direction == 0 || direction == 2) {
			vel.x += transForces[direction] * Math.sin(rotation - (direction * Math.PI/2)) / mass;
			vel.y -= transForces[direction] * Math.cos(rotation - (direction * Math.PI/2)) / mass;		
		}else {
			vel.x -= transForces[direction] * Math.sin(rotation - (direction * Math.PI/2)) / mass;
			vel.y += transForces[direction] * Math.cos(rotation - (direction * Math.PI/2)) / mass;	
		}
	}
	
	public void cmdRotateTo(double deg) {
		double curr = rotation;
		System.out.println(Math.toDegrees(curr));
	}
	
	
	
	public void rotate(double amt) {
		rotation += amt;
		for(Part p : parts) {
			p.bounds.rotate(amt, cm);
		}
	}
	

	public void draw(Graphics2D g) {
		for (Part p : parts) {
			g.setStroke(new BasicStroke((float) (3*Camera.scale)));
			p.draw(g, Camera.toScreen(pos), rotation, Camera.toScreen(cm));
			
		}
		g.setColor(Color.RED);
		Camera.toScreen(cm).fillCircle(g, (int) (2 * Camera.scale));
		g.setColor(Color.BLACK);
	}
	public abstract void update(Sector s);
	
	public void checkBrokenParts() {
		for(Part p : parts) {
			if(p.health <= 0) {
				mass -= p.mass;
				parts.remove(p);
				updateCM();
				checkBrokenParts();
				break;
			}
		}
	}
	
	public void checkDestroyed(Sector s) {
		if(parts.size() == 0) {
			s.ships.remove(this);
			this.destoryed = true;
		}
	}
	
	public void addPart(Part... ps) {
		double rot = rotation;
		rotate(-rot);
		for(Part p : ps) {
		double tlx = pos.x + p.pos.x * Part.SQUARE_WIDTH;
		double tly = pos.y + p.pos.y * Part.SQUARE_WIDTH;
		p.bounds = new Poly(tlx, tly, tlx + Part.SQUARE_WIDTH * p.width, tly, tlx + Part.SQUARE_WIDTH * p.width,
				tly + Part.SQUARE_WIDTH * p.height, tlx, tly + Part.SQUARE_WIDTH * p.height, tlx, tly);
		mass += p.mass;
		for(int i = 0; i< transForces.length; i++) {
			transForces[i] += p.transForces[i];
		}
		rotForce += p.rotForce;
		
		p.bounds.setCenter(cm);
		parts.add(p);
		}
		//for(double d : transForces) System.out.println(d);
		//System.out.println("New Mass: " + mass);
		double sumX = 0;
		double sumY = 0;
		for(Part p : parts) { //TODO can keep running list of these values for efficiency on big ships
			sumX += (p.getCM().x * p.mass);
			sumY += (p.getCM().y * p.mass);
		}
		cm = new Point(sumX/mass, sumY/mass);
		
		for(Part p : parts) p.bounds.setCenter(cm);
		rotate(rot);
		
	}
	public void updateCM(){
		double rot = rotation;
		rotate(-rot);
		//System.out.println("New Mass: " + mass);
		double sumX = 0;
		double sumY = 0;
		for(Part p : parts) { //TODO can keep running list of these values for efficiency on big ships
			sumX += (p.getCM().x * p.mass);
			sumY += (p.getCM().y * p.mass);
		}
		cm = new Point(sumX/mass, sumY/mass);
		
		for(Part p : parts) p.bounds.setCenter(cm);
		rotate(rot);
	}
	
	//n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2
	public int countComponents(int n, int[][] edges) { //stuff i pulled online
	    int count = n;
	 
	    int[] root = new int[n];
	    // initialize each node is an island
	    for(int i=0; i<n; i++){
	        root[i]=i;        
	    }
	 
	    for(int i=0; i<edges.length; i++){
	        int x = edges[i][0];
	        int y = edges[i][1];
	 
	        int xRoot = getRoot(root, x);
	        int yRoot = getRoot(root, y);
	 
	        if(xRoot!=yRoot){
	            count--;
	            root[xRoot]=yRoot;
	        }
	 
	    }
	 
	    return count;
	}
	 
	public int getRoot(int[] arr, int i){
	    while(arr[i]!=i){
	        arr[i]= arr[arr[i]];
	        i=arr[i];
	    }
	    return i;
	}
	
	public void shoot(Ship s) {
		shoot = true;
		target = s;
	}
	public void ceaseFire() {
		shoot = false;
	}
	
}
