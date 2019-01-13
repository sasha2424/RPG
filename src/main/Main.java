package main;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class Main extends PApplet {

	float scale = 1;
	float MIN_SCALE = 0.4f;
	float MAX_SCALE = 2.5f;
	Game g;
	Keys keys;

	float centerX = 0;
	float centerY = 0;
	float oldX = 0;// used for dragging
	float oldY = 0; // used for dragging

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		g = new Game();
		keys = new Keys();
	}

	public void draw() {
		background(255);

		doPlayerTracking();
		doPlayerMove();
		doPlayerInteract();
		doPlayerInventory();
		g.tick();

		pushMatrix();
		translate(width / 2, height / 2);
		scale(scale);
		translate(-centerX, -centerY);

		g.draw(this, getTranslatedMouseX(), getTranslatedMouseY(), scale, centerX, centerY);

		popMatrix();

		if (frameRate >= 40) {
			fill(0, 255, 0);
		} else {
			fill(255, 0, 0);
		}
		text((int) frameRate, 50, 50);
	}

	private double getTranslatedMouseX() {
		return (double) ((mouseX - width / 2) / scale + centerX);
	}

	private double getTranslatedMouseY() {
		return (double) ((mouseY - height / 2) / scale + centerY);
	}

	private void doPlayerInventory() {
		g.openPlayerInventory(keys.getKey("inventory"));
	}

	private void doPlayerInteract() {
		if (keys.getKey("interact")) {
			g.playerAct();
		}
	}

	private void doPlayerTracking() {
		if (keys.getKey("trackPlayer")) {
			centerX = (float) g.player.getX();
			centerY = (float) g.player.getY();
		}
	}

	private void doPlayerMove() {
		if (keys.getKey("up")) {
			g.movePlayerUp();
		}
		if (keys.getKey("down")) {
			g.movePlayerDown();
		}
		if (keys.getKey("left")) {
			g.movePlayerLeft();
		}
		if (keys.getKey("right")) {
			g.movePlayerRight();
		}
	}

	public void mouseWheel(MouseEvent event) {
		if (event.getCount() > 0) {
			if (scale * .9 >= MIN_SCALE)
				scale *= .9;
		} else {
			if (scale * 1.1 <= MAX_SCALE)
				scale *= 1.1;
		}
	}

	public void mouseDragged() {
		if (!keys.getKey("trackPlayer") && mouseButton == RIGHT) {
			centerX -= (mouseX - oldX) / scale;
			centerY -= (mouseY - oldY) / scale;
			oldX = mouseX;
			oldY = mouseY;
		}
	}

	public void mousePressed() {
		if (mouseButton == RIGHT) {
			oldX = mouseX;
			oldY = mouseY;
		}
	}

	public void keyPressed() {
		// System.out.println(keyCode);
		keys.setKey(keyCode, true);
	}

	public void keyReleased() {
		keys.setKey(keyCode, false);
	}

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

}
