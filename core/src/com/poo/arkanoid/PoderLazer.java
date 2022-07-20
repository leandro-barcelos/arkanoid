package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderLazer extends Poder {
    public PoderLazer(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, new Texture("power-lazer-spritesheet.png"), batch);
    }

    public void ativar(Vaus vaus) {
        if (vaus.getHabilidade() != Vaus.vausHabilidade.LAZER)
            vaus.changeMode(Vaus.vausHabilidade.LAZER);
    }
}

// TODO: implementar bibilioteca de colisoes