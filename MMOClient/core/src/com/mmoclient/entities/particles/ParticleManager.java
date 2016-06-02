package com.mmoclient.entities.particles;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.fightingfriends.entities.PlayerModel;
import com.fightingfriends.entities.particles.playerparts.PlayerDeath;
import com.fightingfriends.entities.terrain.TerrainManager;

public class ParticleManager {

	private MainGame game;

	private ArrayList<Dirt> dirts;
	private ArrayList<Spark> sparks;
	private ArrayList<BoxFragment> boxFragments;
	private ArrayList<Explosion> explosions;
	private ArrayList<Blood> bloods;
	private ArrayList<Shell> shells;

	private ArrayList<PlayerDeath> playerDeaths;

	public ParticleManager(final MainGame game) {
		this.game = game;

		dirts = new ArrayList<Dirt>();
		sparks = new ArrayList<Spark>();
		boxFragments = new ArrayList<BoxFragment>();
		explosions = new ArrayList<Explosion>();
		bloods = new ArrayList<Blood>();
		shells = new ArrayList<Shell>();

		playerDeaths = new ArrayList<PlayerDeath>();
	}

	public void update(float delta, TerrainManager tm) {
		synchronized (dirts) {
			for (Dirt d : dirts) {
				d.update(delta, tm);
			}
			Iterator<Dirt> i = dirts.iterator();
			while (i.hasNext()) {
				Dirt d = i.next();
				if (d.getIsDestroyed())
					i.remove();
			}
		}

		synchronized (sparks) {
			for (Spark s : sparks) {
				s.update(delta);
			}
			Iterator<Spark> i2 = sparks.iterator();
			while (i2.hasNext()) {
				Spark s = i2.next();
				if (s.getIsDestroyed())
					i2.remove();
			}
		}

		synchronized (boxFragments) {
			for (BoxFragment b : boxFragments) {
				b.update(delta, tm);
			}
			Iterator<BoxFragment> i3 = boxFragments.iterator();
			while (i3.hasNext()) {
				BoxFragment b = i3.next();
				if (b.getIsDestroyed())
					i3.remove();
			}
		}
		synchronized (explosions) {
			for (Explosion e : explosions) {
				e.update(delta);
			}
			Iterator<Explosion> i4 = explosions.iterator();
			while (i4.hasNext()) {
				Explosion e = i4.next();
				if (e.getIsDestroyed())
					i4.remove();
			}
		}
		synchronized (bloods) {
			for (Blood b : bloods) {
				b.update(delta, tm);
			}
			Iterator<Blood> i5 = bloods.iterator();
			while (i5.hasNext()) {
				Blood b = i5.next();
				if (b.getIsDestroyed())
					i5.remove();
			}
		}
		synchronized (shells) {
			for (Shell s : shells) {
				s.update(delta, tm);
			}
			Iterator<Shell> i6 = shells.iterator();
			while (i6.hasNext()) {
				Shell s = i6.next();
				if (s.getIsDestroyed())
					i6.remove();
			}
		}
		synchronized (playerDeaths) {
			for (PlayerDeath p : playerDeaths) {
				p.update(delta, tm, this);
			}
			Iterator<PlayerDeath> i7 = playerDeaths.iterator();
			while (i7.hasNext()) {
				PlayerDeath p = i7.next();
				if (p.getIsDestroyed())
					i7.remove();
			}
		}
	}

	public void render() {
		synchronized (dirts) {
			for (Dirt d : dirts) {
				d.render();
			}
		}
		synchronized (sparks) {
			for (Spark s : sparks) {
				s.render();
			}
		}
		synchronized (boxFragments) {
			for (BoxFragment b : boxFragments) {
				b.render();
			}
		}
		synchronized (explosions) {
			for (Explosion e : explosions) {
				e.render();
			}
		}
		synchronized (bloods) {
			for (Blood b : bloods) {
				b.render();
			}
		}
		synchronized (shells) {
			for (Shell s: shells) {
				s.render();
			}
		}
		synchronized (playerDeaths) {
			for (PlayerDeath p : playerDeaths) {
				p.render();
			}
		}
	}

	public void addDirt(Vector2 pos, int amount, float dir, float randDir, int type, float speed) {
		synchronized (dirts) {
			for (int i = 0; i < amount; i++) {
				dirts.add(new Dirt(game, pos, dir + randDir * (float) (Math.random() - 0.5f), type, speed));
			}
		}
	}

	public void addSpark(Vector2 pos, int amount, float dir) {
		synchronized (sparks) {
			for (int i = 0; i < amount; i++) {
				sparks.add(new Spark(game, pos, dir));
			}
		}
	}

	public void addBoxfragment(Vector2 pos, int amount) {
		synchronized (boxFragments) {
			for (int i = 0; i < amount; i++) {
				boxFragments.add(new BoxFragment(game, pos));
			}
		}
	}

	public void addExplosion(Vector2 pos, int amount) {
		synchronized (explosions) {
			for (int i = 0; i < amount; i++) {
				explosions.add(new Explosion(game, pos, (float) amount / 5f));
			}
		}
	}

	public void addBlood(Vector2 pos, int amount, float dir, float randDir, float speed) {
		synchronized (bloods) {
			for (int i = 0; i < amount; i++) {
				bloods.add(new Blood(game, pos, dir + randDir * (float) (Math.random() - 0.5f), speed));
			}
		}
	}
	
	public void addShell(Vector2 pos, float armRot, boolean turn) {
		synchronized (shells) {
			shells.add(new Shell(game, pos, armRot, turn));
		}
	}

	public void addPlayerDeath(Vector2 pos, PlayerModel model) {
		synchronized (playerDeaths) {
			playerDeaths.add(new PlayerDeath(game, pos, model));
		}
	}
}
