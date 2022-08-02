package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlocoPrata extends Bloco{
    public BlocoPrata(float x, float y, float width, float height, int levelNum, SpriteBatch batch) {
        super(x, y, 0, 64, width, height, 50 * levelNum, (2 + levelNum / 8), batch);
    }

}
