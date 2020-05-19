public class Camera {
	public static double xOff = 0;
	public static double yOff = 0;
	public static int screenW = Driver.screenWidth; 
	public static int screenH = Driver.screenHeight;
	public static double scale = 1;
	public static Point center = new Point(Driver.screenWidth / 2, Driver.screenHeight / 2);
	public static float scaleNotches = 0;

	public static void focus(Point p) {
		// p = map coordinates
		// place point p in center of screen & determine how displaced that was
		xOff = screenW / 2 - p.x;
		yOff = screenH / 2 - p.y;

	}

	public static void changeScale(double scroll) {
		scaleNotches += scroll;
		scale = Math.pow(2, scaleNotches);
	}

	public static int toXScreen(double x) {
		int dx = (int) ((x + xOff - center.x) * scale);
		return (int) (center.x + dx);

	}

	public static int toYScreen(double y) {
		int dy = (int) ((y + yOff - center.y) * scale);
		return (int) (center.y + dy);

	}
	
	public static Point toScreen(Point p) {
		int dx = (int) ((p.x + xOff - center.x) * scale);
		int x = (int) (center.x + dx);
		int dy = (int) ((p.y + yOff - center.y) * scale);
		int y = (int) (center.y + dy);
		return new Point(x,y);
	}

	public static double toXMap(double x) {
		return ((x - center.x) / scale) + center.x - xOff;

	}

	public static double toYMap(double y) {
		return ((y - center.y) / scale) + center.y - yOff;

	}

}