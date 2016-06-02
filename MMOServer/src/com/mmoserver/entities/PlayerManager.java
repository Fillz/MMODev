package com.mmoserver.entities;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.ffserver.GameServer;
import com.ffserver.entities.terrain.TerrainManager;

public class PlayerManager {
	private ConcurrentHashMap<Integer, Player> players;
	private GameServer server;

	public PlayerManager(GameServer server) {
		players = new ConcurrentHashMap<Integer, Player>(8);
		this.server = server;

	}

	public void update(float delta, TerrainManager tm) {
		for (Player p : players.values()) {
			p.update(delta, tm);
			if (p.getIsDead() && !p.getWasDeadLF()) {
				// add kill and death to stats
				players.get(p.getLastHitBy()).addKill();
				p.addDeath();
				server.sendAddKillDeath(p.getLastHitBy(), p.getID());
				//tell clients to kill the player
				server.sendKill(p.getID());
				p.setWasDeadLF(true);
			}
		}
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public Player getPlayer(int id) {
		return players.get(id);
	}

	public void addPlayer(int id, String name, PlayerModel model) {
		players.put(id, new Player(id, name, model));
	}

	public void removePlayer(int id) {
		players.remove(id);
	}
}
