package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {
    Vaus vaus;
    Parede paredeBl, paredeTl, paredeTr;
    ParedeWarp paredeWarp;
//    Bola bola;
//    Blocos
//    Inimigos[] inimigos;
    Texture background;
    int levelNum;
    SpriteBatch batch;

    public Level(int levelNum, SpriteBatch batch) {
        this.levelNum = levelNum;

        vaus = new Vaus(207, 49, 64, 14, batch);

        paredeBl = new Parede(23,34, 16,46, batch);
        paredeTl = new Parede(130,449, 16,46, batch);
    }

    public void dispose() {
        vaus.dispose();
        paredeBl.dispose();
        paredeTl.dispose();
    }
}
