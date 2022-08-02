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
        imgNormal = new Texture("Vaus/vaus-normal.png");
        imgLazer = new Texture("Vaus/vaus-lazer.png");
        imgLarge = new Texture("Vaus/vaus-large.png");

        // CARREGAR ANIMACOES
        toLargeAnimation = new Animacao(new Texture("Vaus/vaus-large-spritesheet.png"), 6, 1);
        toLazerAnimation = new Animacao(new Texture("Vaus/vaus-lazer-spritesheet.png"), 8, 1);
        destructionAnimation = new Animacao(new Texture("Vaus/vaus-break-spritesheet.png"),8, 1);

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
            if (b.getVelocidade() == 0 ) b.setVelocidade(300);
            else if (b.getVelocidade() < 500) b.setVelocidade(b.getVelocidade() + 2);

            int maxGrau = 70;

            float distancia = b.getX() - getX();
            float intervaloAngulos =  (float) maxGrau / (getWidth() / 2);
            float teta = maxGrau;

            for (int i = (int) -(getWidth() / 2 - 1); i <= (getWidth() / 2); i++) {
                if (distancia <= i) {
                    if (i <= 0) teta = (maxGrau - i * intervaloAngulos) - 1;
                    else teta = (maxGrau + -(i - 1) * intervaloAngulos) + 1;
                    break;
                }
            }

            b.setxSpeed((float) (b.getVelocidade() * Math.cos(Math.PI / 180 * teta)));
            b.setySpeed((float) (b.getVelocidade() * Math.sin(Math.PI / 180 * teta)));
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
