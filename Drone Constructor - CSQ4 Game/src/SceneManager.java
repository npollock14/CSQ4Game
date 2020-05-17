import java.awt.Graphics;
import java.util.ArrayList;

public class SceneManager {
	static ArrayList<Scene> scenes = new ArrayList<Scene>();
	static MenuScene ms = new MenuScene(); //menu scene
	static GameScene gs = new GameScene(); //final level
	static DeathScreen ds = new DeathScreen();
	static ScreenTearingLevel stl = new ScreenTearingLevel();
	
	public static void update() {
		for (Scene s : scenes) {
			if (s.running)
				s.update();
		}
	}

	public static void draw(Graphics g) {
		for (Scene s : scenes) {
			if (s.running)
				s.draw(g);
		}
	}

	public static void initScenes(boolean all) {
		for (Scene s : scenes) {
			if (all) {
				s.init();
			} else if (!s.init) {
				s.init();
			}

		}
	}
	
	public static void setAll(boolean b) {
		for(Scene s : scenes) {
			s.setActive(b);
		}
	}
	
	
	public static void initManager() {
		scenes.add(ms);
		
//		scenes.add(new Level1());
//		scenes.add(new Level2());
//		scenes.add(new Level3());
//		scenes.add(new AngryBirds());
//		scenes.add(new AngryBirds2());
//		scenes.add(new LevelRedX());
		scenes.add(stl);
		scenes.add(gs);
		
		scenes.add(ds);
		
	}

}
