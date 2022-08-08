package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Animavel extends GameObject {
    private Boolean animationActive;
    private float stateTime;
    private Animacao animacao;

    public Animavel(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, batch);
        animationActive = false;

        stateTime = 0f;
    }

    public boolean rodarAnimacao() {
        if (animacao.getAtiva() == null) return true;

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (animacao.getAtiva().isAnimationFinished(getStateTime())) {

            setAnimationActive(false);
            setStateTime(0f);
            return true;
        }

        TextureRegion currentFrame = animacao.getAtiva().getKeyFrame(getStateTime(), true);
        getBatch().draw(currentFrame, getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());

        return false;
    }

    public boolean rodarAnimacao(boolean espelhar, float rotacao) {
        if (animacao.getAtiva() == null) return true;

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (animacao.getAtiva().isAnimationFinished(getStateTime())) {
            setAnimationActive(false);
            setStateTime(0f);
            return true;
        }

        TextureRegion currentFrame = getAnimacao().getAtiva().getKeyFrame(getStateTime(), true);
        getBatch().draw(currentFrame, getX() - getWidth() / 2, getY(), 0, getHeight(), getWidth() / 2, getHeight() / 2, (espelhar ? -1 : 1), 1, rotacao);

        return false;
    }

    public Boolean getAnimationActive() {
        return animationActive;
    }

    public void setAnimationActive(Boolean animationActive) {
        this.animationActive = animationActive;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Animacao getAnimacao() {
        return animacao;
    }

    public void setAnimacao(Animacao animacao) {
        this.animacao = animacao;
    }
}
