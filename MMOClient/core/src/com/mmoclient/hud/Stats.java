package com.mmoclient.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.badlogic.gdx.math.Vector2;
import com.fightingfriends.MainGame;
import com.fightingfriends.entities.Player;
import com.fightingfriends.util.BorderDrawer;
import com.fightingfriends.util.IntComparable;

public class Stats {

	private MainGame game;
	private Vector2 pos;
	private BorderDrawer bd;

	public Stats(final MainGame game) {
		this.game = game;
		pos = new Vector2(216, 160);
		bd = new BorderDrawer();
	}

	public void render(Collection<Player> players, int ping, int myID) {
		game.batch.setColor(0, 0, 0, 0.3f);
		game.batch.draw(game.am.pixel, pos.x, pos.y, 768, 480);
		int amount = 2;

		ArrayList<Player> tempList = new ArrayList<Player>(players);
		Collections.sort(tempList, new IntComparable());

		game.am.font.getData().setScale(0.3f);

		game.am.font.setColor(1, 1, 1, 1);
		game.am.font.draw(game.batch, "PLAYER", pos.x + 16, pos.y + 480 - 24);
		game.am.font.draw(game.batch, "KILLS", pos.x + 180, pos.y + 480 - 24);
		game.am.font.draw(game.batch, "DEATHS", pos.x + 244, pos.y + 480 - 24);
		game.am.font.draw(game.batch, "K/D", pos.x + 320, pos.y + 480 - 24);
		game.batch.setColor(1, 1, 1, 1);
		game.batch.draw(game.am.pixel, pos.x, pos.y + 480 - 48, 768, 2);
		for (Player p : tempList) {
			if (p.getIsDead())
				game.am.font.setColor(0.6f, 0.6f, 0.6f, 1);
			else {
				if (amount == 2)
					game.am.font.setColor(1f, 0.9f, 0, 1);
				else
					game.am.font.setColor(1, 1, 1, 1);
			}
			game.am.font.draw(game.batch, p.getName(), pos.x + 16, pos.y + 480 - amount * 32);
			game.am.font.draw(game.batch, Integer.toString(p.getKills()), pos.x + 180, pos.y + 480 - amount * 32);
			game.am.font.draw(game.batch, Integer.toString(p.getDeaths()), pos.x + 244, pos.y + 480 - amount * 32);
			game.am.font.draw(game.batch,
					Integer.toString(p.getDeaths() == 0 ? p.getKills() == 0 ? 0 : 100
							: (int) (100 * ((float) p.getKills() / (float) p.getDeaths()))) + " %",
					pos.x + 320, pos.y + 480 - amount * 32);
			if (p.getID() == myID)
				game.am.font.draw(game.batch, Integer.toString(ping) + " ms", pos.x + 690, pos.y + 480 - amount * 32);

			game.batch.setColor(0.4f, 0, 0, 1);
			game.batch.draw(game.am.pixel, pos.x, pos.y + 480 - amount * 32 - 24, 768, 2);
			amount++;
		}
		game.batch.setColor(1, 1, 1, 1);
		bd.drawBorder(game, pos, 768, 480, 16);
	}
}
