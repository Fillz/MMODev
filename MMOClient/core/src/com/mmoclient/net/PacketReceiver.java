package com.mmoclient.net;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mmoclient.Loop;

public class PacketReceiver {
	// Methods called by the ClientMSG when RECEIVING
	
	private Loop loop;
	
	public PacketReceiver(Loop loop){
		this.loop = loop;
	}
	
	public void receivePing() {
		long currentTime = System.nanoTime();
		loop.setPing((int) (((double) (currentTime - loop.getPingSendTime()) / 1000000d) + 0.5d));
	}

	// TERRAIN
	public void addPlatform(int type, float x, float y) {
		loop.getEM().getTM().addPlatform(type, x, y);
	}

	public void addRock(float x) {
		loop.getEM().getTM().addRock(x);
	}

	// PLAYER

	public void addPlayer(int id, String name, float x, float y, int w, int hat, float hatColorR, float hatColorG,
			float hatColorB, float torsoColorR, float torsoColorG, float torsoColorB, float legColorR, float legColorG,
			float legColorB, float shoeColorR, float shoeColorG, float shoeColorB) {
		loop.getEM().getPM().addPlayer(id, name, x, y, w, loop.getMyID(), hat, new Vector3(hatColorR, hatColorG, hatColorB),
				new Vector3(torsoColorR, torsoColorG, torsoColorB), new Vector3(legColorR, legColorG, legColorB),
				new Vector3(shoeColorR, shoeColorG, shoeColorB));
	}

	public void removePlayer(int id) {
		loop.getEM().getPM().removePlayer(id);
	}

	public void respawnPlayer(int id, float x) {
		loop.getEM().getPM().getPlayer(id).respawn(x);
		if (id == loop.getMyID())
			loop.setIsDead(false);
	}

	// MOVEMENT
	public void setPosition(int id, float x, float y) {
		loop.getEM().getPM().getPlayer(id).setPos(x, y);
	}

	public void movePlayer(int id, float xSpeed) {
		loop.getEM().getPM().getPlayer(id).setDir(xSpeed);
	}

	public void jumpPlayer(int id) {
		loop.getEM().getPM().getPlayer(id).jump();
	}

	// BATTLE
	public void setWeapon(int id, int weapon) {
		loop.getEM().getPM().getPlayer(id).setWeapon(weapon);
	}

	public void attackSword(int id, float dir) {
		loop.getEM().getPM().getPlayer(id).getWeapon().fire(dir);
	}

	public void addMissile(int id, float x, float y, float rot, boolean isMissile) {
		loop.getEM().getPM().getPlayer(id).getWeapon().fire(rot);
		loop.getEM().getProM().addProjectile(id, new Vector2(x, y), rot, isMissile);
		loop.getEM().getPM().getPlayer(id).setArmRot(rot);
		if (!isMissile)
			loop.getEM().getParM().addShell(new Vector2(loop.getEM().getPM().getPlayer(id).getBox().x, loop.getEM().getPM().getPlayer(id).getBox().y),
					rot, loop.getEM().getPM().getPlayer(id).getTurn());
	}

	public void damagePlayer(int id, float x, float y, float dir, boolean isMissile) {
		loop.getEM().getPM().getPlayer(id).blinkRed();
		loop.getEM().getParM().addBlood(new Vector2(x, y),
				isMissile ? (int) (9 + Math.random() * 5) : (int) (3 + Math.random() * 5), dir, 1.5f, 192);
	}

	public void killPlayer(int id) {
		loop.getEM().getPM().getPlayer(id).kill(loop.getEM().getParM());
	}

	public void addExplosion(float x, float y) {
		loop.getEM().getParM().addExplosion(new Vector2(x, y), (int) (4 + Math.random() * 4));
	}

	// DROPWEAPONS
	public void addDropBox(int id, float xPos) {
		loop.getEM().getDbM().addDropBox(id, xPos);
	}

	public void addDropBoxOnConnect(int id, float x, float y) {
		loop.getEM().getDbM().addDropBoxOnConnect(id, x, y);
	}

	public void removeDropBox(int id) {
		loop.getEM().getParM().addBoxfragment(loop.getEM().getDbM().getDB(id).getPos(), 4);
		loop.getEM().getDbM().removeDropBox(id);
	}

	public void damageDropBox(int id) {
		loop.getEM().getDbM().getDB(id).blinkRed();
		loop.getEM().getParM().addBoxfragment(
				new Vector2(loop.getEM().getDbM().getDB(id).getPos().x, loop.getEM().getDbM().getDB(id).getPos().y + 32), 2);
	}

	public void addPickup(int id, float x, float y, int type) {
		loop.getEM().getDbM().addPickupWeapon(id, x, y, type);
	}

	public void removePickup(int id) {
		loop.getEM().getDbM().removePickupWeapon(id);
	}

	public void addKillDeath(int idK, int idD) {
		loop.getEM().getPM().getPlayer(idK).addKill();
		loop.getEM().getPM().getPlayer(idD).addDeath();

		// add to kill feed
		loop.getHM().getKfM().addKillFeed(loop.getEM().getPM().getPlayer(idK).getName(), loop.getEM().getPM().getPlayer(idD).getName());

			// remember the name of killer if you died
			if (loop.getMyID() != idD)
				return;
			loop.getEndMenu().reset(loop.getEM().getPM().getPlayer(idK).getName());
		}

		public void allKillsDeaths(int id, int kills, int deaths) {
			loop.getEM().getPM().getPlayer(id).setKills(kills);
			loop.getEM().getPM().getPlayer(id).setDeaths(deaths);
		}

		public void addChatMessage(int id, String message) {
			loop.getHM().getChat().addMessage(loop.getEM().getPM().getPlayer(id).getName(), message);
		}
}
