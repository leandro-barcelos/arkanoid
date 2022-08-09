package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderPlayer extends Poder {

    public PoderPlayer(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-player-spritesheet.png"), batch);
    }

    public void ativar(Player player) {
        player.incVidas();
    }
}
