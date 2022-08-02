package com.poo.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Arkanoid extends ApplicationAdapter {

    int width, height;
    SpriteBatch batch;
    OrthographicCamera camera;
    BitmapFont gameFont;
    Nivel []niveis;
    Nivel nivelAtual;
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

        gameFont = new BitmapFont(Gdx.files.internal("joystix-font.fnt") , Gdx.files.internal("joystix-font.png"), false);
        gameFont.setColor(Color.WHITE);

        try {
            player = new Player();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        startScreen = new TelaInicial(batch, player.getHighscore());

        niveis = new Nivel[33];
        for (int i = 0; i < 3; i++) {
            try {
                niveis[i] = new Nivel(player, batch, width, height, i+1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

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
            try {
                startScreen.draw(player, gameFont);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {

            nivelAtual = niveis[player.getNivelAtual() - 1];
            nivelAtual.draw(gameFont);
            nivelAtual.moverObjetos();

            nivelAtual.bola.grudar = !Gdx.input.isKeyPressed(Input.Keys.SPACE) && nivelAtual.bola.grudar;

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                nivelAtual.bola.grudar = false;
            }

            nivelAtual.colisao();
            try {
                nivelAtual.player.setHighscore();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                // GAME OVER
                if (nivelAtual.checarDerrota()) {
                    nivelAtual.loadMapa();
                    startGame = false;
                    player.reset();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (nivelAtual.checarVitoria()){
                player.incNivelAtual();
            }

        }

        batch.end();
    }

    @Override
    public void dispose() {
        startScreen.dispose();
        batch.dispose();
        nivelAtual.dispose();
    }
}
