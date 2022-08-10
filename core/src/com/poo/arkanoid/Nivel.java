package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nivel {
    private final Vaus vaus;
    private final Gate gateTl;
    private final Gate gateTr;
    private final GateWarp gateWarp;
    private final Parede parede;
    private final int levelNum;
    private final SpriteBatch batch;
    private final int windowWidth;
    private final int windowHeight;
    private final Sound poderSfx;
    private Bola[] bolas;
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

        bolas = new Bola[3];
        bolas[0] = new Bola(0, 66, batch);
        bolas[0].grudarBarra(vaus);
        bolas[0].setGrudar(true);

        blocosNivel = new MatrizBlocos(levelNum, 28, 11, parede.getLimXEsq(), batch);

        gateTl = new Gate(135, 456, true, 90, batch);
        gateTr = new Gate(283, 456, true, 90, batch);
        gateWarp = new GateWarp(392, 57, true, 0, batch);

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

        poderSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/Poder.wav"));
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
            font.draw(batch, "READY", (float) ((parede.getLimXEsq() + parede.getLimXDir()) / 2) - 64, 67);
            readyTime += Gdx.graphics.getDeltaTime();

            if (readyTime >= 1) {
                readyDone = true;
                readyTime = 0;
            }
        }

        if (readyDone && ((bolas[0] != null && !bolas[0].perdeu())
                || (bolas[1] != null && !bolas[1].perdeu())
                || (bolas[2] != null && !bolas[2].perdeu()))) {

            vaus.draw();
            for (Bola i : bolas)
                if (i != null)
                    i.draw();
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
        if (((bolas[0] != null && !bolas[0].perdeu())
                || (bolas[1] != null && !bolas[1].perdeu())
                || (bolas[2] != null && !bolas[2].perdeu())) && readyDone) {

            for (Bola i : bolas)
                if (i != null)
                    i.mover();

            vaus.Mover(parede);
            if (poder != null && !poder.colisao(vaus)) {
                poder.cair();
                if (poder.getY() - poder.getHeight() / 2 <= 0)
                    poder = null;
            }

        }
    }

    public void colisao(Player player) {
        for (int i = 0; i < 3; i++)
            if (bolas[i] != null) {
                if (vaus.colisao(bolas[i]) && bolas[i].isGrudar())
                    bolas[i].grudarBarra(vaus);

                parede.colisao(bolas[i]);

                player.setScore(player.getScore() + blocosNivel.colisao(bolas[i]));

                if (bolas[i].perdeu()) bolas[i] = null;
            }

        if (gateWarp.colisao(vaus)) {
            player.setScore(player.getScore() + 10000);
        }

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
                } else if (poder instanceof PoderBola) {
                    poderSfx.play(0.5f);
                    for (Bola i : bolas) {
                        if (i != null)
                            ((PoderBola) poder).ativar(i);
                    }
                } else if (poder instanceof PoderDisruption) {
                    poderSfx.play(0.5f);
                    if (!(bolas[1] != null && bolas[2] != null ||
                            bolas[0] != null && bolas[2] != null ||
                            bolas[0] != null && bolas[1] != null)) {
                        for (int i = 0; i < 3; i++)
                            if (bolas[i] != null) {
                                int i1 = i - 1;
                                int i2 = i + 1;

                                if (i1 == -1) i1 = 2;
                                if (i2 == 3) i2 = 0;

                                bolas[i1] = new Bola(bolas[i].getX(), bolas[i].getY(), batch);
                                bolas[i1].setxSpeed(bolas[i].getxSpeed());
                                bolas[i1].setySpeed(bolas[i].getySpeed());
                                bolas[i1].setVelocidade(bolas[i].getVelocidade());
                                bolas[i1].setAngulo(bolas[i].getAngulo() + 0.785398f);

                                bolas[i2] = new Bola(bolas[i].getX(), bolas[i].getY(), batch);
                                bolas[i2].setxSpeed(bolas[i].getxSpeed());
                                bolas[i2].setySpeed(bolas[i].getySpeed());
                                bolas[i2].setVelocidade(bolas[i].getVelocidade());
                                bolas[i2].setAngulo(bolas[i].getAngulo() - 0.785398f);
                            }
                    }
                } else if (poder instanceof PoderGate) {
                    ((PoderGate) poder).ativar(gateWarp, vaus);
                } else if (poder instanceof PoderPlayer) {
                    poderSfx.play(0.5f);
                    ((PoderPlayer) poder).ativar(player);
                }

                if (!vaus.getAnimationActive() && !gateWarp.getAnimationActive())
                    poder = null;
            }
        }
    }

    public boolean checarPassarNivel() {
        return blocosNivel.isTudoQuebrado() || gateWarp.colisao(vaus);
    }

    public boolean checarVitoria(Player player) {
        return player.getNivelAtual() > 10;
    }

    public boolean checarDerrota(Player player) {
        if ((bolas[0] == null || bolas[0].perdeu())
                && (bolas[1] == null || bolas[1].perdeu())
                && (bolas[2] == null || bolas[2].perdeu())) {
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

        gateWarp.setEstado(Gate.estadoParede.FECHADA);

        bolas = new Bola[3];
        bolas[0] = new Bola(0, 66, batch);
        bolas[0].grudarBarra(vaus);
        bolas[0].setGrudar(true);

        poder = null;

        readyDone = true;
    }

    public void dispose() {
        vaus.dispose();
        gateTl.dispose();
        gateTr.dispose();
        gateWarp.dispose();
        blocosNivel.getHitSound().dispose();
        poderSfx.dispose();
        parede.getHit().dispose();
        for (Bola i : bolas)
            if(i != null)
                i.dispose();

        poder.dispose();
        blocosNivel.dispose();
        background.dispose();
    }

    public int getLevelNum() {
        return levelNum;
    }

    public Vaus getVaus() {
        return vaus;
    }

    public Bola[] getBolas() {
        return bolas;
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

    public GateWarp getGateWarp() {
        return gateWarp;
    }
}
