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
		if(ships.size() == 1) {
			clear = true;
		}
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
	int num = Misc.rBt(2, 4);
	for(int i = 0; i < num; i++) {
		Point pos = new Point(Misc.rBt(-8000, 8000), Misc.rBt(-8000, 8000));
		while(pos.distanceTo(new Point(0,0)) < 2600) {
			pos = new Point(Misc.rBt(-8000, 8000), Misc.rBt(-8000, 8000));
		}
		ships.add(ShipList.getShip(pos, (int)(Math.random() * (ShipList.ships.size()))));
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
