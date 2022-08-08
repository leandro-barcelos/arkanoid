package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlocoPrata extends Bloco{
    public BlocoPrata(float x, float y, int levelNum, SpriteBatch batch) {
        super(x, y, CorBlocos.SILVER, batch);

        setDurabilidade((2 + levelNum / 8));
        setPontos(50 * levelNum);
    }

}
