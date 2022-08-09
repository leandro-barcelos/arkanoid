package com.poo.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

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
        width = 640;
        height = 480;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        batch = new SpriteBatch();

        gameFont = new BitmapFont(Gdx.files.internal("joystix-font.fnt"), Gdx.files.internal("joystix-font.png"), false);
        gameFont.setColor(Color.WHITE);

        player = new Player();
        nivel = new Nivel(batch, width, height, player.getNivelAtual());

        startScreen = new TelaInicial(batch);

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


            if (nivel.getLevelNum() != player.getNivelAtual())
                nivel = new Nivel(batch, width, height, player.getNivelAtual());


            if (!startGame) {
                startScreen.draw(player, gameFont);
            } else {
                if (player.getNivelAtual() != nivel.getLevelNum()) {
                    nivel = new Nivel(batch, width, height, player.getNivelAtual());
                }

                nivel.draw(gameFont, player);
                nivel.moverObjetos();

                if (nivel.getBolas()[0] != null)
                    nivel.getBolas()[0].setGrudar(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && nivel.getBolas()[0].isGrudar());

                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) nivel.getVaus().atirar();

                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    for (Bola i: nivel.getBolas())
                        if (i != null)
                            i.setGrudar(false);
                }

                nivel.colisao(player);
                player.setHighscore();

                nivel.getBlocosNivel().spawnarPoder(nivel);

                // GAME OVER
                if (nivel.checarDerrota(player)) {
                    startGame = false;
                } else if (nivel.checarVitoria()) {
                    player.incNivelAtual();
                }
            }

            batch.end();
        } catch (Exception e) {
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
