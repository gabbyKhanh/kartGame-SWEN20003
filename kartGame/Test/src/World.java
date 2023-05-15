/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Modified solution from Matt Giuca <mgiuca>
 * Student: Thi Mai Khanh Nguyen <nguyen6@unimelb.edu.au>
 * student ID: 683889
 */

import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** The string identifier to use for looking up the map friction. */
	private static final String MAP_FRICTION_PROPERTY = "friction";

	/** The world map (two dimensional grid of tiles).
	 * The concept of tiles is a private implementation detail to World. All
	 * public methods deal with pixels, not tiles.
	 */
	private TiledMap map;

	/** Panel displaying player's ranking and item. */
	private Panel panel;

	/** The player's kart. */
	private Player player;

	/** Enemy karts. */
	private Dog dog;
	private Elephant elephant;
	private Octopus octopus;

	/** List of Item(s) currently in the world, and to be rendered. */
	public List<Item> items = new ArrayList<Item>();
	/** List of Hazard(s) currently in the world, and to be rendered. */
	public List<Hazard> hazards = new ArrayList<Hazard>();
	
	/** Array of all playing karts, 4 of them. */
	private Kart[] karts;

	/** The camera used to track what the screen should show. */
	private Camera camera;

	/** Create a new World object. */
	public World() throws SlickException {
		map = new TiledMap(Game.ASSETS_PATH + "/map.tmx", Game.ASSETS_PATH);
		player = new Player(1332, 13086, Angle.fromDegrees(0), 0);
		camera = new Camera(Game.SCREENWIDTH, Game.SCREENHEIGHT, this.player);
		dog = new Dog(1404, 13086, Angle.fromDegrees(0), 0);
		octopus = new Octopus(1476, 13086, Angle.fromDegrees(0), 0);
		elephant = new Elephant(1260, 13086, Angle.fromDegrees(0), 0);

		// init karts array
		this.karts = new Kart[4];
		this.karts[0] = player;
		this.karts[1] = elephant;
		this.karts[2] = dog;
		this.karts[3] = octopus;

		panel = new Panel();

		/* Initialize all Items and add to the world */
		this.items.add(new OilCan(1350, 12438, Angle.fromDegrees(0),0));
		this.items.add(new OilCan(864, 7614, Angle.fromDegrees(0),0));
		this.items.add(new OilCan(1962, 6498, Angle.fromDegrees(0),0));
		this.items.add(new OilCan(1314, 3690, Angle.fromDegrees(0),0));
		// next 4 are tomato
		this.items.add(new Tomato(990, 11610, Angle.fromDegrees(0),0));
		this.items.add(new Tomato(1206, 5130, Angle.fromDegrees(0),0));
		this.items.add(new Tomato(1206, 3690, Angle.fromDegrees(0),0));
		this.items.add(new Tomato(1422, 2322, Angle.fromDegrees(0),0));
		// last 4 are boost
		this.items.add(new Boost(990, 10242, Angle.fromDegrees(0),0));
		this.items.add(new Boost(1818, 6534, Angle.fromDegrees(0),0));
		this.items.add(new Boost(990, 4302, Angle.fromDegrees(0),0));
		this.items.add(new Boost(1926, 3510, Angle.fromDegrees(0),0));
	}

	/** Get the width of the game world in pixels. */
	public int getWidth()
	{
		return map.getWidth() * map.getTileWidth();
	}

	/** Get the height of the game world in pixels. */
	public int getHeight()
	{
		return map.getHeight() * map.getTileHeight();
	}

	/** Update the game state for a frame.
	 * @param rotate_dir The player's direction of rotation
	 *      (-1 for anti-clockwise, 1 for clockwise, or 0).
	 * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void update(double rotate_dir, double move_dir, boolean use_item)
			throws SlickException
	{
		// Rotate and move the player by rotate_dir and move_dir
		player.update(rotate_dir, move_dir, use_item, this);
		camera.follow(player);

		// Enemy moves
		elephant.update(this, player);
		dog.update(this, player);
		octopus.update(this, player);     

		// Remove any disabled item from the items list 
		// Only the player will check for collision with items.
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item.disabled) {
				items.remove(item);	
			}
		}

		/** Work through the hazards list
		 * 1. removing all disabled hazards
		 * 2. checking for collision with all the karts and invoke
		 * behavior.
		 */
		for (int i = 0; i < hazards.size(); i++) {
			Hazard h = hazards.get(i);
			h.move(this);
			if (h.disabled) {
				hazards.remove(h);	
			}
			// Check collision between karts and the hazard h
			kartHazard(karts, h);
		}
		
		// Every kart checks if it has crossed the final line so that
		// the ranking can be consistent.
		for (Kart k: karts) {
			k.checkFinish();
		}
	}

	/** Check if any kart is colliding with a hazard.
	 * On collision, the hazard invoke's its behavior on the colliding
	 * kart, and is removed.
	 * @param karts all karts allowed to collide with the hazard.
	 * @param hazard 
	 */
	public void kartHazard(Kart[] karts, Hazard hazard) {
		for (Kart k: karts) {
			if (hazard.collide(k)) {
				hazard.affect(k);
				hazard.disable();
				break;
			}
		}
	}

	/** Render the entire screen, so it reflects the current game state.
	 * @param g The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g)
			throws SlickException
			{
		// Calculate the camera location (in tiles) and offset (in pixels).
		// Render 24x18 tiles of the map to the screen, starting from the
		// camera location in tiles (rounded down). Begin drawing at a
		// negative offset relative to the screen, to ensure smooth scrolling.
		int cam_tile_x = camera.getLeft() / map.getTileWidth();
		int cam_tile_y = camera.getTop() / map.getTileHeight();
		int cam_offset_x = camera.getLeft() % map.getTileWidth();
		int cam_offset_y = camera.getTop() % map.getTileHeight();        
		int screen_tilew = camera.getWidth() / map.getTileWidth() + 2;
		int screen_tileh = camera.getHeight() / map.getTileHeight() + 2;

		
	
		map.render(
				-cam_offset_x, -cam_offset_y,
				cam_tile_x, cam_tile_y,
				screen_tilew, screen_tileh);

		// Display bottom status panel, with player ranking and item
		panel.render(g, player.getRanking(elephant, octopus, dog), player.getCurrentItem());

		// Render the player
		player.render(g, camera);

		dog.render(g, camera);
		elephant.render(g, camera);
		octopus.render(g, camera);
		
		// Rendering items
		for (Item i: this.items) {
			i.render(g, camera);
		}       

		// Rendering Hazards
		for (Hazard h: this.hazards) {
			h.render(g, camera);
		}     
		// Rendering end game text if the game is over.
		if (player.hasFinished()) {
			g.drawString("YOU CAME: " + player.getFinalRank(), camera.getWidth()/2 - 50, camera.getHeight()/2 + 100);
		}
		
			}

	/** Get the friction coefficient of a map location.
	 * @param x Map tile x coordinate (in pixels).
	 * @param y Map tile y coordinate (in pixels).
	 * @return Friction coefficient at that location.
	 */
	public double frictionAt(int x, int y)
	{
		int tile_x = x / map.getTileWidth();
		int tile_y = y / map.getTileHeight();
		int tileid = map.getTileId(tile_x, tile_y, 0);
		String friction =
				map.getTileProperty(tileid, MAP_FRICTION_PROPERTY, null);
		return Double.parseDouble(friction);
	}

	/** Determines whether a particular map location blocks movement.
	 * @param x Map tile x coordinate (in pixels).
	 * @param y Map tile y coordinate (in pixels).
	 * @return true if the tile at that location blocks movement.
	 */
	public boolean blockingAt(int x, int y)
	{
		return frictionAt(x, y) >= 1;
	}

	public void addHazard(Hazard hazard) {
		hazards.add(hazard);
	}

	public Kart[] getKarts() {
		return karts;
	} 

	public List<Item> getItems() {
		return items;
	} 

}
