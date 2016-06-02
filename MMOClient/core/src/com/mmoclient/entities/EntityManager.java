package com.mmoclient.entities;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.mmoclient.entities.terrain.TerrainManager;

public class EntityManager {
	
	private MainGame game;
	
	private TerrainManager tm;
	private PlayerManager pm;
	
	public EntityManager(final MainGame game){
		this.game = game;
		tm = new TerrainManager(game);
		pm = new PlayerManager(game);
	}
	
	public void update(float delta, Vector2 touchPos){
		pm.update(delta, tm, touchPos);
	}
	public void render(){
		game.batch.setColor(1, 1, 1, 1);
		tm.render();
		pm.render();
	}
	public TerrainManager getTM(){
		return tm;
	}
	public PlayerManager getPM(){
		return pm;
	}
}
