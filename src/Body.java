import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Body {

	private static boolean enableCollisions = true;
	private static boolean noGravity = false;
	private static boolean runningSimulation = true;
	private static boolean enableFriction = true;
	private static boolean enableRotations = true;
	private static final int COLLISION_ITERATIONS = 100;

	protected Vec pos;
	protected Color color;
	protected int mass;
	protected double invMass;
	protected Vec vel;
	protected double angle;
	protected double angularVelocity;
	protected boolean isStatic;
	protected double inertia;
	protected double density;
	protected double friction;
	protected double invInertia;
	protected String name;
	protected double elasticity;

	/**
	 * @return the pos
	 */
	public Vec getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vec pos) {
		this.pos = pos;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the mass
	 */
	public int getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(int mass) {
		this.mass = mass;
		this.updateProperties();
	}

	/**
	 * @return the invMass
	 */
	public double getInvMass() {
		return invMass;
	}

	/**
	 * @return the vel
	 */
	public Vec getVel() {
		return vel;
	}

	/**
	 * @param vel the vel to set
	 */
	public void setVel(Vec vel) {
		this.vel = vel;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.rotate(angle - this.angle);
	}

	/**
	 * @return the angularVelocity
	 */
	public double getAngularVelocity() {
		return angularVelocity;
	}

	/**
	 * @param angularVelocity the angularVelocity to set
	 */
	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	/**
	 * @return the isStatic
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * @param isStatic the isStatic to set
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
		if (!this.isStatic) {
			this.invMass = 1.0 / this.mass;
			this.invInertia = 1.0 / this.inertia;
		}
	}

	/**
	 * @return the inertia
	 */
	public double getInertia() {
		return inertia;
	}

	/**
	 * @return the inverse inertia
	 */
	public double getInvInertia() {
		return invInertia;
	}

	/**
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * @return the friction
	 */
	public double getFriction() {
		return friction;
	}

	/**
	 * @param friction the friction to set
	 */
	public void setFriction(double friction) {
		this.friction = friction;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the elasticity
	 */
	public double getElasticity() {
		return elasticity;
	}

	/**
	 * @param elasticity the elasticity to set
	 */
	public void setElasticity(double elasticity) {
		this.elasticity = elasticity;
	}

	/**
	 * @return the enableCollisions
	 */
	public static boolean isEnableCollisions() {
		return enableCollisions;
	}

	/**
	 * @param enableCollisions the enableCollisions to set
	 */
	public static void setEnableCollisions(boolean enableCollisions) {
		Body.enableCollisions = enableCollisions;
	}

	/**
	 * @return the noGravity
	 */
	public static boolean isNoGravity() {
		return noGravity;
	}

	/**
	 * @param noGravity the noGravity to set
	 */
	public static void setNoGravity(boolean noGravity) {
		Body.noGravity = noGravity;
	}

	/**
	 * @return the runningSimulation
	 */
	public static boolean isRunningSimulation() {
		return runningSimulation;
	}

	/**
	 * @param runningSimulation the runningSimulation to set
	 */
	public static void setRunningSimulation(boolean runningSimulation) {
		Body.runningSimulation = runningSimulation;
	}

	/**
	 * @return the enableFriction
	 */
	public static boolean isEnableFriction() {
		return enableFriction;
	}

	/**
	 * @param enableFriction the enableFriction to set
	 */
	public static void setEnableFriction(boolean enableFriction) {
		Body.enableFriction = enableFriction;
	}

	/**
	 * @return the enableRotations
	 */
	public static boolean isEnableRotations() {
		return enableRotations;
	}

	/**
	 * @param enableRotations the enableRotations to set
	 */
	public static void setEnableRotations(boolean enableRotations) {
		Body.enableRotations = enableRotations;
	}

	/**
	 * TODO
	 * 
	 * @param pos
	 * @param vertices
	 * @param color
	 * @param mass
	 * @param isStatic
	 * @param name
	 */
	public Body(Vec pos, Color color, int mass, boolean isStatic, String name) {
		this.pos = pos;
		this.color = color;
		this.mass = mass;
		this.vel = new Vec(0, 0);
		this.angle = 0;
		this.angularVelocity = 0;
		this.isStatic = isStatic;
		this.friction = 0.2;
		this.name = name;
		this.elasticity = 0;
	}

	protected void rotate(double angle) {
		this.angle += angle / Math.PI * 360;
		if (this.angle >= 360) {
			this.angle -= 360;
		} else if (this.angle < 0) {
			this.angle += 360;
		}
	}

	protected abstract double calcArea();

	protected abstract double calcMomentOfInertia();

	public abstract double[] getMaxMin();

	public abstract void draw(Graphics g);

	public void applyImpulse(Vec impulse, Vec radius) {
		if (!isStatic) {
			vel.add(impulse.getMult(invMass));
			if (Body.enableRotations) {
				angularVelocity += 1.0 / inertia * Vec.cross(radius, impulse);
			}
		}
	}

	private void move(double dt) {
		if (!isStatic) {
			pos.add(vel.getMult(dt));
			rotate(angularVelocity * dt);
			if (!Body.noGravity) {
				vel.add(new Vec(0, Simulation.GRAVITY * dt / 100));
			}
		} else {
			vel = new Vec(0, 0);
			angularVelocity = 0;
		}
	}

	public static void updateAll(ArrayList<Body> arr) {
		double dt = Simulation.dt;
		ArrayList<CollisionResolver> cols = new ArrayList<CollisionResolver>();

		int len = arr.size();
		if (enableCollisions) {
			for (int i = 0; i < len - 1; i++) {
				for (int j = i + 1; j < len; j++) {
					Body obj1 = arr.get(i);
					Body obj2 = arr.get(j);
					CollisionDetector col = new CollisionDetector(obj1, obj2);

					if (col.checkCollision()) {
						cols.add(new CollisionResolver(col, dt));
					}
				}
			}
		}

		for (int j = 0; j < COLLISION_ITERATIONS; j++) {
			for (int i = 0; i < cols.size(); i++) {
				cols.get(i).applyImpulse();
			}
		}

		for (int i = 0; i < len; i++) {
			arr.get(i).move(dt);
		}

		Iterator<Body> i = arr.iterator();
		while (i.hasNext()) {
			Body obj = i.next();
			if (obj.pos.y > Simulation.screenHeight + 100) {
				i.remove();
			}
		}

	}

	protected void updateProperties() {
		if (this.mass <= 0) {
			this.mass = 1; // default mass value of 1
		}
		this.invMass = 1.0 / this.mass;
		this.density = this.mass / this.calcArea();
		this.inertia = this.calcMomentOfInertia();
		this.invInertia = 1.0 / this.inertia;
		if (isStatic) {
			this.invMass = 0;
			this.invInertia = 0;
		}
	}

}
