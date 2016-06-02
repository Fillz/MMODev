package com.mmoclient.entities;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.fightingfriends.entities.particles.ParticleManager;
import com.fightingfriends.entities.projectiles.ProjectileManager;
import com.fightingfriends.entities.terrain.TerrainManager;
import com.fightingfriends.entities.weapons.DropBoxManager;

public class EntityManager {
	
	private MainGame game;
	
	private TerrainManager tm;
	private DropBoxManager dbM;
	private ParticleManager parM;
	private ProjectileManager proM;
	private PlayerManager pm;
	
	public EntityManager(final MainGame game){
		this.game = game;
		tm = new TerrainManager(game);
		dbM = new DropBoxManager(game);
		parM = new ParticleManager(game);
		proM = new ProjectileManager(game);
		pm = new PlayerManager(game);
	}
	
	public void update(float delta, Vector2 touchPos){
		dbM.update(delta, tm);
		parM.update(delta, tm);
		proM.update(delta, pm, tm, parM);
		pm.update(delta, tm, parM, touchPos);
	}
	public void render(){
		game.batch.setColor(1, 1, 1, 1);
		tm.render();
		dbM.render();
		pm.render();
		proM.render();
		parM.render();
		pm.renderNames();
	}
	public TerrainManager getTM(){
		return tm;
	}
	public DropBoxManager getDbM(){
		return dbM;
	}
	public ParticleManager getParM(){
		return parM;
	}
	public ProjectileManager getProM(){
		return proM;
	}
	public PlayerManager getPM(){
		return pm;
	}
}
