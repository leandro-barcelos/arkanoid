package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;


public class Parede extends Prop{
    Texture imgFechada, imgAberta, openSheet, warpSheet;
    Animation<TextureRegion> openAnimation, closeAnimation, warpAnimation;
    
    public Parede(float x, float y, float width, float height) {
        super(x, y, width, height);

        // CARREGAR TEXTURAS
        imgFechada = new Texture("wall-close.png");
        imgAberta = new Texture("wall-open.png");

        // CARREGAR ANIMACOES
        openSheet = new Texture("wall-open-spritesheet.png");
        warpSheet = new Texture("wall-warp-spritesheet.png");

        TextureRegion[][] tmp = TextureRegion.split(openSheet,
                openSheet.getWidth() / 8,
                openSheet.getHeight());

        TextureRegion[] openFrames = new TextureRegion[8];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                openFrames[index++] = tmp[i][j];
            }
        }

        openAnimation = new Animation<>(0.1f, openFrames);

        TextureRegion[] closeFrames = new TextureRegion[8];
        for (int i = closeFrames.length - 1, j = 0; i >= 0; i--, j++) {
            closeFrames[j] = closeFrames[i];
        }

        closeAnimation = new Animation<>(0.1f, closeFrames);
    }

    @Override
    void draw(SpriteBatch batch) {
        if (getAnimationActive()) return;

        setWidth(64);

        switch(getModo()) {
            case "aberta":
                setTextura(imgAberta);
                break;
            case "fechada":
                setTextura(imgFechada);
                break;
            case "warp":
                setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

                TextureRegion currentFrame = warpAnimation.getKeyFrame(getStateTime(), true);

                if (warpAnimation.isAnimationFinished(getStateTime())) {
                    setModo("lazer");
                    setAnimationActive(false);
                    setStateTime(0f);
                    return;
                }

                batch.draw(currentFrame, getX() - getWidth() / 2, getY());
                return;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    @Override
    void changeMode(SpriteBatch batch, String toMode) {

    }


    @Override
    void dispose() {
        imgFechada.dispose();
        imgAberta.dispose();
        openSheet.dispose();
        warpSheet.dispose();
    }
}
