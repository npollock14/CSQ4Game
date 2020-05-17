import java.awt.Color;
import java.awt.Graphics;

public class Level3 extends Scene{
Button b;
Vec2 vel = new Vec2(Math.random() * 7, Math.random() * 7);
int size = 50;
	@Override
	public void draw(Graphics g) {
		b.draw(g);
		Driver.drawUI(g);
	}

	@Override
	public void update() {
		b.bounds.pos.add(vel);
		if(b.bounds.pos.x + size >= Driver.screenWidth) {
			vel.x *=-1;
			b.bounds.pos.x = Driver.screenWidth - size-1;
		}
		if(b.bounds.pos.x <= 0) {
			vel.x *=-1;
			b.bounds.pos.x = 1;
		}
		if(b.bounds.pos.y + size >= Driver.screenHeight) {
			vel.y *=-1;
			b.bounds.pos.y = Driver.screenHeight - size-1;
		}
		if(b.bounds.pos.y <= 0) {
			vel.y *=-1;
			b.bounds.pos.y = 1;
		}
		b.update();
		if(InputManager.mouseReleased[1]) {
		if(b.clicked) {
			Driver.nextLevel();
		}else {
			Driver.loseLife();
		}
		
		}
	}

	@Override
	public void init() {
		b = new Button(new Rect((int)(Math.random() * (Driver.screenWidth - size)), (int)(Math.random() * (Driver.screenHeight - size)), size,size),Color.green, true);
	}

}
