import java.awt.Graphics2D;

public class ScrapEntity {
	Vec2 vel;
	Point pos;
	double drag = .975;
	double rotation = 0;
	double rVel = 0;
	boolean alive = true;
	int velRandomness = 15;
	double rotRandom = .05;
	
	public ScrapEntity (Point pos){
		this.pos = new Point(pos.x,pos.y);
		this.vel = new Vec2((Math.random()-.5) * velRandomness, (Math.random()-.5) * velRandomness);
		this.rVel = (Math.random() - .5) * 2 * rotRandom;
	}
	public void draw(Graphics2D g) {
		if(!alive) return;
		g.rotate(rotation, Camera.toXScreen(pos.x) + (int)(25/2 * Camera.scale), Camera.toYScreen(pos.y)+ (int)(25/2 * Camera.scale));
		g.drawImage(Driver.scrapIcon, Camera.toXScreen(pos.x), Camera.toYScreen(pos.y), (int)(25 * Camera.scale), (int)(25* Camera.scale), null);
		g.rotate(-rotation, Camera.toXScreen(pos.x) + (int)(25/2 * Camera.scale), Camera.toYScreen(pos.y)+ (int)(25/2 * Camera.scale));
	}
	public void update(Sector se) {
		if(!alive) return;
		pos.add(vel);
		rotation += rVel;
		vel.x *= drag;
		vel.y *= drag;
		for(Ship s : se.ships) {
			if(s.isPlayer) {
				double d = pos.distanceTo(s.cm);
				if(d < 600) {
					Vec2 v = s.cm.subtract(pos).toVec2();
					
					
				vel = vel.add(v.simpleMult(100/(d*d)));
				}
				
				for(Part p : s.parts) {
					if(p.bounds.surrounds(pos)) {
						Driver.playerScrap ++;
						alive = false;
					}
				}
			}
		}
	}
	public int getSign(double d) {
		return d > 0 ? 1 : -1;
	}
	

}
