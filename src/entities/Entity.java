package entities;

import java.util.ArrayList;

import main.Game;
import processing.core.PApplet;
import processing.core.PImage;
import textures.TextureLoader;

public abstract class Entity {

	protected double x;
	protected double y;

	protected boolean movable;

	protected ArrayList<CollisionBorder> collisionBox;
	protected double collisionRange;

	protected Entity(double x, double y) {
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
			p.ellipse((float) (x + b.getX()), (float) (y + b.getY()), (float) (b.r * 2), (float) (b.r * 2));
		}
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
			dist = (double) Math.sqrt((b.x + x - mx) * (b.x + x - mx) + (b.y + y - my) * (b.y + y - my));
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

		double[] selfNew = new double[2];
		double[] otherNew = new double[2];
		double count = 0;

		for (CollisionBorder self : collisionBox) {
			for (CollisionBorder other : e.collisionBox) {
				if (!self.collidesWith(other)) {
					continue;
				}
				double[] vect = self.collideWith(other);

				if (this.movable && !e.movable) {
					selfNew[0] = (vect[0] + selfNew[0] * count) / (count + 1);
					selfNew[1] = (vect[1] + selfNew[1] * count) / (count + 1);
					count++;
				}
				if (!this.movable && e.movable) {
					otherNew[0] = (-vect[0] + otherNew[0] * count) / (count + 1);
					otherNew[1] = (-vect[1] + otherNew[1] * count) / (count + 1);
					count++;
				}
				if (this.movable && e.movable) {
					selfNew[0] = (vect[0] / 2d + selfNew[0] * count) / (count + 1);
					selfNew[1] = (vect[1] / 2d + selfNew[1] * count) / (count + 1);
					otherNew[0] = (-vect[0] / 2d + otherNew[0] * count) / (count + 1);
					otherNew[1] = (-vect[1] / 2d + otherNew[1] * count) / (count + 1);
					count++;
				}
			}
		}
		this.shiftX(selfNew[0]);
		this.shiftY(selfNew[1]);
		e.shiftX(otherNew[0]);
		e.shiftY(otherNew[1]);
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

	protected PImage getTexture(String name) {
		return TextureLoader.getTexture(name);
	}

	public void shiftX(double vect) {
		x += vect;
	}

	public void shiftY(double dy) {
		y += dy;
	}

	private double dist(Entity e) {
		return (double) Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY()));
	}

	protected class CollisionBorder {
		protected double x, y; // relative to the x and y of the entity
		// these are the top left corner
		protected double r; // radius of ball

		protected Entity e; // Entity that this CollisionBorder belongs to

		public CollisionBorder(Entity e, double x, double y, double r) {
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
		public double[] collideWith(CollisionBorder b) {
			double dist = dist(b);
			if (dist == 0 || dist > b.getR() + r) {
				return new double[] { 0f, 0f };
			}
			double overlap = b.getR() + r - dist;
			return new double[] { overlap * getDeltaX(b) / dist, overlap * getDeltaY(b) / dist };

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

		public double getR() {
			return r;
		}

		public void setR(double r) {
			this.r = r;
		}

		private double dist(CollisionBorder b) {
			return (double) Math.sqrt(getDeltaX(b) * getDeltaX(b) + getDeltaY(b) * getDeltaY(b));
		}

		public double dist() {
			return (double) Math.sqrt(x * x + y * y);
		}

		private double getDeltaX(CollisionBorder b) {
			return (x + e.x - b.getX() - b.e.getX());
		}

		private double getDeltaY(CollisionBorder b) {
			return (y + e.y - b.getY() - b.e.getY());
		}

	}

}
