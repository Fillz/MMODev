package com.mmoclient.net;

public class PacketSender {

	private int myID;
	private Client clientMSG;

	public PacketSender(int myID, Client clientMSG) {
		this.myID = myID;
		this.clientMSG = clientMSG;
	}

	// Methods called by gameClasses when SENDING
	public void sendPing() {
		clientMSG.sendMessage("C_PING;" + myID);
	}
}
