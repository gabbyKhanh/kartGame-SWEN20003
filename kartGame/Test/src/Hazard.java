/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.SlickException;

/** Describes a Hazard. 
 * All Hazard types implement a movement method (stationary or projectile) and
 * a method 'affect' to describe how a kart should behave if it collides with this item.
 */
public abstract class Hazard extends GameObject{

	public Hazard(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x, y, angle, velocity);
	}
	
	/** Update the state of the hazard according to the move behavior. 
	 * Both stationary and moving projectile hazards can be updated this way. 
	 * Every update checks for collision with the karts in the passed world. If there is a
	 * collision, the hazards 'affect' method implementation is invoked to run the expected 
	 * Behavior.
	 * @param world the instance of the World the hazard and karts are present in.
	 */
	public void update(World world) {
		this.move(world);
		for (Kart k: world.getKarts()) {
			if(this.collide(k)) {
				// there is no need for the hazard to be rendered/present in 
				// the world anymore (consumed).
				this.disabled = true;
				this.affect(k);
				break;
			}
		}
	}
	
	/** Movement of hazard on the map. **/
	public abstract void move(World world);
	/** Describe 'kart' behavior on collision **/
	public abstract void affect(Kart kart);
}
