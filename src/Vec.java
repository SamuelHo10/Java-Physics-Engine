
public class Vec {

	    public double x;
	    public double y;
	    
	    public Vec() { }

	    public Vec(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }

	    public Vec(Vec v) {
	        set(v);
	    }

	    public void set(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }

	    public void set(Vec v) {
	        this.x = v.x;
	        this.y = v.y;
	    }

	    public void setZero() {
	        x = 0;
	        y = 0;
	    }

	    public double[] getComponents() {
	        return new double[]{x, y};
	    }

	    public double getLength() {
	        return Math.sqrt(x * x + y * y);
	    }

	    public double getLengthSq() {
	        return (x * x + y * y);
	    }

	    public double distanceSq(double vx, double vy) {
	        vx -= x;
	        vy -= y;
	        return (vx * vx + vy * vy);
	    }

	    public double distanceSq(Vec v) {
	        double vx = v.x - this.x;
	        double vy = v.y - this.y;
	        return (vx * vx + vy * vy);
	    }

	    public double distance(double vx, double vy) {
	        vx -= x;
	        vy -= y;
	        return Math.sqrt(vx * vx + vy * vy);
	    }

	    public double distance(Vec v) {
	        double vx = v.x - this.x;
	        double vy = v.y - this.y;
	        return Math.sqrt(vx * vx + vy * vy);
	    }

	    public double getAngle() {
	        return Math.atan2(y, x);
	    }

	    public void normalize() {
	        double magnitude = getLength();
	        if(magnitude != 0) {
	        	x /= magnitude;
		        y /= magnitude;
	        }
	        
	    }

	    public Vec getNormalized() {
	        double magnitude = getLength();
	        return new Vec(x / magnitude, y / magnitude);
	    }

	    public static Vec toCartesian(double magnitude, double angle) {
	        return new Vec(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	    }

	    public void add(Vec v) {
	        this.x += v.x;
	        this.y += v.y;
	    }

	    public void add(double vx, double vy) {
	        this.x += vx;
	        this.y += vy;
	    }

	    public static Vec getAdd(Vec v1, Vec v2) {
	        return new Vec(v1.x + v2.x, v1.y + v2.y);
	    }

	    public Vec getAdd(Vec v) {
	        return new Vec(this.x + v.x, this.y + v.y);
	    }

	    public void sub(Vec v) {
	        this.x -= v.x;
	        this.y -= v.y;
	    }

	    public void sub(double vx, double vy) {
	        this.x -= vx;
	        this.y -= vy;
	    }

	    public static Vec getSub(Vec v1, Vec v2) {
	        return new Vec(v1.x - v2.x, v1.y - v2.y);
	    }

	    public Vec getSub(Vec v) {
	        return new Vec(this.x - v.x, this.y - v.y);
	    }

	    public void mult(double scalar) {
	        x *= scalar;
	        y *= scalar;
	    }

	    public Vec getMult(double scalar) {
	        return new Vec(x * scalar, y * scalar);
	    }

	    public void div(double scalar) {
	        x /= scalar;
	        y /= scalar;
	    }

	    public Vec getDiv(double scalar) {
	        return new Vec(x / scalar, y / scalar);
	    }

	    public Vec getPerp() {
	        return new Vec(-y, x);
	    }

	    public double dot(Vec v) {
	        return (this.x * v.x + this.y * v.y);
	    }

	    public double dot(double vx, double vy) {
	        return (this.x * vx + this.y * vy);
	    }

	    public static double dot(Vec v1, Vec v2) {
	        return v1.x * v2.x + v1.y * v2.y;
	    }

	    public double cross(Vec v) {
	        return (this.x * v.y - this.y * v.x);
	    }

	    public double cross(double vx, double vy) {
	        return (this.x * vy - this.y * vx);
	    }

	    public static double cross(Vec v1, Vec v2) {
	        return (v1.x * v2.y - v1.y * v2.x);
	    }
	    
	    public static Vec crossProduct( Vec a, double s )
	    {
	      return new Vec( s * a.y, -s * a.x );
	    }

	    public static Vec crossProduct( double s, Vec a )
	    {
	      return new Vec( -s * a.y, s * a.x );
	    }
	    
	    public double project(Vec v) {
	        return (this.dot(v) / this.getLength());
	    }

	    public double project(double vx, double vy) {
	        return (this.dot(vx, vy) / this.getLength());
	    }

	    public static double project(Vec v1, Vec v2) {
	        return (dot(v1, v2) / v1.getLength());
	    }

	    public Vec getProjectedVector(Vec v) {
	        return this.getNormalized().getMult(this.dot(v) / this.getLength());
	    }

	    public Vec getProjectedVector(double vx, double vy) {
	        return this.getNormalized().getMult(this.dot(vx, vy) / this.getLength());
	    }

	    public static Vec getProjectedVector(Vec v1, Vec v2) {
	        return v1.getNormalized().getMult(Vec.dot(v1, v2) / v1.getLength());
	    }

	    public void rotateBy(double angle) {
	        double cos = Math.cos(angle);
	        double sin = Math.sin(angle);
	        double rx = x * cos - y * sin;
	        y = x * sin + y * cos;
	        x = rx;
	    }

	    public Vec getRotatedBy(double angle) {
	        double cos = Math.cos(angle);
	        double sin = Math.sin(angle);
	        return new Vec(x * cos - y * sin, x * sin + y * cos);
	    }

	    public void rotateTo(double angle) {
	        set(toCartesian(getLength(), angle));
	    }

	    public Vec getRotatedTo(double angle) {
	        return toCartesian(getLength(), angle);
	    }

	    public void reverse() {
	        x = -x;
	        y = -y;
	    }

	    public Vec getReversed() {
	        return new Vec(-x, -y);
	    }

	    @Override
	    public Vec clone() {
	        return new Vec(x, y);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj instanceof Vec) {
	            Vec v = (Vec) obj;
	            return (x == v.x) && (y == v.y);
	        }
	        return false;
	    }

	    @Override
	    public String toString() {
	        return "Vec[" + x + ", " + y + "]";
	    }
}
