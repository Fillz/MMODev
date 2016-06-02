package com.mmoclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.mmoclient.entities.Player;

public class InputManager {

	private int dir, dirLF;
	private Loop loop;

	public InputManager(Loop loop) {
		this.loop = loop;
		dir = 0;
		dirLF = 0;
	}
	
	public void checkInputDir() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			dir = -1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			dir = 1;
		} else {
			dir = 0;
		}
		if (dirLF == dir)
			return;
		// send to server
		//loop.getPacketSender().sendDir(dir);

		dirLF = dir;
	}
}
