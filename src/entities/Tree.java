package entities;

import main.Game;
import processing.core.PApplet;
import textures.TextureLoader;

public class Tree extends Entity {

	public Tree(double x, double y) {
		super(x, y);
	}

	public void setStats() {
		movable = false;
		collisionBox.add(new CollisionBorder(this, 140, 420, 30));
	}

	public void reactToHover(Game g) {
		System.out.println("HOVER");
	}

	public void reactToClick(Game g) {
		System.out.println("CLICK");
	}

	public void draw(PApplet p) {
		p.image(getTexture("Tree"), (float) x, (float) y);
	}

	public void tick(Game g) {

	}

}
