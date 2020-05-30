import java.awt.Graphics2D;

public class ScrapEntity {
	Vec2 vel;
	Point pos;
	double drag = .95;
	double rotation = 0;
	double rVel = 0;
	
	public ScrapEntity (Point pos){
		this.pos = new Point(pos.x,pos.y);
		this.vel = new Vec2(Math.random() * 5, Math.random() * 5);
		this.rVel = (Math.random() - .5) * 2 * .1;
	}
	public void draw(Graphics2D g) {
		g.rotate(rotation);
		g.drawImage(Driver.scrapIcon, (int)pos.x, (int)pos.y, 25, 25, null);
		g.rotate(-rotation);
	}
	public void update(Sector se) {
		pos.add(vel);
		rotation += rVel;
		vel.simpleMult(drag);
		for(Ship s : se.ships) {
			if(s.isPlayer) {
				if(pos.distanceTo(s.cm) < 200) {
					Vec2 v = s.cm.subtract(pos).toVec2().simpleMult(.1);
				vel.x += getSign(v.x)/(Math.pow(v.x, 2));
				vel.y += getSign(v.y)/(Math.pow(v.y, 2));
				}
				
				for(Part p : s.parts) {
					if(p.bounds.surrounds(pos)) {
						Driver.playerScrap ++;
						se.scrap.remove(this); 
					}
				}
			}
		}
	}
	public int getSign(double d) {
		return d > 0 ? 1 : -1;
	}
	

}
