package com.mmoserver.entities;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.mmoserver.GameServer;

public class PlayerManager {
	private ConcurrentHashMap<Integer, Player> players;
	private GameServer server;

	public PlayerManager(GameServer server) {
		players = new ConcurrentHashMap<Integer, Player>(8);
		this.server = server;

	}

	public void update(float delta) {
		for (Player p : players.values()) {
			p.update(delta);
		}
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public Player getPlayer(int id) {
		return players.get(id);
	}

	public void addPlayer(int id, String name) {
		players.put(id, new Player(id, name));
	}

	public void removePlayer(int id) {
		players.remove(id);
	}
}
