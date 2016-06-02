package com.mmoclient.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.mmoclient.entities.terrain.TerrainManager;

public class Player {

	private MainGame game;

	private String name;
	private int id;
	private Rectangle box;

	private boolean isDead, isThisPlayer;

	public Player(final MainGame game, int id, String name, float x, float y, boolean isThisPlayer) {
		this.game = game;
		this.id = id;
		this.name = name;
		this.isThisPlayer = isThisPlayer;
		box = new Rectangle(x, y, 96, 192);
		isDead = false;
	}

	public void update(float delta, TerrainManager tm, Vector2 touchPos) {
	//	box.x += speed.x * delta;
	//	box.y += speed.y * delta;
	}

	public void render() {
		game.batch.setColor(1, 1, 1, 1);
		game.batch.draw(game.am.player, box.x - 30 - 8, box.y + 48, 30, 24, 96, 48, 1, 1, 0);

		game.batch.setColor(1, 1, 1, 1);
	}

	public void kill() {
		isDead = true;
	}

	public void respawn(float x) {
		box = new Rectangle(x, 800, 96, 192);
	}

	public boolean getIsDead() {
		return isDead;
	}

	public void setPos(float x, float y) {
		box.x = x;
		box.y = y;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Rectangle getBox() {
		return box;
	}
}
