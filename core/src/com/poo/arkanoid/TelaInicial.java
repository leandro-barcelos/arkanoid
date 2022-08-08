package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TelaInicial {
    private final SpriteBatch batch;
    private final Texture background;

    public TelaInicial(SpriteBatch batch) {
        this.batch = batch;
        background = new Texture("Telas/tela-inicial.png");
    }

    public void draw(Player player, BitmapFont font) {
        batch.draw(background, 0, 0, background.getWidth(), background.getHeight());
        player.drawHighscore(248, 460, batch, font);
    }

    public void dispose() {
        background.dispose();
    }

}
