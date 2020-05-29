import java.awt.Color;
import java.awt.Graphics2D;

public class TestSector extends Sector{
double sectorDrag = .992;
	@Override
	public void update() {
		Camera.changeScale(InputManager.scroll / 2);
		try {
		for(Ship s : ships) {
			s.update(this);
			s.applyDrag(sectorDrag);
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
			s.draw(g,false);
		}
		for(Projectile p : projectiles) {
			p.draw(g);
		}
	}

	@Override
	public void init() {
		
	}

}
