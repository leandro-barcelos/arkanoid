package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.FileNotFoundException;

public class TelaInicial {
    SpriteBatch batch;
    int highscore;
    Texture background;

    public TelaInicial(SpriteBatch batch, int highscore) {
        this.batch = batch;
        this.highscore = highscore;
        background = new Texture("Telas/tela-inicial.png");
    }

    public void draw(Player player, BitmapFont font) throws FileNotFoundException {
        batch.draw(background, 0, 0, background.getWidth(), background.getHeight());
        player.drawHighscore(248, 460, batch, font);
    }

    public void dispose() {
        background.dispose();
    }
}
