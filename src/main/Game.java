package main;

import java.util.ArrayList;

import entities.*;
import processing.core.PApplet;

public class Game {

	private ArrayList<Entity> entities;

	Player player;

	public Game() {
		entities = new ArrayList<Entity>();

		entities.add(new Tree(500, 500f));
		entities.add(new Building(0, 800));
		player = new Player(0, 0);
		entities.add(player);

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

	public void draw(PApplet p) {
		for (Entity e : entities) {
			e.draw(p);
		}
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
