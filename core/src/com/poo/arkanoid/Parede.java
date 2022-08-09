package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Parede implements Colidivel<Void, Bola> {
    private final int limXEsq;
    private final int limXDir;
    private final int limYTop;
    private final int limYBot;
    private final Sound hit;

    public Parede(int limXEsq, int limXDir, int limYTop, int limYBot) {
        this.limXEsq = limXEsq;
        this.limXDir = limXDir;
        this.limYTop = limYTop;
        this.limYBot = limYBot;

        hit = Gdx.audio.newSound(Gdx.files.internal("sfx/ColisaoParede.wav"));
    }

    @Override
    public Void colisao(Bola b) {

        if (b.getX() - b.getWidth() / 2 <= limXEsq) {
            b.setX(limXEsq + b.getWidth() / 2);
            b.setxSpeed(-b.getxSpeed());
            b.incVelocidade(5);
            hit.play(0.5f);
        } else if (b.getX() + b.getWidth() / 2 >= limXDir) {
            b.setX(limXDir - b.getWidth() / 2);
            b.setxSpeed(-b.getxSpeed());
            b.incVelocidade(5);
            hit.play(0.5f);
        } else if (b.getY() + b.getHeight() / 2 >= limYTop) {
            b.setY(limYTop - b.getHeight() / 2);
            b.setySpeed(-b.getySpeed());
            b.incVelocidade(2);
            hit.play(0.5f);
        }

        return null;
    }

    public int getLimXEsq() {
        return limXEsq;
    }

    public int getLimXDir() {
        return limXDir;
    }

    public int getLimYTop() {
        return limYTop;
    }

    public int getLimYBot() {
        return limYBot;
    }

    public Sound getHit() {
        return hit;
    }
}
