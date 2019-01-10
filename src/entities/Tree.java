package entities;

import main.Game;
import processing.core.PApplet;
import textures.TextureLoader;

public class Tree extends Entity {

	public Tree(double x, double y) {
		super(x, y);
	}

	public void setStats() {
		
		renderYShift = 420;
		movable = false;
		collisionBox.add(new CollisionBorder(this, 135, 420, 25));
		description = "This is a tree";
		textureName = "Tree";
	}

	public void reactToClick(Game g) {
		System.out.println("CLICK");
	}

	public void tick(Game g) {

	}

}
