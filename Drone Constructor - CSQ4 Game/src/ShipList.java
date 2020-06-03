import java.util.ArrayList;

public class ShipList {
	static EnemyShip testShip;
	static EnemyShip testShip2;
	static ArrayList<Ship> ships = new ArrayList<Ship>();
	static boolean madeShips = false;
	
	public static void makeShips() {
		if(!madeShips) {
			
			testShip = new EnemyShip(new Point(300,300));
			testShip.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
					new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 2),
					new Thruster(new Point(1, 2), 2));
			
			testShip2 = new EnemyShip(new Point(300,300));
			testShip2.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
					new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 3),
					new Thruster(new Point(1, 2), 3));
			
			
			
			ships.add(testShip);
			ships.add(testShip2);
			
			madeShips = true;
		}
	}
	public static Ship getShip(Point pos, int t) {
		Ship s = ships.get(t).clone();
		s.teleport(pos);
		return s;
		
	}

}
