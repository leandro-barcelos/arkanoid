package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;


public class Parede extends Animatable {
    Texture imgFechada, imgAberta;
    Animacao openAnimation, closeAnimation, warpAnimation;
    
    public Parede(float x, float y, float width, float height) {
        super(x, y, width, height);

        // CARREGAR TEXTURAS
        imgFechada = new Texture("wall-close.png");
        imgAberta = new Texture("wall-open.png");

        // CARREGAR ANIMACOES
        openAnimation = new Animacao(new Texture("wall-open-spritesheet.png"), 1, 8, false);
        closeAnimation = new Animacao(new Texture("wall-open-spritesheet.png"), 1, 8, true);

        warpAnimation = new Animacao(new Texture("wall-warp-spritesheet.png"), 1, 2, false);

        setModo("fechada");
    }

    @Override
    void draw(SpriteBatch batch) {
        if (getAnimationActive()) return;

        switch(getModo()) {
            case "aberta":
                setTextura(imgAberta);
                break;
            case "fechada":
                setTextura(imgFechada);
                break;
            case "warp":
                setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

                TextureRegion currentFrame = warpAnimation.animacao.getKeyFrame(getStateTime(), true);

                if (warpAnimation.animacao.isAnimationFinished(getStateTime())) {
                    setModo("warp");
                    setAnimationActive(false);
                    setStateTime(0f);
                    return;
                }

                batch.draw(currentFrame, getX() - getWidth() / 2, getY(), getWidth(), getHeight());
                return;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    @Override
    void changeMode(SpriteBatch batch, String toMode) {
        if (Objects.equals(getModo(), toMode)) return;

        Animacao toModeAnimation = openAnimation;

        switch (toMode) {
            case "fechada":
                toModeAnimation = closeAnimation;
                break;
            case "aberta":
                if (Objects.equals(getModo(), "warp")) {
                    setModo(toMode);
                    setAnimationActive(false);
                    setStateTime(0f);
                    return;
                }
                break;
            case "warp":
                if (Objects.equals(getModo(), "aberta")) {
                    setModo(toMode);
                    setAnimationActive(false);
                    setStateTime(0f);
                    return;
                }
                break;
        }

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (toModeAnimation.animacao.isAnimationFinished(getStateTime())) {
            setModo(toMode);
            setAnimationActive(false);
            setStateTime(0f);
            return;
        }

        TextureRegion currentFrame = toModeAnimation.animacao.getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }


    @Override
    void dispose() {
        imgFechada.dispose();
        imgAberta.dispose();
    }
}
