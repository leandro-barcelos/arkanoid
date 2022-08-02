package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlocoDourado extends Bloco{
    public BlocoDourado(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, 32, 64, width, height, 0, -1, batch);
    }
}
