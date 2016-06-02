package com.mmoclient.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mmoclient.MainGame;
import com.mmoclient.util.BorderDrawer;
import com.mmoclient.util.Button;
import com.mmoclient.util.KeyInputListener;

public class Menu {
	
	private MainGame game;
	
	private boolean startGame;
	
	private boolean textInput;
	private String userName;
	
	private Button playButton;//, exitButton;
	private KeyInputListener keyInputListener;
	private Rectangle usernameBox;
	
	private ModelEditor me;
	
	private BorderDrawer bd;
	
	public Menu(final MainGame game){
		this.game = game;
		
		textInput = true;
		userName = "";
		
		playButton = new Button(game, new Vector2(384, 360), new Vector2(128, 128), 0);
		keyInputListener = new KeyInputListener(false, 16);
		usernameBox = new Rectangle(16, 384, 256, 40);
		
		me = new ModelEditor(game);
		me.randomize();
		bd = new BorderDrawer();
	}
	
	public void update(float delta, Vector2 touchPos){
		me.update(delta, touchPos);
		playButton.update(delta, touchPos);
		if(Gdx.input.isTouched()){
			if(usernameBox.contains(new Vector2(touchPos.x, touchPos.y)))
				textInput = true;
			else
				textInput = false;
		}
		
		if(playButton.getWasClicked())
			startGame = true;
		else
			startGame = false;
			
		if(textInput)
			keyInputListener.update(delta);
		userName = keyInputListener.getText().toLowerCase();
	}
	public void render(){
		me.render();
		if(textInput) game.batch.setColor(1, 1, 1, 0.85f);
		else game.batch.setColor(1, 1, 1, 0.5f);
		game.batch.draw(game.am.pixel, usernameBox.x, usernameBox.y, usernameBox.width, usernameBox.height);
		
		game.batch.setColor(1, 1, 1, 1);
		playButton.render();
		game.batch.setColor(1, 1, 1, 1);
		
		String text;
		if(textInput && keyInputListener.getAnimationCounter() > 0.5f) text = userName + "|";
		else text = userName;
		game.am.font.setColor(0, 0, 0, 1);
		game.am.font.getData().setScale(0.4f);
		game.am.font.draw(game.batch, text, usernameBox.x + 8, usernameBox.y + 28);
		game.am.font.setColor(1, 1, 1, 1);
		game.am.font.draw(game.batch, "username", usernameBox.x + 8, usernameBox.y + 72);
		game.am.font.getData().setScale(1f);
		game.am.font.setColor(1, 1, 1, 1);
		
		bd.drawBorder(game, usernameBox.getPosition(new Vector2()), usernameBox.width, usernameBox.height, 8);
	}
	
	public void setStart(boolean start){
		this.startGame = start;
	}
	public boolean getStart(){
		return startGame;
	}
	public String getName(){
		return userName;
	}
	public ModelEditor getME(){
		return me;
	}
}
