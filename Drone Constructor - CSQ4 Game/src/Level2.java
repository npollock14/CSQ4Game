import java.awt.Color;
import java.awt.Graphics;

public class Level2 extends Scene{
Button b;
	@Override
	public void draw(Graphics g) {
		b.draw(g);
		Driver.drawUI(g);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
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
		b = new Button(new Rect((int)(Math.random() * (Driver.screenWidth - 50)), (int)(Math.random() * (Driver.screenHeight - 50)), 50,50),Color.green, true);
	}

}
