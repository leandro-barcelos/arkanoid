package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderEnlarge extends Poder implements PoderVaus {
    public PoderEnlarge(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-enlarge-spritesheet.png"), batch);
    }

    @Override
    public void ativar(Vaus vaus) {
        if (vaus.getHabilidade() != Vaus.vausHabilidade.LARGE) {
            vaus.changeMode(Vaus.vausHabilidade.LARGE);
            setX(vaus.getX());
            setY(vaus.getY());
        }
    }
}
