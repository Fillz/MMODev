package com.mmoclient.net;

import com.mmoclient.Loop;

public class ClientMSG {
	private ComClient cc;
	private Loop loop;

	public ClientMSG(String ip, int port, Loop loop, Loop.platformCode pc, String name, PlayerModel model) {
		// if (){ om det �r i browser
		// cc = new GWTClient(ip, port, this);
		// }
		// else{
		cc = new WSClient(ip, port, this, pc, name, model);
		// }
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

		// TERRAIN
		if (values[0].equals("S_ADD_T_PLATFORM")) {
			loop.getPacketReciever().addPlatform(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
			return;
		}

		if (values[0].equals("S_ADD_T_ROCK")) {
			loop.getPacketReciever().addRock(Float.valueOf(values[1]));
			return;
		}

		// PLAYERS
		if (values[0].equals("S_ADD_PLAYER")) {
			loop.getPacketReciever().addPlayer(Integer.valueOf(values[1]), values[2], Float.valueOf(values[3]), Float.valueOf(values[4]),
					Integer.valueOf(values[5]), Integer.valueOf(values[6]), Float.valueOf(values[7]), Float.valueOf(values[8]),
							Float.valueOf(values[9]), Float.valueOf(values[10]), Float.valueOf(values[11]),
							Float.valueOf(values[12]), Float.valueOf(values[13]), Float.valueOf(values[14]),
							Float.valueOf(values[15]), Float.valueOf(values[16]), Float.valueOf(values[17]),
							Float.valueOf(values[18]));
			return;
		}
		if (values[0].equals("S_REMOVE_PLAYER")) {
			loop.getPacketReciever().removePlayer(Integer.valueOf(values[1]));
			return;
		}

		if (values[0].equals("S_RESPAWN_PLAYER")) {
			loop.getPacketReciever().respawnPlayer(Integer.valueOf(values[1]), Float.valueOf(values[2]));
			return;
		}

		// MOVEMENT
		if (values[0].equals("S_MOVE_PLAYER")) {
			loop.getPacketReciever().movePlayer(Integer.valueOf(values[1]), Float.valueOf(values[2]));
			return;
		}
		if (values[0].equals("S_JUMP_PLAYER")) {
			loop.getPacketReciever().jumpPlayer(Integer.valueOf(values[1]));
			return;
		}
		if (values[0].equals("S_CORRECT_PLAYER")) {
			loop.getPacketReciever().setPosition(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
			return;
		}

		// BATTLE
		if (values[0].equals("S_SET_WEAPON")) {
			loop.getPacketReciever().setWeapon(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
			return;
		}
		if (values[0].equals("S_ATTACK_SWORD")) {
			loop.getPacketReciever().attackSword(Integer.valueOf(values[1]), Float.valueOf(values[2]));
			return;
		}
		if (values[0].equals("S_ADD_MISSILE")) {
			loop.getPacketReciever().addMissile(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]),
					Float.valueOf(values[4]), Boolean.valueOf(values[5]));
			return;
		}
		if (values[0].equals("S_DAMAGE_PLAYER")) {
			loop.getPacketReciever().damagePlayer(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]),
					Float.valueOf(values[4]), Boolean.valueOf(values[5]));
			return;
		}
		if (values[0].equals("S_KILL_PLAYER")) {
			loop.getPacketReciever().killPlayer(Integer.valueOf(values[1]));
			return;
		}
		if (values[0].equals("S_ADD_EXPLOSION")) {
			loop.getPacketReciever().addExplosion(Float.valueOf(values[1]), Float.valueOf(values[2]));
			return;
		}

		// DROPWEAPONS
		if (values[0].equals("S_ADD_DROPBOX")) {
			loop.getPacketReciever().addDropBox(Integer.valueOf(values[1]), Float.valueOf(values[2]));
			return;
		}

		if (values[0].equals("S_ADD_DROPBOX_OC")) {
			loop.getPacketReciever().addDropBoxOnConnect(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
			return;
		}

		if (values[0].equals("S_REMOVE_DROPBOX")) {
			loop.getPacketReciever().removeDropBox(Integer.valueOf(values[1]));
			return;
		}

		if (values[0].equals("S_DAMAGE_DROPBOX")) {
			loop.getPacketReciever().damageDropBox(Integer.valueOf(values[1]));
			return;
		}

		if (values[0].equals("S_ADD_PICKUP")) {
			loop.getPacketReciever().addPickup(Integer.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]),
					Integer.valueOf(values[4]));
			return;
		}

		if (values[0].equals("S_REMOVE_PICKUP")) {
			loop.getPacketReciever().removePickup(Integer.valueOf(values[1]));
			return;
		}

		// STATS
		if (values[0].equals("S_ADD_KILL_DEATH")) {
			loop.getPacketReciever().addKillDeath(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
			return;
		}

		if (values[0].equals("S_ALL_KILLS_DEATHS")) {
			loop.getPacketReciever().allKillsDeaths(Integer.valueOf(values[1]), Integer.valueOf(values[2]), Integer.valueOf(values[3]));
			return;
		}

		if (values[0].equals("S_CHAT_MSG")) {
			loop.getPacketReciever().addChatMessage(Integer.valueOf(values[1]), values[2]);
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