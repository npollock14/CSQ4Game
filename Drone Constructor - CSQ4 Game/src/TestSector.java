import java.awt.Color;
import java.awt.Graphics2D;

public class TestSector extends Sector {
	int version;
	
	public TestSector(int version, Point pos) {
		this.version = version;
		this.pos = pos;
		init();
	}

	@Override
	public void update() {
		Camera.changeScale(InputManager.scroll / 2);
		try {
			for (Ship s : ships) {
				s.update(this);
				s.applyDrag(sectorDrag);
			}
		} catch (Exception e) {

		}
		for (Projectile p : projectiles) {
			p.update();
		}
		for (ScrapEntity s : scrap) {
			s.update(this);

		}
		for (ScrapEntity s : scrap) {
			if (!s.alive) {
				scrap.remove(s);
				break;
			}

		}

		for (Projectile p : projectiles) {
			if (!p.alive) {
				projectiles.remove(p);
				break;
			}
		}
		
		

	}

	@Override
	public void draw(Graphics2D g) {
		
		for (ScrapEntity s : scrap) {
			s.draw(g);
		}

		for (Ship s : ships) {
			s.draw(g, false);
		}
		for (Projectile p : projectiles) {
			p.draw(g);
		}
		
	}

	@Override
	public void init() {
if(version == 0) { //basic enemies
	int num = Misc.rBt(2, 3);
	for(int i = 0; i < num; i++) {
		Ship e = new EnemyShip(new Point(Misc.rBt(-2000, 2000), Misc.rBt(-2000, 2000)));
		e.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
				new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 2),
				new Thruster(new Point(1, 2), 2));
		ships.add(e);
	}
}
if(version == 1) {//transport
	
}
if(version == 2) { //mining station
	
}
if(version == 3) { //special offer
	
}
	}

}
