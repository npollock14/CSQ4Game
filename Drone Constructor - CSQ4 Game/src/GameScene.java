import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Robot;

import javax.swing.JFrame;

public class GameScene extends Scene { //Escape level
	Robot r;
	
	
	
	@Override
	public void draw(Graphics g) {
if(InputManager.keysToggled[32]) {
	g.setFont(Misc.fBig);
	g.setColor(Color.red);
	g.drawString("u STUCK", 300, 300);
}else {
	g.setFont(Misc.fBig);
	g.setColor(Color.GREEN);
	g.drawString("u WIN", 300, 300);
}
	}

	@Override
	public void update() {
		if (InputManager.keysToggled[32]) {
			Driver.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			Driver.frame.setAlwaysOnTop(true);
			Driver.frame.setLocation(0, 0);
			Driver.frame.setState(Frame.NORMAL);
			r.keyRelease(17);
			if (InputManager.keysToggled[80])
				r.mouseMove((int) (Driver.windowLoc.x + Driver.screenWidth / 2),
						(int) (Driver.windowLoc.y + Driver.screenHeight / 2));
		} else {
			Driver.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Driver.frame.setAlwaysOnTop(false);
		}

	}

	@Override
	public void init() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
