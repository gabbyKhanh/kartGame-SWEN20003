/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes the Elephant enemy player.
 */
public class Elephant extends Enemy{
	/** Waypoint index */
	private static int i = 0;


	public Elephant (double x, double y, Angle angle, double velocity) throws SlickException {
		super(x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/karts/elephant.png"));
	}


	/** Movement for the Elephant.
	 * The elephant follows its waypoints without any additional behavior.
	 * The move method is invoked from here itself. The waypoint
	 * index i is used to decide the current waypoint of the current frame.
	 */
	public void update(World world, Player player) {
		// enemy always accelerates forward
		double move_dir = 1;

		int rotate_dir= 0;
		double[] Ax = getAx();
		double[] Ay = getAy();
		if (i < getAx().length) {
			// every time elephant moves, take its coordinate to be (O,O) reference to shift way point
			// angle between elephant coordinate and waypoint
			Angle anglepoint = new Angle(Math.atan((Math.abs(Ax[i]-this.getX()))/ (Math.abs((Ay[i]-this.getY())))));

			double x = Ax[i]-this.getX();
			double y = -(Ay[i]-this.getY());
	
			Angle newanglepoint = newRotationAngle(anglepoint, x, y);

			// angle between waypoint and elephant
			double rotateamount = Math.abs(newanglepoint.getRadians() - this.getAngle().getRadians());
			rotate_dir = computeRotateDir(newanglepoint);

			// turn to the waypoint
			if (Math.abs(this.getAngle().getRadians()) <= rotateamount) {
				move(rotate_dir, move_dir, false, world);
			} 

			//after finish turning, go straight till reach the circumference
			if (Math.sqrt((Math.pow(this.getX()-Ax[i], 2)) + (Math.pow(this.getY()-Ay[i], 2))) >= 250) {
				move(rotate_dir,move_dir,false, world);
				// once reach the circumference, forget the current waypoint and move to the next one
			} else {
				i++; 
			} 
		} else {
			this.setVelocity(0);
		}
	}
}











































