package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nivel {
    private final Vaus vaus;
    private final Gate gateTl;
    private final Gate gateTr;
    private final GateWarp gateWarp;
    private final Bola bola;
    private final Parede parede;
    private final int levelNum;
    private final SpriteBatch batch;
    private final int windowWidth;
    private final int windowHeight;
    private Poder poder;
    private MatrizBlocos blocosNivel;
    private Texture background;
    private boolean readyDone;
    private float readyTime;

    public Nivel(SpriteBatch batch, int windowWidth, int windowHeight, int levelNum) {
        this.levelNum = levelNum;
        this.batch = batch;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        parede = new Parede(32, 384, 449, 0);
        vaus = new Vaus((parede.getLimXEsq() + parede.getLimXDir()) / 2, 57, batch);
        bola = new Bola(0, 66, batch);
        bola.grudarBarra(vaus);
        bola.setGrudar(true);

        blocosNivel = new MatrizBlocos(levelNum, 28, 11, parede.getLimXEsq(), batch);

        gateTl = new Gate(135, 456, true, 90, batch);
        gateTr = new Gate(283, 456, true, 90, batch);
        gateWarp = new GateWarp(392, 57, false, 0, batch);

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
        readyDone = false;
        poder = null;
    }

    public void draw(BitmapFont font, Player player) {
        batch.draw(background, 0, 0, windowWidth, windowHeight);


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

        if (!readyDone) {
            font.draw(batch, "READY", (float) ((parede.getLimXEsq() + parede.getLimXDir()) / 2 - 120), 67);
            readyTime += Gdx.graphics.getDeltaTime();

            if (readyTime >= 1) {
                readyDone = true;
                readyTime = 0;
            }
        }

        if (readyDone && !bola.perdeu()) {
            vaus.draw();
            bola.draw();
        }

        if (poder != null && !poder.colisao(vaus))
            poder.draw();

        for (Laser[] i : vaus.getLasers()) {
            for (Laser j : i) {
                if (j != null) j.draw();
            }
        }
    }

    public void moverObjetos() {
        if (!bola.perdeu() && readyDone) {
            bola.mover();
            vaus.Mover(parede);
            if (poder != null && !poder.colisao(vaus)) {
                poder.cair();
                if (poder.getY() - poder.getHeight() / 2 <= 0)
                    poder = null;
            }

        }
    }

    public void colisao(Player player) {
        if (vaus.colisao(bola) && bola.isGrudar())
            bola.grudarBarra(vaus);

        parede.colisao(bola);

        player.setScore(player.getScore() + blocosNivel.colisao(bola));

        for (int i = 0; i < vaus.getLasers().size(); i++) {
            int pontosLaser;

            if (vaus.getLasers().get(i)[0] != null) {
                pontosLaser = vaus.getLasers().get(i)[0].colisao(blocosNivel);

                if (pontosLaser >= 0) {
                    player.setScore(player.getScore() + pontosLaser);
                    vaus.getLasers().get(i)[0] = null;
                }
            }

            if (vaus.getLasers().get(i)[1] != null) {

                pontosLaser = vaus.getLasers().get(i)[1].colisao(blocosNivel);

                if (pontosLaser >= 0) {
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

    public boolean checarVitoria() {
        return blocosNivel.isTudoQuebrado() || gateWarp.colisao(vaus);
    }

    public boolean checarDerrota(Player player) {
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


    public void reset() {
        vaus.setX((float) (parede.getLimXEsq() + parede.getLimXDir()) / 2);
        vaus.setY(57);
        vaus.setHabilidade(Vaus.vausHabilidade.NORMAL);

        bola.setVelocidade(0);

        bola.setGrudar(true);
        bola.grudarBarra(vaus);
        poder = null;

        readyDone = true;
    }

    public void dispose() {
        vaus.dispose();
        gateTl.dispose();
    }

    public int getLevelNum() {
        return levelNum;
    }

    public Vaus getVaus() {
        return vaus;
    }

    public Bola getBola() {
        return bola;
    }

    public Poder getPoder() {
        return poder;
    }

    public void setPoder(Poder poder) {
        this.poder = poder;
    }

    public MatrizBlocos getBlocosNivel() {
        return blocosNivel;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
