package com.mmoclient.entities;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.mmoclient.entities.terrain.TerrainManager;

public class PlayerManager {
	private MainGame game;
	private ConcurrentHashMap<Integer, Player> players;

	public PlayerManager(final MainGame game) {
		this.game = game;
		players = new ConcurrentHashMap<Integer, Player>(8);
	}

	public void update(float delta, TerrainManager tm, Vector2 touchPos) {
		for (Player p : players.values()) {
			if (!p.getIsDead())
				p.update(delta, tm, touchPos);
		}
	}

	public void render() {
		for (Player p : players.values()) {
			if (!p.getIsDead())
				p.render();
		}
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public Player getPlayer(int id) {
		return players.get(id);
	}

	public void addPlayer(int id, String name, float x, float y, int myID) {
		players.put(id,
				new Player(game, id, name, x, y, id == myID));
	}

	public void removePlayer(int id) {
		players.remove(id);
	}
}
