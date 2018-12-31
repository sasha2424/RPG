package entities;

import entities.Entity.CollisionBorder;
import main.Game;
import processing.core.PApplet;

public class Player extends Entity {

	float speed = 10;

	public Player(float x, float y) {
		super(x, y);
	}

	public void setStats() {
		movable = true;
		collisionBox.add(new CollisionBorder(this, 0, 0, 50));
	}

	public void reactToHover(Game g) {

	}

	public void reactToClick(Game g) {

	}

	public void draw(PApplet p) {
		p.fill(255, 0, 0);
		drawCollisionBox(p);
		p.noFill();
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

}
