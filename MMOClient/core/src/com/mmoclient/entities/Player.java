package com.mmoclient.entities;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mmoclient.MainGame;
import com.fightingfriends.entities.particles.ParticleManager;
import com.fightingfriends.entities.terrain.Platform;
import com.fightingfriends.entities.terrain.Rock;
import com.fightingfriends.entities.terrain.TerrainManager;
import com.fightingfriends.entities.weapons.Weapon;

public class Player {

	private MainGame game;

	private String name;
	private int id;

	private float animationCounter;
	private int aIndex;
	private Rectangle box;
	private boolean isInAir;

	private Vector2 speed;
	private boolean isDead;

	private float armRot, angleToCursor;
	private Weapon weapon;
	private float blinkRed;

	private float spawnDirtCounter;
	private int walkOnType;

	private GlyphLayout glyphLayout;

	private int kills, deaths;

	/**
	 * true = right, false = left
	 */
	private boolean turn;

	private boolean isThisPlayer;

	private PlayerModel model;

	public Player(final MainGame game, int id, String name, float x, float y, int wType, boolean isThisPlayer, int hat,
			Vector3 hatColor, Vector3 torsoColor, Vector3 legColor, Vector3 shoeColor) {
		this.game = game;
		this.id = id;
		this.name = name;
		this.isThisPlayer = isThisPlayer;
		animationCounter = 0;
		aIndex = 0;
		box = new Rectangle(x, y, 96, 192);
		speed = new Vector2(0, 0);
		isInAir = false;

		armRot = 0;
		angleToCursor = 0;
		weapon = new Weapon(game);
		weapon.setType(wType);
		isDead = false;

		blinkRed = 0;

		spawnDirtCounter = 0;
		walkOnType = 0;
		glyphLayout = new GlyphLayout();
		game.am.font.getData().setScale(1);
		glyphLayout.setText(game.am.font, name);

		kills = 0;
		deaths = 0;

		turn = true;

		model = new PlayerModel(hat, hatColor, torsoColor, legColor, shoeColor);
	}

	public void update(float delta, TerrainManager tm, ParticleManager pm, Vector2 touchPos) {
		// animation
		if (speed.x != 0) {
			if (speed.x < 0)
				turn = false;
			else
				turn = true;
			animationCounter += delta * 3f;
			spawnDirtCounter += delta;
			if (spawnDirtCounter > 0.2f) {
				spawnDirtCounter -= 0.2f;
				if (!isInAir) {
					pm.addDirt(new Vector2(box.x, box.y + 16), 3, (float) Math.PI / 2, 2, walkOnType, 128);
				}
			}
		}
		animationCounter %= 1.6f;
		aIndex = calculateAIndex();

		if (blinkRed > 0) {
			blinkRed -= 2 * delta;
			if (blinkRed < 0)
				blinkRed = 0;
		}

		speed.y -= 1900f * delta;
		box.x += speed.x * delta;
		box.y += speed.y * delta;

		if (box.x < -1000)
			box.x = -1000;
		if (box.x > 2280)
			box.x = 2280;

		movement(delta, tm);
		rockCollision(tm);

		// arm rotation
		if (isThisPlayer)
			armRot = (float) (Math.atan2(box.y + 48f - touchPos.y, box.x - 8f - touchPos.x) + Math.PI);
		// else take arm rot from server
		if (weapon.getIsAttackingWithSword()) {
			armRot = weapon.getArmRotBeforeSwordAttack() + (turn ? 2 : -2) * weapon.getCoolDown() + (turn ? -1 : 1);
		}
		weapon.update(delta, new Vector2(box.x, box.y), armRot, turn);
		angleToCursor = (float) (Math.atan2(box.y + 48f - touchPos.y, box.x - 8f - touchPos.x) + Math.PI);
	}

	public void render() {
		renderPlayer(aIndex);
		game.batch.setColor(1, 1, 1, 1);
		weapon.render(armRot, turn);
		game.batch.setColor(model.getTorsoColor().x, model.getTorsoColor().y - model.getTorsoColor().y * blinkRed,
				model.getTorsoColor().z - model.getTorsoColor().z * blinkRed, 1);
		game.batch.draw(game.am.playerArm, box.x - 30 - (turn ? 8 : -8), box.y + 48, 30, 24, 96, 48, 1, (turn ? 1 : -1),
				(float) Math.toDegrees(armRot));
		game.batch.setColor(1, 1 - blinkRed, 1 - blinkRed, 1);
		game.batch.draw(game.am.playerHand, box.x - 30 - (turn ? 8 : -8), box.y + 48, 30, 24, 96, 48, 1,
				(turn ? 1 : -1), (float) Math.toDegrees(armRot));

		game.batch.setColor(1, 1, 1, 1);
	}

