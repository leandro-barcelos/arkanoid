package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Nivel {
    Vaus vaus;
    Gate gateTl, gateTr;
    GateWarp gateWarp;
    Bola bola;
    Parede parede;

    Player player;

    Bloco [][]blocos;
//    Inimigos[] inimigos;
    Texture background;
    int levelNum;
    SpriteBatch batch;
    int windowWidth, windowHeight;

    boolean ready;
    float readyTime;

    public Nivel(Player player, SpriteBatch batch, int windowWidth, int windowHeight, int levelNum) throws FileNotFoundException {
        this.levelNum = levelNum;
        this.batch = batch;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.player = player;

        parede = new Parede(32, 384, 449, 0);
        vaus = new Vaus((parede.getLimXEsq() + parede.getLimXDir()) / 2, 57, 64, 14, batch);
        bola = new Bola(0, 66, 8, 8, batch);
        bola.grudar = true;

        blocos = new Bloco[28][11];
        loadMapa();

        gateTl = new Gate(135, 456, 16, 46, true, 90, batch);
        gateTr = new Gate(283, 456, 16, 46, true, 90, batch);
        gateWarp = new GateWarp(392, 57, 16, 46, false, 0, batch);

        switch ((levelNum - 1) % 4) {
            case 0:
                background = new Texture("Telas/background-blue.png");
                break;
            case 1:
              background = new Texture("Telas/background-green.png");
                break;
            case 2:
               background = new Texture("Telas/background-dark-blue.png");
                break;
            case 3:
               background = new Texture("Telas/background-red.png");
                break;
        }

        readyTime = 0;
    }

    public void readyMensage(BitmapFont font) {
        if (readyTime < 1)
            font.draw(batch, "READY", (float) (parede.getLimXEsq() + parede.getLimXDir()) / 2, 57);
        else
            ready = true;
        readyTime += Gdx.graphics.getDeltaTime();
    }

    public void loadMapa() throws FileNotFoundException {
        File mapData = new File("level" + levelNum + ".ark");
        Scanner sc = new Scanner(mapData);
        for (int i = 0; i < 28; i++) {
            String line = sc.nextLine();
            blocos[i] = new Bloco[11];
            for (int j = 0; j < 11; j++) {
                char tmp = line.charAt(j * 2);
                int blocoX = (parede.getLimXEsq() + j * 32);
                int blocoY = ((26 - i) * 16);

                if (tmp == '0') {
                    blocos[i][j] = null;
                }
                else if (tmp == 'S') {
                    blocos[i][j] = new BlocoPrata(blocoX, blocoY, 32, 16, levelNum, batch);
                }
                else if (tmp == 'D') {
                    blocos[i][j] = new BlocoDourado(blocoX, blocoY, 32, 16, batch);
                }
                else {
                    BlocoColorido.Color cor;
                    switch (tmp) {
                        case 'W':
                            cor = BlocoColorido.Color.WHITE;
                            break;
                        case 'O':
                            cor = BlocoColorido.Color.ORANGE;
                            break;
                        case 'C':
                            cor = BlocoColorido.Color.CYAN;
                            break;
                        case 'G':
                            cor = BlocoColorido.Color.GREEN;
                            break;
                        case 'R':
                            cor = BlocoColorido.Color.RED;
                            break;
                        case 'B':
                            cor = BlocoColorido.Color.BLUE;
                            break;
                        case 'P':
                            cor = BlocoColorido.Color.PINK;
                            break;
                        default:
                            cor = BlocoColorido.Color.YELLOW;
                            break;
                    }

                    blocos[i][j] = new BlocoColorido(cor, blocoX, blocoY, 32, 16, batch);
                }
            }
        }

    }

    void draw(BitmapFont font) {
        batch.draw(background, 0,0,windowWidth,windowHeight);


        // PORTOES
        gateTl.draw();
        gateTr.draw();
        gateWarp.draw();

        font.draw(batch, String.valueOf(levelNum), 509, 57);

        // PLAYER INFORMATION
        player.drawScore(447, 311, batch, font);
        player.drawHighscore(447, 250, batch, font);
        player.drawLives(20, 473, batch);

        // BLOCOS
        for (Bloco []i: blocos) {
            for (Bloco j: i)
                if (j != null)
                    j.draw();
        }

        readyMensage(font);

        if (ready && !bola.perdeu()) {
            vaus.draw();
            bola.draw();
        }
    }

    public void moverObjetos() {
        if (!bola.perdeu() && ready) {
            bola.mover();
            vaus.Mover(parede);
        }
    }

    void colisao() {
        if (bola.grudar)
            bola.grudarBarra(vaus);
        else
            vaus.colisao(bola);
        parede.colisao(bola);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 11; j++)
                if (blocos[i][j] != null) {
                    blocos[i][j].colisao(bola, player, blocos);
                    if (blocos[i][j].isQuebrado())
                        blocos[i][j] = null;
                }
        }
    }

    boolean checarVitoria() {
        for (Bloco []i: blocos)
            for (Bloco j: i)
                if (!(j instanceof BlocoDourado) && j != null) return false;

        return true;
    }

    boolean checarDerrota() throws FileNotFoundException {
        if (bola.perdeu()) {
            vaus.destroy();
            if (!vaus.getAnimationActive()) {
                player.decVidas();
                reset();
            }
        }

        return player.getVidas() == 0;
    }


    void reset(){
        vaus.setX((float) (parede.getLimXEsq() + parede.getLimXDir()) / 2);
        vaus.setY(57);

        bola.setVelocidade(0);

        bola.grudar = true;
    }

    public void dispose() {
        vaus.dispose();
        gateTl.dispose();
    }
}
