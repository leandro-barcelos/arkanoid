package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Vaus extends Animavel {

    public enum vausHabilidade {
        NORMAL,
        LARGE,
        LAZER
    }

    private final Texture imgNormal, imgLazer, imgLarge;
    private final Animacao toLargeAnimation, toLazerAnimation, destructionAnimation;
    private vausHabilidade habilidade;

    public Vaus(int x, int y, int width, int height, SpriteBatch batch) {
        super(x, y, width, height, batch);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("vaus-normal.png");
        imgLazer = new Texture("vaus-lazer.png");
        imgLarge = new Texture("vaus-large.png");

        // CARREGAR ANIMACOES
        toLargeAnimation = new Animacao(new Texture("vaus-large-spritesheet.png"), 6, 1);
        toLazerAnimation = new Animacao(new Texture("vaus-lazer-spritesheet.png"), 8, 1);
        destructionAnimation = new Animacao(new Texture("vaus-break-spritesheet.png"),8, 1);

        habilidade = vausHabilidade.NORMAL;
    }

    public vausHabilidade getHabilidade() {
        return habilidade;
    }

    public void Mover(Parede parede) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if (getX() - getWidth() / 2 < parede.getLimXEsq())
            setX(parede.getLimXEsq() + getWidth() / 2);
        if (getX() - getWidth() / 2 > parede.getLimXDir() - getWidth())
            setX(parede.getLimXDir() - getWidth() / 2);
    }

    public void changeMode(vausHabilidade toMode) {
        if (habilidade == toMode) return;

        if (toMode != vausHabilidade.NORMAL && habilidade != vausHabilidade.NORMAL) {
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
        batch.draw(currentFrame, getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
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
        batch.draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    void colisao(Bola b) {
        boolean colidiu;
        colidiu = (b.getY() - 1 - (b.getHeight() / 2) <= (getY() + getHeight() / 2) && b.getY() + b.getHeight() / 2 >= (getY() - getHeight() / 2))
                && (b.getX() >= getX() - (getWidth() / 2) && b.getX() <= getX() + (getWidth() / 2));
        if (colidiu) {
            if (b.velocidade == 0 ) b.velocidade = 250;
            else if (b.velocidade < 750) b.velocidade += 10;

            float distancia = b.getX() - getX();
            float intervaloAngulos =  (float) 90 / (getWidth() / 2);
            float teta = 90f;

            for (int i = (int) -(getWidth() / 2 - 1); i <= (getWidth() / 2); i++) {
                if (distancia <= i) {
                    if (i <= 0) teta = (90 - i * intervaloAngulos) - 1;
                    else teta = (90 + -(i - 1) * intervaloAngulos) + 1;
                    break;
                }
            }

            b.setxSpeed((float) (b.velocidade * Math.cos(Math.PI / 180 * teta)));
            b.setySpeed((float) (b.velocidade * Math.sin(Math.PI / 180 * teta)));
        }
    }

    void destroy() {

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (destructionAnimation.foward.isAnimationFinished(getStateTime())) {
            setAnimationActive(false);
            setStateTime(0f);
            return;
        }

        TextureRegion currentFrame = destructionAnimation.foward.getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        imgNormal.dispose();
        imgLazer.dispose();
        imgLarge.dispose();
    }
}
