package com.mmoserver.entities;

import com.mmoserver.GameServer;
import com.mmoserver.entities.terrain.TerrainManager;

public class EntityManager {

	private TerrainManager tm;
	private PlayerManager pm;

	public EntityManager(GameServer server) {
		tm = new TerrainManager();
		pm = new PlayerManager(server);
	}

	public void update(float delta) {
		pm.update(delta);
	}

	public TerrainManager getTM() {
		return tm;
	}

	public PlayerManager getPM() {
		return pm;
	}
}
