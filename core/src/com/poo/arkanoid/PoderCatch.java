package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderCatch extends Poder implements PoderBola{
    public PoderCatch(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-catch-spritesheet.png"), batch);
    }

    @Override
    public void ativar(Bola bola) {
        bola.setGrudar(true);
    }
}
