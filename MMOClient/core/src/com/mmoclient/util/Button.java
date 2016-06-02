package com.mmoclient.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;

public class Button {

	private MainGame game;

	private BorderDrawer bd;

	private boolean isClicked, isClickedLF, wasClicked, justClicked;
	private Vector2 pos, size;

	private float scale;
	private int type;
	private boolean isActive;

	private GlyphLayout glyphLayout;

	public Button(final MainGame game, Vector2 pos, Vector2 size, int type) {
		this.game = game;
		this.pos = pos;
		this.size = size;

		bd = new BorderDrawer();

		isClicked = false;
		isClickedLF = false;
		wasClicked = false;
		justClicked = false;

		scale = 1;

		this.type = type;

		isActive = true;

		glyphLayout = new GlyphLayout();
	}

	public void update(float delta, Vector2 touchPos) {
		if (!isActive)
			return;
		// effect
		if (isClicked && scale > 0.9f) {
			scale -= delta * 2f;
			if (scale < 0.9f)
				scale = 0.9f;
		} else if (!isClicked && scale < 1) {
			scale += delta * 2f;
			if (scale > 1)
				scale = 1;
		}

		if (Gdx.input.justTouched() && (touchPos.x > pos.x && touchPos.x <= pos.x + size.x)
				&& (touchPos.y > pos.y && touchPos.y <= pos.y + size.y))
			justClicked = true;
		else
			justClicked = false;
		if ((Gdx.input.justTouched() || Gdx.input.isTouched()) && (touchPos.x > pos.x && touchPos.x <= pos.x + size.x)
				&& (touchPos.y > pos.y && touchPos.y <= pos.y + size.y)) {
			isClicked = true;
		} else
			isClicked = false;
		if (!isClicked && isClickedLF)
			wasClicked = true;
		else
			wasClicked = false;
		isClickedLF = isClicked;
	}

	public void render() {
		game.batch.setColor(1, 1, 1, 1);
		switch (type) {
		case 0:
			game.batch.draw(game.am.playButton, pos.x + (size.x / 2f) * (1f - scale),
					pos.y + (size.y / 2f) * (1f - scale), size.x * scale, size.y * scale);
			break;
		case 1:
			if (!isActive)
				return;
			game.batch.setColor(0, 0, 0, 0.4f);
			game.batch.draw(game.am.pixel, pos.x + (size.x / 2f) * (1f - scale), pos.y + (size.y / 2f) * (1f - scale),
					size.x * scale, size.y * scale);
			game.am.font.getData().setScale(scale);
			glyphLayout.setText(game.am.font, "RESPAWN");
			game.am.font.setColor(1, 1, 0, 1);
			game.am.font.draw(game.batch, "RESPAWN", pos.x + size.x / 2f - (glyphLayout.width / 2f),
					pos.y + 48*scale + size.y / 2f - (glyphLayout.height / 2f));
			game.batch.setColor(1, 1, 1, 1);
			bd.drawBorder(game, new Vector2(pos.x + (size.x / 2f) * (1f - scale), pos.y + (size.y / 2f) * (1f - scale)),
					size.x * scale, size.y * scale, 16 * scale);
			break;
		}

		game.batch.setColor(1, 1, 1, 1);
	}

	public boolean getIsClicked() {
		return isClicked;
	}

	public boolean getWasClicked() {
		return wasClicked;
	}

	public boolean getJustClicked() {
		return justClicked;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public boolean getActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setType(int type) {
		this.type = type;
	}
}
