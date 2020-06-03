import java.util.ArrayList;

public class ShipList {
	static EnemyShip testShip;
	static EnemyShip testShip2;
	static EnemyShip twinGuns;
	static ArrayList<Ship> ships = new ArrayList<Ship>();
	static boolean madeShips = false;

	public static void makeShips() {
		if (!madeShips) {

			testShip = new EnemyShip(new Point(300, 300));
			// testShip.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new
			// Armor(new Point(-1, 0)),
			// new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new
			// Point(-1, 2), 2),
			// new Thruster(new Point(1, 2), 2));

			testShip.addPart(new Armor(new Point(-1, 0)), new Laser(new Point(0, -2), 0),
					new Thruster(new Point(-1, 2), 2), new Thruster(new Point(1, 2), 2));

			testShip2 = new EnemyShip(new Point(300, 300));
			testShip2.addPart(new Armor(new Point(0, 0)), new Armor(new Point(1, 0)), new Armor(new Point(-1, 0)),
					new Armor(new Point(0, 1)), new Laser(new Point(0, -2), 0), new Thruster(new Point(-1, 2), 3),
					new Thruster(new Point(1, 2), 3));

			twinGuns = new EnemyShip(new Point(0, 0));
			twinGuns.addPart(new Hull(new Point(-1, 0)), new Hull(new Point(-1, 1)), new Hull(new Point(2, 0)),
					new Hull(new Point(2, 1)), new Hull(new Point(1, -1)), new Armor(new Point(3, 0)),
					new Armor(new Point(3, 1)), new Armor(new Point(-2, 0)), new Armor(new Point(-2, 1)),
					new Laser(new Point(-1, -2), 0), new Laser(new Point(2, -2), 0), new Hull(new Point(0, -1)),
					new Armor(new Point(0, -2)), new Armor(new Point(1, -2)), new Armor(new Point(-1, 2)),
					new Armor(new Point(2, 2)), new Hull(new Point(1, 2)), new Hull(new Point(0, 2)),
					new Thruster(new Point(-1, 3), 2), new Thruster(new Point(0, 3), 2),
					new Thruster(new Point(1, 3), 2), new Thruster(new Point(2, 3), 2));

			ships.add(twinGuns);
			// ships.add(testShip2);

			madeShips = true;
		}
	}

	public static Ship getShip(Point pos, int t) {
		Ship s = ships.get(t).clone();
		s.teleport(pos);
		return s;

	}

}
