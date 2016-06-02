package com.mmoclient.net;

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
	// PLAYER

	public void addPlayer(int id, String name) {
		loop.getEM().getPM().addPlayer(id, name, 100, 100, loop.getMyID());
	}

	public void removePlayer(int id) {
		loop.getEM().getPM().removePlayer(id);
	}

	// MOVEMENT
	public void setPosition(int id, float x, float y) {
		loop.getEM().getPM().getPlayer(id).setPos(x, y);
	}
}
