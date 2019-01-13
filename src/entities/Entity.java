package entities;

import java.util.ArrayList;

import main.Game;
import processing.core.PApplet;
import processing.core.PImage;
import textures.TextureLoader;

public class Entity implements Comparable<Entity> {

	public static final double ACCEL_IN_INVENTORY = .1;

	protected double x;
	protected double y;

	protected double vx;
	protected double vy;

	protected double speed;

	protected double interactRange = 0;

	protected boolean movable = true;
	protected boolean visible = true;
	protected boolean alive = true;
	protected boolean isItem = false;
	protected boolean hasInventory = false;

	protected ArrayList<CollisionBorder> collisionBox;
	protected double collisionRange = 0;

	protected Inventory inventory;

	protected String textureName;
	protected String description = "this is a thing";

	protected int renderPriority = 0;
	protected double renderYShift = 0;
	protected double scale = 1;

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
		collisionBox = new ArrayList<CollisionBorder>();
		inventory = new Inventory(this);
	}

	public void reactToHover(Game g) {

	}

	public void reactToClick(Game g) {
		
	}

	public void reactToInteract(Game g, Entity e) {
		if (isItem && e.hasInventory) {
			e.giveItem(this);
			this.alive = false;
		}
	}

	public void tick(Game g) {

	}

	public void tickInventory(Game g) {
		inventory.tick(g);
	}

	public void giveItem(Entity e) {
		inventory.addItem(e);
	}

	public void draw(PApplet p) {
		if (visible) {
			p.pushMatrix();
			p.translate((float) x, (float) y);
			p.scale((float) scale);
			p.image(getTexture(textureName), 0, 0);
			p.popMatrix();
			this.drawCollisionBox(p);
		}
	}

	public boolean touchesMouse(double mouseX, double mouseY) {
		for (CollisionBorder b : collisionBox) {
			if (b.inBorder(mouseX, mouseY)) {
				return true;
			}
		}

		return false;
	}

	protected void drawCollisionBox(PApplet p) {
		p.noFill();
		for (CollisionBorder b : collisionBox) {
			p.ellipse((float) (x + b.getX()), (float) (y + b.getY()), (float) (b.getR() * 2), (float) (b.getR() * 2));
		}
	}

	public void drawInventory(PApplet p) {
		inventory.draw(p);
	}

	/**
	 * Mouse is given in world coordinates
	 * 
	 * @param mx
	 * @param my
	 * @return
	 */
	public boolean mouseInBorder(double mx, double my) {
		double dist = (double) Math.sqrt((x - mx) * (x - mx) + (y - my) * (y - my));
		if (dist > collisionRange) {
			return false;
		}
		for (CollisionBorder b : collisionBox) {
			dist = (double) Math
					.sqrt((b.getX() + x - mx) * (b.getX() + x - mx) + (b.getY() + y - my) * (b.getY() + y - my));
			if (dist < b.getR()) {
				return true;
			}
		}
		return false;
	}

	public boolean nearEntity(Entity e) {
		return dist(e) < collisionRange + e.collisionRange;
	}

	protected void recalculateCollisionRange() {
		// collisionRange is set to farthest possible point from entity center
		collisionRange = 0;
		for (CollisionBorder b : collisionBox) {
			if (b.dist() + b.getR() > collisionRange) {
				collisionRange = b.dist() + b.getR();
			}
		}
	}

	/**
	 * Collides with given Entity shifting both entities appropriately
	 * 
	 * @param e
	 */

	public void collide(Entity e) {
		if (!this.movable && !e.movable) {
			return;
		}

		for (CollisionBorder self : collisionBox) {
			for (CollisionBorder other : e.collisionBox) {
				if (!self.collidesWith(other)) {
					continue;
				}
				double[] vect = self.collideWith(other);

				if (this.movable && !e.movable) {
					this.shiftX(vect[0]);
					this.shiftY(vect[1]);
				}
				if (!this.movable && e.movable) {
					e.shiftX(-vect[0]);
					e.shiftY(-vect[1]);
				}
				if (this.movable && e.movable) {
					this.shiftX(vect[0] / 2d);
					this.shiftY(vect[1] / 2d);
					e.shiftX(-vect[0] / 2d);
					e.shiftY(-vect[1] / 2d);
				}
			}
		}
	}

	public boolean collidesWith(Entity e) {
		for (CollisionBorder self : collisionBox) {
			for (CollisionBorder other : e.collisionBox) {
				if (self.collidesWith(other)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getDescription() {
		return description;
	}

	protected PImage getTexture(String name) {
		return TextureLoader.getTexture(name);
	}

	public double getX() {
		return (double) x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return (double) y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void shiftX(double vect) {
		x += vect;
	}

	public void shiftY(double dy) {
		y += dy;
	}

	public boolean isAlive() {
		return alive;
	}

	public double getInteractRange() {
		return interactRange;
	}

	public double dist(Entity e) {
		return (double) Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
	}

	protected class CollisionBorder {

		private double x, y; // relative to the x and y of the entity
		// these are the top left corner
		private double r; // radius of ball

		protected Entity e; // Entity that this CollisionBorder belongs to

		public CollisionBorder(Entity e, double x, double y, double r) {
			super();
			this.e = e;

			this.x = x;
			this.y = y;
			this.r = r;
		}

		public CollisionBorder getCopy(Entity e) {
			return new CollisionBorder(e, x, y, r);
		}

		/**
		 * Returns if the given world coordinate is in the border
		 * 
		 * @param mouseX
		 * @param mouseY
		 * @return
		 */
		public boolean inBorder(double mouseX, double mouseY) {
			double dist = Math.sqrt((e.x + getX() - mouseX) * (e.x + getX() - mouseX)
					+ (e.y + getY() - mouseY) * (e.y + getY() - mouseY));
			return dist < getR();
		}

		public boolean collidesWith(CollisionBorder b) {
			return dist(b) < b.getR() + getR();
		}

		/**
		 * Returns vector for direction of motion from collision Vector length
		 * corresponds to overlap of CollisionBorders
		 * 
		 * @param b CollisionBorder to collide with
		 * @return
		 */
		public double[] collideWith(CollisionBorder b) {
			double dist = dist(b);
			if (dist == 0 || dist > b.getR() + getR()) {
				return new double[] { 0f, 0f };
			}
			double overlap = (b.getR() + getR() - dist) / dist;
			return new double[] { overlap * getDeltaX(b), overlap * getDeltaY(b) };

		}

		public double getX() {
			return x * e.scale;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y * e.scale;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getR() {
			return r * e.scale;
		}

		public void setR(double r) {
			this.r = r;
		}

		private double dist(CollisionBorder b) {
			return (double) Math.sqrt(getDeltaX(b) * getDeltaX(b) + getDeltaY(b) * getDeltaY(b));
		}

		public double dist() {
			return (double) Math.sqrt(getX() * getX() + getY() * getY());
		}

		private double getDeltaX(CollisionBorder b) {
			return (getX() + e.x - b.getX() - b.e.getX());
		}

		private double getDeltaY(CollisionBorder b) {
			return (getY() + e.y - b.getY() - b.e.getY());
		}

	}

	public class Inventory {
		private Entity e;
		private ArrayList<Entity> inventory;
		private double width;
		private double height;

		public Inventory(Entity e) {
			this.e = e;
			inventory = new ArrayList<Entity>();
		}

		public void draw(PApplet p) {
			p.fill(193, 123, 42);
			p.rect(0, 0, (float) width, (float) height);
			for (Entity e : inventory) {
				e.draw(p);
			}
		}

		public void tick(Game g) {
			for (Entity e : inventory) {

				// these move entity back into box
				if (e.getX() - e.collisionRange < 0) {
					e.setX(e.collisionRange);
				}
				if (e.getX() + e.collisionRange > width) {
					e.setX(width - e.collisionRange);
				}
				if (e.getY() - e.collisionRange < 0) {
					e.setY(e.collisionRange);
				}
				if (e.getY() + e.collisionRange > height) {
					e.setY(height - e.collisionRange);
					e.vy = 0;
				}

				// gravity and motion
				if (e.getY() + e.collisionRange + e.vy + ACCEL_IN_INVENTORY < height) {
					e.vy += ACCEL_IN_INVENTORY;
					e.setY(e.getY() + e.vy);
				}
			}

			for (int i = 0; i < inventory.size() - 1; i++) {
				for (int j = i + 1; j < inventory.size(); j++) {
					if (inventory.get(i).nearEntity(inventory.get(j))) {
						if (inventory.get(i).collidesWith(inventory.get(j))) {
							inventory.get(i).vy = 0;
						}
						inventory.get(i).collide(inventory.get(j));
					}
				}
			}

		}

		public void addItem(Entity e) {
			e.setX(Math.random()*width);
			e.setY(0);
			inventory.add(e);
		}

		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
			this.height = height;
		}

	}

	public int compareTo(Entity e) {
		if (e.renderPriority != renderPriority) {
			return renderPriority - e.renderPriority;
		}
		return (int) (y + renderYShift - e.y - e.renderYShift);
	}

	/**
	 * This creates another entity with all the same parameters. The entity is
	 * placed at the given X and Y coordinates.
	 * 
	 * @return
	 */
	public Entity createCopy(double x, double y) {
		Entity e = new Entity(x, y);
		e.speed = this.speed;
		e.movable = this.movable;
		e.visible = this.visible;
		e.alive = this.alive;

		ArrayList<CollisionBorder> collisionBox = new ArrayList<CollisionBorder>();
		for (CollisionBorder b : this.collisionBox) {
			collisionBox.add(b.getCopy(e));
		}
		e.collisionBox = collisionBox;
		e.collisionRange = this.collisionRange;

		e.textureName = this.textureName;
		e.description = this.description;

		e.renderPriority = this.renderPriority;
		e.renderYShift = this.renderYShift;

		return e;
	}

}
