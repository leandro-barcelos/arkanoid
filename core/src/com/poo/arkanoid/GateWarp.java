package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GateWarp extends Gate implements Colidivel<Boolean, Vaus> {
    private float warpStateTime;

    public GateWarp(float x, float y, boolean espelhado, int rotacao, SpriteBatch batch) {
        super(x, y, espelhado, rotacao, batch);
        setAnimacao(new Animacao(new Texture("Gates/wall-warp-spritesheet.png"), 1, 2, 1f));
        getAnimacao().ativarForward();

        warpStateTime = 0f;
    }

    @Override
    public void draw() {
        if (getAnimationActive()) return;

        switch (getEstado()) {
            case FECHADA:
                setTextura(getImgFechada());
                setWidth(getTextura().getWidth());
                getBatch().draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, getRotacao(), 0, 0, (int) getWidth(), (int) getHeight(), isEspelhar(), false);
                break;
            case ABERTA:
                warpStateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentFrame = getAnimacao().getAtiva().getKeyFrame(warpStateTime, true);
                getBatch().draw(currentFrame, isEspelhar() ? getX() + getWidth() / 2 : getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, isEspelhar() ? -getWidth() : getWidth(), getHeight(), 1, 1, getRotacao());
                break;
        }
    }


    @Override
    public Boolean colisao(Vaus objeto) {
        return (objeto.getX() + objeto.getWidth() / 2 >= getX() - getWidth() / 2 && getEstado() == estadoParede.ABERTA);
    }
}
