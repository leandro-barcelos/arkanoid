package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
    private final SpriteBatch batch;
    private float x, y;
    private float height, width;
    private Texture textura;

    public GameObject(float x, float y, float width, float height, SpriteBatch batch) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.batch = batch;
    }

    abstract void draw();

    abstract void dispose();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
