package com.mmoserver.entities;

import java.awt.Rectangle;

import com.mmoserver.entities.terrain.TerrainManager;

public class Player {

	private String name;
	private int id;
	private Rectangle box;

	private boolean isDead;

	private float correctCounter;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		box = new Rectangle(100, 100, 96, 192);
		isDead = false;

		correctCounter = 0;
	}

	public void update(float delta) {
		
		//box.x += speedX * delta;
		//box.y += speedY * delta;

		correctCounter += delta;
	}

	/**
	 * @return whether the player is dead
	 */
	public boolean getIsDead() {
		return isDead;
	}
	
	/**
	 * Set the position of the player
	 * 
	 * @param x
	 *            the position in the x-axis
	 * @param y
	 *            the position in the y-axis
	 */
	public void setPos(int x, int y) {
		box.x = x;
		box.y = y;
	}

	/**
	 * @return the id of the player
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the collision box of this player
	 */
	public Rectangle getBox() {
		return box;
	}

	/**
	 * @return whether the server shall send a position-correction to the
	 *         clients
	 */
	public boolean getCorrect() {
		return 2 < correctCounter;
	}

	/**
	 * Reset the correctionValue Call this after sending correction to clients
	 */
	public void resetCorrect() {
		correctCounter -= 2;
	}
}
