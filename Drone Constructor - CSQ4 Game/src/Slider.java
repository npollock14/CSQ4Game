import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Slider {
	Button b;
	double value;
	Point pos;
	int w;
	int bw, bh;
	boolean moving;
	boolean vertical = false;

	public Slider(Point pos, int w, Color c, int bw, int bh, double initValue, boolean vertical) {
		super();
		this.pos = pos;
		this.w = w;
		if (initValue < 0 || initValue > 1)
			throw new IllegalArgumentException("Initial Value Out of Range Exception");
		b = new Button(new Rect((int) (((double) w * initValue) + pos.x - bw / 2), pos.y - bh / 2, bw, bh), null, 1,
				null, Driver.fb, c, false, false);
		this.bw = bw;
		this.bh = bh;
		this.vertical = vertical;
	}

	public void update() {
		b.update();
		if (!vertical) {
			if (b.clicked || moving) {
				b.bounds = new Rect(InputManager.mPos.x - bw / 2, pos.y - bh / 2, bw, bh);
				if (b.bounds.pos.x > pos.x + w - bw / 2)
					b.bounds.pos.x = pos.x + w - bw / 2;
				if (b.bounds.pos.x < pos.x - bw / 2)
					b.bounds.pos.x = pos.x - bw / 2;
				value = (((double) b.bounds.pos.x + bw / 2 - (double) pos.x) / (double) w);
				moving = InputManager.mouse[1];
			}
		} else {
			if (b.clicked || moving) { //TODO Vertical not implemented
				b.bounds = new Rect(InputManager.mPos.x - bw / 2, pos.y - bh / 2, bw, bh);
				if (b.bounds.pos.x > pos.x + w - bw / 2)
					b.bounds.pos.x = pos.x + w - bw / 2;
				if (b.bounds.pos.x < pos.x - bw / 2)
					b.bounds.pos.x = pos.x - bw / 2;
				value = (((double) b.bounds.pos.x + bw / 2 - (double) pos.x) / (double) w);
				moving = InputManager.mouse[1];
			}
		}

	}

	public void draw(Graphics g) {

		if (!vertical)
			g.drawLine((int) pos.x, (int) pos.y, (int) (pos.x + w), (int) (pos.y));
		if (vertical)
			g.drawLine((int) pos.x, (int) pos.y, (int) (pos.x), (int) (pos.y + w));
		b.draw(g, 0, 0);
	}

}