	private void renderPlayer(int aIndex) {
		game.batch.setColor(model.getTorsoColor().x, model.getTorsoColor().y - model.getTorsoColor().y * blinkRed,
				model.getTorsoColor().z - model.getTorsoColor().z * blinkRed, 1);
		game.batch.draw(game.am.playerTorso, box.x + (turn ? -48 : 48), box.y, (turn ? 96 : -96), 96);
		game.batch.setColor(1, 1 - blinkRed, 1 - blinkRed, 1);
		game.batch.draw(game.am.playerHead, box.x + (turn ? -48 : 48), box.y + 96, (turn ? 96 : -96), 96);

		game.batch.setColor(model.getHatColor().x, model.getHatColor().y - model.getHatColor().y * blinkRed,
				model.getHatColor().z - model.getHatColor().z * blinkRed, 1);
		game.batch.draw(game.am.playerHat[model.getHat()], box.x + (turn ? -48 : 48), box.y + 96, (turn ? 96 : -96),
				96);
		game.batch.setColor(model.getLegColor().x, model.getLegColor().y - model.getLegColor().y * blinkRed,
				model.getLegColor().z - model.getLegColor().z * blinkRed, 1);
		game.batch.draw(game.am.playerLeg[aIndex], box.x + (turn ? -48 : 48), box.y + 12, (turn ? 96 : -96),
				12);
		game.batch.setColor(model.getShoeColor().x, model.getShoeColor().y - model.getShoeColor().y * blinkRed,
				model.getShoeColor().z - model.getShoeColor().z * blinkRed, 1);
		game.batch.draw(game.am.playerShoe[aIndex], box.x + (turn ? -48 : 48), box.y, (turn ? 96 : -96),
				12);
	}

	public void renderName() {
		// render name in front of other stuff
		game.batch.setColor(0, 0, 0, 0.5f);
		game.batch.draw(game.am.pixel, box.x - 8 - 0.6f * (glyphLayout.width / 2f) - 4,
				box.y + 220 - 0.6f * (glyphLayout.height) - 4, 0.6f * glyphLayout.width + 8,
				0.6f * glyphLayout.height + 8);
		game.am.font.getData().setScale(0.6f);
		game.am.font.setColor(1, 1, 1, 1);
		game.am.font.draw(game.batch, name, box.x - 8 - 0.6f * (glyphLayout.width / 2f), box.y + 220);
		game.batch.setColor(1, 1, 1, 1);
	}

	private void movement(float delta, TerrainManager tm) {
		isInAir = false;

		if (box.x > 8 && box.x < 1272) {
			if (box.y < tm.getG().getGroundLevel() && box.y > tm.getG().getGroundLevel() - 64 && speed.y < 0) {
				box.y = 96;
				speed.y = 0;
				walkOnType = tm.getG().getType();
				return;
			}
		}
		if (box.y < -256) {
			box.y = 800;
			if (speed.y < -2048)
				speed.y = -2048;
			isInAir = true;
			return;
		}
		synchronized (tm.getPlatforms()) {
			for (Platform p : tm.getPlatforms()) {
				if (p.getBox().contains(box.x, box.y) && speed.y < 0) {
					box.y = p.getBox().y + 64;
					speed.y = 0;
					walkOnType = p.getType();
					return;
				}
			}
		}
		isInAir = true;
	}

	private void rockCollision(TerrainManager tm) {
		for (Rock r : tm.getRocks()) {
			if (r.getBox().contains(box.x, box.y)) {
				if (r.getBox().x - box.x < 0) {
					box.x = r.getBox().x;
					return;
				} else {
					box.x = r.getBox().x + r.getBox().width;
					return;
				}
			}
		}
	}

	private int calculateAIndex() {
		if (isInAir)
			return 5;
		if (animationCounter < 0.2f)
			return 0;
		else if (animationCounter < 0.4f)
			return 1;
		else if (animationCounter < 0.6f)
			return 2;
		else if (animationCounter < 0.8f)
			return 1;
		else if (animationCounter < 1f)
			return 0;
		else if (animationCounter < 1.2f)
			return 3;
		else if (animationCounter < 1.4f)
			return 4;
		else
			return 3;
	}

	public void kill(ParticleManager pm) {
		isDead = true;
		pm.addPlayerDeath(new Vector2(box.x, box.y), model);
	}

	public void respawn(float x) {
		animationCounter = 0;
		aIndex = 0;
		box = new Rectangle(x, 800, 96, 192);
		isInAir = false;

		armRot = 0;
		weapon.setType(0);
		isDead = false;

		blinkRed = 0;

		spawnDirtCounter = 0;
		walkOnType = 0;
	}

	public boolean getIsDead() {
		return isDead;
	}

	public void setPos(float x, float y) {
		box.x = x;
		box.y = y;
	}

	public void setDir(float xSpeed) {
		speed.x = xSpeed * 512;
	}

	public void jump() {
		speed.y = 1000;
	}

	public void setArmRot(float rot) {
		armRot = rot;
	}

	public float getArmRot() {
		return (weapon.getIsAttackingWithSword()) ? weapon.getArmRotBeforeSwordAttack() : armRot;
	}

	public float getAngleToCursor() {
		return angleToCursor;
	}

	public void setWeapon(int w) {
		weapon.setType(w);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Rectangle getBox() {
		return box;
	}

	public boolean getIsInAir() {
		return isInAir;
	}

	public void blinkRed() {
		blinkRed = 1;
	}

	public boolean getTurn() {
		return turn;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void addKill() {
		kills++;
	}

	public void addDeath() {
		deaths++;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}
}
