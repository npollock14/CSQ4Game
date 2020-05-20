import java.awt.Graphics2D;

public class TestSector extends Sector{

	@Override
	public void update() {
		for(Ship s : ships) {
			s.update(this);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		for(Ship s : ships) {
			s.draw(g);
		}
	}

	@Override
	public void init() {
		
	}

}
