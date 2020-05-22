
public class Vec2 {
	double x, y;

	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vec2 clone() {
		return new Vec2(this.x,this.y);
	}

	public Vec2() {
		this.x = Math.random() * 2 - 1;
		this.y = Math.random() * 2 - 1;
		double mag = this.getMagnitude();
		this.x /= mag;
		this.y /= mag;

	}
	public Point toPoint() {
		return new Point(x,y);
	}
	public double dot(Vec2 v) {
		return (x*v.x + y*v.y);
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}
	public int getQuadrant() {
		double angle = Math.toDegrees(this.getAngle());
		if(angle >= 0 && angle <= 90) return 1;
		if(angle > 90 && angle <= 180) return 2;
		if(angle < 0 && angle >= -90) return 4;
		if(angle < -90 && angle >= -180) return 3;
		return -1;
	}
	public Vec2 setMagnitude(double m) {
		return new Vec2(m*x/(Math.sqrt(x*x + y*y)),m*y/(Math.sqrt(x*x + y*y)));
	}
	public Vec2 simpleMult(double d) {
		return new Vec2(x*d,y*d);
	}
	public Vec2 normalize() {
		double mag = this.getMagnitude();
		return new Vec2(x/mag,y/mag);
	}
	public int getSign(double i) {
		return i > 0 ? 1 : -1;
	}

	public double getAngle() {
		try {
			return Math.atan2(this.y, this.x);
		} catch (Exception e) {

		}
		return 0;
	}
	public Vec2 add(Vec2 v) {
		return new Vec2(this.x + v.x, this.y + v.y);
	}
	public Vec2 subtract(Vec2 v) {
		return new Vec2(this.x - v.x, this.y - v.y);
	}
	public void print() {
		System.out.println("<" + this.x + "," + this.y + ">");
	}

}
