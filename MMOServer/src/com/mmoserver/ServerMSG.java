package com.mmoserver;

import com.ffserver.entities.PlayerModel;

public class ServerMSG {
	private WSServer wss;
	private GameServer s;

	public ServerMSG(int port, GameServer s, int currentClientID) {
		wss = new WSServer(port, this, currentClientID);
		this.s = s; // f�r att k�ra metoder fr�n GameServer
	}

	public void addPlayer(int id, String name, PlayerModel model) {
		s.addPlayer(id, name, model);
	}

	public void removePlayer(int id) {
		s.removePlayer(id);
	}

	public void onMessage(String message) {
		String[] values = message.split(";"); // dela upp vid ";"

		if (values[0].equals("C_PING")) {
			sendMessageToClient(Integer.valueOf(values[1]), "S_PING");
		}

		if (values[0].equals("C_RESPAWN_PLAYER")) {
			s.respawnPlayer(Integer.valueOf(values[1]));
		}
		if (values[0].equals("C_MOVE_PLAYER")) {
			s.movePlayer(Integer.valueOf(values[1]), Float.valueOf(values[2]));
		}
		if (values[0].equals("C_JUMP_PLAYER")) {
			s.jumpPlayer(Integer.valueOf(values[1]));
		}
		if (values[0].equals("C_ATTACK")) {
			s.attackWithSword(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]),
					Float.valueOf(values[4]));
		}
		if (values[0].equals("C_SHOOT")) {
			s.shootWithWeapon(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]),
					Float.valueOf(values[4]), Integer.valueOf(values[5]), Boolean.valueOf(values[6]));
		}
		if (values[0].equals("C_PICKUP_WEAPON")) {
			s.pickupWeapon(Integer.valueOf(values[1]));
		}
		if (values[0].equals("C_CHAT_MSG")) {
			s.chatMessage(Integer.valueOf(values[1]), values[2]);
		}
	}

	public boolean sendMessageToClient(int clientID, String message) {
		return (wss.sendToClient(clientID, message));
	}

	public void sendMessageToAll(String message) {
		wss.sendToAll(message);
	}

	public void close() {
		wss.stop();
	}
}