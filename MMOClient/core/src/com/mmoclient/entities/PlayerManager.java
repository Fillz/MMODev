package com.mmoclient.entities;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fightingfriends.MainGame;
import com.fightingfriends.entities.particles.ParticleManager;
import com.fightingfriends.entities.terrain.TerrainManager;

public class PlayerManager {
	private MainGame game;
	private ConcurrentHashMap<Integer, Player> players;

	public PlayerManager(final MainGame game) {
		this.game = game;
		players = new ConcurrentHashMap<Integer, Player>(8);
	}

	public void update(float delta, TerrainManager tm, ParticleManager pm, Vector2 touchPos) {
		for (Player p : players.values()) {
			if (!p.getIsDead())
				p.update(delta, tm, pm, touchPos);
		}
	}

	public void render() {
		for (Player p : players.values()) {
			if (!p.getIsDead())
				p.render();
		}
	}

	public void renderNames() {
		for (Player p : players.values()) {
			if (!p.getIsDead())
				p.renderName();
		}
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public Player getPlayer(int id) {
		return players.get(id);
	}

	public void addPlayer(int id, String name, float x, float y, int wType, int myID, int hat, Vector3 hatColor,
			Vector3 torsoColor, Vector3 legColor, Vector3 shoeColor) {
		players.put(id,
				new Player(game, id, name, x, y, wType, id == myID, hat, hatColor, torsoColor, legColor, shoeColor));
	}

	public void removePlayer(int id) {
		players.remove(id);
	}
}
