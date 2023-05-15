/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes the Octopus enemy player.
 */
public class Octopus extends Enemy{
	private static int m = 0;

	public Octopus(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/karts/octopus.png"));

	}

	/** Movement for the Octopus.
	 * The dog follows its waypoints when it's not 100 to 250 pixel around player
	 * The move method is invoked from here itself.
	 */

	public void update(World world, Player player) {
		// enemy always accelerates forward
		double move_dir = 1;

		int rotate_dir= 0;
		double[] Ax = getAx();
		double[] Ay = getAy();


		// when in boundary, treat player as a waypoint 
		// in the boundary, go straight to player
		if (Math.sqrt((Math.pow(player.getX() - this.getX(), 2)) + (Math.pow(player.getY() - this.getY(), 2))) >= 100 &&
				Math.sqrt((Math.pow(player.getX() - this.getX(), 2)) + (Math.pow(player.getY() - this.getY(), 2))) <= 250) {
			//angle between octopus coordinate and player coordinate
			Angle anglepoint1 = new Angle(Math.atan((Math.abs(player.getX()-this.getX()))/ (Math.abs((player.getY()-this.getY())))));

			double x1 = player.getX()-this.getX();
			double y1 = -(player.getY()-this.getY());

			Angle newanglepoint1 = newRotationAngle(anglepoint1, x1, y1);

			//angle between waypoint and octopus
			double rotateamount1 = Math.abs(newanglepoint1.getRadians() - this.getAngle().getRadians());

			// if player angle and octopus angle both positive or negative
			rotate_dir = computeRotateDir(newanglepoint1);

			// turn to the player
			if (Math.abs(this.getAngle().getRadians()) < rotateamount1) {
				move(rotate_dir, move_dir, false, world);
			}
			// Move straight
			move(0, move_dir, false, world);
		} else {
			if (m < getAx().length) {
				//angle between octopus coordinate and waypoint
				Angle anglepoint = new Angle(Math.atan((Math.abs(Ax[m]-this.getX()))/ (Math.abs((Ay[m]-this.getY())))));

				double x = Ax[m]-this.getX();
				double y = -(Ay[m]-this.getY());

				Angle newanglepoint = newRotationAngle(anglepoint, x, y);
				//angle between waypoint and octopus
				double rotateamount = Math.abs(newanglepoint.getRadians() - this.getAngle().getRadians());
				rotate_dir = computeRotateDir(newanglepoint);
				
				// turn to the waypoint
				if (Math.abs(this.getAngle().getRadians()) < rotateamount) {
					move(rotate_dir, move_dir, false, world);
				} 
				//after finish turning, go straight till reach the circumference
				if (Math.sqrt((Math.pow(this.getX()-Ax[m], 2)) + (Math.pow(this.getY()-Ay[m], 2))) >= 250) {
					move(rotate_dir, move_dir,false, world); 
				} else {
					// forget about current waypoint, move to next one
					m++; 
				} 
			} else {
				this.setVelocity(0);
			}

		}
	}

}

