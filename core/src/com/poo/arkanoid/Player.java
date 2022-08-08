package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Player
{

    private static int highscore;
    private int score;
    private int vidas;
    private static int nivelAtual;

    public Player() throws FileNotFoundException {
        score = 0;
        vidas = 3;
        nivelAtual = 1;

        getHighscore();
    }

    public void drawHighscore(int x, int y, SpriteBatch batch, BitmapFont font) {
        String highscoreFormat = String.valueOf(highscore);
        while (highscoreFormat.length() < 6) {
            highscoreFormat = "0".concat(highscoreFormat);
        }

        font.draw(batch, highscoreFormat, x, y);
    }

    public void drawScore(int x, int y, SpriteBatch batch, BitmapFont font) {
        String scoreFormat = String.valueOf(score);
        while (scoreFormat.length() < 6) {
            scoreFormat = "0".concat(scoreFormat);
        }

        font.draw(batch, scoreFormat, x , y );
    }

    public void drawLives(int x, int y, SpriteBatch batch) {
        for (int i = 0; i < vidas; i++) {
            Texture vidasTextura = new Texture("Vaus/vaus-normal.png");
            batch.draw(vidasTextura, x, y - (float) vidasTextura.getHeight() / 2, 32, 7);
            x += 34;
        }
    }

    public void reset() {
        score = 0;
        vidas = 3;
        nivelAtual = 1;
    }

    public int getHighscore() {
        try {
            File playerData = new File("player.dat");
            if (playerData.createNewFile()) {
                FileWriter wt = new FileWriter("player.dat");
                wt.write(0);
                wt.close();
                highscore = 0;
            } else {
                Scanner sc = new Scanner(playerData);
                highscore = sc.nextInt();
            }
        } catch (IOException e) {
            System.out.println("ERRO: Nao foi possivel ler os dados do jogador");
            e.printStackTrace();
        }

        return highscore;
    }

    public void setHighscore() throws IOException {
        if (score > highscore) {
            highscore = Math.min(score, 999999);
        }

        FileWriter wt = new FileWriter("player.dat");
        wt.write(String.valueOf(highscore));
        wt.close();
    }

    public int getScore() {
        return score;
    }

    public int getVidas() {
        return vidas;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incVidas() {
        if (vidas < 5)
            vidas++;
    }

    public void decVidas() {
        if (vidas > 0)
            vidas--;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void incNivelAtual() {
        if (nivelAtual < 33)
            nivelAtual++;
        else nivelAtual = 1;
    }
}
