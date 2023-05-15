/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes the Dog enemy player.
 */
public class Dog extends Enemy{
	/** waypoint index */
	private static int w = 0;
	
	
	public Dog(double x, double y, Angle angle, double velocity) throws SlickException {
		super (x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/karts/dog.png"));
	}

	/** Movement for the Dog.
	 * The dog follows its waypoints and adjusts its acceleration depends on its postion with player.
	 * The move method is invoked from here itself.
	 */
	
	public void update(World world, Player player) {
		double move_dir = 1;

		int rotate_dir= 0;
		double[] Ax = getAx();
		double[] Ay = getAy();
		if (w < getAx().length) {
			// every time dog moves, take its coordinate to be (O,O) reference to shift way point
			// angle between dog coordinate and waypoint
			Angle anglepoint = new Angle(Math.atan((Math.abs(Ax[w]-this.getX()))/ (Math.abs((Ay[w]-this.getY())))));


			double x = Ax[w]-this.getX();
			double y = -(Ay[w]-this.getY());

			Angle newanglepoint = newRotationAngle(anglepoint, x, y);
			
			//angle between waypoint and dog
			double rotateamount = Math.abs(newanglepoint.getRadians() - this.getAngle().getRadians());

			rotate_dir = computeRotateDir(newanglepoint);
			if (player.getY() >= this.getY()) {
				this.setAccel(0.9);
			} else {
				this.setAccel(1.1);
			}

			// turn to the waypoint

			if (Math.abs(this.getAngle().getRadians()) <= rotateamount) {
				move(rotate_dir, move_dir, false, world);

			} 

			//after finish turning, go straight till reach the circumference
			if (Math.sqrt((Math.pow(this.getX()-Ax[w], 2)) + (Math.pow(this.getY()-Ay[w], 2))) >= 250) {
				move(rotate_dir,move_dir,false, world);

				// once reach the circumference, forget the current waypoint and move to the next one
			} else {
				w++; 
			} 
		} else {
			this.setVelocity(0);
		}

	}
}