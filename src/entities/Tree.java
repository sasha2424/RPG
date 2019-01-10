package entities;

import main.Game;

public class Tree extends Entity {

	public Tree(double x, double y) {
		super(x, y);
		renderYShift = 420;
		movable = false;

		description = "This is a tree";
		textureName = "Tree";

		collisionBox.add(new CollisionBorder(this, 135, 420, 25));
		
		recalculateCollisionRange();
	}

	public void reactToClick(Game g) {
		System.out.println("CLICK");
	}

	public void tick(Game g) {

	}

}
