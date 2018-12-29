package main;

import java.util.ArrayList;

import entities.*;
import processing.core.PApplet;

public class Game {

	private ArrayList<Entity> entities;

	Player player;

	public Game() {
		entities = new ArrayList<Entity>();
		player = new Player(0, 0);
		entities.add(player);

		for (int i = 0; i < 50; i++) {
			entities.add(new Tree((float) (Math.random() * 10000), (float) (Math.random() * 10000)));
		}

	}

	public void draw(PApplet p) {
		for (Entity e : entities) {
			e.draw(p);
		}
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
