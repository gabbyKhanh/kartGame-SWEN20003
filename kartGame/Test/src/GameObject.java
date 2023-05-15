/** SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * @author Thi Mai Khanh (Gabby) Nguyen <nguyen6@unimelb.edu.au>
 */

import java.awt.geom.Point2D;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Implementation of an in game Object.
 * Describes basic state and behavior possible for any object being rendered
 * or updated in the game.
 */
public class GameObject {
	/** The image of the object. */
    private Image img;

    /** The X coordinate of the object (pixels). */
    private double x;
    /** The Y coordinate of the object (pixels). */
    private double y;
    
    /** The disable flag stops the rendering of the object. */
    protected boolean disabled = false;
    
    /** The angle the object is currently facing.
     * Note: This is in neither degrees nor radians -- the Angle class allows
     * angles to be manipulated without worrying about units, and the angle
     * value can be extracted in either degrees or radians.
     */
    private Angle angle;
    /** The object's current velocity (px/ms). */
    private double velocity;
    
    /** Creates a new GameObject.
     * @param x The game object's initial X location (pixels).
     * @param y The game object's initial Y location (pixels).
     * @param angle The kart's initial angle.
     */
    public GameObject(double x, double y, Angle angle, double velocity) 
    throws SlickException
    {
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
        this.setVelocity(0);
    }
    
	/** Draw the player to the screen at the correct place.
     * @param g The current Graphics context.
     * @param camera The camera to draw relative to.
     */
    public void render(Graphics g, Camera camera)
    {
        // Calculate the player's on-screen location from the camera
    	if (!disabled) {
	        int screen_x = (int) (getX() - camera.getLeft());
	        int screen_y = (int) (getY() - camera.getTop());
	        getImg().setRotation((float) getAngle().getDegrees());
	        getImg().drawCentered(screen_x, screen_y);
    	} 
    }
    
    public void disable() {
    	this.disabled = true;
    }
    
    public void enable() {
    	this.disabled = false;
    }
	
    /** Check collision with another GameObject instance.
     * A collision occurs when the distance between two points is <= 40.
     * @param a The second GameObject instance being compared
     * @return indication of whether a collision should be registered or not.
     */
	public boolean collide(GameObject a) {
		Point2D.Double p = new Point2D.Double(this.x, this.y);
		Point2D.Double t = new Point2D.Double(a.getX(), a.getY());
		return p.distance(t) <= 40;
	}
	
	/** Check collision between a Point and GameObject instance.
	 * A Point2D instance is created by the given x and y coords and compared
	 * with another GameObject's Point2D representation for collision.
	 * @param x 
	 * @param y
	 * @param a
	 * @return indication of a collision
	 */
	public boolean collide(double x, double y, GameObject a) {
		Point2D.Double p = new Point2D.Double(x, y);
		Point2D.Double t = new Point2D.Double(a.getX(), a.getY());
		return p.distance(t) <= 40;
	}
	
	//getters and setters
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Angle getAngle() {
		return angle;
	}

	public void setAngle(Angle angle) {
		this.angle = angle;
	}
	
	public void setVelocity(double velocity) {
	    this.velocity = velocity;
	}
	    
	public double getVelocity() {
	    return velocity;
	}
}
