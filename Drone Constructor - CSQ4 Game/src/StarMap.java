import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StarMap extends Scene {

	ArrayList<Sector> sectors = new ArrayList<Sector>();
	Sector currSector;
	Sector endSector;
	Sector selected;
	Sector hover;
	int jumpDist = 350;
	double rSkipChance = .1;
	int sX = 6;
	int sY = 5;
	
	BufferedImage star = Misc.loadImage("/star.png");
	
	Ship player;

	Button jump;

	public void draw(Graphics2D g) {
		g.setColor(new Color(30,30,30));
		g.fillRect(0, 0, Driver.screenWidth, Driver.screenHeight);

		g.setColor(Color.white);
		for (Sector s : sectors) {
			if (!s.equals(currSector)) {
				if (s.pos.distanceTo(currSector.pos) < jumpDist) {
					g.setStroke(new BasicStroke(1));
					currSector.pos.drawLine(g, s.pos);
				}
			}
		}

		for (Sector s : sectors) {
			g.drawImage(star, (int)s.pos.x - star.getWidth()/4, (int)s.pos.y - star.getHeight()/4,star.getWidth()/2, star.getHeight()/2, null);
		}
		g.setStroke(new BasicStroke(6));
		g.setColor(Color.GREEN);
		currSector.pos.drawCircle(g, 30);
		g.setColor(Color.blue);
		endSector.pos.drawCircle(g, 30);
		g.setColor(Color.white);
		g.setFont(Misc.arialSmall);
		g.drawString("YOU", (int) currSector.pos.x, (int) currSector.pos.y - 30);
		g.drawString("FINISH", (int) endSector.pos.x, (int) endSector.pos.y - 30);
//System.out.println(selected == null);
		if (selected != null && selected.pos.distanceTo(currSector.pos) < jumpDist)
			{
			jump.draw(g);
			g.setFont(Misc.arialBig);
			g.setColor(Color.white);
			g.drawString("JUMP", 1920/2 - 100, 1010 - 80);
			g.setStroke(new BasicStroke(6));
			g.setColor(Color.CYAN);
			selected.pos.drawCircle(g, 30);
			currSector.pos.drawDashedLine(g, selected.pos);
			}
		if(hover != null) {
			g.setStroke(new BasicStroke(6));
			g.setColor(Color.white);
			hover.pos.drawCircle(g, 30);
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
		endSector = sectors.get(0);
		for (Sector s : sectors) {
			if (s.pos.x < currSector.pos.x)
				currSector = s;
			if (s.pos.x > endSector.pos.x)
				endSector = s;
		}

		jump = new Button(new Rect(820,860,300,100), null, 0, "JUMP", null, Color.gray, true, false);
	}

	@Override
	public void update() {
		
		if(InputManager.keysReleased[27]) {
			SceneManager.sm.setActive(false);
			SceneManager.ms.setActive(true);
		}
		if(selected != null) jump.update();
		
		if (InputManager.mouseReleased[1] && !jump.clicked) {
			for (Sector s : sectors) {
				if (InputManager.mPos.distanceTo(s.pos) < 50 && s != currSector) {
					selected = s;
					break;
				}
				selected = null;
			}
			
		}
		for (Sector s : sectors) {
			if (InputManager.mPos.distanceTo(s.pos) < 50 && s != currSector) {
				hover = s;
				break;
			}
			hover = null;
		}

		if(jump.clicked && selected != null) {
			if(selected.pos.distanceTo(currSector.pos) < jumpDist) {
				currSector.ships.remove(player);
				currSector = selected;
				player.teleport(new Point(0,0));
				player.vel = new Vec2(0,0);
				player.rVel = 0.0;
				currSector.ships.add(player);
				this.running = false;
				SceneManager.hs.init();
				SceneManager.hs.setActive(true);
			}
		}
	}
}
