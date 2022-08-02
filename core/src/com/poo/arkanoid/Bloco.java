package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public abstract class Bloco extends GameObject{
    private final int pontos;
    private int durabilidade;

    private final int texX;
    private final int texY;


    public Bloco(float x, float y, int texX, int texY, float width, float height, int pontos, int durabilidade, SpriteBatch batch) {
        super(x, y, width, height, batch);

        this.texX = texX;
        this.texY = texY;
        this.durabilidade = durabilidade;
        this.pontos = pontos;
        setTextura(new Texture("blocos.png"));
    }

    @Override
    public void draw() {
        if (durabilidade == 0)  return;
        TextureRegion texturaBloco = new TextureRegion(getTextura(), texX, texY, (int) getWidth(), (int) getHeight());
        batch.draw(texturaBloco, getX(), getY(),getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        getTextura().dispose();
    }

    public void colisao(Bola b, Player p, Bloco [][]matrizBlocos) {

        int[] coordTl = {(int) (getX() - 4), (int) (getY() + getHeight() + 4)};
        int[] coordBl = {(int) (getX() - 4), (int) (getY() - 4)};
        int[] coordTr = {(int) (getX() + getWidth() + 4), (int) (getY() + getHeight() + 4)};
        int[] coordBr = {(int) (getX() + getWidth() + 4), (int) (getY() - 4)};
        int[] coordCenter = {(int) (getX() + getWidth() / 2), (int) (getY() + getHeight() / 2)};

        Polygon lTri = new Polygon(new int[] {coordTl[0], coordBl[0], coordCenter[0]},
                                   new int[] {coordTl[1], coordBl[1], coordCenter[1]}, 3);

        Polygon tTri = new Polygon(new int[] {coordTl[0], coordTr[0], coordCenter[0]},
                new int[] {coordTl[1], coordTr[1], coordCenter[1]}, 3);

        Polygon rTri = new Polygon(new int[] {coordTr[0], coordBr[0], coordCenter[0]},
                new int[] {coordTr[1], coordBr[1], coordCenter[1]}, 3);

        Polygon bTri = new Polygon(new int[] {coordBl[0], coordBr[0], coordCenter[0]},
                new int[] {coordBl[1], coordBr[1], coordCenter[1]}, 3);

        int blocoI =  (int) (26 - getY() / 16);
        int blocoJ = (int) (getX() / 32 - 1);

        boolean esqBloq, topBloq, dirBloq, botBloq;
        esqBloq = blocoJ == 0 || matrizBlocos[blocoI][blocoJ - 1] != null;
        topBloq = blocoI == 0 || matrizBlocos[blocoI - 1][blocoJ] != null;
        dirBloq = blocoJ == 10 || matrizBlocos[blocoI][blocoJ + 1] != null;
        botBloq = blocoI == 27 || matrizBlocos[blocoI + 1][blocoJ] != null;

        boolean colisaoTopo = !topBloq && tTri.contains(b.getX(), b.getY()) && b.getySpeed() < 0;
        boolean colisaoBott = !botBloq && bTri.contains(b.getX(), b.getY()) && b.getySpeed() > 0;
        boolean colisaoEsquerda = !esqBloq && lTri.contains(b.getX(), b.getY()) && b.getxSpeed() > 0;
        boolean colisaoDireita = !dirBloq && rTri.contains(b.getX(), b.getY()) && b.getxSpeed() < 0;


        if (colisaoTopo || colisaoBott) {
            if (colisaoEsquerda || colisaoDireita){
                b.setxSpeed(-b.getxSpeed());
                b.setySpeed(-b.getySpeed());
            }
            else {
                b.setySpeed(-b.getySpeed());
            }
        }
        else if (colisaoEsquerda || colisaoDireita) {
            b.setxSpeed(-b.getxSpeed());
        }

        if (colisaoTopo || colisaoBott || colisaoEsquerda || colisaoDireita){
            decDurabilidade();
            if (isQuebrado()) {
                p.setScore(p.getScore() + getPontos());
            }
        }

    }

    public int getPontos() {
        return pontos;
    }

    public boolean isQuebrado() {
        return durabilidade == 0;
    }

    public void decDurabilidade() {
        if(durabilidade != 0)
            durabilidade--;
    }
}
