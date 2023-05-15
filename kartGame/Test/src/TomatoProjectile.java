/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Modified solution from Matt Giuca <mgiuca>
 * Student: Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TomatoProjectile extends Hazard  {

	private final double TOMATO_VELOCITY = 1.7;

	private double TOMATOACCEL = 0.0008;
	public TomatoProjectile(double x, double y, Angle angle, double velocity) throws SlickException {
		super(x,y,angle, velocity);
		setImg(new Image(Game.ASSETS_PATH + "/items/tomato-projectile.png"));
	}

	public void move(World world) {
		setX(getX() + getAngle().getXComponent(this.TOMATO_VELOCITY));
		setY(getY() + getAngle().getYComponent(this.TOMATO_VELOCITY));

		if (world.blockingAt((int) getX(), (int) getY())) {
			// item disappear
			world.hazards.remove(this);
		} else {	
			for (Kart k: world.getKarts()) {
				if (this.collide(getX(), getY(), k)) {

					// k spins around for 0.7 seconds
				} 
			}   
		}
	}

	public double getTomatoAccel() {
		return TOMATOACCEL;
	}

	// kart a is the cart in front
	public void affect (Kart a) {
		a.spin();
	}


}