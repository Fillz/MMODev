package com.mmoclient.entities.particles;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.fightingfriends.entities.terrain.TerrainManager;

public class Shell {
	private MainGame game;

	private Vector2 pos, speed;

	private float alpha, rot, rSpeed;
	private boolean isInGround;

	public Shell(final MainGame game, Vector2 pos, float armRot, boolean turn) {
		this.game = game;
		alpha = 1f;
		this.pos = new Vector2(
				pos.x - 8f * (turn ? 1 : -1) + (float) Math.cos(armRot) * 42
						- (float) Math.sin(armRot) * 32 * (turn ? 1 : -1),
				pos.y + 24 + 48f + (float) Math.sin(armRot) * 42 + (float) Math.cos(armRot) * 32 * (turn ? 1 : -1));
		float force = 192;
		float dir = armRot + (float) Math.PI / (2f * (turn ? 1 : -1));
		speed = new Vector2(force * (float) Math.cos(dir + (float) Math.random() - 0.5f),
				force * (float) Math.sin(dir + (float) Math.random() - 0.5f));
		isInGround = false;
		rSpeed = ((float) Math.random() + 1f) * (turn ? 1 : -1);
		rot = 0;
	}

	public void update(float delta, TerrainManager tm) {
		if (isInGround) {
			alpha -= delta;
			return;
		}
		speed.y -= delta * 512f;
		pos.x += speed.x * delta;
		pos.y += speed.y * delta;
		rot += delta * 720 * rSpeed;
		isInGround = tm.objectIsColliding(pos) != -1;
	}

	public void render() {
		game.batch.setColor(1, 1, 1, alpha);
		game.batch.draw(game.am.shell, pos.x - 16, pos.y - 16, 16, 16, 32, 32, 1, 1, rot);
		game.batch.setColor(1, 1, 1, 1);
	}

	public boolean getIsDestroyed() {
		return alpha < 0;
	}
}
