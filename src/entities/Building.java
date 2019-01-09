package entities;

import java.util.ArrayList;

import entities.Entity.CollisionBorder;
import main.Game;
import processing.core.PApplet;

public class Building extends Entity {

	protected ArrayList<Segment> boundry;

	public Building(double x, double y) {
		super(x, y);
	}

	public void setStats() {

		float Size = 300;
		boundry = new ArrayList<Segment>();
		boundry.add(new Segment(this, 0, 0, 0, Size, Type.WALL));
		boundry.add(new Segment(this, 0, Size, Size, Size, Type.WALL));
		boundry.add(new Segment(this, Size, Size, Size, 0, Type.DOOR));
		boundry.add(new Segment(this, Size, 0, 0, 0, Type.WALL));

		for (Segment s : boundry) {
			// collisionBox.add(new CollisionBorder(this, s.x1, s.y1, 5));
			if (s.t == Type.WALL) {
				float length = (float) Math.sqrt((s.x1 - s.x2) * (s.x1 - s.x2) + (s.y1 - s.y2) * (s.y1 - s.y2));

				for (double i = 0; i < length; i += 40) {
					collisionBox.add(new CollisionBorder(this, s.x1 + i * (s.x2 - s.x1) / length,
							s.y1 + i * (s.y2 - s.y1) / length, 0));
				}

			}
		}

	}

	public void reactToHover(Game g) {

	}

	public void reactToClick(Game g) {

	}

	public void draw(PApplet p) {
		for (Segment s : boundry) {
			s.draw(p);
		}
	}

	public void tick(Game g) {

	}

	private enum Type {
		EMPTY, DOOR, WALL
	}

	private class Segment {
		private float x1, y1;
		private float x2, y2;

		private Entity e;

		private Type t;

		public Segment(Entity e, float x1, float y1, float x2, float y2, Type t) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.e = e;
			this.t = t;
		}

		public void draw(PApplet p) {
			p.line((float) (e.x + x1), (float) (e.y + y1), (float) (e.x + x2), (float) (e.y + y2));
			// TODO draw as a set of repeating images for the wall/door
		}

	}

}
