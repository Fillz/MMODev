package com.mmoclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
	
	private Texture gameTex;
	
	public TextureRegion borderCorner, borderH, borderV;
	
	public TextureRegion player;
	
	public BitmapFont font;
	
	public TextureRegion playButton;
	public TextureRegion pixel;
	
	public AssetManager(){
		
		font = new BitmapFont(Gdx.files.internal("font/FFfont.fnt"),Gdx.files.internal("font/FFfont.png"), false);
		font.getData().setScale(0.4f);
		
		gameTex = new Texture(Gdx.files.internal("gametex.png"));
		
		//border
		borderCorner = new TextureRegion(gameTex, 128, 72, 8, 8);
		borderH = new TextureRegion(gameTex, 136, 72, 8, 8);
		borderV = new TextureRegion(gameTex, 128, 80, 8, 8);
		
		player = new TextureRegion(gameTex, 129, 65, 1, 1);
		pixel = new TextureRegion(gameTex, 129, 65, 1, 1);
		playButton = new TextureRegion(gameTex, 216, 0, 16, 16);
	}
	
	public void dispose(){
		gameTex.dispose();
		font.dispose();
	}
}
