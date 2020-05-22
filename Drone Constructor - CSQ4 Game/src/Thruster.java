
import java.awt.Color;
import java.awt.Graphics2D;

public class Thruster extends Part {
	static int width = 1; // in blocks
	static int height = 1;
	static int baseHealth = 10;
	static double mass = 10;
	static String type = "Thruster";
	int direction;
	double force = 2.0;
	double rForce = .01;

	public Thruster(Point pos, Point sPos, Point cm, double mass, int direction) {
		super(width, height, baseHealth, pos, type, sPos, cm, mass);
		this.direction = direction;
		rotForce = rForce;
		if(direction == 0) transForces[2] = force; //flame faces up
		if(direction == 1) transForces[3] = force; //flame right
		if(direction == 2) transForces[0] = force; //flame down
		if(direction == 3) transForces[1] = force; //flame left
	}


	public Thruster(Point pos, int direction) { //up right down left 0123
		super(width, height, baseHealth, pos, type, mass);
		this.direction = direction;
		rotForce = rForce;
		if(direction == 0) transForces[2] = force; //flame faces up
		if(direction == 1) transForces[3] = force; //flame right
		if(direction == 2) transForces[0] = force; //flame down
		if(direction == 3) transForces[1] = force; //flame left
	}

	public void draw(Graphics2D g, Point sPos, double sRot, Point cm) {

		g.rotate(sRot, cm.x, cm.y);

		int x1 = (int) (sPos.x + pos.x * SQUARE_WIDTH * Camera.scale);
		int y1 = (int) (sPos.y + pos.y * Camera.scale * SQUARE_WIDTH);
		int w1 = (int) (width * Camera.scale * SQUARE_WIDTH);
		int h1 = (int) (height * Camera.scale * SQUARE_WIDTH);

		g.setColor(new Color(255, 250, 191));
		g.fillRect(x1, y1, w1, h1);
		g.setColor(new Color(255,191,89));
		g.drawRect(x1, y1, w1, h1);
		
		
		if (direction == 0) {
			g.drawLine((int) x1, (int) (y1 + SQUARE_WIDTH * Camera.scale / 4), (int) (x1 + SQUARE_WIDTH * Camera.scale),
					(int) (y1 + SQUARE_WIDTH * Camera.scale / 4));
		}
		if (direction == 1) {
			g.drawLine((int) (x1 + SQUARE_WIDTH * 3 * Camera.scale / 4), (int) (y1),
					(int) (x1 + SQUARE_WIDTH * 3 * Camera.scale / 4), (int) (y1 + SQUARE_WIDTH * Camera.scale));
		}
		if (direction == 2) {
			g.drawLine((int) x1, (int) (y1 + SQUARE_WIDTH * 3 * Camera.scale / 4),
					(int) (x1 + SQUARE_WIDTH * Camera.scale), (int) (y1 + SQUARE_WIDTH * 3 * Camera.scale / 4));
		}
		if (direction == 3) {
			g.drawLine((int) (x1 + SQUARE_WIDTH*Camera.scale/4), (int) (y1),
					(int) (x1 + SQUARE_WIDTH*Camera.scale/4), (int) (y1 + SQUARE_WIDTH * Camera.scale));
		}
		

		g.rotate(-sRot, cm.x, cm.y);

		// g.setColor(Color.green);
		// Camera.toScreen(getCM()).fillCircle(g, (int) (2*Camera.scale));
		// bounds.draw(g, false);

	}

	@Override
	public void update(Ship s) {
		// TODO Auto-generated method stub

	}

}
