/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.SlickException;

/** Describes an Enemy kart and the waypoints for the same.
 * 
 * Also contains common helper methods for an enemy cart to follow the waypoints.
 *
 */
public abstract class Enemy extends Kart{
	private double[] Ax = {1350, 990, 990, 1350, 1638, 1962, 1638,
			774, 846, 1026, 1710, 1584, 1584, 1584, 918, 702, 882, 882,
			1386, 1602, 2034, 1926, 1278, 918, 738, 900, 900, 1674, 2034,
			1962, 1746, 1350, 954, 558, 702, 1170, 1530, 1134, 1386,
			1998, 1998, 1890, 1566, 738, 918, 1350, 1350, 1296};

	private double[] Ay = {12186, 11682, 11466, 11070, 10890, 10458, 10170,
			10206, 9882, 9738, 9738, 9378, 8946, 8334, 8226, 7974, 7686,
			6606, 6498, 7038, 7002, 6174, 5958, 6030, 5706, 5562, 4986,
			4770, 4770, 4518, 4122, 3978, 4410, 4122, 3150,
			2934, 3150, 3726, 3870, 3762, 3150, 2790, 2214, 2142, 1818,
			1746, 1530, 0};

	public Enemy(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x,y,angle, velocity);
		// EVERYONE IS TOO FAST FOR ME
		setAccel(0.75);
	}

	public double[] getAx() {
		return Ax;
	}

	public double[] getAy() {
		return Ay;
	}

	public abstract void update(World world, Player player);


	/** Depend on the quadrant, compute the new rotation angle towards
	 * the the given coordinates, using the old rotation angle.
	 * The following convention is used:
	 *        0
	 *      - | +
	 * -90 ---|--- 90
	 *      - | +
	 *   -180 / +180
	 *    
	 * @param oldAngle
	 * @param x
	 * @param y
	 * @return
	 */
	public Angle newRotationAngle (Angle oldAngle, double x, double y) {
		double realanglepoint;
		// depends on which quadrant, adjust the angle of waypoint when elephant is at origin.
		// 1st quadrant
		if ((x >= 0) && ( y >= 0)) {
			realanglepoint = oldAngle.getDegrees();
			// 3rd quadrant
		} else if ((x < 0) && (y < 0)) {
			realanglepoint = -180 + oldAngle.getDegrees();
			// 2nd quadrant
		} else if ((x < 0) && (y > 0)) {
			realanglepoint = -1 * oldAngle.getDegrees();
			// 4th quadrant
		} else {	
			realanglepoint = 180 - oldAngle.getDegrees();
		}

		Angle newanglepoint = new Angle(realanglepoint*Math.PI/180);
		return newanglepoint;
	}

	/** Decide the rotation direction from the given rotation
	 * angle.
	 * 
	 * a rotate_dir of 1 is clockwise, -1 is anti-clockwise and 
	 * 0 is straight.
	 * 
	 * @param newanglepoint waypoint angle after shifting
	 * @return rotation direction
	 */
	public int computeRotateDir(Angle newanglepoint) {
		int rotate_dir = 0;
		// if waypoint angle and elephant angle both positive or negative
		if (newanglepoint.getRadians()*this.getAngle().getRadians() >= 0) {
			if (newanglepoint.getRadians() - this.getAngle().getRadians() > 0 ) {
				rotate_dir = 1;
			} else if (newanglepoint.getRadians() - this.getAngle().getRadians() < 0) {
				rotate_dir = -1;
			} 
		}
		// different side
		if (newanglepoint.getRadians()*this.getAngle().getRadians() < 0) {
			// if both belong to 1st and 2nd quadrant ( and different side of + and - )
			if ((this.getAngle().getRadians() < 0) && (this.getAngle().getRadians() > (-90*Math.PI/180))
					&& (newanglepoint.getRadians() > 0) && (newanglepoint.getRadians() < (90*Math.PI/180)) ) {

				rotate_dir = 1;
			} else if ((newanglepoint.getRadians() < 0) && (this.getAngle().getRadians() > 0)
					&& (newanglepoint.getRadians() > (-90*Math.PI/180)) && (this.getAngle().getRadians() < (90*Math.PI/180))) {
				rotate_dir = -1;
				// both belong to 3rd and 4th quadrant
			} else if ((newanglepoint.getRadians() < 0) && (this.getAngle().getRadians() > 0)
					&& (newanglepoint.getRadians() < (-90*Math.PI/180)) && (this.getAngle().getRadians() > (90*Math.PI/180))) {
				rotate_dir = 1;
			} else if ((newanglepoint.getRadians() > 0) && (this.getAngle().getRadians() < 0)
					&& (newanglepoint.getRadians() > (90*Math.PI/180)) && (this.getAngle().getRadians() < (-90*Math.PI/180))) {
				rotate_dir = -1;
			}
		}
		
		return rotate_dir;
	}
}
