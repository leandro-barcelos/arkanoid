package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Poder extends Animatable {

    Animacao rodar;
    public Poder(float x, float y, float width, float height, Texture rodarSpritesheet, SpriteBatch batch) {
        super(x, y, width, height, batch);

        rodar = new Animacao(rodarSpritesheet, 8, 1);
    }


    @Override
    void draw() {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        TextureRegion currentFrame = rodar.foward.getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    @Override
    void dispose() {

    }
}
