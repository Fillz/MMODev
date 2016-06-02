package com.mmoclient.hud;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.fightingfriends.Loop;
import com.fightingfriends.MainGame;
import com.fightingfriends.entities.Player;

public class HudManager {
	
	private Stats stats;
	private Chat chat;
	private KillFeedManager kfM;
	
	public HudManager(final MainGame game, Loop loop){
		stats = new Stats(game);
		kfM = new KillFeedManager(game);
		chat = new Chat(game, loop);
	}
	
	public void update(float delta){
		kfM.update(delta);
		chat.update(delta);
	}
	
	public void render(Collection<Player> players, int ping, int myID, String localName){
		kfM.render();
		chat.render(localName);
		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			stats.render(players, ping, myID);
		}
	}
	
	public KillFeedManager getKfM(){
		return kfM;
	}
	
	public Chat getChat(){
		return chat;
	}
}
