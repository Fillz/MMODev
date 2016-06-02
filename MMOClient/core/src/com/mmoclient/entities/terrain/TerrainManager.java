package com.mmoclient.entities.terrain;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;

public class TerrainManager {
	private MainGame game;
	private Ground g;
	private ArrayList<Platform> platforms;
	private ArrayList<Rock> rocks;

	public TerrainManager(final MainGame game) {
		this.game = game;
		g = new Ground(game, 0);
		platforms = new ArrayList<Platform>();
		rocks = new ArrayList<Rock>();
	}

	public void render() {
		synchronized (rocks) {
			for (Rock r : rocks) {
				r.render();
			}
		}
		g.render();
		synchronized (platforms) {
			for (Platform p : platforms) {
				p.render();
			}
		}
	}

	public int objectIsColliding(Vector2 r) {
		if (r.y <= g.getGroundLevel())
			return 0;
		for (Platform p : platforms) {
			if (p.getBox().contains(r))
				return p.getType();
		}
		for (Rock ro : rocks) {
			if (ro.getBox().contains(r))
				return 1;
		}
		return -1;
	}

	public void addPlatform(int type, float x, float y) {
		synchronized (platforms) {
			platforms.add(new Platform(game, type, x, y));
		}
	}

	public void addRock(float x) {
		synchronized (rocks) {
			rocks.add(new Rock(game, x));
		}
	}

	public Ground getG() {
		return g;
	}

	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	public ArrayList<Rock> getRocks() {
		return rocks;
	}
}
