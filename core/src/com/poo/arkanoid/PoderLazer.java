package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderLazer extends Poder implements PoderVaus {
    public PoderLazer(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, new Texture("Powerups/power-lazer-spritesheet.png"), batch);
    }

    @Override
    public void ativar(Vaus vaus) {
        if (vaus.getHabilidade() != Vaus.vausHabilidade.LAZER) {
            vaus.changeMode(Vaus.vausHabilidade.LAZER);
            setX(vaus.getX());
            setY(vaus.getY());
        }
    }
}

