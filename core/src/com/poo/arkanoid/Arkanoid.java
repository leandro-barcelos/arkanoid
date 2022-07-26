package com.poo.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.FileNotFoundException;

public class Arkanoid extends ApplicationAdapter {
    int width, height;
    SpriteBatch batch;
    OrthographicCamera camera;
    Nivel nivel1;
    TelaInicial startScreen;
    Player player;
    boolean startGame;

    @Override
    public void create() {
        width = 640 ;
        height = 480;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        batch = new SpriteBatch();

        try {
            player = new Player();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        startScreen = new TelaInicial(batch, player.getHighscore());
        nivel1 = new Nivel(batch, width, height, 4);

        startGame = false;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        startGame = Gdx.input.isKeyPressed(Input.Keys.ENTER) || startGame;

        if (!startGame) {
            startScreen.draw();
        }
        else {
            nivel1.draw();

            nivel1.bola.grudar = !Gdx.input.isKeyPressed(Input.Keys.SPACE) && nivel1.bola.grudar;

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                nivel1.bola.grudar = false;
            }

            nivel1.colisao();

            nivel1.checarDerrota();

        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        nivel1.dispose();
    }
}
