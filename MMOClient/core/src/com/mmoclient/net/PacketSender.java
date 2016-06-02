package com.mmoclient.net;

public class PacketSender {
	
	private int myID;
	private ClientMSG clientMSG;
	
	public PacketSender(int myID, ClientMSG clientMSG){
		this.myID = myID;
		this.clientMSG = clientMSG;
	}
	
	// Methods called by gameClasses when SENDING
	public void sendPing() {
		clientMSG.sendMessage("C_PING;" + myID);
	}

	public void sendRespawn() {
		clientMSG.sendMessage("C_RESPAWN_PLAYER;" + myID);
	}

	public void sendDir(int dir) {
		clientMSG.sendMessage("C_MOVE_PLAYER;" + myID + ";" + dir);
	}

	public void sendJump() {
		clientMSG.sendMessage("C_JUMP_PLAYER;" + myID);
	}

	public void sendAttack(float x, float y, float dir) {
		clientMSG.sendMessage("C_ATTACK;" + myID + ";" + x + ";" + y + ";" + dir);
	}

	public void sendShoot(float x, float y, float dir, int damage, boolean isMissile) {
		clientMSG.sendMessage("C_SHOOT;" + myID + ";" + x + ";" + y + ";" + dir + ";" + damage + ";" + isMissile);
	}

	public void sendPickupWeapon() {
		clientMSG.sendMessage("C_PICKUP_WEAPON;" + myID);
	}

	public void sendMessage(String message) {
		clientMSG.sendMessage("C_CHAT_MSG;" + myID + ";" + message);
	}
}
