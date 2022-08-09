package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class MatrizBlocos implements Colidivel<Integer, Bola> {
    private final Bloco[][] matriz;
    private final Random rand;
    private int blocosQuebrados;
    private int randNBlocos;
    private Bloco ultimoQuebrado;
    private Sound hitSound;

    public MatrizBlocos(int levelNum, int linhas, int colunas, int minX, SpriteBatch batch) {
        matriz = new Bloco[linhas][colunas];

        blocosQuebrados = 0;

        FileHandle mapData = Gdx.files.internal("levels/level" + levelNum + ".ark");
        Scanner sc = new Scanner(mapData.readString());
        for (int i = 0; i < linhas; i++) {
            String line = sc.nextLine();
            matriz[i] = new Bloco[colunas];
            for (int j = 0; j < colunas; j++) {
                char tmp = line.charAt(j * 2);
                int blocoX = (minX + j * 32);
                int blocoY = ((26 - i) * 16);

                if (tmp == '0') {
                    matriz[i][j] = null;
                } else if (tmp == 'S') {
                    matriz[i][j] = new BlocoPrata(blocoX, blocoY, levelNum, batch);
                } else {
                    CorBlocos cor;
                    switch (tmp) {
                        case 'D':
                            cor = CorBlocos.GOLDEN;
                            break;
                        case 'W':
                            cor = CorBlocos.WHITE;
                            break;
                        case 'O':
                            cor = CorBlocos.ORANGE;
                            break;
                        case 'C':
                            cor = CorBlocos.CYAN;
                            break;
                        case 'G':
                            cor = CorBlocos.GREEN;
                            break;
                        case 'R':
                            cor = CorBlocos.RED;
                            break;
                        case 'B':
                            cor = CorBlocos.BLUE;
                            break;
                        case 'P':
                            cor = CorBlocos.PINK;
                            break;
                        default:
                            cor = CorBlocos.YELLOW;
                            break;
                    }

                    matriz[i][j] = new Bloco(blocoX, blocoY, cor, batch);
                }
            }
        }

        rand = new Random();

        randNBlocos = rand.nextInt(6);


    }


    public void draw() {
        for (Bloco[] i : matriz) {
            for (Bloco j : i)
                if (j != null)
                    j.draw();
        }
    }


    @Override
    public Integer colisao(Bola objeto) {
        int pontos = 0;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] != null) {
                    int[] coordTl = {(int) (matriz[i][j].getX() - 4), (int) (matriz[i][j].getY() + matriz[i][j].getHeight() + 4)};
                    int[] coordBl = {(int) (matriz[i][j].getX() - 4), (int) (matriz[i][j].getY() - 4)};
                    int[] coordTr = {(int) (matriz[i][j].getX() + matriz[i][j].getWidth() + 4), (int) (matriz[i][j].getY() + matriz[i][j].getHeight() + 4)};
                    int[] coordBr = {(int) (matriz[i][j].getX() + matriz[i][j].getWidth() + 4), (int) (matriz[i][j].getY() - 4)};
                    int[] coordCenter = {(int) (matriz[i][j].getX() + matriz[i][j].getWidth() / 2), (int) (matriz[i][j].getY() + matriz[i][j].getHeight() / 2)};

                    Polygon lTri = new Polygon(new int[]{coordTl[0], coordBl[0], coordCenter[0]},
                            new int[]{coordTl[1], coordBl[1], coordCenter[1]}, 3);

                    Polygon tTri = new Polygon(new int[]{coordTl[0], coordTr[0], coordCenter[0]},
                            new int[]{coordTl[1], coordTr[1], coordCenter[1]}, 3);

                    Polygon rTri = new Polygon(new int[]{coordTr[0], coordBr[0], coordCenter[0]},
                            new int[]{coordTr[1], coordBr[1], coordCenter[1]}, 3);

                    Polygon bTri = new Polygon(new int[]{coordBl[0], coordBr[0], coordCenter[0]},
                            new int[]{coordBl[1], coordBr[1], coordCenter[1]}, 3);


                    boolean colisaoTopo = tTri.contains(objeto.getX(), objeto.getY()) && objeto.getySpeed() < 0;
                    boolean colisaoBot = bTri.contains(objeto.getX(), objeto.getY()) && objeto.getySpeed() > 0;
                    boolean colisaoEsquerda = lTri.contains(objeto.getX(), objeto.getY()) && objeto.getxSpeed() > 0;
                    boolean colisaoDireita = rTri.contains(objeto.getX(), objeto.getY()) && objeto.getxSpeed() < 0;


                    if (colisaoTopo || colisaoBot) {
                        if (colisaoEsquerda || colisaoDireita) {
                            objeto.setxSpeed(-objeto.getxSpeed());
                            objeto.setySpeed(-objeto.getySpeed());
                        } else {
                            objeto.setySpeed(-objeto.getySpeed());
                        }
                    } else if (colisaoEsquerda || colisaoDireita) {
                        objeto.setxSpeed(-objeto.getxSpeed());
                    }

                    if (colisaoTopo || colisaoBot || colisaoEsquerda || colisaoDireita) {
                        hitSound = Gdx.audio.newSound(Gdx.files.internal(matriz[i][j].getCor().pathSfx));
                        hitSound.play(0.5f);
                        matriz[i][j].decDurabilidade();
                        if (matriz[i][j].isQuebrado()) {
                            pontos += matriz[i][j].getPontos();
                            ultimoQuebrado = matriz[i][j];
                            matriz[i][j] = null;
                            blocosQuebrados++;
                        }
                        objeto.incVelocidade(2);
                    }
                }
            }
        }

        return pontos;
    }

    public void spawnarPoder(Nivel nivel) {
        int randPoder = rand.nextInt(99);

        if (blocosQuebrados - 1 >= randNBlocos) {
            if (randPoder <= 9) {
                if (!nivel.getVaus().getAnimationActive() && nivel.getPoder() == null) {
                    nivel.setPoder(new PoderLazer(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else if (randPoder <= 24) {
                if (!nivel.getVaus().getAnimationActive() && nivel.getPoder() == null) {
                    nivel.setPoder(new PoderEnlarge(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else if (randPoder <= 39) {
                if (nivel.getPoder() == null) {
                    nivel.setPoder(new PoderCatch(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else if (randPoder <= 59) {
                if (nivel.getPoder() == null) {
                    nivel.setPoder(new PoderSlow(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else if (randPoder <= 64) {
                if (!nivel.getGateWarp().getAnimationActive() && nivel.getPoder() == null) {
                    nivel.setPoder(new PoderBreak(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else if (randPoder <= 79) {
                if (nivel.getPoder() == null) {
                    nivel.setPoder(new PoderDisruption(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            } else {
                if (nivel.getPoder() == null) {
                    nivel.setPoder(new PoderPlayer(ultimoQuebrado.getX(), ultimoQuebrado.getY(), nivel.getBatch()));
                    randNBlocos = rand.nextInt(6);
                    blocosQuebrados = 0;
                }
            }
        }
    }

    public void dispose() {
        for (Bloco[] i : matriz)
            for (Bloco j: i)
                j.dispose();
        hitSound.dispose();
    }

    public boolean isTudoQuebrado() {
        for (Bloco[] i : matriz)
            for (Bloco j : i)
                if (j != null && j.getCor() != CorBlocos.GOLDEN) return false;

        return true;
    }

    public Bloco[][] getMatriz() {
        return matriz;
    }

    public Sound getHitSound() {
        return hitSound;
    }
}
