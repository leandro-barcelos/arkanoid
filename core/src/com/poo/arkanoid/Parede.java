package com.poo.arkanoid;

public class Parede implements Colidivel<Void, Bola> {
    private final int limXEsq;
    private final int limXDir;
    private final int limYTop;
    private final int limYBot;

    public Parede(int limXEsq, int limXDir, int limYTop, int limYBot) {
        this.limXEsq = limXEsq;
        this.limXDir = limXDir;
        this.limYTop = limYTop;
        this.limYBot = limYBot;
    }

    @Override
    public Void colisao(Bola b) {
        if (b.getX() - b.getWidth() / 2 <= limXEsq) {
            b.setX(limXEsq + b.getWidth() / 2);
            b.setxSpeed(-b.getxSpeed());
            b.incVelocidade(5);
        } else if (b.getX() + b.getWidth() / 2 >= limXDir) {
            b.setX(limXDir - b.getWidth() / 2);
            b.setxSpeed(-b.getxSpeed());
            b.incVelocidade(5);
        } else if (b.getY() + b.getHeight() / 2 >= limYTop) {
            b.setY(limYTop - b.getHeight() / 2);
            b.setySpeed(-b.getySpeed());
            b.incVelocidade(5);
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
}
