import java.util.ArrayList;

public class ShipList {
	static EnemyShip testShip;
	static EnemyShip testShip2;
	static EnemyShip twinGuns;
	static EnemyShip runner;
	static EnemyShip cornered;
	static EnemyShip fatBoy;
	static EnemyShip stationary;

	// bosses
	static EnemyShip megaCorp;

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
			runner = new EnemyShip(new Point(0, 0));
			cornered = new EnemyShip(new Point(0, 0));
			fatBoy = new EnemyShip(new Point(0, 0));
			stationary = new EnemyShip(new Point(0, 0));
			megaCorp = new EnemyShip(new Point(0, 0));

			twinGuns.addPart(new Hull(new Point(-1, 0)), new Hull(new Point(-1, 1)), new Hull(new Point(2, 0)),
					new Hull(new Point(2, 1)), new Hull(new Point(1, -1)), new Armor(new Point(3, 0)),
					new Armor(new Point(3, 1)), new Armor(new Point(-2, 0)), new Armor(new Point(-2, 1)),
					new Laser(new Point(-1, -2), 0), new Laser(new Point(2, -2), 0), new Hull(new Point(0, -1)),
					new Armor(new Point(0, -2)), new Armor(new Point(1, -2)), new Armor(new Point(-1, 2)),
					new Armor(new Point(2, 2)), new Hull(new Point(1, 2)), new Hull(new Point(0, 2)),
					new Thruster(new Point(-1, 3), 2), new Thruster(new Point(0, 3), 2),
					new Thruster(new Point(1, 3), 2), new Thruster(new Point(2, 3), 2));

			runner.addPart(new Thruster(new Point(-1, 2), 2), new Thruster(new Point(0, 2), 2),
					new Thruster(new Point(1, 2), 2), new Thruster(new Point(2, 2), 2), new Laser(new Point(1, -2), 0),
					new Hull(new Point(-1, 0)), new Hull(new Point(-1, 1)), new Hull(new Point(2, 0)),
					new Hull(new Point(2, 1)), new Hull(new Point(0, -1)), new Thruster(new Point(1, 3), 2),
					new Thruster(new Point(0, 3), 2));

			cornered.addPart(new Armor(new Point(1, -1)), new Armor(new Point(2, -1)), new Armor(new Point(2, 0)),
					new Armor(new Point(-1, 1)), new Armor(new Point(-1, 2)), new Armor(new Point(0, 2)),
					new Armor(new Point(2, 2)), new Armor(new Point(-1, -1)), new Thruster(new Point(0, 3), 2),
					new Thruster(new Point(2, 3), 2), new Thruster(new Point(3, 2), 1),
					new Thruster(new Point(1, -2), 0), new Thruster(new Point(-1, -2), 0),
					new Thruster(new Point(-2, -1), 3), new Laser(new Point(1, 2), 2), new Laser(new Point(2, 1), 1),
					new Laser(new Point(0, -2), 0), new Laser(new Point(-2, 0), 3));

			fatBoy.addPart(new Hull(new Point(0, -1)), new Hull(new Point(1, -1)), new Hull(new Point(-1, 0)),
					new Hull(new Point(-1, 1)), new Hull(new Point(0, 2)), new Hull(new Point(1, 2)),
					new Hull(new Point(2, 0)), new Hull(new Point(2, 1)), new Hull(new Point(2, -1)),
					new Hull(new Point(-1, -1)), new Hull(new Point(-1, 2)), new Hull(new Point(2, 2)),
					new Thruster(new Point(-1, 3), 2), new Thruster(new Point(-1, 4), 2),
					new Thruster(new Point(0, 3), 2), new Thruster(new Point(0, 4), 2),
					new Thruster(new Point(1, 3), 2), new Thruster(new Point(1, 4), 2),
					new Thruster(new Point(2, 3), 2), new Thruster(new Point(2, 4), 2), new Laser(new Point(-2, -1), 0),
					new Laser(new Point(3, -1), 0), new Armor(new Point(-3, -1)), new Armor(new Point(-3, 0)),
					new Armor(new Point(4, -1)), new Armor(new Point(4, 0)));

