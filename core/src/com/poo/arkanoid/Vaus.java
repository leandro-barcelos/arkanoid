package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Vaus extends Animatable {

    public vausHabilidade getHabilidade() {
        return habilidade;
    }

    public enum vausHabilidade {
        NORMAL,
        LARGE,
        LAZER
    }

    private vausHabilidade habilidade;

    private final Texture imgNormal, imgLazer, imgLarge;
    private final Animacao toLargeAnimation, toLazerAnimation;

    public Vaus(int x, int y, int width, int height, SpriteBatch batch) {
        super(x, y, width, height, batch);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("vaus-normal.png");
        imgLazer = new Texture("vaus-lazer.png");
        imgLarge = new Texture("vaus-large.png");

        // CARREGAR ANIMACOES
        toLargeAnimation = new Animacao(new Texture("vaus-large-spritesheet.png"), 6, 1);
        toLazerAnimation = new Animacao(new Texture("vaus-lazer-spritesheet.png"),8, 1);

        habilidade = vausHabilidade.NORMAL;
    }

    public void Mover() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if (getX() - getWidth() / 2 < 31) setX(31 + getWidth() / 2);
        if (getX() - getWidth() / 2 > 383 - getWidth()) setX(383 - getWidth() / 2);
    }


    public void changeMode(vausHabilidade toMode) {
        if (habilidade == toMode) return;

        if (toMode != vausHabilidade.NORMAL  && habilidade != vausHabilidade.NORMAL) {
            changeMode(vausHabilidade.NORMAL);
            return;
        }

        Animation<TextureRegion> toModeAnimation = toLargeAnimation.backward;

        switch (toMode) {
            case NORMAL:
                if (habilidade == vausHabilidade.LAZER) toModeAnimation = toLazerAnimation.backward;
                break;
            case LARGE:
                setWidth(imgLarge.getWidth());
                toModeAnimation = toLargeAnimation.foward;
                break;
            case LAZER:
                toModeAnimation = toLazerAnimation.foward;
                break;
        }

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (toModeAnimation.isAnimationFinished(getStateTime())) {
            habilidade = toMode;
            setAnimationActive(false);
            setStateTime(0f);
            return;
        }

        TextureRegion currentFrame = toModeAnimation.getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    @Override
    public void draw() {
        if (getAnimationActive()) return;

        setWidth(64);

        switch (habilidade) {
            case NORMAL:
                setTextura(imgNormal);
                break;
            case LAZER:
                setTextura(imgLazer);
                break;
            case LARGE:
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
