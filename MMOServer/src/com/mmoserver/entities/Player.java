package com.mmoserver.entities;

import com.badlogic.gdx.math.Rectangle;
import com.ffserver.entities.terrain.Platform;
import com.ffserver.entities.terrain.Rock;
import com.ffserver.entities.terrain.TerrainManager;

public class Player {

	private String name;
	private int id;
	private Rectangle box;
	private boolean isInAir;

	private float speedX, speedY;
	private boolean isDead, wasDeadLF;
	private int weapon;

	private float correctCounter;

	private int hp;

	private int lastHitBy;
	private int kills, deaths;
	
	private PlayerModel model;

	public Player(int id, String name, PlayerModel model) {
		this.id = id;
		this.name = name;
		box = new Rectangle((float) Math.random() * 1280, 800, 96, 192);
		isInAir = false;
		weapon = 0;
		wasDeadLF = false;
		isDead = false;

		correctCounter = 0;
		hp = 100;
		kills = 0;
		deaths = 0;
		lastHitBy = -1;
		
		this.model = model;
	}

	public void update(float delta, TerrainManager tm) {
		speedY -= 1900f * delta;
		box.x += speedX * delta;
		box.y += speedY * delta;

		correctCounter += delta;

		movement(delta, tm);
		rockCollision(tm);
		if (hp <= 0)
			isDead = true;
	}

	private void movement(float delta, TerrainManager tm) {
		isInAir = false;

		if (box.x < -1000)
			box.x = -1000;
		if (box.x > 2280)
			box.x = 2280;

		if (box.x > 8 && box.x < 1272) {
			if (box.y < tm.getG().getGroundLevel() && box.y > tm.getG().getGroundLevel() - 64 && speedY < 0) {
				box.y = 96;
				speedY = 0;
				return;
			}
		}
		if (box.y < -256) {
			box.y = 800;
			if (speedY < -2048)
				speedY = -2048;
			isInAir = true;
			return;
		}

		for (Platform p : tm.getPlatforms()) {
			if (p.getBox().contains(box.x, box.y) && speedY < 0) {
				box.y = p.getBox().y + 64;
				speedY = 0;
				return;
			}
		}
		isInAir = true;
	}

	private void rockCollision(TerrainManager tm) {
		for (Rock r : tm.getRocks()) {
			if (r.getBox().contains(box.x, box.y)) {
				if (r.getBox().x - box.x < 0) {
					box.x = r.getBox().x;
					return;
				} else {
					box.x = r.getBox().x + r.getBox().width;
					return;
				}
			}
		}
	}

	/**
	 * @return whether the player is dead
	 */
	public boolean getIsDead() {
		return isDead;
	}

	/**
	 * Set whether the player was dead last frame
	 * 
	 * @param b
	 *            if the player was dead last frame
	 */
	public void setWasDeadLF(boolean b) {
		wasDeadLF = b;
	}

	public boolean getWasDeadLF() {
		return wasDeadLF;
	}

	/**
	 * Respawn the player reset the variables
	 */
	public void respawn() {
		box.setPosition((float) Math.random() * 1280, 800);
		isInAir = false;
		weapon = 0;
		wasDeadLF = false;
		isDead = false;

		correctCounter = 0;
		hp = 100;
	}

	/**
	 * Set the position of the player
	 * 
	 * @param x
	 *            the position in the x-axis
	 * @param y
	 *            the position in the y-axis
	 */
	public void setPos(float x, float y) {
		box.x = y;
		box.y = y;
	}

	/**
	 * Set the speed in the x-axis
	 * 
	 * @param xSpeed
	 */
	public void setDir(float xSpeed) {
		speedX = xSpeed * 512;
	}

	/**
	 * Make the player jump
	 */
	public void jump() {
		speedY = 1000;
	}

	/**
	 * Set the weapon of this player Call this when a player has picked up a
	 * DropWeapon
	 * 
	 * @param the
	 *            weapon index
	 */
	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}

	/**
	 * @return the current weapon of the player
	 */
	public int getWeapon() {
		return this.weapon;
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
		return 2 < correctCounter && !isInAir;
	}

	/**
	 * Reset the correctionValue Call this after sending correction to clients
	 */
	public void resetCorrect() {
		correctCounter -= 2;
	}

	/**
	 * @return the current hp of the player
	 */
	public int getHP() {
		return hp;
	}

	/**
	 * deal damage to a player
	 * 
	 * @param amount
	 *            the amount of damage
	 */
	public void dealDamage(int amount, int playerWhoDealt) {
		hp -= amount;
		lastHitBy = playerWhoDealt;
	}

	public int getLastHitBy() {
		return lastHitBy;
	}

	public void addKill() {
		kills++;
	}

	public void addDeath() {
		deaths++;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}
	
	public PlayerModel getModel(){
		return model;
	}
}
