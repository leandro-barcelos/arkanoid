package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderSlow extends Poder implements PoderBola {
    public PoderSlow(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-slow-spritesheet.png"), batch);
    }

    @Override
    public void ativar(Bola bola) {
        bola.setVelocidade(50);
    }
}
