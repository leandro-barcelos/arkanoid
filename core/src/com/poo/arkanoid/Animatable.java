package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Animatable extends Prop{
    private Boolean animationActive;
    private String modo;
    private float stateTime;

    public Animatable(float x, float y, float width, float height){
        super(x,y, width, height);
        animationActive = false;
        stateTime = 0f;
    }

    abstract void changeMode(SpriteBatch batch, String toMode);

    public Boolean getAnimationActive() {
        return animationActive;
    }

    public void setAnimationActive(Boolean animationActive) {
        this.animationActive = animationActive;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
