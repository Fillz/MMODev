package com.mmoserver;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ffserver.entities.EntityManager;
import com.ffserver.entities.Player;
import com.ffserver.entities.PlayerModel;
import com.ffserver.entities.terrain.Platform;
import com.ffserver.entities.terrain.Rock;
import com.ffserver.entities.weapons.DropBox;
import com.ffserver.entities.weapons.PickupWeapon;

public class GameServer {

	private ServerMSG serverMSG;
	private EntityManager em;

	public GameServer() {
		int currentClientID = 0;

		serverMSG = new ServerMSG(53008, this, currentClientID);
		em = new EntityManager(this);
		buildTerrain();

		long lastTime = System.nanoTime();
		long TICKS_PER_SECOND = 64l;

		while (true) {
			// Ensure we run at 64 updates/sec
			long currentTime = System.nanoTime();

			update((currentTime - lastTime) / 1000000f);

			long millisToNextUpdate = Math.max(0,
					(1000 / TICKS_PER_SECOND) - ((System.nanoTime() - currentTime) / 1000000));
			lastTime = currentTime;
			try {
				Thread.sleep(millisToNextUpdate);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	private void dispose() {
		serverMSG.close();
	}

	private void update(float delta) {
		delta *= 1d / 1000d;
		em.update(delta);

		for (Player p : em.getPM().getAllPlayers()) {
			if (p.getCorrect()) {
				sendCorrect(p.getID(), p.getBox().x, p.getBox().y);
				p.resetCorrect();
			}
		}
	}

	/**
	 * This method builds the terrain that is sent to the clients when they
	 * connect
	 */
	private void buildTerrain() {
		em.getTM().addPlatform(1, 128, 280);
		em.getTM().addPlatform(0, 512, 400);
	}

	// Methods called by the ServerMSG when RECIEVING
	public void addPlayer(int id, String name, PlayerModel m) {
		if (id < 0)
			return;

		// search db if the player is already existing
		
		//create the model
		PlayerModel model = m;
		
		em.getPM().addPlayer(id, name, model);
		serverMSG.sendMessageToAll("S_ADD_PLAYER;" + id + ";" + name + ";" + em.getPM().getPlayer(id).getBox().x + ";"
				+ em.getPM().getPlayer(id).getBox().y + ";" + em.getPM().getPlayer(id).getWeapon() + ";" + model.getHat()
				+ ";" + model.getHatColor().x + ";" + model.getHatColor().y + ";" + model.getHatColor().z
				+ ";" + model.getTorsoColor().x + ";" + model.getTorsoColor().y + ";" + model.getTorsoColor().z
				+ ";" + model.getLegColor().x + ";" + model.getLegColor().y + ";" + model.getLegColor().z
				+ ";" + model.getShoeColor().x + ";" + model.getShoeColor().y + ";" + model.getShoeColor().z);

		// tell the player that connected about the terrain
		for (Platform p : em.getTM().getPlatforms()) {
			serverMSG.sendMessageToClient(id,
					"S_ADD_T_PLATFORM;" + p.getType() + ";" + p.getBox().x + ";" + p.getBox().y);
		}
		for (Rock r : em.getTM().getRocks()) {
			serverMSG.sendMessageToClient(id, "S_ADD_T_ROCK;" + r.getBox().x);
		}

		// tell the player that joined about the connected players
		for (Player p : em.getPM().getAllPlayers()) {
			if (p.getID() == id)
				continue;
			serverMSG.sendMessageToClient(id, "S_ADD_PLAYER;" + p.getID() + ";" + p.getName() + ";" + p.getBox().x + ";"
					+ p.getBox().y + ";" + p.getWeapon() + ";" + p.getModel().getHat()
					+ ";" + p.getModel().getHatColor().x + ";" + p.getModel().getHatColor().y + ";" + p.getModel().getHatColor().z
					+ ";" + p.getModel().getTorsoColor().x + ";" + p.getModel().getTorsoColor().y + ";" + p.getModel().getTorsoColor().z
					+ ";" + p.getModel().getLegColor().x + ";" + p.getModel().getLegColor().y + ";" + p.getModel().getLegColor().z
					+ ";" + p.getModel().getShoeColor().x + ";" + p.getModel().getShoeColor().y + ";" + p.getModel().getShoeColor().z);
		}
		// tell the player about the already dropped dropboxes
		for (DropBox d : em.getDbM().getAllDBs()) {
			sendAddDropBoxOnConnect(id, d.getID(), d.getPos().x, d.getPos().y);
		}
		// tell the player about the already created pickups
		for (PickupWeapon p : em.getDbM().getAllPickups()) {
			sendAddPickupOnConnect(id, p.getID(), p.getPos().x, p.getPos().y, p.getType());
		}
		// tell player about the current stats
		for (Player p : em.getPM().getAllPlayers()) {
			sendAllKillsDeaths(id, p.getID(), p.getKills(), p.getDeaths());
		}
	}

	public void removePlayer(int id) {
		if (id < 0)
			return;
		// save player

		em.getPM().removePlayer(id);
		serverMSG.sendMessageToAll("S_REMOVE_PLAYER;" + id);
	}

	public void respawnPlayer(int id) {
		em.getPM().getPlayer(id).respawn();
		// tell all players
		sendRespawn(id, em.getPM().getPlayer(id).getBox().x);
	}

	public void movePlayer(int id, float x) {
		em.getPM().getPlayer(id).setDir(x);
		// tell everyone
		sendMove(id, x);
	}

	public void jumpPlayer(int id) {
		em.getPM().getPlayer(id).jump();

		// tell everyone
		sendJump(id);
	}

	public void attackWithSword(int id, float x, float y, float dir) {
		for (Player p : em.getPM().getAllPlayers()) {
			if (p.getID() == id || p.getIsDead())
				continue;
			if (p.getBox().contains(new Vector2(x-48, y)) || p.getBox().contains(new Vector2(x + 48, y))) {
				p.dealDamage(20, id);
				sendDamage(p.getID(), x, y, dir + (float) Math.PI, false);
			}
		}
		for (DropBox d : em.getDbM().getAllDBs()) {
			if (new Rectangle(d.getPos().x - 64, d.getPos().y-16, 128, 128).contains(x, y)) {
				d.takeDamage(20);
				sendDamageDropBox(d.getID());
			}
		}
		sendAttackWithSword(id, dir);
	}

	public void shootWithWeapon(int id, float x, float y, float dir, int damage, boolean isMissile) {
		em.getProM().addProjectile(this, id, new Vector2(x, y), dir, damage, isMissile);
		sendAddMissile(id, x, y, dir, isMissile);
	}

	public void pickupWeapon(int id) {
		for (PickupWeapon p : em.getDbM().getAllPickups()) {
			if (em.getPM().getPlayer(id).getBox().contains(p.getPos().x, p.getPos().y + 16)) {
				em.getPM().getPlayer(id).setWeapon(p.getType());
				sendSetWeapon(id, p.getType());
				p.destroy();
				return;
			}
		}
	}

	public void chatMessage(int id, String message) {
		sendChatMessage(id, message);
		if (message.toLowerCase().startsWith("/platform")) {
			String[] values = message.split("/");
			if(Integer.valueOf(values[2])<0||Integer.valueOf(values[2])>1||values.length!=5)
				return;
			em.getTM().addPlatform(Integer.valueOf(values[2]), Integer.valueOf(values[3]), Integer.valueOf(values[4]));
			serverMSG.sendMessageToAll("S_ADD_T_PLATFORM;" + values[2] + ";" + values[3] + ";" + values[4]);
		}
	}

	// Methods called by the game classes when SENDING

	// MOVEMENT
	private void sendRespawn(int id, float x) {
		serverMSG.sendMessageToAll("S_RESPAWN_PLAYER;" + id + ";" + x);
	}

	private void sendMove(int id, float x) {
		serverMSG.sendMessageToAll("S_MOVE_PLAYER;" + id + ";" + x);
	}

	private void sendJump(int id) {
		serverMSG.sendMessageToAll("S_JUMP_PLAYER;" + id);
	}

	private void sendCorrect(int id, float x, float y) {
		serverMSG.sendMessageToAll("S_CORRECT_PLAYER;" + id + ";" + x + ";" + y);
	}

	// BATTLE
	public void sendSetWeapon(int id, int weapon) {
		serverMSG.sendMessageToAll("S_SET_WEAPON;" + id + ";" + weapon);
	}

	private void sendAttackWithSword(int id, float dir) {
		serverMSG.sendMessageToAll("S_ATTACK_SWORD;" + id + ";" + dir);
	}

	public void sendAddMissile(int id, float x, float y, float rot, boolean isMissile) {
		serverMSG.sendMessageToAll("S_ADD_MISSILE;" + id + ";" + x + ";" + y + ";" + rot + ";" + isMissile);
	}

	public void sendDamage(int id, float x, float y, float dir, boolean isMissile) {
		serverMSG.sendMessageToAll("S_DAMAGE_PLAYER;" + id + ";" + x + ";" + y + ";" + dir + ";" + isMissile);
	}

	public void sendKill(int id) {
		serverMSG.sendMessageToAll("S_KILL_PLAYER;" + id);
	}

	public void sendExplosion(float x, float y) {
		serverMSG.sendMessageToAll("S_ADD_EXPLOSION;" + x + ";" + y);
	}

	// DROPWEAPONS
	public void sendAddDropBox(int id, float xPos) {
		serverMSG.sendMessageToAll("S_ADD_DROPBOX;" + id + ";" + xPos);
	}

	private void sendAddDropBoxOnConnect(int id, int dbID, float x, float y) {
		serverMSG.sendMessageToClient(id, "S_ADD_DROPBOX_OC;" + dbID + ";" + x + ";" + y);
	}

	public void sendRemoveDropBox(int id) {
		serverMSG.sendMessageToAll("S_REMOVE_DROPBOX;" + id);
	}

	public void sendDamageDropBox(int id) {
		serverMSG.sendMessageToAll("S_DAMAGE_DROPBOX;" + id);
	}

	public void sendAddPickup(int id, float x, float y, int type) {
		serverMSG.sendMessageToAll("S_ADD_PICKUP;" + id + ";" + x + ";" + y + ";" + type);
	}

	private void sendAddPickupOnConnect(int id, int pickupID, float x, float y, int type) {
		serverMSG.sendMessageToClient(id, "S_ADD_PICKUP;" + pickupID + ";" + x + ";" + y + ";" + type);
	}

	public void sendRemovePickup(int id) {
		serverMSG.sendMessageToAll("S_REMOVE_PICKUP;" + id);
	}

	// STATS
	public void sendAddKillDeath(int idK, int idD) {
		serverMSG.sendMessageToAll("S_ADD_KILL_DEATH;" + idK + ";" + idD);
	}

	public void sendAllKillsDeaths(int id, int pID, int kills, int deaths) {
		serverMSG.sendMessageToClient(id, "S_ALL_KILLS_DEATHS;" + pID + ";" + kills + ";" + deaths);
	}

	// CHAT
	private void sendChatMessage(int id, String message) {
		serverMSG.sendMessageToAll("S_CHAT_MSG;" + id + ";" + message);
	}
}
