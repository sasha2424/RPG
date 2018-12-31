package entities;

import java.util.ArrayList;

import main.Game;
import processing.core.PApplet;
import processing.core.PImage;
import textures.TextureLoader;

public abstract class Entity {

	protected float x;
	protected float y;

	protected boolean movable;

	protected ArrayList<CollisionBorder> collisionBox;
	protected float collisionRange;

	protected Entity(float x, float y) {
		this.x = x;
		this.y = y;
		collisionBox = new ArrayList<CollisionBorder>();

		setStats();

		// collisionRange is set to farthest possible point from entity center
		collisionRange = 0;
		for (CollisionBorder b : collisionBox) {
			if (b.dist() + b.r > collisionRange) {
				collisionRange = b.dist() + b.r;
			}
		}

	}

	public abstract void setStats();

	public abstract void reactToHover(Game g);

	public abstract void reactToClick(Game g);

	public abstract void draw(PApplet p);

	public abstract void tick(Game g);

	protected void drawCollisionBox(PApplet p) {
		for (CollisionBorder b : collisionBox) {
			p.ellipse(x + b.getX(), y + b.getY(), b.r * 2, b.r * 2);
		}
	}

	/**
	 * Mouse is given in world coordinates
	 * 
	 * @param mx
	 * @param my
	 * @return
	 */
	public boolean mouseInBorder(float mx, float my) {
		float dist = (float) Math.sqrt((x - mx) * (x - mx) + (y - my) * (y - my));
		if (dist > collisionRange) {
			return false;
		}
		for (CollisionBorder b : collisionBox) {
			dist = (float) Math.sqrt((b.x + x - mx) * (b.x + x - mx) + (b.y + y - my) * (b.y + y - my));
			if (dist < b.r) {
				return true;
			}
		}
		return false;
	}

	public boolean nearEntity(Entity e) {
		return dist(e) < collisionRange + e.collisionRange;
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
				float[] vect = self.collideWith(other);

				if (this.movable && !e.movable) {
					this.shiftX(vect[0]);
					this.shiftY(vect[1]);
				}
				if (!this.movable && e.movable) {
					e.shiftX(-vect[0]);
					e.shiftY(-vect[1]);
				}
				if (this.movable && e.movable) {
					this.shiftX(vect[0] / 2f);
					this.shiftY(vect[1] / 2f);
					e.shiftX(-vect[0] / 2f);
					e.shiftY(-vect[1] / 2f);
				}
			}
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	protected PImage getTexture(String name) {
		return TextureLoader.getTexture(name);
	}

	public void shiftX(float dx) {
		x += dx;
	}

	public void shiftY(float dy) {
		y += dy;
	}

	private float dist(Entity e) {
		return (float) Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
	}

	protected class CollisionBorder {
		protected float x, y; // relative to the x and y of the entity
		// these are the top left corner
		protected float r; // radius of ball

		protected Entity e; // Entity that this CollisionBorder belongs to

		public CollisionBorder(Entity e, float x, float y, float r) {
			super();
			this.e = e;
			this.x = x;
			this.y = y;
			this.r = r;
		}

		public boolean collidesWith(CollisionBorder b) {
			return dist(b) < b.getR() + r;
		}

		/**
		 * Returns vector for direction of motion from collision Vector length
		 * corresponds to overlap of CollisionBorders
		 * 
		 * @param b CollisionBorder to collide with
		 * @return
		 */
		public float[] collideWith(CollisionBorder b) {
			float dist = dist(b);
			if (dist == 0 || dist > b.getR() + r) {
				return new float[] { 0f, 0f };
			}
			float overlap = b.getR() + r - dist;
			return new float[] { overlap * getDeltaX(b) / dist, overlap * getDeltaY(b) / dist };

		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getR() {
			return r;
		}

		public void setR(float r) {
			this.r = r;
		}

		private float dist(CollisionBorder b) {
			return (float) Math.sqrt(getDeltaX(b) * getDeltaX(b) + getDeltaY(b) * getDeltaY(b));
		}

		public float dist() {
			return (float) Math.sqrt(x * x + y * y);
		}

		private float getDeltaX(CollisionBorder b) {
			return (x + e.x - b.getX() - b.e.getX());
		}

		private float getDeltaY(CollisionBorder b) {
			return (y + e.y - b.getY() - b.e.getY());
		}

	}

}
