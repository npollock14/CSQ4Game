import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class StarMap extends Scene {

	ArrayList<Sector> sectors = new ArrayList<Sector>();
	Sector currSector;
	int jumpDist = 350;
	double rSkipChance = .1;
	int sX = 6;
	int sY = 4;

	public void draw(Graphics2D g) {
		g.setColor(Color.blue);

		g.setColor(Color.black);
		for (Sector s : sectors) {
			for (Sector s2 : sectors) {
				if (!s.equals(s2)) {
					if (s.pos.distanceTo(s2.pos) < jumpDist) {
						g.setStroke(new BasicStroke(1));
						s.pos.drawLine(g, s2.pos);
					}
				}
			}
		}
		for (Sector s : sectors) {

			s.pos.fillCircle(g, 5);
		}

	}

	public void init() {
		sectors.clear();
		for (double i = 0; i < sX; i++) {
			for (double j = 0; j < sY; j++) {
				if (Math.random() < rSkipChance)
					continue;
				sectors.add(new TestSector(0, new Point(Misc.gau(i * (double) (1600.0 / sX) + 300, 30),
						Misc.gau(j * (double) (800.0 / sY) + 200, 30))));
			}
		}
		currSector = sectors.get(0);
		for (Sector s : sectors) {
			if (s.pos.x < s.pos.x)
				currSector = s;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
