package entities;

import java.util.ArrayList;

import entities.Entity.CollisionBorder;
import main.Game;
import processing.core.PApplet;

public class Player extends Entity {

	protected double renderDistance = 2000;

	public Player(float x, float y) {
		super(x, y);
	}

	public void setStats() {
		renderPriority = 0;
		movable = true;
		collisionBox.add(new CollisionBorder(this, 0, 0, 50));
		speed = 10;
	}

	public void reactToHover(Game g) {

	}

	public void reactToClick(Game g) {

	}

	public void draw(PApplet p) {
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

	public double getRenderDistance() {
		return renderDistance;
	}

}
