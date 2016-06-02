package com.mmoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WSServer {
	private int port;
	private WebSocketServer wss;
	private boolean isReady;

	private List<Connection> clientSockets; // lagra alla connections
	private int nClients; // Antal clients
	private int clientId; // Unikt id f�r varje client

	private ServerMSG s;

	public WSServer(int port, ServerMSG s, int currentClientID) {
		this.port = port;
		isReady = false;
		nClients = 0;
		clientId = currentClientID;
		clientSockets = new ArrayList<Connection>();
		startServer();
		System.out.println("Server IP: " + this.getIP());
		this.s = s; // F�r att k�ra metoder fr�n ServerMSG
	}

	public void startServer() {
		wss = new WebSocketServer(new InetSocketAddress(port)) {
			@Override
			public void onOpen(WebSocket arg0, ClientHandshake arg1) {
				System.out.println("Handshake :" + arg1.toString());
			}

			@Override
			public void onMessage(WebSocket arg0, String arg1) {
				System.out.println("Server receives:  " + arg1 + " " + arg0.getRemoteSocketAddress());

				String[] values = arg1.split(";"); // dela upp vid ";"

				if (values[0].equals("MSG_REQUEST_ID")) {
					// send id och registrera
					clientSockets.add(new Connection(arg0, clientId));
					arg0.send("MSG_SEND_ID;" + clientId);
					System.out.println("Server sent MSG_SEND_ID " + clientId);

					// l�gg till i hashmap
					s.addPlayer(clientId, values[1]);

					clientId++;
					nClients++;
				} else
					s.onMessage(arg1); // f�r alla andra meddelanden fr�n clients
			}

			@Override
			public void onError(WebSocket arg0, Exception arg1) {
				// TODO Auto-generated method stub
				System.out.println("Server Error " + arg1);
			}

			@Override
			public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
				closeConnection(arg0);
			}
		};

		wss.start(); // starta servern
		isReady = true;
		System.out.println("Server started and is ready.");
	}

	public boolean isListening() {
		return isReady;
	}

	public void sendToAll(String text) {
		synchronized (clientSockets) {
			for (Connection c : clientSockets) {
				if (c.getWS().isOpen())
					c.getWS().send(text);
				System.out.println("Server send to all:" + c.getWS().isOpen() + "  " + text);
				// Only we must send the message if the WS is Open.
			}
		}
	}

	public boolean sendToClient(int ID, String text) { // not tested already
		for (Connection c : clientSockets) {
			if (c.getID() == ID) {
				c.getWS().send(text);
				System.out.println("Server send to: " + ID + " " + text);
				return true;
			}
		}
		return false;
	}

	private boolean closeConnection(WebSocket ws) {
		int i = 0, clientToDelete = 0, clientID = 0;
		boolean found = false;

		if (nClients == 0)
			return found;

		synchronized (clientSockets) {
			for (Connection c : clientSockets) {
				if (!found && (c.getWS().hashCode() == ws.hashCode())) {
					clientToDelete = i; // store the index to remove later
					clientID = c.getID();
					found = true;
				}
				i++;
			}
		}

		if (found) {
			s.removePlayer(clientID);
			clientSockets.remove(clientToDelete);
			nClients--;
			System.out.println("Client " + clientID + " disconnected. " + nClients + " clients connected.");

		}
		return found;
	}

	public int nClients() {
		return nClients;
	}

	public void dropAllClients() {
		this.sendToAll("MSG_CLOSE_WS");
	}

	public void stop() {
		dropAllClients();
		try {
			wss.stop();
			System.out.println("Server Stopped.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		isReady = false;
	}

	public String getIP() {
		return ("81.224.116.98");
	}
}

class Connection {
	WebSocket ws;
	int clientID;

	public Connection(WebSocket ws, int ID) {
		this.ws = ws;
		this.clientID = ID;
	}

	public int getID() {
		return clientID;
	}

	public WebSocket getWS() {
		return ws;
	}
}