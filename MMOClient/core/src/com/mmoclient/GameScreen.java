package com.mmoclient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen{
	
	private MainGame game;
	private OrthographicCamera camera;
	private Vector3 touchPos;
	
	private Loop loop;
	
	public GameScreen(MainGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 768);
		camera.position.set(640, 384, 0);
		
		touchPos = new Vector3();
		
		loop = new Loop(game);
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		loop.render(delta,camera);
		
		game.batch.end();
	}
	
	private void update(float delta){
		//camera
		game.batch.setProjectionMatrix(camera.combined);
		camera.update();
        
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		
		camera.unproject(touchPos);
		camera.update();
		
		//update logic
		loop.update(delta, new Vector2(touchPos.x, touchPos.y));
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		game.dispose();
		loop.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
