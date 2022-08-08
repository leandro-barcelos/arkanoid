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

public class Arkanoid extends ApplicationAdapter {

    int width, height;
    SpriteBatch batch;
    OrthographicCamera camera;
    BitmapFont gameFont;
    Nivel nivel;
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
            nivel = new Nivel(player, batch, width, height, player.getNivelAtual());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        startScreen = new TelaInicial(batch, player.getHighscore());

        startGame = false;
    }

    @Override
    public void render() {
        try {
            ScreenUtils.clear(1, 0, 0, 1);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            startGame = Gdx.input.isKeyPressed(Input.Keys.ENTER) || startGame;

            if (!startGame) {
                if (nivel.getLevelNum() != player.getNivelAtual())
                    nivel = new Nivel(player, batch, width, height, player.getNivelAtual());

                try {
                    startScreen.draw(player, gameFont);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (player.getNivelAtual() != nivel.getLevelNum()) {
                    nivel = new Nivel(player, batch, width, height, player.getNivelAtual());
                }

                nivel.draw(gameFont);
                nivel.moverObjetos();

                nivel.bola.setGrudar(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && nivel.bola.isGrudar());
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) nivel.vaus.atirar();

                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    nivel.bola.setGrudar(false);
                }

                nivel.colisao();
                nivel.player.setHighscore();

                nivel.blocosNivel.spawnarPoder(nivel);

                // GAME OVER
                if (nivel.checarDerrota()) {
                    startGame = false;
                } else if (nivel.checarVitoria()) {
                    player.incNivelAtual();
                }
            }

            batch.end();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() {
        startScreen.dispose();
        batch.dispose();
        nivel.dispose();
    }
}
