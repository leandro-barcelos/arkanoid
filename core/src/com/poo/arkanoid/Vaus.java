package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;


public class Vaus extends Animatable {
    private final Texture imgNormal, imgLazer, imgLarge;
    private final Animacao enlargeAnimation, shrinkAnimation, lazerAnimation, delazerAnimation;

    public Vaus(int x, int y, int width, int height) {

        super(x, y, width, height);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("vaus-normal.png");
        imgLazer = new Texture("vaus-lazer.png");
        imgLarge = new Texture("vaus-large.png");

        // CARREGAR ANIMACOES
        enlargeAnimation = new Animacao(new Texture("vaus-large-spritesheet.png"), 6, 1, false);
        shrinkAnimation = new Animacao(new Texture("vaus-large-spritesheet.png"), 6, 1, true);

        lazerAnimation = new Animacao(new Texture("vaus-lazer-spritesheet.png"),8, 1, false);
        delazerAnimation = new Animacao(new Texture("vaus-lazer-spritesheet.png"),8, 1, true);

        setModo("normal");
    }

    public void Mover() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if (getX() - getWidth() / 2 < 31) setX(31 + getWidth() / 2);
        if (getX() - getWidth() / 2 > 383 - getWidth()) setX(383 - getWidth() / 2);
    }

    @Override
    public void changeMode(SpriteBatch batch, String toMode) {
        if (Objects.equals(getModo(), toMode)) return;

        if (!Objects.equals(toMode, "normal") && !Objects.equals(getModo(), "normal")) {
            changeMode(batch, "normal");
            return;
        }

        Animacao toModeAnimation = shrinkAnimation;

        switch (toMode) {
            case "normal":
                if (Objects.equals(getModo(), "lazer")) toModeAnimation = delazerAnimation;
                break;
            case "large":
                setWidth(imgLarge.getWidth());
                toModeAnimation = enlargeAnimation;
                break;
            case "lazer":
                toModeAnimation = lazerAnimation;
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
    public void draw(SpriteBatch batch) {
        if (getAnimationActive()) return;

        setWidth(64);

        switch (getModo()) {
            case "normal":
                setTextura(imgNormal);
                break;
            case "lazer":
                setTextura(imgLazer);
                break;
            case "large":
                setTextura(imgLarge);
                break;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        imgNormal.dispose();
        imgLazer.dispose();
        imgLarge.dispose();
    }
}
