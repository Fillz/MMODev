package com.mmoclient;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mmoclient.entities.EntityManager;
import com.mmoclient.menus.Menu;
import com.mmoclient.net.Client;
import com.mmoclient.net.PacketReceiver;
import com.mmoclient.net.PacketSender;

/**
 * The main loop of the game. This class binds the managers together and handles
 * the transitions between gamestates. It also holds the networking objects.
 * 
 * @author Erik Bratth�ll
 * @since 2015-11-13
 */
public class Loop {

	private MainGame game;

	private int myID;

	private Menu menu;

	private enum State {
		MENU, INGAME,
	}

	private State GAMESTATE;

	private InputManager im;
	private EntityManager em;

	private Client client;
	private PacketSender packetSender;
	private PacketReceiver packetReceiver;

	private boolean isDead;

	private float pingCounter;
	private int ping;
	private long pingSendTime;

	private OrthographicCamera movingCamera;

	public Loop(final MainGame game) {
		this.game = game;

		im = new InputManager(this);
		menu = new Menu(game);
		em = new EntityManager(game);

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
		switch (GAMESTATE) {
		case MENU:
			menu.update(delta, touchPos);
			if (menu.getStart() && menu.getName().length() > 2) {
				connectToServer(menu.getName());
				GAMESTATE = State.INGAME;
			}
			break;
		case INGAME:
			if (em.getPM().getAllPlayers().size() > 0) {

				updateCamera(delta);

				em.update(delta, new Vector2(touchPos.x + movingCamera.position.x - 640,
						touchPos.y + movingCamera.position.y - 384));
				pingUpdate(delta);

				if (!isDead) {
					im.checkInputDir();
					isDead = em.getPM().getPlayer(myID).getIsDead();
				}
			}
			if (isDead) {
			}
			break;
		}
	}

	public void render(float delta, OrthographicCamera camera) {
		switch (GAMESTATE) {
		case MENU:
			menu.render();
			break;
		case INGAME:
			game.batch.setProjectionMatrix(movingCamera.combined);
			em.render();
			game.batch.setProjectionMatrix(camera.combined);
			if (isDead) {
			}
			break;
		}
	}

	// connect to server
	private void connectToServer(String name) {
		client = new Client("81.224.116.98", 9007, this, name);

		// wait for the server to respond
		// (not a nice way to do it but it works)
		while (client.getId() == -1) {
			System.out.println(client.getId());
		}
		myID = client.getId();
		packetSender = new PacketSender(myID, client);
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
		client.close();
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

	public PacketSender getPacketSender() {
		return packetSender;
	}

	public PacketReceiver getPacketReciever() {
		return packetReceiver;
	}
}
