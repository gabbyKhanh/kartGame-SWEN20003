/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Modified solution from Matt Giuca <mgiuca>
 * Student: Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import java.util.Arrays;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** The player's kart (Donkey).
 */
public class Player extends Kart
{
	/** Holds the currentItem in the player's stash */
	private Item currentItem = null;
	
	/** Flag for indicating if a item is in use currently */
	private boolean itemInUse = false;

	/** Creates a new Player.
	 * @param x The player's initial X location (pixels).
	 * @param y The monster's initial Y location (pixels).
	 * @param angle The player's initial angle.
	 */
	public Player(double x, double y, Angle angle, double velocity)
			throws SlickException
			{
		super(x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/karts/donkey.png"));

			}

	/** Update the player for a frame.
	 * Adjusts the player's angle and velocity based on input, and updates the
	 * player's position. Prevents the player from entering a blocking tile.
	 * @param rotate_dir The player's direction of rotation
	 *      (-1 for anti-clockwise, 1 for clockwise, or 0).
	 * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
	 * @param delta Time passed since last frame (milliseconds).
	 * @param world The world the player is on (to get friction and blocking).
	 * @throws SlickException 
	 */
	public void update(double rotate_dir, double move_dir, boolean use_item, World world) throws SlickException
	{    
		// Do nothing if the race has ended.
		if (hasFinished()) {
    		return;
    	} 
		
		// calling Kart's move
		this.move(rotate_dir, move_dir, use_item, world);
		
		// Checking for collision with the items on the map
		for (int i = 0; i < world.getItems().size(); i++) {
			Item k = world.getItems().get(i);
			if(this.pickUp(k)) {
				// set as player current item and remove from the world
				this.setCurrentItem(k);
				k.disabled = true;
			}
		}
		
		/* Checking if some item is still being used.
		 * If it is then, the item's 'use' method is invoked again, 
		 * until the 'use' method returns false (indicating, completion
		 * of use).
		 */
		// If it is, then 
		if (this.itemInUse) {
			this.itemInUse = this.currentItem.use(this, world);
			// reset
			if (!this.itemInUse)
				this.currentItem = null;
		} else if (use_item && this.currentItem != null) {
			this.itemInUse = this.currentItem.use(this, world);
			// reset
			if (!this.itemInUse)
				this.currentItem = null;
		}
	}

	/** Check for collision with a GameObject.
	 * @param a perceived item scattered around the map
	 * @return
	 */
	public boolean pickUp(GameObject a) {
		if (this.collide(a)) {
			return true;
		} else {
			return false;
		}
	}

	/** Set the player's currently held item */
	public void setCurrentItem(Item a) {
		this.currentItem = a;
	}

	// getter
	public Item getCurrentItem() {
		return currentItem;
	}

	/** Compute the player's current ranking by comparing the y-coordinates
	 * with all the other carts.
	 * @param elephant
	 * @param octopus
	 * @param dog
	 * @return the current rank the player is at.
	 */
	public int getRanking(Kart elephant, Kart octopus, Kart dog) {
		int[] array = {(int)this.getY(), (int)elephant.getY(), (int)octopus.getY(), (int)dog.getY()};
		int k = 0;
		Arrays.sort(array);
		for (int i = 0; i < array.length; i++) {
			if ((int)this.getY() == array[i]) {
				k = i+1;
			}
		}
		return k;
	}
}

