package entities;

import main.Game;

public class Particle extends Entity {

	long timer = 0;
	long timerMax = 100;

	public static Particle getDescription(String desc, double x, double y) {
		Particle p = new Particle(x, y);
		p.description = desc;
		return p;
	}

	private Particle(double x, double y) {
		super(x, y);
	}

	public void draw() {

	}

	@Override
	public void reactToClick(Game g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(Game g) {

	}

}
