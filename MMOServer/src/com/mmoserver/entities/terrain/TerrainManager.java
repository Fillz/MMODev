package com.mmoserver.entities.terrain;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class TerrainManager {
	private Ground g;
	private ArrayList<Platform> platforms;
	private ArrayList<Rock> rocks;

	public TerrainManager() {
		g = new Ground();
		platforms = new ArrayList<Platform>();
		rocks = new ArrayList<Rock>();
	}
	
	/**
	 * Check if the point is colliding with the terrain
	 * @param r the point
	 * @return whether the point is colliding with the terrain
	 */
	public boolean objectIsColliding(Vector2 r) {
		if (r.y <= g.getGroundLevel())
			return true;
		synchronized (platforms) {
			for (Platform p : platforms) {
				if (p.getBox().contains(r))
					return true;
			}
		}
		synchronized (rocks) {
			for (Rock ro : rocks) {
				if (ro.getBox().contains(r))
					return true;
			}
		}
		return false;
	}

	public void addPlatform(int type, float x, float y) {
		synchronized (platforms) {
			platforms.add(new Platform(type, x, y));
		}
	}

	public void addRock(float x) {
		synchronized (rocks) {
			rocks.add(new Rock(x));
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
