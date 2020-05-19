import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Scene {

	boolean running = false;
	boolean drawing = false;
	boolean init = false;

	public abstract void draw(Graphics2D g);

	public abstract void update();

	public abstract void init();

	public void setActive(boolean b) {
		this.running = b;
		this.drawing = b;
	}
	
	
}
