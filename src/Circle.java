import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
* File Name: Circle.java
* @author: Samuel Ho
* Class: ICS4U1-13 
* Date: January 17, 2022
* Description: This class stores all the information for a circle Rigidbody.
*/
public class Circle extends Body {

	protected double radius;
	public ArrayList<Vec> points;
	public Vec axis;

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @param pos
	 * @param radius
	 * @param color
	 * @param mass
	 * @param isStatic
	 * @param name
	 */
	public Circle(Vec pos, double radius, Color color, int mass, boolean isStatic, String name) {
		super(pos, color, mass, isStatic, name);

		this.radius = radius;

		this.updateProperties();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (pos.x - radius), (int) (pos.y - radius), (int) radius * 2, (int) radius * 2);
		
		g.setColor(Color.RED);
		g.fillOval((int)(Math.cos(this.angle*Math.PI/180)*this.radius- 5+this.pos.x), (int)(Math.sin(this.angle*Math.PI/180)*this.radius - 5+this.pos.y), 10, 10);
	}

	@Override
	protected double calcArea() {
		return radius * radius * Math.PI;
	}

	@Override
	protected double calcMomentOfInertia() {
		return mass * radius * radius / 4;
	}

	@Override
	public double[] getMaxMin() {
		// [min x, max x, min y, max y]
		return new double[] { pos.x - radius, pos.x + radius, pos.y - radius, pos.y + radius };
	}
}
