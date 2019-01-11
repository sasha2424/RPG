package entities;

import main.Game;
import processing.core.PApplet;

public class Player extends Entity {

	protected double renderDistance = 2000;

	public Player(float x, float y) {
		super(x, y);
		movable = true;
		speed = 10;
		hasInventory = true;

		collisionBox.add(new CollisionBorder(this, 0, 0, 50));
		recalculateCollisionRange();
	}

	public void reactToHover(Game g) {

	}

	public void reactToClick(Game g) {

	}

	public void draw(PApplet p) {
		// drawCollisionBox(p);
		p.fill(255, 0, 0);
		p.ellipse((float) x, (float) y, 100, 100);
	}

	public void tick(Game g) {

	}

	public void moveUp() {
		this.y -= speed;
	}

	public void moveDown() {
		this.y += speed;
	}

	public void moveRight() {
		this.x += speed;
	}

	public void moveLeft() {
		this.x -= speed;
	}

	public double getRenderDistance() {
		return renderDistance;
	}

}
