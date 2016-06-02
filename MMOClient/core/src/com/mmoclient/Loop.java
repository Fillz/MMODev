package com.mmoclient;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mmoclient.entities.EntityManager;
import com.mmoclient.hud.HudManager;
import com.mmoclient.menus.Menu;
import com.mmoclient.net.ClientMSG;
import com.mmoclient.net.PacketReceiver;
import com.mmoclient.net.PacketSender;

import javafx.scene.layout.Background;

/**
 * The main loop of the game. This class binds the managers together and handles
 * the transitions between gamestates. 
 * It also holds the networking objects.
 * 
 * @author Erik Bratth�ll
 * @since 2015-11-13
 */
public class Loop {

	private MainGame game;

	private int myID;

	private Menu menu;
	private EndMenu endMenu;

	private enum State {
		MENU, INGAME,
	}

	private State GAMESTATE;

	private InputManager im;
	private Background bg;
	private EntityManager em;
	private HudManager hm;

	private ClientMSG clientMSG;
	private PacketSender packetSender;
	private PacketReceiver packetReceiver;

	private boolean isDead;

	private float pingCounter;
	private int ping;
	private long pingSendTime;

	private OrthographicCamera movingCamera;

	public static enum platformCode {
		DESKTOP, ANDROID, HTML5
	};

	public Loop(final MainGame game) {
		this.game = game;

		im = new InputManager(this);
		menu = new Menu(game);
		endMenu = new EndMenu(game);
		bg = new Background(game);
		em = new EntityManager(game);
		hm = new HudManager(game, this);

		isDead = false;

		pingCounter = 0;
		ping = 0;
		pingSendTime = 0;

		GAMESTATE = State.MENU;

		movingCamera = new OrthographicCamera();
		movingCamera.setToOrtho(false, 1280, 768);
		movingCamera.position.set(640, 384, 0);
	}

	public void update(float delta, Vector2 touchPos) {
		if (delta > 0.05f)
			delta = 0.05f;
		bg.update(delta);
		switch (GAMESTATE) {
		case MENU:
			menu.update(delta, touchPos);
			if (menu.getStart() && menu.getName().length() > 2) {
				connectToServer(menu.getName(), menu.getME().getModel());
				GAMESTATE = State.INGAME;
			}
			break;
		case INGAME:
			if (em.getPM().getAllPlayers().size() > 0) {

				updateCamera(delta);

				em.update(delta, new Vector2(touchPos.x + movingCamera.position.x - 640,
						touchPos.y + movingCamera.position.y - 384));

				hm.update(delta);
				pingUpdate(delta);

				if (!isDead) {
					if (!hm.getChat().getInputText()) {
						im.checkInputDir();
						im.checkInputJump(em.getPM().getPlayer(myID));
						im.checkInputShoot(em.getPM().getPlayer(myID));
						im.checkInputPickup(em.getPM().getPlayer(myID));
					}
					isDead = em.getPM().getPlayer(myID).getIsDead();
				}
			}
			if (isDead) {
				endMenu.update(delta, touchPos);
				endMenu.checkInputRespawn(this);
			}
			break;
		}
	}

	public void render(float delta, OrthographicCamera camera) {
		bg.render();
		switch (GAMESTATE) {
		case MENU:
			menu.render();
			break;
		case INGAME:
			game.batch.setProjectionMatrix(movingCamera.combined);
			em.render();
			game.batch.setProjectionMatrix(camera.combined);
			if (isDead) {
				endMenu.render();
			}
			if (em.getPM().getAllPlayers().size() > 0) {
				hm.render(em.getPM().getAllPlayers(), ping, myID, em.getPM().getPlayer(myID).getName());
			}
			break;
		}
	}

	// connect to server
	private void connectToServer(String name, PlayerModel model) {
		clientMSG = new ClientMSG("81.224.116.98", 53008, this, platformCode.DESKTOP, name, model);

		// wait for the server to respond
		// (not a nice way to do it but it works)
		while (clientMSG.getId() == -1) {
			System.out.println(clientMSG.getId());
		}
		myID = clientMSG.getId();
		packetSender = new PacketSender(myID, clientMSG);
		packetReceiver = new PacketReceiver(this);
	}

	private void updateCamera(float delta) {
		if (movingCamera.position.x < em.getPM().getPlayer(myID).getBox().x) {
			movingCamera.position.x += delta * 512;
			if (movingCamera.position.x > em.getPM().getPlayer(myID).getBox().x)
				movingCamera.position.x = em.getPM().getPlayer(myID).getBox().x;
		} else if (movingCamera.position.x > em.getPM().getPlayer(myID).getBox().x) {
			movingCamera.position.x -= delta * 512;
			if (movingCamera.position.x < em.getPM().getPlayer(myID).getBox().x)
				movingCamera.position.x = em.getPM().getPlayer(myID).getBox().x;
		}

		if (movingCamera.position.x < 0)
			movingCamera.position.x = 0;
		else if (movingCamera.position.x > 1280)
			movingCamera.position.x = 1280;

		movingCamera.update();
	}

	private void pingUpdate(float delta) {
		pingCounter += delta;
		if (pingCounter > 5) {
			pingCounter -= 5;
			packetSender.sendPing();
			pingSendTime = System.nanoTime();
		}
	}

	public void dispose() {
		clientMSG.close();
	}

	public long getPingSendTime() {
		return pingSendTime;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getMyID() {
		return myID;
	}

	public EntityManager getEM() {
		return em;
	}

	public HudManager getHM() {
		return hm;
	}

	public EndMenu getEndMenu() {
		return endMenu;
	}

	public PacketSender getPacketSender() {
		return packetSender;
	}

	public PacketReceiver getPacketReciever() {
		return packetReceiver;
	}
}
