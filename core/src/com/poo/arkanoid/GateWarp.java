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
        warp = new Animacao(new Texture("wall-warp-spritesheet.png"), 1, 2);
        warpStateTime = 0f;
    }

    @Override
    public void draw() {
        if (getAnimationActive()) return;

        switch (estado) {
            case FECHADA:
                setTextura(imgFechada);
                setWidth(getTextura().getWidth());
                batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
                break;
            case ABERTA:
                warpStateTime += Gdx.graphics.getDeltaTime();

                TextureRegion currentFrame = warp.foward.getKeyFrame(warpStateTime, true);
                batch.draw(currentFrame, getX() - getWidth() / 2, getY(), getWidth(), getHeight());
                break;
        }
    }

//    public void mudarEstado(SpriteBatch batch, ParedeWarp.estadoParede paraEstado) {
//        if (estado == paraEstado) return;
//
//        Animation<TextureRegion> toModeAnimation;
//
//        if (paraEstado == estadoParede.FECHADA) toModeAnimation = openAnimation.backward;
//        else toModeAnimation = openAnimation.foward;
//
//        setAnimationActive(true);
//
//        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
//
//        if (toModeAnimation.isAnimationFinished(getStateTime())) {
//            estado = paraEstado;
//            setAnimationActive(false);
//            setStateTime(0f);
//            return;
//        }
//
//        TextureRegion currentFrame = toModeAnimation.getKeyFrame(getStateTime(), true);
//        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
//    }
}
