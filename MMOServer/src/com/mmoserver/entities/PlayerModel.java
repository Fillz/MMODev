package com.mmoserver.entities;

import com.badlogic.gdx.math.Vector3;

public class PlayerModel {
	
	private int hat;
	private Vector3 hatColor, torsoColor, legColor, shoeColor;
	
	public PlayerModel(int hat, Vector3 hatColor, Vector3 torsoColor, Vector3 legColor, Vector3 shoeColor){
		this.hat = hat;
		this.hatColor = hatColor;
		this.torsoColor = torsoColor;
		this.legColor = legColor;
		this.shoeColor = shoeColor;
	}
	
	public int getHat(){
		return hat;
	}
	public Vector3 getHatColor() {
		return hatColor;
	}
	public Vector3 getTorsoColor() {
		return torsoColor;
	}
	public Vector3 getLegColor() {
		return legColor;
	}
	public Vector3 getShoeColor() {
		return shoeColor;
	}
}
