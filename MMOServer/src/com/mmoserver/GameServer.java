package com.mmoserver;

import java.awt.Rectangle;

import com.mmoserver.entities.EntityManager;
import com.mmoserver.entities.Player;

public class GameServer {

	private ServerMSG serverMSG;
	private EntityManager em;

	public GameServer() {
		int currentClientID = 0;

		serverMSG = new ServerMSG(9007, this, currentClientID);
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
	}

	// Methods called by the ServerMSG when RECIEVING
	public void addPlayer(int id, String name) {
		if (id < 0)
			return;

		// search db if the player is already existing
		
		//create the model
		
		em.getPM().addPlayer(id, name);
		serverMSG.sendMessageToAll("S_ADD_PLAYER;" + id + ";" + name + ";" + em.getPM().getPlayer(id).getBox().x + ";"
				+ em.getPM().getPlayer(id).getBox().y);
		
		//send map

		// tell the player that joined about the connected players
		for (Player p : em.getPM().getAllPlayers()) {
			if (p.getID() == id)
				continue;
			serverMSG.sendMessageToClient(id, "S_ADD_PLAYER;" + p.getID() + ";" + p.getName() + ";" + p.getBox().x + ";"
					+ p.getBox().y);
		}
	}

	public void removePlayer(int id) {
		if (id < 0)
			return;
		// save player

		em.getPM().removePlayer(id);
		serverMSG.sendMessageToAll("S_REMOVE_PLAYER;" + id);
	}

	// Methods called by the game classes when SENDING

	private void sendCorrect(int id, float x, float y) {
		serverMSG.sendMessageToAll("S_CORRECT_PLAYER;" + id + ";" + x + ";" + y);
	}
}
