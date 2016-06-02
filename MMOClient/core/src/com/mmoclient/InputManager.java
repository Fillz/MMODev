package com.mmoclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.mmoclient.entities.Player;

public class InputManager {

	private int dir, dirLF;
	private Loop loop;

	public InputManager(Loop loop) {
		this.loop = loop;
		dir = 0;
		dirLF = 0;
	}
	
	public void checkInputRespawn(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			loop.getPacketSender().sendRespawn();
		}
	}
	
	public void checkInputDir() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			dir = -1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			dir = 1;
		} else {
			dir = 0;
		}
		if (dirLF == dir)
			return;
		// send to server
		loop.getPacketSender().sendDir(dir);

		dirLF = dir;
	}

	public void checkInputJump(Player p) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {	
			if (!p.getIsInAir()) {
				loop.getPacketSender().sendJump();
			}
		} 
	}

	public void checkInputShoot(Player p) {
		if (Gdx.input.justTouched()||(p.getWeapon().canFire())&&Gdx.input.isTouched()){
			p.getWeapon().setCantFire();
			if (p.getWeapon().getType() == 0)
				loop.getPacketSender().sendAttack(p.getWeapon().getExitPos().x, p.getWeapon().getExitPos().y, p.getAngleToCursor());
			else
				loop.getPacketSender().sendShoot(p.getWeapon().getExitPos().x, p.getWeapon().getExitPos().y, p.getAngleToCursor(),
						p.getWeapon().getDamage(), p.getWeapon().getCreateMissile());	
		}
		//REMOVE LATER
		//WHEN INPUT FOR MOUSEPAD IS WORKING
		else if (Gdx.input.isKeyJustPressed(Keys.Q)||(p.getWeapon().canFire())&&Gdx.input.isKeyPressed(Keys.Q)){
			p.getWeapon().setCantFire();
			if (p.getWeapon().getType() == 0)
				loop.getPacketSender().sendAttack(p.getWeapon().getExitPos().x, p.getWeapon().getExitPos().y, p.getAngleToCursor());
			else
				loop.getPacketSender().sendShoot(p.getWeapon().getExitPos().x, p.getWeapon().getExitPos().y, p.getAngleToCursor(),
						p.getWeapon().getDamage(), p.getWeapon().getCreateMissile());	
		}
	}
	
	public void checkInputPickup(Player p) {
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			loop.getPacketSender().sendPickupWeapon();
		}
	}
}
