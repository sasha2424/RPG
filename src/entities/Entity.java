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

	public boolean nearEntity(Entity e) {
		return dist(e) < collisionRange + e.collisionRange;
	}

	public void collide(Entity e) {
		// TODO
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
		float x, y; // relative to the x and y of the entity
					// these are the top left corner
		float r; // radius of ball

		public CollisionBorder(float x, float y, float r) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
		}

		public boolean collidesWith(CollisionBorder b) {
			return dist(b) < b.getR() + r;
		}

		/***
		 * Returns Unit vector for direction of motion from collision
		 * 
		 * @param b CollisionBorder to collide with
		 * @return
		 */
		public float[] collideWith(CollisionBorder b) {
			float dist = dist(b);
			if (dist == 0) {
				return new float[] { 0f, 0f };
			}
			return new float[] { (x - b.getX()) / dist, (y - b.getY()) / dist };
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
			return (float) Math.sqrt((x - b.getX()) * (x - b.getX()) + (y - b.getY()) * (y - b.getY()));
		}

		public float dist() {
			return (float) Math.sqrt(x * x + y * y);
		}

	}

}
