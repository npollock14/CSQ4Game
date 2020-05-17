import java.awt.Color;
import java.awt.Graphics;

public class Level1 extends Scene {
	Button bGood;
	Button bBad;
	boolean side = Math.random() > .5;

	@Override
	public void draw(Graphics g) {
		bGood.draw(g);
		bBad.draw(g);
		
		
		Driver.drawUI(g);
	
	}

	@Override
	public void update() {
		bGood.update();
		bBad.update();
		if (bGood.clicked) {
			Driver.nextLevel();
		}
		if (bBad.clicked) {
			Driver.loseLife();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		bGood = new Button(
				new Rect(Driver.screenWidth * (side ? 0 : .5), 0, Driver.screenWidth / 2, Driver.screenHeight),
				Color.GREEN, false);
		bBad = new Button(
				new Rect(Driver.screenWidth * (side ? .5 : 0), 0, Driver.screenWidth / 2, Driver.screenHeight),
				Color.RED, false);
		//bBad.glowColor = Misc.
	}

}
