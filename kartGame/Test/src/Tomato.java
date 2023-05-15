/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Describes a Tomato item scattered around the map.
 * Consumption of this item by the player introduces a TomatoProjectile
 * hazard which can be used the player later.
 */
public class Tomato extends Item {

	public Tomato(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x, y, angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/items/tomato.png"));
	}
	
	/** Consume the tomato item instance.
	 * Using this items introduces a tomato projectile hazard which can be
	 * used by the player. This projectile is launched from 40 units ahead of the
	 * player.
	 * 
	 * @param player the player instance colliding with the item.
	 * @param world the world instance in which the player resides.
	 * @return is always false since this item is a single use item.
	 */
	public boolean use(Player player, World world) throws SlickException {
		Angle a = player.getAngle();
		double x = player.getX() + a.getXComponent(40);
		double y = player.getY() + a.getYComponent(40);
		world.addHazard(new TomatoProjectile(x, y, a, 0));
		return false;
	}
}
