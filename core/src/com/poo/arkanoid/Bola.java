package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bola extends GameObject {

    private float xSpeed;
    private float ySpeed;
    private boolean grudar;
    private int velocidade;

    public Bola(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, batch);

        setTextura(new Texture("ball.png"));
        ySpeed = 0;
        xSpeed = 0;
        velocidade = 0;

        grudar = false;
    }

    void mover() {
        setX(getX() + xSpeed * Gdx.graphics.getDeltaTime());
        setY(getY() + ySpeed * Gdx.graphics.getDeltaTime());
    }

    void grudarBarra(Vaus vaus) {
        setX(vaus.getX() + (vaus.getWidth() / 2) - 12);
        setY(vaus.getY() + (vaus.getHeight() / 2) + getHeight() / 2 + 1);

        setVelocidade(250);
    }

    public boolean perdeu() {
        return getY() - getHeight() / 2 <= 0;
    }

    @Override
    void draw() {
        getBatch().draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    @Override
    void dispose() {
        getTextura().dispose();
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public boolean isGrudar() {
        return grudar;
    }

    public void setGrudar(boolean grudar) {
        this.grudar = grudar;
    }
}
