import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Window extends JPanel
		implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;
	int screenWidth;
	int screenHeight;
	 boolean[] keys = new boolean[300];
	 boolean[] keysToggled = new boolean[300];
	 boolean[] keysPressed = new boolean[300];
	 boolean[] keysReleased = new boolean[300]; 
	 boolean[] keysHeld = new boolean[300];
	 boolean[] mouse = new boolean[200];
	 boolean[] mouseReleased = new boolean[300];
	Point mPos;
	JFrame jframe;

	static Font f = new Font("Press Start", 0, 48);
	static Font fBig = new Font("Press Start", 0, 82);
	static Font font = new Font("Courier New", 1, 25);
	static Font titleFont = new Font("Dialog", 3, 50);
	static Font smallTitleFont = new Font("Dialog", 3, 30);
	static Font smallestTitleFont = new Font("Dialog", 1, 17);
	static Font fpsFont = new Font("Impact", 1, 25);
	static Font fancyTitleFont = new Font("TimesRoman", 3, 82);
	ScreenTearingRSWV rw; 
	
	// ============== end of settings ==================

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.translate(-Driver.screenWidth/2, 0);
		rw.draw(g);
		
	}
	
	
	

	public void update() throws InterruptedException {
		mPos = getMousePos();
		
		rw.update();

		updateKeysHeld();
		updateKeysPressed();
		for(int i = 0; i < mouseReleased.length; i++) {
			mouseReleased[i] = false;
		}
		for(boolean b : keysReleased) {
			b = false;
		}

	}

	private void updateKeysPressed() {

		for(int i = 0; i < keysPressed.length; i++) {
			if(keys[i] && !keysPressed[i]) {
				keysPressed[i] = true;
			}else if(keysHeld[i]) {
				keysPressed[i] = false;
			}
		}
		
	}

	private void updateKeysHeld() {
		for (int i = 0; i < keysHeld.length; i++) {
			if (keys[i]) {
				keysHeld[i] = true;
			} else {
				keysHeld[i] = false;
			}

		}
	}

	private void init() {

		rw = new ScreenTearingRSWV();
		rw.init();

	}

	public Point getMousePos() {
		try {
			return new Point(this.getMousePosition().x, this.getMousePosition().y);
		} catch (Exception e) {
			return new Point(0,0);
		}
	}

	// ==================code above ===========================

	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			update();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();

	}

	

	public Window(int screenWidth, int screenHeight, int xPos, int yPos) {

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		init();

		jframe = new JFrame();
		jframe.setTitle("Window");
		jframe.setSize(screenWidth, screenHeight);
		jframe.setBackground(Color.BLACK);
		jframe.setResizable(false);
		jframe.addKeyListener(this);
		jframe.addMouseMotionListener(this);
		jframe.addMouseWheelListener(this);
		jframe.addMouseListener(this);
		jframe.setAlwaysOnTop(true);
		jframe.setLocation(xPos, yPos);

		jframe.add(this);

		t = new Timer(14, this);
		t.start();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		keysReleased[e.getKeyCode()] = true;

		keys[e.getKeyCode()] = false;

		if (keysToggled[e.getKeyCode()]) {
			keysToggled[e.getKeyCode()] = false;
		} else {
			keysToggled[e.getKeyCode()] = true;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse[e.getButton()] = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse[e.getButton()] = false;
		mouseReleased[e.getButton()] = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouse[e.getButton()] = true;

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}

