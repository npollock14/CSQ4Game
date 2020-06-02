import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class StarMap extends Scene {

	ArrayList<Sector> sectors = new ArrayList<Sector>();
	Sector currSector;
	Sector endSector;
	int jumpDist = 350;
	double rSkipChance = .1;
	int sX = 6;
	int sY = 5;

	public void draw(Graphics2D g) {

		g.setColor(Color.black);
		for (Sector s : sectors) {
				if (!s.equals(currSector)) {
					if (s.pos.distanceTo(currSector.pos) < jumpDist) {
						g.setStroke(new BasicStroke(1));
						currSector.pos.drawLine(g, s.pos);
					}
				}
			}
		
		for (Sector s : sectors) {
			s.pos.fillCircle(g, 5);
		}
		g.setColor(Color.GREEN);
		currSector.pos.fillCircle(g, 10);
		g.setColor(Color.blue);
		endSector.pos.fillCircle(g, 10);
		g.setColor(Color.BLACK);
		g.setFont(Misc.arialSmall);
		g.drawString("YOU", (int)currSector.pos.x, (int)currSector.pos.y - 30);
		g.drawString("FINISH", (int)endSector.pos.x, (int)endSector.pos.y - 30);
		

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
		endSector = sectors.get(0);
		for (Sector s : sectors) {
			if (s.pos.x < currSector.pos.x)
				currSector = s;
			if(s.pos.x > endSector.pos.x)
				endSector = s;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
