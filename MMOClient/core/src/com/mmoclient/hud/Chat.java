package com.mmoclient.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fightingfriends.Loop;
import com.fightingfriends.MainGame;
import com.fightingfriends.util.BorderDrawer;
import com.fightingfriends.util.KeyInputListener;

public class Chat {

	private MainGame game;
	private Loop loop;

	private Rectangle box, nBox;
	private BorderDrawer bd;

	private boolean inputText;
	private KeyInputListener keyInputListener;
	private String text;

	private ChatMessage[] messages;
	private float counter;

	public Chat(final MainGame game, Loop loop) {
		this.game = game;
		this.loop = loop;
		box = new Rectangle(24, 164, 320, 128);
		nBox = new Rectangle(24, 124, 320, 24);
		bd = new BorderDrawer();

		inputText = false;
		keyInputListener = new KeyInputListener(true, 32);
		text = "";

		messages = new ChatMessage[7];
		for (int i = 0; i < 7; i++) {
			messages[i] = new ChatMessage("", "");
		}
		counter = 7;
	}

	public void update(float delta) {
		if (inputText){
			keyInputListener.update(delta);
			text = keyInputListener.getText();
		}
		
		// send text
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			inputText = !inputText;
			if (text.trim().length() > 0) {
				loop.getPacketSender().sendMessage(text);
				text = "";
				keyInputListener.clearText();
			}
		}

		// other messages
		counter += delta;
	}

	public void render(String localName) {
		game.am.font.getData().setScale(0.25f);

		if(inputText)
			game.batch.setColor(0, 0, 0, 0.6f);
		else
			game.batch.setColor(0, 0, 0, 0.3f);
		
		game.batch.draw(game.am.pixel, nBox.x, nBox.y, nBox.width, nBox.height);

		game.am.font.draw(game.batch, text+((counter%1<0.5f && inputText)? "|":""), nBox.x + 12, nBox.y + 16);

		if(inputText)
			game.batch.setColor(1, 1, 1, 1);
		else
			game.batch.setColor(0.3f, 0.3f, 0.3f, 1);
		bd.drawBorder(game, new Vector2(nBox.x, nBox.y), nBox.width, nBox.height, 8);

		if (counter > 6 && !inputText)
			return;
		game.batch.setColor(0, 0, 0, 0.5f);
		game.batch.draw(game.am.pixel, box.x, box.y, box.width, box.height);
		synchronized (messages) {
			for (int i = 0; i < 7; i++) {
				if (messages[i].getMessage() == "")
					continue;
				game.am.font.setColor(1, 1, 1, 1);
				game.am.font.draw(game.batch, messages[i].getName() + ": " + messages[i].getMessage(), box.x + 12,
						box.y + i * 16 + 20);
				game.am.font.setColor(1, (messages[i].getName().equals(localName))?1:0, 0, 1);
				game.am.font.draw(game.batch, messages[i].getName() + ": ", box.x + 12, box.y + i * 16 + 20);
			}
		}
		game.batch.setColor(1, 1, 1, 1);
		bd.drawBorder(game, new Vector2(box.x, box.y), box.width, box.height, 8);
	}

	public void addMessage(String name, String message) {
		counter = 0;
		synchronized (messages) {
			game.am.font.getData().setScale(0.15f);
			for (int i = 6; i > 0; i--) {
				messages[i] = messages[i - 1];
			}
			messages[0] = new ChatMessage(name, message);
		}
	}

	public boolean getInputText() {
		return inputText;
	}
}
