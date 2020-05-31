import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Grid {
	ArrayList<Node> open = new ArrayList<Node>();
	ArrayList<Node> closed = new ArrayList<Node>();
	ArrayList<Node> blocked = new ArrayList<Node>();
	ArrayList<Node> pathTree = new ArrayList<Node>();

	public Grid(ArrayList<Node> blocked) {
		super();
		this.blocked = blocked;
	}
	public Node makeBlockerNode(int x, int y) {
		return new Node(new GridPoint(x, y), true, false, false, 0, 0, null);
	}

	public void getPath(GridPoint a, GridPoint b) {
		open.add(new Node(a, false, false, true, getGCost(a, b), 0, null));
		while (open.size() >= 1) {
			Node curr = getLoF(open);
			//System.out.print("========================== Chosen: ");
			//curr.pos.print();
			if (!curr.start) {
				//System.out.print("Parent: ");
				//curr.parent.pos.print();
			}
			//System.out.println("OPEN: ");
			for (Node n : open) {
				//n.pos.print();
				if (!n.start) {
					//System.out.print("Parent: ");
					//curr.parent.pos.print();
				}
				//System.out.println("GCOST: " + n.gCost + " SCOST: " + n.sCost + " FCOST: " + n.fCost);

			}
			if (!curr.start) {
				//curr.parent.pos.print();
			}
			open.remove(curr);
			closed.add(curr);

			if (curr.target) {
				//System.out.println("====== FOUND PATH ======");
				setNodePath(curr);
				//System.out.println("====== DONE ======");
				open.clear();
				closed.clear();
				return;
			}
			ArrayList<Node> neighbors = getNeighbors(curr, a, b);
			//System.out.println("Neighbors: " + neighbors.size());
			for (Node n : neighbors) {

				if (n.blocked || closed.contains(n)) {
					continue;
				}

				if (!open.contains(n)) {
//System.out.println("here");
					n.parent = curr;
					n.sCost = getSCost(n);
					n.fCost = n.sCost + n.gCost;
					//System.out.println("Set (" + n.pos.x + ", " + n.pos.y + ")'s parent to: (" + n.parent.pos.x + ", "
					//		+ n.parent.pos.y + ")");
					if (!open.contains(n)) {
						open.add(n);
					}
				}

			}

		}
		//System.out.println("Couldn't find a path -_-");
	}

	private void setNodePath(Node n) {
		for (; !n.start; n = n.parent) {
			pathTree.add(n);
		}
		pathTree.add(n);

		for (Node node : pathTree) {
			//node.pos.print();
		}

	}

	private ArrayList<Node> getNeighbors(Node curr, GridPoint a, GridPoint b) {

		ArrayList<Node> neighbors = new ArrayList<Node>();
		for (Node n : open) {

			if (n.pos.isAdjacentTo(curr.pos)) {

				n.sCost = getSCost(n);
				n.fCost = n.sCost + n.gCost;
				neighbors.add(n);
			}
		}
		for (Node n : closed) {
			if (n.pos.isAdjacentTo(curr.pos)) {
				if (!n.start) {

					n.sCost = getSCost(n);
					n.fCost = n.sCost + n.gCost;
				}
				neighbors.add(n);
			}
		}
		for (Node n : blocked) {
			if (n.pos.isAdjacentTo(curr.pos)) {
				neighbors.add(n);
			}
		}
		// add neighbors that are not on either closed or open and add them to open
		for (int y = -1; y < 2; y++) {
			outer: for (int x = -1; x < 2; x++) {
				if (!(x == 0 && y == 0)) {
					GridPoint tempPos = new GridPoint(curr.pos.x + x, curr.pos.y + y);
					for (Node n : neighbors) {
						if (n.pos.isSamePosition(tempPos)) {
							continue outer;
						}
					}
					// add new node
					Node n = new Node(tempPos, false, tempPos.isSamePosition(b), tempPos.isSamePosition(a),
							getGCost(tempPos, b), 0, curr);
					//System.out.println("Created (" + n.pos.x + ", " + n.pos.y + ") and set their parent to: ("
					//		+ curr.pos.x + ", " + curr.pos.y + ")");
					n.sCost = getSCost(n);
					n.fCost = n.sCost + n.gCost;
					neighbors.add(n);
				}
			}

		}

		// sort neighbors by f cost
		Collections.sort(neighbors);

		return neighbors;
	}

	private int getGCost(GridPoint curr, GridPoint b) {
		return (int) ((Math.abs(Math.abs(curr.x - b.x) - Math.abs(curr.y - b.y))) * 10 + (14
				* (Math.abs(curr.x - b.x) < Math.abs(curr.y - b.y) ? Math.abs(curr.x - b.x) : Math.abs(curr.y - b.y))));
	}

	private int getSCost(Node n) {
		int SCost = 0;
		for (; !n.start; n = n.parent) {
			SCost += (int) (10 * n.pos.distanceTo(n.parent.pos));
		}

		return SCost;
	}

	private Node getLoF(ArrayList<Node> open) {
		Node lowF = open.get(0);
		for (Node n : open) {
			if (n.fCost == lowF.fCost && n.gCost < lowF.gCost) {
				lowF = n;
			} else if (n.fCost < lowF.fCost) {
				lowF = n;
			}

		}
		return lowF;
	}

	public void drawPath(Graphics g) {

		// draw lines
		int w = 10;

		for (Node o : blocked) {
			g.setColor(Color.BLACK);
			g.fillRect(Camera.toXScreen(o.pos.x * (w) - w / 2), Camera.toYScreen((o.pos.y * (w)) - (w / 2)),
					(int) (w * Camera.scale), (int) (w * Camera.scale));

		}
		for (Node o : pathTree) {
			g.setColor(Color.BLUE);
			g.fillRect(Camera.toXScreen(o.pos.x * (w) - w / 2), Camera.toYScreen((o.pos.y * (w)) - (w / 2)),
					(int) (w * Camera.scale), (int) (w * Camera.scale));

			if (o.start) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Impact", 1, (int) Camera.scale * 10));
				g.drawString("A", Camera.toXScreen(o.pos.x * w - w / 4), Camera.toYScreen(o.pos.y * w + w / 4));
			}
			if (o.target) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Impact", 1, (int) Camera.scale * 10));
				g.drawString("B", Camera.toXScreen(o.pos.x * w - w / 4), Camera.toYScreen(o.pos.y * w + w / 4));
			}

		}
		for (int i = 0; i <= 1000 + 1; i++) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(Camera.toXScreen(i * 10 - w / 2), Camera.toYScreen(0 - w / 2), Camera.toXScreen(i * 10 - w / 2),
					Camera.toYScreen(10000 + 5));
			g.drawLine(Camera.toXScreen(0 - w / 2), Camera.toYScreen(i * 10 - w / 2), Camera.toXScreen(10000 - w / 2),
					Camera.toYScreen(i * 10 - w / 2));
		}
	}

	public void draw(Graphics g) {

		// draw lines
		int w = 10;

		for (Node o : blocked) {
			g.setColor(Color.BLACK);
			g.fillRect(Camera.toXScreen(o.pos.x * (w) - w / 2), Camera.toYScreen((o.pos.y * (w)) - (w / 2)),
					(int) (w * Camera.scale), (int) (w * Camera.scale));

		}

		for (int i = 0; i <= 1000 + 1; i++) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(Camera.toXScreen(i * 10 - w / 2), Camera.toYScreen(0 - w / 2), Camera.toXScreen(i * 10 - w / 2),
					Camera.toYScreen(10000 + 5));
			g.drawLine(Camera.toXScreen(0 - w / 2), Camera.toYScreen(i * 10 - w / 2), Camera.toXScreen(10000 - w / 2),
					Camera.toYScreen(i * 10 - w / 2));
		}
	}
}

