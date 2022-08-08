package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.FileNotFoundException;

public class Nivel {
    Vaus vaus;
    Gate gateTl, gateTr;
    GateWarp gateWarp;
    Bola bola;
    Parede parede;
    Player player;
    Poder poder;
    MatrizBlocos blocosNivel;
    Texture background;
    private final int levelNum;
    SpriteBatch batch;
    int windowWidth, windowHeight;

    boolean messageDone;
    float messageTime;

    public Nivel(Player player, SpriteBatch batch, int windowWidth, int windowHeight, int levelNum) throws FileNotFoundException {
        this.levelNum = levelNum;
        this.batch = batch;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.player = player;

        parede = new Parede(32, 384, 449, 0);
        vaus = new Vaus((parede.getLimXEsq() + parede.getLimXDir()) / 2, 57, 64, 14, batch);
        bola = new Bola(0, 66, 8, 8, batch);
        bola.setGrudar(true);

        blocosNivel = new MatrizBlocos(levelNum, 28, 11, parede.getLimXEsq(), batch);

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

        messageTime = 0;
        messageDone = false;
        poder = null;
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
        blocosNivel.draw();

        if (!messageDone) {
            font.draw(batch, "READY", (float)((parede.getLimXEsq() + parede.getLimXDir()) / 2 - 120), 67);
            messageTime += Gdx.graphics.getDeltaTime();

            if (messageTime >= 1) {
                messageDone = true;
                messageTime = 0;
            }
        }

        if (messageDone && !bola.perdeu()) {
            vaus.draw();
            bola.draw();
        }

        if (poder != null && !poder.colisao(vaus))
            poder.draw();

        for (Laser []i: vaus.getLasers()) {
            for (Laser j: i){
                if (j != null) j.draw();
            }
        }
    }

    public void moverObjetos() {
        if (!bola.perdeu() && messageDone) {
            bola.mover();
            vaus.Mover(parede);
            if (poder != null && !poder.colisao(vaus)) {
                poder.cair();
                if (poder.getY() - poder.getHeight() / 2 <= 0)
                    poder = null;
            }

        }
    }

    void colisao() {
        if (bola.isGrudar())
            bola.grudarBarra(vaus);
        else
            vaus.colisao(bola);

        parede.colisao(bola);

        player.setScore(player.getScore() + blocosNivel.colisao(bola));

        for (int i = 0; i < vaus.getLasers().size(); i++) {
            int pontosLaser;

            if (vaus.getLasers().get(i)[0] != null) {
                pontosLaser = vaus.getLasers().get(i)[0].colisao(blocosNivel);

                if (pontosLaser > 0) {
                    player.setScore(player.getScore() + pontosLaser);
                    vaus.getLasers().get(i)[0] = null;
                }
            } else if (vaus.getLasers().get(i)[1] != null) {

                pontosLaser = vaus.getLasers().get(i)[1].colisao(blocosNivel);

                if (pontosLaser > 0) {
                    player.setScore(player.getScore() + pontosLaser);
                    vaus.getLasers().get(i)[1] = null;
                }
            }
        }

        if (poder != null) {
            if (poder.colisao(vaus)) {
                if (poder instanceof PoderVaus) {
                    ((PoderVaus) poder).ativar(vaus);
                } else {
                    ((PoderBola) poder).ativar(bola);
                }

                if (!vaus.getAnimationActive())
                    poder = null;
            }
        }
    }

    boolean checarVitoria() {
        return blocosNivel.isTudoQuebrado() || gateWarp.colisao(vaus);
    }

    boolean checarDerrota() throws FileNotFoundException {
        if (bola.perdeu()) {
            vaus.destroy();
            if (!vaus.getAnimationActive()) {
                player.decVidas();
                reset();
            }
        }
        if (player.getVidas() == 0) {
            blocosNivel = new MatrizBlocos(levelNum, 28, 11, parede.getLimXEsq(), batch);
            player.reset();
            return true;
        }

        return false;
    }


    void reset(){
        vaus.setX((float) (parede.getLimXEsq() + parede.getLimXDir()) / 2);
        vaus.setY(57);
        vaus.setHabilidade(Vaus.vausHabilidade.NORMAL);

        bola.setVelocidade(0);

        bola.setGrudar(true);
        poder = null;

        messageDone = true;
    }

    public void dispose() {
        vaus.dispose();
        gateTl.dispose();
    }

    public int getLevelNum() {
        return levelNum;
    }
}
