package SWT;

import java.util.ArrayList;

public class Ray {
	 SWTPoint2d p;
     SWTPoint2d q;
//     std::vector<SWTPoint2d> points;
     
     ArrayList<SWTPoint2d> points;

	public SWTPoint2d getP() {
		return p;
	}

	public void setP(SWTPoint2d p) {
		this.p = p;
	}

	public SWTPoint2d getQ() {
		return q;
	}

	public void setQ(SWTPoint2d q) {
		this.q = q;
	}

	public ArrayList<SWTPoint2d> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<SWTPoint2d> points) {
		this.points = points;
	}
     
     
     
     
}
