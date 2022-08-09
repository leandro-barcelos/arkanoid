package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderDisruption extends Poder{

    public PoderDisruption(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-disruption-spritesheet.png"), batch);
    }

}
