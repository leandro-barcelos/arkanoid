package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GateWarp extends Gate {
    Animacao warp;
    float warpStateTime;

    public GateWarp(float x, float y, float width, float height, boolean espelhado, int rotacao, SpriteBatch batch) {
        super(x, y, width, height, espelhado, rotacao, batch);
        warp = new Animacao(new Texture("Gates/wall-warp-spritesheet.png"), 1, 2);
        warpStateTime = 0f;
    }

    @Override
    public void draw() {
        if (getAnimationActive()) return;

        switch (estado) {
            case FECHADA:
                setTextura(imgFechada);
                setWidth(getTextura().getWidth());
                batch.draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, getRotacao(), 0, 0, (int) getWidth(), (int) getHeight(), isEspelhar(), false);
                break;
            case ABERTA:
                warpStateTime += Gdx.graphics.getDeltaTime();

                TextureRegion currentFrame = warp.foward.getKeyFrame(warpStateTime, true);
                batch.draw(currentFrame, isEspelhar() ? getX() + getWidth() / 2 : getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, isEspelhar() ? -getWidth() : getWidth(), getHeight(), 1, 1, getRotacao());
                break;
        }
    }
}
