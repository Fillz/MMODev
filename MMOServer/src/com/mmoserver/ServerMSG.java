package com.mmoserver;

public class ServerMSG {
	private WSServer wss;
	private GameServer s;

	public ServerMSG(int port, GameServer s, int currentClientID) {
		wss = new WSServer(port, this, currentClientID);
		this.s = s; // f�r att k�ra metoder fr�n GameServer
	}

	public void addPlayer(int id, String name) {
		s.addPlayer(id, name);
	}

	public void removePlayer(int id) {
		s.removePlayer(id);
	}

	public void onMessage(String message) {
		String[] values = message.split(";");

		if (values[0].equals("C_PING")) {
			sendMessageToClient(Integer.valueOf(values[1]), "S_PING");
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