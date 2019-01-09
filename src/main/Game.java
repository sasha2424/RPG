package main;

import java.util.ArrayList;

import entities.*;
import processing.core.PApplet;

public class Game {

	private ArrayList<Entity> entities;

	Player player;

	public Game() {
		entities = new ArrayList<Entity>();

		entities.add(new Building(0, 0));
		for (int i = 0; i < 20; i++) {
			double x = Math.random() * 5000 - 2500;
			double y = Math.random() * 5000 - 2500;
			if (Math.random() < .8) {
				entities.add(new Tree(x, y));
			} else {
				entities.add(new Building(x, y));
			}
		}

		player = new Player(0, 0);
		entities.add(player);

	}

	public void mouseAct(boolean pressed, double mouseX, double mouseY) {
		if (pressed) {
			for (Entity e : entities) {
				e.tick(this);
			}
		} else {
			for (Entity e : entities) {
				e.tick(this);
			}
		}
	}

	public void tick() {
		for (Entity e : entities) {
			e.tick(this);
		}

		// perform each entity collision pair only once
		for (int i = 0; i < entities.size() - 1; i++) {
			for (int j = i + 1; j < entities.size(); j++) {
				if (entities.get(i).nearEntity(entities.get(j))) {
					entities.get(i).collide(entities.get(j));
				}
			}
		}
	}

	/**
	 * Draws world. mouseX and mouseY are given in world coordinates.
	 * 
	 * @param p
	 * @param mouseX
	 * @param mouseY
	 */
	public void draw(PApplet p, double mouseX, double mouseY) {
		for (Entity e : entities) {
			e.draw(p);
		}
		for (Entity e : entities) {
			if (e.mouseInBorder(mouseX, mouseY)) {
				renderDescription(p, e.getDescription(), mouseX, mouseY);
				if (p.mousePressed) {
					e.reactToClick(this);
				} else {
					e.reactToHover(this);
				}
				continue;
			}
		}
	}

	private void renderDescription(PApplet p, String s, double x, double y) {

		float hSpace = 4;
		float vSpace = 6;
		float tSize = 18;
		p.fill(255);
		p.stroke(0);
		p.strokeWeight(1);
		p.textSize(tSize);
		p.rect((float) x - hSpace, (float) y + vSpace, p.textWidth(s) + hSpace * 2, -tSize - vSpace);
		p.fill(0);
		p.text(s, (float) x, (float) y);
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public void ReactEntityToMouse() {

	}

	public void movePlayerUp() {
		player.moveUp();
	}

	public void movePlayerDown() {
		player.moveDown();
	}

	public void movePlayerLeft() {
		player.moveLeft();
	}

	public void movePlayerRight() {
		player.moveRight();
	}

}
