import java.awt.Color;
import java.awt.Graphics2D;

public class LoseScene extends Scene{

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(32,32,32));
		g.fillRect(0, 0, 1920, 1100);
		g.setColor(Color.white);
		g.setFont(Misc.fancyTitleFont);
		g.drawString("YOU LOST         ):", 500, Driver.screenHeight/2);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
