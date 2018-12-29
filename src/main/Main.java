package main;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class Main extends PApplet {

	float scale = 1;
	Game g = new Game();
	Keys keys = new Keys();

	float centerX = 0;
	float centerY = 0;
	float oldX = 0;// used for dragging
	float oldY = 0; // used for dragging

	public void settings() {
		size(800, 800);
	}

	public void setup() {

	}

	public void draw() {
		background(255);
		doPlayerTracking();

		translate(width / 2, height / 2);
		scale(scale);
		translate(-centerX, -centerY);
		g.draw(this);

		doPlayerMove();
	}

	private void doPlayerTracking() {
		if (keys.getKey("trackPlayer")) {
			centerX = g.player.getX();
			centerY = g.player.getY();
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
			scale *= .9;
		} else {
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
		System.out.println(keyCode);
		keys.setKey(keyCode, true);
	}

	public void keyReleased() {
		keys.setKey(keyCode, false);
	}

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

}
