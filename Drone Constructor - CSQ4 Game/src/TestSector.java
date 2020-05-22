import java.awt.Color;
import java.awt.Graphics2D;

public class TestSector extends Sector{

	@Override
	public void update() {
		try {
		for(Ship s : ships) {
			s.update(this);
		}
		}catch(Exception e) {
			
		}
		for(Projectile p : projectiles) {
			p.update();
			
		}
		for(Projectile p : projectiles) {
			if(!p.alive) {
				projectiles.remove(p);
				break;
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		for(Ship s : ships) {
			s.draw(g);
		}
		for(Projectile p : projectiles) {
			p.draw(g);
		}
	}

	@Override
	public void init() {
		
	}

}
