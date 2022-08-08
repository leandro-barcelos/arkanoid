package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Poder extends Animavel implements Colidivel<Boolean, Vaus> {
    public Poder(float x, float y, float width, float height, Texture rodarSpritesheet, SpriteBatch batch) {
        super(x, y, width, height, batch);

        setAnimacao(new Animacao(rodarSpritesheet, 8, 1, 1f));
        getAnimacao().ativarForward();
    }

    @Override
    public void draw() {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        TextureRegion currentFrame = getAnimacao().getAtiva().getKeyFrame(getStateTime(), true);
        getBatch().draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void cair() {
        if (getY() + getHeight() / 2 <= 0)
            return;

        setY(getY() - 140 * Gdx.graphics.getDeltaTime());
    }

    @Override
    public Boolean colisao(Vaus vaus) {
        boolean colisaoX = getX() + getWidth() / 2 > vaus.getX() - vaus.getWidth() / 2 && getX() - getWidth() / 2 < vaus.getX() + vaus.getWidth() / 2;
        boolean colisaoY = getY() - getWidth() / 2 < vaus.getY() + vaus.getHeight() / 2 && getY() + getWidth() / 2 > vaus.getY() - vaus.getHeight() / 2;

        return colisaoX && colisaoY;
    }

    @Override
    public void dispose() {
        getTextura().dispose();
    }
}
