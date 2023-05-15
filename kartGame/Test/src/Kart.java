/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import org.newdawn.slick.SlickException;

public class Kart extends GameObject{
	/** Rotation speed, in radians per ms. */
    private static final double ROTATE_SPEED = 0.004;
    /** Acceleration while the player is driving, in px/ms^2. */
    private static final double ACCELERATION = 0.0005;
    private double accelFactor =1;
    
    /** Counts frames elapsed in a spin */
    private int spinFrameCount = 0;
    /** Spin state */
    private boolean doSpin = false;
    
    /** Counter to keep track of Kart instances 
     * which have crossed the finish line. */
    private static int globalCrossLine = 0;
    /** Final ranking */
    private int finalRank = 0;
    
    // construction of Kart(child class) from GameObject( parent class)
    public Kart(double x, double y, Angle angle, double velocity) throws SlickException {
    	super(x,y,angle, velocity);
    }

	//getter
    public double getRotateSpeed() {
    	return ROTATE_SPEED;
    }
    
    // boolean is true when object is dog, next boolean is true when dog is ahead of donkey
    public double getAcceleration() {
    	return ACCELERATION*accelFactor;	
    }
    
    
    public void setAccel(double factor) {
    	this.accelFactor = factor;
    }
   
    public double getAccel () {
    	return accelFactor;
    }
    
    /** move the player for a frame.
     * Adjusts the player's angle and velocity based on input, and updates the
     * player's position. Prevents the player from entering a blocking tile.
     * @param rotate_dir The player's direction of rotation
     *      (-1 for anti-clockwise, 1 for clockwise, or 0).
     * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param world The world the player is on (to get friction and blocking).
     */
    public void move(double rotate_dir, double move_dir, boolean use_item, World world) {
    	
    	//delta = 1 milisecond
    	double delta = 1;
    	// Modify the player's angle
        Angle rotateamount = new Angle(this.getRotateSpeed() * rotate_dir * delta);
    	
        // If doSpin is flipped on, perform a spin instead of moving first.
        // The spin is done over 700 frames ~0.7s
    	if (this.doSpin && this.spinFrameCount <= 700) {
    		move_dir = 0;
    		rotate_dir = 1;
    		rotateamount = new Angle(0.008);
    		this.spinFrameCount++;
    	} else {
    		// Spin end, reset spin
    		this.doSpin = false;
    		this.spinFrameCount = 0;
    	}
    	
        this.setAngle(this.getAngle().add(rotateamount));
        // Determine the friction of the current location
        double friction = world.frictionAt((int) this.getX(), (int) this.getY());
        // Modify the player's velocity. First, increase due to acceleration.
        this.setVelocity(this.getVelocity() + this.getAcceleration() * move_dir * delta);
        // Then, reduce due to friction (this has the effect of creating a
        // maximum velocity for a given coefficient of friction and
        // acceleration)
        this.setVelocity(this.getVelocity() * Math.pow(1 - friction, delta));

        // Modify the position, based on velocity
        // Calculate the amount to move in each direction
        double amount = this.getVelocity() * delta;
        // Compute the next position, but don't move there yet
        double next_x = this.getX() + this.getAngle().getXComponent(amount);
        double next_y = this.getY() + this.getAngle().getYComponent(amount);
        // If the intended destination is a blocking tile, do not move there
        // (crash) -- instead, set velocity to 0
        
        //double distance = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        //for (Kart k: world.karts) {
        if (world.blockingAt((int) next_x, (int) next_y)) {
        	this.setVelocity(0);
        } else {
        	// Flag for collision indication
        	boolean collisionOccured = false;
        	// The expected next coords of the kart is checked for possible
        	// collisions with other karts. 
        	// If the current instance is moving, it will stop on collision.
        	for (Kart k: world.getKarts()) {
        		if (this != k && this.collide(next_x, next_y, k) && k.getVelocity() != 0) {
        			this.setVelocity(0);
        			collisionOccured = true;
        		} 
        	}
        	// Moving only if no collision occurs.
        	if (!collisionOccured) {
        		this.setX(next_x);
                this.setY(next_y);
        	}   
        }
    }
    
    /** Sets the spin state 'on'.
     * When a kart is in doSpin state it will not move ahead, but instead implement
     * a spin maneuver for 700 frames.
     */
    public void spin() {
    	this.spinFrameCount = 0;
    	this.doSpin = true;
    }
    
    /** Checks whether the kart instance has crossed the finish
     * line. If it has, it will take a rank from the Class Counter variable
     * 'globalCrossLine' and make it it's final rank. 
     */
    public void checkFinish() {
    	if (this.getY() < 1026 && !hasFinished()) {
    		this.finalRank = ++Kart.globalCrossLine;
    	}
    }
    
    /** Get final rank. 
     * Rank is 0 if the kart has not finished.
     * @return Final ranking 
     */
    public int getFinalRank() {
    	return this.finalRank;
    }
    
    /** Check if the kart instance has crossed the line. */
    public boolean hasFinished() {
    	if (this.finalRank > 0) {
    		return true;
    	}
    	return false;
    }
    
}




