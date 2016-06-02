package com.mmoclient.net;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17; //This is the Standard WebSocket Implementation
import org.java_websocket.handshake.ServerHandshake;

import com.fightingfriends.Loop;
import com.fightingfriends.entities.PlayerModel;

public class WSClient implements ComClient {
	private int port;
	private WebSocketClient wsc;
	private boolean connected;

	private int myID;
	private String name;
	private ClientMSG c;

	private int hat;
	private float hatColorR, hatColorG, hatColorB;
	private float torsoColorR, torsoColorG, torsoColorB;
	private float legColorR, legColorG, legColorB;
	private float shoeColorR, shoeColorG, shoeColorB;

	public WSClient(String ip, int port, ClientMSG c, Loop.platformCode pC, String name, PlayerModel model) {
		this.port = port;
		connected = false;
		this.connectClient(ip);
		myID = -1;
		this.name = name;

		this.hat = model.getHat();
		this.hatColorR = model.getHatColor().x;
		this.hatColorG = model.getHatColor().y;
		this.hatColorB = model.getHatColor().z;
		this.torsoColorR = model.getTorsoColor().x;
		this.torsoColorG = model.getTorsoColor().y;
		this.torsoColorB = model.getTorsoColor().z;
		this.legColorR = model.getLegColor().x;
		this.legColorG = model.getLegColor().y;
		this.legColorB = model.getLegColor().z;
		this.shoeColorR = model.getShoeColor().x;
		this.shoeColorG = model.getShoeColor().y;
		this.shoeColorB = model.getShoeColor().z;

		this.c = c; // f�r att k�ra metoder fr�n ClientMSG
	}

	public void connectClient(String ip) {
		if (!ip.isEmpty()) {
			// Websocket implementation
			URI url = null; // URI (url address of the server)
			try {
				url = new URI("ws://" + ip + ":" + port);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			Draft standard = new Draft_17();

			wsc = new WebSocketClient(url, standard) {

				@Override
				public void onOpen(ServerHandshake handshake) {
					connected = true;
					requestID(name, hat, hatColorR, hatColorG, hatColorB, torsoColorR, torsoColorG, torsoColorB,
							legColorR, legColorG, legColorB, shoeColorR, shoeColorG, shoeColorB);
				}

				@Override
				public void onMessage(String message) {
					// Hantera l�gniv� meddelanden h�r, resten i ClientMSG
					if (message.equals("MSG_CLOSE_WS")) {
						this.close();
					}

					// receive id
					else if (message.startsWith("MSG_SEND_ID")) {
						String[] values = message.split(";"); // splitter with
																// the " "
																// separator
						myID = Integer.valueOf(values[1]);
					}
					// Andra grejerna g�r till ClientMSG
					else {
						c.onMessage(message);
					}
				}

				@Override
				public void onError(Exception ex) {
					System.out.println("WSClient Error.");
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					connected = false;
				}
			};
			wsc.connect();
		}
	}

	private void requestID(String name, int hat, float hatColorR, float hatColorG, float hatColorB, float torsoColorR,
			float torsoColorG, float torsoColorB, float legColorR, float legColorG, float legColorB, float shoeColorR,
			float shoeColorG, float shoeColorB) {
		sendMsg("MSG_REQUEST_ID;" + name + ";" + hat + ";" + hatColorR + ";" + hatColorG + ";" + hatColorB + ";"
				+ torsoColorR + ";" + torsoColorG + ";" + torsoColorB + ";" + legColorR + ";" + legColorG + ";"
				+ legColorB + ";" + shoeColorR + ";" + shoeColorG + ";" + shoeColorB);
	}

	public boolean sendMsg(String msg) {
		if (connected) {
			wsc.send(msg);
			return true;
		} else
			return false;
	}

	public boolean isConnected() {
		return connected;
	}

	public int getId() {
		return myID;
	}

	public void close() {
		wsc.close();
		connected = false;
	}
}