			stationary.addPart(new Armor(new Point(0, 2)), new Armor(new Point(1, 2)), new Armor(new Point(2, 0)),
					new Armor(new Point(2, 1)), new Armor(new Point(0, -1)), new Armor(new Point(-1, -1)),
					new Armor(new Point(-1, 0)), new Armor(new Point(2, 2)), new Hull(new Point(0, -2)),
					new Hull(new Point(-1, -2)), new Hull(new Point(-2, -2)), new Hull(new Point(-2, -1)),
					new Hull(new Point(-2, 0)), new Hull(new Point(0, 3)), new Hull(new Point(1, 3)),
					new Hull(new Point(2, 3)), new Hull(new Point(3, 3)), new Hull(new Point(3, 2)),
					new Hull(new Point(3, 1)), new Hull(new Point(3, 0)), new Laser(new Point(1, -2), 0),
					new Laser(new Point(2, -1), 1), new Laser(new Point(-2, 1), 3), new Laser(new Point(-1, 2), 2),
					new Armor(new Point(-2, 2)), new Armor(new Point(2, -2)));

			megaCorp.addPart(new Hull(new Point(0, -1)), new Hull(new Point(1, -1)), new Hull(new Point(2, -1)), new Hull(new Point(2, 0)), new Hull(new Point(2, 1)), new Hull(new Point(2, 2)), new Hull(new Point(1, 2)), new Hull(new Point(0, 2)), new Hull(new Point(-1, 2)), new Hull(new Point(-1, 1)), new Hull(new Point(-1, 0)), new Hull(new Point(-1, -1)), new Armor(new Point(2, -2)), new Armor(new Point(-1, 3)), new Armor(new Point(3, -2)), new Armor(new Point(3, -1)), new Armor(new Point(3, 0)), new Armor(new Point(3, 1)), new Armor(new Point(3, 2)), new Armor(new Point(3, 3)), new Armor(new Point(2, 3)), new Armor(new Point(1, 3)), new Armor(new Point(-2, 3)), new Armor(new Point(-2, 2)), new Armor(new Point(-2, 1)), new Armor(new Point(-2, 0)), new Armor(new Point(-2, -1)), new Armor(new Point(-2, -2)), new Armor(new Point(-1, -2)), new Armor(new Point(0, -2)), new Laser(new Point(-4, -2), 3), new Laser(new Point(-2, -4), 0), new Laser(new Point(-4, 3), 3), new Laser(new Point(-2, 4), 2), new Laser(new Point(3, 4), 2), new Laser(new Point(4, 3), 1), new Laser(new Point(4, -2), 1), new Laser(new Point(3, -4), 0), new Armor(new Point(-3, -1)), new Armor(new Point(-3, 0)), new Armor(new Point(-3, 1)), new Armor(new Point(-3, 2)), new Armor(new Point(4, -1)), new Armor(new Point(4, 0)), new Armor(new Point(4, 1)), new Armor(new Point(4, 2)), new Hull(new Point(-4, 0)), new Hull(new Point(-4, 1)), new Laser(new Point(-6, 0), 3), new Laser(new Point(-6, 1), 3), new Hull(new Point(5, 0)), new Hull(new Point(5, 1)), new Laser(new Point(6, 0), 1), new Laser(new Point(6, 1), 1), new Laser(new Point(1, 4), 2), new Laser(new Point(0, -4), 0), new Hull(new Point(1, -3)), new Hull(new Point(-1, -3)), new Hull(new Point(-3, -3)), new Hull(new Point(-4, -1)), new Hull(new Point(-4, 2)), new Hull(new Point(-3, 4)), new Hull(new Point(2, 4)), new Hull(new Point(4, 4)), new Hull(new Point(5, 2)), new Hull(new Point(5, -1)), new Hull(new Point(4, -3)), new Hull(new Point(-4, -3)), new Hull(new Point(-5, -3)), new Hull(new Point(-4, 4)), new Hull(new Point(-5, 4)), new Hull(new Point(5, 4)), new Hull(new Point(6, 4)), new Hull(new Point(5, -3)), new Hull(new Point(6, -3)), new Armor(new Point(0, 3)), new Armor(new Point(1, -2)), new Hull(new Point(0, 4)), new Hull(new Point(-1, 4)), new Hull(new Point(2, -3)));


			ships.add(twinGuns);
			ships.add(runner);
			ships.add(cornered);
			ships.add(fatBoy);
			ships.add(stationary);
			
			
			//ships.add(megaCorp);
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
