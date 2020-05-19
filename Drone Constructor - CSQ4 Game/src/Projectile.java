
public abstract class Projectile {
Poly bounds;
Vec2 vel;
double rVel;
double rotation;
boolean alive = true;
double maxLifeSpan = 1000;
double damage;

public Projectile(Poly bounds, Vec2 vel, double rVel, double rotation, double maxLifeSpan, double damage) {
	this.bounds = bounds;
	this.vel = vel;
	this.rVel = rVel;
	this.rotation = rotation;
	this.maxLifeSpan = maxLifeSpan;
	this.damage = damage;
}
}
