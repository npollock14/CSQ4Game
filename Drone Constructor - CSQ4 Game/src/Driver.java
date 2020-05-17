import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel
		implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	static Font fb = new Font("Press Start", 0, 48);
	static Font fm = new Font("Press Start", 0, 20);
	static Font fs = new Font("Press Start", 0, 11);
	static int screenWidth = 1920;
	static int screenHeight = 1040;
	static Point windowLoc = new Point();
	static JFrame frame;
	static int level = 0;
	
	Image test;
	
	ArrayList<Long> fps = new ArrayList<Long>();
	long frameStart = 0;
	static boolean invisCursor = false;
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new java.awt.Point(0, 0),
			"blank cursor");
	Cursor def = Cursor.getDefaultCursor();
	static ArrayList<Boolean> changeInvis = new ArrayList<Boolean>();

	static int lives = 3;
	
	static long gameTimer = 0;

	// ============== end of settings ==================

	public void paint(Graphics g) { // main paint		
		super.paintComponent(g);
		SceneManager.draw(g);
		
		g.drawImage(test, 0, 0, this);

		g.setFont(fs);
		fps.add((long) (1 / ((System.nanoTime() - frameStart) / Math.pow(10, 9))));
		if (fps.size() >= 60) {
			fps.remove(0);
		}
		double sum = 0;
		for (Long d : fps) {
			sum += d;
		}
		double avg = sum / fps.size();
		g.drawString((int) avg + "", screenWidth - 50, 25);
		frameStart = System.nanoTime();

	}

	public void update() throws InterruptedException { // main update
		SceneManager.update();
		windowLoc.setXY(frame.getLocation().x, frame.getLocation().y);
		if (changeInvis.size() >= 1) {
			setCursorInvis(changeInvis.get(0));
			changeInvis.clear();
		}
		// System.out.println(windowLoc.toString());

	}

	public void setCursorInvis(boolean b) {
		invisCursor = b;
		this.setCursor(b ? blankCursor : def);
	}

	private void init() {
		SceneManager.initManager();
		SceneManager.ms.init();
		SceneManager.ms.setActive(true);
		
		try {
			test = ImageIO.read(new File(getClass().getResource("/test.gif").getFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ==================code above ===========================

	public static void loseLife() {
		lives--;
		if(lives == 0) {
			lives = 3;
			SceneManager.setAll(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SceneManager.ds.init();
			SceneManager.ds.setActive(true);
			
		}
	}
	
	public static void drawUI(Graphics g) {
		g.setFont(Misc.titleFont);
		g.drawString("Level: " + Driver.level, Driver.screenWidth - 300, 80);
		g.setFont(Misc.fancyTitleFont);
		g.drawString("" + (System.currentTimeMillis() - Driver.gameTimer) / 1000.0, Driver.screenWidth - 300, 150);
		g.drawString(lives + " Lives", Driver.screenWidth - 300, 300);
	}

	public static void nextLevel() {
		InputManager.mouseReleased[1] = false;
		level++;
		for (int i = 0; i < SceneManager.scenes.size(); i++) {
			if (SceneManager.scenes.get(i).drawing) {
				SceneManager.scenes.get(i).setActive(false);
				SceneManager.scenes.get(i + 1).init();
				SceneManager.scenes.get(i + 1).setActive(true);
				return;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			InputManager.mPos = getMousePos();

			update();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
		InputManager.update();

	}

	public Point getMousePos() {
		try {
			return new Point(this.getMousePosition().x, this.getMousePosition().y);
		} catch (Exception e) {
			return InputManager.mPos;
		}
	}

	public static void main(String[] arg) {
		@SuppressWarnings("unused")
		Driver d = new Driver();
	}

	public Driver() {

		init();

		frame = new JFrame();
		frame.setTitle("CS Q3 Game");
		frame.setSize(screenWidth, screenHeight);
		frame.setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.addKeyListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
		frame.addMouseListener(this);

		frame.add(this);

		t = new Timer(15, this);
		t.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {
		InputManager.keys[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		InputManager.setKeyReleased(e.getKeyCode());
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
		InputManager.mouse[e.getButton()] = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		InputManager.mouse[e.getButton()] = false;
		InputManager.mouseReleased[e.getButton()] = true;
		InputManager.resetMouseReleased = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		InputManager.mouse[e.getButton()] = true;

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
