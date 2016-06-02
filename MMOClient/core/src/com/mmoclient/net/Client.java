package com.mmoclient.net;

import com.mmoclient.Loop;


public class Client {
	private ComClient cc;
	private Loop loop;
	
	public static enum platformCode {
		DESKTOP, ANDROID, HTML5
	};

	public Client(String ip, int port, Loop loop, String name) {
		
		cc = new WSClient(ip, port, this, platformCode.DESKTOP, name);

		this.loop = loop; // To call the methods of the the upper level class
	}

	public void onMessage(String message) {
		System.out.println(message);
		String[] values = message.split(";"); // Deal upp vid ";"

		// Calls to Loop

		if (values[0].equals("S_PING")) {
			loop.getPacketReciever().receivePing();
			return;
		}

		// PLAYERS
		if (values[0].equals("S_ADD_PLAYER")) {
			loop.getPacketReciever().addPlayer(Integer.valueOf(values[1]), values[2]);
			return;
		}
		if (values[0].equals("S_REMOVE_PLAYER")) {
			loop.getPacketReciever().removePlayer(Integer.valueOf(values[1]));
			return;
		}
		if (values[0].equals("S_CORRECT_PLAYER")) {
			loop.getPacketReciever().setPosition(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
			return;
		}
	}

	public boolean sendMessage(String message) {
		if (cc != null && cc.isConnected()) {
			return cc.sendMsg(message);
		} else
			return false;
	}

	public int getId() {
		return (cc.getId());
	}

	public void close() {
		cc.close();
	}
}