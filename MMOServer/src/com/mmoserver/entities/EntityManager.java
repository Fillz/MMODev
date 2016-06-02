package com.mmoserver.entities;

import com.ffserver.GameServer;
import com.ffserver.entities.projectiles.ProjectileManager;
import com.ffserver.entities.terrain.TerrainManager;
import com.ffserver.entities.weapons.DropBoxManager;

public class EntityManager {

	private TerrainManager tm;
	private DropBoxManager dbM;
	private ProjectileManager proM;
	private PlayerManager pm;

	public EntityManager(GameServer server) {
		tm = new TerrainManager();
		dbM = new DropBoxManager(server);
		proM = new ProjectileManager();
		pm = new PlayerManager(server);
	}

	public void update(float delta) {
		dbM.update(delta, tm);
		proM.update(delta, pm, tm);
		pm.update(delta, tm);
	}

	public TerrainManager getTM() {
		return tm;
	}

	public DropBoxManager getDbM() {
		return dbM;
	}

	public ProjectileManager getProM() {
		return proM;
	}

	public PlayerManager getPM() {
		return pm;
	}
}
