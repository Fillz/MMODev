package com.mmoclient.util;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;

public class BorderDrawer {
	public void drawBorder(final MainGame game, Vector2 pos, float w, float h, float thickness) {
		game.batch.draw(game.am.borderH, pos.x, pos.y - thickness/2f, w, thickness);
		game.batch.draw(game.am.borderH, pos.x, pos.y - thickness/2f + h, w, thickness);
		game.batch.draw(game.am.borderV, pos.x  - thickness/2f, pos.y, thickness, h);
		game.batch.draw(game.am.borderV, pos.x  - thickness/2f + w, pos.y, thickness, h);

		game.batch.draw(game.am.borderCorner, pos.x  - thickness/2f, pos.y - thickness/2f, thickness, thickness);
		game.batch.draw(game.am.borderCorner, pos.x  - thickness/2f, pos.y + thickness/2f + h, thickness, -thickness);
		game.batch.draw(game.am.borderCorner, pos.x + thickness/2f + w, pos.y - thickness/2f, -thickness, thickness);
		game.batch.draw(game.am.borderCorner, pos.x + thickness/2f + w, pos.y + thickness/2f + h, -thickness, -thickness);
	}
}
