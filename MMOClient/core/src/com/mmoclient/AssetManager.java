package com.mmoclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
	
	private Texture gameTex;
	
	public TextureRegion borderCorner, borderH, borderV;
	
	public TextureRegion groundDirt, groundStone;
	public TextureRegion[] platform;
	public TextureRegion rock;
	
	public TextureRegion playerHead, playerTorso;
	public TextureRegion[] playerLeg, playerShoe;
	public TextureRegion playerArm, playerHand;
	public TextureRegion pDeadShoe, pDeadLeg, pDeadTorso1, pDeadTorso2, pDeadHead;
	public TextureRegion pDeadArmBlood, pDeadLegBlood, pDeadTorso1Blood, pDeadTorso2Blood;
	public TextureRegion pDeadArmS, pDeadLegS, pDeadTorso1S, pDeadTorso2S, pDeadHeadS;
	
	public TextureRegion[] playerHat;
	
	//weapon
	public TextureRegion wBox, wBoxFragment;
	public TextureRegion wGun, wUzi, wAK, wBazooka, wKnife;
	public TextureRegion shot, missile, shell;
	public TextureRegion[] mFire, flash;
	
	//particles
	public TextureRegion parExpl;
	public TextureRegion[] parSpark, parBlood, parDirt;
	
	//HUD
	public TextureRegion hp, ammo;
	
	//background
	private Texture bg;
	
	public TextureRegion cliff1, cliff2, cliff3;
	public TextureRegion cloud;
	public TextureRegion mountain1, mountain2;
	public TextureRegion sun, moon, star;
	public TextureRegion skyShade;
	
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
		
		//terrain
		groundStone = new TextureRegion(gameTex, 0, 96, 256, 16);
		groundDirt = new TextureRegion(gameTex, 0, 112, 256, 16);
		
		platform = new TextureRegion[2];
		platform[0] = new TextureRegion(gameTex, 0, 64, 40, 16);
		platform[1] = new TextureRegion(gameTex, 40, 64, 40, 16);
		
		rock = new TextureRegion(gameTex, 80, 64, 16, 32);
		
		//player
		playerHead = new TextureRegion(gameTex, 0, 0, 16, 16);
		playerTorso = new TextureRegion(gameTex, 0, 16, 16, 16);
		playerLeg = new TextureRegion[6];
		playerShoe = new TextureRegion[6];
		for(int i = 0; i < 6; i++){
			playerLeg[i] = new TextureRegion(gameTex, i*16, 60, 16, 2);
			playerShoe[i] = new TextureRegion(gameTex, i*16, 62, 16, 2);
		}
		playerArm = new TextureRegion(gameTex, 96, 0, 16, 8);
		playerHand = new TextureRegion(gameTex, 96, 32, 16, 8);
		
		//deadplayer
		pDeadShoe = new TextureRegion(gameTex, 104, 8, 8, 8);
		pDeadLeg = new TextureRegion(gameTex, 96, 8, 8, 8);
		pDeadTorso1 = new TextureRegion(gameTex, 112, 16, 16, 8);
		pDeadTorso2= new TextureRegion(gameTex, 112, 24, 8, 8);
		pDeadHead = new TextureRegion(gameTex, 112, 0, 16, 16);
		
		pDeadArmBlood = new TextureRegion(gameTex, 96, 16, 9, 8);
		pDeadLegBlood = new TextureRegion(gameTex, 96, 24, 8, 8);
		pDeadTorso1Blood = new TextureRegion(gameTex, 112, 32, 16, 8);
		pDeadTorso2Blood = new TextureRegion(gameTex, 112, 40, 8, 8);
		
		pDeadArmS = new TextureRegion(gameTex, 96, 8+64, 16, 8);
		pDeadLegS = new TextureRegion(gameTex, 96, 16+64, 8, 8);
		pDeadTorso1S = new TextureRegion(gameTex, 112, 16+64, 16, 8);
		pDeadTorso2S = new TextureRegion(gameTex, 112, 24+64, 8, 8);
		pDeadHeadS = new TextureRegion(gameTex, 112, 64, 16, 16);
		
		//hats
		playerHat = new TextureRegion[15];
		
		for(int i = 0; i < 15; i++){
			if(i<5)
				playerHat[i] = new TextureRegion(gameTex, 16 + ((i%5)*16), 0, 16, 16);
			else if(i<10)
				playerHat[i] = new TextureRegion(gameTex, 16 + ((i%5)*16), 16, 16, 16);
			else
				playerHat[i] = new TextureRegion(gameTex, 16 + ((i%5)*16), 32, 16, 16);
		}
		
		//weapons
		wBox = new TextureRegion(gameTex, 184, 16, 16, 16);
		wBoxFragment = new TextureRegion(gameTex, 200, 16, 16, 8);
		
		wGun = new TextureRegion(gameTex, 128, 0, 24, 16);
		wUzi = new TextureRegion(gameTex, 152, 0, 24, 16);
		wAK = new TextureRegion(gameTex, 176, 0, 24, 16);
		wBazooka = new TextureRegion(gameTex, 128, 16, 32, 16);
		wKnife = new TextureRegion(gameTex, 160, 16, 8, 24);
		
		shot = new TextureRegion(gameTex, 128, 32, 16, 8);
		missile = new TextureRegion(gameTex, 144, 32, 16, 8);
		shell = new TextureRegion(gameTex, 168, 16, 8, 8);
		
		mFire = new TextureRegion[3];
		mFire[0] = new TextureRegion(gameTex, 128, 40, 16, 8);
		mFire[1] = new TextureRegion(gameTex, 128, 48, 16, 8);
		mFire[2] = new TextureRegion(gameTex, 128, 56, 16, 8);
		
		flash = new TextureRegion[3];
		flash[0] = new TextureRegion(gameTex, 184, 32, 16, 8);
		flash[1] = new TextureRegion(gameTex, 184, 40, 16, 8);
		flash[2] = new TextureRegion(gameTex, 184, 48, 16, 8);
		
		//particles
		parExpl = new TextureRegion(gameTex, 168, 32, 16, 16);
		parBlood = new TextureRegion[2];
		parBlood[0] = new TextureRegion(gameTex, 152, 40, 8, 8);
		parBlood[1] = new TextureRegion(gameTex, 160, 40, 8, 8);
		parSpark = new TextureRegion[2];
		parSpark[0] = new TextureRegion(gameTex, 144, 48, 8, 8);
		parSpark[1] = new TextureRegion(gameTex, 152, 48, 8, 8);
		parDirt = new TextureRegion[2];
		parDirt[0] = new TextureRegion(gameTex, 160, 48, 8, 8);
		parDirt[1] = new TextureRegion(gameTex, 160, 56, 8, 8);
		
		
		//hud
		hp = new TextureRegion(gameTex, 200, 0, 16, 16);
		ammo = new TextureRegion(gameTex, 168, 24, 16, 8);
		
		//background
		bg = new Texture(Gdx.files.internal("bg.png"));
		
		cliff1 = new TextureRegion(bg, 0, 0, 512, 192);
		cliff2 = new TextureRegion(bg, 960, 384, 320, 384);
		cliff3 = new TextureRegion(bg, 512, 0, 768, 384);
		
		cloud = new TextureRegion(bg, 0, 192, 512, 192);
		
		mountain1 = new TextureRegion(bg, 448, 768, 384, 320);
		mountain2 = new TextureRegion(bg, 832, 768, 448, 320);
		
		sun = new TextureRegion(bg, 64, 576, 320, 320);
		moon = new TextureRegion(bg, 64, 896, 192, 192);
		star = new TextureRegion(bg, 0, 384, 16, 16);
		
		skyShade = new TextureRegion(bg, 0, 576, 1, 448);
		
		pixel = new TextureRegion(gameTex, 129, 65, 1, 1);
		
		playButton = new TextureRegion(gameTex, 216, 0, 16, 16);
	}
	
	public void dispose(){
		gameTex.dispose();
		font.dispose();
	}
}
