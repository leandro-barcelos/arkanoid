package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Animavel extends GameObject {
    private Boolean animationActive;
    private float stateTime;


    public Animavel(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, batch);
        animationActive = false;

        stateTime = 0f;
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
}
