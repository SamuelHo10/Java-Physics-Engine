import java.util.ArrayList;

public class CollisionResolver {
	private static final double ALLOWED_PENETRATION = 0.01;
	private static final double BIAS_FACTOR = 0.1;
	private static final double THRESHOLD_BOUNCE_VELOCITY = -0.1;
	
	private Body A;
	private Body B;
	private CollisionDetector col;
	private ArrayList<Vec> contacts;
	private int len; 
	private double massNormal;
	private double massTangent;
	private double bias;
	private double Pt;
	private double Pn;
	private double elasticityBias;
	private double friction;

	public CollisionResolver(CollisionDetector col, double dt) {
		this.A = col.getObj1();
		this.B = col.getObj2();
		this.col = col;
		this.contacts = col.getContactPoints();
		this.len = this.contacts.size();
		this.Pt = 0;
		this.Pn = 0;
		this.friction = Math.sqrt(Math.pow(A.getFriction(), 2) + Math.pow(B.getFriction(), 2));

		// Precompute normal mass, tangent mass, and bias
		
		for (int i = 0; i < this.len; i++) {

			Vec contact = this.contacts.get(i);

			Vec ra = Vec.getSub(contact, A.getPos());
			Vec rb = Vec.getSub(contact, B.getPos());

			double rna = Vec.dot(ra, col.getNormal());
			double rnb = Vec.dot(rb, col.getNormal());
			
			double kNormal = A.getInvMass() + B.getInvMass() + (Vec.dot(ra, ra) - rna * rna) * A.getInvInertia()
					+ (Vec.dot(rb, rb) - rnb * rnb) * B.getInvInertia();
			this.massNormal = 1.0 / kNormal;

			Vec tangent = Vec.crossProduct(col.getNormal(), 1);
			double rta = Vec.dot(ra, tangent);
			double rtb = Vec.dot(rb, tangent);
			
			double kTangent = A.getInvMass() + B.getInvMass() + (Vec.dot(ra, ra) - rta * rta) * A.getInvInertia()
					+ (Vec.dot(rb, rb) - rtb * rtb) * B.getInvInertia();
			this.massTangent = 1.0 / kTangent;

			this.bias = -BIAS_FACTOR / dt * Math.min(0, -col.getOverlap() + ALLOWED_PENETRATION);
			
			//get elasticity
			
			Vec rv = Vec.getSub(Vec.getAdd(B.getVel(), Vec.crossProduct(B.getAngularVelocity(), rb)),
					Vec.getAdd(A.getVel(), Vec.crossProduct(A.getAngularVelocity(), ra)));
			
			double vn =  rv.dot(col.getNormal());
			
			double e = Math.min(A.getElasticity(), B.getElasticity());

			if(vn < THRESHOLD_BOUNCE_VELOCITY) {
				this.elasticityBias = -e*vn;
			}else {
				this.elasticityBias = 0;
			}
		}
		
		
	}
	
	public void applyImpulse() {
		for (int i = 0; i < this.len; i++) {
			
			Vec contact = this.contacts.get(i);
			
			Vec ra = Vec.getSub(contact, A.getPos());
			Vec rb = Vec.getSub(contact, B.getPos());
			
			Vec rv = Vec.getSub(Vec.getAdd(B.getVel(), Vec.crossProduct(B.getAngularVelocity(), rb)),
					Vec.getAdd(A.getVel(), Vec.crossProduct(A.getAngularVelocity(), ra)));
			
			double vn =  rv.dot(col.getNormal());
			
			double dPn = massNormal * (-vn + bias + this.elasticityBias);
			
			double Pn0 = Pn;
			Pn = Math.max(Pn0 + dPn, 0);
			dPn = Pn - Pn0;
			
			Vec Pn = col.getNormal().getMult(dPn);
			
			A.applyImpulse(Pn.getReversed(), ra);
			B.applyImpulse(Pn, rb);
			
			rv = Vec.getSub(Vec.getAdd(B.getVel(), Vec.crossProduct(B.getAngularVelocity(), rb)),
					Vec.getAdd(A.getVel(), Vec.crossProduct(A.getAngularVelocity(), ra)));
			
			Vec tangent = Vec.crossProduct(col.getNormal(), 1.0);
			double vt = Vec.dot(rv, tangent);
			double dPt = massTangent * (-vt);
			
			double Pt0 = Pt;
			double maxPt = friction * this.Pn;
			Pt = clamp(Pt0 + dPt, -maxPt, maxPt);
			dPt = Pt - Pt0;
			
			Vec Pt = tangent.getMult(dPt);
			
			A.applyImpulse(Pt.getReversed(), ra);
			B.applyImpulse(Pt, rb);
		}
	}

	private static double clamp(double val, double min, double max) {
	    return Math.max(min, Math.min(max, val));
	}
	
}
