package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import entities.*;
import processing.core.PApplet;

public class Game {

	private ArrayList<Entity> entities;

	Player player;

	public Game() {
		entities = new ArrayList<Entity>();

		entities.add(new Building(0, 0));

		double size = 50000;
		for (int i = 0; i < 10000; i++) {
			double x = Math.random() * size - size / 2;
			double y = Math.random() * size - size / 2;
			if (Math.random() < 1) {
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
		ArrayList<Entity> toUpdate = new ArrayList<Entity>();

		for (Entity e : entities) {
			if (Math.abs((e.getX() - player.getX())) > player.getRenderDistance()) {
				continue;
			}
			if (Math.abs((e.getY() - player.getY())) > player.getRenderDistance()) {
				continue;
			}
			if (e.dist(player) > player.getRenderDistance() * 2) {
				continue;
			}
			toUpdate.add(e);
		}

		for (Entity e : toUpdate) {
			e.tick(this);
		}

		// perform each entity collision pair only once
		for (int i = 0; i < toUpdate.size() - 1; i++) {
			for (int j = i + 1; j < toUpdate.size(); j++) {
				if (toUpdate.get(i).nearEntity(toUpdate.get(j))) {
					toUpdate.get(i).collide(toUpdate.get(j));
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
	public void draw(PApplet p, double mouseX, double mouseY, double scale, double centerX, double centerY) {
		ArrayList<Entity> toRender = new ArrayList<Entity>();

		for (Entity e : entities) {
			if (Math.abs((e.getX() - player.getX())) > player.getRenderDistance()) {
				continue;
			}
			if (Math.abs((e.getY() - player.getY())) > player.getRenderDistance()) {
				continue;
			}
			if (e.dist(player) > player.getRenderDistance()) {
				continue;
			}
			toRender.add(e);
		}

		Collections.sort(toRender);
		for (Entity e : toRender) {
			e.draw(p);
		}

		for (Entity e : entities) {
			if (e == player) {
				continue;
			}
			if (e.mouseInBorder(mouseX, mouseY)) {
				renderDescription(p, e.getDescription(), mouseX, mouseY);
				if (p.mousePressed) {
					e.reactToClick(this);
				} else {
					e.reactToHover(this);
				}
				break;
			}
		}
		drawVisionRangeBorder(p, scale, centerX, centerY);
	}

	private void drawVisionRangeBorder(PApplet p, double scale, double centerX, double centerY) {
		Color fogColor = new Color(0, 0, 0);
		double gradientSize = 500;
		double extraBorderSize = 500;

		p.loadPixels();
		for (int i = 0; i < p.pixels.length; i++) {
			double x = (int) i % (p.width);
			double y = (int) (i / p.width);
			x = ((x - (double) p.width / 2) / scale + centerX);
			y = ((y - (double) p.height / 2) / scale + centerY);

			double diff = player.getRenderDistance() - dist(x, y, player.getX(), player.getY());
			if (diff < extraBorderSize) {
				p.pixels[i] = p.color(fogColor.getRGB());
				continue;
			}
			if (diff < gradientSize + extraBorderSize) {
				double ratio = ((diff - extraBorderSize) / (gradientSize));
				Color pixel = new Color(p.pixels[i]);
				int R = (int) (fogColor.getRed() + (pixel.getRed() - fogColor.getRed()) * ratio);
				int G = (int) (fogColor.getGreen() + (pixel.getGreen() - fogColor.getGreen()) * ratio);
				int B = (int) (fogColor.getBlue() + (pixel.getBlue() - fogColor.getBlue()) * ratio);
				p.pixels[i] = (new Color(R, G, B)).getRGB();
			}
		}
		p.updatePixels();
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

	private double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

}
