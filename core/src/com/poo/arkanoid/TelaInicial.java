package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TelaInicial {
    SpriteBatch batch;
    int highscore;
    Texture background;

    public TelaInicial(SpriteBatch batch, int highscore) {
        this.batch = batch;
        this.highscore = highscore;
        background = new Texture("tela-inicial.png");
    }

    public void draw() {
        batch.draw(background, 0, 0, background.getWidth(), background.getHeight());
    }

    public void dispose() {
        background.dispose();
    }
}
