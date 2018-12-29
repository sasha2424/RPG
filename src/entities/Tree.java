package entities;

import main.Game;
import processing.core.PApplet;
import textures.TextureLoader;

public class Tree extends Entity {

	public Tree(float x, float y) {
		super(x, y);
	}

	public void reactToHover(Game g) {
		System.out.println("HOVER");
	}

	public void reactToClick(Game g) {
		System.out.println("CLICK");
	}

	public void draw(PApplet p) {
		p.image(getTexture("Tree"), x, y);
	}

	public void tick(Game g) {
		
	}

}
