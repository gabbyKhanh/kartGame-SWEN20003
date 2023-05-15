/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes a boost item.
 * Consuming this item gives the player increased acceleration for ~3 seconds.
 * Timing for 3 seconds is computed by counting the number of elapsed frames.
 */
public class Boost extends Item{

	/** Counting elapsed frames **/
	private int frameCount = 0;
	
	public Boost(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x, y, angle, velocity);	
		setImg(new Image(Game.ASSETS_PATH + "/items/boost.png"));
	}

	/** Describe behavior of the player on using this item.
	 * This item is used over a period of 3 seconds. While it is within this
	 * period, it returns true indicating that the item is has to be used 
	 * every frame. Counting the calls to update and hence use, and with an 
	 * assumption of 1000 frames per second, 3 seconds is computed. Once this
	 * period is over, 'false' is returned to finish using this item.
	 */
	public boolean use(Player player, World world) throws SlickException {
		if (this.frameCount <= 3000 ) {
			player.setAccel(1.7);
			this.frameCount++;
			return true;
		} else {
			player.setAccel(1);
			this.frameCount = 0;
			return false;
		}
	}
}

