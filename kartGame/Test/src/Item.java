/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.SlickException;

/** Describes an item scattered around the map.
 * This abstract class implements methods which would describe an item usable by the player.
 */
public abstract class Item extends GameObject{
	
	public Item(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x, y, angle, velocity);
	}

	public void drawImage(int i, int panel_top, Item item) {
		// to to Auto-generated method stub

		this.getImg().draw(i, panel_top);
	} 
	
	/** Describes the effect of using the item on the player.
	 * @return A flag indicating if the item is single use or is still going to be used
	 * over a period of time. 
	 **/
	public abstract boolean use(Player player, World world) throws SlickException;
}