class Node implements Comparable<Node> {
	GridPoint pos;
	boolean blocked, target, start;
	int fCost, gCost, sCost;
	Node parent;

	public Node(GridPoint pos, boolean blocked, boolean target, boolean start, int gCost, int sCost, Node parent) {
		super();
		this.pos = pos;
		this.blocked = blocked;
		this.target = target;
		this.start = start;
		this.gCost = gCost;
		this.fCost = gCost + sCost;
		this.parent = parent;
	}

	@Override
	public int compareTo(Node n) {
		return -n.fCost + this.fCost;
	}

}
class GridPoint {
	int x, y;

	public GridPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double distanceTo(GridPoint p2) {
		return Math.sqrt((this.x - p2.x) * (this.x - p2.x) + (this.y - p2.y) * (this.y - p2.y));
	}

	public double angleTo(GridPoint p2) {
		try {
			return Math.atan2(this.y - p2.y, this.x - p2.x);
		} catch (Exception e) {

		}
		return 0;
	}

	public boolean isSamePosition(GridPoint p) {
//		System.out.println("Check");
//		this.print();
//		p.print();
//		System.out.println("End Check");
		return ((int)p.x == (int)this.x && (int)p.y == (int)this.y);
	}

	public boolean isAdjacentTo(GridPoint p) {
		return (Math.abs(p.x - this.x) <= 1 && Math.abs(p.y - this.y) <= 1 && !p.equals(this));
	}

	public void print() {
		System.out.println("(" + x + ", " + y + ")");
	}
}