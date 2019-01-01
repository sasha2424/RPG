package entities;

import main.Game;
import processing.core.PApplet;
import textures.TextureLoader;

public class Tree extends Entity {

	public Tree(float x, float y) {
		super(x, y);
	}

	public void setStats() {
		movable = true;
		collisionBox.add(new CollisionBorder(this, 0, 0, 50));
		collisionBox.add(new CollisionBorder(this, 100, 100, 50));
		collisionBox.add(new CollisionBorder(this, 0, 200, 50));
	}

	public void reactToHover(Game g) {
		System.out.println("HOVER");
	}

	public void reactToClick(Game g) {
		System.out.println("CLICK");
	}

	public void draw(PApplet p) {
		p.image(getTexture("Tree"), (float) x, (float) y);
		drawCollisionBox(p);
	}

	public void tick(Game g) {

	}

}
