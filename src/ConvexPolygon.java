import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * 
 */

/**
 * @author samuel
 *
 */
public class ConvexPolygon extends Body {

	protected Vec[] vertices;
	
	/**
	 * @return the vertices
	 */
	public Vec[] getVertices() {
		return vertices;
	}

	/**
	 * The vertices must be in counterclockwise order.
	 * 
	 * @param vertices the vertices to set
	 */
	public void setVertices(Vec[] vertices) {
		this.vertices = vertices;
	}
	
	/**
	 * @param pos
	 * @param vertices
	 * @param color
	 * @param mass
	 * @param isStatic
	 * @param name
	 */
	public ConvexPolygon(Vec pos, Vec[] vertices, Color color, int mass, boolean isStatic, String name) {
		super(pos, color, mass, isStatic, name);

		this.vertices = vertices;
		
		this.updateProperties();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void rotate(double angle) {
		super.rotate(angle);
		for (int i = 0; i < this.vertices.length; i++) {
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			double xNew = this.vertices[i].x * cos - this.vertices[i].y * sin;
			double yNew = this.vertices[i].x * sin + this.vertices[i].y * cos;

			this.vertices[i].x = xNew;
			this.vertices[i].y = yNew;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		Polygon p = new Polygon();
		for (int j = 0; j < vertices.length; j++) {
			p.addPoint((int) (vertices[j].x + pos.x), (int) (vertices[j].y + pos.y));
		}
		g.setColor(color);
		g.fillPolygon(p);
	}
	
	@Override
	protected double calcArea() {
		double area = 0;
		for (int i = 1; i < this.vertices.length - 1; i++) {
			Vec v1 = Vec.getSub(this.vertices[i + 1], this.vertices[0]);
			Vec v2 = Vec.getSub(this.vertices[i], this.vertices[0]);
			area += Vec.cross(v1, v2) / 2.0;
		}
		return Math.abs(area);
	}
	
	@Override
	protected double calcMomentOfInertia() {
		double momentOfInertia = 0;
		for (int i = 1; i < this.vertices.length - 1; i++) {
			Vec p1 = this.vertices[0], p2 = this.vertices[i], p3 = this.vertices[i + 1];
			double w = p1.distance(p2);
			double w1 = Math.abs(Vec.getSub(p1, p2).dot(Vec.getSub(p3, p2)) / w);
			double w2 = Math.abs(w - w1);
			double signedTriArea = Vec.cross(Vec.getSub(p3, p1), Vec.getSub(p2, p1)) / 2.0;
			double h = 2.0 * Math.abs(signedTriArea) / w;
			Vec p4 = Vec.getAdd(p2, Vec.getSub(p1, p2).getMult(w1 / w));
			Vec cm1 = new Vec(p2.x + p3.x + p4.x, p2.y + p3.y + p4.y);
			Vec cm2 = new Vec(p1.x + p3.x + p4.x, p1.y + p3.y + p4.y);
			double I1 = this.density * w1 * h * ((h * h / 4) + (w1 * w1 / 12));
			double I2 = this.density * w2 * h * ((h * h / 4) + (w2 * w2 / 12));
			double m1 = 0.5 * w1 * h * this.density;
			double m2 = 0.5 * w2 * h * this.density;
			double I1cm = I1 - (m1 * Math.pow(cm1.distance(p3), 2));
			double I2cm = I2 - (m2 * Math.pow(cm2.distance(p3), 2));
			double momentOfInertiaPart1 = I1cm + (m1 * cm1.getLengthSq());
			double momentOfInertiaPart2 = I2cm + (m2 * cm2.getLengthSq());
			if (Vec.cross(Vec.getSub(p1, p3), Vec.getSub(p4, p3)) > 0) {
				momentOfInertia += momentOfInertiaPart1;
			} else {
				momentOfInertia -= momentOfInertiaPart1;
			}
			if (Vec.cross(Vec.getSub(p4, p3), Vec.getSub(p2, p3)) > 0) {
				momentOfInertia += momentOfInertiaPart2;
			} else {
				momentOfInertia -= momentOfInertiaPart2;
			}
		}
		return Math.abs(momentOfInertia);
	}
	
	@Override
	public double[] getMaxMin() {
		double[] arr = { Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE };// [min x, max x, min
																									// y, max y]
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i].x < arr[0]) {
				arr[0] = vertices[i].x;
			}
			if (vertices[i].x > arr[1]) {
				arr[1] = vertices[i].x;
			}
			if (vertices[i].y < arr[2]) {
				arr[2] = vertices[i].y;
			}
			if (vertices[i].y > arr[3]) {
				arr[3] = vertices[i].y;
			}
		}
		arr[0] += pos.x;
		arr[1] += pos.x;
		arr[2] += pos.y;
		arr[3] += pos.y;
		return arr;
	}
	
	public static Vec[] regularPolygon(int radius, int numVertices) {
		Vec[] vertices = new Vec[numVertices];
		for (int i = 0; i < numVertices; i++) {
			vertices[i] = new Vec(Math.cos(-i * Math.PI * 2.0 / numVertices) * radius,
					Math.sin(-i * Math.PI * 2.0 / numVertices) * radius);
		}
		return vertices;
	}

	public static Vec[] rectangle(int width, int height) {
		Vec[] vertices = new Vec[4]; // 4 Vertices in a rectangle

		vertices[0] = new Vec(width / 2, height / 2);
		vertices[1] = new Vec(width / 2, -height / 2);
		vertices[2] = new Vec(-width / 2, -height / 2);
		vertices[3] = new Vec(-width / 2, height / 2);

		return vertices;
	}
}
