
import java.awt.Graphics2D;
import java.util.ArrayList;

public class SceneManager {
	static ArrayList<Scene> scenes = new ArrayList<Scene>();
	static BattleScene ms = new BattleScene(); //menu scene
	static BuildScene bs = new BuildScene(); //build scene
	static StarMap sm = new StarMap(); //build scene
	static HyperspaceScene hs = new HyperspaceScene();
	
	public static void update() {
		for (Scene s : scenes) {
			if (s.running)
				s.update();
		}
	}

	public static void draw(Graphics2D g) {
		for (Scene s : scenes) {
			if (s.drawing)
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
		scenes.add(bs);
		scenes.add(sm);
		scenes.add(hs);
	}

}
