package com.poo.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Arkanoid extends ApplicationAdapter {
	SpriteBatch batch;
    OrthographicCamera camera;
	Texture background;

    boolean enlarge = false;
    boolean shrink = false;

    Level level1;
	
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 480);
		batch = new SpriteBatch();
		background = new Texture("backgroud-blue.png");
        level1 = new Level(1, batch);

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, 512, 480);
        level1.vaus.draw();
        level1.paredeBl.draw();
        level1.paredeTl.draw();
        level1.vaus.Mover();



        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            enlarge = true;
            shrink = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            shrink = true;
            enlarge = false;
        }

        if (enlarge) {
            level1.paredeBl.mudarEstado(batch, Parede.estadoParede.ABERTA);
        }

        if (shrink) level1.paredeBl.mudarEstado(batch, Parede.estadoParede.FECHADA);

        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
        level1.dispose();
	}
}
