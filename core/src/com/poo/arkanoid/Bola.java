package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bola extends GameObject {

    private float xSpeed;
    private float ySpeed;
    private boolean grudar;
    private int velocidade;

    public Bola(float x, float y, SpriteBatch batch) {
        super(x, y, 8, 8, batch);

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
        setX(vaus.getX() + 20);
        setY(vaus.getY() + (vaus.getHeight() / 2) + getHeight() / 2 + 1);

        setxSpeed(0);
        setySpeed(0);
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
        double cosTeta = xSpeed / this.velocidade;
        double sinTeta = ySpeed / this.velocidade;

        xSpeed = (float) (velocidade * cosTeta);
        ySpeed = (float) (velocidade * sinTeta);

        this.velocidade = velocidade;
    }

    public float getAngulo() {
        double cosTeta = xSpeed / velocidade;
        return (float) Math.acos(cosTeta);
    }

    public void setAngulo(float angulo) {
        xSpeed = (float) (velocidade * Math.cos(angulo));
        ySpeed = (float) (velocidade * Math.sin(angulo));
    }

    public void incVelocidade(int valor) {
        double cosTeta = xSpeed / velocidade;
        double sinTeta = ySpeed / velocidade;

        if (velocidade + valor >= 300 && velocidade + valor <= 50) {
            if (valor < 0)
                velocidade = 50;
            else
                velocidade = 300;
        } else
            velocidade += valor;

        xSpeed = (float) (velocidade * cosTeta);
        ySpeed = (float) (velocidade * sinTeta);
    }

    public boolean isGrudar() {
        return grudar;
    }

    public void setGrudar(boolean grudar) {
        this.grudar = grudar;
    }
}
