/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes an OilCan item.
 * These item(s) are scattered around the world and can be picked up by the player
 * and consumed by the call of the 'use' method.
 */
public class OilCan extends Item{

	public OilCan(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x, y, angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/items/oilcan.png"));
	}
	
	/** When a player uses (consumes) this item, an 'OilSlick' hazard is created behind
	 * the player. This new hazard is registered with the passed world. The oil slick
	 * hazard is created 41 units behind the player (since 40 units is within the collision radius).
	 * @param player instance of the player consuming this item
	 * @param world instance of the world the player resides in
	 * @return is always false since this item is single use.
	 */
	public boolean use(Player player, World world) throws SlickException {
		Angle a = player.getAngle();
		double x = player.getX() - a.getXComponent(41);
		double y = player.getY() - a.getYComponent(41);
		world.addHazard(new OilSlick(x, y, a, 0));
		// Single use item
		return false;
	}

}
