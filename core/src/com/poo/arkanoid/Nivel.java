package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nivel {
    Vaus vaus;
    Gate gateTl, gateTr;
    GateWarp gateWarp;
    Bola bola;
    Parede parede;
//    Blocos
//    Inimigos[] inimigos;
    Texture background;
    int levelNum;
    SpriteBatch batch;
    int windowWidth, windowHeight;

    public Nivel(SpriteBatch batch, int windowWidth, int windowHeight, int levelNum) {
        this.levelNum = levelNum;
        this.batch = batch;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        parede = new Parede(32, 384, 449, 0);
        vaus = new Vaus((parede.getLimXEsq() + parede.getLimXDir()) / 2, 66, 64, 14, batch);
        bola = new Bola(0, 66, 8, 8, batch);
        bola.grudar = true;


        gateTl = new Gate(178, 590, 16, 46, true, 90, batch);

        switch ((levelNum - 1) % 4) {
            case 0:
                background = new Texture("background-blue.png");
                break;
            case 1:
              background = new Texture("background-green.png");
                break;
            case 2:
               background = new Texture("background-dark-blue.png");
                break;
            case 3:
               background = new Texture("background-red.png");
                break;
        }
    }

    void draw() {
        batch.draw(background, 0,0,windowWidth,windowHeight);
        vaus.draw();
        gateTl.draw();
    }

    void colisao() {
        if (bola.grudar)
            bola.grudarBarra(vaus);
        else
            vaus.colisao(bola);
        parede.colisao(bola);
    }

    void checarDerrota() {
        if (bola.perdeu()) {
            vaus.destroy();
            if (!vaus.getAnimationActive())
                reset();
        } else {
            bola.draw();
            bola.mover();
            vaus.Mover(parede);
        }
    }

    void reset() {
        vaus.setX(288);
        vaus.setY(86);

        bola.grudar = true;
    }

    public void dispose() {
        vaus.dispose();
        gateTl.dispose();
    }
}
