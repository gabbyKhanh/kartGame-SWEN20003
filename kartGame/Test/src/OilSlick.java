/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes an oil slick hazard.
 * The effect of colliding with this hazard is spinning.
 */
public class OilSlick extends Hazard  {
	public OilSlick(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/items/oilslick.png"));
	}
	
	/** Stationary hazard, nothing to do for move. **/
	public void move(World world) {};

	/** Invoke the spin behavior of the kart passed.
	 * @param a kart which collided with the oil slick isntance
	 */
	public void affect (Kart a) {
		a.spin();
	}
}
