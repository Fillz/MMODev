package com.mmoclient;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game{
	public SpriteBatch batch;
	public AssetManager am;
	
	public MainGame(){
		super();
	}
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		am = new AssetManager();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render(){
		super.render();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		am.dispose();
	}
}
