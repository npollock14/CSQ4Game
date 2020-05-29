import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
	ArrayList<Long> fps = new ArrayList<Long>();
	long frameStart = 0;

	// ============== end of settings ==================

	public void paint(Graphics g) { // main paint
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);
		SceneManager.draw(g2);

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

	}

	private void init() {
		System.out.println("Initializing...");
		SceneManager.initManager();
		SceneManager.ms.init();
		SceneManager.bs.init();
		SceneManager.ms.setActive(true);
		System.out.println("Done");

	}

	// ==================code above ===========================

	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			InputManager.mPos = getMousePos();

			update();
		} catch (Exception e) {
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
		JFrame frame;
		init();

		frame = new JFrame();
		frame.setTitle("CS Q4 Game");
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
		InputManager.scroll = e.getPreciseWheelRotation();
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
