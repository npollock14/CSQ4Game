
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Button {
	Rect bounds;
	boolean circle = false;
	boolean pressing = false;
	BufferedImage img;
	int centeringMethod;
	boolean clicked, glowing, glow;
	String text = "";
	Font f = new Font("Arial", 0, 12);
	Color c;
	Color glowColor = Color.blue;

	public Button(Rect bounds, BufferedImage img, int centeringMethod, String text, Font f, Color c, boolean glow,
			boolean circle) {
		super();
		this.circle = circle;
		this.bounds = bounds;
		this.img = img;
		this.centeringMethod = centeringMethod;
		this.text = text;
		this.f = f;
		this.c = c;
		this.glow = glow;
	}

	public Button(Rect bounds, Color c, boolean circle) { // quick button
		super();
		this.circle = circle;
		this.bounds = bounds;
		this.c = c;
	}

	public void update() {
		if (circle) {
			
			if(InputManager.mouse[1] && InputManager.mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2, bounds.pos.y + bounds.w / 2)) < bounds.w / 2) {
				pressing = true;
			}else if(!InputManager.mouse[1]){
				pressing = false;
			}
			
			if ((InputManager.mouseReleased[1]) && InputManager.mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2, bounds.pos.y + bounds.w / 2)) < bounds.w / 2) {
				clicked = true;
			} else {
				clicked = false;
			}
			if (InputManager.mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2, bounds.pos.y + bounds.w / 2)) < bounds.w / 2
					&& glow) {// TODO inside/test the section
				glowing = true;
			} else {
				glowing = false;
			}
		}
		if (!circle) {
			if (InputManager.mouse[1] && InputManager.mPos.inside(bounds,false)) {
				pressing = true;
			} else if(!InputManager.mouse[1]){
				pressing = false;
			}
			if (InputManager.mouseReleased[1] && InputManager.mPos.inside(bounds,false)) {
				clicked = true;
			} else {
				clicked = false;
			}
			if (InputManager.mPos.inside(bounds,false) && glow) {
				glowing = true;
			} else {
				glowing = false;
			}
		}
	}
	public void update(boolean[] mouseReleased, boolean[] mouse, Point mPos, int xOff, int yOff) {
		if (circle) {
			
			if(mouse[1] && mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2 - xOff, bounds.pos.y + bounds.w / 2 - yOff)) < bounds.w / 2) {
				pressing = true;
			}else if(!mouse[1]){
				pressing = false;
			}
//			System.out.println("Distance: " + mPos
//					.distanceTo(new Point(bounds.pos.x + bounds.w / 2 - xOff, bounds.pos.y + bounds.w / 2 - yOff)));
			if ((mouseReleased[1]) && mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2 - xOff, bounds.pos.y + bounds.w / 2 - yOff)) < bounds.w / 2) {
				clicked = true;
				//System.out.println("clicked");
			} else {
				clicked = false;
			}
			if (mPos
					.distanceTo(new Point(bounds.pos.x + bounds.w / 2 - xOff, bounds.pos.y + bounds.w / 2 - yOff)) < bounds.w / 2
					&& glow) {// TODO inside/test the section
				glowing = true;
			} else {
				glowing = false;
			}
		}
		if (!circle) {
			if (mouse[1] && mPos.inside(bounds,false)) {
				pressing = true;
			} else if(!mouse[1]){
				pressing = false;
			}
			if (mouseReleased[1] && mPos.inside(bounds,false)) {
				clicked = true;
			} else {
				clicked = false;
			}
			if (mPos.inside(bounds,false) && glow) {
				glowing = true;
			} else {
				glowing = false;
			}
		}
	}

	public void draw(Graphics g, int xOff, int yOff) {
		if (c != null) {
			g.setColor(c);
		} else {
			g.setColor(Color.white);
		}
		if (glowing) g.setColor(glowColor);
		if(circle) {
		g.fillOval((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
		g.setColor(Color.black);
		g.drawOval((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
		}else {
			g.fillRect((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
			g.setColor(Color.black);
			g.drawRect((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
		}
		if (img != null)
			g.drawImage(img, (int) bounds.pos.x, (int) bounds.pos.y,
					bounds.w < bounds.h ? (int) bounds.w : (int) bounds.h,
					bounds.w < bounds.h ? (int) bounds.w : (int) bounds.h, null);
		if (text != null)
			g.drawString(text, (int) (bounds.pos.x + xOff), (int) (bounds.pos.y + yOff));
	}
	public void draw(Graphics g) {
		if (c != null) {
			g.setColor(c);
		} else {
			g.setColor(Color.white);
		}
		if (glowing) g.setColor(glowColor);
		if(circle) {
			g.fillOval((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
			g.setColor(Color.black);
			g.drawOval((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
			}else {
				g.fillRect((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
				g.setColor(Color.black);
				g.drawRect((int) bounds.pos.x, (int) bounds.pos.y, (int) bounds.w, (int) bounds.h);
			}
		if (img != null)
			g.drawImage(img, (int) bounds.pos.x, (int) bounds.pos.y,
					bounds.w < bounds.h ? (int) bounds.w : (int) bounds.h,
					bounds.w < bounds.h ? (int) bounds.w : (int) bounds.h, null);
	}

}