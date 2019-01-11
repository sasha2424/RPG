package entities;

public class Rock extends Entity {

	public Rock(double x, double y) {
		super(x, y);

		renderYShift = 420;
		movable = true;
		isItem = true;

		description = "This is a rock";
		textureName = "Rock";
		scale = .25;

		collisionBox.add(new CollisionBorder(this, 95, 100, 30));
		recalculateCollisionRange();

	}

}
