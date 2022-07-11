package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;


public class Vaus extends Prop {
    private final Texture imgNormal, imgLazer, imgLarge, enlargeSheet, lazerSheet;
    private final Animation<TextureRegion> enlargeAnimation, shrinkAnimation, lazerAnimation, delazerAnimation;

    public Vaus(int x, int y, int width, int height) {

        super(x, y, width, height);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("vaus-normal.png");
        imgLazer = new Texture("vaus-lazer.png");
        imgLarge = new Texture("vaus-large.png");

        // CARREGAR ANIMACOES
        enlargeSheet = new Texture("vaus-large-spritesheet.png");
        lazerSheet = new Texture("vaus-lazer-spritesheet.png");


        TextureRegion[][] tmp = TextureRegion.split(enlargeSheet,
                enlargeSheet.getWidth(),
                enlargeSheet.getHeight() / 6);

        TextureRegion[] enlargeFrames = new TextureRegion[6];
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 1; j++) {
                enlargeFrames[index++] = tmp[i][j];
            }
        }

        enlargeAnimation = new Animation<>(0.1f, enlargeFrames);

        TextureRegion[] shrinkFrames = new TextureRegion[6];
        for (int i = enlargeFrames.length - 1, j = 0; i >= 0; i--, j++) {
            shrinkFrames[j] = enlargeFrames[i];
        }

        shrinkAnimation = new Animation<>(0.1f, shrinkFrames);

        tmp = TextureRegion.split(lazerSheet,
                lazerSheet.getWidth(),
                lazerSheet.getHeight() / 8);

        TextureRegion[] lazerFrames = new TextureRegion[8];
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1; j++) {
                lazerFrames[index++] = tmp[i][j];
            }
        }

        lazerAnimation = new Animation<>(0.1f, lazerFrames);

        TextureRegion[] delazerFrames = new TextureRegion[8];
        for (int i = lazerFrames.length - 1, j = 0; i >= 0; i--, j++) {
            delazerFrames[j] = lazerFrames[i];
        }

        delazerAnimation = new Animation<>(0.1f, delazerFrames);

        setStateTime(0f);
        setAnimationActive(false);

        setModo("normal");
    }

    public void Mover() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if (getX() - getWidth() / 2 < 33) setX(33 + getWidth() / 2);
        if (getX() - getWidth() / 2 > 381 - getWidth()) setX(381 - getWidth() / 2);
    }

    @Override
    public void changeMode(SpriteBatch batch, String toMode) {
        if (Objects.equals(getModo(), toMode)) return;

        if (!Objects.equals(toMode, "normal") && !Objects.equals(getModo(), "normal")) {
            changeMode(batch, "normal");
            return;
        }

        Animation<TextureRegion> toModeAnimation = shrinkAnimation;

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

        if (enlargeAnimation.isAnimationFinished(getStateTime())) {
            setModo(toMode);
            setAnimationActive(false);
            setStateTime(0f);
            return;
        }

        TextureRegion currentFrame = toModeAnimation.getKeyFrame(getStateTime(), true);
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
        enlargeSheet.dispose();
    }
}
